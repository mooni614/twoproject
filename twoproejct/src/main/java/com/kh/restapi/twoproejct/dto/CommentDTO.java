package com.kh.restapi.twoproejct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kh.restapi.twoproejct.entity.Article;
import com.kh.restapi.twoproejct.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor   //모든 변수를 사용하는 생성자 
@NoArgsConstructor     // 기본생성자
@EqualsAndHashCode     // equals 메서드랑 hashcode 메서드도 자동완성
@Data
public class CommentDTO {
	
	
	
	private Long id;
	private String nickname;
	private String body;
	
	
	
	//커맨드 객체를 사용해서 json데이터를 받아 저장하려 할 경우
	// json데이터의 key는 스네이크 표기법을 사영하고
	// 커맨드 객체는 카멜 표기법을 사용하면 데이터를 받지 못한다.
	// @JsonProperty("json 키")
	// 어노테이션을 사용하면 json의 key와 커맨드 객체의 필드 이름이 다르더라도 데이터를 받을 수 있다.
	
	@JsonProperty("article_id")
	private Long articleId;
	
	
	
	public CommentDTO createCommentDTO(Comment comment) {
		
		return new CommentDTO(comment.getId(),comment.getNickname(),comment.getBody(),comment.getArticle().getId());
		
	}

}
