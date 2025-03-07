package com.example.demo.service;

import com.example.demo.modal.Model;
import com.example.demo.repository.ModelRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public Model createModel(Model model) {
     
        return modelRepository.save(model);
    }
    // Phương thức lấy model theo ID
    public Model getModelById(Long id) {
        return modelRepository.findById(id).orElse(null); // Trả về null nếu không tìm thấy
    }
    // Phương thức tìm tất cả model có deleteFlag = false
    public List<Model> getActiveModels() {
        return modelRepository.findByDeleteFlagFalse(); // Lấy các model có deleteFlag = false
    }
    
 // Phương thức sửa model
    public Model updateModel(Model model) {
        return modelRepository.save(model); // Lưu model đã sửa vào cơ sở dữ liệu
    }
}
