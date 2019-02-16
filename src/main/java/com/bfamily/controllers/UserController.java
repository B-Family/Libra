package com.bfamily.controllers;

import com.bfamily.assets.validators.RequestDataValidator;
import com.bfamily.assets.wrappers.ResponseEntityWrapper;
import com.bfamily.dto.UserDto;
import com.bfamily.entities.implementation.UserEntity;
import com.bfamily.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController
{
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PutMapping
    public ResponseEntity<?> putUser(@Validated(value = {RequestDataValidator.PutUser.class}) @RequestBody UserDto userDtoToPut) throws Exception
    {
        return new ResponseEntityWrapper<>(userService.putUser(modelMapper.map(userDtoToPut, UserEntity.class)));
    }

    @GetMapping(value = {"", "/{email}"})
    public ResponseEntity<?> getUser(@PathVariable(value = "email") String email) throws Exception
    {
        return userService.getUser(email);
    }

    @PatchMapping
    public ResponseEntity<?> patchUser(@Validated(value = {RequestDataValidator.PatchUser.class}) @RequestBody UserDto userDtoToPatch) throws Exception
    {

        return new ResponseEntityWrapper<>(userService.patchUser(modelMapper.map(userDtoToPatch, UserEntity.class)));
    }

    @DeleteMapping(value = {"", "/{email}"})
    public ResponseEntity<?> deleteUser(@PathVariable(value = "email") String email) throws Exception
    {
        return new ResponseEntityWrapper<>(userService.deleteUser(email));
    }

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper)
    {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
}