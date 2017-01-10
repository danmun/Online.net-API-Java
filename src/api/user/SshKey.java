/**
 * Copyright (c) 2016, danmun
 */
package api.user;

import api.connection.Connection;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danmun
 */
public class SshKey {
    private String uuid_ref;
    private String description;
    private String fingerprint;

    /**
     * Construct an SshKey via its ID.
     * @param key_id the id of the SshKey to construct
     * @return the SshKey
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static SshKey sshKeyFromId(String key_id) throws IllegalStateException, IOException{
        String url = "/user/key/ssh/" + key_id;
        return (SshKey) Connection.get(url, SshKey.class);
    }
    
    public static String DEBUG_PRINTS_JSON_RESPONSE(int id) throws IllegalStateException, IOException{
        String url = "/user/key/ssh/" + id;
        return Connection.DEBUG_GET_JSON_RESPONSE(url);
    }         
    
    /**
     * Delete an SSH key.
     * @param key_id the id of the key to delete
     * @return true iff key was deleted successfully
     */
    public static boolean delete(String key_id){
        String url = "/user/key/ssh/" + key_id;
        try {
            return (Boolean) Connection.delete(url,Boolean.class);
        } catch (IOException ex) {
            Logger.getLogger(SshKey.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public static boolean delete(SshKey key){
        return delete(key.getId());
    }
    
    /**
     * Delete this SSH key.
     * @return 
     */
    public boolean delete(){
        return delete(this.uuid_ref);
    }
    
    /**
     * Add an SSH key to this user's account.
     * @param description a description of this key, e.g. a title
     * @param content the content of the key
     * @return the newly constructed SshKey
     */
    public static String add(String description, String content){
        String url = "/user/key/ssh";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("description", description);
        params.put("content", content);
        try {
            return ((String) Connection.post(url,params,String.class)).replace("\"", ""); // adding a key returns it's id in a JSON format e.g. "sd323-stTE4e-tt43", have to 
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    
    
    public String getId() {
        return uuid_ref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }
    

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof SshKey)) return false;
        if(obj == this) return true;
        SshKey that = (SshKey) obj;
        if(that.uuid_ref.equals(this.uuid_ref) && that.description.equals(this.description) && that.fingerprint.equals(this.fingerprint)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.uuid_ref);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.fingerprint);
        return hash;
    }
    
    @Override
    public String toString(){
        return "SshKey {[ getId=" + getId() + ", getDescription=" + getDescription() + ", getFingerprint=" + getFingerprint() + " ]}";
    }
}
