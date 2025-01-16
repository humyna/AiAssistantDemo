package info.zoio.langchain4jside.controller.demo;

import info.zoio.langchain4jside.demo.entity.User;
import info.zoio.langchain4jside.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author humyna
 * @date 2025/01/16 17:16
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/getAllUsers")
    public List<User> getAllUsers() {
        return userService.list();
    }
}
