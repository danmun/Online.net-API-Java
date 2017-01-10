/**
 * Copyright (c) 2016, danmun
 */
package api.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author danmun
 */
public class Connection {
    private static final String API = "https://api.online.net/api/v1";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0";
    private static String API_KEY;
    private final static String DELETE_MODE = "DELETE";
    private final static String POST_MODE = "POST";
    private final static String PUT_MODE = "PUT";
    private final static String GET_MODE = "GET";
    
    public static void SET_API_KEY(String key){
        API_KEY = key;
    }
    
    /**
     * FOR DEBUG ONLY
     * Print and return the JSON response received by scraping the specified URL.
     * @param url the url in the API to get
     * @return the string JSON response
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static String DEBUG_GET_JSON_RESPONSE(String url) throws IllegalStateException, IOException{
        String scrape = Connection.get(url);
        System.out.println(scrape);
        return scrape;
    }     

    /**
     * FOR DEBUG ONLY
     * Print and return the JSON response received by scraping the specified URL.
     * @param url the url in the API to get
     * @param params optional params
     * @param MODE
     * @return the string JSON response
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static String DEBUG_GET_JSON_RESPONSE(String url, Map<String,String> params, String MODE) throws IllegalStateException, IOException{
        String scrape = (String) Connection.post(url,params,String.class);
        System.out.println(scrape);
        return scrape;
    }    
    
    private static HttpsURLConnection getConnection(String page){
        try {
            URL url = new URL(page);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY); // set authorization property
            return connection;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Perform a GET request to get some API object and use the passed Gson to deserialize it
     *
     * @param url  the url extension to get
     * @param t    type of object to create
     * @return the parsed object or null if parsing failed
     * @throws java.io.IOException
     * @throws IllegalStateException if the site was not set prior to this call
     */
    public static Object get(String url, Type t) throws IllegalStateException, IOException{
        return get(url,null,t);
    }      
    
    /**
     * Perform a GET request on the path site + url
     * (for debug only)
     * @param url the url extension to get
     * @return the response data as a string
     * @throws java.lang.IllegalStateException if the site was not set prior to calling this method
     */
    private static String get(String url) throws IllegalStateException, IOException {
	if (API_KEY == null){
            throw new IllegalStateException("Must set API_KEY before use. To set API key: Connection.SET_API_KEY(key)");
        }
        String response = null;
        HttpURLConnection connection = null;
        try {
            connection = getConnection(API + url);
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            response = IOUtils.toString(in, "UTF-8");
            in.close();
        }
        catch (IOException e){
            e.printStackTrace();
            throw new IOException("Could not load: " + url);
        }
        finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return response;
    }    
    
    /**
     * Perform a GET request with parameters.
     *
     * @param url  the url extension to get
     * @param params the GET parameters to include in the url
     * @param t    type of object to create
     * @return the parsed object or null if parsing failed
     * @throws IllegalStateException if the site was not set prior to this call
     */
    public static Object get(String url, Map<String,String> params, Type t) throws IllegalStateException, IOException{
	if (API_KEY == null){
            throw new IllegalStateException("Must set API_KEY before use. To set API key: Connection.SET_API_KEY(key)");
        }
        Object o = null;
        HttpURLConnection connection = null;
        try {
            String urlParams = (params != null) ? buildParams(params,Connection.GET_MODE) : "";
            connection = getConnection(API + url + urlParams);
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            o = Response.toObject(in, t);
            in.close();
        }
        catch (IOException e){
            e.printStackTrace();
            throw new IOException("Could not load: " + url);
        }
        finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return o;
    }       
    
    
    /**
     * POST to the given URL including the given params and return the 
     * specified type (constructed from the response).
     * @param url url to post to
     * @param params params to include
     * @param t type of object to return
     * @return the object of type t constructed from the response from the POST request
     * @throws IOException 
     */
    public static Object post(String url, Map<String,String> params, Type t) throws IOException{
        return postMethod(url,params,t,POST_MODE); // for some reason it was postMethod(url,null,POST_MODE), no idea why a null was passed
    }     
    
