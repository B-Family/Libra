package com.bfamily.controllers;

import com.bfamily.assets.validators.RequestDataValidator;
import com.bfamily.assets.wrappers.ResponseEntityWrapper;
import com.bfamily.dto.ParticipantDto;
import com.bfamily.entities.implementation.ParticipantEntity;
import com.bfamily.services.ParticipantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/participant")
public class ParticipantController
{
    private final ParticipantService participantService;
    private final ModelMapper modelMapper;

    @PutMapping
    public ResponseEntity<?> putParticipant(@Validated(value = {RequestDataValidator.PutParticipant.class}) @RequestBody ParticipantDto participantDtoToPut) throws Exception
    {
        return new ResponseEntityWrapper<>(participantService.putParticipant(modelMapper.map(participantDtoToPut, ParticipantEntity.class)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteParticipant(@PathVariable(value = "id") Integer id) throws Exception
    {
        return new ResponseEntityWrapper<>(participantService.deleteParticipant(id));
    }

    @Autowired
    public ParticipantController(ParticipantService participantService, ModelMapper modelMapper)
    {
        this.participantService = participantService;
        this.modelMapper = modelMapper;
    }
}