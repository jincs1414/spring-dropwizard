package me.jincs.java.dropwizard.app.resources;

import me.jincs.java.dropwizard.app.mybtis.mapper.TaUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("user")
@Component
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {


    @Autowired
    private TaUserMapper taUserMapper;

    @Path("getUser")
    @GET
    public Map getUser(){
        return taUserMapper.findUserByName("陈静");
    }
}
