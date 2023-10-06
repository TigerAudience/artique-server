package com.artique.api.member;

import com.artique.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
  @Query("select m from Member m where m.nickname = :nickname limit 1")
  public Optional<Member> findMemberByNickname(@Param("nickname")String nickname);
}
