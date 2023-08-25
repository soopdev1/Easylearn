<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.Engine"%>
<%@page import="rc.soop.sic.jpa.Tipologia_Percorso"%>
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
    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Gestione Istanze</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="assets/css/gfont.css" />
        <!--end::Fonts-->
        <!--begin::Page Vendor Stylesheets(used by this page)-->
        <link href="assets/plugins/custom/fullcalendar/fullcalendar.bundle.css" rel="stylesheet" type="text/css" />
        <!--end::Page Vendor Stylesheets-->
        <!--begin::Global Stylesheets Bundle(used by all pages)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="assets/plugins/jquery-confirm.3.3.2.min.css">
        <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
        <script src="https://cdn.tiny.cloud/1/<%=Constant.TINYMCEKEY%>/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>

        <link href="assets/plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
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
                                    <%String esito = Utils.getRequestValue(request, "esito");
                                        if (esito.equals("APPROVED")) {%>
                                    <div class="alert alert-success">
                                        <i class="fa fa-check-circle"></i> ISTANZA APPROVATA CORRETTAMENTE. DECRETO GENERATO ED INVIATO VIA PEC AL SOGGETTO PROPONENTE.
                                    </div>
                                    <hr>
                                    <%}%>
                                    <%
                                        List<Tipologia_Percorso> per1 = Engine.tipo_percorso_attivi();
                                    %>
                                    <div class="card card-xl-stretch">
                                        <div class="card-header border-0 pt-5">
                                            <h3 class="card-title align-items-start flex-column">
                                                <span class="card-label fw-bolder fs-3 mb-1">ELENCO ISTANZE</span>
                                            </h3>
                                        </div>
                                        <div class="card-body py-3">
                                            <div class="col-md-12 row">
                                                <div class="col-md-6">
                                                    <label>Tipo Percorso</label>
                                                    <select aria-label="Scegli..." 
                                                            data-placeholder="Scegli Tipologia percorso" 
                                                            class="form-select form-select-solid form-select-lg fw-bold" 
                                                            name="tipopercorso"
                                                            id="tipopercorso" onchange="return refreshtable();"
                                                            >
                                                        <option value="">...</option>  
                                                        <%for (Tipologia_Percorso t1 : per1) {%>
                                                        <option value="<%=t1.getIdtipopercorso()%>"><%=t1.getNometipologia().toUpperCase()%></option>  
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-md-6">
                                                    <label>Stato Istanza</label>
                                                    <select aria-label="Scegli..." 
                                                            data-placeholder="Scegli Tipologia percorso" 
                                                            class="form-select form-select-solid form-select-lg fw-bold" 
                                                            name="statoistanza"
                                                            id="statoistanza" onchange="return refreshtable();"
                                                            >
                                                        <option value="">...</option>  
                                                        <option value="07">ISTANZA DA GESTIRE</option>  
                                                        <option value="08">ISTANZA APPROVATA</option>  
                                                        <option value="09">ISTANZA RIGETTATA</option>  
                                                        <option value="10">ISTANZA RIGETTATA - SOC.ISTR.</option>  
                                                    </select>
                                                </div>
                                            </div>
                                            <hr>
                                            <!--begin::Table container-->
                                            <div class="table-responsive ">
                                                <!--begin::Table-->
                                                <table class="table align-middle gy-3 table-bordered table-hover" id="tab_dt1">
                                                    <!--begin::Table head-->
                                                    <thead>
                                                        <tr>
                                                            <th scope="col"><b>ID</b></th>
                                                            <th scope="col"><b>Soggetto Proponente</b></th>
                                                            <th scope="col"><b>Corsi</b></th>
                                                            <th scope="col"><b>Data presentazione</b></th>
                                                            <th scope="col"><b>Stato</b></th>
                                                            <th scope="col"><b>Azioni</b></th>
                                                        </tr>
                                                    </thead>
                                                    <!--end::Table head-->
                                                    <!--begin::Table body-->
                                                    <tbody></tbody>
                                                    <!--end::Table body-->
                                                </table>
                                                <!--end::Table-->
                                            </div>
                                            <!--end::Table container-->
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
        </div>
        <div id="kt_scrolltop" class="scrolltop" data-kt-scrolltop="true">
            <span class="svg-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                <rect opacity="0.5" x="13" y="6" width="13" height="2" rx="1" transform="rotate(90 13 6)" fill="black" />
                <path d="M12.5657 8.56569L16.75 12.75C17.1642 13.1642 17.8358 13.1642 18.25 12.75C18.6642 12.3358 18.6642 11.6642 18.25 11.25L12.7071 5.70711C12.3166 5.31658 11.6834 5.31658 11.2929 5.70711L5.75 11.25C5.33579 11.6642 5.33579 12.3358 5.75 12.75C6.16421 13.1642 6.83579 13.1642 7.25 12.75L11.4343 8.56569C11.7467 8.25327 12.2533 8.25327 12.5657 8.56569Z" fill="black" />
                </svg>
            </span>
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
        <script src="assets/plugins/DataTables/jquery-3.5.1.js"></script>
        <script src="assets/plugins/DataTables/jquery.dataTables.min.js"></script>
        <script src="assets/plugins/DataTables/datatables.min.js"></script>
        <script src="assets/plugins/DataTables/date-euro.js"></script>


        <!--end::Page Vendors Javascript-->
        <!--begin::Page Custom Javascript(used by this page)-->
        <script src="assets/js/widgets.bundle.js"></script>
        <script src="assets/js/custom/widgets.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>

        <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
        <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>

        <script src="assets/plugins/jquery-confirm.min3.3.2.js"></script>
        <script type="text/javascript" src="assets/js/ADM_istanze.js"></script>

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