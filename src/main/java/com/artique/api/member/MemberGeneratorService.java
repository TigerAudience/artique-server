package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.external.aws.S3Service;
import com.artique.api.member.request.JoinMemberReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberGeneratorService {
  private final S3Service s3Service;
  public Member generateInitialMember(JoinMemberReq joinMemberReq){
    Member member;
    try {
      String nickname = generateInitialNickname();
      String profileUrl = s3Service.upload(generateInitialFile(),joinMemberReq.getMemberId());
      ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
      member = new Member(joinMemberReq.getMemberId(),
              nickname, profileUrl,"소개 글을 입력해주세요!", joinMemberReq.getMemberPW(), dateTime);
    }catch (Exception e){
      throw new RuntimeException("create member exception");
    }
    return member;
  }
  private String generateInitialNickname(){
    return "nickname";
  }

  private File generateInitialFile()throws IOException {
    File initialSource = new File("default-image.png");
    String newImageUrl = "default-image"+ UUID.randomUUID()+".png";
    File generatedImage = new File(newImageUrl);
    Files.copy(initialSource.toPath(),generatedImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
    return generatedImage;
  }
}
