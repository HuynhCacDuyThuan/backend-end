package com.example.demo.controller;

import com.example.demo.modal.ImportOrder;
import com.example.demo.repository.ImportOrderRepository;
import com.example.demo.service.ImportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/import-orders")
@CrossOrigin(origins = {"http://localhost:3000", "http://14.225.29.33:3000" ,"http://zto.com.vn:3000"})
public class ImportOrderController {

    @Autowired
    private ImportOrderService importOrderService;
    @Autowired
    ImportOrderRepository importOrderRepository;

   
    @PostMapping
    public ResponseEntity<?> createImportOrder(@RequestBody ImportOrder order) {
        try {
            if (order.getName() == null || order.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Tên đơn hàng không được để trống");
            }

            ImportOrder createdOrder = importOrderService.createImportOrder(order);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi khi tạo đơn nhập hàng: " + e.getMessage());
        }
    }

    @GetMapping("/customer/{emailCustomer}")
    public ResponseEntity<?> getOrdersByCustomerCode(
            @PathVariable String emailCustomer,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ImportOrder> orders = importOrderRepository.findByEmailCustomer(emailCustomer, pageable);

            if (orders.isEmpty()) {
                return ResponseEntity.status(404).body("Không tìm thấy đơn hàng nào với mã khách hàng: " + emailCustomer);
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi lấy đơn hàng: " + e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<Page<ImportOrder>> getAllImportOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdDate) { // ✅ Hỗ trợ định dạng ngày

        // Thêm sắp xếp theo createdDate giảm dần
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdDate")));
        Page<ImportOrder> orders;

        if (search != null && !search.isEmpty() && createdDate != null) {
            orders = importOrderRepository.findByNameContainingIgnoreCaseAndCreatedDate(
                    search, createdDate, pageable);
        } else if (search != null && !search.isEmpty()) {
            orders = importOrderRepository.findByNameContainingIgnoreCaseOrCnShippingCodeContainingIgnoreCaseOrVnShippingCodeContainingIgnoreCaseOrEmailCustomerContainingIgnoreCaseOrLineIdContainingIgnoreCaseOrPackageUnitIdContainingIgnoreCaseOrStatusIdContainingIgnoreCase(
                    search, search, search, search, search, search, search, pageable);
        } else if (createdDate != null) {
            orders = importOrderRepository.findByCreatedDate(createdDate, pageable);
        } else {
            orders = importOrderRepository.findAll(pageable);
        }
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getImportOrderById(@PathVariable Long id) {
        Optional<ImportOrder> order = importOrderService.getImportOrderById(id);

        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy đơn hàng với ID: " + id);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImportOrder(@PathVariable Long id) {
        try {
            boolean isDeleted = importOrderService.deleteImportOrderById(id);
            if (isDeleted) {
                return ResponseEntity.ok("Xóa đơn hàng thành công với ID: " + id);
            } else {
                return ResponseEntity.status(404).body("Không tìm thấy đơn hàng với ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi khi xóa đơn hàng: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateImportOrder(@PathVariable Long id, @RequestBody ImportOrder orderDetails) {
        try {
            Optional<ImportOrder> existingOrder = importOrderService.getImportOrderById(id);
            if (!existingOrder.isPresent()) {
                return ResponseEntity.status(404).body("Không tìm thấy đơn hàng với ID: " + id);
            }
            ImportOrder updatedOrder = importOrderService.updateImportOrder(id, orderDetails);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
           
            return ResponseEntity.internalServerError().body("Lỗi khi cập nhật đơn hàng: " + e.getMessage());
        }
    }
}
