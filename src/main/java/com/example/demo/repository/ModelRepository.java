package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {

    List<Model> findByDeleteFlagFalse();
}
