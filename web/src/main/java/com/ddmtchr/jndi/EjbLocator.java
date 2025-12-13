package com.ddmtchr.jndi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Component
public class EjbLocator {

    @Value("${ejb.jndi.url}")
    private final String providerUrl;

    public EjbLocator() {
        this.providerUrl = "http-remoting://localhost:8080";
    }

    public Object lookup(String jndiName) {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            props.put(Context.PROVIDER_URL, providerUrl);
            props.put("jboss.naming.client.ejb.context", true); // важно для ejb: lookups

            Context ctx = new InitialContext(props);
            return ctx.lookup(jndiName);

        } catch (NamingException e) {
            throw new IllegalStateException("Failed to lookup EJB: " + jndiName, e);
        }
    }

    public <T> T lookup(String jndiName, Class<T> clazz) {
        return clazz.cast(lookup(jndiName));
    }
}
