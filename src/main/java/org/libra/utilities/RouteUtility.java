package org.libra.utilities;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.MenuItemClickEvent;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
public class RouteUtility
{
    private final AuthorityUtility authorityUtility;

    public AppLayout buildAppLayout()
    {
        AppLayout appLayout = new AppLayout();
        buildMenuItems(appLayout.createMenu());
        return appLayout;
    }
    private void buildMenuItems(AppLayoutMenu appLayoutMenu)
    {
        appLayoutMenu.addMenuItem(VaadinIcon.USER.create(), "Profile", this::menuItemClickListener);
        appLayoutMenu.addMenuItem(VaadinIcon.PRESENTATION.create(), "Presentation", this::menuItemClickListener);
        if (authorityUtility.validateAdminAuthority())
        {
            appLayoutMenu.addMenuItem(VaadinIcon.ACADEMY_CAP.create(), "Admin", this::menuItemClickListener);
        }
        appLayoutMenu.addMenuItem(VaadinIcon.SIGN_OUT.create(), "Logout", this::menuItemClickListener);
    }
    private void menuItemClickListener(MenuItemClickEvent menuItemClickEvent)
    {
        String routeName = menuItemClickEvent.getSource().getTitle().toLowerCase();
        menuItemClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate(routeName));
    }

    @Autowired
    public RouteUtility(AuthorityUtility authorityUtility)
    {
        this.authorityUtility = authorityUtility;
    }
}