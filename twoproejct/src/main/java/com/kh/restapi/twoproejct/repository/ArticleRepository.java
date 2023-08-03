package com.kh.restapi.twoproejct.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.kh.restapi.twoproejct.entity.Article;


public interface ArticleRepository extends JpaRepository<Article,Long> {

}
