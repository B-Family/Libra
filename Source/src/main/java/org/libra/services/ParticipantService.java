//package org.libra.services;
//
//import org.libra.utilities.AuthorityUtility;
//import org.libra.utilities.EmailUtility;
//import org.libra.utilities.ServiceUtility;
//import org.libra.entities.implementation.ParticipantEntity;
//import org.libra.entities.implementation.PresentationEntity;
//import org.libra.exceptions.AccessDeniedException;
//import org.libra.exceptions.DuplicatedEntryException;
//import org.libra.exceptions.NotFoundEntryException;
//import org.libra.repositories.ParticipantRepository;
//import org.libra.repositories.PresentationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//public class ParticipantService
//{
//    private final AuthorityUtility authorityUtility;
//    private final EmailUtility emailUtility;
//    private final ServiceUtility serviceUtility;
//    private final PresentationRepository presentationRepository;
//    private final ParticipantRepository participantRepository;
//    private final ResponseBean responseBean;
//    private final HttpHeaders httpHeaders;
//
//    public ResponseBean postParticipant(ParticipantEntity participantEntity) throws Exception
//    {
//        Optional<PresentationEntity> optionalPresentationEntity = presentationRepository
//                .findPresentationEntityById(participantEntity.getPresentationId());
//        if (optionalPresentationEntity.isPresent())
//        {
//            Optional<ParticipantEntity> optionalParticipantEntity = participantRepository
//                    .findParticipantEntityByPresentationIdAndEmail(participantEntity.getPresentationId(),
//                            authorityUtility.getCurrentAuthenticationEmail());
//            if (!optionalParticipantEntity.isPresent())
//            {
//                emailUtility.sendPutParticipantNotificationEmail(serviceUtility.setAuthenticatedEmailPropertyValue(participantEntity));
//                participantRepository.save(serviceUtility.setAuthenticatedEmailPropertyValue(participantEntity));
//                responseBean.setHeaders(httpHeaders);
//                responseBean.setStatus(HttpStatus.CREATED);
//                responseBean.setResponse("Your joined presentation with id: '" + participantEntity.getPresentationId() + "'");
//            }
//            else
//            {
//                throw new DuplicatedEntryException("You are already joined this presentation");
//            }
//        }
//        else
//        {
//            throw new NotFoundEntryException("Presentation with id: '" + participantEntity.getPresentationId() + "' not found");
//        }
//        return responseBean;
//    }
//    public ResponseBean deleteParticipant(Integer id) throws Exception
//    {
//        Optional<ParticipantEntity> optionalParticipantEntity = participantRepository.findParticipantEntityById(id);
//        if (optionalParticipantEntity.isPresent())
//        {
//            if (authorityUtility.getCurrentAuthenticationEmail().equals(optionalParticipantEntity.get().getEmail())
//                    || authorityUtility.validateAdminAuthority())
//            {
//                participantRepository.deleteParticipantEntityById(id);
//                responseBean.setHeaders(httpHeaders);
//                responseBean.setStatus(HttpStatus.OK);
//                responseBean.setResponse("Participant with id: '" + id + "' successfully deleted");
//            }
//            else
//            {
//                throw new AccessDeniedException("Access denied for you authority");
//            }
//        }
//        else
//        {
//            throw new NotFoundEntryException("Participant with id: '" + id + "' not found");
//        }
//        return responseBean;
//    }
//
//    @Autowired
//    public ParticipantService(AuthorityUtility authorityUtility, EmailUtility emailUtility, ServiceUtility serviceUtility,
//                              PresentationRepository presentationRepository, ParticipantRepository participantRepository,
//                              ResponseBean responseBean, HttpHeaders httpHeaders)
//    {
//        this.authorityUtility = authorityUtility;
//        this.emailUtility = emailUtility;
//        this.serviceUtility = serviceUtility;
//        this.presentationRepository = presentationRepository;
//        this.participantRepository = participantRepository;
//        this.responseBean = responseBean;
//        this.httpHeaders = httpHeaders;
//    }
//}