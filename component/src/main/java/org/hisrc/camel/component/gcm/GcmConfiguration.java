package org.hisrc.camel.component.gcm;


import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

@UriParams
public final class GcmConfiguration {

    public static final String DEFAULT_COMPONENT_NAME = "gcm";

    @UriParam
    @Metadata(required = "true")
    private String apiKey;

    @UriParam
    private String collapseKey;

    @UriParam
    @Metadata(defaultValue = "3")
    private Integer timeToLive;

    @UriParam
    private Boolean delayWhileIdle;

    @UriParam
    private String restrictedPackageName;

    @UriParam
    private Boolean dryRun;

    @UriParam
    private int retries = 0;
    
    @UriParam
    private String stringBodyDataKey= GcmConstants.DEFAULT_STRING_BODY_DATA_KEY;


    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public Integer getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Integer timeToLive) {
        this.timeToLive = timeToLive;
    }

    public Boolean isDelayWhileIdle() {
        return delayWhileIdle;
    }

    public void setDelayWhileIdle(Boolean delayWhileIdle) {
        this.delayWhileIdle = delayWhileIdle;
    }

    public String getRestrictedPackageName() {
        return restrictedPackageName;
    }

    public void setRestrictedPackageName(String restrictedPackageName) {
        this.restrictedPackageName = restrictedPackageName;
    }

    public Boolean isDryRun() {
        return dryRun;
    }

    public void setDryRun(Boolean dryRun) {
        this.dryRun = dryRun;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }
    
    public String getStringBodyDataKey() {
		return stringBodyDataKey;
	}
    
    public void setStringBodyDataKey(String stringBodyDataKey) {
		this.stringBodyDataKey = stringBodyDataKey;
	}
}
