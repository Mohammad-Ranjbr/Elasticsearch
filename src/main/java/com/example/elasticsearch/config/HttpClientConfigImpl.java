package com.example.elasticsearch.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.File;

@Configuration
public class HttpClientConfigImpl implements RestClientBuilder.HttpClientConfigCallback{

    //In the customizeHttpClient method, an HttpAsyncClient is customized before it is
    //created so that it can connect to a secure Elasticsearch cluster (HTTPS + Basic Auth).
    //First, an object of the BasicCredentialsProvider class is created. This class actually holds
    //the username and password that are used for authentication. Then, using UsernamePasswordCredentials,
    //the username and password (here "elastic" and "0sIvsObIpWtasjnbixJn") are set.
    //With credentialsProvider.setCredentials(AuthScope.ANY, creds) you declare that this
    //username and password will be used for any domain and port. This means that wherever authentication is required,
    //this information will be automatically sent as a header (Authorization: Basic …) in the HTTP request.
    //Then, with setDefaultCredentialsProvider, this information is applied to the HTTP client.

    //What is the concept of TrustStore?
    //In SSL/TLS communication (such as HTTPS), when a client (for example, your application) connects to a server
    //(such as Elasticsearch), the server sends a digital certificate (SSL Certificate) to identify itself.
    //Now the client must decide:
    //Whether to trust this certificate or not?
    //Is this server really who it claims to be?
    //To make this decision, clients use a file called TrustStore. This file contains a list of certificates that the client trusts.
    //The TrustStore acts exactly like a "trusted friends list".
    //If the certificate that the server sends is not in this list, Java says: "I don't trust it!" and rejects the connection with an SSLHandshakeException error.
    //This path must be on the client side (i.e. the Java application), not on the Elasticsearch server side.
    //When the Java application wants to establish an HTTPS connection with the Elasticsearch server,
    //it needs to access a TrustStore to verify the validity of the SSL certificate coming from the server to make sure that the certificate is trusted.
    //So you place the TrustStore file on the system or server where the Java application is running.
    //Here we create an SSLContextBuilder whose job is to create an SSL context (secure environment for encrypted communications).
    //With the loadTrustMaterial() method, we tell it that this context should use the TrustStore file (which we created earlier and which contains the CA certificate).
    //What is a Keystore?
    //A Keystore is a file used on the server side and contains cryptographic information such as:
    //Private Key
    //Server Certificate
    //What is the use of a Keystore?
    //When you want to enable SSL/TLS on a server (like Elasticsearch), the server needs to be able to identify itself with a signed certificate.
    //For this reason, Elasticsearch (or any other server) needs a file that:
    //Contains the server's private key (for signing)
    //Contains the SSL certificate (for presenting to clients)
    //Keystore          TrustStore
    //Where to use Server side           Client side
    //Contains the private key + server certificate             Trusted certificates
    //Identifies the role of the server and establishes a secure connection                 Specifies which certificates to trust
    //We got the http.p12 file from Elasticsearch. This file was the Keystore.
    //The certificate in the Keystore is usually self-signed. Why?
    //Because when you generate a certificate with tools like elasticsearch-certutil or openssl, you create a certificate for yourself as a CA. That is:
    //You create a CA (Certificate Authority) (a key pair + a root certificate ca.crt)
    //Then with this CA, you issue a certificate to your server (for example http.p12)
    //This certificate is usually placed in the Keystore

    @Override
    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {

        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials("elastic", "0sIvsObIpWtasjnbixJn");
            credentialsProvider.setCredentials(AuthScope.ANY, usernamePasswordCredentials);
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

            String trustStoreLocation = "/home/pc1/Documents/Projects/elasticsearch/certs/truststore.p12";
            File trustStoreFile = new File(trustStoreLocation);

            SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStoreFile, "truststorepassword".toCharArray());
            SSLContext sslContext = sslContextBuilder.build();
            httpAsyncClientBuilder.setSSLContext(sslContext);
        } catch (Exception e){
            e.printStackTrace();
        }
        return httpAsyncClientBuilder;
    }

}
