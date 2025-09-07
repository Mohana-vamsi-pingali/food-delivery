package com.food_delivery.DishPatch.controllers;

import com.food_delivery.DishPatch.DTOs.ApprovalRequestsDTO;
import com.food_delivery.DishPatch.Response.ApiResponse;
import com.food_delivery.DishPatch.services.ApprovalRequestsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("approve/{id}")
    public ResponseEntity<ApiResponse> approveRequest(@PathVariable Long id){
        String msg = approvalRequestsService.approveRequest(id);
        return ResponseEntity.ok().body(new ApiResponse(msg));
    }

    @PostMapping("reject/{id}")
    public ResponseEntity<ApiResponse> rejectRequest(@PathVariable Long id){
        String msg = approvalRequestsService.rejectRequest(id);
        return ResponseEntity.ok().body(new ApiResponse(msg));
    }
}
