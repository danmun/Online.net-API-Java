/**
 * Copyright (c) 2016, danmun
 */
package api.server;

import api.connection.Connection;
import java.io.IOException;
import java.util.ArrayList;
// required for additional parsing:
import api.connection.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 * @author danmun
 */
public class Servers extends ArrayList<ServerRef>{
    
    public static String DEBUG_PRINTS_JSON_RESPONSE() throws IllegalStateException, IOException{
        String url = "/server";
        return Connection.DEBUG_GET_JSON_RESPONSE(url);
    }     
   
    public static Servers getServerList() throws IllegalStateException, IOException{
        String url = "/server";
        String[] refs = (String[])Connection.get(url, String[].class);
        JsonArray jarr = new JsonArray();
        for(String s : refs){
            JsonObject jobj = new JsonObject();
            jobj.addProperty(Ref.SERIALIZED_NAME, s);
            jarr.add(jobj);
        }
        return (Servers) Response.toObjectFromJSONString(jarr.toString(), Servers.class);
    }
    
    public Server getServer(int id) throws IllegalStateException, IOException{
        return Server.serverFromId(id);
    }

    public Server getServer(int id, boolean refreshPowerState) throws IllegalStateException, IOException{
        return Server.serverFromId(id,refreshPowerState);
    }
    
}
