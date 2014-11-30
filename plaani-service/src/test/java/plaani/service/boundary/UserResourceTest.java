package plaani.service.boundary;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import plaani.service.cdi.UserProducer;
import plaani.service.control.UserService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@Ignore
public class UserResourceTest {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap
                .create(WebArchive.class, "plaani-test.war")
                .addPackages(true, "plaani.service")
                .addAsWebInfResource("META-INF/test-persistence.xml", "classes/META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

    @Test
    @RunAsClient
    public void testGetInfo(@ArquillianResource URL base) throws Exception {
        System.out.println("Base = " + base.toString());
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(base.toURI() + "/user/");

        Response response = webTarget.request().get();

        assertThat(response.getLink("self"), is(notNullValue()));
    }

}
