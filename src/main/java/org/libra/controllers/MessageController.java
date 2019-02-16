package org.libra.controllers;

import org.libra.assets.validators.RequestDataValidator;
import org.libra.assets.wrappers.ResponseEntityWrapper;
import org.libra.dto.MessageDto;
import org.libra.entities.implementation.MessageEntity;
import org.libra.services.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/message")
public class MessageController
{
    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @PutMapping
    public ResponseEntity<?> putMessage(@Validated(value = {RequestDataValidator.PutMessage.class}) @RequestBody MessageDto messageDtoToPut) throws Exception
    {
        return new ResponseEntityWrapper<>(messageService.putMessage(modelMapper.map(messageDtoToPut, MessageEntity.class)));
    }

    @PatchMapping
    public ResponseEntity<?> patchMessage(@Validated(value = {RequestDataValidator.PatchMessage.class}) @RequestBody MessageDto messageDtoToPatch) throws Exception
    {
        return new ResponseEntityWrapper<>(messageService.patchMessage(modelMapper.map(messageDtoToPatch, MessageEntity.class)));
    }

    @DeleteMapping(value = {"", "/{id}"})
    public ResponseEntity<?> deleteMessage(@PathVariable(value = "id") Integer id) throws Exception
    {
        return new ResponseEntityWrapper<>(messageService.deleteMessage(id));
    }

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper)
    {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }
}