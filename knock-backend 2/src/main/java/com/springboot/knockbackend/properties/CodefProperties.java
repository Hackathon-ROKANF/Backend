// src/main/java/com/springboot/knockbackend/properties/CodefProperties.java
package com.springboot.knockbackend.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="codef.real-estate")
public class CodefProperties {
    private String clientId;
    private String clientSecret;
    private String publicKeyPem;
    private String baseUrl;
    private String oauthUrl;
    private int timeout = 300;   // 기본 300초

    // getters / setters
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getPublicKeyPem() { return publicKeyPem; }
    public void setPublicKeyPem(String publicKeyPem) { this.publicKeyPem = publicKeyPem; }

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    public String getOauthUrl() { return oauthUrl; }
    public void setOauthUrl(String oauthUrl) { this.oauthUrl = oauthUrl; }

    public int getTimeout() { return timeout; }
    public void setTimeout(int timeout) { this.timeout = timeout; }
}