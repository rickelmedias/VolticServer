package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MqttDataAcquisition {

    @Incoming("source")
    public void getData(String consumedPayload) {
        System.out.println(consumedPayload);
    }
}