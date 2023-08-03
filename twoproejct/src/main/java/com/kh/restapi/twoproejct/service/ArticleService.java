package com.kh.restapi.twoproejct.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.restapi.twoproejct.dto.ArticleForm;
import com.kh.restapi.twoproejct.entity.Article;
import com.kh.restapi.twoproejct.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

// 서비스
// 데이터베이스에 접근해서 결과를 만드는 곳
// 컨트롤러에서 데이터베이스 접근을 해야되면 서비스한테 넘겨주고 
// 넘겨준 데이터를 가지고 실제 데이터베이스 접근해서 데이터를 가져온다.


@Slf4j // 데이터베이스의 로그를 확인하기 위해서 
@Service
public class ArticleService {
	
	
	//ArticleRepository 인터페이스 객체의 bean을 주입받는다.
	
	@Autowired
	private ArticleRepository articleRepository;
	
	//Article 전체목록 조회 실행
	public List<Article> index(){
		
		log.info("아티클 서비스의 인덱스가 실행");
		
		
		
		
		return articleRepository.findAll();
		
		
	}
	
	
	//단건 조회(한 아이디검색)
	public Article show(Long id) {
		
		log.info("아티클 서비스의 쇼가 실행");
		
		
		return articleRepository.findById(id).orElse(null); //orElse는 데이터가 없는 경우 넘기는 것
		
	}
	
	
	public Article create(ArticleForm dto) {
		
		log.info("아티클 서비스의 크리에이트가 실행");
		
		Article article = dto.toEntity(); // 데이터용으로 바꿔야함
		
		// id는 DB가 자동으로 생성하므로 id가 넘어오는 데이터를 저장하지 않는다.
		if(article.getId() != null) {
			return null;
			
		}
		
		
		return articleRepository.save(article);
		
	}
	
	
	//수정
	public Article update(Long id, ArticleForm dto) {
		
		log.info("아티클 서비스의 업데이트가 실행");
		Article article = dto.toEntity();
		
		Article target = articleRepository.findById(id).orElse(null);
		
		//수정할 대상이 없거나 아이디가 다른 경우
		// 잘못된 요청(400)
		
		if(target == null || id != article.getId()) {
			log.info("잘못된 요청입니다. id;{}, article:{}", id,article.toString());
			return null;
		}
		
		// 수정할 title, 수정할 content가 입력되었니?
		// 수정할 대상이 있는 필드들을 새로 저장해주기
		// patch매서드가 한다. 
		// 검사할 메서드를 작성하고 Article 크래스에서 저장
		
		
		target.patch(article);
		
		
		return articleRepository.save(target);
	}

	
	//삭제
	public Article delete(Long id) {
		
		
		log.info("아티클 서비스의 딜리트가 실행");
	
		
		Article target = articleRepository.findById(id).orElse(null);
		
	
		
		if(target == null) {
			log.info("잘못된 요청입니다. {}번 글은 테이블에 존재하지 않습니다.", id);
			return null;
		}
		
		// 수정할 title, 수정할 content가 입력되었니?
		// 수정할 대상이 있는 필드들을 새로 저장해주기
		// patch매서드가 한다. 
		// 검사할 메서드를 작성하고 Article 크래스에서 저장
		
		
		
		articleRepository.delete(target);
		return target;
		
	}
	
	
	// 데이터를 추가할 때 문제가 ㅏㄹ생하면 추가한 내용을 가지고 게시판에
	// 전체 출력을 할 때 문제가 바생하면 실행내용을 모두 취소하고 
	// 기존에 내용으로 돌아간다.
	
	// 정상적으로 실행하면 데이터를 영구적으로 저장까지 할 수 있도록
	// 만들어주는게 트렌젝션처리방법
	@Transactional
	public List<Article> createArticles(List<ArticleForm> dtos){
		
		log.info("서비스의 createArticles메서드 실행");
		
		//stream()
		// 자바8부터 추가된 자바스트림 
		// 추가된 컬렉션의 저장요소를 하나씩 참조해 람다식으로 처리할 수 있도록 해주는 반복자
		
		//람다식 코드(코드를 간결하게 사용)
		
		// 스트림 특징
		// 데이터소스를 변경하지 않는다.(읽기모드)
		// 일회성 스트림도 요소를 모두 읽고 나면 닫혀서 사용할 수 없다
		// 필요하다면 새 스트림을 생성해서 사용한다.
		// 내부적으로 반복 처리함
		
		//toList() : 
		//toMap
		//toSet
		//stream의 요소를 수집하여 요소를 그룹화하거나
		// 결과를 담아 반환하는데 사용한다.
		
		// stream()
		// 스트림 생성: 스트림 인스턴스 생성
		// 중간 연산: 필터링 및 매핑을 통해서 얻고자 하는 데이터로 가공하는 중간작업
		// 최종 연산 : 최종결과를 만들어내는 작업
		
		
		//Array.stream(dtos)
		
		List<Article> articleList = dtos.stream()
				.map(dto -> dto.toEntity())
				.collect(Collectors.toList());
		
		log.info("articleList:{}", articleList);
		
		articleList.stream().forEach(article->articleRepository.save(article));
		
		
		//강제예외발생
		//데이터베이스 처리과정에서 예외가 발생할 경우에는 orElseThrow()메서드 이용하여 처리 
		// -1L 아이디값들이 마이너스가 나올수는 없다!
		// 일부러 예외발생
		articleRepository.findById(-1L).orElseThrow(
				() -> new IllegalArgumentException("예외처리메시지"));
		
		
		/*
		//dto묶음을 entity묶음으로 변환하는 작업
		List<Article> articleList = new ArrayList<>();
		for(int i = 0; i < dtos.size(); i++) {
			Article entity = dtos.get(i).toEntity();
			articleList.add(entity);
		}
		
		//entity 묶음을 DB로 저장 
		for(int i = 0; i < articleList.size(); i++) {
			articleRepository.save(articleList.get(i));
		*/
		return articleList;
	}
	
	
	// Test
	// 프로그램의 품질 검증을 위한 것 
	// 우리가 의도한대로 프로그램이 동작하는지 확인하는 것
	
	//TDD (Test Driven Development)
	//소프트웨어 개발시 먼저 테스트를 작성하고 그 다음에 코드를 작성하는 것을 강조
	
	// 코드 작성 전에 먼저 테스트를 해서 코드를 작성하면 코드의 품질과 안정성을 높일 수 있다.
	
	
	// Junit
}
