package com.bfamily.controllers;

import com.bfamily.assets.validators.RequestDataValidator;
import com.bfamily.assets.wrappers.ResponseEntityWrapper;
import com.bfamily.dto.MarkDto;
import com.bfamily.entities.implementation.MarkEntity;
import com.bfamily.services.MarkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/mark")
public class MarkController
{
    private final MarkService markService;
    private final ModelMapper modelMapper;

    @PutMapping
    public ResponseEntity<?> putMark(@Validated(value = {RequestDataValidator.PutMark.class}) @RequestBody MarkDto markDtoToPut) throws Exception
    {
        return new ResponseEntityWrapper<>(markService.putMark(modelMapper.map(markDtoToPut, MarkEntity.class)));
    }

    @Autowired
    public MarkController(MarkService markService, ModelMapper modelMapper)
    {
        this.markService = markService;
        this.modelMapper = modelMapper;
    }
}