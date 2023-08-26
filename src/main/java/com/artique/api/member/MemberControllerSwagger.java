package com.artique.api.member;

import com.artique.api.exception.ErrorResponse;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.request.LoginMemberReq;
import com.artique.api.member.request.OauthMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.LoginMember;
import com.artique.api.member.response.MemberDuplicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
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
  MemberDuplicate checkDuplicateMember(@RequestParam("member-id")String memberId);

  @Operation(summary = "회원 가입 API", description = "회원 가입 API입니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(schema = @Schema(implementation = JoinMember.class))),
          @ApiResponse(responseCode = "403", description = "duplicated member",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  JoinMember join(@RequestBody JoinMemberReq memberReq);


  @Operation(summary = "로그인 API", description = "로그인 API입니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(schema = @Schema(implementation = LoginMember.class))),
          @ApiResponse(responseCode = "INVALID_PASSWORD(403)", description = "wrong password",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "INVALID_MEMBER_ID(403)", description = "invalid member id",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  LoginMember login(@RequestBody LoginMemberReq memberReq, HttpServletResponse response);


  @PostMapping("/member/oauth")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
          content = @Content(schema = @Schema(implementation = LoginMember.class))),
          @ApiResponse(responseCode = "CANT_FIND_OAUTH_MEMBER(403)", description = "cannot find oauth member in server",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "INVALID_THIRD_PARTY_NAME(403)", description = "invalid third party name",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "INVALID_JWT(403)", description = "invalid jwt",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  LoginMember oauth(@RequestBody OauthMemberReq memberReq, HttpServletResponse response);
}
