package prodimagem.clin.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messagem")
public class MessageController {
    @GetMapping
    public String message(){
        return "Test Message" ;
    }

}
