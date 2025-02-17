package com.pragra.shippingapplication.services;

import org.springframework.stereotype.Service;
import com.pragra.shippingapplication.services.ShipmentService.client.UserClient;
import com.pragra.shippingapplication.model.Shipment;
import com.pragra.shippingapplication.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private EmailService emailService;

    public Shipment createShipment(Long orderId, Long userId) {
        Shipment shipment = new Shipment();
        shipment.setOrderId(orderId);
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setStatus("Shipped");
        shipment.setShippedDate(new Date());
        shipment.setEstimatedDelivery(new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000)); // 5 days delivery estimate

        String userEmail = userClient.getUserEmail(userId);
        shipment.setUserEmail(userEmail);

        shipmentRepository.save(shipment);
        emailService.sendShipmentEmail(userEmail, shipment.getTrackingNumber(), shipment.getEstimatedDelivery());

        return shipment;
    }
}

