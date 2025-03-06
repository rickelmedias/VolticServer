package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ibm.cloud.sdk.core.service.exception.ConflictException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CouchDbRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onException(ConflictException.class)
                .handled(true)
                .log("Conflict: A user with email ${header.CamelCouchDbDocId} already exists. Skipping insert.");

        from("timer:insert?period=60000")
                .process(exchange -> {
                    String email = "default@example.com";
                    String docId = "user-" + email;

                    Map<String, Object> doc = new HashMap<>();
                    doc.put("_id", docId);
                    doc.put("docType", "User");
                    doc.put("username", "defaultUser");
                    doc.put("email", email);

                    Gson gson = new Gson();
                    JsonElement json = gson.toJsonTree(doc);

                    exchange.getIn().setBody(json);

                    exchange.getIn().setHeader("CamelCouchDbDocId", docId);
                })
                .to("couchdb:http://localhost:5984/volticdb?username=admin&password=AB123456")
                .log("Document inserted into CouchDB: ${body}");
    }
}
