package com.ll.re_fileupload.app.article.controller;

import com.ll.re_fileupload.app.article.dto.ArticleForm;
import com.ll.re_fileupload.app.article.entity.Article;
import com.ll.re_fileupload.app.article.service.ArticleService;
import com.ll.re_fileupload.app.article.service.GenFileService;
import com.ll.re_fileupload.app.common.dto.MemberContext;
import com.ll.re_fileupload.app.common.dto.RsData;
import com.ll.re_fileupload.app.fileUpload.entity.GenFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/article")
@Slf4j
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final GenFileService genFileService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "article/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    @ResponseBody
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid ArticleForm articleForm, MultipartRequest multipartRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/write";
        }
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        Article article = articleService.write(memberContext.getId(), articleForm.getSubject(), articleForm.getContent());

        RsData<Map<String, GenFile>> saveFilesRsData = genFileService.saveFiles(article, fileMap);

        return "작업중";
    }
}