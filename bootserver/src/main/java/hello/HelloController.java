package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);


    @Autowired
    GameService gameService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value= "/new",  method = RequestMethod.GET, produces = "application/json")
    public Game newcall() {
        log.info("New gamer requested the API ");
        return  gameService.newGamer();
    }

}