/**
 * Copyright (c) 2016, danmun
 */
package api.user;

import api.connection.Connection;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danmun
 */
public class User {
    private Number id;
    private String login;
    private String email;
    private String first_name;
    private String last_name;
    private String company;
    
    // assigned at the first call of getSshKeys()
    private transient List<SshKey> sshKeys;
    
    /**
     * Get the user associated with the application's API key found in Connection.API_KEY
     * @return the user
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static User getUser() throws IllegalStateException, IOException{
        String url = "/user";
        return (User) Connection.get(url, User.class);
    }
    
    public static String DEBUG_PRINTS_JSON_RESPONSE() throws IllegalStateException, IOException{
        String url = "/user";
        return Connection.DEBUG_GET_JSON_RESPONSE(url);
    }     
    
    
    public List<SshKey> getSshKeys() throws IllegalStateException, IOException{
        if(sshKeys != null) return sshKeys;
        String url = "/user/key/ssh";
        SshKey[] tmp = (SshKey[]) Connection.get(url, SshKey[].class);
        sshKeys = Arrays.asList(tmp);
        return sshKeys;
    }
    
    /**
     * Add an SSH key to this user's account.
     * @param description a description of this key, e.g. a title
     * @param content the content of the key
     * @return the newly added and constructed key, if addition was successful; null if sshKeyFromId() fails
     */
    public SshKey addSshKey(String description, String content){
        String newKeyId = SshKey.add(description, content); // connection 1
        SshKey newKey = null;
        if(newKeyId != null && sshKeys != null){
            try {
                newKey = SshKey.sshKeyFromId(newKeyId); // connection 2
                sshKeys.add(newKey);
            } catch (IllegalStateException | IOException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return newKey;
    }
    
    /**
     * Delete an SSH key from this user's account.
     * @param key_id id of the SSH key to delete
     * @return true iff deletion was successful
     */
    public boolean deleteSshKey(String key_id){
        boolean deleted = SshKey.delete(key_id);
        if(sshKeys != null && deleted){
            for(int i = sshKeys.size() - 1; i >= 0; i--){
                if(sshKeys.get(i).getId().equals(key_id)){
                    sshKeys.remove(i);
                    return true;
                }
            }
        }
        return false;          
    }
    
    public boolean deleteSshKey(SshKey key){
        return deleteSshKey(key.getId());
    }
    
    public boolean hasSshKey(SshKey key){
        return sshKeys.contains(key);
    }

    public boolean hasSshKeyId(String key_id){
        for(SshKey key : sshKeys){
            if(key.getId().equals(key_id)) return true;
        }
        return false;
    }
    
    public boolean hasSshKeyFingerprint(String fingerprint){
        for(SshKey key : sshKeys){
            if(key.getFingerprint().equals(fingerprint)) return true;
        }
        return false;
    }    
    
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    
    /**
     * Textual representation of this User.
     * 
     * @return 
     */
    public String toString(){
        return "User {[ getId=" + getId() + ", getLogin=" + getLogin() + ", getEmail=" + getEmail() + ", getFirstName=" + getFirstName() + ", "
                + "getLastName=" + getLastName() + ", getCompany=" + getCompany() + " ]}";
    }
    
}

