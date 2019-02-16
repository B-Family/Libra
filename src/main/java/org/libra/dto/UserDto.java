package org.libra.dto;

import org.libra.assets.validators.RequestDataValidator;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable
{
    @Size(groups = {RequestDataValidator.PostUser.class, RequestDataValidator.PutUser.class}, max = 255)
    @Email(groups = {RequestDataValidator.PostUser.class, RequestDataValidator.PutUser.class})
    @NotBlank(groups = {RequestDataValidator.PostUser.class, RequestDataValidator.PutUser.class})
    private String email;

    @Size(groups = {RequestDataValidator.PostUser.class, RequestDataValidator.PutUser.class}, min = 6, max = 16)
    @NotNull(groups = {RequestDataValidator.PostUser.class})
    private String password;

    @PositiveOrZero(groups = {RequestDataValidator.PostUser.class, RequestDataValidator.PutUser.class})
    @Max(groups = {RequestDataValidator.PostUser.class, RequestDataValidator.PutUser.class}, value = 1)
    @NotNull(groups = {RequestDataValidator.PostUser.class})
    private Integer enabled;

    @Valid
    @Null(groups = {RequestDataValidator.PutUser.class})
    @NotEmpty(groups = {RequestDataValidator.PostUser.class})
    private List<UserAuthorityDto> authorities;

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public Integer getEnabled()
    {
        return enabled;
    }
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    public List<UserAuthorityDto> getAuthorities()
    {
        return authorities;
    }
    public void setAuthorities(List<UserAuthorityDto> authorities)
    {
        this.authorities = authorities;
    }
}