<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.Tipologia_Percorso"%>
<%@page import="rc.soop.sic.jpa.User"%>
<%@page import="rc.soop.sic.jpa.Sede"%>
<%@page import="rc.soop.sic.Engine"%>
<%@page import="rc.soop.sic.jpa.Scheda_Attivita"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.soop.sic.jpa.Corso"%>
<%@page import="java.util.List"%>
<%@page import="rc.soop.sic.jpa.EntityOp"%>
<%@page import="rc.soop.sic.jpa.Istanza"%>
<%@page import="rc.soop.sic.Utils"%>
<%@page import="rc.soop.sic.Constant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <%
        int verifysession = Utils.checkSession(session, request);
        switch (verifysession) {
            case 1: {


    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%> - Dashboard</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="assets/css/gfont.css" />
        <!--end::Fonts-->
        <!--begin::Page Vendor Stylesheets(used by this page)-->
        <!--end::Page Vendor Stylesheets-->
        <!--begin::Global Stylesheets Bundle(used by all pages)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/plus.css" rel="stylesheet" type="text/css" />

        <!--end::Global Stylesheets Bundle-->
    </head>
    <!--end::Head-->
    <!--begin::Body-->
    <body id="kt_body">
        <!--begin::Main-->
        <!--begin::Root-->
        <div class="flex-column flex-root">
            <!--begin::Page-->
            <div class="page flex-row flex-column-fluid">
                <!--begin::Wrapper-->
                <div class="flex-column flex-row-fluid" id="kt_wrapper">
                    <!--begin::Header-->
                    <!--end::Header-->
                    <!--begin::Content wrapper-->
                    <div class="flex-column-fluid">
                        <!--begin::Container-->
                        <div class="flex-column flex-column-fluid container-fluid">
                            <!--begin::Post-->
                            <div class="content flex-column-fluid" id="kt_content">
                                <!--begin::Row-->
                                <!--end::Row-->
                                <!--begin::Row-->
                                <!--end::Row-->
                                <!--begin::Row-->
                                <br/>
                                <%
                                    String esito = Utils.getRequestValue(request, "esito");
                                    String ty = "";
                                    String html = "";
                                    switch (esito) {
                                        case "OK_CL": 
                                        case "OK_SM": 
                                            {
                                            ty = "alert-success";
                                            html = "<i class='fa fa-check-circle'></i> Operazione completata con successo. Chiudere questa finestra.";
                                            break;
                                        }
                                        case "KO_FA": {
                                            ty = "alert-success";
                                            html = "<i class='fa fa-check-circle'></i> Operazione completata con successo. Chiudere questa finestra.";
                                            break;
                                        }
                                        default: {
                                            break;
                                        }
                                    }
                                %>
                                <div class="row g-10">
                                    <!--begin::Col-->
                                    <div class="col-xl-12 alert <%=ty%>">
                                        <!--begin::Tables Widget 3-->
                                        <h1 class="text-center fs-4"><%=html%></h1>
                                        <!--end::Tables Widget 3-->
                                    </div>
                                    <!--end::Col-->
                                    <!--begin::Col-->
                                    <!--end::Col-->
                                </div>
                                <!--end::Row-->
                            </div>
                            <!--end::Post-->
                            <!--begin::Footer-->
                            <jsp:include page="menu/footer1.jsp" /> 
                            <!--end::Footer-->
                        </div>
                        <!--end::Container-->
                    </div>
                    <!--end::Content wrapper-->
                </div>
                <!--end::Wrapper-->
            </div>
            <!--end::Page-->
        </div>
        <!--end::Root-->
        <!--begin::Drawers-->
        <!--begin::Activities drawer-->
        <!--end::Activities drawer-->
        <!--begin::Chat drawer-->
        <!--end::Chat drawer-->
        <!--end::Drawers-->
        <!--end::Main-->
        <!--begin::Engage drawers-->
        <!--begin::Demos drawer-->
        <!--end::Demos drawer-->
        <!--begin::Help drawer-->
        <!--end::Help drawer-->
        <!--end::Engage drawers-->
        <!--begin::Engage toolbar-->
        <!--end::Engage toolbar-->
        <!--begin::Scrolltop-->

        <!--end::Scrolltop-->
        <!--begin::Javascript-->
        <script>var hostUrl = "assets/";</script>
        <!--begin::Global Javascript Bundle(used by all pages)-->
        <script src="assets/plugins/global/plugins.bundle.js"></script>
        <script src="assets/js/scripts.bundle.js"></script>
        <script src="assets/js/widgets.bundle.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <!--end::Page Custom Javascript-->
        <!--end::Javascript-->
    </body>
    <!--end::Body-->
    <%break;
            }

            case -1:
                Utils.redirect(request, response, "login.jsp");
                break;
            case 0:
                Utils.redirect(request, response, "login.jsp");
                break;
            default:
                throw new Exception();
        }
    %>
</html>