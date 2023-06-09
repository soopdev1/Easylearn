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
    <%

        EntityOp eo = new EntityOp();
        Long idist = Long.valueOf(Utils.dec_string(Utils.getRequestValue(request, "idist")));
        Istanza is1 = eo.getEm().find(Istanza.class, idist);
        Tipologia_Percorso tp1 = eo.getTipoPercorsoIstanza(is1);
    %>
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
                                <%if (is1 != null) {%>
                                <div class="row g-10">
                                    <!--begin::Col-->
                                    <div class="col-xl-12">
                                        <!--begin::Tables Widget 3-->
                                        <h1 class="text-center fs-4">ID Istanza: <%=is1.getIdistanza()%> - Codice Istanza: <%=is1.getCodiceistanza()%></h1>
                                        <div class="card h-xl-100">
                                            <!--begin::Header-->
                                            <div class="card-header border-0 pt-5">
                                                <h3 class="card-title align-items-start flex-column">
                                                    <span class="card-label fw-bolder fs-3 mb-1">ISTANZA PER L'AUTORIZZAZIONE DEI CORSI AUTOFINANZIATI</span>
                                                    <span class="text-muted mt-1 fw-bold fs-7">Istanza di autorizzazione allo svolgimento di corsi di formazione professionale</span>
                                                </h3>
                                            </div>
                                            <!--end::Header-->
                                            <!--begin::Body-->
                                            <div class="card-body py-3">
                                                <div class="row row-border col-md-12 p-5">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-3 col-form-label fw-bold fs-6" >
                                                        <span class="text-primary"><b>TIPOLOGIA PERCORSO</b></span>
                                                    </label>
                                                    <label class="col-lg-9 col-form-label fw-bold fs-6" >
                                                        <span class="text-dark"><b><%=tp1.getNometipologia()%></b></span>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--end::Tables Widget 3-->
                            </div>
                            <!--end::Col-->
                            <!--begin::Col-->
                            <!--end::Col-->
                        </div>
                        <!--end::Row-->
                        <%}%>
                    </div>
                    <!--end::Post-->
                    <!--begin::Footer-->
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
<div id="kt_scrolltop" class="scrolltop" data-kt-scrolltop="true">
    <!--begin::Svg Icon | path: icons/duotune/arrows/arr066.svg-->
    <span class="svg-icon">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
        <rect opacity="0.5" x="13" y="6" width="13" height="2" rx="1" transform="rotate(90 13 6)" fill="black" />
        <path d="M12.5657 8.56569L16.75 12.75C17.1642 13.1642 17.8358 13.1642 18.25 12.75C18.6642 12.3358 18.6642 11.6642 18.25 11.25L12.7071 5.70711C12.3166 5.31658 11.6834 5.31658 11.2929 5.70711L5.75 11.25C5.33579 11.6642 5.33579 12.3358 5.75 12.75C6.16421 13.1642 6.83579 13.1642 7.25 12.75L11.4343 8.56569C11.7467 8.25327 12.2533 8.25327 12.5657 8.56569Z" fill="black" />
        </svg>
    </span>
    <!--end::Svg Icon-->
</div>
<!--end::Scrolltop-->
<!--begin::Javascript-->
<script>var hostUrl = "assets/";</script>
<!--begin::Global Javascript Bundle(used by all pages)-->
<script src="assets/plugins/global/plugins.bundle.js"></script>
<script src="assets/js/scripts.bundle.js"></script>
<script src="assets/js/widgets.bundle.js"></script>
<script src="assets/fontawesome-6.0.0/js/all.js"></script>

<script type="text/javascript" src="assets/js/wiz_1.js"></script>
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