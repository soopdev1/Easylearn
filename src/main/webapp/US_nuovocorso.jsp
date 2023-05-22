<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

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
                        <jsp:include page="menu/menuUS1.jsp" /> 
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
                                    <%String esito = Utils.getRequestValue(request, "esito");
                                        if (esito.equals("ADDED")) {%>
                                    <div class="alert alert-success">
                                        <i class="fa fa-check-circle"></i> CORSO AVVIATO CON SUCCESSO.
                                    </div>
                                    <hr>
                                    <%}%>
                                    <!--begin::Col-->
                                    <div class="col-xl-12">
                                        <!--begin::Tables Widget 3-->
                                        <div class="card h-xl-100">
                                            <!--begin::Header-->
                                            <div class="card-header border-0 pt-5">
                                                <h3 class="card-title align-items-start flex-column">
                                                    <span class="card-label fw-bolder fs-3 mb-1">AVVIA NUOVO CORSO</span>
                                                    <span class="text-muted mt-1 fw-bold fs-7">Avvio di un nuovo corso facente parte di una Istanza di autorizzazione allo svolgimento ACCETTATA.</span>
                                                </h3>
                                            </div>
                                            <!--end::Header-->

                                            <%
                                                EntityOp en = new EntityOp();
                                                List<Istanza> accettate = en.getIstanzeAccettateAvvioCorsi(session);
                                            %>
                                            <!--begin::Body-->
                                            <form action="Operations" method="POST">
                                                <input type="hidden" name="type" value="AVVIANUOVOCORSO" />
                                                <div class="card-body py-3">
                                                    <!--begin::Table container-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-4 col-form-label required fw-bold fs-6">ISTANZA DI AUTORIZZAZIONE</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8">
                                                            <!--begin::Row-->
                                                            <div class="row">
                                                                <!--begin::Col-->
                                                                <div class="col-lg-12 fv-row">
                                                                    <select aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                                            class="form-select form-select-solid form-select-lg fw-bold" name="codiceistanza" required>
                                                                        <%for (Istanza is1 : accettate) {%>
                                                                        <option value="<%=is1.getCodiceistanza()%>">PROTOCOLLO <%=is1.getProtocollosoggetto()%> DEL <%=is1.getProtocollosoggettodata()%> - DECRETO n. 100 DEL  12/12/2022</option>
                                                                        <%}%>
                                                                    </select>
                                                                </div>
                                                                <!--end::Col-->
                                                                <!--begin::Col-->
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Row-->
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <hr>

                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-4 col-form-label required fw-bold fs-6">Data Inizio</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8">
                                                            <!--begin::Row-->
                                                            <div class="row">
                                                                <!--begin::Col-->
                                                                <div class="col-lg-12 fv-row">
                                                                    <input type="date" name="datainizio" 
                                                                           class="form-control form-control-lg form-control-solid mb-3 mb-lg-0" 
                                                                           required/>
                                                                </div>
                                                                <!--end::Col-->
                                                                <!--begin::Col-->
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Row-->
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-4 col-form-label required fw-bold fs-6">Data Fine</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8">
                                                            <!--begin::Row-->
                                                            <div class="row">
                                                                <!--begin::Col-->
                                                                <div class="col-lg-12 fv-row">
                                                                    <input type="date" name="datafine" 
                                                                           class="form-control form-control-lg form-control-solid mb-3 mb-lg-0" 
                                                                           required/>
                                                                </div>
                                                                <!--end::Col-->
                                                                <!--begin::Col-->
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Row-->
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>

                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                            <span class="required">Elenco Docenti</span>
                                                            <a onclick="return false;" data-bs-toggle="tooltip" data-bs-placement="top" title="Selezione nuovo codice dall'elenco">
                                                                <i class="fas fa-exclamation-circle ms-1 fs-7"></i>
                                                            </a>
                                                        </label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8 fv-row">
                                                            <select aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                                    class="form-select form-select-solid form-select-lg fw-bold" name="docenti" required multiple>
                                                                <option value="">Scegli...</option>
                                                                <option value="D1">Docente Numero Uno</option>
                                                                <option value="D2">Docente Numero Due</option>
                                                            </select>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                            <span class="required">Elenco Allievi</span>
                                                            <a onclick="return false;" data-bs-toggle="tooltip" data-bs-placement="top" title="Selezione nuovo codice dall'elenco">
                                                                <i class="fas fa-exclamation-circle ms-1 fs-7"></i>
                                                            </a>
                                                        </label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8 fv-row">
                                                            <select name="allievi" aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                                    class="form-select form-select-solid form-select-lg fw-bold" required multiple>
                                                                <option value="">Scegli...</option>
                                                                <option value="A1">Allievo Numero Uno</option>
                                                                <option value="A2">Allievo Numero Due</option>
                                                                <option value="A3">Allievo Numero Tre</option>
                                                                <option value="A4">Allievo Numero Quattro</option>
                                                                <option value="A5">Allievo Numero Cinque</option>
                                                                <option value="A6">Allievo Numero Sei</option>
                                                                <option value="A7">Allievo Numero Sette</option>
                                                                <option value="A8">Allievo Numero Otto</option>
                                                                <option value="A9">Allievo Numero Nove</option>                                                                
                                                            </select>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>                                                
                                                    <div class="container">
                                                        <div class="row">
                                                            <div class="col-12">
                                                                <div aria-live="polite" id="errorMsgContainer"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <p class="mb-0">
                                                        <button type="submit" class="btn btn-primary btn-circled">
                                                            <i class="fa fa-save"></i> SALVA DATI
                                                        </button>
                                                    </p>
                                                    <!--end::Table container-->
                                                </div>
                                            </form>
                                            <!--begin::Body-->
                                        </div>
                                        <!--end::Tables Widget 3-->
                                    </div>

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