package com.pragra.shippingapplication.kafka;

import com.pragra.shippingapplication.model.Shipment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/*
This class will send a message to kafka when a shipment is created.
 */


@Service
public class ShipmentProducer {

    private final KafkaTemplate<String, Shipment> kafkaTemplate;

    //all args constructor
    public ShipmentProducer(KafkaTemplate<String, Shipment> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //Sends shipment creation event to Kafka topic.
    public void sendShipmentCreatedEvent(Shipment shipment) {
        kafkaTemplate.send("shipment-created", shipment);
    }
}