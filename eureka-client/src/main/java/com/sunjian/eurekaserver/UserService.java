package com.sunjian.eurekaserver;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User getUserById(Long id);

    List<User> listUsers();

}
