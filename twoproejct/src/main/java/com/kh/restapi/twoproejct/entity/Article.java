package com.kh.restapi.twoproejct.entity;

import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity // 현재 클래스는 entity사용되는 클래스임을 의미
@NoArgsConstructor // 기본생성자 객체 생성
@AllArgsConstructor // 매개변수 전체 받는 생성자
@ToString
@Getter
public class Article {

	


		// 기본키로 사용할 필드선언 (프라이머리 키)
		// 기본키를 자동으로 생성하려면

		@Id // 필드를 기본키로 설정한다.
		// @GeneratedValue // 기본키 값을 자동으로 생성한다. 시퀀스가 무조건 1부터 시작한다.

		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		// 테이블 컬럼과 매핑한다.
		@Column
		private String title;

		@Column
		private String content;

		public void patch(Article article) {
		
			if(article.title != null) {
				
				this.title = article.title;
			}
			
			if(article.content != null) {
				
				this.content = article.content;
			}
				
			
		}

	}
