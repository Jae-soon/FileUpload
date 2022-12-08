package com.ll.re_fileupload.app.article.repository;

import com.ll.re_fileupload.app.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
