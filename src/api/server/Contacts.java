/**
 * Copyright (c) 2016, danmun
 */
package api.server;

/**
 * 
 * @author danmun
 */
public class Contacts {
    private String owner;
    private String tech;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }
    
    public String toString(){
        return "Contacts {[ getOwner=" + getOwner() + ", getTech=" + getTech() + " ]}";
    }
}
