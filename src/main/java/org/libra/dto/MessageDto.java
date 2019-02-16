package org.libra.dto;

import org.libra.assets.validators.RequestDataValidator;
import javax.validation.constraints.*;
import java.io.Serializable;

public class MessageDto implements Serializable
{
    @Positive(groups = {RequestDataValidator.PutMessage.class})
    @Null(groups = {RequestDataValidator.PostMessage.class})
    @NotNull(groups = {RequestDataValidator.PutMessage.class})
    private Integer id;

    @Positive(groups = {RequestDataValidator.PostMessage.class})
    @Null(groups = {RequestDataValidator.PutMessage.class})
    @NotNull(groups = {RequestDataValidator.PostMessage.class})
    private Integer presentationId;

    @Size(groups = {RequestDataValidator.PostMessage.class, RequestDataValidator.PutMessage.class}, max = 255)
    @NotBlank(groups = {RequestDataValidator.PostMessage.class})
    private String message;

    @Size(groups = {RequestDataValidator.PostMessage.class, RequestDataValidator.PutMessage.class}, max = 255)
    @Pattern(groups = {RequestDataValidator.PostMessage.class, RequestDataValidator.PutMessage.class}, regexp = "TYPE_FEEDBACK|TYPE_QUESTION")
    @NotBlank(groups = {RequestDataValidator.PostMessage.class})
    private String type;

    @Null(groups = {RequestDataValidator.PutMessage.class})
    @NotNull(groups = {RequestDataValidator.PostMessage.class})
    private Boolean isAnonymous;

    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    public Integer getPresentationId()
    {
        return presentationId;
    }
    public void setPresentationId(Integer presentationId)
    {
        this.presentationId = presentationId;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public Boolean getIsAnonymous()
    {
        return isAnonymous;
    }
    public void setIsAnonymous(Boolean isAnonymous)
    {
        this.isAnonymous = isAnonymous;
    }
}