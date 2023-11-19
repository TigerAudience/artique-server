package com.artique.api.mail.dto;

public class JoinEmailForm {
  public static String getMailBody(int code){
    return "<div class=\"mail_view_body\">\n" +
            "  <div class=\"mail_view_contents\">\n" +
            "    <div class=\"mail_view_contents_inner\" data-translate-body-17661=\"\">\n" +
            "      <div>\n" +
            "        </p>\n" +
            "        <table align=\"center\" width=\"670\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
            "          style=\"border-top: 2px solid #E94A4B; border-right: 1px solid #e7e7e7; border-left: 1px solid #e7e7e7; border-bottom: 1px solid #e7e7e7;\">\n" +
            "          <tbody>\n" +
            "            <tr>\n" +
            "              <td style=\"background-color: #ffffff; padding: 40px 30px 0 35px; text-align: center;\">\n" +
            "                <table width=\"605\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
            "                  style=\"text-align: left; font-family: '맑은 고딕','돋움';\">\n" +
            "                  <tbody>\n" +
            "                    <tr>\n" +
            "                      <td\n" +
            "                        style=\"color: #3A3D52; font-size: 25px; text-align: left; width: 352px; word-spacing: -1px; vertical-align: top;\">\n" +
            "                        인증번호 확인 후<br>\n" +
            "                        이메일 인증을 완료해 주세요.\n" +
            "                      </td>\n" +
            "                      <td rowspan=\"3\"><img style=\"width: 90%;\"\n" +
            "                          src=\"https://user-images.githubusercontent.com/71641610/284042206-fdafc283-296e-45d4-b259-2bafb1ad2060.png\" loading=\"lazy\">\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                      <td height=\"39\">\n" +
            "                        <div style=\"width: 80px;height: 2px; background-color: #3A3D52;\">\n" +
            "                        </div>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                      <td style=\"font-size: 17px; vertical-align: bottom; height: 27px;\">\n" +
            "                        안녕하세요,Artique입니다.</td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                      <td colspan=\"2\" style=\"font-size: 13px; word-spacing: -1px; height: 30px;\">\n" +
            "                        앱에서 아래 인증번호를 입력하시고 회원가입을 완료해주세요!</td>\n" +
            "                    </tr>\n" +
            "                  </tbody>\n" +
            "                </table>\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "              <td style=\"padding: 39px 196px 70px;\">\n" +
            "                <table width=\"278\" style=\"background-color: #3A3D52; font-family: '맑은 고딕','돋움';\">\n" +
            "                  <tbody>\n" +
            "                    <tr>\n" +
            "                      <td height=\"49\" style=\"text-align: center; color: #fff\">인증번호 :\n" +
            "                        <span>"+code+"</span>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                  </tbody>\n" +
            "                </table>\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "          </tbody>\n" +
            "        </table>\n" +
            "\n" +
            "        <p></p>\n" +
            "        <img height=\"1\" width=\"1\" border=\"0\" style=\"display:none;\"\n" +
            "          src=\"http://ems.midasit.com:4121/2I-110098I-41E-8179442057I-4uPmuPzeI-4I-3\" loading=\"lazy\">\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "      </div>\n" +
            "    </div>\n" +
            "  </div>\n" +
            "</div>";
  }
}
