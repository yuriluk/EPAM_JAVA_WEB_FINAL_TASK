<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="locale"
       value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${locale}"/>

<fmt:setBundle basename="text"/>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>TEST ADD PAGE</title>
</head>
<body>

<div id="header">
    <div>
        <h1></h1>
    </div>
    <nav>
        <ul class="header">
            <li>
                <a href="controller?command=viewIndex"><fmt:message key="button.go_home"/></a>
            </li>
            <c:if test="${userId != null}">
                <li>
                    <a href="controller?command=signOut"><fmt:message key="button.signOut"/></a>
                </li>
            </c:if>
            <c:if test="${userId == null}">
                <p>You are not registered user?</p>
                <li>
                    <a href="controller?command=viewSignUp"><fmt:message key="button.signUp"/></a>
                </li>
            </c:if>
            <li>
                <a href="controller?command=viewUserCabinet"><fmt:message key="button.personal_cabinet"/></a>
            </li>
        </ul>
    </nav>
</div>
<hr>
<div class="main-content">
    <h1><fmt:message key="test.page_add_title"/></h1>
    <form action="controller" method="post">

        <table cellspacing="2" border="1" cellpadding="5" bordercolor="gray">
            <caption><span style="font-size: x-large; ">${test_list}</span></caption>
            <tr>
                <th><fmt:message key="test_title"/></th>
                <th><fmt:message key="test_description"/></th>
                <th><fmt:message key="test_subject"/></th>
            </tr>

            <tr>
                <td align="center" width="100"><input type="text" name="testTitle" required></td>
                <td align="center" width="100"><input type="text" name="testDescription" required></td>
                <td align="center" width="100"><input type="text" name="testSubjectId" required></td>
            </tr>
            <input type="hidden" name="testAuthorId" value="${userId}">
        </table>

        <button type="submit" name="command" value="addTest">
            <strong><fmt:message key="button.test_add"/></strong>
        </button>
    </form>
</div>
<hr>
<div class="footer">
    <p>@2019 Copyright Yuri L. </p>
</div>
</body>
</html>