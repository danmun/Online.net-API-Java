/**
 * Copyright (c) 2016, danmun
 */
package api.server;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author danmun
 */
public class Ref {
    public static final transient String SERIALIZED_NAME = "$ref";
    @SerializedName(SERIALIZED_NAME)
    private String reference;

    protected String getRef() {
        return reference;
    }
    
    protected void setRef(String ref) {
        this.reference = ref;
    }
    
    public int getId(){
        return Integer.parseInt(reference.substring(reference.lastIndexOf("/") + 1));
    }
}
