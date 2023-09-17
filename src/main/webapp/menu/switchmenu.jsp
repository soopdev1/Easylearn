<%-- 
    Document   : switchmenu
    Created on : 17 set 2023, 18:36:09
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String t_us = "0";

    Object o1 = session.getAttribute("us_rolecod");
    if (o1 != null) {
        t_us = o1.toString();
    }
    switch (t_us) {
        case "1": {%>
<jsp:include page="menuADM1.jsp" />
<%
    break;
    }
    case "2": {%>
<jsp:include page="menuUS1.jsp" /> 
<%
    break;
        }
        default:
            break;
    }
%>