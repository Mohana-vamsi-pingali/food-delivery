package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.DTOs.ApprovalRequestsDTO;
import com.food_delivery.DishPatch.DTOs.RestaurantDTO;
import com.food_delivery.DishPatch.DTOs.UserDTO;
import com.food_delivery.DishPatch.Exceptions.UserNotFoundException;
import com.food_delivery.DishPatch.models.ApprovalRequests;
import com.food_delivery.DishPatch.models.Driver;
import com.food_delivery.DishPatch.models.User;
import com.food_delivery.DishPatch.repositories.ApprovalRequestsRepository;
import com.food_delivery.DishPatch.repositories.UserRepository;
import com.food_delivery.DishPatch.security.JwtAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.food_delivery.DishPatch.models.ApprovalRequests.RequestType;

import java.lang.annotation.IncompleteAnnotationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ApprovalRequestsService {
    private final ApprovalRequestsRepository approvalRequestsRepository;
    private final UserRepository userRepository;
    private UserService userService;
    private DriverService driverService;
    private RestaurantService restaurantService;

    public ApprovalRequestsService(ApprovalRequestsRepository approvalRequestsRepository,
                                   UserRepository userRepository,
                                   UserService userService,
                                   DriverService driverService,
                                   RestaurantService restaurantService) {
        this.approvalRequestsRepository = approvalRequestsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.driverService = driverService;
        this.restaurantService = restaurantService;
    }

    public void createAdminApprovalRequest(ApprovalRequestsDTO adminDetails){
        ApprovalRequests newRequest = new ApprovalRequests();
        String email = adminDetails.getEmail();
        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("User already exists.. Use another email");
        }
        newRequest.setEmail(adminDetails.getEmail());
        newRequest.setRequestType(RequestType.valueOf(adminDetails.getRole().toUpperCase()));
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", adminDetails.getName());
        payload.put("password", adminDetails.getPassword());
//        payload.put("restaurantName", adminDetails.getRestaurantName());
        payload.put("address", adminDetails.getAddress());

        newRequest.setPayloadJson(payload);

        approvalRequestsRepository.save(newRequest);
    }

    public String approveRequest(Long id){
        Long userId = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).userId();
        User u = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("Not a Super Admin"));
        if(u.getRole() == User.Role.SUPERADMIN){
            ApprovalRequests request = approvalRequestsRepository.findById(id).orElseThrow(()->new IllegalStateException("Cannot Find Request"));
            request.setApprovalStatus(ApprovalRequests.ApprovalStatus.ACCEPTED);
            approvalRequestsRepository.save(request);

            //Creating the user with credentials
            UserDTO newUser = new UserDTO();
            newUser.setName((String) request.getPayloadJson().get("name"));
            newUser.setEmail(request.getEmail());
            newUser.setPassword((String) request.getPayloadJson().get("password"));
            newUser.setRole(User.Role.valueOf(request.getRequestType().toString()).toString());
            try {
                userService.createUser(newUser);
            }
            catch (Exception e){
                request.setApprovalStatus(ApprovalRequests.ApprovalStatus.PENDING);
                throw new IllegalArgumentException("Some error while creating the user");
            }
            User createdUser = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("Created User Not Found"));;
            //Creating a Restaurant
            if(request.getRequestType()==RequestType.RESTAURANT_ADMIN) {
                RestaurantDTO newRestaurant = new RestaurantDTO();
                newRestaurant.setName((String) request.getPayloadJson().get("name"));
                newRestaurant.setAddress((String) request.getPayloadJson().get("address"));
                newRestaurant.setOwner(createdUser);
                restaurantService.createRestaurant(newRestaurant);
            }
            //Creating a Driver
            else {
                Driver driver = new Driver();
                driver.setUser(createdUser);
                driverService.createDriver(driver);
            }

            return "Request Approved";
        }
        else {
            throw new UserNotFoundException("Not a Super Admin. Cannot Approve requests");
        }
    }
    public String rejectRequest(Long id){
        Long userId = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).userId();
        User u = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("Not a Super Admin"));
        if(u.getRole() == User.Role.SUPERADMIN) {
            ApprovalRequests request = approvalRequestsRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cannot Find Request"));
            request.setApprovalStatus(ApprovalRequests.ApprovalStatus.REJECTED);
            approvalRequestsRepository.save(request);
            return "Request Rejected";
        }
        throw new UserNotFoundException("Not a Super Admin. Cannot Reject requests");
    }
}
