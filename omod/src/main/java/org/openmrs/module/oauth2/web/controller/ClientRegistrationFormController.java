package org.openmrs.module.oauth2.web.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.openmrs.module.oauth2.api.Oauth2Service;
import org.openmrs.module.oauth2.api.model.AuthorizedGrantType;
import org.openmrs.module.oauth2.api.model.RedirectURI;
import org.openmrs.module.oauth2.api.model.Scope;
import org.openmrs.module.oauth2.api.util.ClientSpringOAuthUtils;
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
    public ModelAndView onSubmit(HttpServletRequest request, ModelMap map) {

        Client client = (Client) map.get("client");
        client.setName(request.getParameter("name"));
        client.setDescription(request.getParameter("description"));
        client.setWebsite(request.getParameter("website"));

        String clientTypeString = request.getParameter("clientType");
        client.setClientType(Client.ClientType.valueOf(clientTypeString));

        String redirectionURIString = request.getParameter("registeredRedirectURIs").trim();
        Collection<RedirectURI> redirectURICollection= ClientSpringOAuthUtils.commaDelimitedStringToCollection(redirectionURIString,client,RedirectURI.class);
        client.setRedirectUriCollection(redirectURICollection);

        String[] scopesStringArray = request.getParameterValues("scope");
        String scopesCSV = StringUtils.join(scopesStringArray, ',');
        Collection<Scope> scopeCollection = ClientSpringOAuthUtils.commaDelimitedStringToCollection(scopesCSV, client, Scope.class);
        client.setScopeCollection(scopeCollection);

        String[] grantTypesArray = request.getParameterValues("grantType");
        String grantTypeCSV = StringUtils.join(grantTypesArray,",");
        Collection<AuthorizedGrantType> grantTypeCollection = ClientSpringOAuthUtils.commaDelimitedStringToCollection(grantTypeCSV,client,AuthorizedGrantType.class);
        client.setGrantTypeCollection(grantTypeCollection);

        client.setCreator(Context.getAuthenticatedUser());
        client = getService().saveOrUpdateClient(client);
        getService().generateAndPersistClientCredentials(client);
        String redirectURL = request.getContextPath() + "/" + ViewEditRegisteredClientFormController.VIEW_EDIT_REQUEST_MAPPING + "/" + client.getId() + ".form";
        return new ModelAndView(new RedirectView(redirectURL));
    }

    @ModelAttribute("client")
    public Client getNewClient() {
        Client client = new Client(null, null, null, null, null, null, null);
        return client;
    }

    @ModelAttribute("clientTypes")
    public Client.ClientType[] getClientTypes() {
        Client.ClientType[] clientTypes = getService().getAllClientTypes();
        return clientTypes;
    }

    @ModelAttribute("scopes")
    public List<Scope> getAllSupportedScopes(){
        return getOAuth2Service().getAllSupportedScopes();
    }

    @ModelAttribute("grantTypes")
    public List<AuthorizedGrantType> getAllSupportedGrantTypes(){
        return getOAuth2Service().getAllSupportedGrantTypes();
    }

    private Oauth2Service getOAuth2Service() {
        return Context.getService(Oauth2Service.class);
    }

    /*   @InitBinder
       public void bindCollections(WebDataBinder binder) {
           CollectionPropertyEditor redirectURIPropertyEditor = new CollectionPropertyEditor(RedirectURI.class);
           CollectionPropertyEditor scopesPropertyEditor = new CollectionPropertyEditor(Scope.class);
           CollectionPropertyEditor authorizedGrantTypePropertyEditor = new CollectionPropertyEditor(AuthorizedGrantType.class);
           binder.registerCustomEditor(Collection.class, "redirectUriCollection", redirectURIPropertyEditor);
           binder.registerCustomEditor(Collection.class, "scopeCollection", scopesPropertyEditor);
           binder.registerCustomEditor(Collection.class, "grantTypeCollection", authorizedGrantTypePropertyEditor);
       }
   */
    public ClientRegistrationService getService() {
        return Context.getService(ClientRegistrationService.class);
    }
}
