package ru.itis.kpfu.kafkagrpcintegration.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.kpfu.kafkagrpcintegration.service.MessageService;

/**
 * @author Zagir Dingizbaev
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/message")
    public void sendMessage(@RequestParam String message) {
        messageService.sendMessage(message);
    }
}
