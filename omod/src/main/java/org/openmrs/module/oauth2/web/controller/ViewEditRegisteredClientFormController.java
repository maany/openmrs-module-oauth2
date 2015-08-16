package org.openmrs.module.oauth2.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OPSKMC on 6/24/15.
 */
@Controller
@RequestMapping(value = ViewEditRegisteredClientFormController.VIEW_EDIT_REQUEST_MAPPING)
public class ViewEditRegisteredClientFormController {
    protected final Log log = LogFactory.getLog(getClass());
    public static final String VIEW_EDIT_FORM_VIEW = "/module/oauth2/viewEditRegisteredClient";
    public static final String VIEW_EDIT_REQUEST_MAPPING = "module/oauth2/client/registered/view";

    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET)
    public String showForm(@PathVariable Integer clientId, ModelMap map) {
        Client client = getService().getClient(clientId);
        map.addAttribute("client", client);
        List<String> encodedCredentials = getService().encodeCredentials(client);
        map.addAttribute("app_identifier", encodedCredentials.get(0));
        map.addAttribute("app_password", encodedCredentials.get(1));
        return VIEW_EDIT_FORM_VIEW;
    }

    @RequestMapping(value = "/{clientId}", method = RequestMethod.POST)
    public String editForm(@PathVariable Integer clientId, @Valid @ModelAttribute("client") Client client, BindingResult errors, ModelMap map) {
        if (errors.hasErrors()) {
            //TODO return error view
            log.info("Binding errors found");
            return VIEW_EDIT_FORM_VIEW;
        }
        updateNonFormDetails(client, clientId);
        client = getService().merge(client);
        getService().updateClient(client);
        log.info("Making edits for client with id" + client.getId());
        List<String> encodedCredentials = getService().encodeCredentials(client);
        map.addAttribute("app_identifier", encodedCredentials.get(0));
        map.addAttribute("app_password", encodedCredentials.get(1));
        return VIEW_EDIT_FORM_VIEW;
    }

    /**
     * @param clientId
     * @return invoke controller to render index.jsp
     */
    @RequestMapping(value = "/{clientId}", method = RequestMethod.DELETE)
    public ModelAndView deleteClient(HttpServletRequest request, @ModelAttribute Client client, @PathVariable Integer clientId) {
        client = getService().unregisterClient(client);
        //TODO send message that client is unregistered
        String redirectURI = request.getContextPath() + "/" + RegisteredClientIndexController.INDEX_CONTROLLER;
        log.info("Oauth Client Unregistered : " + client.getName());
        return new ModelAndView(new RedirectView(redirectURI));
    }

    /**
     * This method fills up the details that cannot be changed by viewEditRegisteredClientForm once registration is done
     * These fields include clientIdentifier, clientDeveloper, clientSecret
     *
     * @param client
     * @param id
     */
    private void updateNonFormDetails(Client client, Integer id) {
        Client oldClient = getService().getClient(id);
        client.setCreator(oldClient.getCreator());
        client.setClientIdentifier(oldClient.getClientIdentifier());
        client.setClientSecret(oldClient.getClientSecret());
    }

    private ClientRegistrationService getService() {
        return Context.getService(ClientRegistrationService.class);
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
}
