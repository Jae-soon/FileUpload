package com.ll.re_fileupload.app.member.controller;

<<<<<<< HEAD
import com.ll.re_fileupload.app.member.entity.Member;
import com.ll.re_fileupload.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
=======
>>>>>>> 2340c823ebbb77bec669bc0cd934b49a15976d49
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
<<<<<<< HEAD
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
=======

@Controller
@RequestMapping("/member")
public class MemberController {
>>>>>>> 2340c823ebbb77bec669bc0cd934b49a15976d49
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PostMapping("/join")
    @ResponseBody
<<<<<<< HEAD
    public String join(String username, String password, String email, MultipartFile profileImg) {
        Member oldMember = memberService.getMemberByUsername(username);

        if (oldMember != null) {
            return "이미 가입된 회원입니다.";
        }

        Member member = memberService.join(username, "{noop}" + password, email, profileImg);

=======
    public String join() {
>>>>>>> 2340c823ebbb77bec669bc0cd934b49a15976d49
        return "가입완료";
    }
}