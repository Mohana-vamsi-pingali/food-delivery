package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.DTOs.CartItemSummaryDTO;
import com.food_delivery.DishPatch.DTOs.CartSummaryDTO;
import com.food_delivery.DishPatch.models.*;
import com.food_delivery.DishPatch.repositories.*;
import com.food_delivery.DishPatch.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private RestaurantMenuService restaurantMenuService;

    @Autowired
    private AuthUtils authUtils;

    @Transactional(readOnly = true)
    public CartSummaryDTO checkOut(Long cartId){
        Cart c = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));;
        if (!c.getUser().getId().equals(authUtils.currentUserId())) {
            throw new AccessDeniedException("This cart does not belong to you");
        }
        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        Restaurant restaurant = c.getRestaurant();
        List<CartItemSummaryDTO> itemsDto = new ArrayList<CartItemSummaryDTO>();
        BigDecimal subTotal = new BigDecimal(0);
        for(CartItem item: items){
            RestaurantMenu menu = item.getRestaurantMenu();
            BigDecimal price = menu.getPrice();
            int quantity = item.getQuantity();
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(quantity));
            subTotal = subTotal.add(lineTotal);
            itemsDto.add(new CartItemSummaryDTO(item.getId(), menu.getMenuitem().getName(), restaurantMenuService.generatePresignedUrl(menu.getImageUrl()), price, quantity, lineTotal, cartService.isItemExists(menu)));
        };
        BigDecimal deliveryFee = BigDecimal.valueOf(1);
        BigDecimal tax = BigDecimal.valueOf(2);
        return new CartSummaryDTO(c.getId(), restaurant.getName(), itemsDto, subTotal, deliveryFee, tax, deliveryFee.add(tax).add(subTotal));
    }

    @Transactional
    public String placeOrder(Long cartId){
        Cart c = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        if(c.getStatus() == Cart.Status.CHECKED_OUT) throw new IllegalArgumentException("Cart Does not Exist");
        User user = c.getUser();
        if (!user.getId().equals(authUtils.currentUserId())) {
            throw new AccessDeniedException("This cart does not belong to you");
        }
        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        Restaurant restaurant = c.getRestaurant();

        Order order = new Order();
        order.setRestaurant(restaurant);
        order.setUser(user);

        BigDecimal subTotal = new BigDecimal(0);
        for(CartItem item: items){
            RestaurantMenu menu = item.getRestaurantMenu();
            if(cartService.isItemExists(menu)){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setRestaurantMenu(menu);
                orderItem.setQuantity(item.getQuantity());
                orderItem.setUnitPrice(menu.getPrice());
                BigDecimal lineTotal = menu.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                subTotal = subTotal.add(lineTotal);
                orderItem.setLineTotal(lineTotal);

                order.getItems().add(orderItem);
            }
        }

        order.setSubtotal(subTotal);
        BigDecimal deliveryFee = BigDecimal.valueOf(1);
        BigDecimal tax = BigDecimal.valueOf(2);

        order.setDeliveryFee(deliveryFee);
        order.setTax(tax);

        order.setTotalPrice(subTotal.add(deliveryFee).add(tax));

        orderRepository.save(order);

        c.setStatus(Cart.Status.CHECKED_OUT);
        cartRepository.save(c);

        return "Order Placed";

    }
}
