package org.libra.dto;

import org.libra.assets.validators.RequestDataValidator;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

public class PresentationDto implements Serializable
{
    @Positive(groups = {RequestDataValidator.PutPresentation.class})
    @Null(groups = {RequestDataValidator.PostPresentation.class})
    @NotNull(groups = {RequestDataValidator.PutPresentation.class})
    private Integer id;

    @Size(groups = {RequestDataValidator.PostPresentation.class, RequestDataValidator.PutPresentation.class}, max = 255)
    @NotBlank(groups = {RequestDataValidator.PostPresentation.class})
    private String title;

    @Size(groups = {RequestDataValidator.PostPresentation.class, RequestDataValidator.PutPresentation.class}, max = 255)
    @NotBlank(groups = {RequestDataValidator.PostPresentation.class})
    private String description;

    @NotNull(groups = {RequestDataValidator.PostPresentation.class})
    private Date startDate;

    @NotNull(groups = {RequestDataValidator.PostPresentation.class})
    private Date endDate;

    @Size(groups = {RequestDataValidator.PostPresentation.class, RequestDataValidator.PutPresentation.class}, max = 255)
    @NotBlank(groups = {RequestDataValidator.PostPresentation.class})
    private String place;

    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public Date getStartDate()
    {
        return startDate;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    public Date getEndDate()
    {
        return endDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
    public String getPlace()
    {
        return place;
    }
    public void setPlace(String place)
    {
        this.place = place;
    }
}