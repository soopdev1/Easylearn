<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

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
                Istanza i1 = (Istanza) session.getAttribute("is_memory");
                int corsi_attuali = 0;
                boolean istanzaok = false;
                boolean istanzapresentata = false;
                boolean istanzaaccettata = false;
                boolean istanzarigettata = false;
                if (i1 != null) {
                    istanzaok = i1.getStatocorso().getCodicestatocorso().equals("02");
                    istanzapresentata = i1.getStatocorso().getCodicestatocorso().equals("07") 
                || i1.getStatocorso().getCodicestatocorso().equals("08") 
                || i1.getStatocorso().getCodicestatocorso().equals("09");
                    istanzaaccettata = i1.getStatocorso().getCodicestatocorso().equals("08");
                    istanzarigettata = i1.getStatocorso().getCodicestatocorso().equals("09");
                    corsi_attuali = i1.getQuantitarichiesta();
                } else {
                    i1 = new Istanza();
                }

    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%> - Dashboard</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
        <!--end::Fonts-->
        <!--begin::Page Vendor Stylesheets(used by this page)-->
        <link href="assets/plugins/custom/fullcalendar/fullcalendar.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/plugins/custom/datatables/datatables.bundle.css" rel="stylesheet" type="text/css" />
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
                        <jsp:include page="menu/menuUS2.jsp" /> 
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
                                    <div class="col-xl-12">
                                        <!--begin::Tables Widget 3-->
                                        <div class="card h-xl-100">
                                            <!--begin::Header-->
                                            <div class="card-header border-0 pt-5">
                                                <h3 class="card-title align-items-start flex-column">
                                                    <span class="card-label fw-bolder fs-3 mb-1">ISTANZA PER L'AUTORIZZAZIONE DEI CORSI AUTOFINANZIATI</span>
                                                    <span class="text-muted mt-1 fw-bold fs-7">Istanza di autorizzazione allo svolgimento di corsi di formazione professionale autofinanziati</span>
                                                </h3>

                                            </div>
                                            <!--end::Header-->
                                            <!--begin::Body-->
                                            <div class="card-body py-3">
                                                <!--begin::Table container-->
                                                <div class="table-responsive">
                                                    <!--begin::Table-->
                                                    <table class="table align-middle gs-0 gy-3">
                                                        <!--begin::Table head-->
                                                        <thead>
                                                            <tr>
                                                                <th class="p-0 w-50px"></th>
                                                                <th class="p-0 min-w-150px"></th>
                                                                <th class="p-0 min-w-140px"></th>
                                                                <th class="p-0 min-w-120px"></th>
                                                                <th class="p-0 min-w-40px"></th>
                                                            </tr>
                                                        </thead>
                                                        <!--end::Table head-->
                                                        <!--begin::Table body-->
                                                        <tbody>
                                                            <%if (istanzaok || istanzapresentata) {%>
                                                            <tr>
                                                                <td>
                                                                    <div class="symbol symbol-50px me-2">
                                                                        <span class="symbol-label bg-success">
                                                                            <!--begin::Svg Icon | path: icons/duotune/ecommerce/ecm002.svg-->
                                                                            <span class="symbol-label text-white bg-success">
                                                                                <i class="fa fa-check-circle fa-2x"></i>
                                                                            </span>
                                                                            <!--end::Svg Icon-->
                                                                        </span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <a href="#" class="text-dark fw-bolder text-hover-primary mb-1 fs-6" 
                                                                       data-bs-toggle="tooltip" data-bs-placement="top" 
                                                                       title="Aggiungi Nuovo Corso">
                                                                        1) Elenco Corsi di Formazione
                                                                    </a>
                                                                </td>                                                                
                                                                <td class="text-end fw-bold" colspan="2">ISTANZA PRONTA: Corsi Attuali - <%=corsi_attuali%></td>                                                                
                                                            </tr>

                                                            <%if (i1.getPathfirmato() == null) {%>
                                                            <tr>
                                                                <td>
                                                                    <div class="symbol symbol-50px me-2">
                                                                        <span class="symbol-label bg-light-primary">
                                                                            <!--begin::Svg Icon | path: icons/duotune/ecommerce/ecm002.svg-->
                                                                            <span class="symbol-label text-primary bg-light-primary">
                                                                                <i class="fa fa-hourglass-2 fa-2x"></i>
                                                                            </span>
                                                                            <!--end::Svg Icon-->
                                                                        </span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <a href="#" class="text-dark fw-bolder text-hover-primary mb-1 fs-6">2) Istanza</a>
                                                                </td>
                                                                <td class="text-end text-muted fw-bold" colspan="1">
                                                                    <form action="Operations" method="POST" target="_blank">
                                                                        <input type="hidden" name="type" value="GENERAISTANZA" />
                                                                        <input type="hidden" name="codice_istanza" value="<%=i1.getCodice()%>" />
                                                                        <button type="submit" class="btn btn-sm btn-bg-light btn-primary"><i class="fa fa-file-download"></i> GENERA DOCUMENTO</button>
                                                                    </form>
                                                                </td>
                                                                <td class="text-end text-muted fw-bold" colspan="1">
                                                                    <a href="US_upload.jsp?codice_istanza=<%=i1.getCodice()%>" data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%' 
                                                                       class="btn btn-sm btn-bg-light btn-danger fan1">
                                                                        <i class="fa fa-file-upload"></i> CARICA DOCUMENTO FIRMATO</a>
                                                                </td>
                                                            </tr>
                                                            <%} else {%>
                                                            <tr>
                                                                <td>
                                                                    <div class="symbol symbol-50px me-2">
                                                                        <div class="symbol symbol-50px me-2">
                                                                            <span class="symbol-label bg-success">
                                                                                <!--begin::Svg Icon | path: icons/duotune/ecommerce/ecm002.svg-->
                                                                                <span class="symbol-label text-white bg-success">
                                                                                    <i class="fa fa-check-circle fa-2x"></i>
                                                                                </span>
                                                                                <!--end::Svg Icon-->
                                                                            </span>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <a href="#" class="text-dark fw-bolder text-hover-primary mb-1 fs-6">2) Istanza</a>
                                                                </td>
                                                                <%if (!istanzapresentata) {%>WW
                                                                <td class="text-end text-muted fw-bold" colspan="1">
                                                                    <%} else {%>
                                                                <td class="text-end text-muted fw-bold" colspan="2">
                                                                    <%}%>
                                                                    <form action="Operations" method="POST" target="_blank">
                                                                        <input type="hidden" name="type" value="SCARICAISTANZAFIRMATA" />
                                                                        <input type="hidden" name="codice_istanza" value="<%=i1.getCodice()%>" />
                                                                        <button type="submit" class="btn btn-sm btn-bg-light btn-primary"><i class="fa fa-file-download"></i> VISUALIZZA DOCUMENTO FIRMATO</button>
                                                                    </form>
                                                                </td>
                                                                <%if (!istanzapresentata) {%>
                                                                <td class="text-end text-muted fw-bold" colspan="1">
                                                                    <form action="Operations" method="POST" >
                                                                        <input type="hidden" name="type" value="ELIMINAISTANZAFIRMATA" />
                                                                        <input type="hidden" name="codice_istanza" value="<%=i1.getCodice()%>" />
                                                                        <button type="submit" class="btn btn-sm btn-bg-light btn-danger"><i class="fa fa-remove"></i> ELIMINA DOCUMENTO FIRMATO</button>
                                                                    </form>
                                                                </td>

                                                                <%}%>
                                                            </tr>

                                                            <%
                                                                if (istanzapresentata) {%>
                                                            <tr>
                                                                <td colspan="4">
                                                                    <div class="alert alert-success">
                                                                        <i class="fa fa-info-circle"></i> Istanza presentata correttamente in data <b><%=i1.getDatainvio()%></b>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <%} else {
                                                            %>
                                                            <tr>
                                                                <td colspan="4">
                                                                    <form action="Operations" method="POST">
                                                                        <input type="hidden" name="type" value="SENDISTANZA" />
                                                                        <input type="hidden" name="codice_istanza" value="<%=i1.getCodice()%>" />
                                                                        <button type="submit" 
                                                                                class="btn btn-bg-light btn-success" style="width: 100%;"><i class="fa fa-arrow-right"></i> INVIA ISTANZA</button>
                                                                    </form>
                                                                </td>
                                                            </tr>
                                                            <%}%>

                                                            <%if (istanzaaccettata) {%>
                                                            <tr>
                                                                <td colspan="4">
                                                                    <div class="alert alert-success">
                                                                        <i class="fa fa-info-circle"></i> Istanza <b>ACCETTATA</b> in data <b><%=i1.getDatagestione()%></b> &nbsp;
                                                                        <a href="#" class="btn btn-sm btn-bg-light btn-success" data-bs-toggle='tooltip' title='VISUALIZZA DECRETO' 
                                                                           onclick="return document.forms['decr_<%=i1.getCodice()%>'].submit();">
                                                                            <i class="fa fa-file-pdf"></i></a>
                                                                        <form action="Operations" method="POST" target="_blank" name="decr_<%=i1.getCodice()%>">
                                                                            <input type="hidden" name="type" value="SCARICADECRETOISTANZA" />
                                                                            <input type="hidden" name="codice_istanza" value="<%=i1.getCodice()%>" />
                                                                        </form>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <%}%>
                                                            <%}%>
                                                            <%} else {%>
                                                            <tr>
                                                                <td>
                                                                    <div class="symbol symbol-50px me-2">
                                                                        <span class="symbol-label bg-light-primary">
                                                                            <!--begin::Svg Icon | path: icons/duotune/ecommerce/ecm002.svg-->
                                                                            <span class="symbol-label text-primary bg-light-primary">
                                                                                <i class="fa fa-hourglass-2 fa-2x"></i>
                                                                            </span>
                                                                            <!--end::Svg Icon-->
                                                                        </span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <a href="US_compilacorsi.jsp" class="text-dark fw-bolder text-hover-primary mb-1 fs-6" 
                                                                       data-bs-toggle="tooltip" data-bs-placement="top" 
                                                                       title="Istanza Pronta">
                                                                        1) Elenco Corsi di Formazione
                                                                    </a>
                                                                </td>                                                                
                                                                <td class="text-end text-muted fw-bold" colspan="2">Corsi Attuali - <%=corsi_attuali%></td>
                                                                <td class="text-end text-muted fw-bold" colspan="1">
                                                                    <%if (corsi_attuali > 0) {%>
                                                                    <form action="Operations" method="POST">
                                                                        <input type="hidden" name="type" value="SALVACORSI" />
                                                                        <input type="hidden" name="codice_istanza" value="<%=i1.getCodice()%>" />
                                                                        <button type="submit" class="btn btn-sm btn-bg-light btn-success"><i class="fa fa-save"></i> Salva </button>
                                                                    </form>
                                                                    <%} else {%>
                                                                    <a onclick="return false;" 
                                                                       class="btn btn-sm btn-bg-light btn-color-muted"><i class="fa fa-save"></i> Salva</a>
                                                                    <%}%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <div class="symbol symbol-50px me-2">
                                                                        <span class="symbol-label bg-light-danger">
                                                                            <!--begin::Svg Icon | path: icons/duotune/ecommerce/ecm002.svg-->
                                                                            <span class="symbol-label text-danger bg-light-danger">
                                                                                <i class="fa fa-exclamation-triangle fa-2x"></i>
                                                                            </span>
                                                                            <!--end::Svg Icon-->
                                                                        </span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <a href="#" class="text-dark fw-bolder text-hover-primary mb-1 fs-6">2) Istanza</a>
                                                                </td>
                                                                <td class="text-end text-muted fw-bold" colspan="3">Necessaria compilazione di una o pi&#249; fasi precedenti</td>
                                                            </tr>
                                                            <%}%>
                                                        </tbody>
                                                        <!--end::Table body-->
                                                    </table>
                                                    <!--end::Table-->
                                                </div>
                                                <!--end::Table container-->
                                            </div>
                                            <!--begin::Body-->
                                        </div>
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
        <script src="assets/plugins/custom/datatables/datatables.bundle.js"></script>
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