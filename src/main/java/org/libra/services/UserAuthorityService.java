//package org.libra.services;
//
//import org.libra.utilities.AuthorityUtility;
//import org.libra.entities.implementation.UserAuthorityEntity;
//import org.libra.entities.implementation.UserEntity;
//import org.libra.exceptions.*;
//import org.libra.repositories.UserAuthorityRepository;
//import org.libra.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserAuthorityService
//{
//    private final AuthorityUtility authorityUtility;
//    private final UserRepository userRepository;
//    private final UserAuthorityRepository userAuthorityRepository;
//    private final ResponseBean responseBean;
//    private final HttpHeaders httpHeaders;
//
//    public ResponseBean postUserAuthority(UserAuthorityEntity userAuthorityEntity) throws Exception
//    {
//        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByEmail(userAuthorityEntity.getEmail());
//        if (optionalUserEntity.isPresent())
//        {
//            Optional<UserAuthorityEntity> optionalUserAuthorityEntity = userAuthorityRepository
//                    .findUserAuthorityEntityByEmailAndAuthority(userAuthorityEntity.getEmail(), userAuthorityEntity.getAuthority());
//            if (!optionalUserAuthorityEntity.isPresent())
//            {
//                if (authorityUtility.validateAdminAuthority())
//                {
//                    userAuthorityRepository.save(userAuthorityEntity);
//                    responseBean.setHeaders(httpHeaders);
//                    responseBean.setStatus(HttpStatus.CREATED);
//                    responseBean.setResponse("Authority with role: '" + userAuthorityEntity.getAuthority()
//                            + "', for user with email: '" + userAuthorityEntity.getEmail() + "' successfully added");
//                }
//                else
//                {
//                    throw new AccessDeniedException("Access denied for you authority");
//                }
//            }
//            else
//            {
//                throw new DuplicatedEntryException("Authority with role: '" + userAuthorityEntity.getAuthority()
//                        + "', for user with email: '" + userAuthorityEntity.getEmail() + "' already exists");
//            }
//        }
//        else
//        {
//            throw new NotFoundEntryException("User with email: '" + userAuthorityEntity.getEmail() + "' not found");
//        }
//        return responseBean;
//    }
//    public ResponseBean deleteUserAuthority(Integer id) throws Exception
//    {
//        Optional<UserAuthorityEntity> optionalUserAuthorityEntity = userAuthorityRepository.findUserAuthorityEntityById(id);
//        if (optionalUserAuthorityEntity.isPresent())
//        {
//            if ((!authorityUtility.getCurrentAuthenticationEmail().equals(optionalUserAuthorityEntity.get().getEmail())
//                    || !optionalUserAuthorityEntity.get().getAuthority().equals("ROLE_ADMIN")) && authorityUtility.validateAdminAuthority())
//            {
//                Optional<List<UserAuthorityEntity>> optionalUserAuthorityEntityList = userAuthorityRepository
//                        .findUserAuthorityEntityByEmail(optionalUserAuthorityEntity.get().getEmail());
//                if (optionalUserAuthorityEntityList.isPresent() && optionalUserAuthorityEntityList.get().size() > 1)
//                {
//                    userAuthorityRepository.deleteUserAuthorityEntityById(id);
//                    responseBean.setHeaders(httpHeaders);
//                    responseBean.setStatus(HttpStatus.OK);
//                    responseBean.setResponse("Authority with id: '" + id + "' successfully deleted");
//                }
//                else
//                {
//                    throw new DeleteLastAuthorityException("You cannot delete user's last authority");
//                }
//            }
//            else if ((authorityUtility.getCurrentAuthenticationEmail().equals(optionalUserAuthorityEntity.get().getEmail())
//                    && optionalUserAuthorityEntity.get().getAuthority().equals("ROLE_ADMIN")) && authorityUtility.validateAdminAuthority())
//            {
//                throw new SelfDestructionException("You cannot deactivate admin rights yourself");
//            }
//            else
//            {
//                throw new AccessDeniedException("Access denied for you authority");
//            }
//        }
//        else
//        {
//            throw new NotFoundEntryException("Authority with id: '" + id + "' not found");
//        }
//        return responseBean;
//    }
//
//    @Autowired
//    public UserAuthorityService(AuthorityUtility authorityUtility, UserRepository userRepository,
//                                UserAuthorityRepository userAuthorityRepository, ResponseBean responseBean,
//                                HttpHeaders httpHeaders)
//    {
//        this.authorityUtility = authorityUtility;
//        this.userRepository = userRepository;
//        this.userAuthorityRepository = userAuthorityRepository;
//        this.responseBean = responseBean;
//        this.httpHeaders = httpHeaders;
//    }
//}