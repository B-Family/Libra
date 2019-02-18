//package org.libra.services;
//
//import org.libra.utilities.AuthorityUtility;
//import org.libra.utilities.ServiceUtility;
//import org.libra.entities.implementation.UserAuthorityEntity;
//import org.libra.entities.implementation.UserEntity;
//import org.libra.exceptions.AccessDeniedException;
//import org.libra.exceptions.NotFoundEntryException;
//import org.libra.exceptions.SelfDestructionException;
//import org.libra.repositories.UserRepository;
//import org.libra.exceptions.DuplicatedEntryException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//public class UserService
//{
//    private final AuthorityUtility authorityUtility;
//    private final ServiceUtility serviceUtility;
//    private final UserRepository userRepository;
//    private final ResponseBean responseBean;
//    private final HttpHeaders httpHeaders;
//
//    private UserEntity setUserEntityNestedAuthorityEmails(UserEntity userEntity)
//    {
//        for (UserAuthorityEntity userAuthorityEntity : userEntity.getAuthorities())
//        {
//            userAuthorityEntity.setEmail(userEntity.getEmail());
//        }
//        return userEntity;
//    }
//
//    public ResponseBean postUser(UserEntity userEntity) throws Exception
//    {
//        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByEmail(userEntity.getEmail());
//        if (!optionalUserEntity.isPresent())
//        {
//            if (authorityUtility.validateAdminAuthority())
//            {
//                userRepository.save(serviceUtility.encodeUserEntityPassword(setUserEntityNestedAuthorityEmails(userEntity)));
//                responseBean.setHeaders(httpHeaders);
//                responseBean.setStatus(HttpStatus.CREATED);
//                responseBean.setResponse("User with email: '" + userEntity.getEmail() + "' successfully added");
//            }
//            else
//            {
//                throw new AccessDeniedException("Access denied for you authority");
//            }
//        }
//        else
//        {
//            throw new DuplicatedEntryException("User with email: '" + userEntity.getEmail() + "' already exists");
//        }
//        return responseBean;
//    }
//    public ResponseEntity<?> getUser(String email) throws Exception
//    {
//        ResponseEntity<?> responseEntity;
//        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByEmail(email);
//        if (optionalUserEntity.isPresent())
//        {
//            responseEntity = new ResponseEntity<>(optionalUserEntity.get(), httpHeaders, HttpStatus.OK);
//        }
//        else
//        {
//            throw new NotFoundEntryException("User with email: '" + email + "' not found");
//        }
//        return responseEntity;
//    }
//    public ResponseBean putUser(UserEntity userEntity) throws Exception
//    {
//        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByEmail(userEntity.getEmail());
//        if (optionalUserEntity.isPresent())
//        {
//            if (authorityUtility.getCurrentAuthenticationEmail().equals(userEntity.getEmail())
//                    || authorityUtility.validateAdminAuthority())
//            {
//                userRepository.save(serviceUtility.encodeUserEntityPassword(serviceUtility
//                        .patchEntity(optionalUserEntity.get(), userEntity)));
//                responseBean.setHeaders(httpHeaders);
//                responseBean.setStatus(HttpStatus.OK);
//                responseBean.setResponse("User with email: '" + userEntity.getEmail() + "' successfully modified");
//            }
//            else
//            {
//                throw new AccessDeniedException("Access denied for you authority");
//            }
//        }
//        else
//        {
//            throw new NotFoundEntryException("User with email: '" + userEntity.getEmail() + "' not found");
//        }
//        return responseBean;
//    }
//    public ResponseBean deleteUser(String email) throws Exception
//    {
//        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByEmail(email);
//        if (optionalUserEntity.isPresent())
//        {
//            if (!authorityUtility.getCurrentAuthenticationEmail().equals(email)
//                    && authorityUtility.validateAdminAuthority())
//            {
//                userRepository.deleteUserEntityByEmail(email);
//                responseBean.setHeaders(httpHeaders);
//                responseBean.setStatus(HttpStatus.OK);
//                responseBean.setResponse("User with email: '" + email + "' successfully deleted");
//            }
//            else if (authorityUtility.getCurrentAuthenticationEmail().equals(email)
//                    && authorityUtility.validateAdminAuthority())
//            {
//                throw new SelfDestructionException("You cannot delete yourself");
//            }
//            else
//            {
//                throw new AccessDeniedException("Access denied for you authority");
//            }
//        }
//        else
//        {
//            throw new NotFoundEntryException("User with email: " + email + " not found");
//        }
//        return responseBean;
//    }
//
//    @Autowired
//    public UserService(AuthorityUtility authorityUtility, ServiceUtility serviceUtility,
//                       UserRepository userRepository, ResponseBean responseBean, HttpHeaders httpHeaders)
//    {
//        this.authorityUtility = authorityUtility;
//        this.serviceUtility = serviceUtility;
//        this.userRepository = userRepository;
//        this.responseBean = responseBean;
//        this.httpHeaders = httpHeaders;
//    }
//}