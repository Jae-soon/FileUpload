package com.ll.re_fileupload.app.article.entity;

import com.ll.re_fileupload.app.common.entity.BaseEntity;
import com.ll.re_fileupload.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {
    @ManyToOne
    private Member author;
    @Column(unique = true)
    private String subject;
    private String content;
}
