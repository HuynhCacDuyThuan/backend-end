package com.example.demo.controller;

import com.example.demo.dto.ModelDetailDTO;
import com.example.demo.modal.Model;
import com.example.demo.modal.ModelDetail;
import com.example.demo.repository.ModelDetailRepository;
import com.example.demo.repository.ModelRepository;
import com.example.demo.service.ModelDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/model-details")
@CrossOrigin(origins = {"http://localhost:3000", "http://14.225.29.33:3000" ,"http://zto.com.vn:3000"}, allowCredentials = "true")
public class ModelDetailController {

    @Autowired
    private ModelDetailService modelDetailService;
    @Autowired
    ModelRepository modelRepository;

  
    @GetMapping
    public List<ModelDetail> getAllModelDetails() {
        return modelDetailService.getAllModelDetails();
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<ModelDetail> blockModelDetail(@PathVariable Long id) {
        Optional<ModelDetail> existingModelDetail = modelDetailService.getModelDetailById(id);

        if (existingModelDetail.isEmpty()) {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy ModelDetail
        }

        ModelDetail modelDetail = existingModelDetail.get();
        modelDetail.setBlock(true); // Đặt trạng thái block thành true

        // Lưu lại sự thay đổi
        ModelDetail updatedModelDetail = modelDetailService.UpdateModelDetail(modelDetail);
        return ResponseEntity.ok(updatedModelDetail); // Trả về ModelDetail đã được cập nhật
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDetail> getModelDetailById(@PathVariable Long id) {
        Optional<ModelDetail> modelDetail = modelDetailService.getModelDetailById(id);
        return modelDetail.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ModelDetail> createModelDetail(@RequestBody ModelDetailDTO modelDetailDTO) {
        // Tìm Model theo ID
        Model model = modelRepository.findById(modelDetailDTO.getModelId())
            .orElseThrow(() -> new RuntimeException("Model not found"));

        // Tạo đối tượng ModelDetail
        ModelDetail modelDetail = new ModelDetail();
        modelDetail.setModel(model);
        modelDetail.setName(modelDetailDTO.getName());
        modelDetail.setActiveFlag(modelDetailDTO.isActiveFlag());
        modelDetail.setDeleteFlag(modelDetailDTO.isDeleteFlag());
        modelDetail.setCreatedBy(modelDetailDTO.getCreatedBy());

        ModelDetail savedModelDetail = modelDetailService.createModelDetail(modelDetail);
        return ResponseEntity.ok(savedModelDetail);
    }


   
    @PutMapping("/{id}")
    public ResponseEntity<ModelDetail> updateModelDetail(@PathVariable Long id, @RequestBody ModelDetailDTO modelDetailDTO) {
        if (id == null || modelDetailDTO.getModelId() == null) {
            return ResponseEntity.badRequest().body(null); // Return 400 if `id` or `modelId` is null
        }

        // Ensure the model exists before processing
        Model model = modelRepository.findById(modelDetailDTO.getModelId())
                .orElseThrow(() -> new RuntimeException("Model not found"));

        Optional<ModelDetail> existingModelDetail = modelDetailService.getModelDetailById(id);

        if (existingModelDetail.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if model detail not found
        }

        // Proceed with updating model detail
        ModelDetail modelDetail = existingModelDetail.get();
        modelDetail.setModel(model);
        modelDetail.setName(modelDetailDTO.getName());
        modelDetail.setActiveFlag(true);
      modelDetail.setBlock(true);
        ModelDetail updatedModelDetail = modelDetailService.UpdateModelDetail(modelDetail);
        return ResponseEntity.ok(updatedModelDetail); // Return updated model detail
    }



 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteModelDetail(@PathVariable Long id) {
        boolean deleted = modelDetailService.softDeleteModelDetail(id);
        return deleted ? ResponseEntity.ok("ModelDetail deleted successfully") : ResponseEntity.notFound().build();
    }

    
 
    @GetMapping("/by-model/{modelId}")
    public ResponseEntity<List<ModelDetail>> getModelDetailsByModelId(@PathVariable Long modelId) {
        List<ModelDetail> modelDetails = modelDetailService.getModelDetailsByModelId(modelId);
        return modelDetails.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(modelDetails);
    }

}
