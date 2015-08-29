package org.openmrs.module.oauth2.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.annotations.Param;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.openmrs.module.oauth2.api.model.RedirectURI;
import org.openmrs.module.oauth2.web.util.ClientDetailsPropertyEditor;
import org.openmrs.module.oauth2.web.util.RedirectUriPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by OPSKMC on 6/23/15.
 */
@Controller
@RequestMapping(value = "module/oauth2/client/registrationLink.form")
public class ClientRegistrationFormController {
    protected final Log log = LogFactory.getLog(getClass());
    public static final String REGISTRATION_FORM_VIEW = "/module/oauth2/registrationForm";

    @RequestMapping(method = RequestMethod.GET)
    public String showForm() {
        return REGISTRATION_FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(HttpServletRequest request, @Valid @ModelAttribute("client") Client client,
                                 BindingResult errors, ModelMap map) {

        if (errors.hasErrors()) {
            return new ModelAndView(REGISTRATION_FORM_VIEW);
        }
        client = getService().registerNewClient(client);
        getService().generateAndPersistClientCredentials(client);
        String redirectURL = request.getContextPath() + "/" + ViewEditRegisteredClientFormController.VIEW_EDIT_REQUEST_MAPPING + "/" + client.getId() + ".form";
        return new ModelAndView(new RedirectView(redirectURL));
    }

    @ModelAttribute("client")
    public Client getNewClient() {
        return new Client(null, null, null, null, null, null, "testURI.com");
    }

    @ModelAttribute("clientTypes")
    public Map<String, String> getClientTypes() {
        Client.ClientType[] clientTypes = getService().getAllClientTypes();
        Map<String, String> clientTypeMap = new HashMap<String, String>();
        for (Client.ClientType clientType : clientTypes) {
            clientTypeMap.put(clientType.name(), clientType.name());
        }
        return clientTypeMap;
    }

    @InitBinder
    public void bindCollections(WebDataBinder binder) {
//        RedirectUriPropertyEditor redirectUriPropertyEditor = new RedirectUriPropertyEditor();
        ClientDetailsPropertyEditor<RedirectURI> clientDetailsPropertyEditor = new ClientDetailsPropertyEditor<RedirectURI>(RedirectURI.class);
        binder.registerCustomEditor(Collection.class, "registeredRedirectUris", clientDetailsPropertyEditor);
    }

    public ClientRegistrationService getService() {
        return Context.getService(ClientRegistrationService.class);
    }
}
