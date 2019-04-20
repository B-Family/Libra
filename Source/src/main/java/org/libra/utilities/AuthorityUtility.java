package org.libra.utilities;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringComponent
public class AuthorityUtility
{
    private final AuthenticationManager authenticationManager;

    public void authenticate(String email, String password)
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    public Boolean validateAdminAuthority()
    {
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
            {
                return true;
            }
        }
        return false;
    }
    public String getCurrentAuthenticationEmail()
    {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Autowired
    public AuthorityUtility(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }
}