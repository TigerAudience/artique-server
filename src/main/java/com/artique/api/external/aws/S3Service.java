package com.artique.api.external.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class S3Service {
  private final AmazonS3 amazonS3Client;
  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  public String upload(File uploadFile, String dirName) {
    String url;
    try {
      String fileName = dirName + "/" + uploadFile.getName();
      url = putS3(uploadFile, fileName);
      removeNewFile(uploadFile);  //로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
    }catch (Exception e){
      throw new RuntimeException("[file upload failed]"+e.getMessage());
    }
    return url; // 업로드된 파일의 S3 URL 주소 반환
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
}
