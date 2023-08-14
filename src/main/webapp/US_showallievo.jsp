<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.Allievi"%>
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
                String idistS = Utils.getRequestValue(request, "idallievo");
                if (idistS.equals("")) {
                    idistS = (String) session.getAttribute("ses_idallievo");
                } else {
                    session.setAttribute("ses_idallievo", idistS);
                }
                EntityOp eo = new EntityOp();
                Long idist = Long.valueOf(Utils.dec_string(idistS));
                Allievi is1 = eo.getEm().find(Allievi.class, idist);
    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Anagrafica allievo</title>
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
                <div class="col-xl-12">
                    <div class="card h-xl-100">
                        <div class="card-header border-0 pt-5">
                            <h3 class="card-title align-items-start flex-column">
                                <span class="card-label fw-bolder fs-3 mb-1">DETTAGLI ALLIEVO - ID: <%=is1.getIdallievi()%></span>
                            </h3>
                        </div>
                        <div class="card-body py-3">
                            <div class="row col-md-12">
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span class="text-danger"><b>COGNOME</b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span><b><%=is1.getCognome()%></b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>NOME</b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span><b><%=is1.getNome()%></b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>CODICE FISCALE</b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span><b><%=is1.getCodicefiscale()%></b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>NUMERO DI TELEFONO</b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span><b><%=is1.getTelefono()%></b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>INDIRIZZO EMAIL</b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span><b><%=is1.getEmail()%></b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>NUMERO DOC.ID.</b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span><b><%=is1.getNumdocid()%></b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>SCADENZA DOC.ID.</b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span><b><%=Constant.sdf_PATTERNDATE4.format(is1.getDatanascita())%></b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>TITOLO DI STUDIO</b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span><b><%=is1.getCognome()%></b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>APPARTENENTE A CATEGORIE PROTETTE</b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span><b><%=(is1.isCatprot()) ? "SI" : "NO"%></b></span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>DATA INSERIMENTO</b></span>
                                </label>
                                <label class="col-md-4 col-form-label fw-bold">
                                    <span><b><%=Constant.sdf_PATTERNDATE5.format(is1.getDatainserimento())%></b></span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <!--end::Tables Widget 3-->
                </div>
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