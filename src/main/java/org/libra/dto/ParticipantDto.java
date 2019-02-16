package org.libra.dto;

import org.libra.assets.validators.RequestDataValidator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

public class ParticipantDto implements Serializable
{
    @Positive(groups = {RequestDataValidator.PostParticipant.class})
    @NotNull(groups = {RequestDataValidator.PostParticipant.class})
    private Integer presentationId;

    public Integer getPresentationId()
    {
        return presentationId;
    }
    public void setPresentationId(Integer presentationId)
    {
        this.presentationId = presentationId;
    }
}