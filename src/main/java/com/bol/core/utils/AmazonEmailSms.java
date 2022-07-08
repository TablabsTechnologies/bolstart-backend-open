package com.bol.core.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AmazonEmailSms {


	
    
   

    public boolean sendMailUsingAws(String TO, String SUBJECT,String MESSAGE) throws Exception {

    	// Replace sender@example.com with your "From" address.
        // This address must be verified.
        final String FROM = "reachout@bolstart.com";
        
         final String FROMNAME = "Bolstart Notifications";
         
         final String BODY = StringUtils.join( new String[] {
   			  "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
   					  "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n" + 
   					  "<head>\r\n" + 
   					  "\r\n" + 
   					  "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
   					  "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
   					  "  <meta name=\"x-apple-disable-message-reformatting\">\r\n" + 
   					  "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n" + 
   					  "  <title>Bolstart</title>\r\n" + 
   					  "  \r\n" + 
   					  "    <style type=\"text/css\">\r\n" + 
   					  "      table, td { color: #000000; } a { color: #0000ee; text-decoration: underline; } @media (max-width: 480px) { #u_content_text_2 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_5 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_4 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_3 .v-container-padding-padding { padding: 10px 10px 0px !important; } }\r\n" + 
   					  "@media only screen and (min-width: 620px) {\r\n" + 
   					  "  .u-row {\r\n" + 
   					  "    width: 600px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-row .u-col {\r\n" + 
   					  "    vertical-align: top;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-25 {\r\n" + 
   					  "    width: 230px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-33p33 {\r\n" + 
   					  "    width: 199.98px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-50 {\r\n" + 
   					  "    width: 300px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-66p67 {\r\n" + 
   					  "    width: 400.02px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-100 {\r\n" + 
   					  "    width: 600px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "@media (max-width: 620px) {\r\n" + 
   					  "  .u-row-container {\r\n" + 
   					  "    max-width: 100% !important;\r\n" + 
   					  "    padding-left: 0px !important;\r\n" + 
   					  "    padding-right: 0px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-row .u-col {\r\n" + 
   					  "    min-width: 320px !important;\r\n" + 
   					  "    max-width: 100% !important;\r\n" + 
   					  "    display: block !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-row {\r\n" + 
   					  "    width: calc(100% - 40px) !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-col {\r\n" + 
   					  "    width: 100% !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-col > div {\r\n" + 
   					  "    margin: 0 auto;\r\n" + 
   					  "  }\r\n" + 
   					  "}\r\n" + 
   					  "body {\r\n" + 
   					  "  margin: 0;\r\n" + 
   					  "  padding: 0;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "table,\r\n" + 
   					  "tr,\r\n" + 
   					  "td {\r\n" + 
   					  "  vertical-align: top;\r\n" + 
   					  "  border-collapse: collapse;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "p {\r\n" + 
   					  "  margin: 0;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  ".ie-container table,\r\n" + 
   					  ".mso-container table {\r\n" + 
   					  "  table-layout: fixed;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "* {\r\n" + 
   					  "  line-height: inherit;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "a[x-apple-data-detectors='true'] {\r\n" + 
   					  "  color: inherit !important;\r\n" + 
   					  "  text-decoration: none !important;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "@media (max-width: 480px) {\r\n" + 
   					  "  .hide-desktop {\r\n" + 
   					  "    display: block !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  table.hide-desktop {\r\n" + 
   					  "    display: table !important;\r\n" + 
   					  "  }\r\n" + 
   					  "}\r\n" + 
   					  "    </style>\r\n" + 
   					  "  \r\n" + 
   					  "  \r\n" + 
   					  "\r\n" + 
   					  "</head>\r\n" + 
   					  "\r\n" + 
   					  "<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #e7e7e7;color: #000000\">\r\n" + 
   					  "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\r\n" + 
   					  "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n" + 
   					  "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #e7e7e7;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "  <tr style=\"vertical-align: top\">\r\n" + 
   					  "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #e7e7e7;\"><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #293266;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #293266;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/logo.png\" alt=\"Logo\" title=\"Logo\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 189px;\" width=\"189\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #293266;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #293266;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #293266;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:15px 35px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h1 style=\"margin: 0px; color: #ffffff; line-height: 140%; text-align: left; word-wrap: break-word; font-weight: normal; font-family: arial black,avant garde,arial; font-size: 25px;\">\r\n" + 
   					  "    Welcome<br />to the Bolstart\r\n" + 
   					  "  </h1>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 35px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">Bolstart is a social network that brings the entire startup ecosystem together on one single platform.\r\n" + 
   					  "\r\n" + 
   					  "    </p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #293266;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 35px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<div align=\"left\">\r\n" + 
   					  "  <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:arial,helvetica,sans-serif;\"><tr><td style=\"font-family:arial,helvetica,sans-serif;\" align=\"left\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://unlayer.com/\" style=\"height:37px; v-text-anchor:middle; width:146px;\" arcsize=\"11%\" stroke=\"f\" fillcolor=\"#df6981\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:arial,helvetica,sans-serif;\"><![endif]-->\r\n" + 
   					  "    <a href=\""+MESSAGE+"\""+" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;font-family:arial,helvetica,sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #df6981; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;\">\r\n" + 
   					  "      <span style=\"display:block;padding:10px 20px;line-height:120%;\">Verify now!</span>\r\n" + 
   					  "    </a>\r\n" + 
   					  "  <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-16.png\" alt=\"Illustration\" title=\"Illustration\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 280px;\" width=\"280\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #293266;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #293266;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-15.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 600px;\" width=\"600\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:20px 10px 10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"400\" style=\"width: 400px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-66p67\" style=\"max-width: 320px;min-width: 400px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h4 style=\"margin: 0px; color: #df6981; line-height: 140%; text-align: left; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 16px;\">\r\n" + 
   					  "   Bolstering the Startup Society\r\n" + 
   					  "  </h4>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"43%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #df6981;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h1 style=\"margin: 0px; color: #000000; line-height: 140%; text-align: left; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 22px;\">\r\n" + 
   					  "    Reach Out to Startup Founders, Investors, Mentors, Freelancers and Students on Bolstart.\r\n" + 
   					  "  </h1>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"200\" style=\"width: 200px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-33p33\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 20px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]--><!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<div align=\"center\">\r\n" + 
   					  "  <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:arial,helvetica,sans-serif;\"><tr><td style=\"font-family:arial,helvetica,sans-serif;\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://unlayer.com/\" style=\"height:37px; v-text-anchor:middle; width:142px;\" arcsize=\"11%\" stroke=\"f\" fillcolor=\"#293266\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:arial,helvetica,sans-serif;\"><![endif]-->\r\n" + 
   					  "    <a href=\"http://www.bolstart.com\" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;font-family:arial,helvetica,sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #293266; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;\">\r\n" + 
   					  "      <span style=\"display:block;padding:10px 20px;line-height:120%;\">View all features</span>\r\n" + 
   					  "    </a>\r\n" + 
   					  "  <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"150\" style=\"width: 150px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-25\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-5.png\" alt=\"Icon\" title=\"Icon\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 48px;\" width=\"48\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h2 style=\"margin: 0px; line-height: 140%; text-align: center; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 18px;\">\r\n" + 
   					  "    Network & Connect\r\n" + 
   					  "  </h2>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table id=\"u_content_text_2\" style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 2px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">Build professional relations by connecting with your startup community on Bolstart.</p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"150\" style=\"width: 150px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-30\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-6.png\" alt=\"Icon\" title=\"Icon\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 48px;\" width=\"48\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h2 style=\"margin: 0px; line-height: 140%; text-align: center; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 18px;\">\r\n" + 
   					  "    Weekly Updates.\r\n" + 
   					  "  </h2>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table id=\"u_content_text_5\" style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 2px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">Keep your team, stakeholders and potential investors updated with beautiful weekly updates.</p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"150\" style=\"width: 150px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-30\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-11.png\" alt=\"Icon\" title=\"Icon\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 48px;\" width=\"48\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h2 style=\"margin: 0px; line-height: 140%; text-align: center; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 18px;\">\r\n" + 
   					  "   Redeem Perks\r\n" + 
   					  "  </h2>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table id=\"u_content_text_4\" style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 2px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">Redeem free Credits for AWS, Razorpay, CometChat and many more to boost your startup.</p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"150\" style=\"width: 150px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-30\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-13.png\" alt=\"Illustration\" title=\"Illustration\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 300px;\" width=\"300\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h1 style=\"margin: 0px; color: #293266; line-height: 140%; text-align: left; word-wrap: break-word; font-weight: normal; font-family: arial black,avant garde,arial; font-size: 22px;\">\r\n" + 
   					  "    What do we offer?\r\n" + 
   					  "  </h1>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n" + 
   					  "    <ul style=\"list-style-type: square;\">\r\n" + 
   					  "<li style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"font-size: 16px; line-height: 22.4px;\">Social Network</span></li>\r\n" + 
   					  "<li style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"font-size: 16px; line-height: 22.4px;\">Weekly Updates Management</span></li>\r\n" + 
   					  "<li style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"font-size: 16px; line-height: 22.4px;\">Startup Perks</span></li>\r\n" + 
   					  "\r\n" + 
   					  "</ul>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n" + 
   					  "    <a href=\"http://bolstart.com/\"><p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 12px; line-height: 16.8px;\">Want to try it out? Register and get started now!</span></p>\r\n" + 
   					  " </a> </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 20px 0px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 0px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 20px 0px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 0px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #293266;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #293266;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">reachout@bolstart.com</p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"text-decoration: underline; font-size: 14px; line-height: 19.6px;\">Update</span> | <span style=\"text-decoration: underline; font-size: 14px; line-height: 19.6px;\">Unsubscribe</span></p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 10px 10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<div align=\"center\">\r\n" + 
   					  "  <div style=\"display: table; max-width:209px;\">\r\n" + 
   					  "  <!--[if (mso)|(IE)]><table width=\"209\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"border-collapse:collapse;\" align=\"center\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; mso-table-lspace: 0pt;mso-table-rspace: 0pt; width:209px;\"><tr><![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 10px\">\r\n" + 
   					  "      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "        <a href=\"https://www.facebook.com/bolstart/\" title=\"Facebook\" target=\"_blank\">\r\n" + 
   					  "          <img src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-1.png\" alt=\"Facebook\" title=\"Facebook\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
   					  "        </a>\r\n" + 
   					  "      </td></tr>\r\n" + 
   					  "    </tbody></table>\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 10px\">\r\n" + 
   					  "      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "        <a href=\"https://twitter.com/bolstart\" title=\"Twitter\" target=\"_blank\">\r\n" + 
   					  "          <img src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-2.png\" alt=\"Twitter\" title=\"Twitter\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
   					  "        </a>\r\n" + 
   					  "      </td></tr>\r\n" + 
   					  "    </tbody></table>\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 10px\">\r\n" + 
   					  "      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "        <a href=\"https://www.linkedin.com/company/bolstart?originalSubdomain=in\" title=\"LinkedIn\" target=\"_blank\">\r\n" + 
   					  "          <img src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-3.png\" alt=\"LinkedIn\" title=\"LinkedIn\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
   					  "        </a>\r\n" + 
   					  "      </td></tr>\r\n" + 
   					  "    </tbody></table>\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 10px\">\r\n" + 
   					  "      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "        <a href=\"https://www.instagram.com/bolstart_/\" title=\"Instagram\" target=\"_blank\">\r\n" + 
   					  "          <img src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-4.png\" alt=\"Instagram\" title=\"Instagram\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
   					  "        </a>\r\n" + 
   					  "      </td></tr>\r\n" + 
   					  "    </tbody></table>\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "   \r\n" + 
   					  "    \r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "  <!--  <p style=\"font-size: 14px; line-height: 140%;\">xxxx South, San Francisco, CA</p> -->\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "  <!--[if mso]></div><![endif]-->\r\n" + 
   					  "  <!--[if IE]></div><![endif]-->\r\n" + 
   					  "</body>\r\n" + 
   					  "\r\n" + 
   					  "</html>\r\n" + 
   					  "" } );
    	
        // Replace recipient@example.com with a "To" address. If your account 
        // is still in the sandbox, this address must be verified.
        // final String TO = "sachinparmar.7393@gmail.com";
        
        // Replace smtp_username with your Amazon SES SMTP user name.
         final String SMTP_USERNAME = "AKIATVIMWQHEQHP2XQCX";
        
        // Replace smtp_password with your Amazon SES SMTP password.
         final String SMTP_PASSWORD = "BEKt5Kk+nrG+88jLAlIb6efRLiabeqG8wV7X9ok2TTiD";
        
        // The name of the Configuration Set to use for this message.
        // If you comment out or remove this variable, you will also need to
        // comment out or remove the header below.
       // static final String CONFIGSET = "ConfigSet";
        
        // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
        // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
        // for more information.
         final String HOST = "email-smtp.ap-south-1.amazonaws.com";
        
        // The port you will connect to on the Amazon SES SMTP endpoint. 
         final int PORT = 587;
        
        //final String SUBJECT = "Jai Shree Krishna Amazon SES test (SMTP interface accessed using Java)";
        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(StringEscapeUtils.unescapeJava(BODY), "text/html; charset=UTF-8");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
     //   msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
            return true;
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            return false;
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
	
    
    public boolean sendAwsMailAfterRegistration(String TO, String SUBJECT) throws Exception {

    	// Replace sender@example.com with your "From" address.
        // This address must be verified.
        final String FROM = "reachout@bolstart.com";
        
         final String FROMNAME = "Bolstart Notifications";
         
         final String BODY = StringUtils.join( new String[] {
   			  "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
   					  "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n" + 
   					  "<head>\r\n" + 
   					  "\r\n" + 
   					  "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
   					  "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
   					  "  <meta name=\"x-apple-disable-message-reformatting\">\r\n" + 
   					  "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n" + 
   					  "  <title>Bolstart</title>\r\n" + 
   					  "  \r\n" + 
   					  "    <style type=\"text/css\">\r\n" + 
   					  "      table, td { color: #000000; } a { color: #0000ee; text-decoration: underline; } @media (max-width: 480px) { #u_content_text_2 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_5 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_4 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_3 .v-container-padding-padding { padding: 10px 10px 0px !important; } }\r\n" + 
   					  "@media only screen and (min-width: 620px) {\r\n" + 
   					  "  .u-row {\r\n" + 
   					  "    width: 600px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-row .u-col {\r\n" + 
   					  "    vertical-align: top;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-25 {\r\n" + 
   					  "    width: 230px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-33p33 {\r\n" + 
   					  "    width: 199.98px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-50 {\r\n" + 
   					  "    width: 300px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-66p67 {\r\n" + 
   					  "    width: 400.02px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-100 {\r\n" + 
   					  "    width: 600px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "@media (max-width: 620px) {\r\n" + 
   					  "  .u-row-container {\r\n" + 
   					  "    max-width: 100% !important;\r\n" + 
   					  "    padding-left: 0px !important;\r\n" + 
   					  "    padding-right: 0px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-row .u-col {\r\n" + 
   					  "    min-width: 320px !important;\r\n" + 
   					  "    max-width: 100% !important;\r\n" + 
   					  "    display: block !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-row {\r\n" + 
   					  "    width: calc(100% - 40px) !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-col {\r\n" + 
   					  "    width: 100% !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-col > div {\r\n" + 
   					  "    margin: 0 auto;\r\n" + 
   					  "  }\r\n" + 
   					  "}\r\n" + 
   					  "body {\r\n" + 
   					  "  margin: 0;\r\n" + 
   					  "  padding: 0;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "table,\r\n" + 
   					  "tr,\r\n" + 
   					  "td {\r\n" + 
   					  "  vertical-align: top;\r\n" + 
   					  "  border-collapse: collapse;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "p {\r\n" + 
   					  "  margin: 0;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  ".ie-container table,\r\n" + 
   					  ".mso-container table {\r\n" + 
   					  "  table-layout: fixed;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "* {\r\n" + 
   					  "  line-height: inherit;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "a[x-apple-data-detectors='true'] {\r\n" + 
   					  "  color: inherit !important;\r\n" + 
   					  "  text-decoration: none !important;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "@media (max-width: 480px) {\r\n" + 
   					  "  .hide-desktop {\r\n" + 
   					  "    display: block !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  table.hide-desktop {\r\n" + 
   					  "    display: table !important;\r\n" + 
   					  "  }\r\n" + 
   					  "}\r\n" + 
   					  "    </style>\r\n" + 
   					  "  \r\n" + 
   					  "  \r\n" + 
   					  "\r\n" + 
   					  "</head>\r\n" + 
   					  "\r\n"
   					  ,"<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #e7e7e7;color: #000000\">\r\n" + 
   					  "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\r\n" + 
   					  "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n" + 
   					  "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #e7e7e7;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "  <tr style=\"vertical-align: top\">\r\n" + 
   					  "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #e7e7e7;\"><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #293266;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #293266;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/logo.png\" alt=\"Logo\" title=\"Logo\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 189px;\" width=\"189\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #293266;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #293266;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #293266;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:15px 35px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h1 style=\"margin: 0px; color: #ffffff; line-height: 140%; text-align: left; word-wrap: break-word; font-weight: normal; font-family: arial black,avant garde,arial; font-size: 25px;\">\r\n" + 
   					  "    Welcome<br />to the Bolstart\r\n" + 
   					  "  </h1>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" 
   					  ,"<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 35px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">Bolstart is a social network that brings the entire startup ecosystem together on one single platform.\r\n" + 
   					  "\r\n" + 
   					  "    </p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #293266;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 35px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<div align=\"left\">\r\n" + 
   					  "  <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:arial,helvetica,sans-serif;\"><tr><td style=\"font-family:arial,helvetica,sans-serif;\" align=\"left\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://unlayer.com/\" style=\"height:37px; v-text-anchor:middle; width:146px;\" arcsize=\"11%\" stroke=\"f\" fillcolor=\"#df6981\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:arial,helvetica,sans-serif;\"><![endif]-->\r\n" + 
   					  "    <a href=\"http://www.bolstart.com\" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;font-family:arial,helvetica,sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #df6981; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;\">\r\n" + 
   					  "      <span style=\"display:block;padding:10px 20px;line-height:120%;\">Get Started now!</span>\r\n" + 
   					  "    </a>\r\n" + 
   					  "  <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-16.png\" alt=\"Illustration\" title=\"Illustration\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 280px;\" width=\"280\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" 
   					  ,"<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #293266;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #293266;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-15.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 600px;\" width=\"600\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:20px 10px 10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" 
   					  ,"<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"400\" style=\"width: 400px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-66p67\" style=\"max-width: 320px;min-width: 400px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h4 style=\"margin: 0px; color: #df6981; line-height: 140%; text-align: left; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 16px;\">\r\n" + 
   					  "   Bolstering the Startup Society\r\n" + 
   					  "  </h4>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"43%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #df6981;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h1 style=\"margin: 0px; color: #000000; line-height: 140%; text-align: left; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 22px;\">\r\n" + 
   					  "    Reach Out to Startup Founders, Investors, Mentors, Freelancers and Students on Bolstart.\r\n" + 
   					  "  </h1>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"200\" style=\"width: 200px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-33p33\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 20px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]--><!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<div align=\"center\">\r\n" + 
   					  "  <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:arial,helvetica,sans-serif;\"><tr><td style=\"font-family:arial,helvetica,sans-serif;\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://unlayer.com/\" style=\"height:37px; v-text-anchor:middle; width:142px;\" arcsize=\"11%\" stroke=\"f\" fillcolor=\"#293266\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:arial,helvetica,sans-serif;\"><![endif]-->\r\n" + 
   					  "    <a href=\"http://www.bolstart.com\" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;font-family:arial,helvetica,sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #293266; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;\">\r\n" + 
   					  "      <span style=\"display:block;padding:10px 20px;line-height:120%;\">View all features</span>\r\n" + 
   					  "    </a>\r\n" + 
   					  "  <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" 
   					  ,"<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"150\" style=\"width: 150px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-25\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-5.png\" alt=\"Icon\" title=\"Icon\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 48px;\" width=\"48\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h2 style=\"margin: 0px; line-height: 140%; text-align: center; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 18px;\">\r\n" + 
   					  "    Network & Connect\r\n" + 
   					  "  </h2>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table id=\"u_content_text_2\" style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 2px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">Build professional relations by connecting with your startup community on Bolstart.</p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"150\" style=\"width: 150px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-30\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-6.png\" alt=\"Icon\" title=\"Icon\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 48px;\" width=\"48\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h2 style=\"margin: 0px; line-height: 140%; text-align: center; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 18px;\">\r\n" + 
   					  "    Weekly Updates.\r\n" + 
   					  "  </h2>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table id=\"u_content_text_5\" style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 2px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">Keep your team, stakeholders and potential investors updated with beautiful weekly updates.</p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"150\" style=\"width: 150px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-30\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" 
   					  ,"<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-11.png\" alt=\"Icon\" title=\"Icon\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 48px;\" width=\"48\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h2 style=\"margin: 0px; line-height: 140%; text-align: center; word-wrap: break-word; font-weight: normal; font-family: arial,helvetica,sans-serif; font-size: 18px;\">\r\n" + 
   					  "   Redeem Perks\r\n" + 
   					  "  </h2>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table id=\"u_content_text_4\" style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 2px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">Redeem free Credits for AWS, Razorpay, CometChat and many more to boost your startup.</p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"150\" style=\"width: 150px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-30\" style=\"max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
   					  "  <tr>\r\n" + 
   					  "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n" + 
   					  "      \r\n" + 
   					  "      <img align=\"center\" border=\"0\" src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-13.png\" alt=\"Illustration\" title=\"Illustration\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 300px;\" width=\"300\"/>\r\n" + 
   					  "      \r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <h1 style=\"margin: 0px; color: #293266; line-height: 140%; text-align: left; word-wrap: break-word; font-weight: normal; font-family: arial black,avant garde,arial; font-size: 22px;\">\r\n" + 
   					  "    What do we offer?\r\n" + 
   					  "  </h1>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n" + 
   					  "    <ul style=\"list-style-type: square;\">\r\n" + 
   					  "<li style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"font-size: 16px; line-height: 22.4px;\">Social Network</span></li>\r\n" + 
   					  "<li style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"font-size: 16px; line-height: 22.4px;\">Weekly Updates Management</span></li>\r\n" + 
   					  "<li style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"font-size: 16px; line-height: 22.4px;\">Startup Perks</span></li>\r\n" + 
   					  "\r\n" + 
   					  "</ul>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n"
   					  ,"<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\r\n" + 
   					  "    <a href=\"http://bolstart.com/\"><p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 12px; line-height: 16.8px;\">Want to try it out? Register and get started now!</span></p>\r\n" + 
   					  " </a> </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 20px 0px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 0px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 20px 0px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"background-color: #ffffff;width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 0px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  <!--[if !mso]><!-->\r\n" + 
   					  "<table class=\"hide-desktop\" style=\"display: none;mso-hide: all;font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #ffffff;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "    <tbody>\r\n" + 
   					  "      <tr style=\"vertical-align: top\">\r\n" + 
   					  "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n" + 
   					  "          <span>&#160;</span>\r\n" + 
   					  "        </td>\r\n" + 
   					  "      </tr>\r\n" + 
   					  "    </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "<!--<![endif]-->\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n" + 
   					  "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #293266;\">\r\n" + 
   					  "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n" + 
   					  "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #293266;\"><![endif]-->\r\n" + 
   					  "      \r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 10px 0px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\">reachout@bolstart.com</p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"text-decoration: underline; font-size: 14px; line-height: 19.6px;\">Update</span> | <span style=\"text-decoration: underline; font-size: 14px; line-height: 19.6px;\">Unsubscribe</span></p>\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\r\n" + 
   					  "  <div style=\"width: 100% !important;\">\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 10px 10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "<div align=\"center\">\r\n" + 
   					  "  <div style=\"display: table; max-width:209px;\">\r\n" + 
   					  "  <!--[if (mso)|(IE)]><table width=\"209\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"border-collapse:collapse;\" align=\"center\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; mso-table-lspace: 0pt;mso-table-rspace: 0pt; width:209px;\"><tr><![endif]-->\r\n" + 
   					  "  \r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 10px\">\r\n" + 
   					  "      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "        <a href=\"https://www.facebook.com/bolstart/\" title=\"Facebook\" target=\"_blank\">\r\n" + 
   					  "          <img src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-1.png\" alt=\"Facebook\" title=\"Facebook\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
   					  "        </a>\r\n" + 
   					  "      </td></tr>\r\n" + 
   					  "    </tbody></table>\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 10px\">\r\n" + 
   					  "      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "        <a href=\"https://twitter.com/bolstart\" title=\"Twitter\" target=\"_blank\">\r\n" + 
   					  "          <img src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-2.png\" alt=\"Twitter\" title=\"Twitter\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
   					  "        </a>\r\n" + 
   					  "      </td></tr>\r\n" + 
   					  "    </tbody></table>\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 10px\">\r\n" + 
   					  "      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "        <a href=\"https://www.linkedin.com/company/bolstart?originalSubdomain=in\" title=\"LinkedIn\" target=\"_blank\">\r\n" + 
   					  "          <img src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-3.png\" alt=\"LinkedIn\" title=\"LinkedIn\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
   					  "        </a>\r\n" + 
   					  "      </td></tr>\r\n" + 
   					  "    </tbody></table>\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
   					  "    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 10px\">\r\n" + 
   					  "      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "        <a href=\"https://www.instagram.com/bolstart_/\" title=\"Instagram\" target=\"_blank\">\r\n" + 
   					  "          <img src=\"https://bolstartimages.s3.ap-south-1.amazonaws.com/postimage/image-4.png\" alt=\"Instagram\" title=\"Instagram\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
   					  "        </a>\r\n" + 
   					  "      </td></tr>\r\n" + 
   					  "    </tbody></table>\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "   \r\n" + 
   					  "    \r\n" + 
   					  "    \r\n" + 
   					  "    <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n"
   					  ,"<table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "    <tr>\r\n" + 
   					  "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\" align=\"left\">\r\n" + 
   					  "        \r\n" + 
   					  "  <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n" + 
   					  "  <!--  <p style=\"font-size: 14px; line-height: 140%;\">xxxx South, San Francisco, CA</p> -->\r\n" + 
   					  "  </div>\r\n" + 
   					  "\r\n" + 
   					  "      </td>\r\n" + 
   					  "    </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "</table>\r\n" + 
   					  "\r\n" + 
   					  "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "<!--[if (mso)|(IE)]></td><![endif]-->\r\n" + 
   					  "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n" + 
   					  "    </div>\r\n" + 
   					  "  </div>\r\n" + 
   					  "</div>\r\n" + 
   					  "\r\n" + 
   					  "\r\n" + 
   					  "    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
   					  "    </td>\r\n" + 
   					  "  </tr>\r\n" + 
   					  "  </tbody>\r\n" + 
   					  "  </table>\r\n" + 
   					  "  <!--[if mso]></div><![endif]-->\r\n" + 
   					  "  <!--[if IE]></div><![endif]-->\r\n" + 
   					  "</body>\r\n" + 
   					  "\r\n" + 
   					  "</html>\r\n" + 
   					  "" } );
    	
        // Replace recipient@example.com with a "To" address. If your account 
        // is still in the sandbox, this address must be verified.
        // final String TO = "sachinparmar.7393@gmail.com";
        
        // Replace smtp_username with your Amazon SES SMTP user name.
         final String SMTP_USERNAME = "AKIATVIMWQHEQHP2XQCX";
        
        // Replace smtp_password with your Amazon SES SMTP password.
         final String SMTP_PASSWORD = "BEKt5Kk+nrG+88jLAlIb6efRLiabeqG8wV7X9ok2TTiD";
        
        // The name of the Configuration Set to use for this message.
        // If you comment out or remove this variable, you will also need to
        // comment out or remove the header below.
       // static final String CONFIGSET = "ConfigSet";
        
        // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
        // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
        // for more information.
         final String HOST = "email-smtp.ap-south-1.amazonaws.com";
        
        // The port you will connect to on the Amazon SES SMTP endpoint. 
         final int PORT = 587;
        
        //final String SUBJECT = "Jai Shree Krishna Amazon SES test (SMTP interface accessed using Java)";
        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(StringEscapeUtils.unescapeJava(BODY), "text/html; charset=UTF-8");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
     //   msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
            return true;
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            return false;
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
    
    public boolean sendAwsMailForForgotPassword(String TO, String SUBJECT,String MESSAGE) throws Exception {

    	// Replace sender@example.com with your "From" address.
        // This address must be verified.
        final String FROM = "reachout@bolstart.com";
        
         final String FROMNAME = "Bolstart Notifications";
         
         final String BODY = StringUtils.join( new String[] {
   			  "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
   					  "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n" + 
   					  "<head>\r\n" + 
   					  "\r\n" + 
   					  "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
   					  "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
   					  "  <meta name=\"x-apple-disable-message-reformatting\">\r\n" + 
   					  "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n" + 
   					  "  <title>Bolstart</title>\r\n" + 
   					  "  \r\n" + 
   					  "    <style type=\"text/css\">\r\n" + 
   					  "      table, td { color: #000000; } a { color: #0000ee; text-decoration: underline; } @media (max-width: 480px) { #u_content_text_2 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_5 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_4 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_3 .v-container-padding-padding { padding: 10px 10px 0px !important; } }\r\n" + 
   					  "@media only screen and (min-width: 620px) {\r\n" + 
   					  "  .u-row {\r\n" + 
   					  "    width: 600px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-row .u-col {\r\n" + 
   					  "    vertical-align: top;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-25 {\r\n" + 
   					  "    width: 150px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-33p33 {\r\n" + 
   					  "    width: 199.98px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-50 {\r\n" + 
   					  "    width: 300px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-66p67 {\r\n" + 
   					  "    width: 400.02px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "  .u-row .u-col-100 {\r\n" + 
   					  "    width: 600px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "@media (max-width: 620px) {\r\n" + 
   					  "  .u-row-container {\r\n" + 
   					  "    max-width: 100% !important;\r\n" + 
   					  "    padding-left: 0px !important;\r\n" + 
   					  "    padding-right: 0px !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-row .u-col {\r\n" + 
   					  "    min-width: 320px !important;\r\n" + 
   					  "    max-width: 100% !important;\r\n" + 
   					  "    display: block !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-row {\r\n" + 
   					  "    width: calc(100% - 40px) !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-col {\r\n" + 
   					  "    width: 100% !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  .u-col > div {\r\n" + 
   					  "    margin: 0 auto;\r\n" + 
   					  "  }\r\n" + 
   					  "}\r\n" + 
   					  "body {\r\n" + 
   					  "  margin: 0;\r\n" + 
   					  "  padding: 0;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "table,\r\n" + 
   					  "tr,\r\n" + 
   					  "td {\r\n" + 
   					  "  vertical-align: top;\r\n" + 
   					  "  border-collapse: collapse;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "p {\r\n" + 
   					  "  margin: 0;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  ".ie-container table,\r\n" + 
   					  ".mso-container table {\r\n" + 
   					  "  table-layout: fixed;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "* {\r\n" + 
   					  "  line-height: inherit;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "a[x-apple-data-detectors='true'] {\r\n" + 
   					  "  color: inherit !important;\r\n" + 
   					  "  text-decoration: none !important;\r\n" + 
   					  "}\r\n" + 
   					  "\r\n" + 
   					  "@media (max-width: 480px) {\r\n" + 
   					  "  .hide-desktop {\r\n" + 
   					  "    display: block !important;\r\n" + 
   					  "  }\r\n" + 
   					  "  table.hide-desktop {\r\n" + 
   					  "    display: table !important;\r\n" + 
   					  "  }\r\n" + 
   					  "}\r\n" + 
   					  "    </style>\r\n" + 
   					  "  \r\n" + 
   					  "  \r\n" + 
   					  "\r\n" + 
   					  "</head>\r\n" + 
   					  "\r\n" + 
   					  "<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #e7e7e7;color: #000000\">\r\n" + 
   					  "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\r\n" + 
   					  "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n" + 
   					  "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #e7e7e7;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
   					  "  <tbody>\r\n" + 
   					  "  <tr style=\"vertical-align: top\">\r\n" + 
   					  "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
   					  "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #e7e7e7;\"><![endif]-->\r\n" + 
   					  "    \r\n" + 
   					  "\r\n" + 

   					  "      <span style=\"display:block;padding:10px 20px;line-height:120%;\">"+MESSAGE+"!</span>\r\n" +
   				"" } );
    	
        // Replace recipient@example.com with a "To" address. If your account 
        // is still in the sandbox, this address must be verified.
        // final String TO = "sachinparmar.7393@gmail.com";
        
        // Replace smtp_username with your Amazon SES SMTP user name.
         final String SMTP_USERNAME = "AKIATVIMWQHEQHP2XQCX";
        
        // Replace smtp_password with your Amazon SES SMTP password.
         final String SMTP_PASSWORD = "BEKt5Kk+nrG+88jLAlIb6efRLiabeqG8wV7X9ok2TTiD";
        
        // The name of the Configuration Set to use for this message.
        // If you comment out or remove this variable, you will also need to
        // comment out or remove the header below.
       // static final String CONFIGSET = "ConfigSet";
        
        // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
        // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
        // for more information.
         final String HOST = "email-smtp.ap-south-1.amazonaws.com";
        
        // The port you will connect to on the Amazon SES SMTP endpoint. 
         final int PORT = 587;
        
        //final String SUBJECT = "Jai Shree Krishna Amazon SES test (SMTP interface accessed using Java)";
        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
       // msg.setContent(BODY,"text/html");
        msg.setContent(StringEscapeUtils.unescapeJava(BODY), "text/html; charset=UTF-8");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
     //   msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
            return true;
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            return false;
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
	
	
}











