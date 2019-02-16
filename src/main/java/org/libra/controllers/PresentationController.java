package org.libra.controllers;

import org.libra.assets.validators.RequestDataValidator;
import org.libra.assets.wrappers.ResponseEntityWrapper;
import org.libra.dto.PresentationDto;
import org.libra.entities.implementation.PresentationEntity;
import org.libra.services.PresentationService;
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

    @PostMapping
    public ResponseEntity<?> postPresentation(@Validated(value = {RequestDataValidator.PostPresentation.class}) @RequestBody PresentationDto presentationDtoToPost) throws Exception
    {
        return new ResponseEntityWrapper<>(presentationService.postPresentation(modelMapper.map(presentationDtoToPost, PresentationEntity.class)));
    }

    @GetMapping(value = {"", "/{email}"})
    public ResponseEntity<?> getPresentations(@PathVariable(value = "email", required = false) String email) throws Exception
    {
        return presentationService.getPresentation(email);
    }

    @PutMapping
    public ResponseEntity<?> putPresentation(@Validated(value = {RequestDataValidator.PutPresentation.class}) @RequestBody PresentationDto presentationDtoToPost) throws Exception
    {
        return new ResponseEntityWrapper<>(presentationService.putPresentation(modelMapper.map(presentationDtoToPost, PresentationEntity.class)));
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