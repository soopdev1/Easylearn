<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="java.util.stream.Collectors"%>
<%@page import="rc.soop.sic.jpa.Corsoavviato"%>
<%@page import="rc.soop.sic.jpa.EntityOp"%>
<%@page import="java.util.List"%>
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
                EntityOp e = new EntityOp();
                List<Corsoavviato> avviati = e.getCorsiAvviati_Admin();
                List<Corsoavviato> conclusi = e.getCorsiConclusi_Admin();
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
        <link href="assets/plugins/custom/fullcalendar/fullcalendar.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
        <!--end::Page Vendor Stylesheets-->
        <!--begin::Global Stylesheets Bundle(used by all pages)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
        <!--end::Global Stylesheets Bundle-->
    </head>
    <!--end::Head-->
    <!--begin::Body-->
    <body id="kt_body" class="header-fixed header-tablet-and-mobile-fixed">
        <!--begin::Main-->
        <!--begin::Root-->
        <div class="d-flex flex-column flex-root">
            <!--begin::Page-->
            <div class="page d-flex flex-row flex-column-fluid">
                <!--begin::Wrapper-->
                <div class="wrapper d-flex flex-column flex-row-fluid" id="kt_wrapper">
                    <!--begin::Header-->
                    <jsp:include page="menu/header1.jsp" /> 
                    <!--end::Header-->
                    <!--begin::Content wrapper-->
                    <div class="d-flex flex-column-fluid">
                        <jsp:include page="menu/menuADM1.jsp" /> 
                        <!--begin::Container-->
                        <div class="d-flex flex-column flex-column-fluid container-fluid">
                            <!--begin::Post-->
                            <div class="content flex-column-fluid" id="kt_content">
                                <!--begin::Row-->
                                <!--end::Row-->
                                <!--begin::Row-->
                                <!--end::Row-->
                                <!--begin::Row-->
                                <div class="row g-10">
                                    <!--begin::Col-->
                                    <div class="row g-5 g-lg-10">
                                        <%String esito = Utils.getRequestValue(request, "esito");
                                            if (esito.equals("APPROVED")) {%>
                                        <div class="alert alert-success">
                                            <i class="fa fa-check-circle"></i> ISTANZA APPROVATA CORRETTAMENTE. DECRETO GENERATO ED INVIATO VIA PEC AL SOGGETTO PROPONENTE.
                                        </div>
                                        <hr>
                                        <%}%>
                                        <div class="card h-xl-50">
                                            <br/>
                                            <h3 class="text-danger">CORSI AVVIATI (<%=avviati.size()%>)</h3>
                                            <hr>
                                            <%if (!avviati.isEmpty()) {%>
                                            <table class="table table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th scope="col"><b>ID</b></th>
                                                        <th scope="col"><b>Soggetto Proponente</b></th>
                                                        <th scope="col"><b>Data Inizio</b></th>
                                                        <th scope="col"><b>Data Fine</b></th>
                                                        <th scope="col"><b>Azioni</b></th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <%for (Corsoavviato is1 : avviati) {%>
                                                    <tr>
                                                        <th scope="row"><%=is1.getIdcorso()%></th>
                                                        <td><%=is1.getSoggetto().getRAGIONESOCIALE()%></td>
                                                        <td><%=Constant.sdf_PATTERNDATE4.format(is1.getDatainizio())%></td>
                                                        <td><%=Constant.sdf_PATTERNDATE4.format(is1.getDatafine())%></td>
                                                        <td>
                                                            <a href="ADM_visdco.jsp?idcorso=<%=is1.getIdcorso()%>" data-fancybox data-type='iframe' 
                                                               data-bs-toggle="tooltip" title="VISUALIZZA DOCUMENTI CORSO" 
                                                               data-preload='false' data-width='75%' data-height='75%' 
                                                               class="btn btn-sm btn-bg-light btn-dark fan1">
                                                                <i class="fa fa-list-dots"></i></a>

                                                        </td>
                                                    </tr>  
                                                    <% }%>
                                                </tbody>
                                            </table>
                                            <%}%>
                                        </div>
                                        <div class="card h-xl-50">
                                            <br/>
                                            <h3 class="text-success">CORSI CONCLUSI (<%=conclusi.size()%>)</h3>
                                            <hr>
                                            <%if (!conclusi.isEmpty()) {%>
                                            <table class="table table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th scope="col"><b>ID</b></th>
                                                        <th scope="col"><b>Soggetto Proponente</b></th>
                                                        <th scope="col"><b>Data Inizio</b></th>
                                                        <th scope="col"><b>Data Fine</b></th>
                                                        <th scope="col"><b>Azioni</b></th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <%for (Corsoavviato is1 : conclusi) {%>
                                                    <tr>
                                                        <th scope="row"><%=is1.getIdcorso()%></th>
                                                        <td><%=is1.getSoggetto().getRAGIONESOCIALE()%></td>
                                                        <td><%=Constant.sdf_PATTERNDATE4.format(is1.getDatainizio())%></td>
                                                        <td><%=Constant.sdf_PATTERNDATE4.format(is1.getDatafine())%></td>
                                                        <td>
                                                            <a href="ADM_visdco.jsp?idcorso=<%=is1.getIdcorso()%>" data-fancybox data-type='iframe' 
                                                               data-bs-toggle="tooltip" title="VISUALIZZA DOCUMENTI CORSO" 
                                                               data-preload='false' data-width='75%' data-height='75%' 
                                                               class="btn btn-sm btn-bg-light btn-dark fan1">
                                                                <i class="fa fa-list-dots"></i>
                                                            </a>
                                                            <%if (is1.getMembricommissione() == null) {%>
                                                            <a href="ADM_commissione.jsp?idcorso=<%=is1.getIdcorso()%>" data-fancybox data-type='iframe' 
                                                               data-bs-toggle="tooltip" title="NOMINA COMMISSIONE" 
                                                               data-preload='false' data-width='75%' data-height='75%' 
                                                               class="btn btn-sm btn-bg-light btn-primary fan1">
                                                                <i class="fa fa-users"></i>
                                                            </a>                                                                
                                                            <%}%>
                                                        </td>
                                                    </tr>  
                                                    <% }%>
                                                </tbody>
                                            </table>
                                            <%}%>
                                        </div>

                                    </div>
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
        <!--end::Global Javascript Bundle-->
        <!--begin::Page Vendors Javascript(used by this page)-->
        <script src="assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>
        <script src="assets/plugins/DataTables/datatables.min.js"></script>
        <!--end::Page Vendors Javascript-->
        <!--begin::Page Custom Javascript(used by this page)-->
        <script src="assets/js/widgets.bundle.js"></script>
        <script src="assets/js/custom/widgets.js"></script>
        <script src="assets/js/custom/apps/chat/chat.js"></script>
        <script src="assets/js/custom/utilities/modals/create-app.js"></script>
        <script src="assets/js/custom/utilities/modals/create-campaign.js"></script>
        <script src="assets/js/custom/utilities/modals/users-search.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
        <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
                var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                    return new bootstrap.Tooltip(tooltipTriggerEl);
                });
            });
            $(document).ready(Fancybox.bind(".fan1", {
                groupAll: false, // Group all items
                on: {
                    closing: (fancybox) => {
                        window.location.reload();
                    }
                }
            }));
        </script>
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