package org.libra.dto;

import org.libra.assets.validators.RequestDataValidator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthenticationDto
{
    @Email(groups = {RequestDataValidator.PostAuthentication.class})
    @Size(groups = {RequestDataValidator.PostAuthentication.class}, max = 255)
    @NotBlank(groups = {RequestDataValidator.PostAuthentication.class})
    private String email;

    @Size(groups = {RequestDataValidator.PostAuthentication.class}, min = 6, max = 16)
    @NotBlank(groups = {RequestDataValidator.PostAuthentication.class})
    private String password;

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
}