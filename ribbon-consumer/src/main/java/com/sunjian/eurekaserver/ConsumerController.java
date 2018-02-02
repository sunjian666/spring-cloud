package com.sunjian.eurekaserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class ConsumerController {

    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/ribbon-consumer")
    public String helloConsumer() throws ExecutionException, InterruptedException {
        return helloService.helloService().get();
    }

    @PostMapping(value = "/ribbon-create")
    public User create(@RequestBody User user){
        return helloService.create(user);
    }

    @GetMapping(value = "/ribbon-findone")
    public User findOne(@RequestParam Long id){
        return helloService.findOne(id);
    }

    @GetMapping(value = "/ribbon-findall")
    public List<User> findAll(){
        return helloService.findAll();
    }

}
