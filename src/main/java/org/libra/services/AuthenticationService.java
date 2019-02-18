//package org.libra.services;
//
//import org.libra.utilities.AuthorityUtility;
//import org.libra.utilities.ServiceUtility;
//import org.libra.entities.implementation.UserAuthorityEntity;
//import org.libra.entities.implementation.UserEntity;
//import org.libra.exceptions.BadCredentialsException;
//import org.libra.exceptions.BadRequestException;
//import org.libra.exceptions.DuplicatedEntryException;
//import org.libra.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AuthenticationService
//{
//    private final AuthorityUtility authorityUtility;
//    private final ServiceUtility serviceUtility;
//    private final UserRepository userRepository;
//    private final ResponseBean responseBean;
//    private final HttpHeaders httpHeaders;
//
//    private List<UserAuthorityEntity> setRegistrationObjectAuthorityValues(String userAuthorityEmail)
//    {
//        List<UserAuthorityEntity> userAuthorityEntityList = new ArrayList<>();
//        UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();
//        userAuthorityEntity.setEmail(userAuthorityEmail);
//        userAuthorityEntity.setAuthority("ROLE_ADMIN");
//        userAuthorityEntityList.add(userAuthorityEntity);
//        return userAuthorityEntityList;
//    }
//    private UserEntity completeRegistrationObject(UserEntity userEntity)
//    {
//        userEntity.setEnabled(1);
//        userEntity.setAuthorities(setRegistrationObjectAuthorityValues(userEntity.getEmail()));
//        return userEntity;
//    }
//
//    public ResponseBean postAuthentication(UserEntity userEntity) throws Exception
//    {
//        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByEmail(userEntity.getEmail());
//        if (!optionalUserEntity.isPresent())
//        {
//            userRepository.save(serviceUtility.encodeUserEntityPassword(completeRegistrationObject(userEntity)));
//            responseBean.setStatus(HttpStatus.CREATED);
//            responseBean.setHeaders(httpHeaders);
//            responseBean.setResponse("User with email: '" + userEntity.getEmail() + "' successfully registered");
//        }
//        else
//        {
//            throw new DuplicatedEntryException("User with email: '" + userEntity.getEmail() + "' already exists");
//        }
//        return responseBean;
//    }
//    public ResponseEntity<?> getAuthentication(String status) throws Exception
//    {
//        ResponseEntity<?> responseEntity;
//        if ((status != null) && (status.equals("success")))
//        {
//            responseEntity = new ResponseEntity<>(userRepository.findUserEntityByEmail(authorityUtility.getCurrentAuthenticationEmail()), httpHeaders, HttpStatus.OK);
//        }
//        else if ((status != null) && (status.equals("invalid")))
//        {
//            throw new BadCredentialsException("Invalid authentication data");
//        }
//        else
//        {
//            throw new BadRequestException("This is a authentication page, please use POST method to authenticate");
//        }
//        return responseEntity;
//    }
//
//    @Autowired
//    public AuthenticationService(AuthorityUtility authorityUtility, ServiceUtility serviceUtility,
//                                 UserRepository authenticationRepository, ResponseBean responseBean, HttpHeaders httpHeaders)
//    {
//        this.authorityUtility = authorityUtility;
//        this.serviceUtility = serviceUtility;
//        this.userRepository = authenticationRepository;
//        this.responseBean = responseBean;
//        this.httpHeaders = httpHeaders;
//    }
//}