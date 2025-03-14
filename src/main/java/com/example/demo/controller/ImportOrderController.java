package com.example.demo.controller;

import com.example.demo.dto.ImportOrderDTO;
import com.example.demo.modal.ImportOrder;
import com.example.demo.modal.User;
import com.example.demo.repository.ImportOrderRepository;
import com.example.demo.repository.ModelDetailRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ImportOrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/import-orders")
@CrossOrigin(origins = {"http://localhost:3000" , "http://14.225.29.33:3000" ,"http://zto.com.vn:3000","https://zto.com.vn","https://api.zto.com.vn"})
public class ImportOrderController {
	  private static final Logger log = LoggerFactory.getLogger(ImportOrderController.class);
    @Autowired
    private ImportOrderService importOrderService;
    @Autowired
    ImportOrderRepository importOrderRepository;
      @Autowired
      ModelDetailRepository modelDetailRepository;
      @Autowired
      UserRepository userRepository;
   
    
      

          @PostMapping
          public ResponseEntity<?> createImportOrder(@RequestBody ImportOrderDTO orderDTO) {
              try {
                  log.debug("Received orderDTO: {}", orderDTO); // In ra dữ liệu nhận được

                  if (orderDTO.getName() == null || orderDTO.getName().isEmpty()) {
                      return ResponseEntity.badRequest().body("Tên đơn hàng không được để trống");
                  }

                  // Kiểm tra userId có null không
                  if (orderDTO.getUserId() == null) {
                      return ResponseEntity.badRequest().body("UserId không được để trống");
                  }

                  // Ánh xạ từ DTO sang Entity
                  ImportOrder order = new ImportOrder();
                  order.setName(orderDTO.getName());
                  order.setCnShippingCode(orderDTO.getCnShippingCode());
                  order.setVnShippingCode(orderDTO.getVnShippingCode());
                  order.setPackageNumbers(orderDTO.getPackageNumbers());
                  order.setPackageUnitValue(orderDTO.getPackageUnitValue());
                  order.setInsurancePrice(orderDTO.getInsurancePrice());
                  order.setShippingMethod(orderDTO.getShippingMethod());

                  User user = userRepository.findById(orderDTO.getUserId())
                          .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
                  order.setUser(user);
                  order.setLocked(orderDTO.getLocked());

                  // Kiểm tra và lấy thông tin từ modelDetailRepository
                  order.setLineid(orderDTO.getLineId() != null ? modelDetailRepository.findById(orderDTO.getLineId()).orElse(null) : null);
                  order.setPackageUnitId(orderDTO.getPackageUnitId() != null ? modelDetailRepository.findById(orderDTO.getPackageUnitId()).orElse(null) : null);
                  order.setStatusId(orderDTO.getStatusId() != null ? modelDetailRepository.findById(orderDTO.getStatusId()).orElse(null) : null);

                  ImportOrder createdOrder = importOrderService.createImportOrder(order);
                  return ResponseEntity.ok(createdOrder);
              } catch (Exception e) {
                  log.error("Lỗi khi tạo đơn nhập hàng: ", e); // In lỗi chi tiết
                  return ResponseEntity.internalServerError().body("Lỗi khi tạo đơn nhập hàng: " + e.getMessage());
              }
          
      }


