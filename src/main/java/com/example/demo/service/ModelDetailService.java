package com.example.demo.service;

import com.example.demo.modal.ModelDetail;
import com.example.demo.repository.ModelDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelDetailService {

    @Autowired
    private ModelDetailRepository modelDetailRepository;
   
    public List<ModelDetail> getAllModelDetails() {
    	  return modelDetailRepository.findByBlockTrueAndActiveFlagTrueAndDeleteFlagFalse(); // ✅ Fetch only active & non-deleted
    }

    public List<ModelDetail> getModelDetailsByModelId(Long modelId) {
    	   return modelDetailRepository.findByModel_IdAndActiveFlagTrueAndDeleteFlagFalse(modelId);
    }
    public Optional<ModelDetail> getModelDetailById(Long id) {
        return modelDetailRepository.findById(id);
    }

    // Thêm ModelDetail mới
    public ModelDetail createModelDetail(ModelDetail modelDetail) {
        modelDetail.setCreatedDate(java.time.LocalDateTime.now());
        return modelDetailRepository.save(modelDetail);
    }
    
    
    public ModelDetail UpdateModelDetail(ModelDetail modelDetail) {
        // Nếu modelDetail đã có ID, thì đây là update
        if (modelDetail.getId() != null) {
            return modelDetailRepository.save(modelDetail); // Cập nhật
        }
        return modelDetailRepository.save(modelDetail); // Tạo mới
    }

    public boolean softDeleteModelDetail(Long id) {
        Optional<ModelDetail> optionalModelDetail = modelDetailRepository.findById(id);
        if (optionalModelDetail.isPresent()) {
            ModelDetail modelDetail = optionalModelDetail.get();
            modelDetail.setDeleteFlag(true); 
            modelDetail.setActiveFlag(false); 
            modelDetail.setBlock(false);
            modelDetailRepository.save(modelDetail);
            return true;
        }
        return false;
    }

    // Cập nhật ModelDetail
    public ModelDetail updateModelDetail(Long id, ModelDetail newModelDetail) {
        return modelDetailRepository.findById(id).map(existingModelDetail -> {
            existingModelDetail.setName(newModelDetail.getName());
            existingModelDetail.setActiveFlag(newModelDetail.isActiveFlag());
            existingModelDetail.setDeleteFlag(newModelDetail.isDeleteFlag());
            return modelDetailRepository.save(existingModelDetail);
        }).orElse(null);
    }

   
    public boolean deleteModelDetail(Long id) {
        if (modelDetailRepository.existsById(id)) {
            modelDetailRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
