package com.work.SchoolScore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test(Model model) {
        model.addAttribute("msg", "測試中");
        model.addAttribute("word","再次測試中");
        return "test";
    }
}

// 只傳文字
//@RestController
//public class TestController {
//
//    @GetMapping("/test")
//    public String test() {
//        return "測試中，請嘗試";
//    }
//}
