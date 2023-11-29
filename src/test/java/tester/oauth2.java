/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tester;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dmfs.httpessentials.client.HttpRequestExecutor;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.httpurlconnection.HttpUrlConnectionExecutor;
import org.dmfs.oauth2.client.BasicOAuth2AuthorizationProvider;
import org.dmfs.oauth2.client.BasicOAuth2Client;
import org.dmfs.oauth2.client.BasicOAuth2ClientCredentials;
import org.dmfs.oauth2.client.OAuth2AccessToken;
import org.dmfs.oauth2.client.OAuth2AuthorizationProvider;
import org.dmfs.oauth2.client.OAuth2Client;
import org.dmfs.oauth2.client.OAuth2ClientCredentials;
import org.dmfs.oauth2.client.OAuth2InteractiveGrant;
import org.dmfs.oauth2.client.grants.AuthorizationCodeGrant;
import org.dmfs.oauth2.client.grants.ResourceOwnerPasswordGrant;
import org.dmfs.oauth2.client.scope.BasicScope;
import org.dmfs.oauth2.client.state.InteractiveGrantFactory;
import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.uris.LazyUri;
import org.dmfs.rfc5545.Duration;

/**
 *
 * @author Administrator
 */
public class oauth2 {

    public static void main(String[] args) {
        
        HttpRequestExecutor executor = new HttpUrlConnectionExecutor();
// Create OAuth2 provider
            OAuth2AuthorizationProvider provider = new BasicOAuth2AuthorizationProvider(
                    URI.create("https://is-test.regione.sicilia.it/oauth2/authorize"),
                    URI.create("https://is-test.regione.sicilia.it/oauth2/token"),
                    new Duration(1, 0, 3600) /* default expiration time in case the server doesn't return any */);

// Create OAuth2 client credentials
            OAuth2ClientCredentials credentials = new BasicOAuth2ClientCredentials(
                    "fq8aefusOqwjKNc5dkchF62ncLwa", "YTnffqNEfhfHqLTPUjfsumMaCAYa");

// Create OAuth2 client
            OAuth2Client client = new BasicOAuth2Client(
                    provider,
                    credentials,
                    new LazyUri(new Precoded("https://tu.formati.education/demo/SPIDSERVLET")) /* Redirect URL */);
        OAuth2InteractiveGrant grant = new InteractiveGrantFactory(client).apply("8ANFShW9fcR7vgzOShrX31TnjFiknEnuNfh3FCKyHlPj4NyX-qlDWr0SFzQ19Mv6");
//        try {
//            HttpRequestExecutor executor = new HttpUrlConnectionExecutor();
//// Create OAuth2 provider
//            OAuth2AuthorizationProvider provider = new BasicOAuth2AuthorizationProvider(
//                    URI.create("https://is-test.regione.sicilia.it/oauth2/authorize"),
//                    URI.create("https://is-test.regione.sicilia.it/oauth2/token"),
//                    new Duration(1, 0, 3600) /* default expiration time in case the server doesn't return any */);
//
//// Create OAuth2 client credentials
//            OAuth2ClientCredentials credentials = new BasicOAuth2ClientCredentials(
//                    "fq8aefusOqwjKNc5dkchF62ncLwa", "YTnffqNEfhfHqLTPUjfsumMaCAYa");
//
//// Create OAuth2 client
//            OAuth2Client client = new BasicOAuth2Client(
//                    provider,
//                    credentials,
//                    new LazyUri(new Precoded("https://tu.formati.education/demo/SPIDSERVLET")) /* Redirect URL */);
//// Start an interactive Authorization Code Grant
//            OAuth2InteractiveGrant grant = new AuthorizationCodeGrant(
//                    client, new BasicScope("scope"));
//// Get the authorization URL and open it in a WebView
//            URI authorizationUrl = grant.authorizationUrl();
//// Open the URL in a WebView and wait for the redirect to the redirect URL
//// After the redirect, feed the URL to the grant to retrieve the access token
//            System.out.println("tester.oauth2.main() " + authorizationUrl);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }
}
