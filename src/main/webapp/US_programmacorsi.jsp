<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.Path"%>
<%@page import="rc.soop.sic.jpa.Attrezzature"%>
<%@page import="rc.soop.sic.jpa.Docente"%>
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
                String idcorso = Utils.getRequestValue(request, "idcorso");
                if (idcorso.equals("")) {
                    idcorso = (String) session.getAttribute("ses_idcorso");
                }
                EntityOp eo = new EntityOp();
                Long idc1 = Long.valueOf(Utils.dec_string(idcorso));
                Corso co1 = eo.getEm().find(Corso.class, idc1);
                int attivact = Utils.parseIntR(eo.getEm().find(Path.class, "conf.min.attivact").getDescrizione());
                List<Competenze_Trasversali> comp_tr = (List<Competenze_Trasversali>) eo.findAll(Competenze_Trasversali.class);
                List<Lingua> language = eo.getLingue();
                List<Calendario_Formativo> calendar = eo.calendario_formativo_corso(co1);
                List<Calendario_Formativo> calendarlez = eo.calendario_formativo_corso_lezioni(co1);
                boolean calendariocompleto = Engine.calendario_completo(eo, co1);
                List<Docente> eldoc = eo.findAll(Docente.class);
                List<Docente> assegnati = eo.list_docenti_moduli(eldoc, calendar);
                int moduliassegnati = Engine.verificamoduliassegnati(assegnati);
                int modulidaassegnare = calendarlez.size() - moduliassegnati;
                Engine.verificacorsodainviare(co1, calendariocompleto, modulidaassegnare);
                //String costostimato = (co1.getCostostimatoallievo() == 0.0) ? "" : String.format("%.2f", co1.getCostostimatoallievo());
                double oretec = Utils.calcolaPercentuale(String.valueOf(co1.getDurataore()-co1.getStageore()), String.valueOf(60));
    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Gestione Istanza</title>
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

        <!--end::Global Stylesheets Bundle-->
    </head>
    <!--end::Head-->
    <!--begin::Body-->
    <body id="kt_body" class="header-fixed header-tablet-and-mobile-fixed" onload="competenzetrasv();">
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
                                        <input type="hidden" name="type" value="SALVAPIANIFICAZIONE"/>
                                        <input type="hidden" name="idcorsodasalvare" value="<%=co1.getIdcorso()%>"/>
                                        <div class="col-xl-12">
                                            <!--begin::Tables Widget 3-->
                                            <div class="card h-xl-100">
                                                <!--begin::Header-->
                                                <div class="card-header border-0 pt-5">
                                                    <h3 class="card-title align-items-start flex-column">
                                                        <span class="card-label fw-bolder fs-3 mb-1">ISTANZA ID <%=co1.getIstanza().getIdistanza()%> - Cordo di formazione: Progettazione di Dettaglio ed Unità Formative - <%=co1.getStatocorso().getHtmlicon()%></span>
                                                    </h3>
                                                </div>
                                                <div class="card-body py-3">
                                                    <div class="row">
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Tipologia Percorso</b></span><br/>
                                                            <span><%=co1.getTipologiapercorso().getNometipologia()%></span>
                                                        </label>
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Nome Percorso</b></span><br/>
                                                            <span><%=co1.getRepertorio().getDenominazione()%></span>
                                                        </label>
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Dati Percorso</b></span><br/>
                                                            <span><%=co1.getSchedaattivita().getTipologiapercorso()%></span>
                                                        </label>
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Sede Formativa</b></span><br/>
                                                            <span><%=co1.getSedescelta().getIndirizzo()%> - <%=co1.getSedescelta().getComune()%></span>
                                                        </label>
                                                    </div>
                                                    <div class="row">
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Protocollo (S.P.)</b></span><br/>
                                                            <span><%=co1.getIstanza().getProtocollosoggetto()%></span>
                                                        </label>
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Numero Edizioni</b></span><br/>
                                                            <span><%=co1.getQuantitarichiesta()%></span>
                                                        </label>
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Durata Corso (GIORNI)</b></span><br/>
                                                            <span><%=co1.getDuratagiorni()%></span>
                                                        </label>
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Stage</b></span><br/>
                                                            <span><%=co1.getStageore()%> ORE</span>
                                                        </label>
                                                    </div>
                                                    <input type="hidden" id="startduration" value="<%=(co1.getDurataore()-co1.getStageore())%>" />
                                                    <input type="hidden" id="stageduration" value="<%=co1.getStageore()%>" />
                                                    <input type="hidden" id="tecduration" value="<%=oretec%>" />

                                                    <div class="row">
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Durata in Ore AULA (Iniziale - Complessiva)</b></span><br/>
                                                            <span><%=(co1.getDurataore()-co1.getStageore())%> ORE - <b id="completeduration" class="text-primary"><%=co1.getDurataore()%></b><b class="text-primary">&nbsp;ORE</b></span>
                                                        </label>
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>Ore AULA Area Tecnica (Almeno il 60%)</b></span><br/>
                                                            <span>ALMENO <%=Utils.roundDoubleandFormat(oretec, 2)%></span>
                                                        </label>

                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>eLearning</b></span><br/>
                                                            <span><%=co1.getElearningperc()%> ORE</span>
                                                        </label>
                                                        <label class="col-lg-3 col-form-label fw-bold fs-6">
                                                            <span><b>TOTALE ORE (AULA + STAGE)</b></span><br/>
                                                            <span class="text-success" id="totaleorecompl">0</span>
                                                        </label>
                                                    </div>
                                                    <hr>
                                                    <div class="row">
                                                        <label class="col-lg-12 col-form-label fw-bold fs-6" >
                                                            <span class="text-success"><b>STEP 1) Competenze Trasversali</b></span>
                                                            <button type="submit" class="btn btn-sm btn-success"><i class="fa fa-save"></i> SALVA DATI</button>
                                                        </label>

                                                        <%if (co1.getDurataore() >= attivact) {%>
                                                        <hr>
                                                        <div class="row col-md-12">
                                                            <label class="col-md-5 col-form-label fw-bold">
                                                                <span class="text-dark">Titolo del modulo / Competenza</span>
                                                            </label>
                                                            <label class="col-md-5 col-form-label fw-bold">
                                                                <span class="text-dark">Descrizione</span>
                                                            </label>
                                                            <label class="col-md-2 col-form-label fw-bold">
                                                                <span class="text-dark">Ore</span>
                                                            </label>
                                                        </div>
                                                        <%
                                                            for (Competenze_Trasversali ct : comp_tr) {
                                                                Calendario_Formativo cf0 = new Calendario_Formativo();
                                                                int duratashow = ct.getDurataore();
                                                                if (Engine.checkexist_CT(calendar, ct)) {
                                                                    cf0 = Engine.getexist_CT(calendar, ct);
                                                                    if (cf0.getTipomodulo().equals("BASE")) {
                                                                    } else {
                                                                        duratashow = 0;
                                                                    }
                                                                } else {
                                                                    cf0.setCtdescrizione("");
                                                                    cf0.setCtcodicelingua("");
                                                                }
                                                        %>
                                                        <div class="row row-border col-md-12 p-5">
                                                            <!--begin::Label-->
                                                            <label class="col-md-5">
                                                                <span class="required"><%=ct.getDescrizione()%></span>
                                                            </label>
                                                            <input type="hidden" name="idct_<%=ct.getIdcompetenze()%>" value="<%=ct.getIdcompetenze()%>"/>
                                                            <input type="hidden" name="ctreqing_<%=ct.getIdcompetenze()%>" value="0"/>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <input type="hidden" name="ctdescr_<%=ct.getIdcompetenze()%>" id="ctdescr_<%=ct.getIdcompetenze()%>"
                                                                       class="form-control" 
                                                                       placeholder="..." value="<%=cf0.getCtdescrizione()%>" />
                                                            <%if (ct.getDescrizione().toLowerCase().startsWith("ling")) {%>
                                                            <div class="col-md-5 fv-row">   
                                                                <select name="ctlingua_<%=ct.getIdcompetenze()%>" aria-label="Scegli..." 
                                                                        data-control="select2" data-placeholder="Lingua Straniera" 
                                                                        class="form-select"
                                                                        required>
                                                                    <option value="">Scegli Lingua</option>
                                                                    <%for (Lingua l1 : language) {
                                                                            String sel = (l1.getCodicelingua().equals(cf0.getCtcodicelingua())) ? "selected" : "";
                                                                    %>
                                                                    <option <%=sel%> value="<%=l1.getCodicelingua()%>"><%=l1.getNome()%></option>
                                                                    <%}%>
                                                                </select>
                                                            </div>                                                                
                                                            <label class="col-md-2 col-form-label">
                                                                <span class="ctdurata" id="htmlctdurata_<%=ct.getIdcompetenze()%>"><%=duratashow%></span>
                                                            </label>
                                                            <%} else {%>
                                                            <label class="col-md-5 col-form-label">
                                                            </label>
                                                            <label class="col-md-2 col-form-label">
                                                                <span class="ctdurata" id="htmlctdurata_<%=ct.getIdcompetenze()%>"><%=duratashow%></span>
                                                            </label>
                                                            <%}%>
                                                            <input type="hidden" id="ctdurata_<%=ct.getIdcompetenze()%>" value="<%=ct.getDurataore()%>" />
                                                        </div>
                                                        <%}%>
                                                        <%}else{%>
                                                        NON PREVISTE - CORSO DI ORE INFERIORI A <%=attivact%>
                                                        <%}%>
                                                        <input type="hidden" name="COSTOSTIMATO" id="COSTOSTIMATO"
                                                               value="0" />
                                                    </div>
                                                    <%
                                                    boolean attivastep2 = co1.getDurataore() < attivact;
                                                    
                                                    if (attivastep2 || !calendar.isEmpty()) {
                                                            session.setAttribute("ses_idcorso", Utils.enc_string(String.valueOf(co1.getIdcorso())));
                                                    %>
                                                    <hr>
                                                    <div class="row">
                                                        <label class="col-form-label fw-bold fs-6">
                                                            <span class="text-primary"><b>STEP 2) Calendario Formativo</b></span>
                                                            <br/>
                                                            <span class="text-primary">ORE TOTALI PIANIFICATE: <span id="orepianificate">0</span> (SU <span id="completeduration2"></span>)</span>
                                                            |
                                                            <span class="text-primary">ORE AREA TECNICA PIANIFICATE : <span id="orepianificatec">0</span> (SU ALMENO <%=Utils.roundDoubleandFormat(oretec, 2)%>)</span>
                                                            |
                                                            <span class="text-primary">ORE ELEARNING PIANIFICATE: <span id="orepianificateel">0</span> (SU <%=co1.getElearningperc()%>)</span>
                                                            <br/>
                                                            <span class="text-primary">ORE DA PIANIFICARE: <span id="oredapianificare">0</span></span>
                                                            |
                                                            <a class="btn btn-primary btn-sm fan1" href="US_programmacorsi_details.jsp"
                                                               data-fancybox data-type='iframe' 
                                                               data-bs-toggle="tooltip" title="AGGIUNGI MODULO FORMATIVO" 
                                                               data-preload='false' data-width='75%' data-height='75%' id="addcalendariobutton">
                                                                <i class="fa fa-plus-circle" ></i> Aggiungi</a>
                                                        </label>
                                                        <hr>
                                                        <div class="card-body py-3">
                                                            <!--begin::Table container-->
                                                            <div class="table-responsive">
                                                                <!--begin::Table-->
                                                                <table class="table align-middle gy-3 table-bordered table-hover" id="tab_dt1" style="border-bottom: 2px;">
                                                                    <!--begin::Table head-->
                                                                    <thead>
                                                                        <tr>
                                                                            <th class="p-2 w-50px">#</th>
                                                                            <th class="p-2 w-50px">Tipologia</th>
                                                                            <th class="p-2 w-150px">Descrizione</th>
                                                                            <th class="p-2 w-50px">Ore Totali</th>
                                                                            <th class="p-2 w-50px">Ore eLearning</th>
                                                                            <th class="p-2 w-50px">ID Competenze</th>
                                                                            <th class="p-2 w-50px">ID Abilita'</th>
                                                                            <th class="p-2 w-50px">ID Conoscenze</th>
                                                                            <th class="p-2 w-50px">Azioni</th>
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
                                                                    <input type="hidden" class="value_ore" id="ore_modules_<%=indexmodules%>" value="<%=c1.getOre()%>" />
                                                                    <input type="hidden" class="value_oreaula" id="oreaula_modules_<%=indexmodules%>" value="<%=c1.getOre_aula()%>" />
                                                                    <input type="hidden" class="value_oreel" id="oreel_modules_<%=indexmodules%>" value="<%=c1.getOre_teorica_el()%>" />
                                                                    <input type="hidden" class="value_oretec" id="oretec_modules_<%=indexmodules%>" value="<%=c1.getOre_tecnica_operativa()%>" />
                                                                    <tr>
                                                                        <td class="p-2 w-50px"><%=c1.getCodicemodulo()%></td>
                                                                        <td class="p-2 w-50px"><%=c1.getTipomodulo()%></td>
                                                                        <td class="p-2 w-150px"><%=c1.getCompetenzetrasversali().getDescrizione()%></td>
                                                                        <td class="p-2 w-50px"><%=Utils.roundDoubleandFormat(c1.getOre(), 1)%></td>
                                                                        <td class="p-2 w-50px"><%=Utils.roundDoubleandFormat(c1.getOre_teorica_el(), 1)%></td>
                                                                        <td class="p-2 w-50px"></td>
                                                                        <td class="p-2 w-50px"></td>
                                                                        <td class="p-2 w-50px"></td>
                                                                        <td class="p-2 w-50px"></td>
                                                                    </tr>
                                                                    <%} else if (c1.getTipomodulo().equals("MODULOFORMATIVO")) {%>
                                                                    <input type="hidden" class="value_ore" id="ore_modules_<%=indexmodules%>" value="<%=c1.getOre()%>" />
                                                                    <input type="hidden" class="value_oreaula" id="oreaula_modules_<%=indexmodules%>" value="<%=c1.getOre_aula()%>" />
                                                                    <input type="hidden" class="value_oreel" id="oreel_modules_<%=indexmodules%>" value="<%=c1.getOre_teorica_el()%>" />
                                                                    <input type="hidden" class="value_oretec" id="oretec_modules_<%=indexmodules%>" value="<%=c1.getOre_tecnica_operativa()%>" />
                                                                    <tr>
                                                                        <td class="p-2 w-50px"><%=c1.getCodicemodulo()%></td>
                                                                        <td class="p-2 w-50px">MODULO FORMATIVO</td>
                                                                        <td class="p-2 w-150px"><%=c1.getNomemodulo()%></td>
                                                                        <td class="p-2 w-50px"><%=Utils.roundDoubleandFormat(c1.getOre(), 1)%></td>
                                                                        <td class="p-2 w-50px"><%=Utils.roundDoubleandFormat(c1.getOre_teorica_el(), 1)%></td>
                                                                        <td class="p-2 w-50px"><%=c1.exportCompetenze()%></td>
                                                                        <td class="p-2 w-50px"><%=c1.exportAbilita()%></td>
                                                                        <td class="p-2 w-50px"><%=c1.exportConoscenze()%></td>
                                                                        <td class="p-2 w-50px">
                                                                            <button onclick="return ELIMINAMODULO('<%=c1.getIdcalendarioformativo()%>','<%=c1.getCodicemodulo()%>');" class="btn btn-danger btn-sm" 
                                                               data-bs-toggle="tooltip" title="ELIMINA MODULO FORMATIVO" 
                                                               data-preload='false' data-width='75%' data-height='75%' id="addcalendariobutton">
                                                                <i class="fa fa-remove"></i></button>
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
                                                    <%} else {%>
                                                    <hr>
                                                    <div class="row">
                                                        <label class="col-form-label fw-bold fs-6">
                                                            <span class="text-primary">STEP 2) Calendario Formativo - COMPLETARE STEP 1</span>
                                                    </div>
                                                    <%}
                                                        boolean showstep4 = false;
                                                        if (calendariocompleto) {
                                                            if (modulidaassegnare == 0) {
                                                                showstep4 = true;
                                                            }
                                                    %>
                                                    <hr>
                                                    <div class="row">
                                                        <label class="col-form-label fw-bold fs-6">
                                                            <span class="text-warning">STEP 3) Elenco Docenti 
                                                                | <span class="text-warning">MODULI ASSEGNATI: <span id="moduliass"><%=moduliassegnati%></span></span>
                                                                | <span class="text-warning">MODULI DA ASSEGNARE: <span id="modulidaass"><%=modulidaassegnare%></span></span>
                                                                | <a class="btn btn-warning btn-sm fan1" href="US_programmacorsi_docenti.jsp"
                                                                     data-fancybox data-type='iframe' 
                                                                     data-bs-toggle="tooltip" title="ASSEGNA DOCENTE A MODULO FORMATIVO" 
                                                                     data-preload='false' data-width='75%' data-height='75%' id="adddocentibutton">
                                                                    <i class="fa fa-plus-circle" ></i> Aggiungi</a></span>
                                                    </div>

                                                    <div class="card-body py-3">
                                                        <!--begin::Table container-->
                                                        <div class="table-responsive">
                                                            <!--begin::Table-->
                                                            <table class="table align-middle gy-3 table-bordered table-hover" id="tab_dt2" style="border-bottom: 2px;">
                                                                <!--begin::Table head-->
                                                                <thead>
                                                                    <tr>
                                                                        <th class="p-2 w-150px">Docente</th>
                                                                        <th class="p-2 w-50px">CF</th>
                                                                        <th class="p-2 w-150px">Moduli assegnati (Ore previste)</th>
                                                                        <th class="p-2 w-50px">Azioni</th>
                                                                    </tr>
                                                                </thead>
                                                                <!--end::Table head-->
                                                                <!--begin::Table body-->
                                                                <tbody>
                                                                    <%for (Docente d2 : assegnati) {%>
                                                                    <tr>
                                                                        <td class="p-2 w-150px"><%=d2.getCognome()%> <%=d2.getNome()%></td>
                                                                        <td class="p-2 w-50px"><%=d2.getCodicefiscale()%></td>
                                                                        <td class="p-2 w-150px"><%=d2.formatElencomoduli()%></td>
                                                                        <td class="p-2 w-150px"><i class='fa fa-hourglass'></i></td>      
                                                                    </tr>
                                                                    <%}
                                                                    %>

                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <%} else {%>
                                                    <hr>
                                                    <div class="row">
                                                        <label class="col-form-label fw-bold fs-6">
                                                            <span class="text-warning">STEP 3) Elenco Docenti - COMPLETARE STEP 2</span>
                                                    </div>
                                                    <%}%>
                                                    <hr>
                                                    <%if (showstep4) {%>
                                                    <div class="row">
                                                        <label class="col-form-label fw-bold fs-6">
                                                            <span class="text-dark">STEP 4) Elenco Attrezzature | 
                                                                <a class="btn btn-dark btn-sm fan1" href="US_programmacorsi_attr.jsp"
                                                                   data-fancybox data-type='iframe' 
                                                                   data-bs-toggle="tooltip" title="AGGIUNGI ATTREZZATURE" 
                                                                   data-preload='false' data-width='75%' data-height='75%'>
                                                                    <i class="fa fa-plus-circle" ></i> Aggiungi</a></span>
                                                    </div>

                                                    <div class="card-body py-3">
                                                        <!--begin::Table container-->
                                                        <div class="table-responsive">
                                                            <!--begin::Table-->
                                                            <table class="table align-middle gy-3 table-bordered table-hover" 
                                                                   id="tab_dt3" style="border-bottom: 2px;">
                                                                <!--begin::Table head-->
                                                                <thead>
                                                                    <tr>
                                                                        <th class="p-2 w-150px">Descrizione</th>
                                                                        <th class="p-2 w-50px">Modalit&#224; di acquisizione</th>
                                                                        <th class="p-2 w-50px">Quantit&#224;</th>
                                                                        <th class="p-2 w-50px">Data inizio</th>
                                                                        <th class="p-2 w-150px">Registro e numero inventario</th>
                                                                        <th class="p-2 w-50px">Azioni</th>
                                                                    </tr>
                                                                </thead>
                                                                <!--end::Table head-->
                                                                <!--begin::Table body-->
                                                                <tbody>
                                                                    <%
                                                                        for (Attrezzature at1 : co1.getAttrezzature()) {%>
                                                                    <tr>
                                                                        <td class="p-2 w-150px"><%=at1.getDescrizione()%></td>
                                                                        <td class="p-2 w-50px"><%=at1.getModalita().name()%></td>
                                                                        <td class="p-2 w-50px"><%=at1.getQuantita()%></td>
                                                                        <td class="p-2 w-50px"><%=Constant.sdf_PATTERNDATE4.format(at1.getDatainizio())%></td>
                                                                        <td class="p-2 w-150px"><%=at1.getRegistroinventario()%></td>
                                                                        <td class="p-2 w-50px"><i class='fa fa-hourglass'></i></td>
                                                                    </tr>
                                                                    <%}
                                                                    %>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <%} else {%>
                                                    <div class="row">
                                                        <label class="col-form-label fw-bold fs-6">
                                                            <span class="text-dark">STEP 4) Elenco Attrezzature - COMPLETARE STEP 3</span>
                                                    </div>
                                                    <%}%>
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
                            </div>
                            <jsp:include page="menu/footer1.jsp" /> 
                        </div>
                    </div>
                </div>
            </div>
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
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
        <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
        <script type="text/javascript" src="assets/js/common.js"></script>
        <script src="assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>
            <script src="assets/plugins/jquery-confirm.min3.3.2.js"></script>

        <script src="assets/js/US_programmacorsi.js"></script>

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