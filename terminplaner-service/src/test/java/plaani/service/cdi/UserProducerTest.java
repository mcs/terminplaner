package plaani.service.cdi;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import plaani.service.entity.User;

import javax.inject.Inject;
import javax.inject.Provider;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
public class UserProducerTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap
                .create(JavaArchive.class)
                .addClasses(UserProducer.class, User.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    @Inject
    Provider<User> userProvider;

    @Test
    public void addGetRemove() {
        assertThat(userProvider.get(), is(nullValue()));

        User user = new User();
        user.setId("42");
        UserProducer.set(user);

        assertThat(userProvider.get(), is(user));

        UserProducer.remove();

        assertThat(userProvider.get(), is(nullValue()));
    }

    @Test
    public void testMultithreadedUse() throws InterruptedException {
        final int AMOUNT = 100;
        // create initial 100 users
        User[] users = new User[AMOUNT];
        for (int i = 0; i < AMOUNT; i++) {
            users[i] = new User();
            users[i].setId("u" + i);
        }

        // thread simulator that uses our UserProducer
        class ThreadTest implements Runnable {

            User user;

            ThreadTest(User user) {
                this.user = user;
            }

            @Override
            public synchronized void run() {
                UserProducer.set(user);
                try {
                    Thread.sleep(100);
                    assertThat(UserProducer.get(), is(user));
                    UserProducer.remove();
                    Thread.sleep(100);
                    assertThat(UserProducer.get(), is(nullValue()));
                    user.setId(user.getId() + " success!");
                } catch (InterruptedException e) {
                    fail("Unexpected InterruptedException");
                }
            }
        }

        // create some threads that will use our UserProducer concurrently
        Thread[] threads = new Thread[AMOUNT];
        // create threads
        for (int i = 0; i < AMOUNT; i++) {
            threads[i] = new Thread(new ThreadTest(users[i]));
        }
        // start threads (almost) at the same time
        // maybe should use java.util.concurrent.CyclicBarrier ...
        for (int i = 0; i < AMOUNT; i++) {
            threads[i].start();
        }
        Thread.sleep(1000);
        // check thread-safe behaviour
        for (int i = 0; i < AMOUNT; i++) {
            assertThat(users[i].getId(), is("u" + i + " success!"));
        }
    }

}
