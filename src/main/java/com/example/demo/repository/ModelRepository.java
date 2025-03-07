package com.example.demo.repository;

import com.example.demo.modal.Model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {

    List<Model> findByDeleteFlagFalse();
}
