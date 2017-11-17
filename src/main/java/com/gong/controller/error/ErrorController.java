package com.gong.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {
    @RequestMapping("403")
    public String errorpage403(){
        return "error/403";
    }
}
