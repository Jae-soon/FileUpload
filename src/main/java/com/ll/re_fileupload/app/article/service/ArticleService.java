package com.ll.re_fileupload.app.article.service;

import com.ll.re_fileupload.app.article.entity.Article;
import com.ll.re_fileupload.app.article.repository.ArticleRepository;
import com.ll.re_fileupload.app.gen.service.GenFileService;
import com.ll.re_fileupload.app.gen.entity.GenFile;
import com.ll.re_fileupload.app.hashTag.service.HashTagService;
import com.ll.re_fileupload.app.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final GenFileService genFileService;
    private final HashTagService hashTagService;

    public Article write(Long authorId, String subject, String content) {
        return write(new Member(authorId), subject, content);
    }

    // 해시태그가 없을 경우 빈칸으로
    public Article write(Member author, String subject, String content) {
        return write(author, subject, content, "");
    }

    public Article write(Member author, String subject, String content, String hashTagsStr) {
        Article article = Article
                .builder()
                .author(author)
                .subject(subject)
                .content(content)
                .build();

        articleRepository.save(article);

        // 해시태그 저장
        hashTagService.applyHashTags(article, hashTagsStr);

        return article;
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public void addGenFileByUrl(Article article, String typeCode, String type2Code, int fileNo, String url) {
        genFileService.addGenFileByUrl("article", article.getId(), typeCode, type2Code, fileNo, url);
    }

    public Article getForPrintArticleById(Long id) {
        Article article = getArticleById(id);

        Map<String, GenFile> genFileMap = genFileService.getRelGenFileMap(article);

        article.getExtra().put("age__name__33", 22);
        article.getExtra().put("genFileMap", genFileMap);

        return article;
    }

    public void modify(Article article, String subject, String content) {
        article.setSubject(subject);
        article.setContent(content);
        articleRepository.save(article);
    }
}
