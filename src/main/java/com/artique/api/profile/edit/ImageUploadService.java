package com.artique.api.profile.edit;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.artique.api.entity.Member;
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
  private final AmazonS3 amazonS3Client;
  private final MemberRepository memberRepository;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

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
    String url;
    try {
      url = upload(uploadFile,dirName);
    }catch (Exception e){
      throw new RuntimeException("file upload failed");
    }
    member.changeImage(url);
    return url;
  }
  private Member validateMember(String memberId){
    return memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("invalid member"));
  }

  private String upload(File uploadFile, String dirName) {
    String fileName = dirName + "/" + uploadFile.getName();

    String url = putS3(uploadFile, fileName);
    removeNewFile(uploadFile);  //로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

    return url;      // 업로드된 파일의 S3 URL 주소 반환
  }
  private void removeNewFile(File uploadFile){
    uploadFile.delete();
  }

  private String putS3(File uploadFile, String fileName) {
    amazonS3Client.putObject(
            new PutObjectRequest(bucket, fileName, uploadFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
    );
    return amazonS3Client.getUrl(bucket, fileName).toString();
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
