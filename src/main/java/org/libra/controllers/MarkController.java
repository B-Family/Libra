package org.libra.controllers;

import org.libra.assets.validators.RequestDataValidator;
import org.libra.assets.wrappers.ResponseEntityWrapper;
import org.libra.dto.MarkDto;
import org.libra.entities.implementation.MarkEntity;
import org.libra.services.MarkService;
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