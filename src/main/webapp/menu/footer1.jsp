<%-- 
    Document   : footer1
    Created on : 19-feb-2022, 13.36.42
    Author     : raf
--%>

<%@page import="rc.soop.sic.Constant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="footer py-4 d-flex flex-column flex-md-row flex-stack" id="kt_footer">
    <!--begin::Copyright-->
    <div class="text-dark order-2 order-md-1">
        <span class="text-muted fw-bold me-1"><%=Constant.NAMEAPP%> &#169;2022</span>
        <span class="text-gray-800 text-hover-primary"><%=Constant.VERSIONAPP%></span>
        <%if (Constant.ISDEMO) {%>
        <img alt="Logo" src="assets/media/logos/demo.png" class="h-40px" /> 
        <%}%>
    </div>
</div>
