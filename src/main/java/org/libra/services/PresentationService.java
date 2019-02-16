package org.libra.services;

import org.libra.beans.utilities.AuthorityUtility;
import org.libra.beans.ResponseBean;
import org.libra.beans.utilities.EmailUtility;
import org.libra.beans.utilities.ServiceUtility;
import org.libra.entities.implementation.PresentationEntity;
import org.libra.exceptions.AccessDeniedException;
import org.libra.exceptions.DuplicatedEntryException;
import org.libra.exceptions.NotFoundEntryException;
import org.libra.repositories.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.DateTimeException;
import java.util.List;
import java.util.Optional;

@Service
public class PresentationService
{
    private final AuthorityUtility authorityUtility;
    private final EmailUtility emailUtility;
    private final ServiceUtility serviceUtility;
    private final PresentationRepository presentationRepository;
    private final ResponseBean responseBean;
    private final HttpHeaders httpHeaders;

    private Optional<List<PresentationEntity>> getPresentationsWithOrWithoutFilter(String email)
    {
        if (email != null)
        {
            return presentationRepository.findPresentationEntityByEmail(email);
        }
        else
        {
            return Optional.of(presentationRepository.findAll());
        }
    }
    private Boolean validatePresentationEntityDateAndTime(PresentationEntity storedEntity, PresentationEntity patchingEntity)
    {
        if ((patchingEntity.getStartDate() != null) && (patchingEntity.getEndDate() != null))
        {
            return patchingEntity.getStartDate().before(patchingEntity.getEndDate());
        }
        else if ((patchingEntity.getStartDate() != null) && (patchingEntity.getEndDate() == null))
        {
            return patchingEntity.getStartDate().before(storedEntity.getEndDate());
        }
        else if ((patchingEntity.getStartDate() == null) && (patchingEntity.getEndDate() != null))
        {
            return storedEntity.getStartDate().before(patchingEntity.getEndDate());
        }
        else
        {
            return true;
        }
    }

    public ResponseBean postPresentation(PresentationEntity presentationEntity) throws Exception
    {
        Optional<PresentationEntity> optionalUserEntity = presentationRepository.findPresentationEntityByTitle(presentationEntity.getTitle());
        if (!optionalUserEntity.isPresent())
        {
            if (presentationEntity.getStartDate().before(presentationEntity.getEndDate()))
            {
                presentationRepository.save(serviceUtility.setAuthenticatedEmailPropertyValue(presentationEntity));
                responseBean.setHeaders(httpHeaders);
                responseBean.setStatus(HttpStatus.CREATED);
                responseBean.setResponse("Presentation with title: '" + presentationEntity.getTitle() + "' successfully added");
            }
            else
            {
                throw new DateTimeException("Presentation start or end time is invalid");
            }
        }
        else
        {
            throw new DuplicatedEntryException("Presentation with title: '" + presentationEntity.getTitle() + "' already exists");
        }
        return responseBean;
    }
    public ResponseEntity<?> getPresentation(String email) throws Exception
    {
        ResponseEntity<?> responseEntity;
        Optional<List<PresentationEntity>> optionalPresentationEntities = getPresentationsWithOrWithoutFilter(email);
        if (optionalPresentationEntities.isPresent() && optionalPresentationEntities.get().size() > 0)
        {
            responseEntity = new ResponseEntity<>(optionalPresentationEntities.get(), httpHeaders, HttpStatus.OK);
        }
        else
        {
            throw new NotFoundEntryException("No presentation was found for the given criteria");
        }
        return responseEntity;
    }
    public ResponseBean putPresentation(PresentationEntity presentationEntity) throws Exception
    {
        Optional<PresentationEntity> optionalPresentationEntity = presentationRepository.findPresentationEntityById(presentationEntity.getId());
        if (optionalPresentationEntity.isPresent())
        {
            if (authorityUtility.getCurrentAuthenticationEmail().equals(optionalPresentationEntity.get().getEmail())
                    || authorityUtility.validateAdminAuthority())
            {
                if (validatePresentationEntityDateAndTime(optionalPresentationEntity.get(), presentationEntity))
                {
                    presentationRepository.save(serviceUtility.patchEntity(optionalPresentationEntity.get(), presentationEntity));
                    responseBean.setHeaders(httpHeaders);
                    responseBean.setStatus(HttpStatus.OK);
                    responseBean.setResponse("Presentation with id: '" + presentationEntity.getId() + "' successfully modified");
                }
                else
                {
                    throw new DateTimeException("Presentation start or end time is invalid");
                }
            }
            else
            {
                throw new AccessDeniedException("Access denied for you authority");
            }
        }
        else
        {
            throw new NotFoundEntryException("Presentation with id: '" + presentationEntity.getId() + "' not found");
        }
        return responseBean;
    }
    public ResponseBean deletePresentation(Integer id) throws Exception
    {
        Optional<PresentationEntity> optionalPresentationEntity = presentationRepository.findPresentationEntityById(id);
        if (optionalPresentationEntity.isPresent())
        {
            if (authorityUtility.getCurrentAuthenticationEmail().equals(optionalPresentationEntity.get().getEmail())
                    || authorityUtility.validateAdminAuthority())
            {
                emailUtility.sendDeletePresentationNotificationEmails(optionalPresentationEntity.get());
                presentationRepository.deletePresentationEntityById(id);
                responseBean.setHeaders(httpHeaders);
                responseBean.setStatus(HttpStatus.OK);
                responseBean.setResponse("Presentation with id: '" + id + "' successfully deleted");
            }
            else
            {
                throw new AccessDeniedException("Access denied for you authority");
            }
        }
        else
        {
            throw new NotFoundEntryException("Presentation with id: '" + id + "' not found");
        }
        return responseBean;
    }

    @Autowired
    public PresentationService(AuthorityUtility authorityUtility, EmailUtility emailUtility, ServiceUtility serviceUtility,
                               PresentationRepository presentationRepository,
                               ResponseBean responseBean, HttpHeaders httpHeaders)
    {
        this.authorityUtility = authorityUtility;
        this.emailUtility = emailUtility;
        this.serviceUtility = serviceUtility;
        this.presentationRepository = presentationRepository;
        this.responseBean = responseBean;
        this.httpHeaders = httpHeaders;
    }
}