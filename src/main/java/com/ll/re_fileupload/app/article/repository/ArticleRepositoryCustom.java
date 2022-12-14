package com.ll.re_fileupload.app.article.repository;

import com.ll.re_fileupload.app.article.entity.Article;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<Article> getQslArticlesOrderByIdDesc();
    List<Article> searchQsl(String kwType, String kw);
}