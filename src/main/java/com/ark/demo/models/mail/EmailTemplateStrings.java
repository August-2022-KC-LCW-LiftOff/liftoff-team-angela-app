package com.ark.demo.models.mail;

public class EmailTemplateStrings {
    private String requestConfirmationEmail = """
            <!DOCTYPE html>
            <html lang="en" xmlns:th="https://www.thymeleaf.org">
            <head>
                <style>
                                .label {text-align:right;}
                                .value {text-align:left;}
                                tr:nth-child(odd){background:rgb(127,127,127);}
                                </style>
            </head>
            <body style="text-align:center;">
            <div>
                <table>
                    <tr>
                        <td style="background:black;color:white;" colspan=3>
                            ARK
                        </td>
                    <tr>
                    <tr>
                        <td colspan=3>
                            <h1>A new request has been created!</h1>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=3>
                            <h3>Request Details</h3>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">title:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                    <tr>
                        <td class="label">public:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                    <tr>
                        <td class="label">status:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                    <tr>
                        <td class="label">due date:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                    <tr>
                        <td class="label">address line 1:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                    <tr>
                        <td class="label">address line 2:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                    <tr>
                        <td class="label">city:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                    <tr>
                        <td class="label">state:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                    <tr>
                        <td class="label">zipcode:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                    <tr>
                        <td class="label">description:</td>
                        <td colspan=2 class="value">
                            %s
                        </td>
                    </tr>
                </table>
            </div>
            </body>
            </html>
            """;

    private String registrationEmail = """
            <!DOCTYPE html>
            <html lang="en" xmlns:th="http;//www.thymeleaf.org">
            <head>
                <style>
                    td {text-align:center;}
                    .button{
                      width: 150px;
                      height:75px;
                      background:rgb(0,1,213);
                      }
                     a{
                       color: rgb(255,255,255);
                       font-size:36px;
                       text-decoration: none;
                     }
                     em{
                       font-size: 10px;
                     }
                </style>
            </head>
            <body style="text-align:center;">
            <div>
                <table>
                    <tr>
                        <td style="background:black;color:white;" colspan=3>
                            ARK
                        </td>
                    <tr>
                    <tr>
                        <td colspan=3>
                            <h1>Thank you for registering with <img src='http://216.137.189.96:8080/ark/images/arkheartsmall.png'>ARK!</h1>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=3>
                            <h3>Click the button below to verify your email address.</h3>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="button">
                            <a href="http://216.137.189.96:8080/ark/mail?uid=%s">Verify</a>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan=3>
                            <em>You are receiving this email because this email address was used to register a new account at ARK - Acts of Random Kindness.  If you did not request this email, you can delete it and no further action is required.  If you are not the intended recipient of this email, please delete immediately.</em>
                        </td>
                    </tr>
                </table>
            </div>
            </body>
            </html>
            """;

    private String gratitudeReceivedEmail = """
            <!DOCTYPE html>
            <html lang="en" xmlns:th="http;//www.thymeleaf.org">
            <head>
                <style>
                    td {text-align:center;}
                    .button{
                      width: 150px;
                      height:75px;
                      background:rgb(0,1,213);
                      }
                     a{
                       color: rgb(255,255,255);
                       font-size:36px;
                       text-decoration: none;
                     }
                     em{
                       font-size: 10px;
                     }
                </style>
            </head>
            <body style="text-align:center;">
            <div>
                <table>
                    <tr>
                        <td style="background:black;color:white;" colspan=3>
                            ARK
                        </td>
                    <tr>
                    <tr>
                        <td colspan=3>
                            <h1>You're AWESOME!</h1>
                            <h3>A user has sent you gratitude!</h3>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=3>
                            <h3>Click the button below to login and view the gratitude.</h3>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="button">
                            <a href="http://216.137.189.96:8080/ark/login">Login</a>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan=3>
                            <em>You are receiving this email because a user has sent you gratitude.</em>
                        </td>
                    </tr>
                </table>
            </div>
            </body>
            </html>
            """;

    private String responseReceivedEmail = """
            <!DOCTYPE html>
            <html lang="en" xmlns:th="http;//www.thymeleaf.org">
            <head>
                <style>
                    td {text-align:center;}
                    .button{
                      width: 150px;
                      height:75px;
                      background:rgb(0,1,213);
                      }
                     a{
                       color: rgb(255,255,255);
                       font-size:36px;
                       text-decoration: none;
                     }
                     em{
                       font-size: 10px;
                     }
                </style>
            </head>
            <body style="text-align:center;">
            <div>
                <table>
                    <tr>
                        <td style="background:black;color:white;" colspan=3>
                            ARK
                        </td>
                    <tr>
                    <tr>
                        <td colspan=3>
                            <h1>New Response Received</h1>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=3>
                            <h3>Click the button below to login and view the response.</h3>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="button">
                            <a href="http://216.137.189.96:8080/ark/login">Login</a>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan=3>
                            <em>You are receiving this email because a user has responded to your request.</em>
                        </td>
                    </tr>
                </table>
            </div>
            </body>
            </html>
            """;

    private String updateEmailAddressEmail = """
            <!DOCTYPE html>
            <html lang="en" xmlns:th="http;//www.thymeleaf.org">
            <head>
                <style>
                    td {text-align:center;}
                    .button{
                      width: 150px;
                      height:75px;
                      background:rgb(0,1,213);
                      }
                     a{
                       color: rgb(255,255,255);
                       font-size:36px;
                       text-decoration: none;
                     }
                     em{
                       font-size: 10px;
                     }
                </style>
            </head>
            <body style="text-align:center;">
            <div>
                <table>
                    <tr>
                        <td style="background:black;color:white;" colspan=3>
                            ARK
                        </td>
                    <tr>
                    <tr>
                        <td colspan=3>
                            <h1>Thank you for updating your e-mail address with <img src='http://216.137.189.96:8080/ark/images/arkheartsmall.png'>ARK!</h1>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=3>
                            <h3>Click the button below to verify your updated email address.</h3>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="button">
                            <a href="http://216.137.189.96:8080/ark/mail?uid=%s">Verify</a>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan=3>
                            <em>You are receiving this email because this email address was added to an account at ARK - Acts of Random Kindness.  If you did not request this email, you can delete it and no further action is required.  If you are not the intended recipient of this email, please delete immediately.</em>
                        </td>
                    </tr>
                </table>
            </div>
            </body>
            </html>
            """;
    public String getRequestConfirmationEmail() {
        return requestConfirmationEmail;
    }

    public String getRegistrationEmail() {
        return registrationEmail;
    }

    public String getGratitudeReceivedEmail() {
        return gratitudeReceivedEmail;
    }

    public String getResponseReceivedEmail() {
        return responseReceivedEmail;
    }

    public String getUpdateEmailAddressEmail() {
        return updateEmailAddressEmail;
    }
}
