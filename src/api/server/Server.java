/**
 * Copyright (c) 2016, danmun
 */
package api.server;

import api.server.bmc.BMC;
import api.server.hardware.DriveArray;
import api.server.hardware.RaidControllerRef;
import api.server.hardware.DiskRef;
import api.server.network.Network;
import api.server.network.ServerIP;
import api.connection.Connection;
import api.server.bmc.BmcSessionDetails;
import api.server.network.FailoverIP;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author danmun
 */
public class Server {
    private Number id;
    private String offer;
    private String hostname;
    private OperatingSystem os;
    private String power;
    private String boot_mode;
    private Date last_reboot;
    private boolean anti_ddos;
    // API Documentation missing this value but it's there in the JSON
    private boolean hardware_watch;
    private boolean proactive_monitoring;
    private String support;
    private String abuse;
    private Location location;
    private Network network;
    private ServerIP[] ip;
    private Contacts contacts;
    private RescueCredentials rescue_credentials;
    private BMC bmc;
    private DiskRef[] disks;
    private DriveArray[] drive_arrays;
    private RaidControllerRef[] raid_controllers;
    
    /**
     * Construct a Server from the server with the given ID.
     * @param id the id of the server to construct
     * @return the Server object
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static Server serverFromId(int id) throws IllegalStateException, IOException{
        return serverFromId(id,false);
    }
    
    /**
     * Construct a Server object from the server with the given ID.
     * Optionally force-refresh the power state of the machine so that the POWER_STATE field in the response is up-to-date (response may be slower/can time out).
     * @param id the id of the server to construct
     * @param refreshPowerState if true, the power state of the machine will be refreshed before receiving a response
     * @return the Server object
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static Server serverFromId(int id, boolean refreshPowerState) throws IllegalStateException, IOException{
        String url = "/server/" + id;
        Map<String,String> params = new LinkedHashMap<>();
        params.put("refresh", String.valueOf(refreshPowerState));        
        return (Server) Connection.get(url,Server.class);
    } 
    
    /**
     * For debug only - prints json response on scraping /server/{id}
     * @param id the id of the server we are interested in
     * @return the json response containing the server info
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static String DEBUG_PRINTS_JSON_RESPONSE(int id) throws IllegalStateException, IOException{
        String url = "/server/" + id;
        return Connection.DEBUG_GET_JSON_RESPONSE(url);
    } 
    
    /**
     * use Connection.(MODE) to set request mode
     * @param url
     * @param params
     * @param mode
     * @return
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static String DEBUG_PRINTS_JSON_RESPONSE(String url,Map<String,String> params,String mode) throws IllegalStateException, IOException{
        return Connection.DEBUG_GET_JSON_RESPONSE(url,params,mode);
    }         
    
    public static boolean archive(int id, List<String> platforms) throws IOException{
        return archive(id,"standard",platforms);
    }
    
    /**
     * Archive the specified server's FTP backup on C14, returns true with an HTTP code 202.
     * @param id id of the server to archive
     * @param parity 	parity (standard, or enterprise; default: standard)
     * @param platforms IDs of platforms where the archive will be stored
     * @return true with an HTTP code 202
     * 
     * <note to self, http://stackoverflow.com/questions/34789357/how-to-pass-liststring-in-post-method-using-spring-mvc>
     */
    public static boolean archive(int id, String parity, List<String> platforms) throws IOException{
        String url = "/server/backup/archive";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("server_id", String.valueOf(id));
        String platformsString = "";
        for(int i = 0; i < platforms.size(); i++){
            if(i == 0){
                platformsString += "[";
            }else if(i == platforms.size() - 1){
                platformsString += platforms.get(i) + "]";
            }else{
                platformsString += platforms.get(i) + ",";
            }
        }
        params.put("platforms",platformsString);
        return (Boolean) Connection.post(url,params,Boolean.class);
    }
    
    public boolean archive(List<String> platforms) throws IOException{
        return archive(this.id.intValue(),"standard",platforms);
    }
    
    public boolean archive(String parity, List<String> platforms) throws IOException{
        return archive(this.id.intValue(),parity,platforms);
    }
    
