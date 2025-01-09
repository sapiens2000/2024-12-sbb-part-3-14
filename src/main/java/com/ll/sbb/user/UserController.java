package com.ll.sbb.user;

import com.ll.sbb.answer.Answer;
import com.ll.sbb.answer.AnswerService;
import com.ll.sbb.question.Question;
import com.ll.sbb.question.QuestionService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="
                +client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("kakao", location);
        return "login_form";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String ㅎprofile(Model model,
                          @RequestParam(value = "questionPage", defaultValue = "0") int questionPage,
                          @RequestParam(value = "answerPage", defaultValue = "0") int answerPage,
                          Principal principal){

        SiteUser user = this.userService.getUser(principal.getName());
        Page<Question> questionList = questionService.getListByAuthor(questionPage, user);
        Page<Answer> answerList = answerService.getListByAuthor(answerPage, user);

        model.addAttribute("questionPage", questionList);
        model.addAttribute("answerPage", answerList);
        return "profile";
    }

//    @GetMapping("/login/oauth2/code/kakao")
//    public ResponseEntity<?> loginOAuth2(@RequestParam("code") String code, Model model) {
//
//        // 1. header 생성
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
//
//        // 2. body 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code"); //고정값
//        params.add("client_id", "7c966702772739e3aec9a79623b9e3d5");
//        params.add("redirect_uri", "http://localhost:8080/user/login/oauth2/code/kakao"); //등록한 redirect uri
//        params.add("code", code);
//
//        // 3. header + body
//        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
//
//        // 4. http 요청하기
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Object> response = restTemplate.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                httpEntity,
//                Object.class
//        );
//
//        RestTemplate rt = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + response.getBody());
//        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String, String>> kakaoInfo = new HttpEntity<>(headers);
//
//        ResponseEntity<KakaoProfile> kakaoResponse = rt.exchange("https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST, kakaoInfo, KakaoProfile.class);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
