package com.bfamily.controllers;

import com.bfamily.assets.validators.RequestDataValidator;
import com.bfamily.assets.wrappers.ResponseEntityWrapper;
import com.bfamily.dto.PresentationDto;
import com.bfamily.entities.implementation.PresentationEntity;
import com.bfamily.services.PresentationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/presentation")
public class PresentationController
{
    private final PresentationService presentationService;
    private final ModelMapper modelMapper;

    @PutMapping
    public ResponseEntity<?> putPresentation(@Validated(value = {RequestDataValidator.PutPresentation.class}) @RequestBody PresentationDto presentationDtoToPut) throws Exception
    {
        return new ResponseEntityWrapper<>(presentationService.putPresentation(modelMapper.map(presentationDtoToPut, PresentationEntity.class)));
    }

    @GetMapping(value = {"", "/{email}"})
    public ResponseEntity<?> getPresentations(@PathVariable(value = "email", required = false) String email) throws Exception
    {
        return presentationService.getPresentation(email);
    }

    @PatchMapping
    public ResponseEntity<?> patchPresentation(@Validated(value = {RequestDataValidator.PatchPresentation.class}) @RequestBody PresentationDto presentationDtoToPatch) throws Exception
    {
        return new ResponseEntityWrapper<>(presentationService.patchPresentation(modelMapper.map(presentationDtoToPatch, PresentationEntity.class)));
    }

    @DeleteMapping(value = {"", "/{id}"})
    public ResponseEntity<?> deletePresentation(@PathVariable(value = "id") Integer id) throws Exception
    {
        return new ResponseEntityWrapper<>(presentationService.deletePresentation(id));
    }

    @Autowired
    public PresentationController(PresentationService presentationService, ModelMapper modelMapper)
    {
        this.presentationService = presentationService;
        this.modelMapper = modelMapper;
    }
}