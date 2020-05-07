package com.example.demo.controller;

import com.example.demo.dao.UserDaoService;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j // log4j와 유사
public class UserController {

    @Autowired
    UserDaoService service;

//    public List<User> list = service.getUserList();

    // 사용자 전체 조회
    @GetMapping("/users")
    public MappingJacksonValue getUserList() {

//        for (User users : list) {
////            System.out.println(users); // sout은 메모리 등 리소스를 잡아먹으므로 가급적 사용하지말고 로그 사용
//
//            // error > warn > info > debug
//            log.info(users.toString());
//        }

        List<User> list = service.getUserList();
        // list의 내용 출력
        // print 문장가지고 출력
//        for(User ele : list){
        // 출력 스트림은 리소스를 많이 먹음
        // 로그파일로 만들어서 운용하는게 좋음
//            System.out.println(ele);

        // error > warn > info > debug
//            log.info(ele.toString());
//        }

        // 전체 사용자 목록보여주면서 유저 한명씩 link 보여주기
        List<EntityModel<User>> modelList = new LinkedList<>();
        //forEach 문으로도 생성 가능
        //
        for(User us : list){
            EntityModel<User> model = new EntityModel<>(us);
            System.out.println(this.getClass());
            // 여기에 해당하는 link 붙임
            // linkTo는 controller 메소드를 가리키는 ControllerLinkBuilder 생성
            // methodOn을 이용해서 dummy method invocation 결과 넘겨줄 수 있음
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(us.getId()));
            model.add(linkTo.withRel("user-detail"));
            modelList.add(model);
        }

        // password, ssn 제외한 데이터
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate");
        FilterProvider provider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // 마지막에 매핑에 리스트를 넣어주는 것
        MappingJacksonValue mapping = new MappingJacksonValue(modelList);

        mapping.setFilters(provider);


        return mapping;
    }

    // 사용자 개별 조회 (id)
    @GetMapping("/users/{id}")
    public MappingJacksonValue getUser(@PathVariable(value = "id") Integer id) {
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

    // 사용자 등록
    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        List<User> list = service.getUserList();
        list.add(user);
    }

    // 사용자 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        List<User> list = service.getUserList();
        list.remove(id-1);
    }

    // 사용자 수정
    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody User userDetail) {
        User updateuser = service.getUser(id);
        updateuser.setName(userDetail.getName());
        updateuser.setPassword(userDetail.getPassword());
        updateuser.setJoinDate(userDetail.getJoinDate());
        updateuser.setSsn(userDetail.getSsn());
//        return updateuser;


    }
}
