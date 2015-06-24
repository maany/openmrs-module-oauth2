package org.openmrs.module.oauth2.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by OPSKMC on 6/23/15.
 */
@Controller
@RequestMapping(value = "module/oauth2/registrationLink.form")
public class ClientRegistrationFormController {
    protected final Log log = LogFactory.getLog(getClass());
    private static final String SUCCESS_FORM_VIEW = "/module/oauth2/registrationForm";

    @RequestMapping(method = RequestMethod.GET)
    public String showForm() {
        return SUCCESS_FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject,
                           BindingResult errors) {

        if (errors.hasErrors()) {
            // return error view
        }

        return SUCCESS_FORM_VIEW;
    }
}
