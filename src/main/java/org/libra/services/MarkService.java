package org.libra.services;

import org.libra.beans.utilities.AuthorityUtilityBean;
import org.libra.beans.ResponseBean;
import org.libra.beans.utilities.ServiceUtilityBean;
import org.libra.entities.implementation.MarkEntity;
import org.libra.entities.implementation.PresentationEntity;
import org.libra.exceptions.DuplicatedEntryException;
import org.libra.exceptions.NotFoundEntryException;
import org.libra.repositories.MarkRepository;
import org.libra.repositories.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MarkService
{
    private final AuthorityUtilityBean authorityUtilityBean;
    private final ServiceUtilityBean serviceUtilityBean;
    private final PresentationRepository presentationRepository;
    private final MarkRepository markRepository;
    private final ResponseBean responseBean;
    private final HttpHeaders httpHeaders;

    public ResponseBean putMark(MarkEntity markEntity) throws Exception
    {
        Optional<PresentationEntity> optionalPresentationEntity = presentationRepository
                .findPresentationEntityById(markEntity.getPresentationId());
        if (optionalPresentationEntity.isPresent())
        {
            Optional<MarkEntity> optionalMarkEntity = markRepository.findMarkEntityByPresentationIdAndEmail(markEntity.getPresentationId(),
                    authorityUtilityBean.getCurrentAuthenticationEmail());
            if (!optionalMarkEntity.isPresent())
            {
                markRepository.save(serviceUtilityBean.setAuthenticatedEmailPropertyValue(markEntity));
                responseBean.setHeaders(httpHeaders);
                responseBean.setStatus(HttpStatus.CREATED);
                responseBean.setResponse("Your mark for presentation with id: '" + markEntity.getPresentationId() + "' successfully added");
            }
            else
            {
                throw new DuplicatedEntryException("You have already rated this presentation");
            }
        }
        else
        {
            throw new NotFoundEntryException("Presentation with id: '" + markEntity.getPresentationId() + "' not found");
        }
        return responseBean;
    }

    @Autowired
    public MarkService(AuthorityUtilityBean authorityUtilityBean, ServiceUtilityBean serviceUtilityBean,
                       PresentationRepository presentationRepository, MarkRepository markRepository,
                       ResponseBean responseBean, HttpHeaders httpHeaders)
    {
        this.authorityUtilityBean = authorityUtilityBean;
        this.serviceUtilityBean = serviceUtilityBean;
        this.presentationRepository = presentationRepository;
        this.markRepository = markRepository;
        this.responseBean = responseBean;
        this.httpHeaders = httpHeaders;
    }
}