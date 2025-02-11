package com.example.demo.web;
import com.example.demo.web.dto.HelloResponseDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class HelloController {


    @RequestMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                @RequestParam("amount") int amount){

        return new HelloResponseDto(name,amount);
    }

}


