package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuchaowu
 * @date 2019/5/715:33
 */
@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    @RequestMapping("/list")
    public String getUsers(){
        return "大众";
    }
}
