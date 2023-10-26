package com.artique.api.profile.edit;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.artique.api.entity.Member;
import com.artique.api.external.aws.S3Service;
import com.artique.api.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
  private final S3Service s3Service;
  private final MemberRepository memberRepository;

  @Transactional
  public String changeImage(MultipartFile multipartFile, String memberId){
    String dirName = memberId;
    Member member = validateMember(memberId);
    File uploadFile;
    try {
      uploadFile = convert(multipartFile).orElseThrow(()->new RuntimeException("fail"));
    }catch (Exception e){
      throw new RuntimeException("file convert failed");
    }
    String url=s3Service.upload(uploadFile,dirName);
    member.changeImage(url);
    return url;
  }
  private Member validateMember(String memberId){
    return memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("invalid member"));
  }

  private Optional<File> convert(MultipartFile file) throws IOException {
    File convertFile = new File(UUID.randomUUID()+"@"+Objects.requireNonNull(file.getOriginalFilename()));
    if(convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }
}
