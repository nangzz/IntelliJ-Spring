package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * 처음 시작 클래스
 * 톰캣이 구동되는 클래스
 */

@SpringBootApplication
public class IntelBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelBootApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() { // LocaleResolver 언어나 지역 정보 가지고 있는 객체
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

}
