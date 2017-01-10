/**
 * Copyright (c) 2016, danmun
 */
package example;


import api.connection.Connection;
import api.network.DdosAlert;
import api.server.FtpBackup;
import api.server.OperatingSystem;
import api.server.Ref;
import api.server.hardware.Disk;
import api.server.Server;
import api.server.ServerRef;
import api.server.Servers;
import api.server.hardware.DiskRef;
import api.server.network.FailoverIP;
import api.server.network.ServerIP;
import api.user.User;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danmun
 */
public class Main {
    public static void main(String[] args){
        Connection.SET_API_KEY(auth.API_KEY);
        User u = null;
        Servers list = null;
        try { 
            // get list of servers associated with your account
            // turn off hardware watch for one of the servers
            list = Servers.getServerList();
            for(ServerRef ref : list){
                p("Server:" + ref.getServerReference());
                p("ID:" + ref.getId());
                
                if(ref.getId() == serverid){
                    Server s = list.getServer(ref.getId());
                    boolean status = s.turnOFFHardwareWatch(true);
                    p(status);
                }
            }
            
            // get info of your FTP backup associated with one of your servers
            FtpBackup ftp = Server.getFtpBackup(serverid);
            p(ftp.getLogin());
            p(ftp.getServer());
            p(ftp.getSpaceQuota());
            
            
            // list available op systems for a server
            OperatingSystem[] OSs = Server.getAvailableOperatingSystems(serverid);
            for(OperatingSystem os : OSs){
                p("-----------");
                p(os.getName() + " - " + os.getVersion());
                p(os.getArchitecture());
                p(os.getType());
                p((os.getEndOfLife() != null) ? os.getEndOfLife().toString() : "");
                p((os.getReleaseDate() != null) ? os.getReleaseDate().toString() : "");
            }
            
            // construct a server object from server id
            Server server = Server.serverFromId(111757);
            ///get one of its IPs
            p(server.getIPs()[0].getAddress());
            
            // construct the user object for the account associated with the API key
            u = User.getUser();
            // display info about user
            p(u.getFirstName() + " " + u.getLastName());
            p(u.getEmail());
            p("id:" + u.getId());
            
        } catch (IllegalStateException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void p(Object o){System.out.println(String.valueOf(o));}
}
