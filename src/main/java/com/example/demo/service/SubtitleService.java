package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.SubtitleRepository;

@Service
public class SubtitleService {

    @Autowired
    private SubtitleRepository subtitleRepository;

    public void deleteSubtitle(Long subtitleId) {
        subtitleRepository.deleteById(subtitleId);
    }
}
