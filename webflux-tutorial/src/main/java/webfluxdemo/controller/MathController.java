package webfluxdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webfluxdemo.dto.Response;
import webfluxdemo.service.MathService;

import java.util.List;

@RestController
@RequestMapping("math")
public class MathController {

    @Autowired
    private MathService mathService;

    @GetMapping("square/{input}")
    public Response findSquare(@PathVariable("input") int input){
        return mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public List<Response> multiplicationTable(@PathVariable("input") int input){
        return mathService.multiplicationTable(input);
    }
}
