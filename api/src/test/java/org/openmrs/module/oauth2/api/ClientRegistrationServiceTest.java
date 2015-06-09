package org.openmrs.module.oauth2.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.test.BaseModuleContextSensitiveTest;

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

/*    @Test
    public void saveOrUpdateTestTransaction() {
        Client client = new Client();//getService().getClient(1);
        client.setId(100);
        client.setName("Mayank");
        getService().testTransaction_saveOrUpdateClient(client);
    }*/
}
