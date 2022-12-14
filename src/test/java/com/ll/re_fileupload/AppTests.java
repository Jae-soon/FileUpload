package com.ll.re_fileupload;

import com.ll.re_fileupload.app.article.entity.Article;
import com.ll.re_fileupload.app.article.service.ArticleService;
import com.ll.re_fileupload.app.hashTag.entity.HashTag;
import com.ll.re_fileupload.app.hashTag.service.HashTagService;
import com.ll.re_fileupload.app.home.controller.HomeController;
import com.ll.re_fileupload.app.member.controller.MemberController;
import com.ll.re_fileupload.app.member.entity.Member;
import com.ll.re_fileupload.app.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AppTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private HashTagService hashTagService;
    @Autowired
    private ArticleService articleService;

    @Test
    @DisplayName("????????????????????? ????????? ????????? ??????.")
    void t1() throws Exception {
        // WHEN
        // GET /
        ResultActions resultActions = mvc
                .perform(get("/"))
                .andDo(print());

        // THEN
        // ??????
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(HomeController.class))
                .andExpect(handler().methodName("main"))
                .andExpect(content().string(containsString("??????")));
    }

    @Test
    @DisplayName("????????? ???")
    @Rollback(false)
    void t2() throws Exception {
        long count = memberService.count();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("user1??? ????????? ??? ????????????????????? ???????????? user1??? ???????????? ????????? ??????.")
    @WithUserDetails("user1")
    void t3() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/member/profile")
                )
                .andDo(print());

        // THEN
        // ??????
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showProfile"))
                .andExpect(content().string(containsString("user1@test.com")));
    }
    @Test
    @DisplayName("user4??? ????????? ??? ????????????????????? ???????????? user1??? ???????????? ????????? ??????.")
    @WithUserDetails("user4")
    void t4() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/member/profile")
                )
                .andDo(print());

        // THEN
        // ??????
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showProfile"))
                .andExpect(content().string(containsString("user4@test.com")));
    }

    @Test
    @DisplayName("????????????")
    void t5() throws Exception {
        String testUploadFileUrl = "https://picsum.photos/200/300";
        String originalFileName = "test.png";

        // wget
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Resource> response = restTemplate.getForEntity(testUploadFileUrl, Resource.class);
        InputStream inputStream = response.getBody().getInputStream();

        MockMultipartFile profileImg = new MockMultipartFile(
                "profileImg",
                originalFileName,
                "image/png",
                inputStream
        );

        // ????????????(MVC MOCK)
        // when
        ResultActions resultActions = mvc.perform(
                        multipart("/member/join")
                                .file(profileImg)
                                .param("username", "user5")
                                .param("password", "1234")
                                .param("email", "user5@test.com")
                                .characterEncoding("UTF-8"))
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/profile"))
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("join"));

        Member member = memberService.getMemberById(5L);

        assertThat(member).isNotNull();

        memberService.removeProfileImg(member);
    }



    @Test
    @DisplayName("1??? ??????????????? ???????????? 2??? ????????????.")
    void t6() {
        Article article = articleService.getArticleById(1L);
        List<HashTag> hashTags = hashTagService.getHashTags(article);

        assertThat(hashTags.size()).isEqualTo(2);
    }
}