    /**
     * Create a session on iLO (HP) or iDRAC (Dell), returns a session key with an HTTP code 201. 
     * The session can only be accessed from the provided IP.
     * @param id
     * @param ip
     * @return the session key
     */
    public static String createBmcSession(int id, String ip) throws IOException{
        String url = "/server/bmc/session";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("server_id", String.valueOf(id));
        params.put("ip", ip);
        return (String) Connection.post(url, params, String.class);
    }
    
    public String createBmcSession(String ip) throws IOException{
        return createBmcSession(this.id.intValue(),ip);
    }
    
    /**
     * Returns authentication details for a BMC session (iLO/iDRAC) identified by its session key. 
     * May return an empty JSON object with an HTTP code 202 until the session is ready.
     * @param session_key
     * @return
     * @throws IOException 
     * <DEV NOTE: not possible to overload this method, so decide whether it should be static or not>
     */
    public static BmcSessionDetails getBmcSessionDetails(String session_key) throws IOException{
        String url = "/server/bmc/session/" + session_key;
        return (BmcSessionDetails) Connection.get(url,BmcSessionDetails.class);
    }
    
    public static void closeBmcSession(String session_key) throws IOException{
        String url = "/server/bmc/session/" + session_key;
        Connection.delete(url);
    }
    
    /**
     * Updates properties of the FTP backup service associated with the specified server.
     * @param id
     * @param password the new password, null to leave it unchanged
     * @param autologin true/false to enable/disable autologin, null to leave unchanged
     * @param acl_enabled true/false to enable/disable ACL, null to leave unchanged
     * @return true on successful update process
     */
    public static boolean updateFtpBackupProperties(int id, String password, Boolean autologin, Boolean acl_enabled) throws IOException{
        String url = "/server/backup/edit";
        Map<String,String> params = new LinkedHashMap<>();
        if(password != null) params.put("password",password);
        if(autologin != null) params.put("autologin",String.valueOf(autologin));
        if(acl_enabled != null) params.put("acl_enabled",String.valueOf(acl_enabled));
        if(params.isEmpty()) return false;
        return (Boolean) Connection.post(url, params, Boolean.class);
    }
    
    /**
     * Updates properties of the FTP backup service associated with this server.
     * @param password the new password, null to leave it unchanged
     * @param autologin true/false to enable/disable autologin, null to leave unchanged
     * @param acl_enabled true/false to enable/disable ACL, null to leave unchanged
     * @return true on successful update process
     * @throws IOException 
     */
    public boolean updateFtpBackupProperties(String password, Boolean autologin, Boolean acl_enabled) throws IOException{
        return updateFtpBackupProperties(this.id.intValue(),password,autologin,acl_enabled);
    }
    
    /**
     * Get the FTP Backup service associated with the given server.
     * @param id id of the server whose FTP Backup we want to get
     * @return the FtpBackup object representing the FTP backup service of the server
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static FtpBackup getFtpBackup(int id) throws IllegalStateException, IOException{
        String url = "/server/backup/" + id;
        return (FtpBackup) Connection.get(url,FtpBackup.class);
    }
    
    /**
     * Get the FTP Backup service associated with this server.
     * @return the FtpBackup object representing the FTP backup service of the server
     * @throws IllegalStateException
     * @throws IOException 
     */
    public FtpBackup getFtpBackup() throws IllegalStateException, IOException{
        return getFtpBackup(this.id.intValue());
    }
    /**
     * Update the hostname of the specified server.
     * @param id the id of the server which we want to update
     * @param newHostname the new hostname to set
     * @return true if update was successful
     * @throws IOException 
     */
    public static boolean updateHostname(int id, String newHostname) throws IOException{
        String url = "/server/" + id;
        Map<String,String> params = new LinkedHashMap<>();
        params.put("hostname", newHostname);
        return (Boolean) Connection.put(url,params,Boolean.class); // should be return (Boolean) Connection.put(url,params,Boolean.class);
    }

    /**
     * Update the hostname for this machine.
     * @param newHostname the new hostname to set
     * @return true if update was successful
     * @throws IOException 
     */
    public boolean updateHostname(String newHostname) throws IOException{
        return updateHostname(this.id.intValue(),newHostname);
    }
    
