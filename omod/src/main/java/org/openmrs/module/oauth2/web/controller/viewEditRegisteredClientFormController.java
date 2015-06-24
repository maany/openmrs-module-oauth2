package org.openmrs.module.oauth2.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by OPSKMC on 6/24/15.
 */
@Controller
@RequestMapping(value = "module/oauth2/client/registered/viewEdit.form")
public class viewEditRegisteredClientFormController {
    private static final String VIEW_EDIT_FORM_VIEW = "/module/oauth2/viewEditRegisteredClient";

    @RequestMapping(method = RequestMethod.GET)
    public String showForm() {
        return VIEW_EDIT_FORM_VIEW;
    }
}
