package com.example.demo.dao;

import com.example.demo.entity.User;
import static org.junit.jupiter.api.Assertions.*; // 이렇게 static으로 하면 클래스 안쓰고 메소드만 써서 사용 가능
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestUserDaoService {

    UserDaoService service = new UserDaoService();

    @Test
    public void testUserList() {
        List<User> list = service.getUserList(); // 일단 이런 클래스, 이런 메소드를 쓰겠다고 써놓고 후에 만들어가며 완성시킴

        // 테스트용 메소드 사용 (sout 같은건 잘 안씀)
        assertEquals(3, list.size(), "초기 사용자는 3명이어야 합니다."); // (나와야할 값, 조건1, "나와야할 값과 조건1가 같아야 성공이고 아닐 시 메시지")
//        Assertions.assertNotEquals(2, list.size(), "초기 사용자는 3명이어야 합니다."); // (나와야할 값, 조건1, "나와야할 값과 조건1가 달라야 성공이고 아닐 시 메시지")
//        Assertions.assertTrue(list.size() == 3, "초기 사용자는 3명이어야 합니다."); // (조건, "오류 시 나오는 메시지")
    }

    @Test
    public void testUserConfirm() {
        User user = service.getUserList().get(0);
        assertTrue(user.getId() == 1);
    }

    @Test
    public void testUserSelect() {
       User user =  service.getUser(Integer.valueOf(1)); // 1번 사용자 조회해서 정보 가져오겠다.
        assertNotNull(user);
        assertEquals(1, user.getId(), "사용자 ID가 잘못되었습니다.");
    }
}
