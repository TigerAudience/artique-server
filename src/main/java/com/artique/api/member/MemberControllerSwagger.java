package com.artique.api.member;

import com.artique.api.exception.ErrorResponse;
import com.artique.api.member.dto.DuplicateDto;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.MemberDuplicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface MemberControllerSwagger {
  @Operation(summary = "회원 id 중복 조회 API", description = "중복 조회 API입니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(schema = @Schema(implementation = MemberDuplicate.class))),
          @ApiResponse(responseCode = "403", description = "bad request operation",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public MemberDuplicate checkDuplicateMember(@RequestParam("member-id")String memberId);

  @Operation(summary = "회원 가입 API", description = "회원 가입 API입니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(schema = @Schema(implementation = JoinMember.class))),
          @ApiResponse(responseCode = "403", description = "duplicated member",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public JoinMember join(@RequestBody JoinMemberReq memberReq);
}
