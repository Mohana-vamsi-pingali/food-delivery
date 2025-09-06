package com.food_delivery.DishPatch.models;

import com.food_delivery.DishPatch.utils.JpaJsonConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "approval_requests")
public class ApprovalRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 140)
    private String email;

    public static enum RequestType {RESTAURANT_ADMIN, DRIVER};

    @Enumerated(EnumType.STRING)
    @Column(name="request_type", nullable = false)
    private RequestType requestType;

    public static enum ApprovalStatus {PENDING, ACCEPTED, REJECTED};

    @Enumerated(EnumType.STRING)
    @Column(name="approval_status", nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload_json", columnDefinition = "jsonb")
    private Map<String, Object> payloadJson;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public ApprovalRequests() {
    }

    public ApprovalRequests(Long id, RequestType requestType, ApprovalStatus approvalStatus, Map<String, Object> payloadJson, LocalDateTime createdAt) {
        this.id = id;
        this.requestType = requestType;
        this.approvalStatus = approvalStatus;
        this.payloadJson = payloadJson;
        this.createdAt = createdAt;
    }

    public ApprovalRequests(Long id, RequestType requestType, ApprovalStatus approvalStatus, Map<String, Object> payloadJson) {
        this.id = id;
        this.requestType = requestType;
        this.approvalStatus = approvalStatus;
        this.payloadJson = payloadJson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Map<String, Object> getPayloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(Map<String, Object> payloadJson) {
        this.payloadJson = payloadJson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
