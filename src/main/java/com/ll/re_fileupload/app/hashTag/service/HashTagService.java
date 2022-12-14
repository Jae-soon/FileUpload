package com.ll.re_fileupload.app.hashTag.service;

import com.ll.re_fileupload.app.article.entity.Article;
import com.ll.re_fileupload.app.hashTag.entity.HashTag;
import com.ll.re_fileupload.app.hashTag.repository.HashTagRepository;
import com.ll.re_fileupload.app.keyword.entity.Keyword;
import com.ll.re_fileupload.app.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {
    private final KeywordService keywordService;
    private final HashTagRepository hashTagRepository;

    public void applyHashTags(Article article, String hashTagContents) {
        List<HashTag> oldHashTags = getHashTags(article);

        List<String> keywordContents = Arrays.stream(hashTagContents.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        List<HashTag> needToDelete = new ArrayList<>();

        for (HashTag oldHashTag : oldHashTags) {
            boolean contains = keywordContents.stream().anyMatch(s -> s.equals(oldHashTag.getKeyword().getContent()));

            if (contains == false) {
                needToDelete.add(oldHashTag);
            }
        }

        needToDelete.forEach(hashTag -> {
            hashTagRepository.delete(hashTag);
        });

        keywordContents.forEach(keywordContent -> {
            saveHashTag(article, keywordContent);
        });
    }

    private HashTag saveHashTag(Article article, String hashTagContents) {
        Keyword keyword = keywordService.save(hashTagContents);

        Optional<HashTag> opHashTag = hashTagRepository.findByArticleIdAndKeywordId(article.getId(), keyword.getId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        HashTag hashTag = HashTag.builder()
                .article(article)
                .keyword(keyword)
                .build();

        hashTagRepository.save(hashTag);

        return hashTag;
    }

    public List<HashTag> getHashTags(Article article) {
        return hashTagRepository.findAllByArticleId(article.getId());
    }

    public List<HashTag> getHashTagsByArticleIdIn(long[] ids) {
        return hashTagRepository.findAllByArticleIdIn(ids);
    }
}