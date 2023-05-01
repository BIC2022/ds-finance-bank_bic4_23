package net.froihofer.ejb.bank.common;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class JaxRsAuthenticator implements ClientRequestFilter {

    private String user;
    private String password;

    public JaxRsAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void filter(ClientRequestContext requestContext) throws IOException {
        requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, getBasicAuthentication());
    }

    private String getBasicAuthentication() throws UnsupportedEncodingException {
        String userAndPassword = this.user + ":" + this.password;
        byte[] userAndPasswordBytes = userAndPassword.getBytes("UTF-8");
        return "Basic " + Base64.getEncoder().encodeToString(userAndPasswordBytes);
    }
}