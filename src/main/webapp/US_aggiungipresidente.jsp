<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

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
                EntityOp eo = new EntityOp();
    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Aggiungi anagrafica allievi</title>
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
            <form method="POST" action="Operations" onsubmit="return controllasalvataggio();">
                <input type="hidden" name="type" value="ADDALLIEVO"/>
                <div class="col-xl-12">
                    <div class="card h-xl-100">
                        <div class="card-header border-0 pt-5">
                            <h3 class="card-title align-items-start flex-column">
                                <span class="card-label fw-bolder fs-3 mb-1">NUOVO ALLIEVO</span>
                            </h3>
                            <button class="btn btn-lg btn-success"><i class="fa fa-save"></i> SALVA DATI</button>
                        </div>
                        <div class="card-body py-3">
                            <div class="row col-md-12 col-form-label">
                                <div class="alert alert-danger"  id="messageerror" style="display: none;">
                                    ERRORE: 
                                </div>
                            </div>
                            <div class="row col-md-12">
                                <label class="col-md-3 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>COGNOME</b></span>
                                </label>
                                <div class="col-md-3 col-form-label fs-6">
                                    <input type="text" 
                                           name="COGNOME"
                                           id="COGNOME"
                                           onkeyup="return checkNoSpecialChar(this)"
                                           class="form-control" required maxlength="50" />
                                </div>
                                <label class="col-md-3 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>NOME</b></span>
                                </label>
                                <div class="col-md-3 col-form-label fs-6">
                                    <input type="text" 
                                           name="NOME"
                                           id="NOME"
                                           onkeyup="return checkNoSpecialChar(this)"
                                           class="form-control" required maxlength="50" />
                                </div>
                                <label class="col-md-3 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>CODICE FISCALE</b></span>
                                </label>
                                <div class="col-md-3 col-form-label fs-6">
                                    <input type="text" 
                                           name="CODICEFISCALE"
                                           id="CODICEFISCALE"
                                           onkeyup="return fieldNOSPecial_1(this.id);"
                                           class ="form-control" required maxlength="16" />
                                </div>
                                <label class="col-md-3 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>NUMERO DI TELEFONO (SENZA +39)</b></span>
                                </label>
                                <div class="col-md-3 col-form-label fs-6">
                                    <input type="text" 
                                           name="TELEFONO"
                                           id="TELEFONO"
                                           class="form-control intvalue" required maxlength="12" />
                                </div>
                                <label class="col-md-3 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>INDIRIZZO EMAIL</b></span>
                                </label>
                                <div class="col-md-3 col-form-label fs-6">
                                    <input type="text" 
                                           name="EMAIL"
                                           id="EMAIL"
                                           class="form-control mailvalue" required maxlength="100"/>
                                </div>
                                <label class="col-md-3 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>NUMERO DOC.ID.</b></span>
                                </label>
                                <div class="col-md-3 col-form-label fs-6">
                                    <input type="text" 
                                           name="DOCID"
                                           id="DOCID"
                                           onkeyup="return checkNoSpecialChar(this)"
                                           class="form-control" required maxlength="20"/>
                                </div>
                                <label class="col-md-3 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>SCADENZA DOC.ID.</b></span>
                                </label>
                                <div class="col-md-3 col-form-label fs-6">
                                    <input type="date" 
                                           name="DATEDOCID"
                                           id="DATEDOCID"
                                           class="form-control mailvalue" required maxlength="100"/>
                                </div>
                                <label class="col-md-3 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>TITOLO DI STUDIO</b></span>
                                </label>
                                <div class="col-md-3 col-form-label fs-6">
                                    <input type="text" 
                                           name="TITSTUDIO"
                                           id="TITSTUDIO"
                                           onkeyup="return checkNoSpecialChar(this)"
                                           class="form-control" required maxlength="100"/>
                                </div>
                                <label class="col-md-3 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>APPARTENENTE A CATEGORIE PROTETTE</b></span>
                                </label>
                                <div class="col-md-3 col-form-label fs-6">
                                    <div class="form-check form-switch">
                                        <input class="form-check-input" type="checkbox" role="switch" 
                                               id="CATPROT"
                                               name="CATPROT"/>
                                        <label class="form-check-label" 
                                               for="CATPROT">SI/NO</label>
                                    </div>
                                </div>
                            </div>
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
        <script src="assets/js/US_aggiungiallievo.js"></script>

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