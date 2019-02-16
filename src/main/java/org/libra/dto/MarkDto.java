package org.libra.dto;

import org.libra.assets.validators.RequestDataValidator;
import javax.validation.constraints.*;
import java.io.Serializable;

public class MarkDto implements Serializable
{
    @Positive(groups = {RequestDataValidator.PostMark.class})
    @NotNull(groups = {RequestDataValidator.PostMark.class})
    private Integer presentationId;

    @Positive(groups = {RequestDataValidator.PostMark.class})
    @Max(groups = {RequestDataValidator.PostMark.class}, value = 5)
    @NotNull(groups = {RequestDataValidator.PostMark.class})
    private Integer mark;

    public Integer getPresentationId()
    {
        return presentationId;
    }
    public void setPresentationId(Integer presentationId)
    {
        this.presentationId = presentationId;
    }
    public Integer getMark()
    {
        return mark;
    }
    public void setMark(Integer mark)
    {
        this.mark = mark;
    }
}