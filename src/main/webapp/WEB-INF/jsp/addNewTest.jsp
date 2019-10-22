<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="locale"
       value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="text" var="rb"/>

<fmt:message bundle="${rb}" key="test_list" var="test_list"/>`
<fmt:message bundle="${rb}" key="test.welcome_message" var="test.welcome_message"/>
<fmt:message bundle="${rb}" key="test_id" var="test_id"/>
<fmt:message bundle="${rb}" key="test.page_add_title" var="addPage_title"/>
<fmt:message bundle="${rb}" key="test_title" var="test_title"/>
<fmt:message bundle="${rb}" key="test_description" var="test_description"/>
<fmt:message bundle="${rb}" key="test_subject" var="test_subject_id"/>
<fmt:message bundle="${rb}" key="test_author" var="test_author_id"/>
<fmt:message bundle="${rb}" key="button.test_add" var="test_add"/>
<fmt:message bundle="${rb}" key="test_added_message" var="added_test_success_message"/>

<fmt:message bundle="${rb}" key="registration.enter_login_message" var="login_message"/>
<fmt:message bundle="${rb}" key="registration.enter_password_message" var="password_message"/>

<fmt:message bundle="${rb}" key="button.register" var="button_register"/>
<fmt:message bundle="${rb}" key="button.go_home" var="button_home"/>
<fmt:message bundle="${rb}" key="button.personal_cabinet" var="button_personal_cabinet"/>
<fmt:message bundle="${rb}" key="button.logIn" var="button_login"/>
<fmt:message bundle="${rb}" key="button.logout" var="button_logout"/>


<fmt:message bundle="${rb}" key="message.logged_message" var="logged_message"/>
<fmt:message bundle="${rb}" key="message.logout_message" var="logout_message"/>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${ addPage_title }</title>
</head>
<body>
<div id="header">
        <div class="container">
            <div class="new-select-style-locale">
                <form style="display: inline; margin-left: 20px">
                    <input type="hidden" name="command" value="goToAddNewTest"/>
                    <div class="new-select-style-locale" style="margin-left: 340px; margin-top: -25px; ">
                        <label for="locale"></label>
                        <select id="locale" name="locale" onchange="submit()">
                            <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
                            <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
                        </select>
                    </div>
                </form>
            </div>
        </div>

    <div class="navAndLogin">

        <center>
            <h1>${title_personal_cabinet}</h1>
        </center>

        <nav>
            <c:if test="${user.id != null}">
                <h4>
                   ${logged_message } - ${user.name}(${user.id})
                </h4>

            </c:if>
            <ul class="header" style="margin-right: -23%; margin-top: 3%">
                <c:if test="${user.id != null}">
                    <li>
                        <a href="pages?command=logout">${button_logout}</a>
                    </li>
                    <li>
                        <a href="pages?command=viewPersonalCabinet">${button_personal_cabinet}</a>
                        <input type="hidden" name="userId" value="${user.id}">
                    </li>
                    <li>
                        <a href="pages?command=showAllTests">${test_list}</a>
                    </li>
                </c:if>

                <c:if test="${user.id == null}">

                    <div class="promt_window">
                        <div class="overlay">
                            <div class="login-wrapper">
                                <div class="login-content" id="loginTarget">
                                    <a class="close">x</a>
                                    <h3> ${login_message} </h3>

                                    <form action="pages" method="post">
                                        <label for="login">
                                                ${login_message}
                                            <input type="text" name="login" id="login"
                                                   placeholder="${login_message}" required/>
                                        </label>
                                        <label for="password">
                                                ${password_message}
                                            <input type="password" name="password" id="password"
                                                   placeholder="" required/>
                                        </label>
                                            <%--                                    <input type="hidden" name="command" value="authorization">--%>
                                        <button type="submit" name="command" value="authorization">
                                            <strong>${login_message}</strong>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <c:if test="${signInMessage !=null}">
                            <script>
                                showAlertMessage("<fmt:message key="${signInMessage}"/>");
                            </script>
                        </c:if>
                    </div>
                    <c:remove var="signInMessage"/>
                    <a href="pages?command=goToRegistrationPage"> ${button_register} </a>
                </c:if>
            </ul>


        </nav>

        <%--    <table align="center" width="80%" cellspacing="0" cellpadding="4">--%>
        <%--        <tr>--%>
        <%--            <td align="center" width="70">--%>
        <%--                <form action="pages" method="post">--%>
        <%--                    <input type="hidden" name="command" value="goToShowAllTestsPageCommand"/>--%>
        <%--                    <input type="submit" size="50" value="${button_home}"/>--%>
        <%--                </form>--%>
        <%--            </td>--%>
        <%--            <td align="center" width="70">--%>
        <%--                <form action="pages" method="post">--%>
        <%--                    <input type="hidden" name="command" value="goToRegistrationPage"/>--%>
        <%--                    <input type="submit" value="${button_register}"/>--%>
        <%--                </form>--%>
        <%--            </td>--%>
        <%--            <td align="right" width="70">--%>
        <%--                <form action="pages" method="post">--%>
        <%--                    <input type="hidden" name="command" value="goToLoginPage"/>--%>
        <%--                    <input type="submit" value="${button_login}"/>--%>
        <%--                </form>--%>
        <%--            </td>--%>
        <%--            <td align="right" width="300">--%>
        <%--                <c:if test="${!empty user.id}">--%>
        <%--                    <c:out value="${logged_message } - "/>--%>
        <%--                </c:if>--%>
        <%--            </td>--%>
        <%--            <td align="left" width="200">--%>
        <%--                <c:if test="${!empty user.id}">--%>
        <%--                    ${sessionScope.userName}--%>
        <%--                </c:if>--%>
        <%--            </td>--%>
        <%--            <td align="right" width="70">--%>
        <%--                <form action="pages" method="post">--%>
        <%--                    <c:if test="${!empty user.id}">--%>
        <%--                        <input type="hidden" name="command" value="logOutCommand"/>--%>
        <%--                        <input type="submit" value="${logout_message}"/>--%>
        <%--                    </c:if>--%>
        <%--                </form>--%>
        <%--            </td>--%>
        <%--        </tr>--%>
        <%--    </table>--%>
        <%--    <br>--%>
        <%--    <br>--%>
        <%--    <hr>--%>
    </div>
</div>

<div class="main-content">
    <h1>${addPage_title}</h1>
    <form action="pages" method="post">
        <table width="100%" cellspacing="0" cellpadding="4">
            <tr>
                <td align="right" width="200">${test_title}</td>
                <td><input type="text" size="50" name="testTitle" required/></td>
            </tr>
            <tr>
                <td align="right">${test_description}</td>
                <td><input type="text" size="50" name="testDescription" required/></td>
            </tr>
            <tr>
                <td align="right" title="${test_subject_id}">${test_subject_id}</td>
                <td><input type="text" size="50" name="testSubjectId" pattern="[0-9]" required/></td>
            </tr>
            <%--            <tr>--%>
            <%--                <td align="right" title="${test_author_id}">${test_author_id}</td>--%>
            <%--                <td><input type="text" size="50" name="testAuthorId" pattern="[0-9]" required/></td>--%>
            <%--            </tr>--%>
            <tr>
                <td></td>
                <td>
                    <input type="hidden" name="testAuthorId" value="${user.id}"/>
                    <input type="hidden" name="command" value="addNewTest">
                    <input type="submit" value="${test_add}"/>
                </td>
            </tr>
        </table>
    </form>
    <h3>
        <c:if test="${!empty param.success_param}">
            <c:out value="${added_test_success_message}"/>
        </c:if>
    </h3>
</div>

<div class="footer">
    <hr>
    <p>@2019 Copyright Yuri L. </p>
</div>
</body>
</html>