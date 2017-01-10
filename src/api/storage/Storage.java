/**
 * Copyright (c) 2016, danmun
 */
package api.storage;

import api.connection.Connection;
import java.io.IOException;

/**
 *
 * @author danmun
 */
public class Storage {
    public static RpnRsyncBackup[] getRpnRsyncBackups() throws IOException{
        String url = "/storage/rpn/rsync";
        return (RpnRsyncBackup[]) Connection.get(url,RpnRsyncBackup[].class);
    }
    
    public static RpnSan[] getRpnSans() throws IOException{
        String url = "/storage/rpn/san";
        return (RpnSan[]) Connection.get(url,RpnSan[].class);
    }
}
