package com.example.demo.service;

import com.example.demo.modal.ImportOrder;
import com.example.demo.repository.ImportOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImportOrderService {

    @Autowired
    private ImportOrderRepository importOrderRepository;

    // ✅ Save Import Order
    public ImportOrder createImportOrder(ImportOrder order) {
        return importOrderRepository.save(order);
    }

    // ✅ Get All Import Orders
    public Page<ImportOrder> getAllImportOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return importOrderRepository.findAll(pageable);
    }

    // ✅ Get Import Order by ID
    public Optional<ImportOrder> getImportOrderById(Long id) {
        return importOrderRepository.findById(id);
    }
    public boolean deleteImportOrderById(Long id) {
        Optional<ImportOrder> order = importOrderRepository.findById(id);
        if (order.isPresent()) {
            importOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public ImportOrder updateImportOrder(Long id, ImportOrder orderDetails) {
        return importOrderRepository.findById(id).map(order -> {
            // Kiểm tra và cập nhật các trường khác
            if (orderDetails.getName() != null) {
                order.setName(orderDetails.getName());
            }
            if (orderDetails.getPackageNumbers() != null) {
                order.setPackageNumbers(orderDetails.getPackageNumbers());
            }
            if (orderDetails.getPackageUnitValue() != null) {
                order.setPackageUnitValue(orderDetails.getPackageUnitValue());
            }
            if (orderDetails.getInsurancePrice() != null) {
                order.setInsurancePrice(orderDetails.getInsurancePrice());
            }
            if (orderDetails.getShippingMethod() != null) {
                order.setShippingMethod(orderDetails.getShippingMethod());
            }
            if (orderDetails.getEmailCustomer() != null) {
                order.setEmailCustomer(orderDetails.getEmailCustomer());
            }
            if (orderDetails.getCnShippingCode() != null) {
                order.setCnShippingCode(orderDetails.getCnShippingCode());
            }
            if (orderDetails.getVnShippingCode() != null) {
                order.setVnShippingCode(orderDetails.getVnShippingCode());
            }
            if (orderDetails.getLineId() != null) {
                order.setLineId(orderDetails.getLineId());
            }
            if (orderDetails.getPackageUnitId() != null) {
                order.setPackageUnitId(orderDetails.getPackageUnitId());
            }
            if (orderDetails.getStatusId() != null) {
                order.setStatusId(orderDetails.getStatusId());
            }
            
            // Nếu "locked" không được truyền qua orderDetails, cập nhật thành true
            if (orderDetails.getLocked() != null) {
                order.setLocked(orderDetails.getLocked());
            } else {
                order.setLocked(true); // Set locked = true nếu không có giá trị trong orderDetails
            }
            
            return importOrderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
    }

   
}
