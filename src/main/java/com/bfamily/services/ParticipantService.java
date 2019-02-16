package com.bfamily.services;

import com.bfamily.beans.utilities.AuthorityUtilityBean;
import com.bfamily.beans.ResponseBean;
import com.bfamily.beans.utilities.EmailUtilityBean;
import com.bfamily.beans.utilities.ServiceUtilityBean;
import com.bfamily.entities.implementation.ParticipantEntity;
import com.bfamily.entities.implementation.PresentationEntity;
import com.bfamily.exceptions.AccessDeniedException;
import com.bfamily.exceptions.DuplicatedEntryException;
import com.bfamily.exceptions.NotFoundEntryException;
import com.bfamily.repositories.ParticipantRepository;
import com.bfamily.repositories.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ParticipantService
{
    private final AuthorityUtilityBean authorityUtilityBean;
    private final EmailUtilityBean emailUtilityBean;
    private final ServiceUtilityBean serviceUtilityBean;
    private final PresentationRepository presentationRepository;
    private final ParticipantRepository participantRepository;
    private final ResponseBean responseBean;
    private final HttpHeaders httpHeaders;

    public ResponseBean putParticipant(ParticipantEntity participantEntity) throws Exception
    {
        Optional<PresentationEntity> optionalPresentationEntity = presentationRepository
                .findPresentationEntityById(participantEntity.getPresentationId());
        if (optionalPresentationEntity.isPresent())
        {
            Optional<ParticipantEntity> optionalParticipantEntity = participantRepository
                    .findParticipantEntityByPresentationIdAndEmail(participantEntity.getPresentationId(),
                            authorityUtilityBean.getCurrentAuthenticationEmail());
            if (!optionalParticipantEntity.isPresent())
            {
                emailUtilityBean.sendPutParticipantNotificationEmail(serviceUtilityBean.setAuthenticatedEmailPropertyValue(participantEntity));
                participantRepository.save(serviceUtilityBean.setAuthenticatedEmailPropertyValue(participantEntity));
                responseBean.setHeaders(httpHeaders);
                responseBean.setStatus(HttpStatus.CREATED);
                responseBean.setResponse("Your joined presentation with id: '" + participantEntity.getPresentationId() + "'");
            }
            else
            {
                throw new DuplicatedEntryException("You are already joined this presentation");
            }
        }
        else
        {
            throw new NotFoundEntryException("Presentation with id: '" + participantEntity.getPresentationId() + "' not found");
        }
        return responseBean;
    }
    public ResponseBean deleteParticipant(Integer id) throws Exception
    {
        Optional<ParticipantEntity> optionalParticipantEntity = participantRepository.findParticipantEntityById(id);
        if (optionalParticipantEntity.isPresent())
        {
            if (authorityUtilityBean.getCurrentAuthenticationEmail().equals(optionalParticipantEntity.get().getEmail())
                    || authorityUtilityBean.validateAdminAuthority())
            {
                participantRepository.deleteParticipantEntityById(id);
                responseBean.setHeaders(httpHeaders);
                responseBean.setStatus(HttpStatus.OK);
                responseBean.setResponse("Participant with id: '" + id + "' successfully deleted");
            }
            else
            {
                throw new AccessDeniedException("Access denied for you authority");
            }
        }
        else
        {
            throw new NotFoundEntryException("Participant with id: '" + id + "' not found");
        }
        return responseBean;
    }

    @Autowired
    public ParticipantService(AuthorityUtilityBean authorityUtilityBean, EmailUtilityBean emailUtilityBean, ServiceUtilityBean serviceUtilityBean,
                              PresentationRepository presentationRepository, ParticipantRepository participantRepository,
                              ResponseBean responseBean, HttpHeaders httpHeaders)
    {
        this.authorityUtilityBean = authorityUtilityBean;
        this.emailUtilityBean = emailUtilityBean;
        this.serviceUtilityBean = serviceUtilityBean;
        this.presentationRepository = presentationRepository;
        this.participantRepository = participantRepository;
        this.responseBean = responseBean;
        this.httpHeaders = httpHeaders;
    }
}