package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author wuchaowu
 * @date 2019/5/715:29
 */
@RestController
public class UserController {

    @RequestMapping("/list")
    public Mono<String> getUsers(){
        return Mono.just("scott");
    }

}