    /**
     * Shut down the specified server. 
     * Give a reason and an email to notify of the shutdown.
     * @param id the id of the server to shutdown
     * @param reason the reason for the shutdown, can be left blank but probably shouldn't be null
     * @param emailToNotify the email address to send a notification to
     * @return true if shutdown was successful
     * @throws IOException 
     */
    public static boolean shutdown(int id, String reason, String emailToNotify) throws IOException{
        String url = "/server/shutdown/" + id;
        Map<String,String> params = new LinkedHashMap<>();
        params.put("reason", reason);
        params.put("email", emailToNotify);
        return (Boolean) Connection.post(url,params,Boolean.class); // should be return (Boolean) Connection.put(url,params,Boolean.class);
    }

    /**
     * Shut down this server.
     * @param reason the reason for the shutdown, can be left blank but probably shouldn't be null
     * @param emailToNotify the email address to send a notification to
     * @return true if shutdown was successful
     * @throws IOException 
     */
    public boolean shutdown(String reason, String emailToNotify) throws IOException{
        return shutdown(this.id.intValue(),reason,emailToNotify);
    }    
    
    /**
     * Reboot the specified server.
     * Give a reason and an email to notify of the reboot.
     * @param id the id of the server to reboot
     * @param reason the reason for the reboot, can be left blank but probably shouldn't be null
     * @param emailToNotify the email address to send a notification to
     * @return true if reboot was successful
     * @throws IOException 
     */
    public static boolean reboot(int id, String reason, String emailToNotify) throws IOException{
        String url = " /server/reboot/" + id;
        Map<String,String> params = new LinkedHashMap<>();
        params.put("reason", reason);
        params.put("email", emailToNotify);
        return (Boolean) Connection.post(url,params,Boolean.class); // should be return (Boolean) Connection.put(url,params,Boolean.class);
    }

    /**
     * Reboot this server.
     * Give a reason and an email to notify of the reboot.
     * @param reason the reason for the reboot, can be left blank but probably shouldn't be null
     * @param emailToNotify the email address to send a notification to
     * @return true if reboot was successful
     * @throws IOException 
     */
    public boolean reboot(String reason, String emailToNotify) throws IOException{
        return reboot(this.id.intValue(),reason,emailToNotify);
    }       
    
    /**
     * Turn ON hardware watch for this server.
     * Specify whether the associated phone number with this server should be called before tech intervention.
     * @param phoneBeforeIntervention set it to true iff server owner should be phoned before hardware is inspected/removed/changed
     * @return true iff the hardware watch was successfully turned on
     * @throws IOException 
     */
    public boolean turnONHardwareWatch(boolean phoneBeforeIntervention) throws IOException{
        return turnONHardwareWatch(this.id.intValue(),phoneBeforeIntervention);
    }
    
    /**
     * Turn OFF hardware watch for this server.
     * Specify whether the associated phone number with this server should be called before tech intervention.
     * @param phoneBeforeIntervention set it to true iff server owner should be phoned before hardware is inspected/removed/changed
     * @return true iff the hardware watch was successfully turned on
     * @throws IOException 
     */    
    public boolean turnOFFHardwareWatch(boolean phoneBeforeIntervention) throws IOException{
        return turnOFFHardwareWatch(this.id.intValue(),phoneBeforeIntervention);
    }    
    
    /**
     * Turn ON hardware watch for the specified server.
     * Specify whether the associated phone number with this server should be called before tech intervention.
     * @param id the id of the server on which hardware watch is to be enabled
     * @param phoneBeforeIntervention set it to true iff server owner should be phoned before hardware is inspected/removed/changed
     * @return true iff the hardware watch was successfully turned on
     * @throws IOException 
     */    
    public static boolean turnONHardwareWatch(int id, boolean phoneBeforeIntervention) throws IOException{
        return setHardwareWatch("enable",id,phoneBeforeIntervention);
    }
    
    /**
     * Turn OFF hardware watch for the specified server.
     * Specify whether the associated phone number with this server should be called before tech intervention.
     * @param id the id of the server on which hardware watch is to be disabled
     * @param phoneBeforeIntervention set it to true iff server owner should be phoned before hardware is inspected/removed/changed
     * @return true iff the hardware watch was successfully turned on
     * @throws IOException 
     */        
    public static boolean turnOFFHardwareWatch(int id, boolean phoneBeforeIntervention) throws IOException{
        return setHardwareWatch("disable",id,phoneBeforeIntervention);
    }    
    
