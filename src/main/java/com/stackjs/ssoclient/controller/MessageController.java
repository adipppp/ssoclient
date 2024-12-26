package com.stackjs.ssoclient.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stackjs.ssoclient.dto.MessageDto;
import com.stackjs.ssoclient.service.MessageService;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping({"", "/"})
    public String getAllMessages(Model model) {
        List<MessageDto> messageDtos;
        try {
            messageDtos = messageService.findAll();
        } catch (RuntimeException exception) {
            model.addAttribute("error", exception.getMessage());
            return "error";
        }
        model.addAttribute("messages", messageDtos);
        return "messages";
    }

    @GetMapping({"/add", "/add/"})
    public String getMessage(Model model) {
        model.addAttribute("message", new MessageDto());
        return "addMessage";
    }

    @PostMapping({"/add", "/add/"})
    public String postMessage(@ModelAttribute MessageDto message, Model model) {
        try {
            messageService.save(message);
        } catch (RuntimeException exception) {
            model.addAttribute("error", exception.getMessage());
            return "error";
        }
        return "redirect:/messages";
    }

}
