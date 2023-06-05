<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.Lingua"%>
<%@page import="rc.soop.sic.jpa.Competenze_Trasversali"%>
<%@page import="rc.soop.sic.jpa.Tipologia_Percorso"%>
<%@page import="rc.soop.sic.jpa.EntityOp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.soop.sic.jpa.Corso"%>
<%@page import="rc.soop.sic.jpa.Istanza"%>
<%@page import="rc.soop.sic.jpa.Scheda_Attivita"%>
<%@page import="rc.soop.sic.jpa.Repertorio"%>
<%@page import="rc.soop.sic.jpa.Sede"%>
<%@page import="rc.soop.sic.jpa.User"%>
<%@page import="rc.soop.sic.jpa.Livello_Certificazione"%>
<%@page import="rc.soop.sic.Engine"%>
<%@page import="java.util.List"%>
<%@page import="rc.soop.sic.jpa.Certificazione"%>
<%@page import="rc.soop.sic.Utils"%>
<%@page import="rc.soop.sic.Constant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <%
        int verifysession = Utils.checkSession(session, request);
        switch (verifysession) {
            case 1: {
                EntityOp eo = new EntityOp();
                Long idc1 = Long.valueOf(Utils.dec_string(Utils.getRequestValue(request, "idcorso")));
                Corso co1 = eo.getEm().find(Corso.class, idc1);
                User u1 = (User) session.getAttribute("us_memory");
                List<Competenze_Trasversali> comp_tr = Engine.elenco_competenze_trasversali();
                List<Lingua> language = Engine.elenco_lingua();
                double el_perc = 0.0D;
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
            <div class="col-xl-12">
                <!--begin::Tables Widget 3-->
                <div class="card h-xl-100">
                    <!--begin::Header-->
                    <div class="card-header border-0 pt-5">
                        <h3 class="card-title align-items-start flex-column">
                            <span class="card-label fw-bolder fs-3 mb-1">Corso di formazione</span>
                        </h3>
                    </div>
                    <div class="card-body py-3">
                        <div class="row">
                            <label class="col-lg-4 col-form-label fw-bold fs-6">
                                <span><b>Tipologia Percorso</b></span><br/>
                                <span><%=co1.getTipologiapercorso().getNometipologia()%></span>
                            </label>
                            <label class="col-lg-4 col-form-label fw-bold fs-6">
                                <span><b>Nome Percorso</b></span><br/>
                                <span><%=co1.getRepertorio().getDenominazione()%></span>
                            </label>
                            <label class="col-lg-4 col-form-label fw-bold fs-6">
                                <span><b>Dati Percorso</b></span><br/>
                                <span><%=co1.getSchedaattivita().getTipologiapercorso()%></span>
                            </label>
                        </div>
                        <div class="row">
                            <label class="col-lg-4 col-form-label fw-bold fs-6">
                                <span><b>Durata in Ore (Iniziale - Complessiva)</b></span><br/>
                                <span><%=co1.getDurataore()%> - <b id="completeduration"><%=co1.getDurataore()%></b></span>
                            </label>
                            <label class="col-lg-4 col-form-label fw-bold fs-6">
                                <span><b>Ore di Stage</b></span><br/>
                                <span><%=co1.getStageore()%></span>
                            </label>
                            <label class="col-lg-4 col-form-label fw-bold fs-6">
                                <span><b>eLearning Percentuale - Ore</b></span><br/>
                                <span><%=co1.getElearningperc()%> - <%=Utils.getPercentuale(co1.getDurataore(),co1.getElearningperc())%></span>
                            </label>
                        </div>
                        <hr>
                        <div class="row">
                            <p class="text-center mb-4">Progettazione di Dettaglio ed Unità Formative</p>
                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                <span class="text-danger">Competenze Trasversali</span>
                            </label>
                            <hr>
                            <div class="row col-md-12">
                                <label class="col-md-4 col-form-label fw-bold">
                                    <span class="text-dark">Titolo del modulo / Competenza</span>
                                </label>
                                <label class="col-md-2 col-form-label fw-bold">
                                    <span class="text-dark">Requisito Ingresso</span>
                                </label>
                                <label class="col-md-5 col-form-label fw-bold">
                                    <span class="text-dark">Descrizione</span>
                                </label>
                                <label class="col-md-1 col-form-label fw-bold">
                                    <span class="text-dark">Ore</span>
                                </label>
                            </div>
                            <%
                                for (Competenze_Trasversali ct : comp_tr) {%>
                            <div class="row row-border col-md-12 p-5">
                                <!--begin::Label-->
                                <label class="col-md-4">
                                    <span class="required"><%=ct.getDescrizione()%></span>
                                </label>
                                <!--end::Label-->
                                <!--begin::Col-->
                                <div class="col-md-2 fv-row">
                                    <select name="ctreqing_<%=ct.getIdcompetenze()%>" aria-label="Scegli..." 
                                            data-control="select2" data-placeholder="Scegli..." 
                                            class="form-select" onchange="return selezionaCT('<%=ct.getIdcompetenze()%>');"
                                            required>
                                        <%if (ct.isRequisito_ingresso()) {%>
                                        <option value="">Scegli...</option>
                                        <option value="1">SI</option>
                                        <%}%>
                                        <option value="0">NO</option>
                                    </select>
                                </div>
                                <%if (ct.getDescrizione().toLowerCase().startsWith("ling")) {%>

                                <div class="col-md-2 fv-row">   
                                    <select name="ctlingua_<%=ct.getIdcompetenze()%>" aria-label="Scegli..." 
                                            data-control="select2" data-placeholder="Lingua Straniera" 
                                            class="form-select"
                                            required>
                                        <option value="">Scegli Lingua</option>
                                        <%for (Lingua l1 : language) {%>
                                        <option value="<%=l1.getCodicelingua()%>"><%=l1.getNome()%></option>
                                        <%}%>
                                    </select>
                                </div>
                                <div class="col-md-3 fv-row">
                                    <input type="text" name="ctdescr_<%=ct.getIdcompetenze()%>" id="ctdescr_<%=ct.getIdcompetenze()%>"
                                           class="form-control" 
                                           placeholder="..." required/>
                                </div>
                                <label class="col-md-1 col-form-label">
                                    <span id="ctdurata_<%=ct.getIdcompetenze()%>"><%=ct.getDurataore()%></span>
                                </label>
                                <%} else {%>
                                <div class="col-md-5 fv-row">
                                    <input type="text" name="ctdescr_<%=ct.getIdcompetenze()%>" id="ctdescr_<%=ct.getIdcompetenze()%>"
                                           class="form-control" 
                                           placeholder="..." required/>
                                </div>

                                <label class="col-md-1 col-form-label">
                                    <span id="ctdurata_<%=ct.getIdcompetenze()%>"><%=ct.getDurataore()%></span>
                                </label>
                                <%}%>
                            </div>
                            <%}%>
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