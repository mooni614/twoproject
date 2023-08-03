package com.kh.restapi.twoproejct.dto;


import com.kh.restapi.twoproejct.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//롬복 
// set, get을 자동으로 생성해주는 어노테이션 사용

@Setter
@Getter
@ToString
@AllArgsConstructor   //모든 변수를 사용하는 생성자 
@NoArgsConstructor     // 기본생성자
@EqualsAndHashCode     // equals 메서드랑 hashcode 메서드도 자동완성
@Data
public class ArticleForm {

	private Long id;
	private String title;
	private String content;
	
	//DTO 클래스에 데이터를 Etity(테이블과 매핑되는 클래스)로 변환하는 메서드를 추가한다.
	
	public Article toEntity() {
		
		return new Article(id,title,content);
		
	}
	
	
	
}
