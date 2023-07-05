<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

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
                if (idcorso == null) {
                    idcorso = (String) session.getAttribute("ses_idcorso");
                }
                EntityOp eo = new EntityOp();
                Long idc1 = Long.valueOf(Utils.dec_string(idcorso));
                Corso co1 = eo.getEm().find(Corso.class, idc1);
                List<Competenze_Trasversali> comp_tr = (List<Competenze_Trasversali>) eo.findAll(Competenze_Trasversali.class);
                List<Lingua> language = eo.getLingue();
                List<Calendario_Formativo> calendar = eo.calendario_formativo_corso(co1);
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
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link href="assets/plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css" />

        <!--end::Global Stylesheets Bundle-->
    </head>
    <!--end::Head-->
    <!--begin::Body-->
    <body id="kt_body" onload="competenzetrasv();">
        <!--begin::Main-->
        <!--begin::Root-->
        <!--begin::Row-->
        <!--end::Row-->
        <!--begin::Row-->
        <!--end::Row-->
        <!--begin::Row-->
        <div class="row">
            <!--begin::Col-->
            <form method="POST" action="Operations" onsubmit="return verificasalvataggiodati();">
                <input type="hidden" name="type" value="SALVAPIANIFICAZIONE"/>
                <input type="hidden" name="idcorsodasalvare" value="<%=co1.getIdcorso()%>"/>
                <div class="col-xl-12">
                    <!--begin::Tables Widget 3-->
                    <div class="card h-xl-100">
                        <!--begin::Header-->
                        <div class="card-header border-0 pt-5">
                            <h3 class="card-title align-items-start flex-column">
                                <span class="card-label fw-bolder fs-3 mb-1">Corso di formazione - Progettazione di Dettaglio ed Unità Formative - <%=co1.getStatocorso().getHtmlicon()%></span>
                            </h3>
                            <button class="btn btn-lg btn-success"><i class="fa fa-save"></i> SALVA DATI</button>
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
                            <input type="hidden" id="startduration" value="<%=co1.getDurataore()%>" />
                            <input type="hidden" id="stageduration" value="<%=co1.getStageore()%>" />
                            <div class="row">
                                <label class="col-lg-3 col-form-label fw-bold fs-6">
                                    <span><b>Durata in Ore AULA (Iniziale - Complessiva)</b></span><br/>
                                    <span><%=co1.getDurataore()%> ORE - <b id="completeduration" class="text-primary"><%=co1.getDurataore()%></b><b class="text-primary">&nbsp;ORE</b></span>
                                </label>
                                <label class="col-lg-3 col-form-label fw-bold fs-6">
                                    <span><b>Stage</b></span><br/>
                                    <span><%=co1.getStageore()%> ORE</span>
                                </label>
                                <label class="col-lg-3 col-form-label fw-bold fs-6">
                                    <span><b>eLearning</b></span><br/>
                                    <span><%=co1.getElearningperc()%> % - <%=Utils.getPercentuale(co1.getDurataore(), co1.getElearningperc())%> ORE</span>
                                </label>
                                <label class="col-lg-3 col-form-label fw-bold fs-6">
                                    <span><b>TOTALE ORE (AULA + STAGE)</b></span><br/>
                                    <span class="text-success" id="totaleorecompl">0</span>
                                </label>
                            </div>
                            <hr>
                            <div class="row">
                                <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                    <span class="text-danger"><b>STEP 1) Competenze Trasversali</b></span>
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
                                    for (Competenze_Trasversali ct : comp_tr) {
                                        Calendario_Formativo cf0 = new Calendario_Formativo();

                                        String ri_selsi = "";
                                        String ri_selno = "";
                                        if (calendar.stream().anyMatch(c3 -> c3.getCompetenzetrasversali().equals(ct))) {
                                            cf0 = calendar.stream().filter(c3 -> c3.getCompetenzetrasversali().equals(ct)).findAny().get();
                                            if (cf0.getTipomodulo().equals("BASE")) {
                                                ri_selno = "selected";
                                            } else {
                                                ri_selsi = "selected";
                                            }
                                        } else {
                                            cf0.setCtdescrizione("");
                                            cf0.setCtcodicelingua("");
                                        }

                                %>
                                <div class="row row-border col-md-12 p-5">
                                    <!--begin::Label-->
                                    <label class="col-md-4">
                                        <span class="required"><%=ct.getDescrizione()%></span>
                                    </label>
                                    <input type="hidden" name="idct_<%=ct.getIdcompetenze()%>" value="<%=ct.getIdcompetenze()%>"/>
                                    <!--end::Label-->
                                    <!--begin::Col-->
                                    <div class="col-md-2 fv-row">
                                        <select 
                                            id="ctreqing_<%=ct.getIdcompetenze()%>"
                                            name="ctreqing_<%=ct.getIdcompetenze()%>" aria-label="Scegli..." 
                                            data-control="select2" data-placeholder="Scegli..." 
                                            class="form-select" onchange="return selezionaCT('<%=ct.getIdcompetenze()%>');"
                                            required >
                                            <%if (ct.isRequisito_ingresso()) {%>
                                            <option value="">Scegli...</option>
                                            <option value="1" <%=ri_selsi%>>SI</option>
                                            <%}%>
                                            <option value="0" <%=ri_selno%>>NO</option>
                                        </select>
                                    </div>
                                    <%if (ct.getDescrizione().toLowerCase().startsWith("ling")) {%>
                                    <div class="col-md-2 fv-row">   
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
                                    <div class="col-md-3 fv-row">
                                        <input type="text" name="ctdescr_<%=ct.getIdcompetenze()%>" id="ctdescr_<%=ct.getIdcompetenze()%>"
                                               class="form-control" 
                                               placeholder="..." required value="<%=cf0.getCtdescrizione()%>" />
                                    </div>
                                    <label class="col-md-1 col-form-label">
                                        <span id="htmlctdurata_<%=ct.getIdcompetenze()%>"><%=ct.getDurataore()%></span>
                                    </label>
                                    <%} else {%>
                                    <div class="col-md-5 fv-row">
                                        <input type="text" name="ctdescr_<%=ct.getIdcompetenze()%>" id="ctdescr_<%=ct.getIdcompetenze()%>"
                                               class="form-control" 
                                               placeholder="..." required value="<%=cf0.getCtdescrizione()%>"/>
                                    </div>

                                    <label class="col-md-1 col-form-label">
                                        <span class="ctdurata" id="htmlctdurata_<%=ct.getIdcompetenze()%>"><%=ct.getDurataore()%></span>
                                    </label>
                                    <%}%>
                                    <input type="hidden" id="ctdurata_<%=ct.getIdcompetenze()%>" value="<%=ct.getDurataore()%>" />
                                </div>
                                <%}%>
                            </div>
                            <%if (!calendar.isEmpty()) {
                                session.setAttribute("ses_idcorso", Utils.enc_string(String.valueOf(co1.getIdcorso())));
                            %>
                            <hr>
                            <div class="row">
                                <label class="col-form-label fw-bold fs-6">
                                    <span class="text-primary"><b>STEP 2) Calendario Formativo</b></span>
                                    |
                                    <span class="text-primary">ORE PIANIFICATE: <span id="orepianificate">0</span></span>
                                    |
                                    <span class="text-primary">ORE DA PIANIFICARE: <span id="oredapianificare">0</span></span>
                                    |
                                    <a class="btn btn-primary btn-sm fan1" href="US_programmacorsi_details.jsp"
                                       data-fancybox data-type='iframe' 
                                       data-bs-toggle="tooltip" title="VISUALIZZA/MODIFICA DETTAGLI ISTANZA" 
                                       data-preload='false' data-width='75%' data-height='75%'>
                                        <i class="fa fa-plus-circle"></i> Aggiungi</a>
                                </label>

                                <hr>
                                <div class="card-body py-3">
                                    <!--begin::Table container-->
                                    <div class="table-responsive ">
                                        <!--begin::Table-->
                                        <table class="table align-middle gy-3 table-bordered table-hover" id="tab_dt1" style="border-bottom: 2px;">
                                            <!--begin::Table head-->
                                            <thead>
                                                <tr>
                                                    <th class="p-2 w-50px">#</th>
                                                    <th class="p-2 w-50px">Tipologia</th>
                                                    <th class="p-2 w-150px">Descrizione</th>
                                                    <th class="p-2 w-50px">Ore</th>
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
                                            <tr>
                                                <td class="p-2 w-50px"><%=c1.getCodicemodulo()%></td>
                                                <td class="p-2 w-50px"><%=c1.getTipomodulo()%></td>
                                                <td class="p-2 w-150px"><%=c1.getCompetenzetrasversali().getDescrizione()%></td>
                                                <td class="p-2 w-50px"><%=c1.getOre()%></td>
                                                <td class="p-2 w-50px"></td>
                                                <td class="p-2 w-50px"></td>
                                                <td class="p-2 w-50px"></td>
                                                <td class="p-2 w-50px">Azioni</td>
                                            </tr>
                                            <%}
                                            %>

                                            <%    }
                                            %>
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
                            <%}%>
                            <hr>
                            <div class="row">
                                <label class="col-form-label fw-bold fs-6">
                                    <span class="text-warning">STEP 3) Elenco Docenti - COMPLETARE STEP 2</span>
                            </div>
                            <hr>
                            <div class="row">
                                <label class="col-form-label fw-bold fs-6">
                                    <span class="text-dark">STEP 4) Elenco Attrezzature - COMPLETARE STEP 3</span>
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
        <script src="assets/plugins/DataTables/jquery-3.5.1.js"></script>
        <script src="assets/plugins/DataTables/jquery.dataTables.min.js"></script>
        <script src="assets/plugins/DataTables/datatables.min.js"></script>
        <script src="assets/plugins/DataTables/date-eu.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
        <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
        <script type="text/javascript" src="assets/js/common.js"></script>
        <script src="assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>

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