    @GetMapping("/customer/{emailCustomer}")
    public ResponseEntity<?> getOrdersByCustomerCode(
            @PathVariable String customerCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ImportOrder> orders = importOrderRepository.findByUser(customerCode, pageable);

            if (orders.isEmpty()) {
                return ResponseEntity.status(404).body("Không tìm thấy đơn hàng nào với mã khách hàng: " + customerCode);
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi lấy đơn hàng: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdDate) { // Lọc theo ngày
    	System.out.println("UserID: " + userId);
    	System.out.println("CreatedDate: " + createdDate);

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdDate")));
            Page<ImportOrder> orders;

            if (search != null && !search.isEmpty() && createdDate != null) {
                orders = importOrderRepository.findByUser_IdAndNameContainingIgnoreCaseAndCreatedDate(
                        userId, search, createdDate, pageable);
            } else if (search != null && !search.isEmpty()) {
                orders = importOrderRepository.findByUser_IdAndNameContainingIgnoreCaseOrCnShippingCodeContainingIgnoreCaseOrVnShippingCodeContainingIgnoreCaseOrUser_CustomerCodeContainingIgnoreCaseOrLineId_NameContainingIgnoreCaseOrPackageUnitId_NameContainingIgnoreCaseOrStatusId_NameContainingIgnoreCaseOrShippingMethodContainingIgnoreCaseOrCreatedDate(
                        userId, search,search, search, search, search, search, search, search, createdDate, pageable);
            } else if (createdDate != null) {
                orders = importOrderRepository.findByUser_IdAndCreatedDate(userId, createdDate, pageable);
            } else {
                orders = importOrderRepository.findByUser_Id(userId, pageable);
            }

            if (orders.isEmpty()) {
                return ResponseEntity.status(404).body("Không tìm thấy đơn hàng nào cho người dùng có ID: " + userId);
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
        	orders = importOrderRepository.findByNameContainingIgnoreCaseOrCnShippingCodeContainingIgnoreCaseOrVnShippingCodeContainingIgnoreCaseOrUser_CustomerCodeContainingIgnoreCaseOrLineId_NameContainingIgnoreCaseOrPackageUnitId_NameContainingIgnoreCaseOrStatusId_NameContainingIgnoreCaseOrShippingMethodContainingIgnoreCaseOrCreatedDate(
                    search, search, search, search, search, search, search, 
                    search, createdDate, pageable);
        } else if (search != null && !search.isEmpty()) {
        	orders = importOrderRepository.findByNameContainingIgnoreCaseOrCnShippingCodeContainingIgnoreCaseOrVnShippingCodeContainingIgnoreCaseOrUser_CustomerCodeContainingIgnoreCaseOrLineId_NameContainingIgnoreCaseOrPackageUnitId_NameContainingIgnoreCaseOrStatusId_NameContainingIgnoreCaseOrShippingMethodContainingIgnoreCaseOrCreatedDate(
                    search, search, search, search, search, search, search, 
                    search, createdDate, pageable);
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
    public ResponseEntity<?> updateImportOrder(@PathVariable Long id, @RequestBody ImportOrderDTO orderDetails) {
        try {
            log.debug("Received update request for order ID {}: {}", id, orderDetails);

            ImportOrder existingOrder = importOrderService.getImportOrderById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
            if (existingOrder.getLocked()==true) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Hiện tại chúng tôi đã nhận được đơn nên sẽ không thể thay đổi thông tin. Mọi thắc mắc vui lòng liên hệ bộ phận CSKH.");
            }
            // Cập nhật các trường nếu có dữ liệu mới
            if (orderDetails.getName() != null) existingOrder.setName(orderDetails.getName());
            if (orderDetails.getPackageNumbers() != null) existingOrder.setPackageNumbers(orderDetails.getPackageNumbers());
            if (orderDetails.getPackageUnitValue() != null) existingOrder.setPackageUnitValue(orderDetails.getPackageUnitValue());
            if (orderDetails.getInsurancePrice() != null) existingOrder.setInsurancePrice(orderDetails.getInsurancePrice());
            if (orderDetails.getShippingMethod() != null) existingOrder.setShippingMethod(orderDetails.getShippingMethod());
            if (orderDetails.getCnShippingCode() != null) existingOrder.setCnShippingCode(orderDetails.getCnShippingCode());
            if (orderDetails.getVnShippingCode() != null) existingOrder.setVnShippingCode(orderDetails.getVnShippingCode());

            // Cập nhật User nếu có userId mới
            if (orderDetails.getUserId() != null) {
                User user = userRepository.findById(orderDetails.getUserId())
                        .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
                existingOrder.setUser(user);
            }

            // Cập nhật các ID nếu có
            existingOrder.setLineid(orderDetails.getLineId() != null ? modelDetailRepository.findById(orderDetails.getLineId()).orElse(null) : existingOrder.getLineId());
            existingOrder.setPackageUnitId(orderDetails.getPackageUnitId() != null ? modelDetailRepository.findById(orderDetails.getPackageUnitId()).orElse(null) : existingOrder.getPackageUnitId());
            existingOrder.setStatusId(orderDetails.getStatusId() != null ? modelDetailRepository.findById(orderDetails.getStatusId()).orElse(null) : existingOrder.getStatusId());

            // Xử lý trường "locked"
            existingOrder.setLocked(orderDetails.getLocked() != null ? orderDetails.getLocked() : true);

            ImportOrder updatedOrder = importOrderRepository.save(existingOrder);

            return ResponseEntity.ok("thành công");
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật đơn hàng: ", e);
            return ResponseEntity.internalServerError().body("Lỗi khi cập nhật đơn hàng: " + e.getMessage());
        }
    }
    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateImportOrderAdmin(@PathVariable Long id, @RequestBody ImportOrderDTO orderDetails) {
        try {
            log.debug("Received update request for order ID {}: {}", id, orderDetails);

            ImportOrder existingOrder = importOrderService.getImportOrderById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
           
            // Cập nhật các trường nếu có dữ liệu mới
            if (orderDetails.getName() != null) existingOrder.setName(orderDetails.getName());
            if (orderDetails.getPackageNumbers() != null) existingOrder.setPackageNumbers(orderDetails.getPackageNumbers());
            if (orderDetails.getPackageUnitValue() != null) existingOrder.setPackageUnitValue(orderDetails.getPackageUnitValue());
            if (orderDetails.getInsurancePrice() != null) existingOrder.setInsurancePrice(orderDetails.getInsurancePrice());
            if (orderDetails.getShippingMethod() != null) existingOrder.setShippingMethod(orderDetails.getShippingMethod());
            if (orderDetails.getCnShippingCode() != null) existingOrder.setCnShippingCode(orderDetails.getCnShippingCode());
            if (orderDetails.getVnShippingCode() != null) existingOrder.setVnShippingCode(orderDetails.getVnShippingCode());

            // Cập nhật User nếu có userId mới
            if (orderDetails.getUserId() != null) {
                User user = userRepository.findById(orderDetails.getUserId())
                        .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
                existingOrder.setUser(user);
            }

            // Cập nhật các ID nếu có
            existingOrder.setLineid(orderDetails.getLineId() != null ? modelDetailRepository.findById(orderDetails.getLineId()).orElse(null) : existingOrder.getLineId());
            existingOrder.setPackageUnitId(orderDetails.getPackageUnitId() != null ? modelDetailRepository.findById(orderDetails.getPackageUnitId()).orElse(null) : existingOrder.getPackageUnitId());
            existingOrder.setStatusId(orderDetails.getStatusId() != null ? modelDetailRepository.findById(orderDetails.getStatusId()).orElse(null) : existingOrder.getStatusId());

            // Xử lý trường "locked"
            existingOrder.setLocked(orderDetails.getLocked() != null ? orderDetails.getLocked() : true);

            ImportOrder updatedOrder = importOrderRepository.save(existingOrder);

            return ResponseEntity.ok("thành công");
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật đơn hàng: ", e);
            return ResponseEntity.internalServerError().body("Lỗi khi cập nhật đơn hàng: " + e.getMessage());
        }
    }

}
