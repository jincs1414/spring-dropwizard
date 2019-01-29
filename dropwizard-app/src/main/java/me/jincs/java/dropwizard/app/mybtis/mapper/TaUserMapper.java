package me.jincs.java.dropwizard.app.mybtis.mapper;

import java.util.Map;

public interface TaUserMapper {
    Map findUserByName(String name);
}
