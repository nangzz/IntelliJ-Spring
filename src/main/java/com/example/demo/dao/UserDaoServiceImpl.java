package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoServiceImpl implements IUserService {

    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "Kenneth", new Date(), "test1", "701010-1111111"));
        users.add(new User(2, "Alice", new Date(), "test2", "801111-2222222"));
        users.add(new User(3, "Elena", new Date(), "test3", "901313-1111111"));
    }

    // 사용자 전체 조회
    @Override
    public List<User> getUserList() {
        return users;
    }

    // 사용자 1명 조회
    @Override
    public User getUser(Integer id) {
        for(User user : users) {
            if(id.equals(user.getId())) { // list 다 검사해서 파라미터로 넘긴 id 값과 같으면 그거 return
                return user;
            }
        }

        // exception 처리 > UserNotFoundException 반환
        return null; // 없으면 null return
    }

    // 사용자 등록
    @Override
    public User createUser(User newUser) {
        if(newUser.getId() == null) { // 아이디가 없다라는건 null이라는 뜻 (Integer) / 만약 int id;이면 if(newUser.getId() == 0)
            newUser.setId(users.get(users.size() - 1).getId() + 1);
        }

        users.add(newUser);

        return newUser;
    }

    // 사용자 수정
    @Override
    public User modifyUser(User modifyUser) {
        Iterator<User> itUsers = users.iterator();
        while(itUsers.hasNext()) {
            User user = itUsers.next();

            if(user.getId() == modifyUser.getId()) {
                user.setName(modifyUser.getName());
                user.setJoinDate(modifyUser.getJoinDate());
                user.setPassword(modifyUser.getPassword());
                user.setSsn(modifyUser.getSsn());

                return user;
            }
        }

        return null;
    }

    /**
     * list > ordering
     * set > ordering, not duplicate
     * map(key, value) > not ordering, duplicate
     * sortedmap > ordering , low speed
     */
    // 사용자 삭제
    @Override
    public User removeUser(Integer id) {
        Iterator<User> itUsers = users.iterator();
        while(itUsers.hasNext()) {
            User user = itUsers.next();

            if(user.getId() == id) {
                itUsers.remove();
                return user;
            }
        }

        return null;
    }
}
