package com.kavya.interviewai.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findAllByOrderByUploadedAtDesc();

}