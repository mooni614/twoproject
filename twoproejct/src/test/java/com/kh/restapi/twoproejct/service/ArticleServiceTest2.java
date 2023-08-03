package com.kh.restapi.twoproejct.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kh.restapi.twoproejct.dto.ArticleForm;
import com.kh.restapi.twoproejct.entity.Article;

@SpringBootTest //어노테이션을 붙여서 스프링부트와 연동할 통합테스트를 수행한다.
class ArticleServiceTest2 {


	@Autowired 
	ArticleService articleService;
	
	@Test
	void testIndex() {
	
		// 예상
		// 모든 게시물을 불러오면 data.sql에 저장했던 데이터들이 불러와 질 것이라는 예상
	//	Article a = new Article(1L, "한꼬미", "천재");
	//	Article b = new Article(2L, "보미", "하하");
	//	Article c = new Article(3L, "세미", "후후");
		
		
		
		Article article1 = new Article(1L, "홍길동", "천재");
		Article article2 = new Article(2L, "임꺽정", "처언재");
		Article article3 = new Article(3L, "장길산", "처어언재");
		Article article4 = new Article(4L, "일지매", "처어어언재");
		

		
		// List만들기 
		List<Article> expected = new ArrayList<Article>(Arrays
				.asList(article1,article2,article3,article4));
		
		//실제
		//데이터베이스에서 데이터를 가져오는 서비스를 이용해서 데이터를 저장하고
		// 실제 내용과 위에 들어가는 내용을 비교하는 메서드로 assertEquals()
		List<Article> articles = articleService.index();
		
		
		//비교
		assertEquals(expected.toString(),articles.toString());
	
	}
	
	
 	@Test
	void testShow_성공_존재하는_id() {
 		//예상
 		
 		Long id = 1L;
 		Article expected = new Article(id,"홍길동","천재");
 		
 		//실제
 		Article article = articleService.show(id);
 		
 		//비교 
 		assertEquals(expected.toString(),article.toString());
 		
 		
	}
	
	@Test
	void testShow_성공_존재하지않는_id() {
 		//예상
 		
 		Long id = -1L;
 		Article expected = null;
 		
 		//실제
 		Article article = articleService.show(id);
 		
 		//비교 
 		assertEquals(expected,article);
 		
 		
	}
	
	//테이블이 변경되는 테스트들 실행하는 경우 이전 데이터의 영향을 받아서 
	// 하나씩 테스트할때는 정상적으로 실행이 된다.
	// 테스트가 오류가 발생할 수 있기 때문에 테스트 결과가 테이블을 변경시키는
	// 테이스틑 트랜젝션 어노테이션을 이용해서 테이트가 끝나면 롤백할 수 있도록 해야 한다.
	
	
	@Test
	@Transactional //이 테스트는 저장하지 말아라
	void testCreate_성공_title과_content만_있는_dto입력() {
		String title = "홍길동";
		String content = "수리수리마수리";
		
		ArticleForm dto = new ArticleForm(null, title, content);
		Article expected = new Article(5L,title,content);
		
		Article article = articleService.create(dto);
		
		assertEquals(expected.toString(),article.toString());
		
	}
	

	/*


	@Test
	void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateArticles() {
		fail("Not yet implemented");
	}

*/
}
