package org.libra.controllers;

import org.libra.assets.validators.RequestDataValidator;
import org.libra.assets.wrappers.ResponseEntityWrapper;
import org.libra.dto.AuthenticationDto;
import org.libra.entities.implementation.UserEntity;
import org.libra.services.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController
{
    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> postAuthentication(@Validated(value = {RequestDataValidator.PostAuthentication.class}) @RequestBody AuthenticationDto authenticationDtoToPost) throws Exception
    {
        return new ResponseEntityWrapper<>(authenticationService.postAuthentication(modelMapper.map(authenticationDtoToPost, UserEntity.class)));
    }

    @GetMapping(value = {"", "/{status}"})
    public ResponseEntity<?> getAuthentication(@PathVariable(value = "status", required = false) String status) throws Exception
    {
        return authenticationService.getAuthentication(status);
    }

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, ModelMapper modelMapper)
    {
        this.authenticationService = authenticationService;
        this.modelMapper = modelMapper;
    }
}