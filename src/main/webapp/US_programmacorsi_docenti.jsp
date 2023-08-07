<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.Docente"%>
<%@page import="rc.soop.sic.jpa.Conoscenze"%>
<%@page import="rc.soop.sic.jpa.Abilita"%>
<%@page import="rc.soop.sic.jpa.Competenze"%>
<%@page import="rc.soop.sic.jpa.Calendario_Formativo"%>
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
                String idcorso = (String) session.getAttribute("ses_idcorso");
                EntityOp eo = new EntityOp();
                Long idc1 = Long.valueOf(Utils.dec_string(idcorso));
                Corso co1 = eo.getEm().find(Corso.class, idc1);
                List<Docente> eldoc = eo.findAll(Docente.class);
                List<Calendario_Formativo> calendar = eo.calendario_formativo_corso(co1);
    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Gestione istanza</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="assets/css/gfont.css" />
        <!--end::Fonts-->
        <!--begin::Page Vendor Stylesheets(used by this page)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link href="assets/plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
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
            <form method="POST" action="Operations" onsubmit="return verificasalvataggiodocentemodulo();">
                <input type="hidden" name="type" value="MODIFICAPIANIFICAZIONEDOCENTI"/>
                <input type="hidden" id="idcorsodasalvare" name="idcorsodasalvare" value="<%=co1.getIdcorso()%>"/>
                <div class="col-xl-12">
                    <div class="card h-xl-100">
                        <div class="card-header border-0 pt-5">
                            <h3 class="card-title align-items-start flex-column">
                                <span class="card-label fw-bolder fs-3 mb-1">INSERISCI DETTAGLI DOCENTE/MODULI</span>
                            </h3>
                            <button class="btn btn-lg btn-success"><i class="fa fa-save"></i> SALVA DATI</button>
                        </div>
                        <div class="card-body py-3">
                            <div class="row col-md-12 col-form-label">
                                <div class="alert alert-danger"  id="messageerror" style="display: none;">
                                    ERRORE: 
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>SELEZIONA DOCENTE</b></span>
                                </label>
                                <div class="col-form-label fs-6">
                                    <select 
                                        id="docente"
                                        name="docente" aria-label="Scegli..." 
                                        data-control="select2" data-placeholder="Scegli..." 
                                        class="form-select">
                                        <option value=""></option>
                                        <%for (Docente d1 : eldoc) {%>
                                        <option value="<%=d1.getIddocente()%>"><%=d1.getCognome()%> <%=d1.getNome()%> - <%=d1.getCodicefiscale()%></option>
                                        <%}%>
                                    </select>
                                </div>
                                <hr>
                                <label class="col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>CALENDARIO FORMATIVO</b></span>
                                </label>
                                <div class="card-body py-3">
                                    <!--begin::Table container-->
                                    <div class="table-responsive checkboxesr">
                                        <!--begin::Table-->
                                        <table class="table align-middle gy-3 table-bordered table-hover" id="tab_dt1" style="border-bottom: 2px;">
                                            <!--begin::Table head-->
                                            <thead>
                                                <tr>
                                                    <th class="p-2 w-50px">#</th>
                                                    <th class="p-2 w-50px">Tipologia</th>
                                                    <th class="p-2 w-150px">Descrizione</th>
                                                    <th class="p-2 w-50px">Ore Associabili</th>
                                                    <th class="p-2 w-50px">Associa Modulo</th>
                                                    <th class="p-2 w-50px">Numero ore previste per il docente</th>
                                                </tr>
                                            </thead>
                                            <!--end::Table head-->
                                            <!--begin::Table body-->
                                            <tbody>
                                                <%
                                                    int indexmodules = 0;
                                                    for (Calendario_Formativo c1 : calendar) {
                                                        indexmodules++;
                                                        if (c1.getTipomodulo().equals("BASE")) {%>
                                            <input type="hidden" class="value_ore" 
                                                   id="ore_modules_<%=c1.getIdcalendarioformativo()%>" 
                                                   value="<%=c1.getOre()%>" />
                                            <tr>
                                                <td class="p-2 w-50px"><%=c1.getCodicemodulo()%></td>
                                                <td class="p-2 w-50px"><%=c1.getTipomodulo()%></td>
                                                <td class="p-2 w-150px"><%=c1.getCompetenzetrasversali().getDescrizione()%></td>
                                                <td class="p-2 w-50px"><%=Utils.roundDoubleandFormat(c1.getOre(), 1)%></td>
                                                <td class="p-2 w-50px">
                                                    <div class="form-check form-switch">
                                                        <input class="form-check-input" type="checkbox" role="switch" 
                                                               id="CH_<%=c1.getIdcalendarioformativo()%>"
                                                               name="CH_<%=c1.getIdcalendarioformativo()%>" />
                                                    </div>
                                                </td>
                                                <td class="p-2 w-50px">
                                                    <input type="text" 
                                                           name="ORE_<%=c1.getIdcalendarioformativo()%>"
                                                           id="ORE_<%=c1.getIdcalendarioformativo()%>"
                                                           class="form-control decimalvalue" />
                                                </td>
                                            </tr>
                                            <%} else if (c1.getTipomodulo().equals("MODULOFORMATIVO")) {%>
                                            <input type="hidden" 
                                                   class="value_ore" 
                                                   id="ore_modules_<%=c1.getIdcalendarioformativo()%>"
                                                   value="<%=c1.getOre()%>" />
                                            <tr>
                                                <td class="p-2 w-50px"><%=c1.getCodicemodulo()%></td>
                                                <td class="p-2 w-50px">MODULO FORMATIVO</td>
                                                <td class="p-2 w-150px"><%=c1.getNomemodulo()%></td>
                                                <td class="p-2 w-50px"><%=Utils.roundDoubleandFormat(c1.getOre(), 1)%></td>
                                                <td class="p-2 w-50px">
                                                    <div class="form-check form-switch">
                                                        <input class="form-check-input" type="checkbox" role="switch" 
                                                               id="CH_<%=c1.getIdcalendarioformativo()%>"
                                                               name="CH_<%=c1.getIdcalendarioformativo()%>" />
                                                    </div>
                                                </td>
                                                <td class="p-2 w-50px">
                                                    <input type="text" 
                                                           name="ORE_<%=c1.getIdcalendarioformativo()%>"
                                                           id="ORE_<%=c1.getIdcalendarioformativo()%>"
                                                           class="form-control decimalvalue" />
                                                </td>
                                            </tr>
                                            <%}%>
                                            <%}%>
                                            <input type="hidden" id="lengthmodules" value="<%=indexmodules%>" /> 
                                            </tbody>
                                        </table>
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
        <script src="assets/js/US_programmacorsi_docenti.js"></script>

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