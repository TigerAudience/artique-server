package com.artique.api.member;

import com.artique.api.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
  @Query("select m from Member m where m.nickname = :nickname")
  List<Member> findMemberByNickname(@Param("nickname")String nickname);

  @Modifying
  @Query(value = "CALL delete_member(:member_id)", nativeQuery = true)
  void deleteMemberByProcedure(@Param(value = "member_id")String memberId);
}
