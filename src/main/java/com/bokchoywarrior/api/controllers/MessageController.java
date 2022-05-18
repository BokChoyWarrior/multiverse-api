package com.bokchoywarrior.api.controllers;

import com.bokchoywarrior.api.models.Message;
import com.bokchoywarrior.api.repositories.MessageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public List<Message> messages() {
        return messageRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Message get(@PathVariable Long id) {
        Optional<Message> found = messageRepository.findById(id);
        if (!found.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Entity with id: %s not found", id));
        }
        return found.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message create(@RequestBody final Message message) {
        return messageRepository.saveAndFlush(message);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        Optional<Message> foundMessage = messageRepository.findById(id);
        if (!foundMessage.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Entity with id: %s not found", id));
        }
        messageRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Message update(@PathVariable Long id, @RequestBody Message message) {
        Optional<Message> foundMessage = messageRepository.findById(id);
        if (!foundMessage.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Entity with id: %s not found", id));
        }
        Message existingMessage = foundMessage.get();
        BeanUtils.copyProperties(message, existingMessage, "id");
        return messageRepository.saveAndFlush(existingMessage);
    }
}
