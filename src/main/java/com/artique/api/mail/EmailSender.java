package com.artique.api.mail;

import com.artique.api.entity.EmailLog;
import com.artique.api.mail.dto.EmailRequest;
import com.artique.api.mail.dto.EmailRequest.JoinAuthorizationRequest;
import com.artique.api.mail.dto.JoinEmailForm;
import com.artique.api.mail.dto.RenewPasswordForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RequiredArgsConstructor
@Transactional
public class EmailSender {

  private final JavaMailSender mailSender;
  private final EmailLogRepository emailLogRepository;
  @Value("${spring.mail.username}")
  private String adminEmail;
  public Boolean sendRenewPasswordMail(String email,String newPassword){
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(adminEmail);
      helper.setTo(email);
      helper.setSubject("[Artitque] 비밀번호 재발급 이메일입니다");
      helper.setText(RenewPasswordForm.getMailBody(newPassword),true);

      mailSender.send(message);
    }catch (MessagingException e){
      return false;
    }
    return true;
  }
  public Integer sendVerificationMail(@RequestBody JoinAuthorizationRequest emailRequest){
    int code=generateJoinCode();
    EmailLog emailLog = getCodeAdjustedEmailLog(emailRequest,code);
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(adminEmail);
      helper.setTo(emailRequest.getMailAddress());
      helper.setSubject("[Artitque] 본인인증 이메일입니다");
      helper.setText(JoinEmailForm.getMailBody(code), true);

      mailSender.send(message);
    }catch (MessagingException e){
      return -1;
    }
    return code;
  }

  public int generateJoinCode(){
    return (int)(Math.random()*10000);
  }

  private EmailLog getCodeAdjustedEmailLog(JoinAuthorizationRequest request,int code){
    EmailLog emailLog;
    try {
      emailLog = emailLogRepository.findById(request.getMailAddress())
              .orElseThrow(()->new EmailException("email find exception","EMAIL_FIND"));
      emailLog.updateCode(code);
    }catch (EmailException e){
      emailLog = emailLogRepository.save(new EmailLog(request.getMailAddress(),code));
    }
    return emailLog;
  }
}
