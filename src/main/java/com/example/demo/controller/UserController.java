package com.example.demo.controller;

import com.example.demo.dao.UserDaoService;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j // log4j와 유사
public class UserController {

    @Autowired
    UserDaoService service;

    // 사용자 전체 조회
    @GetMapping("/users")
    public List<User> getUserList() {
        List<User> list = service.getUserList();

        for (User users : list) {
//            System.out.println(users); // sout은 메모리 등 리소스를 잡아먹으므로 가급적 사용하지말고 로그 사용

            // error > warn > info > debug
            log.info(users.toString());
        }

        return list;
    }

    // 사용자 개별 조회 (id)
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(value = "id") Integer id) {
        User user = service.getUser(id);

        // 아이디 없는 경우 익셉션 발생시키기
        if(user == null) {
            throw new UserNotFoundException("id : " + id);
        }
        return user;
    }

    // 관리자가 볼 수 있는 사용자 개별 조회
    @GetMapping("/admin/users/{id}")
    public MappingJacksonValue getUserByAdmin(@PathVariable Integer id) {
        User user = service.getUser(id);

        // 아이디 없는 경우 익셉션 발생시키기
        if(user == null) {
            throw new UserNotFoundException("id : " + id);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn"); // 보여줄 데이터
        FilterProvider provider = new SimpleFilterProvider().addFilter("UserInfo", filter); // 이 'UserInfo'를 Entity에 매핑시켜야 함
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(provider);

        return mapping;
    }
}
