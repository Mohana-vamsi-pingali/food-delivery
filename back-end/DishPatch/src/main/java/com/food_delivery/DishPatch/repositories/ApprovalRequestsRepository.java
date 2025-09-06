package com.food_delivery.DishPatch.repositories;

import com.food_delivery.DishPatch.models.ApprovalRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRequestsRepository extends JpaRepository<ApprovalRequests, Long> {

}
