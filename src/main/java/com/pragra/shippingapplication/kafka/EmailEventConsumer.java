package com.pragra.shippingapplication.kafka;

import com.pragra.shippingapplication.model.Shipment;
import com.pragra.shippingapplication.services.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// This class listens for shipment events and triggers email notifications.

@Service
public class EmailEventConsumer {

    private final EmailService emailService;

    // all args constructor
    public EmailEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    // Listens for shipment-created events and sends an email.
    @KafkaListener(topics = "shipment-created", groupId = "email-service-group")
    public void consumeShipmentEvent(Shipment shipment) {
        System.out.println("📨 Received Shipment Event: " + shipment);

        // Send email notification using EmailService
        emailService.sendShipmentEmail(
                shipment.getUserEmail(),
                shipment.getTrackingNumber(),
                shipment.getEstimatedDelivery().toString()
        );
        //Now, EmailService is fully decoupled from ShipmentService.
    }
}
