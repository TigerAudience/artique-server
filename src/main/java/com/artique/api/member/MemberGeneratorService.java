package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.external.aws.S3Service;
import com.artique.api.member.request.JoinMemberReq;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberGeneratorService {
  private final S3Service s3Service;
  private final MemberRepository memberRepository;
  private Queue<String> randomNickname;
  private List<String> animals;
  private List<String> adjectives;
  @PostConstruct
  public void initNickname() throws IOException{
    initAnimalsAndAdjectives();
    List<Member> members = memberRepository.findAll();
    Map<String,String> memberNicknames = new HashMap<>();
    for(Member member:members){
      memberNicknames.put(member.getNickname(),member.getId());
    }
    Queue<String> nicknamesQueue = new LinkedList<>();
    for (String animalName : animals){
      for (String adjective : adjectives){
        if(!memberNicknames.containsKey(adjective+" "+animalName))
          nicknamesQueue.add(adjective+" "+animalName);
      }
    }
    randomNickname = nicknamesQueue;
  }
  private void initAnimalsAndAdjectives() throws IOException{
    animals = readOneLineTextFile("animals.txt");
    adjectives = readOneLineTextFile("adjectives.txt");
  }
  private List<String> readOneLineTextFile(String fileName) throws IOException {
    List<String> result = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String[] line = br.readLine().split(", ");
      result = Arrays.stream(line).toList();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
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
    String selectedNickname;
    int randomPopCount = (int)(Math.random() * 1000);
    if(randomNickname.isEmpty())
      throw new RuntimeException("random nickname pool is empty!");
    for(int i=0;i<randomPopCount;i++){
      String popNickname = randomNickname.peek();
      randomNickname.remove();
      randomNickname.add(popNickname);
    }
    selectedNickname=randomNickname.peek();
    randomNickname.remove();
    return selectedNickname;
  }

  private File generateInitialFile()throws IOException {
    File initialSource = new File("default-image.png");
    String newImageUrl = "default-image"+ UUID.randomUUID()+".png";
    File generatedImage = new File(newImageUrl);
    Files.copy(initialSource.toPath(),generatedImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
    return generatedImage;
  }
}
