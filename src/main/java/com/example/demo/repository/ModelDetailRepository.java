package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.ModelDetail;

import java.util.List;

@Repository
public interface ModelDetailRepository extends JpaRepository<ModelDetail, Long> {
	List<ModelDetail> findByModel_IdAndActiveFlagTrueAndDeleteFlagFalse
    (Long modelId);

List<ModelDetail> findByBlockTrueAndActiveFlagTrueAndDeleteFlagFalse();
}
