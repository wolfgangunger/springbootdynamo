package com.unw;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.unw.api.DynamoResource;

/**
 * @author UNGERW
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(DynamoResource.class);
    }

}
