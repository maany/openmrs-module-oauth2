package org.openmrs.module.oauth2.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.List;

/**
 * Created by OPSKMC on 5/5/15.
 */
public class ClientRegistrationServiceTest extends BaseModuleContextSensitiveTest {
    protected static final String CLIENT_INITIAL_DATA_XML = "ClientRegistrationServiceTest-initialData.xml";//"org/openmrs/api/include/LocationServiceTest-initialData.xml";

    public ClientRegistrationService getService() {
        return Context.getService(ClientRegistrationService.class);
    }

    @Before
    public void runBeforeEachTest() throws Exception {
        executeDataSet(CLIENT_INITIAL_DATA_XML);
    }

    @Test
    public void saveOrUpdate_shouldSaveNewClientsUpdateExistingClients() {
        Client client = new Client();
        client.setName("Demo Application");
        client.setClientType(Client.ClientType.WEB_APPLICATION);
        client.setLegalAcceptance(true);
        client.setRedirectionURI("www.demoapp.com");
        getService().saveOrUpdateClient(client);
        client = getService().getClient(1);
        Assert.assertNotNull(client);
    }

    @Test
    public void getClient_shouldGetClientById() {
        Client client = getService().getClient(1);
        Assert.assertNotNull(client);
    }

    /**
     * test data has been set in the xml to return
     */
    @Test
    public void getAllClientsForClientDeveloper_shouldListAllClientsRegisteredByClientDeveloper(){
        User clientDeveloper = Context.getUserService().getUser(4);
       // List<Client> clients = getService().getAllClientsForClientDeveloper(clientDeveloper);
        //Assert.assertNotNull(clients);
    }


}
