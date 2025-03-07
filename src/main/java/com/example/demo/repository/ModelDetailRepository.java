package com.example.demo.repository;

import com.example.demo.modal.ModelDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModelDetailRepository extends JpaRepository<ModelDetail, Long> {
	List<ModelDetail> findByModel_IdAndActiveFlagTrueAndDeleteFlagFalseOrBlockTrue(Long modelId);
	   // Truy vấn ModelDetail có activeFlag = true, deleteFlag = false và block = true
    List<ModelDetail> findByActiveFlagTrueAndDeleteFlagFalseAndBlockTrue();
}
