package com.kavya.interviewai.resume;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public Resume uploadResume(@RequestParam("file") MultipartFile file) throws Exception {
        return resumeService.uploadResume(file);
    }
}