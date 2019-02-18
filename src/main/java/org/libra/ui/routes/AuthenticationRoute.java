package org.libra.ui.routes;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.libra.beans.utilities.AuthorityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

@UIScope
@SpringComponent
@Route(value = "authentication")
public class AuthenticationRoute extends HorizontalLayout
{
    private final AuthorityUtility authorityUtility;

    private void init()
    {
        add(buildLOginOverlay());
    }
    private LoginOverlay buildLOginOverlay()
    {
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setTitle(buildTitle());
        loginOverlay.setDescription("Application for managing presentations");
        loginOverlay.setI18n(buildI18n());
        loginOverlay.onEnabledStateChanged(false);
        loginOverlay.addLoginListener(this::loginListener);
        loginOverlay.setOpened(true);
        return loginOverlay;
    }
    private Component buildTitle()
    {
        H1 h1 = new H1();
        h1.getStyle().set("color", "var(--lumo-base-color)");
        h1.add(new Text("Libra"));
        return h1;
    }
    private LoginI18n buildI18n()
    {
        LoginI18n loginI18n = LoginI18n.createDefault();
        loginI18n.setErrorMessage(buildErrorMessage());
        loginI18n.getForm().setUsername("Email");
        loginI18n.getForm().setForgotPassword("Register new account");
        return loginI18n;
    }
    private LoginI18n.ErrorMessage buildErrorMessage()
    {
        LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setTitle("Incorrect email or password");
        return errorMessage;
    }
    private void loginListener(AbstractLogin.LoginEvent loginEvent)
    {
        try
        {
            authorityUtility.authenticate(loginEvent.getUsername(), loginEvent.getPassword());
            loginEvent.getSource().getUI().ifPresent(ui -> ui.navigate("presentation"));
        }
        catch (BadCredentialsException e)
        {
            loginEvent.getSource().setError(true);
        }
    }

    @Autowired
    public AuthenticationRoute(AuthorityUtility authorityUtility)
    {
        this.authorityUtility = authorityUtility;
        init();
    }
}