package com.example.demo.repository;

import com.example.demo.modal.ImportOrder;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportOrderRepository extends JpaRepository<ImportOrder, Long> {
    Page<ImportOrder> findByNameContainingIgnoreCaseOrCnShippingCodeContainingIgnoreCaseOrVnShippingCodeContainingIgnoreCaseOrEmailCustomerContainingIgnoreCaseOrLineIdContainingIgnoreCaseOrPackageUnitIdContainingIgnoreCaseOrStatusIdContainingIgnoreCase(
            String name, String cnShippingCode, String vnShippingCode, String emailCustomer, String lineId, String packageUnitId, String statusId, Pageable pageable);
    
    Page<ImportOrder> findByNameContainingIgnoreCaseAndCreatedDate(
            String name, Date createdDate, Pageable pageable);
    
    @Query("SELECT io FROM ImportOrder io WHERE DATE(io.createdDate) = :createdDate")
    Page<ImportOrder> findByCreatedDate(@Param("createdDate") Date createdDate, Pageable pageable);
    Page<ImportOrder> findByEmailCustomer(String emailCustomer, Pageable pageable);

}