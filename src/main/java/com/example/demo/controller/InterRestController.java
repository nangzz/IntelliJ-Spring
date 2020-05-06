package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * 다국어 처리를 위해 생성한 클래스
 */

@RestController
public class InterRestController {

    @Autowired
    private MessageSource messageSource; // MessageSource : 메시지 번들에서 값을 가져와서 넣어주는 클래스

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {
        return "Good Morning";
    }

    @GetMapping(path = "/hello-world")
    public String helloWorld(@RequestHeader(name="Accept-language", required = false)Locale locale) { // Accept-language가 헤더에 담겨 Locale과 함께 전달
        return messageSource.getMessage("greeting.message", null, locale); // (".properties에서 지정한 이름", "추가 내용", locale)
    }
}
