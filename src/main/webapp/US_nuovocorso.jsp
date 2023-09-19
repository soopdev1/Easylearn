<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.Altropersonale"%>
<%@page import="rc.soop.sic.jpa.Allievi"%>
<%@page import="rc.soop.sic.jpa.User"%>
<%@page import="rc.soop.sic.jpa.SoggettoProponente"%>
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
        <title><%=Constant.NAMEAPP%>: Nuovo corso</title>
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
                                                SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
                                                List<Allievi> allievi = en.getAllieviSoggettoAvvioCorso(so);
                                                List<Istanza> accettate = en.getIstanzeAccettateAvvioCorsi(session);
                                                List<Altropersonale> ap_all = en.list_all_AltroPersonale();
                                                List<Altropersonale> altrop = en.getAltroPersonale(ap_all);
                                            %>
                                            <!--begin::Body-->
                                            <form action="Operations" method="POST" onsubmit="return checkdatisalvati();">
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
                                                                            class="form-select form-select-solid form-select-lg fw-bold" name="ISTANZA" id="ISTANZA" required>
                                                                        <option value="">Scegli...</option>
                                                                        <%for (Istanza is1 : accettate) {
                                                                                if (is1.getDecreto() == null) {
                                                                                    continue;
                                                                                }
                                                                                String DDS = is1.getDecreto().split("§")[0];
                                                                                String DDSDATA = Utils.datemysqltoita(is1.getDecreto().split("§")[1]);
                                                                        %>
                                                                        <option value="<%=is1.getIdistanza()%>">
                                                                            ID: <%=is1.getIdistanza()%> - 
                                                                            PROTOCOLLO <%=is1.getProtocollosoggetto()%> DEL <%=is1.getProtocollosoggettodata()%> - 
                                                                            D.D.S. n. <%=DDS%> DEL <%=DDSDATA%></option>
                                                                            <%}%>
                                                                    </select>
                                                                    <small class="form-text text-muted">Elenco Istanza Autorizzate.</small>
                                                                </div>
                                                                <!--end::Col-->
                                                                <!--begin::Col-->
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Row-->
                                                        </div>

                                                    </div>
                                                    <div class="row mb-6">
                                                        <label class="col-lg-4 col-form-label required fw-bold fs-6">TIPOLOGIA CORSO</label>
                                                        <div class="col-lg-8">
                                                            <!--begin::Row-->
                                                            <div class="row">
                                                                <!--begin::Col-->
                                                                <div class="col-lg-12 fv-row">
                                                                    <select aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                                            class="form-select form-select-solid form-select-lg fw-bold" 
                                                                            name="CORSO" id="CORSO" required >
                                                                    </select>
                                                                    <small id="CORSOHelp" class="form-text text-muted"></small>
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
                                                        <label class="col-lg-4 col-form-label required fw-bold fs-6">Data Inizio <small id="INFODATAINIZIO"></small></label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8">
                                                            <!--begin::Row-->
                                                            <div class="row">
                                                                <!--begin::Col-->
                                                                <div class="col-lg-12 fv-row">
                                                                    <input type="date" name="DATAINIZIO" id="DATAINIZIO" 
                                                                           class="form-control form-control-lg form-control-solid mb-3 mb-lg-0" 
                                                                           required/>
                                                                    <small class="form-text text-muted">Minimo 15 giorni a partire da oggi.</small>
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
                                                                    <input type="date" name="DATAFINE" id="DATAFINE" 
                                                                           class="form-control form-control-lg form-control-solid mb-3 mb-lg-0" 
                                                                           required/>
                                                                    <small id="DATAFINEHelp" class="form-text text-muted"></small>
                                                                </div>
                                                                <input type="hidden" id="checkend" value="0" />
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
                                                        </label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8 fv-row">
                                                            <select aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                                    class="form-select form-select-solid form-select-lg fw-bold" name="DOCENTI" id="DOCENTI" required multiple>
                                                            </select>
                                                            <small id="DOCENTIHelp" class="form-text text-muted"></small>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                            <span class="required">Elenco Allievi</span>
                                                        </label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8 fv-row">
                                                            <select name="ALLIEVI" id="ALLIEVI" aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                                    class="form-select form-select-solid form-select-lg fw-bold" required multiple>
                                                                <option value="">Scegli...</option>
                                                                <%for (Allievi al1 : allievi) {%>
                                                                <option value="<%=al1.getIdallievi()%>">
                                                                    <%=al1.getCodicefiscale()%> - <%=al1.getCognome()%> <%=al1.getNome()%>
                                                                </option>                                                                
                                                                <%}%>
                                                            </select>
                                                            <small id="ALLIEVIHelp" class="form-text text-muted"></small>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                            <span class="required">Direttore Corso</span>
                                                        </label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8 fv-row">
                                                            <input type="text" name="DIRETTORE" id="DIRETTORE" 
                                                                           class="form-control form-control-lg form-control-solid mb-3 mb-lg-0" 
                                                                           required/>
                                                                    <small class="form-text text-muted">Inserire Cognome e Nome del Direttore del Corso</small>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>        
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                            <span class="required">Elenco Altro Personale</span>
                                                        </label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-8 fv-row">
                                                            <select name="ALTROP" id="ALTROP" aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                                    class="form-select form-select-solid form-select-lg fw-bold" required multiple>
                                                                <option value="">Scegli...</option>
                                                                <%for (Altropersonale al1 : altrop) {%>
                                                                <option value="<%=al1.getIdaltropersonale()%>">
                                                                    <%=al1.getCognome()%> <%=al1.getNome()%> - <%=al1.getCodicefiscale()%> (<%=al1.getProfiloprof()%>)
                                                                </option>                                                                
                                                                <%}%>
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
                                                            <i class="fa fa-save"></i> SALVA DATI E CONTINUA
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
        <script src="assets/js/US_nuovocorso.js"></script>

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