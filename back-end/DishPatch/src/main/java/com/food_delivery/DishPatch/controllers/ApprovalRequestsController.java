package com.food_delivery.DishPatch.controllers;

import com.food_delivery.DishPatch.DTOs.ApprovalRequestsDTO;
import com.food_delivery.DishPatch.Response.ApiResponse;
import com.food_delivery.DishPatch.models.ApprovalRequests;
import com.food_delivery.DishPatch.services.ApprovalRequestsService;
import jakarta.validation.Valid;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("approval")
public class ApprovalRequestsController {
    private final ApprovalRequestsService approvalRequestsService;

    public ApprovalRequestsController(ApprovalRequestsService approvalRequestsService) {
        this.approvalRequestsService = approvalRequestsService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createAdminRequest(@Valid @RequestBody ApprovalRequestsDTO adminDetails){
        approvalRequestsService.createAdminApprovalRequest(adminDetails);
        return ResponseEntity.ok().body(new ApiResponse("Approval Request Created"));
    }
}
