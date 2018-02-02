package com.sunjian.eurekaserver;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class HelloController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/hello")
    public String index(){

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());

        return "Hello World";
    }

    @PostMapping(value = "/user")
    public User create(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping(value = "/user")
    public User findOne(@RequestParam Long id){
        return userService.getUserById(id);
    }

    @GetMapping(value = "/users")
    public List<User> findAll(){
        return userService.listUsers();
    }

}
