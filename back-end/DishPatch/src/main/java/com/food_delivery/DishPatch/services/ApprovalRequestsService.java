package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.DTOs.ApprovalRequestsDTO;
import com.food_delivery.DishPatch.models.ApprovalRequests;
import com.food_delivery.DishPatch.repositories.ApprovalRequestsRepository;
import com.food_delivery.DishPatch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food_delivery.DishPatch.models.ApprovalRequests.RequestType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ApprovalRequestsService {
    private final ApprovalRequestsRepository approvalRequestsRepository;
    private final UserRepository userRepository;

    public ApprovalRequestsService(ApprovalRequestsRepository approvalRequestsRepository, UserRepository userRepository) {
        this.approvalRequestsRepository = approvalRequestsRepository;
        this.userRepository = userRepository;
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
        payload.put("restaurantName", adminDetails.getRestaurantName());

        newRequest.setPayloadJson(payload);

        approvalRequestsRepository.save(newRequest);
    }

    public void approveRequest(Long id){

    }
}
