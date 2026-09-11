package pixel.academy.rest_crud_app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoRestController {

    //adaugam end point pentru hello
    @GetMapping("hello")
    public String sayHello(){
        return "Hello world";
    }
}
