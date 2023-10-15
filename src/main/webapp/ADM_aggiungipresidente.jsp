<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.EnteStage"%>
<%@page import="rc.soop.sic.jpa.IstatProv"%>
<%@page import="java.util.List"%>
<%@page import="rc.soop.sic.jpa.Ateco"%>
<%@page import="rc.soop.sic.Constant"%>
<%@page import="rc.soop.sic.jpa.EntityOp"%>
<%@page import="rc.soop.sic.Utils"%>
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
        <title><%=Constant.NAMEAPP%>: Aggiungi anagrafica presidente</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="assets/css/gfont.css" />
        <!--end::Fonts-->
        <!--begin::Page Vendor Stylesheets(used by this page)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link href="assets/plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="assets/plugins/jquery-confirm.3.3.2.min.css">

        <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/plus.css" rel="stylesheet" type="text/css" />

        <!--end::Global Stylesheets Bundle-->
    </head>
    <!--end::Head-->
    <!--begin::Body-->
    <body id="kt_body">
        <!--begin::Main-->
        <!--begin::Root-->
        <!--begin::Row-->
        <!--end::Row-->
        <!--begin::Row-->
        <!--end::Row-->
        <!--begin::Row-->
        <div class="row">
            <!--begin::Col-->
            <form method="POST" action="Operations">
                <input type="hidden" name="type" value="ADDPRESIDENTE"/>
                <div class="col-xl-12">
                    <div class="card h-xl-100">
                        <div class="card-header border-0 pt-5">

                            <h3 class="card-title align-items-start flex-column">
                                <span class="card-label fw-bolder fs-3 mb-1">ANAGRAFICA PRESIDENTE DI COMMISSIONE</span>
                            </h3>
                            <button class="btn btn-lg btn-success"><i class="fa fa-save"></i> SALVA DATI</button>
                        </div>
                        <div class="card-body py-3">
                            <div class="row col-md-12">
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-dark"><b>COGNOME</b></span>
                                </label>
                                <div class="col-md-4 col-form-label fs-6">
                                    <input type="text" 
                                           name="COGNOME"
                                           id="COGNOME"
                                           class="form-control" required maxlength="50" onkeyup="return checkNoSpecialChar(this)"/>
                                </div>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-dark"><b>NOME</b></span>
                                </label>
                                <div class="col-md-4 col-form-label fs-6">
                                    <input type="text" 
                                           name="NOME"
                                           id="NOME"
                                           class="form-control capvalue" required maxlength="50" onkeyup="return checkNoSpecialChar(this)"/>
                                </div>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-dark"><b>QUALIFICA</b></span>
                                </label>
                                <div class="col-md-4 col-form-label fs-6">
                                    <input type="text" 
                                           name="QUALIFICA"
                                           id="QUALIFICA"
                                           class="form-control" required maxlength="150" onkeyup="return checkNoSpecialChar(this)"/>
                                </div>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-dark"><b>DIPARTIMENTO</b></span>
                                </label>
                                <div class="col-md-4 col-form-label fs-6">
                                    <input type="text" 
                                           name="DIPARTIMENTO"
                                           id="DIPARTIMENTO"
                                           class="form-control" required maxlength="150" onkeyup="return checkNoSpecialChar(this)"/>
                                </div>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-dark"><b>AREA</b></span>
                                </label>
                                <div class="col-md-4 col-form-label fs-6">
                                    <input type="text" 
                                           name="AREA"
                                           id="AREA"
                                           class="form-control" required maxlength="150" onkeyup="return checkNoSpecialChar(this)"/>
                                </div>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-dark"><b>INDIRIZZO EMAIL</b></span>
                                </label>
                                <div class="col-md-4 col-form-label fs-6">
                                    <input type="text" 
                                           name="EMAIL"
                                           id="EMAIL"
                                           class="form-control mailvalue" required maxlength="100"/>
                                </div>
                                
                            </div>
                            <span class="help-block">
                                N.B. dopo aver salvato l'anagrafica verrà creata l'utenza di accesso e le credenziali saranno inviate all'indirizzo email inserite.
                            </span>
                        </div>
                    </div>
                    <!--end::Tables Widget 3-->
                </div>
            </form>

            <!--end::Col-->
            <!--begin::Col-->
            <!--end::Col-->
        </div>
        <!--end::Row-->
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

        <!--end::Global Javascript Bundle-->
        <!--begin::Page Vendors Javascript(used by this page)-->
        <!--end::Page Vendors Javascript-->
        <!--begin::Page Custom Javascript(used by this page)-->
        <script src="assets/plugins/global/plugins.bundle.js"></script>
        <script src="assets/js/scripts.bundle.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <script src="assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>
        <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
        <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
        <script src="assets/js/common.js"></script>
        <script src="assets/plugins/jquery-confirm.min3.3.2.js"></script>
        <script src="assets/js/ADM_presidenti.js"></script>

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