    private static boolean setHardwareWatch(String action, int id, boolean phoneBeforeIntervention) throws IOException{
        String url = "/server/hardwareWatch/" + action + "/" + id;
        Map<String,String> params = new LinkedHashMap<>();
        params.put("phone", String.valueOf(phoneBeforeIntervention));
        return (Boolean) Connection.post(url,params,Boolean.class); // should be return (Boolean) Connection.put(url,params,Boolean.class);
    }
    
    /**
     * Boot a server in normal mode.
     * @param id the id of the server to boot
     * @return success of the boot
     * @throws IOException 
     */
    private static boolean bootNormal(int id) throws IOException{
        String url = "/server/boot/normal/" + id;
        return (Boolean) Connection.post(url,Boolean.class);
    }
    
    /**
     * Boot up this server.
     * @return
     * @throws IOException 
     */
    public boolean bootNormal() throws IOException{
        return bootNormal(this.id.intValue());
    }    

    /**
     * Boot a server in Rescue mode.
     * @param id the id of the server to boot in Rescue mode
     * @param rescue_image the name of the rescue image to use (see /server/rescue_images for values)
     * @return the rescue credentials used to log in after the rescue boot
     * @throws IOException 
     */
    private static RescueCredentials bootRescue(int id, String rescue_image) throws IOException{
        String url = "/server/boot/rescue/" + id;
        if(rescue_image == null) return null;
        Map<String,String> params = new LinkedHashMap<>();
        params.put("image", rescue_image);
        return (RescueCredentials) Connection.post(url,params,RescueCredentials.class);
    }
    
    /**
     * Boot this server in rescue mode.
     * @param rescue_image the name of the rescue image to use (see /server/rescue_images for values)
     * @return the rescue credentials used to log in after the rescue boot
     * @throws IOException 
     */
    public RescueCredentials bootRescue(String rescue_image) throws IOException{
        return bootRescue(this.id.intValue(),rescue_image);
    }
    
    /**
     * Get a list of all the available rescue images for the specified server which can be used for booting in rescue mode.
     * @param id id of the server for which to check the list
     * @return array of strings, each string is a rescue image name
     * @throws IOException 
     */
    public static String[] getAvailableRescueImages(int id) throws IOException{
        String url = "/server/rescue_images/" + id;
        return (String[]) Connection.get(url,String[].class);
    }
    
    /**
     * Get a list of all the available rescue images for this server.
     * @return array of strings, each string is a rescue image name
     * @throws IOException 
     */
    public String[] getAvailableRescueImages() throws IOException{
        return getAvailableRescueImages(this.id.intValue());
    }
    
    /**
     * Get a list (array) of available operating systems available for the server with the given ID.
     * @param id the id of the server
     * @param type the type of operating system, select one from: all,server,panel,desktop,live,custom,virtualization
     * @return the list of operating systems
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static OperatingSystem[] getAvailableOperatingSystems(int id, String type) throws IllegalStateException, IOException{//all, server, panel, desktop, live, custom, or virtualization
        String url = "/server/operatingSystems/" + id;
        Map<String,String> params = new LinkedHashMap<>();
        params.put("type",type);
        return (OperatingSystem[]) Connection.get(url,params,OperatingSystem[].class);
    }
    
    /**
     * Get a list (array) of available operating systems available for the server with the given ID.
     * @param id the id of the server
     * @return the list of operating systems
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static OperatingSystem[] getAvailableOperatingSystems(int id) throws IllegalStateException, IOException{//all, server, panel, desktop, live, custom, or virtualization
        return getAvailableOperatingSystems(id,"all");
    }    
    
    /**
     * Get a list (array) of available operating systems available for this server.
     * @param type the type of operating system, select one from: all,server,panel,desktop,live,custom,virtualization
     * @return the list of operating systems
     * @throws IllegalStateException
     * @throws IOException 
     */
    public OperatingSystem[] getAvailableOperatingSystems(String type) throws IllegalStateException, IOException{
        return getAvailableOperatingSystems(this.id.intValue(),type);
    }
    
