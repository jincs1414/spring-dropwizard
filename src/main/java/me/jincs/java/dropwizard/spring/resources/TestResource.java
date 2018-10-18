package me.jincs.java.dropwizard.spring.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by jinchengsong on 2018/10/18.
 */
@Component
@Path("test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @Autowired
    Map defaultUserMap;

    @GET
    @Path("sayHello")
    public Map sayHello(@QueryParam("name") Optional<String> name) {
        Map m = new HashMap();
        m.put("name",name.orElse((String) defaultUserMap.get("name")));
        return m;
    }

}
