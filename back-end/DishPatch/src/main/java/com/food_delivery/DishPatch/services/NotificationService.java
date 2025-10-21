package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.DTOs.OrderNotificationDTO;
import com.food_delivery.DishPatch.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendOrderNotification(Long restaurantId, Order orderPayload) {
        messagingTemplate.convertAndSend("/topic/orders/" + restaurantId, OrderNotificationDTO.from(orderPayload));
    }
}
