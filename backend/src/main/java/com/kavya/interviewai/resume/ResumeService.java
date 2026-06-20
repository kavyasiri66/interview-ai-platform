package com.kavya.interviewai.resume;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume uploadResume(MultipartFile file) throws Exception {

        String uploadDir = "uploads/resumes/";

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = uploadDir + file.getOriginalFilename();

        File destinationFile = new File(filePath).getAbsoluteFile();
        file.transferTo(destinationFile);

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setFileType(file.getContentType());
        resume.setFilePath(filePath);
        resume.setUploadedAt(LocalDateTime.now());

        return resumeRepository.save(resume);
    }
}