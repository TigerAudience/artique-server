package com.artique.api.mail;

import com.artique.api.entity.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<EmailLog,String> {
}
