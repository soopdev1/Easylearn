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
        <title><%=Constant.NAMEAPP%>: Page Message</title>
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
                                        case "OK_SMAT":
                                        case "OK_SMD":
                                        case "OK_UPAL":
                                        case "OKRI_IS1": {
                                            ty = "alert-success";
                                            html = "<i class='fa fa-check-circle'></i> Operazione completata con successo. Chiudere questa finestra.";
                                            break;
                                        }
                                        case "KOUP_IS1":
                                        case "KOUP_IS2": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile caricare il documento. Istanza non trovata, riprovare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KOUP_AL1":
                                        case "KOUP_AL2": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile caricare il documento. Allievo non trovato, riprovare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KOUP_EN1":
                                        case "KOUP_EN2": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile caricare il documento. Ente non trovato, riprovare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KOUP_CA1":
                                        case "KOUP_CA2": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile caricare il documento. Corso non trovato, riprovare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KOUP_IS3":
                                        case "KOUP_IS4": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile caricare il documento. File corrotto o assente, riprovare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KORI_IS1":
                                        case "KORI_IS2": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile rigettare l'istanza. Istanza non trovata, riprovare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KORI_CO1":
                                        case "KORI_CO2": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile rigettare il corso. Corso non trovata, riprovare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KO_NAL1": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile inserire nuovo allievo. Riprovare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KO_NAL2": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile inserire nuovo allievo. COdice fiscale già presente in anagrafica allievi."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KO_NSED1": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile inserire nuova sede tirocinio/stage. Controllare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KO_NEN1": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile inserire nuovo ente. Controllare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KO_NEN2": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile inserire nuovo ente. Partita IVA già presente in anagrafica."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KO_NTP1": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile inserire/modificare tipologia percorso. Controllare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KO_NCIN1": 
                                        case "KO_NCIN2": 
                                            {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile inserire nuovo corso. Controllare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
                                            break;
                                        }
                                        case "KO_LEZ1": {
                                            ty = "alert-danger";
                                            html = "<i class='fa fa-exclamation-triangle'></i> ERRORE! Impossibile inserire la lezione. Controllare."
                                                    + " <hr>"
                                                    + "<button class='btn btn-danger' onclick='history.back()'><i class='fa fa-backward'></i> Torna Indietro</button>";
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