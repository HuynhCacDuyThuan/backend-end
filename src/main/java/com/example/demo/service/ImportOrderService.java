package com.example.demo.service;

import com.example.demo.dto.ImportOrderDTO;
import com.example.demo.modal.ImportOrder;
import com.example.demo.modal.User;
import com.example.demo.repository.ImportOrderRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

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
   @Autowired
   UserRepository userRepository;
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
    @Transactional
    public void lockOrder(Long id) {
        ImportOrder order = importOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với id: " + id));
        order.setLocked(true);
        importOrderRepository.save(order);
    }

    @Transactional
    public void unlockOrder(Long id) {
        ImportOrder order = importOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với id: " + id));
        order.setLocked(false);
        importOrderRepository.save(order);
    }
   
}
