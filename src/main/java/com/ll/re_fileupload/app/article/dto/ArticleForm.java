package com.ll.re_fileupload.app.article.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ArticleForm {
    @NotEmpty
    private String subject;
    @NotEmpty
    private String content;
    private String hashTagContents;
}