    /**
     * Get a list (array) of available operating systems available for this server.
     * @return the list of operating systems
     * @throws IllegalStateException
     * @throws IOException 
     */
    public OperatingSystem[] getAvailableOperatingSystems() throws IllegalStateException, IOException{
        return getAvailableOperatingSystems(this.id.intValue(),"all");
    }    
    
    /**
     * @deprecated 
     * Get the Failover IPs associated with the account of the API key holder.
     * (not instance method because FailoverIPs are associated with an account and not a server (as per the API call),
     * in face, this method might be moved to FailoverIP class permanently in future versions)
     * @return array of FailoverIPs (implementation may be changed to return List&lt;FailoverIP&gt; in the future)
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static FailoverIP[] getFailoverIPs() throws IllegalStateException, IOException{
        String url = "/server/failover";
        return (FailoverIP[]) Connection.get(url,FailoverIP[].class);
    }
    
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public OperatingSystem getOS() {
        return os;
    }

    public void setOS(OperatingSystem os) {
        this.os = os;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getBoot_mode() {
        return boot_mode;
    }

    public void setBoot_mode(String boot_mode) {
        this.boot_mode = boot_mode;
    }

    public Date getLast_reboot() {
        return last_reboot;
    }

    public void setLast_reboot(Date last_reboot) {
        this.last_reboot = last_reboot;
    }

    public boolean hasAntiDdos() {
        return anti_ddos;
    }

    public void setAnti_ddos(boolean anti_ddos) {
        this.anti_ddos = anti_ddos;
    }

    public boolean hasHardwareWatch() {
        return hardware_watch;
    }

    public void setHardware_watch(boolean hardware_watch) {
        this.hardware_watch = hardware_watch;
    }

    public boolean hasProactiveMonitoring() {
        return proactive_monitoring;
    }

    public void setProactive_monitoring(boolean proactive_monitoring) {
        this.proactive_monitoring = proactive_monitoring;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getAbuse() {
        return abuse;
    }

    public void setAbuse(String abuse) {
        this.abuse = abuse;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public ServerIP[] getIPs() {
        return ip;
    }

    public void setIPs(ServerIP[] ip) {
        this.ip = ip;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public RescueCredentials getRescue_credentials() {
        return rescue_credentials;
    }

    public void setRescue_credentials(RescueCredentials rescue_credentials) {
        this.rescue_credentials = rescue_credentials;
    }

    public BMC getBmc() {
        return bmc;
    }

    public void setBmc(BMC bmc) {
        this.bmc = bmc;
    }

    public DiskRef[] getDiskRefs() {
        return disks;
    }

    public void setDiskRefs(DiskRef[] disks) {
        this.disks = disks;
    }

    public DriveArray[] getDriveArrays() {
        return drive_arrays;
    }

    public void setDriveArrays(DriveArray[] drive_arrays) {
        this.drive_arrays = drive_arrays;
    }

    public RaidControllerRef[] getRaidControllerRefs() {
        return raid_controllers;
    }

    public void setRaidControllerRefs(RaidControllerRef[] raid_controllers) {
        this.raid_controllers = raid_controllers;
    }
    
    public String toString(){
        return "Server {[ getId=" + getId().intValue() + ", getOffer=" + getOffer() + ", getHostname=" + getHostname() + ", "
                + "getOS=" + getOS() + ", getPower=" + getPower() + ", getBoot_mode=" + getBoot_mode() + ", "
                + "getLast_reboot=" + getLast_reboot() + ", hasAntiDdos=" + hasAntiDdos() + ", "
                + "hasHardwareWatch=" + hasHardwareWatch() + ", hasProactiveMonitoring=" + hasProactiveMonitoring() + ", "
                + "getSupport=" + getSupport() + ", getAbuse=" + getAbuse() + ", getLocation=" + getLocation() + ", "
                + "getNetwork=" + getNetwork() + ", getIPs=" + Arrays.toString(getIPs()) + ", "
                + "getContacts=" + getContacts() + ", getRescue_credentials=" + getRescue_credentials() + ", "
                + "getBmc=" + getBmc() + ", getDiskRefs=" + Arrays.toString(getDiskRefs()) + ", getDrive_arrays=" + Arrays.toString(getDriveArrays()) + ", "
                + "getRaidControllerRefs=" + Arrays.toString(getRaidControllerRefs()) + " ]}";
    }
}
