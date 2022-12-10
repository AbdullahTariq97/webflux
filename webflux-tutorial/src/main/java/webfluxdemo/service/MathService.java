package webfluxdemo.service;

import org.springframework.stereotype.Service;
import webfluxdemo.SleepUtil;
import webfluxdemo.dto.Response;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {

    public Response findSquare(int input){
        return new Response(input * input);
    }

    // We will assume that the multiplication process is a very time intensive process
    public List<Response> multiplicationTable(int input){
        return IntStream.rangeClosed(1,10)
                .peek(i -> SleepUtil.sleepSeconds(1))
                .peek(i -> System.out.println("math-service processing: " + i))
                .mapToObj((count) -> new Response(input * count))
                .collect(Collectors.toList());
    }
}
