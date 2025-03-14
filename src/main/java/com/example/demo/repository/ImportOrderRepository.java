package com.example.demo.repository;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.ImportOrder;

@Repository
public interface ImportOrderRepository extends JpaRepository<ImportOrder, Long> {
	Page<ImportOrder> findByNameContainingIgnoreCaseOrCnShippingCodeContainingIgnoreCaseOrVnShippingCodeContainingIgnoreCaseOrUser_CustomerCodeContainingIgnoreCaseOrLineId_NameContainingIgnoreCaseOrPackageUnitId_NameContainingIgnoreCaseOrStatusId_NameContainingIgnoreCaseOrShippingMethodContainingIgnoreCaseOrCreatedDate(
		    String name, String cnShippingCode, String vnShippingCode, String customerCode, 
		    String lineId, String packageUnitId, String statusId, 
		    String shippingMethod, Date createdDate, Pageable pageable);


    
    Page<ImportOrder> findByNameContainingIgnoreCaseAndCreatedDate(
            String name, Date createdDate, Pageable pageable);
    
    @Query("SELECT io FROM ImportOrder io WHERE DATE(io.createdDate) = :createdDate")
    Page<ImportOrder> findByCreatedDate(@Param("createdDate") Date createdDate, Pageable pageable);
    Page<ImportOrder> findByUser(String customerCode, Pageable pageable);
    Page<ImportOrder> findByUser_IdAndNameContainingIgnoreCaseAndCreatedDate(Long userId, String name, Date createdDate, Pageable pageable);

    // Tìm kiếm theo userId và nhiều tiêu chí khác
    Page<ImportOrder> findByUser_IdAndNameContainingIgnoreCaseOrCnShippingCodeContainingIgnoreCaseOrVnShippingCodeContainingIgnoreCaseOrUser_CustomerCodeContainingIgnoreCaseOrLineId_NameContainingIgnoreCaseOrPackageUnitId_NameContainingIgnoreCaseOrStatusId_NameContainingIgnoreCaseOrShippingMethodContainingIgnoreCaseOrCreatedDate(
    		Long userId, String name, String cnShippingCode, String vnShippingCode, String customerCode, 
		    String lineId, String packageUnitId, String statusId, 
		    String shippingMethod, Date createdDate, Pageable pageable);
    @Query("SELECT io FROM ImportOrder io WHERE io.user.id = :userId AND FUNCTION('DATE', io.createdDate) = :createdDate")
    Page<ImportOrder> findByUser_IdAndCreatedDate(
        @Param("userId") Long userId,
        @Param("createdDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date createdDate,
        Pageable pageable);



    // Lấy đơn hàng theo userId
    Page<ImportOrder> findByUser_Id(Long userId, Pageable pageable);
}