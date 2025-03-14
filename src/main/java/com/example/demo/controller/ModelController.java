package com.example.demo.controller;

import com.example.demo.modal.Model;
import com.example.demo.repository.ModelRepository;
import com.example.demo.service.ModelService;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"http://localhost:3000" , "http://14.225.29.33:3000" ,"http://zto.com.vn:3000","https://zto.com.vn","https://api.zto.com.vn"})
@RestController
@RequestMapping("/api/models")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private ModelRepository modelRepository;

    

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity<String> deleteModel(@PathVariable("id") Long id) {
        
        if (!modelRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy model
        }

        // Xóa model khỏi database
        modelRepository.deleteById(id);
        return ResponseEntity.ok("Model with id " + id + " deleted successfully.");
    }
    @PostMapping
    public ResponseEntity<Model> createModel(@RequestBody Model model) {
        // Kiểm tra nếu dữ liệu không hợp lệ có thể trả về lỗi
        if (model == null || model.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Model createdModel = modelService.createModel(model);
        return ResponseEntity.ok(createdModel); // Trả về model đã tạo thành công
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Model>> getActiveModels() {
        List<Model> models = modelService.getActiveModels();
        return ResponseEntity.ok(models); // Trả về danh sách model có deleteFlag = false
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Model> updateModel(@PathVariable("id") Long id, @RequestBody Model model) {
        // Kiểm tra xem model với id có tồn tại không
        Model existingModel = modelService.getModelById(id);
        if (existingModel == null) {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy, trả về 404
        }

        // Cập nhật các thông tin của model
        existingModel.setName(model.getName());
        existingModel.setActiveFlag(model.isActiveFlag());
        existingModel.setDeleteFlag(model.isDeleteFlag());
        existingModel.setModifiedBy(model.getModifiedBy());
        existingModel.setModifiedDate(LocalDateTime.now()); // Cập nhật thời gian sửa

        // Lưu model đã sửa
        Model updatedModel = modelService.updateModel(existingModel);
        return ResponseEntity.ok(updatedModel); // Trả về model đã cập nhật
    }
}
