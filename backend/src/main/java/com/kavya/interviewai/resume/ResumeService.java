package com.kavya.interviewai.resume;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import com.kavya.interviewai.ai.OpenAiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final PdfService pdfService;
    private final OpenAiService openAiService;

    public List<Resume> getAllResumes() {
        return resumeRepository.findAllByOrderByUploadedAtDesc();
    }

    public ResumeService(
            ResumeRepository resumeRepository,
            PdfService pdfService,
            OpenAiService openAiService
    ) {
        this.resumeRepository = resumeRepository;
        this.pdfService = pdfService;
        this.openAiService = openAiService;
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

        String extractedText = pdfService.extractTextFromPdf(destinationFile.getPath());
        String aiAnalysis = openAiService.analyzeResume(extractedText);

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setFileType(file.getContentType());
        resume.setFilePath(filePath);
        resume.setUploadedAt(LocalDateTime.now());
        resume.setExtractedText(extractedText);
        resume.setAiAnalysis(aiAnalysis);

        return resumeRepository.save(resume);
    }
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Resume not found"
                ));
    }

    public void deleteResume(Long id) {
        Resume resume = getResumeById(id);
        resumeRepository.delete(resume);
    }
}