    /**
     * Method for sending a POST request to the specified URL without parameters.
     * @param url the URL to send POST request to
     * @param t the type of Object to return to return
     * @return the server's response to the POST request
     * @throws IOException 
     */
    public static Object post(String url, Type t) throws IOException{
        return postMethod(url,null,t,POST_MODE);
    }    
    
    /**
     * Method for sending a PUT request to the specified URL.
     * @param url the URL to send PUT request to
     * @param params the POST parameters to include
     * @param t the type of Object to return to return
     * @return the server's response to the PUT request
     * @throws IOException 
     */
    public static Object put(String url, Map<String,String> params, Type t) throws IOException{
        return postMethod(url,params,t,PUT_MODE);
    }    
    
    /**
     * Method for sending a DELETE request to the specified URL.
     * @param url the URL to send DELETE request to
     * @param params the DELETE parameters to include
     * @param t the type of Object to return to return
     * @return the server's response to the POST request
     * @throws IOException 
     */    
    public static Object delete(String url, Map<String,String> params,Type t) throws IOException{
        return postMethod(url,params,t,DELETE_MODE);
    }

    /**
     * Method for sending a DELETE request to the specified URL.
     * @param url the URL to send DELETE request to
     * @param t the type of Object to return to return
     * @return the server's response to the POST request
     * @throws IOException 
     */    
    public static Object delete(String url, Type t) throws IOException{
        return postMethod(url,null,t,DELETE_MODE);
    }
    
    public static void delete(String url) throws IOException{
        postMethod(url,null,null,DELETE_MODE);
    }
    
    /**
     * Perform a POST to some site url with the desired list of parameters
     * 
     *POST is on the site, so cookies have to be held for it. Will exception if cookies empty.
     * @param url    the url to submit to, the site will be prepended to it to form
     *               the final url, eg: http://site.com/url
     * @return returns the type specified or null if not specified (t == null)
     * @throws java.lang.IllegalStateException if the site was not set prior to calling this method
     */
    private static Object postMethod(String url, Map<String, String> params, Type t, String MODE) throws IOException{
	if (API_KEY == null){
            throw new IllegalStateException("Must set API_KEY before use. To set API key: Connection.SET_API_KEY(key)");
        }
        HttpURLConnection connection = null;
        Object response = null;
        try {
            String urlParams = (params != null) ? buildParams(params,MODE) : "";
            connection = getConnection(API + url);
            connection.setDoOutput(true);
            connection.setRequestMethod(MODE);

            BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
            out.write(urlParams.getBytes("UTF-8"));
            out.close();
            connection.getResponseCode();
            if(t != null){
                BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                response = Response.toObject(in, t);//IOUtils.toString(in, "UTF-8");
                in.close();            
            }
        }
        catch (IOException e){
            e.printStackTrace();
            throw new IOException("Could not post method to: " + url);
        }
        finally {
            if (connection != null){
                connection.disconnect();
                
            }
        }
        return response;
    }    
    
//    private static String buildParams(Map<String,String> params){
//        return buildParams(params,Connection.POST_MODE);
//    }
    
   /**
     * Build the POST method parameters string for some list of parameters
     *
     * @param params params to encode for a POST method
     * @return the parameters as a string
     */
    private static String buildParams(Map<String,String> params, String MODE){
        StringBuilder result = new StringBuilder();
        if(MODE.equals(Connection.GET_MODE)) result.append("?");
        try {   
            Set<String> keys = params.keySet();
            int i = 0;
            for(String key : keys){
                if(i == 0){
                    result.append(URLEncoder.encode(key, "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(params.get(key), "UTF-8"));
                }else{
                    result.append("&");
                    result.append(URLEncoder.encode(key, "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(params.get(key), "UTF-8"));
                }
                i++;
            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
