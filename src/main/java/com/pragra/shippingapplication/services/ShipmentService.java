package com.pragra.shippingapplication.services;

import com.pragra.shippingapplication.kafka.ShipmentProducer;
import lombok.Data;
import org.springframework.stereotype.Service;
import com.pragra.shippingapplication.model.Shipment;
import com.pragra.shippingapplication.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Data
@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentProducer shipmentProducer; // Kafka Producer to send shipment events

    @Autowired
    private EmailService emailService;// Inject email service


    //Creates a new shipment  + publishes event to Kafka. + triggers

    public Shipment createShipment(Long orderId, Long userId) {
        Shipment shipment = new Shipment();
        shipment.setOrderId(orderId);

        // We generate a unique random identifier (UUID), convert it to a string, make it the shipment's tracking no:
        shipment.setTrackingNumber(UUID.randomUUID().toString());

        shipment.setStatus("Shipped"); // must use enums to do this instead
        shipment.setShippedDate(new Date());
        // 5 days delivery estimate. Can make this customizable in a future release.
        shipment.setEstimatedDelivery(new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000));
        // shipment added to repo
        shipmentRepository.save(shipment);

        // Publish shipment event to Kafka
        shipmentProducer.sendShipmentCreatedEvent(shipment);// this method comes from  Kafka Producer Service

    /*    // Send email notification via EmailService
        emailService.sendShipmentEmail("customer@example.com",
                shipment.getTrackingNumber(), shipment.getEstimatedDelivery().toString());*/

        // 🔥 Publish shipment event to Kafka (instead of calling EmailService directly)
        shipmentProducer.sendShipmentCreatedEvent(shipment);

        return shipment;

    }

    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    public Shipment updateShipment(Long id, Long orderId, Long userId) {
        Shipment shipment = getShipmentById(id);
        shipment.setOrderId(orderId);
        // Additional update logic can be added here if needed.
        shipmentRepository.save(shipment);
        return shipment;
    }

    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }
}

