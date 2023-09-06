package com.example.study1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        //Key: "name", Value: name
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;

        //Hello라는 클래스를 만든 후
        //web에서 name이라는 파라미터를 받아 hello객체의 name으로 설정
        //hello 객체를 반환
    }

    static class Hello{
        private String name;
        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
    }
}
