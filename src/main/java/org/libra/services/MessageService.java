package org.libra.services;

import org.libra.beans.utilities.AuthorityUtility;
import org.libra.beans.ResponseBean;
import org.libra.beans.utilities.ServiceUtility;
import org.libra.entities.implementation.MessageEntity;
import org.libra.entities.implementation.PresentationEntity;
import org.libra.exceptions.AccessDeniedException;
import org.libra.exceptions.NotFoundEntryException;
import org.libra.repositories.MessageRepository;
import org.libra.repositories.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MessageService
{
    private final AuthorityUtility authorityUtility;
    private final ServiceUtility serviceUtility;
    private final PresentationRepository presentationRepository;
    private final MessageRepository messageRepository;
    private final ResponseBean responseBean;
    private final HttpHeaders httpHeaders;

    private MessageEntity setAuthenticatedEmailPropertyValue(MessageEntity targetEntity)
    {
        if (targetEntity.getAnonymous())
        {
            targetEntity.setEmail("ANONYMOUS");
        }
        else
        {
            targetEntity.setEmail(authorityUtility.getCurrentAuthenticationEmail());
        }
        return targetEntity;
    }

    public ResponseBean postMessage(MessageEntity messageEntity) throws Exception
    {

        Optional<PresentationEntity> optionalPresentationEntity = presentationRepository
                .findPresentationEntityById(messageEntity.getPresentationId());
        if (optionalPresentationEntity.isPresent())
        {
            messageRepository.save(setAuthenticatedEmailPropertyValue(messageEntity));
            responseBean.setHeaders(httpHeaders);
            responseBean.setStatus(HttpStatus.CREATED);
            responseBean.setResponse("Message for presentation with id: '" + messageEntity.getPresentationId() + "' successfully added");
        }
        else
        {
            throw new NotFoundEntryException("Presentation with id: '" + messageEntity.getPresentationId() + "' not found");
        }
        return responseBean;
    }
    public ResponseBean putMessage(MessageEntity messageEntity) throws Exception
    {
        Optional<MessageEntity> optionalMessageEntity = messageRepository.findMessageEntityById(messageEntity.getId());
        if (optionalMessageEntity.isPresent())
        {
            if (authorityUtility.getCurrentAuthenticationEmail().equals(optionalMessageEntity.get().getEmail()))
            {
                messageRepository.save(serviceUtility.patchEntity(optionalMessageEntity.get(), messageEntity));
                responseBean.setHeaders(httpHeaders);
                responseBean.setResponse("Message with id: '" + messageEntity.getId() + "' successfully modified");
            }
            else
            {
                throw new AccessDeniedException("Access denied for you authority");
            }
        }
        else
        {
            throw new NotFoundEntryException("Message with id: '" + messageEntity.getId() + "' not found");
        }
        return responseBean;
    }
    public ResponseBean deleteMessage(Integer id) throws Exception
    {
        Optional<MessageEntity> optionalMessageEntity = messageRepository.findMessageEntityById(id);
        if (optionalMessageEntity.isPresent())
        {
            if (authorityUtility.getCurrentAuthenticationEmail().equals(optionalMessageEntity.get().getEmail())
                    || authorityUtility.validateAdminAuthority())
            {
                messageRepository.deleteMessageEntityById(id);
                responseBean.setHeaders(httpHeaders);
                responseBean.setStatus(HttpStatus.OK);
                responseBean.setResponse("Message with id: '" + id + "' successfully deleted");
            }
            else
            {
                throw new AccessDeniedException("Access denied for you authority");
            }
        }
        else
        {
            throw new NotFoundEntryException("Message with id: '" + id + "' not found");
        }
        return responseBean;
    }

    @Autowired
    public MessageService(AuthorityUtility authorityUtility, ServiceUtility serviceUtility,
                          PresentationRepository presentationRepository, MessageRepository messageRepository,
                          ResponseBean responseBean, HttpHeaders httpHeaders)
    {
        this.authorityUtility = authorityUtility;
        this.serviceUtility = serviceUtility;
        this.presentationRepository = presentationRepository;
        this.messageRepository = messageRepository;
        this.responseBean = responseBean;
        this.httpHeaders = httpHeaders;
    }
}