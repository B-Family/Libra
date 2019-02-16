package org.libra.dto;

import org.libra.assets.validators.RequestDataValidator;
import javax.validation.constraints.*;
import java.io.Serializable;

public class UserAuthorityDto implements Serializable
{
    @Size(groups = {RequestDataValidator.PostUserAuthority.class}, max = 255)
    @Email(groups = {RequestDataValidator.PostUserAuthority.class})
    @Null(groups = {RequestDataValidator.PostUser.class})
    @NotBlank(groups = {RequestDataValidator.PostUserAuthority.class})
    private String email;

    @Size(groups = {RequestDataValidator.PostUser.class, RequestDataValidator.PostUserAuthority.class}, max = 255)
    @Pattern(groups = {RequestDataValidator.PostUser.class, RequestDataValidator.PostUserAuthority.class}, regexp = "ROLE_USER|ROLE_ADMIN")
    @NotBlank(groups = {RequestDataValidator.PostUser.class, RequestDataValidator.PostUserAuthority.class})
    private String authority;

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getAuthority()
    {
        return authority;
    }
    public void setAuthority(String authority)
    {
        this.authority = authority;
    }
}