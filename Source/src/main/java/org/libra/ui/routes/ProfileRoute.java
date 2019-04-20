package org.libra.ui.routes;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.libra.utilities.RouteUtility;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
@Route(value = "profile")
public class ProfileRoute extends HorizontalLayout
{
    private final RouteUtility routeUtility;

    private void init()
    {
        buildRoute();
        add(routeUtility.buildAppLayout());
    }
    private void buildRoute()
    {
        setSpacing(false);
    }

    @Autowired
    public ProfileRoute(RouteUtility routeUtility)
    {
        this.routeUtility = routeUtility;
        init();
    }
}