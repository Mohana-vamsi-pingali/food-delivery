package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.DTOs.AddCartItemDTO;
import com.food_delivery.DishPatch.models.Cart;
import com.food_delivery.DishPatch.models.CartItem;
import com.food_delivery.DishPatch.models.RestaurantMenu;
import com.food_delivery.DishPatch.repositories.CartItemRepository;
import com.food_delivery.DishPatch.repositories.CartRepository;
import com.food_delivery.DishPatch.repositories.RestaurantMenuRepository;
import com.food_delivery.DishPatch.repositories.UserRepository;
import com.food_delivery.DishPatch.security.AuthUtils;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private RestaurantMenuRepository restaurantMenuRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUtils authUtils;

    public Cart createCart(long userId, RestaurantMenu menuEntry){
        Cart newCart = new Cart();
        newCart.setUser(userRepository.findById(userId).get());
        newCart.setRestaurant(menuEntry.getRestaurant());
        return cartRepository.save(newCart);
    }

    public boolean isItemExists(RestaurantMenu item){
        return item.getAvailability().equals(RestaurantMenu.Availability.AVAILABLE);
    }

    @Transactional
    public AddCartItemDTO addCartItem(AddCartItemDTO dto){
        Long userId = authUtils.currentUserId();
        RestaurantMenu menuEntry = restaurantMenuRepository.findById(dto.getMenuItemId()).orElseThrow(()->new IllegalArgumentException("Cannot Find Restaurant Menu Item"));
        if(isItemExists(menuEntry)) {
            boolean doesCartExist = cartRepository.findByUserIdAndStatus(userId, Cart.Status.OPEN).isPresent();
            Cart c;
            if (doesCartExist) {
                c = cartRepository.findByUserIdAndStatus(userId, Cart.Status.OPEN).get();
                if (c.getRestaurant() != menuEntry.getRestaurant()) {
                    c.setStatus(Cart.Status.ABANDONED);
                    cartItemRepository.deleteByCartId(c.getId());
                    c = createCart(userId, menuEntry);
                }
            } else {
                c = createCart(userId, menuEntry);
            }
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(c);
            newCartItem.setQuantity(dto.getQuantity());
            newCartItem.setRestaurantMenu(menuEntry);
            newCartItem = cartItemRepository.save(newCartItem);
            return new AddCartItemDTO(newCartItem.getId(), newCartItem.getQuantity());
        }
        return new AddCartItemDTO(null, -1);
    }

    public String changeQuantity(Long id, boolean shouldIncrease){
        CartItem item = cartItemRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("No Item Found"));
        item.setQuantity(shouldIncrease ? item.getQuantity()+1 : item.getQuantity()-1);

        cartItemRepository.save(item);
        if(item.getQuantity()<=0){
            cartItemRepository.delete(item);
        }
        return "Successfully Changed";
    }
}
