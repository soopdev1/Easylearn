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
        <title><%=Constant.NAMEAPP%> - Dashboard</title>
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
    <%

        EntityOp eo = new EntityOp();
        List<Scheda_Attivita> sche1 = (List<Scheda_Attivita>) eo.findAll(Scheda_Attivita.class);
        List<Tipologia_Percorso> listper = (List<Tipologia_Percorso>) eo.findAll(Tipologia_Percorso.class);

        int maxrichiesta = Utils.parseIntR(Engine.getPath("conf.max.edizioni"));
        int maxallievi = Utils.parseIntR(Engine.getPath("conf.max.allievi"));
        User u1 = (User) session.getAttribute("us_memory");

        Long idist = Long.valueOf(Utils.dec_string(Utils.getRequestValue(request, "idist")));

        Istanza is1 = eo.getEm().find(Istanza.class, idist);

        Tipologia_Percorso tp1 = eo.getTipoPercorsoIstanza(is1);
        List<Corso> c1 = new EntityOp().getCorsiIstanza(is1);

    %>
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
                                <%if (is1 != null) {%>
                                <div class="row g-10">
                                    <!--begin::Col-->
                                    <div class="col-xl-12">
                                        <!--begin::Tables Widget 3-->
                                        <h1 class="text-center fs-4">Aggiungi nuovo percorso - Codice Istanza: <%=is1.getCodiceistanza()%></h1>
                                        <form id="signUpForm" action="Operations" method="POST">
                                            <input type="hidden" name="type" value="ADDCORSO" />
                                            <input type="hidden" name="closewindow" value="YES" />

                                            <!-- start step indicators -->
                                            <div class="form-header d-flex mb-4">
                                                <span class="stepIndicator">Tipologia Percorso</span>
                                                <span class="stepIndicator">Scelta Percorso</span>
                                                <span class="stepIndicator">Dettagli Percorso</span>
                                            </div>
                                            <!-- end step indicators -->
                                            <!-- step one -->
                                            <div class="step">
                                                <p class="text-center mb-4">Istanza di autorizzazione allo svolgimento di corsi di formazione professionale</p>
                                                <div class="mb-3">
                                                    <select aria-label="Scegli..." data-control="select2" 
                                                            data-placeholder="Scegli Tipologia percorso" 
                                                            class="form-select form-select-solid form-select-lg fw-bold" 
                                                            name="scelta"
                                                            id="scelta"
                                                            required>
                                                        <%
                                                            if (tp1 != null) {%>
                                                        <option value="<%=tp1.getIdtipopercorso()%>"><%=tp1.getNometipologia()%></option> 
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- step two -->
                                            <div class="step">
                                                <p class="text-center mb-4">Istanza di autorizzazione allo svolgimento di corsi di formazione professionale</p>
                                                <div class="mb-3">
                                                    <select aria-label="Scegli..." data-control="select2" 
                                                            data-placeholder="Scegli..." 
                                                            class="form-select form-select-solid form-select-lg fw-bold" 
                                                            name="istat"
                                                            id="repertoriochoice"
                                                            required onchange="return selezionaCorso();">
                                                        <option value="">Scegli...</option>                                                                
                                                        <%for (Scheda_Attivita sa : sche1) {
                                                                boolean ok = true;
                                                                for (Corso cor : c1) {
                                                                    if (cor.getSchedaattivita().getIdschedaattivita().equals(
                                                                            sa.getIdschedaattivita())) {
                                                                        ok = false;
                                                                    }
                                                                }
                                                                if (ok) {
                                                        %>
                                                        <option value="<%=sa.getRepertorio().getIdrepertorio()%>;<%=sa.getIdschedaattivita()%>" >
                                                            <%=sa.getRepertorio().getDenominazione()%> - <%=sa.getTipologiapercorso()%>
                                                        </option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="mb-3" id="detail_corso" style="display: none;">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                        <span class="text-danger">Dettagli corso</span>
                                                    </label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-12 fv-row">
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Area Professionale:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" id="cont_label_area">
                                                            </label>
                                                        </div>                                                            
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Sottoarea Professionale:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" id="cont_label_sottoarea">
                                                            </label>
                                                        </div>                                                            
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Professioni NUP/ISTAT correlate:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" id="cont_label_professioni">
                                                            </label>
                                                        </div>                                                            
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Livello:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" id="cont_label_livello">
                                                            </label>
                                                        </div>                                                            
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Certificazione rilasciata:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" id="cont_label_cert">
                                                            </label>
                                                        </div>

                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Ore di corso (ore):
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" id="cont_label_ore">
                                                            </label>
                                                        </div>
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Ore di stage (ore):
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" id="cont_label_stage">
                                                            </label>
                                                        </div>
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Ore di eLearning (perc.):
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" id="cont_label_elearn">
                                                            </label>
                                                        </div>
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Ore assenza massime consentite (perc.):
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_assmax">
                                                            </label>
                                                        </div>

                                                    </div>
                                                    <!--end::Col-->
                                                </div>    
                                            </div>

                                            <!-- step three -->
                                            <div class="step">
                                                <p class="text-center mb-4">Istanza di autorizzazione allo svolgimento di corsi di formazione professionale</p>
                                                <div class="mb-3" id="detail_corso_s3" style="display: none;">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                        <span class="text-danger">Dettagli corso</span>
                                                    </label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-12 fv-row">
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Area Professionale:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_area_s3">
                                                            </label>
                                                        </div>                                                            
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Sottoarea Professionale:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_sottoarea_s3">
                                                            </label>
                                                        </div>                                                            
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Professioni NUP/ISTAT correlate:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_professioni_s3">
                                                            </label>
                                                        </div>                                                            
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Livello:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_livello_s3">
                                                            </label>
                                                        </div>                                                            
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Certificazione rilasciata:
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_cert_s3">
                                                            </label>
                                                        </div>

                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Ore di corso (ore):
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_ore_s3">
                                                            </label>
                                                        </div>
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Ore di stage (ore):
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_stage_s3">
                                                            </label>
                                                        </div>
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Ore di eLearning (perc.):
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_elearn_s3">
                                                            </label>
                                                        </div>
                                                        <div class="row col-lg-12">
                                                            <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                                Ore assenza massime consentite (perc.):
                                                            </label>
                                                            <label class="col-lg-8 col-form-label fw-bold fs-6 text-info" 
                                                                   id="cont_label_assmax_s3">
                                                            </label>
                                                        </div>

                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-4 col-form-label required fw-bold fs-6">Numero Protocollo (S.P.)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-12 fv-row">
                                                                <input type="text" name="protnum" 
                                                                       class="form-control form-control-lg form-control-solid mb-3 mb-lg-0" 
                                                                       placeholder="..." required/>
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
                                                        <span class="required">Numero Edizioni</span>
                                                        <a onclick="return false;" data-bs-toggle="tooltip" data-bs-placement="top" 
                                                           title="Selezione il numero di edizioni richieste (Max 5 ad istanza)">
                                                            <i class="fas fa-exclamation-circle ms-1 fs-7"></i>
                                                        </a>
                                                    </label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8 fv-row">
                                                        <select name="quantitarichiesta" aria-label="Scegli..." 
                                                                data-control="select2" data-placeholder="Scegli..." 
                                                                class="form-select form-select-solid form-select-lg fw-bold" 
                                                                required>
                                                            <option value="">Scegli...</option>
                                                            <%for (int i = 1; i <= maxrichiesta; i++) {%>
                                                            <option value="<%=i%>"><%=i%></option>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-4 col-form-label required fw-bold fs-6">Durata Corso (ORE)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-12 fv-row">
                                                                <input type="text" name="durataore" id="durataore"
                                                                       class="form-control form-control-lg form-control-solid mb-3 mb-lg-0 numbR" 
                                                                       placeholder="000" required/>
                                                            </div>
                                                        </div>
                                                        <!--end::Row-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-4 col-form-label required fw-bold fs-6">Durata Complessiva Corso (GIORNI)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-12 fv-row">
                                                                <input type="text" name="duratagiorni" id="duratagiorni"
                                                                       class="form-control form-control-lg form-control-solid mb-3 mb-lg-0 numbR" 
                                                                       placeholder="000" required/>
                                                            </div>
                                                        </div>
                                                        <!--end::Row-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-4 col-form-label required fw-bold fs-6">Durata Stage (ORE)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-12 fv-row">
                                                                <input type="text" name="stageore" id="stageore"
                                                                       class="form-control form-control-lg form-control-solid mb-3 mb-lg-0 numbR" 
                                                                       placeholder="000" required/>
                                                            </div>
                                                        </div>
                                                        <!--end::Row-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-4 col-form-label required fw-bold fs-6">Ore eLearning (%)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-12 fv-row">
                                                                <input type="text" name="elearning"  id="elearning"
                                                                       class="form-control form-control-lg form-control-solid mb-3 mb-lg-0 numbR" 
                                                                       placeholder="0-100" required />
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <input type="hidden" name="maxallievi" id="maxallievi" value="<%=maxallievi%>" />
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-4 col-form-label required fw-bold fs-6">Numero Allievi (MAX <%=maxallievi%>)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-12 fv-row">
                                                                <input type="text" name="numeroallievi" id="numeroallievi" 
                                                                       class="form-control form-control-lg form-control-solid mb-3 mb-lg-0 numbR" 
                                                                       placeholder="000" required/>
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
                                                        <span class="required">Sede formativa scelta</span>
                                                        <a onclick="return false;" data-bs-toggle="tooltip" data-bs-placement="top" 
                                                           title="Selezione sede dall'elenco">
                                                            <i class="fas fa-exclamation-circle ms-1 fs-7"></i>
                                                        </a>
                                                    </label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8 fv-row">
                                                        <select name="sedescelta" aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                                class="form-select form-select-solid form-select-lg fw-bold" required>
                                                            <option value="">Scegli...</option>
                                                            <%
                                                                for (Sede s1 : u1.getSoggetto().getSediformazione()) {%>
                                                            <option value="<%=s1.getIdsede()%>"><%=s1.getIndirizzo()%> <%=s1.getCap()%> - <%=s1.getComune()%>, <%=s1.getProvincia()%></option>
                                                            <%}
                                                            %>
                                                        </select>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                            </div>

                                            <div class="mb-3">
                                                <div aria-live="polite" id="errorMsgContainer"></div>
                                            </div>

                                            <!-- start previous / next buttons -->
                                            <div class="form-footer d-flex">
                                                <button type="button" id="prevBtn" onclick="nextPrev(-1)"

                                                        class="btn btn-circled btn-hover-rise"
                                                        ><i class="fa fa-backward-step"></i> INDIETRO</button>

                                                <button type="button" id="nextBtn" 
                                                        onclick="nextPrev(1)" 
                                                        class="btn btn-circled btn-hover-rise"
                                                        ><i class="fa fa-forward-step"></i> AVANTI</button>
                                            </div>
                                            <!-- end previous / next buttons -->
                                        </form>
                                        <!--end::Tables Widget 3-->
                                    </div>
                                    <!--end::Col-->
                                    <!--begin::Col-->
                                    <!--end::Col-->
                                </div>
                                <!--end::Row-->
                                <%}%>
                            </div>
                            <!--end::Post-->
                            <!--begin::Footer-->
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
        <script src="assets/js/widgets.bundle.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>

        <script type="text/javascript" src="assets/js/wiz_1.js"></script>
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