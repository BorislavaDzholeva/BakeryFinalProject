package BakeryProject.demo.web;

import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class CartRestController {
    private final UserRepository userRepository;

    public CartRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/cart/test/")
    public Map<String,String> test(Principal principal){
        Map<String,String> pesho = Map.of("pesho","pesho");
        return pesho;
    }
}
