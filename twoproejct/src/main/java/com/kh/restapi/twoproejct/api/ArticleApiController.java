package com.kh.restapi.twoproejct.api;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//RestAPI용 컨트롤러: 데이터를 반환한다(json형식)
import org.springframework.web.bind.annotation.RestController;

import com.kh.restapi.twoproejct.dto.ArticleForm;
import com.kh.restapi.twoproejct.entity.Article;
import com.kh.restapi.twoproejct.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ArticleApiController {

	
	//articleService 인터페이스의 객체를 bean으로 자동주입받는다.
	@Autowired
	private ArticleService articleService;
	
	// get (전체 글 조회) articles
	
	
	@GetMapping("/api/articles")
	public List<Article> index() {
		
		
		log.info("아티클 컨트롤러의 인덱스가 실행");
		
		
		//서비스의 인덱스를 호출해서 데이터베이스 처리 결과를 받는다.
		return articleService.index();
		
		
	}
	
	
	// 하나의 글 조회할 때 메서드
	@GetMapping("/api/articles/{id}")
	public Article show(@PathVariable Long id) {
		log.info("아티클 컨트롤러의 쇼가 실행");
		
		return articleService.show(id);
		
	}
	
	
	// post(생성)
//폼에서 데이터를 받아올때는 커맨드 객체로 받으면 되지만
	// RestAPI에서 json으로 던지는 데이터를 받을 때 body부분에 담겨오는 데이터를 받아야하므로
	// 커멘드 객체에 @RequestBody 어노테이션을 붙여서 받아야된다.
	// 데이터를 테이블에 저장하고 저장한 데이터를 리턴시킨다.
	// 상태코드와 데이터를 두개를 받아와야 되기 때문에 응답을 받을 때 
	// ResponseENtity 객체를 사용해야 된다.
	@PostMapping("/api/articles") 
	public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
		
		
		log.info("아티클 컨트롤러의 크레이트가 실행");
		log.info(dto.toString());
		
		
		Article saved = articleService.create(dto);
		
		
		// 모든게 정상적으로 입력받고 저장까지 완료한다는 전제로 만들어짐.
		// 만약 데이터 하나를 빼고 들어오거나 오류가 생기면 
		
		
		
		return saved != null ?
				ResponseEntity.status(HttpStatus.CREATED).body(saved):
					ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
				
				// build메서드: 에러가 발생해서 바디부분이 비어있을 경우 build를 이용해서 바디부분없이 넘긴다.
		
	}
	
	
	// patch
	@PatchMapping("/api/articles/{id}")
	public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
		
		log.info("아티클 컨트롤러의 업데이트가 실행");
		log.info("id:"+id);
		log.info(dto.toString());
		
		Article article = dto.toEntity(); // 수정하기 위해 입력한 데이터
		
		
		//수정할 엔티티를 조회
		Article updated = articleService.update(id, dto);
		
		
		
		return updated != null ?
				ResponseEntity.status(HttpStatus.OK).body(updated):
					ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
				
		
	}
	
	
	// delete
	// 글번호를 이용해서 삭제
	@DeleteMapping("/api/articles/{id}")
	public ResponseEntity<Article> delete(@PathVariable Long id){
		
		log.info("아티클 컨트롤러의 삭제가 실행");
		log.info("id:"+id);
		
	
		
		Article deleted = articleService.delete(id);
		
		
		
		return deleted != null ?
				ResponseEntity.status(HttpStatus.OK).build():
					ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
				
		
	}
	
	
}
