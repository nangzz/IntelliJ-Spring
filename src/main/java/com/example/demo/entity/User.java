package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data // 세터게터 , toString() 따로 안만들어도 이 어노테이션이 있으면 자동으로 있다고 처리 됨
@AllArgsConstructor // 생성자 따로 안만들어도 /  단, 이 어노테이션은 default 생성자는 안생김
@NoArgsConstructor // default 생성자 만들어주는 어노테이션
@JsonFilter("UserInfo") // 컨트롤러에서 만든 필터 매핑시키기 //@JsonIgnoreProperties(value = {"password", "ssn"})
public class User {

    private Integer id;
    private String name;
    private Date joinDate;
//    private String joinDate;

//    @JsonIgnore
    private String password;
//    @JsonIgnore
    private String ssn;

}

