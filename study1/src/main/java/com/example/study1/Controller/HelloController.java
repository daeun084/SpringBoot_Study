package com.example.study1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello~");
        return "hello";
    }

    @GetMapping("hello-MVC")
    public String helloMVC(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        //웹에서 "name"을 파라미터로 받아옴
        //"name" 파라미터를 name으로 변경
        return "hello-template";
    }
}
