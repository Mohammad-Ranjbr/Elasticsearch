1-We enter the Elasticsearch container :
    − docker exec -it elasticsearch /bin/bash

2-We run this command inside the container :
    − /usr/share/elasticsearch/http-certs.zip

3-We are leaving the container :
    - exit

4- Now we need to extract the http-certs.zip file from the container :
    - docker cp elasticsearch:/usr/share/elasticsearch/http-certs.zip .<path>/http-certs.zip

5- We extract the file :
    − unzip http-certs.zip -d certs

6- Now we need to extract ca.crt from ca.p12 :
    - openssl pkcs12 -in ca.p12 -out ca.crt -clcerts -nokeys

7- Now we move the ca.crt file to where the Java code is running and execute the following command :
    - keytool -import \
    -file ca.crt \
    -alias elasticsearch-ca 
    -keystore truststore.p12 \
    -storepass truststorepassword \
    -storetype PKCS12 \
    -noprompt

8- Now we use the address of the truststore.p12 file.
