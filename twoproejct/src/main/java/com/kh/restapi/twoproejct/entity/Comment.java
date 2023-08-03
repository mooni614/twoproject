package com.kh.restapi.twoproejct.entity;

import javax.persistence.Column;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kh.restapi.twoproejct.dto.CommentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Entity // 현재 클래스는 entity사용되는 클래스임을 의미
@NoArgsConstructor // 기본생성자 객체 생성
@AllArgsConstructor // 매개변수 전체 받는 생성자
@ToString
@Getter
@Slf4j
public class Comment {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본시퀀스를 DB가 자동으로 증가시킨다.
	private Long id;
	
	@Column
	private String nickname;
	
	@Column
	private String body;
	
	// 제이에이피에 단방향
	@ManyToOne // 댓글 entity(comment) 여러개가 하나의 메인글(아티클)에 연관된다.
	@JoinColumn(name="article_id") // article_id 컬럼에 article의 대표값(기본키)를 저장한다.
	private Article article;
	

	// commentDTO를 entity로 변환하는 메서드 (댓글, 메인글)
	
	public static Comment createComment(CommentDTO dto,Article article) {
		
		log.info("Comment의 createComment메서드 실행");
		log.info("dto:",dto);
		log.info("Article",article);
		
		//예외발생
		// 댓글의 id는 db가 자동으로 붙여주기 때문에 di가 넘어오는 경우 예외를 발생시킨다.
		if(dto.getId() != null) {
			throw new IllegalArgumentException("댓글생성 실패! 댓글의 id는 없어야 한다.");
		}
		
		//댓글을 생성하기 위해 요청한 id와 db에 저장된 id가 다를 경우 예외를 발생시킨다.
		if(dto.getArticleId() != article.getId()) {
			throw new IllegalArgumentException("댓글생성 실패! 게시글의 id가 잘못되었습니다.");
		}
		
		return new Comment(dto.getId(),dto.getNickname(),dto.getBody(),article);
		
	}
	
	
}
