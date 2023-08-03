package com.kh.restapi.twoproejct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kh.restapi.twoproejct.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

	// 특정 게시글의 모든 댓글을 조회하는 메서드
	@Query(value = "select & from comment where article_id=:",nativeQuery=true)
	List<Comment> findByArticleId(Long articleId);
	
	// 특정 닉네임의 모든 댓글을 조회하는 메서드
	
	
}
