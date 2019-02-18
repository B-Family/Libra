//package org.libra.services;
//
//import org.libra.utilities.AuthorityUtility;
//import org.libra.utilities.ServiceUtility;
//import org.libra.entities.implementation.MarkEntity;
//import org.libra.entities.implementation.PresentationEntity;
//import org.libra.exceptions.DuplicatedEntryException;
//import org.libra.exceptions.NotFoundEntryException;
//import org.libra.repositories.MarkRepository;
//import org.libra.repositories.PresentationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//public class MarkService
//{
//    private final AuthorityUtility authorityUtility;
//    private final ServiceUtility serviceUtility;
//    private final PresentationRepository presentationRepository;
//    private final MarkRepository markRepository;
//    private final ResponseBean responseBean;
//    private final HttpHeaders httpHeaders;
//
//    public ResponseBean postMark(MarkEntity markEntity) throws Exception
//    {
//        Optional<PresentationEntity> optionalPresentationEntity = presentationRepository
//                .findPresentationEntityById(markEntity.getPresentationId());
//        if (optionalPresentationEntity.isPresent())
//        {
//            Optional<MarkEntity> optionalMarkEntity = markRepository.findMarkEntityByPresentationIdAndEmail(markEntity.getPresentationId(),
//                    authorityUtility.getCurrentAuthenticationEmail());
//            if (!optionalMarkEntity.isPresent())
//            {
//                markRepository.save(serviceUtility.setAuthenticatedEmailPropertyValue(markEntity));
//                responseBean.setHeaders(httpHeaders);
//                responseBean.setStatus(HttpStatus.CREATED);
//                responseBean.setResponse("Your mark for presentation with id: '" + markEntity.getPresentationId() + "' successfully added");
//            }
//            else
//            {
//                throw new DuplicatedEntryException("You have already rated this presentation");
//            }
//        }
//        else
//        {
//            throw new NotFoundEntryException("Presentation with id: '" + markEntity.getPresentationId() + "' not found");
//        }
//        return responseBean;
//    }
//
//    @Autowired
//    public MarkService(AuthorityUtility authorityUtility, ServiceUtility serviceUtility,
//                       PresentationRepository presentationRepository, MarkRepository markRepository,
//                       ResponseBean responseBean, HttpHeaders httpHeaders)
//    {
//        this.authorityUtility = authorityUtility;
//        this.serviceUtility = serviceUtility;
//        this.presentationRepository = presentationRepository;
//        this.markRepository = markRepository;
//        this.responseBean = responseBean;
//        this.httpHeaders = httpHeaders;
//    }
//}