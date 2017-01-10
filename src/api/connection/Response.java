/**
 * Copyright (c) 2016, danmun
 */
package api.connection;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 *
 * @author danmun
 */
public class Response {
    private final static Gson GSON = new Gson(); //new GsonBuilder().registerTypeAdapter(String[].class,new RefDeserializer()).create();

    /**
     * Deserialize a JSON object from an input stream
     *
     * @param stream stream to deserialize from
     * @param t      type to deserialize
     * @return the deserialized object or null if deserializing failed
     */
    public static Object toObject(InputStream stream, Type t){
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
            return GSON.fromJson(reader, t);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Create an Object of some Type from a JSON string
     *
     * @param string the JSON formatted data as a string
     * @param t      the type of Object to create
     * @return the created object
     */
    public static Object toObjectFromJSONString(String string, Type t){
        try {
            Object o = GSON.fromJson(string, t);
            return o;
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Create an Object of some Type from a Map
     *
     * //added by danmun
     * @param map
     * @param t      the type of Object to create
     * @return the created object
     */
    public static Object toObject(Map<Object,Object> map, Type t){
        return toObjectFromJSONString(GSON.toJson(map),t);
    }        
}
