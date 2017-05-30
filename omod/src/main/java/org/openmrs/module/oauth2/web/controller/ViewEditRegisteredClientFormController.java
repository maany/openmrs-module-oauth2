package org.openmrs.module.oauth2.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.openmrs.module.oauth2.api.Oauth2Service;
import org.openmrs.module.oauth2.api.model.AuthorizedGrantType;
import org.openmrs.module.oauth2.api.model.RedirectURI;
import org.openmrs.module.oauth2.api.model.Scope;
import org.openmrs.module.oauth2.web.util.CollectionPropertyEditor;
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
        // Scopes
        Map<Scope,Boolean> scopeMap = new HashMap<Scope, Boolean>();
        List<Scope> allSupportedScopes = getOAuth2Service().getAllSupportedScopes();
        Collection<Scope> clientScopes =client.getScopeCollection();
        for(Scope scope:allSupportedScopes){
            boolean found=false;
            for(Scope clientScope:clientScopes){
                if(scope.equals(clientScope)){
                    found=true;
                    scopeMap.put(scope,true);
                    break;
                }
            }
            if(!found){
                scopeMap.put(scope,false);
            }
        }
        map.addAttribute("scope",scopeMap);

        //AuthorizedGrantTypes
        Map<AuthorizedGrantType, Boolean> grantTypeMap = new HashMap<AuthorizedGrantType, Boolean>();
        List<AuthorizedGrantType> allSupportedGrantTypes = getOAuth2Service().getAllSupportedGrantTypes();
        Collection<AuthorizedGrantType> clientGrantTypes = client.getGrantTypeCollection();
        for(AuthorizedGrantType grantType:allSupportedGrantTypes){
            boolean found=false;
            for(AuthorizedGrantType clientGrantType:clientGrantTypes){
                if(clientGrantType.equals(grantType)){
                    grantTypeMap.put(grantType,true);
                    found=true;
                    break;
                }
            }
            if(!found){
                grantTypeMap.put(grantType,false);
            }
        }
        map.addAttribute("grantType",grantTypeMap);

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
    private Oauth2Service getOAuth2Service(){
        return Context.getService(Oauth2Service.class);
    }
/*
    @InitBinder
    public void bindCollections(WebDataBinder binder) {
        CollectionPropertyEditor redirectURIPropertyEditor = new CollectionPropertyEditor(RedirectURI.class);
        CollectionPropertyEditor scopesPropertyEditor = new CollectionPropertyEditor(Scope.class);
        CollectionPropertyEditor authorizedGrantTypePropertyEditor = new CollectionPropertyEditor(AuthorizedGrantType.class);
        binder.registerCustomEditor(Collection.class, "redirectUriCollection", redirectURIPropertyEditor);
        binder.registerCustomEditor(Collection.class, "scopeCollection", scopesPropertyEditor);
        binder.registerCustomEditor(Collection.class, "grantTypeCollection", authorizedGrantTypePropertyEditor);
    }
*/


    @ModelAttribute("clientTypes")
    public Client.ClientType[] getClientTypes() {
        Client.ClientType[] clientTypes = getService().getAllClientTypes();
        return clientTypes;
    }

}
