package jian.springcafe.controller;

import jian.springcafe.domain.User;
import jian.springcafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String createForm(){
        return "user/form"; // template 폴더에 만들었다면
    }

    @PostMapping("/user/form")
    public String create(UserForm form){
        User user = new User();
        user.setName(form.getUserId());

        userService.join(user);
        return "redirect:/";
    }

}
