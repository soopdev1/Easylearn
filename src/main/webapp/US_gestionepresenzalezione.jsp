<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>
<%@page import="rc.soop.sic.jpa.Presenze_Lezioni_Allievi"%>
<%@page import="rc.soop.sic.jpa.Presenze_Lezioni"%>
<%@page import="java.nio.charset.Charset"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="rc.soop.sic.jpa.Stati"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.soop.sic.jpa.Sede"%>
<%@page import="rc.soop.sic.jpa.User"%>
<%@page import="rc.soop.sic.jpa.SoggettoProponente"%>
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
                String numlez = Utils.getRequestValue(request, "numlez");
                String idlez = Utils.getRequestValue(request, "idlez");
                if (idlez.equals("")) {
                    idlez = (String) session.getAttribute("ses_idlez");
                } else {
                    session.setAttribute("ses_idlez", idlez);
                }
                EntityOp eo = new EntityOp();
                Calendario_Lezioni cl1 = eo.getEm().find(Calendario_Lezioni.class, Long.valueOf(Utils.dec_string(idlez)));
                boolean modify = false;
                List<CorsoAvviato_Docenti> avv_doc = eo.list_cavv_docenti(cl1.getCorsodiriferimento());
                List<Allievi> allievi = eo.getAllieviCorsoAvviato(cl1.getCorsodiriferimento());

                Presenze_Lezioni pl1 = eo.getPresenzeLezione(cl1);

                List<Presenze_Lezioni_Allievi> pa1 = new ArrayList<>();

                String datainserimento = "DA INSERIRE";
                String orainizio = cl1.getOrainizio();
                String orafine = cl1.getOrafine();
                String dataeffettiva = Constant.sdf_PATTERNDATE6.format(cl1.getDatalezione());
                String datashow = Constant.sdf_PATTERNDATE4.format(cl1.getDatalezione());
                Docente doc1 = cl1.getDocente();
                Long idpresenza = 0L;
                if (pl1 != null) {
                    datainserimento = Constant.sdf_PATTERNDATE5.format(pl1.getDatainserimento());
                    pa1 = eo.getpresenzelezioniGiornata(pl1);
                    dataeffettiva = Constant.sdf_PATTERNDATE6.format(pl1.getDatarealelezione());
                    datashow = Constant.sdf_PATTERNDATE4.format(pl1.getDatarealelezione());
                    doc1 = pl1.getDocente();
                    orainizio = pl1.getOrainizio();
                    orafine = pl1.getOrafine();
                    idpresenza = pl1.getIdpresenzelezioni();
                }

                if (!Utils.isAdmin(session)) {
                    SoggettoProponente so = ((User) session.getAttribute("us_memory")).getSoggetto();
                    if (so.getIdsoggetto().equals(cl1.getCorsodiriferimento().getCorsobase().getSoggetto().getIdsoggetto())
                            && cl1.getCorsodiriferimento().getStatocorso().getCodicestatocorso().equals("44")
                            && (cl1.getStatolezione() == null || !cl1.getStatolezione().getCodicestatocorso().equals("61"))) {
                        modify = true;
                    }
                }

                String readonly = modify ? "" : "readonly";

    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Modifica presenze lezioni</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="assets/css/gfont.css" />
        <!--end::Fonts-->
        <!--begin::Page Vendor Stylesheets(used by this page)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="assets/plugins/jquery-confirm.3.3.2.min.css">
        <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/plus.css" rel="stylesheet" type="text/css" />

        <!--end::Global Stylesheets Bundle-->
    </head>
    <!--end::Head-->
    <!--begin::Body-->
    <body class="bg-white">
        <!--begin::Main-->
        <!--begin::Root-->
        <!--begin::Post-->
        <div>
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
                                <span class="card-label fw-bolder fs-3 mb-1">PRESENZE LEZIONE NUMERO <u><%=numlez%></u> - CORSO ID: <%=cl1.getCorsodiriferimento().getIdcorsoavviato()%> - 
                                    <%=cl1.getCorsodiriferimento().getCorsobase().getIstanza().getTipologiapercorso().getNometipologia()%> - 
                                    <u><%=cl1.getCorsodiriferimento().getCorsobase().getRepertorio().getDenominazione()%></u><br/>MODULO: <%=cl1.getCalendarioformativo().getNomemodulo()%>
                                    <br/>Presenze inserite in data: <u><%=datainserimento%></u>
                                </span>
                            </h3>
                        </div>
                        <div class="card-body py-3">
                            <form action="Operations" method="POST">
                                <input type="hidden" name="type" value="AGGIUNGIPRESENZELEZIONE"/>
                                <input type="hidden" name="idcalendariolezione" value="<%=cl1.getIdcalendariolezioni()%>"/>
                                <input type="hidden" name="idpresenza" value="<%=idpresenza%>"/>
                                <div class="row col-md-12">
                                    <label class="col-md-3 col-form-label fw-bold fs-6">
                                        <span class="text-danger"><b>DATA EFFETTIVA LEZIONE:</b></span>
                                    </label>
                                    <div class="col-md-3 col-form-label fw-bold fs-6">
                                        <%if (modify) {%>
                                        <input type="date" class="form-control" id="datelez" name="datelez" value="<%=dataeffettiva%>" <%=readonly%>/>
                                        <%} else {%>
                                        <span class="text-dark"><b><%=datashow%></b></span>
                                        <input type="hidden" id="datelez" name="datelez" value=""/>
                                        <%}%>
                                    </div>
                                    <label class="col-md-3 col-form-label fw-bold fs-6">
                                        <span class="text-danger"><b>DOCENTE</b></span>
                                    </label>
                                    <div class="col-md-3 col-form-label fs-6">

                                        <%if (modify) {%>


                                        <select 
                                            id="docente"
                                            name="docente" aria-label="Scegli..." 
                                            data-control="select2" data-placeholder="Scegli..." 
                                            class="form-select" required>
                                            <option value="<%=doc1.getIddocente()%>">
                                                <%=doc1.getCognome()%> <%=doc1.getNome()%> - <%=doc1.getCodicefiscale()%>
                                            </option>
                                            <%for (CorsoAvviato_Docenti d1 : avv_doc) {
                                                    if (d1.getDocente().getIddocente().equals(doc1.getIddocente())) {
                                                        continue;
                                                    }
                                            %>
                                            <option value="<%=d1.getDocente().getIddocente()%>"><%=d1.getDocente().getCognome()%> <%=d1.getDocente().getNome()%> - <%=d1.getDocente().getCodicefiscale()%></option>
                                            <%}%>
                                        </select>
                                        <%} else {%>
                                        <span class="text-dark"><b><%=doc1.getCognome()%> <%=doc1.getNome()%> - <%=doc1.getCodicefiscale()%></b></span>
                                        <input type="hidden" class="form-control" id="docente" name="docente" value=""/>
                                        <%}%>
                                    </div>  
                                    <label class="col-md-3 col-form-label fw-bold fs-6">
                                        <span class="text-danger"><b>ORA INIZIO LEZIONE:</b></span>
                                    </label>
                                    <div class="col-md-3 col-form-label fw-bold fs-6">
                                        <%if (modify) {%>
                                        <select 
                                            id="orai"
                                            name="orai" aria-label="Scegli..." 
                                            data-control="select2" data-placeholder="Ora Inizio"
                                            class="form-select" required onchange="return populatedatafine(this);">
                                            <option value="<%=orainizio%>" selected><%=orainizio%></option>
                                            <option value="08:00">08:00</option>
                                            <option value="08:30">08:30</option>
                                            <option value="09:00">09:00</option>
                                            <option value="09:30">09:30</option>
                                            <option value="10:00">10:00</option>
                                            <option value="10:30">10:30</option>
                                            <option value="11:00">11:00</option>
                                            <option value="11:30">11:30</option>
                                            <option value="12:00">12:00</option>
                                            <option value="12:30">12:30</option>
                                            <option value="13:00">13:00</option>
                                            <option value="13:30">13:30</option>
                                            <option value="14:00">14:00</option>
                                            <option value="14:30">14:30</option>
                                            <option value="15:00">15:00</option>
                                            <option value="15:30">15:30</option>
                                            <option value="16:00">16:00</option>
                                            <option value="16:30">16:30</option>
                                            <option value="17:00">17:00</option>
                                            <option value="17:30">17:30</option>
                                            <option value="18:00">18:00</option>
                                            <option value="18:30">18:30</option>
                                            <option value="19:00">19:00</option>
                                            <option value="19:30">19:30</option>
                                            <option value="20:00">20:00</option>
                                        </select>
                                        <%} else {%>
                                        <input type="hidden" class="form-control" id="orai" name="orai" value=""/>
                                        <span class="text-dark"><b><%=orainizio%></b></span>
                                                <%}%>
                                    </div>
                                    <label class="col-md-3 col-form-label fw-bold fs-6">
                                        <span class="text-danger"><b>ORA FINE LEZIONE:</b></span>
                                    </label>
                                    <div class="col-md-3 col-form-label fw-bold fs-6">
                                        <%if (modify) {%>
                                        <select 
                                            id="oraf"
                                            name="oraf" aria-label="Scegli..." 
                                            data-control="select2" data-placeholder="Ora Fine" 
                                            class="form-select" required>
                                            <option value="<%=orafine%>" selected><%=orafine%></option>                                        
                                        </select>
                                        <%} else {%>
                                        <input type="hidden" class="form-control" id="oraf" name="oraf" value=""/>
                                        <span class="text-dark"><b><%=orafine%></b></span>
                                                <%}%>
                                    </div>
                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                        <span class="text-danger"><b>ALLIEVI:</b></span>
                                    </label>    
                                    <div class="col-md-10">
                                        <table class="table table-hover table-row-bordered">
                                            <thead>
                                                <tr>
                                                    <th>Discente</th>
                                                    <th>Codice Fiscale</th>
                                                    <th>Stato Attuale</th>
                                                    <th>Presenza SI/NO</th>
                                                    <th>Orario Ingresso/Uscita</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%for (Allievi a1 : allievi) {

                                                        boolean modifyallievo = false;
                                                        String allievo_presente = "1";
                                                        String allievo_orai = "";
                                                        String allievo_oraf = "";

                                                        for (Presenze_Lezioni_Allievi pla : pa1) {
                                                            if (pla.getAllievo().getIdallievi().equals(a1.getIdallievi())) {
                                                                modifyallievo = true;
                                                                allievo_presente = pla.getDurata() > 0 ? "1" : "0";
                                                                allievo_orai = pla.getOrainizio();
                                                                allievo_oraf = pla.getOrafine();
                                                            }
                                                        }

                                                %>
                                            <input type="hidden" name="modify_<%=a1.getIdallievi()%>" value="<%=modifyallievo%>" />
                                            <input type="hidden" id="startoi_<%=a1.getIdallievi()%>" value="<%=allievo_orai%>" />
                                            <input type="hidden" id="startof_<%=a1.getIdallievi()%>" value="<%=allievo_oraf%>" />
                                            <tr>
                                                <td>
                                                    <%=a1.getCognome()%> <%=a1.getNome()%>
                                                </td>
                                                <td>
                                                    <%=a1.getCodicefiscale()%>
                                                </td>
                                                <td>
                                                    <%=Utils.getEtichettastato(a1.getStatoallievo())%>
                                                </td>
                                                <td>
                                                    <%if (modify) {%>
                                                    <select 
                                                        id="sino_<%=a1.getIdallievi()%>"
                                                        name="sino_<%=a1.getIdallievi()%>" aria-label="..." 
                                                        data-control="select2" data-placeholder="..." 
                                                        class="form-select" required onchange="return checkorariomax();">

                                                        <%if (allievo_presente.equals("1")) {%>
                                                        <option value="1" selected>SI</option>
                                                        <option value="0">NO</option>
                                                        <%} else {%>
                                                        <option value="1">SI</option>
                                                        <option value="0" selected>NO</option>
                                                        <%}%>


                                                    </select>
                                                    <%} else {%>
                                                    <span class="text-dark"><b><%=allievo_presente.equals("1") ? "SI" : "NO"%></b></span>
                                                            <%}%>
                                                </td>
                                                <td>
                                                    <div class="row col-md-12">
                                                        <div class="col-md-6">
                                                            <%if (modify) {%>
                                                            <select 
                                                                id="orai_<%=a1.getIdallievi()%>"
                                                                name="orai_<%=a1.getIdallievi()%>" aria-label="Scegli..." 
                                                                data-control="select2" data-placeholder="Ora Inizio"
                                                                class="form-select sel-presenza" required>
                                                                <option value="<%=orainizio%>" selected><%=orainizio%></option>
                                                            </select>
                                                            <%} else if(allievo_presente.equals("1")){%>
                                                            <span class="text-dark"><b><%=orainizio%></b></span>
                                                                    <%}else{%>
                                                            <span class="text-dark"></span>
                                                            <%}%>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <%if (modify) {%>
                                                            <select 
                                                                id="oraf_<%=a1.getIdallievi()%>"
                                                                name="oraf_<%=a1.getIdallievi()%>" aria-label="Scegli..." 
                                                                data-control="select2" data-placeholder="Ora Fine" 
                                                                class="form-select sel-presenza" required>
                                                                <option value="<%=orafine%>" selected><%=orafine%></option>                                        
                                                            </select>
                                                            <%} else if(allievo_presente.equals("1")){%>
                                                            <span class="text-dark"><b><%=orafine%></b></span>
                                                                    <%}else{%>
                                                            <span class="text-dark"></span>
                                                            <%}%>
                                                        </div>
                                                </td>
                                            </tr>
                                            <%}%>
                                            </tbody>
                                        </table>
                                    </div>    
                                </div>
                                <%if (modify) {%>
                                <hr>
                                <p class="mb-0">
                                    <button type="submit" class="btn btn-success btn-circled">
                                        <i class="fa fa-save"></i> SALVA DATI
                                    </button>
                                </p>
                                <%}%>
                            </form>
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
            <!--end::Footer-->
        </div>
        <!--end::Container-->

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
        <script type="text/javascript" src="assets/plugins/jquery-confirm.min3.3.2.js"></script>
        <script src="assets/js/US_presenzelezioni.js"></script>
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