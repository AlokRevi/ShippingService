package com.pragra.shippingapplication.controller;


import com.pragra.shippingapplication.model.Shipment;
import com.pragra.shippingapplication.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    //create a new Shipment
    @PostMapping
    public Shipment createShipment(@RequestParam Long orderId, @RequestParam Long userId) {
        return shipmentService.createShipment(orderId, userId);
    }

    // get Shipment based on ID.
    @GetMapping("/shipment/{id}")
    public Shipment getShipmentById(@RequestParam Long orderId, @RequestParam Long userId) {
        return shipmentService.getShipmentById(orderId, userId);
    }

    //update shipment
    @PutMapping("/{id}")
    public Shipment updateShipment(@RequestParam Long orderId, @RequestParam Long userId) {
        return shipmentService.createShipment(orderId, userId);
    }

    // delete shipment
    @DeleteMapping("/{id}")
    public Shipment deleteShipment(@RequestParam Long orderId, @RequestParam Long userId) {
        return shipmentService.createShipment(orderId, userId);
    }

}
