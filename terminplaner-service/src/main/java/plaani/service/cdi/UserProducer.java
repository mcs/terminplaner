package plaani.service.cdi;

import plaani.service.entity.User;

import javax.enterprise.inject.Produces;

public class UserProducer {

    private static final ThreadLocal<User> holder = new ThreadLocal<>();

    public static void set(User user) {
        holder.set(user);
    }

    public static User get() {
        return holder.get();
    }

    public static void remove() {
        holder.remove();
    }

    @Produces
    public User produceUser() {
        return get();
    }
}
