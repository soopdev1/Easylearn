<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>
<%@page import="rc.soop.sic.jpa.Calendario_Lezioni"%>
<%@page import="rc.soop.sic.jpa.Calendario_Formativo"%>
<%@page import="rc.soop.sic.jpa.CorsoAvviato_AltroPersonale"%>
<%@page import="rc.soop.sic.jpa.Allievi"%>
<%@page import="rc.soop.sic.jpa.CorsoAvviato_Docenti"%>
<%@page import="java.util.List"%>
<%@page import="rc.soop.sic.jpa.Docente"%>
<%@page import="rc.soop.sic.jpa.Corsoavviato"%>
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
                String idistS = Utils.getRequestValue(request, "idcorso");
                if (idistS.equals("")) {
                    idistS = (String) session.getAttribute("ses_idcorso");
                } else {
                    session.setAttribute("ses_idcorso", idistS);
                }
                EntityOp eo = new EntityOp();
                Corsoavviato is1 = eo.getEm().find(Corsoavviato.class, Long.valueOf(idistS));
                List<CorsoAvviato_Docenti> avv_doc = eo.list_cavv_docenti(is1);
                List<CorsoAvviato_AltroPersonale> avv_altrop = eo.list_cavv_altropers(is1);
                List<Allievi> allievi = eo.getAllieviCorsoAvviato(is1);
                List<Calendario_Formativo> cal_istanza = eo.calendario_formativo_corso(is1.getCorsobase());
                List<Calendario_Lezioni> lezioni = eo.calendario_lezioni_corso(is1);
                Utils.confrontaLezioniCalendario(lezioni, cal_istanza);
    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Dettagli corso</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="assets/css/gfont.css" />
        <!--end::Fonts-->
        <!--begin::Page Vendor Stylesheets(used by this page)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="assets/plugins/jquery-confirm.3.3.2.min.css">
        <link href="assets/plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />

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
                            <span class="card-label fw-bolder fs-3 mb-1">Dettagli Corso di formazione ID: <%=is1.getIdcorsoavviato()%> - <%=is1.getCorsobase().getIstanza().getTipologiapercorso().getNometipologia()%> - <u><%=is1.getCorsobase().getRepertorio().getDenominazione()%></u></span>
                        </h3>
                    </div>
                    <div class="card-body py-3">
                        <div class="row col-md-12">
                            <label class="col-md-2 col-form-label fw-bold">
                                <span class="text-danger"><b>SOGGETTO PROPONENTE:</b></span>
                            </label>
                            <label class="col-md-4 col-form-label fw-bold">
                                <span><b><%=is1.getCorsobase().getSoggetto().getRAGIONESOCIALE()%></b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold fs-6">
                                <span class="text-danger"><b>DIRETTORE:</b></span>
                            </label>
                            <label class="col-md-4 col-form-label fw-bold">
                                <span><b><%=is1.getDirettore().getCognome()%> <%=is1.getDirettore().getNome()%></b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold fs-6">
                                <span class="text-danger"><b>DATA INIZIO:</b></span>
                            </label>
                            <label class="col-md-4 col-form-label fw-bold">
                                <span><b><%=Constant.sdf_PATTERNDATE4.format(is1.getDatainizio())%></b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold fs-6">
                                <span class="text-danger"><b>DATA FINE:</b></span>
                            </label>
                            <label class="col-md-4 col-form-label fw-bold">
                                <span><b><%=Constant.sdf_PATTERNDATE4.format(is1.getDatafine())%></b></span>
                            </label>
                            <hr>
                            <label class="col-md-2 col-form-label fw-bold fs-6">
                                <span class="text-danger"><b>ELENCO DOCENTI:</b></span>
                            </label>
                            <label class="col-md-10 col-form-label">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>Cognome</th>
                                            <th>Nome</th>
                                            <th>Codice Fiscale</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%for (CorsoAvviato_Docenti d1 : avv_doc) {%>
                                        <tr>
                                            <td>
                                                <%=d1.getDocente().getCognome()%>
                                            </td>
                                            <td>
                                                <%=d1.getDocente().getNome()%>
                                            </td>
                                            <td>
                                                <%=d1.getDocente().getCodicefiscale()%>
                                            </td>
                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                            </label>
                            <hr>
                            <label class="col-md-2 col-form-label fw-bold fs-6">
                                <span class="text-danger"><b>ELENCO ALLIEVI</b></span>
                            </label>
                            <label class="col-md-10 col-form-label">
                                <table class="table table-hover table-row-bordered">
                                    <thead>
                                        <tr>
                                            <th>Cognome</th>
                                            <th>Nome</th>
                                            <th>Codice Fiscale</th>
                                            <th>Stato</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%for (Allievi a1 : allievi) {%>
                                        <tr>
                                            <td>
                                                <%=a1.getCognome()%>
                                            </td>
                                            <td>
                                                <%=a1.getNome()%>
                                            </td>
                                            <td>
                                                <%=a1.getCodicefiscale()%>
                                            </td>
                                            <td>
                                                <%=Utils.getEtichettastato(a1.getStatoallievo())%>
                                            </td>

                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                            </label>
                            <hr>
                            <label class="col-md-2 col-form-label fw-bold fs-6">
                                <span class="text-danger"><b>ELENCO ALTRO PERSONALE</b></span>
                            </label>
                            <label class="col-md-10 col-form-label">
                                <table class="table table-hover table-row-bordered table-rounded">
                                    <thead>
                                        <tr>
                                            <th>Cognome</th>
                                            <th>Nome</th>
                                            <th>Codice Fiscale</th>
                                            <th>Profilo professionale</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%for (CorsoAvviato_AltroPersonale a1 : avv_altrop) {%>
                                        <tr>
                                            <td>
                                                <%=a1.getAltropersonale().getCognome()%>
                                            </td>
                                            <td>
                                                <%=a1.getAltropersonale().getNome()%>
                                            </td>
                                            <td>
                                                <%=a1.getAltropersonale().getCodicefiscale()%>
                                            </td>
                                            <td>
                                                <%=a1.getAltropersonale().getProfiloprof()%>
                                            </td>

                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                            </label>
                            <hr>
                            <label class="col-form-label fw-bold fs-6">CALENDARIO FORMATIVO | 
                                <a class="btn btn-primary btn-sm fan1" href="US_calendariolezioni.jsp"
                                   data-fancybox data-type='iframe' 
                                   data-bs-toggle="tooltip" title="INSERISCI NUOVA LEZIONE" 
                                   data-preload='false' data-width='75%' data-height='75%' id="addcalendariobutton">
                                    <i class="fa fa-plus-circle" ></i> Aggiungi lezione</a></label>
                            <div class="row col-md-12">

                                <div class="col-md-7" style="border-right: 1px dashed #333;">
                                    <label class="col-form-label fw-bold fs-6">LEZIONI INSERITE: <%=lezioni.size()%></label>
                                    <div class="table-responsive">
                                        <!--begin::Table-->
                                        <table class="table align-middle gy-3 table-bordered table-hover" id="tab_dt0" style="border-bottom: 2px;">
                                            <!--begin::Table head-->
                                            <thead>
                                                <tr>
                                                    <th class="p-2 w-10px">#</th>
                                                    <th class="p-2 w-50px">Data (Orario)</th>
                                                    <th class="p-2 w-50px">Docente</th>
                                                    <th class="p-2 w-50px">Modulo (Ore)</th>
                                                </tr>
                                            </thead>
                                            <!--end::Table head-->
                                            <!--begin::Table body-->
                                            <tbody>
                                                <%
                                                    int indice = 0;
                                                    for (Calendario_Lezioni c1 : lezioni) {
                                                    indice++;%>
                                                <tr>
                                                    <td class="p-2 w-10px"><%=indice%></td>
                                                    <td class="p-2 w-50px"><%=Constant.sdf_PATTERNDATE4.format(c1.getDatalezione())%> (<%=c1.getOrainizio()%> - <%=c1.getOrafine()%>)</td>
                                                    <td class="p-2 w-50px"><%=c1.getDocente().getCognome()%> <%=c1.getDocente().getNome()%></td>
                                                    <td class="p-2 w-50px"><%=c1.getCalendarioformativo().getNomemodulo()%> (<%=Utils.roundDoubleandFormat(c1.getOre(), 1)%>)</td>
                                                </tr>
                                                <%}%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="col-md-5">
                                    <label class="col-form-label fw-bold fs-6">PRESENTE IN ISTANZA</label>
                                    <!--begin::Table container-->
                                    <div class="table-responsive">
                                        <!--begin::Table-->
                                        <table class="table align-middle gy-3 table-bordered table-hover" id="tab_dt1" style="border-bottom: 2px;">
                                            <!--begin::Table head-->
                                            <thead>
                                                <tr>
                                                    <th class="p-2 w-50px">Codice</th>
                                                    <th class="p-2 w-50px">Tipologia</th>
                                                    <th class="p-2 w-150px">Descrizione</th>
                                                    <th class="p-2 w-50px">Ore Totali</th>
                                                </tr>
                                            </thead>
                                            <!--end::Table head-->
                                            <!--begin::Table body-->
                                            <tbody>
                                                <%
                                                    for (Calendario_Formativo c1 : cal_istanza) {
                                                        if (c1.getTipomodulo().equals("BASE")) {%>
                                                <tr>
                                                    <td class="p-2 w-50px"><%=c1.getCodicemodulo()%></td>
                                                    <td class="p-2 w-50px"><%=c1.getTipomodulo()%></td>
                                                    <td class="p-2 w-150px"><%=c1.getCompetenzetrasversali().getDescrizione()%></td>
                                                    <td class="p-2 w-50px"><%=Utils.roundDoubleandFormat(c1.getOre(), 1)%></td>
                                                </tr>
                                                <%} else if (c1.getTipomodulo().equals("MODULOFORMATIVO")) {%>
                                                <tr>
                                                    <td class="p-2 w-50px"><%=c1.getCodicemodulo()%></td>
                                                    <td class="p-2 w-50px">MODULO FORMATIVO</td>
                                                    <td class="p-2 w-150px"><%=c1.getNomemodulo()%></td>
                                                    <td class="p-2 w-50px"><%=Utils.roundDoubleandFormat(c1.getOre(), 1)%></td>
                                                </tr>
                                                <%}%>
                                                <%}%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
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
        <script src="assets/plugins/DataTables/jquery-3.5.1.js"></script>
        <script src="assets/plugins/DataTables/jquery.dataTables.min.js"></script>
        <script src="assets/plugins/DataTables/datatables.min.js"></script>
        <script src="assets/plugins/DataTables/date-eu.js"></script>
        <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
        <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <script src="assets/js/US_showcorsoavviato.js"></script>

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