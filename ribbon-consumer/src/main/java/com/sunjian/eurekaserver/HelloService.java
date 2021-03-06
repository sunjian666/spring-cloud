package com.sunjian.eurekaserver;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Function;

@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    /*@HystrixCommand(fallbackMethod = "helloFallback")  //同步执行
    public String helloService(){
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
    }*/

    @HystrixCommand(fallbackMethod = "helloFallback", observableExecutionMode = ObservableExecutionMode.EAGER)  //异步执行  //表示observe()立即执行，反之toObserveable()延时执行
    public Future<String> helloService(){

        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
            }
        };

    }

    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
    public User create(User user){
        return restTemplate.postForEntity("http://HELLO-SERVICE/user", user, User.class).getBody();
    }

    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
    public User findOne(Long id){
        return restTemplate.getForObject("http://HELLO-SERVICE/user?id={1}", User.class, id);
    }

    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
    public List<User> findAll(){
        return restTemplate.getForObject("http://HELLO-SERVICE/users", List.class);
    }

    public String helloFallback(Throwable e){ //可以获取对应异常
        return "error";
    }



}
