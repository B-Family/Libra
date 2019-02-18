package org.libra.ui.routes;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
@Route(value = "presentation")
public class PresentationRoute extends HorizontalLayout
{
    private void init()
    {
        add(buildAppLayout());
    }



    private AppLayout buildAppLayout()
    {
        AppLayout appLayout = new AppLayout();
        appLayout.setBranding(new H3("Libra"));


        return appLayout;
    }



    public PresentationRoute()
    {
        init();
    }
}