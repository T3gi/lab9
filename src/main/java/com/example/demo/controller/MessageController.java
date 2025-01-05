package com.example.demo.controller;

import com.example.demo.model.Developer;
import com.example.demo.model.Message;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/chat")
    public String saveProject(@ModelAttribute Message message) {
        Developer developer = userService.findCurrent();
        message.setAuthor(developer.getName());
        messageService.save(message);
        return "redirect:/";
    }
}
