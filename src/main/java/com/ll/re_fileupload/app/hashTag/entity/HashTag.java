package com.ll.re_fileupload.app.hashTag.entity;

import com.ll.re_fileupload.app.article.entity.Article;
import com.ll.re_fileupload.app.common.entity.BaseEntity;
import com.ll.re_fileupload.app.keyword.entity.Keyword;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class HashTag extends BaseEntity {
    @ManyToOne
    @ToString.Exclude
    private Article article;
    @ManyToOne
    @ToString.Exclude
    private Keyword keyword;
}