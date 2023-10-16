<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>
<%@page import="rc.soop.sic.jpa.CommissioneEsame"%>
<%@page import="rc.soop.sic.jpa.PresidenteCommissione"%>
<%@page import="org.joda.time.DateTime"%>
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
                String idistS = Utils.getRequestValue(request, "idcorso");
                if (idistS.equals("")) {
                    idistS = (String) session.getAttribute("ses_idcorso");
                }
                session.setAttribute("ses_idcorso", idistS);

                EntityOp eo = new EntityOp();
                Corsoavviato is1 = eo.getEm().find(Corsoavviato.class, Long.valueOf(idistS));
                List<CorsoAvviato_Docenti> avv_doc = eo.list_cavv_docenti(is1);
                List<CorsoAvviato_AltroPersonale> avv_altrop = eo.list_cavv_altropers(is1);
                List<Allievi> allievi = eo.getAllieviCorsoAvviato(is1);
                List<Calendario_Formativo> cal_istanza = eo.calendario_formativo_corso(is1.getCorsobase());
                List<Calendario_Lezioni> lezioni = eo.calendario_lezioni_corso(is1);
                CommissioneEsame com = eo.getCommissioneEsameCorso(is1);

                boolean modify = true;
                boolean view = false;
                boolean azionicorso = false;
                if (Utils.isAdmin(session)) {
                    modify = false;
                    view = true;
                } else {
                    PresidenteCommissione so = ((User) session.getAttribute("us_memory")).getPresidente();
                    modify = so.getIdpresidente().equals(is1.getPresidentecommissione().getIdpresidente()) && is1.getStatocorso().getCodicestatocorso().equals("51");
                }


    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: VERBALE ESAME</title>
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
                        <jsp:include page="menu/switchmenu.jsp" /> 
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
                                    <div class="col-xl-12">
                                        <div class="card h-xl-100">
                                            <div class="card-header border-0 pt-5">
                                                <h3 class="card-title align-items-start flex-column">
                                                    <span class="card-label fw-bolder fs-3 mb-1">ESAME FINALE - Corso di formazione ID: <%=is1.getIdcorsoavviato()%> - <%=is1.getCorsobase().getIstanza().getTipologiapercorso().getNometipologia()%> - <u><%=is1.getCorsobase().getRepertorio().getDenominazione()%></u></span>
                                                </h3>
                                            </div>
                                            <div class="card-body py-3">

                                                <div class="row col-md-12">
                                                    <h2>DATI ESAME</h2>
                                                </div>
                                                <hr>
                                                <div class="row col-md-12">
                                                    <h4>STRUTTURA FORMATIVA</h4>
                                                </div>

                                                <div class="row col-md-12">
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>DENOMINAZIONE:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSoggetto().getRAGIONESOCIALE()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-danger"><b>VIA E COMUNE SEDE LEGALE:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSoggetto().getSedelegale().getIndirizzo()%> - <%=is1.getCorsobase().getSoggetto().getSedelegale().getCap()%> - <%=is1.getCorsobase().getSoggetto().getSedelegale().getComune()%> (<%=is1.getCorsobase().getSoggetto().getSedelegale().getProvincia()%>)</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-danger"><b>DDG ACCREDITAMENTO:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSoggetto().getDDGNUMERO()%> del <%=is1.getCorsobase().getSoggetto().getDDGDATA()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-danger"><b>CIR:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSoggetto().getCIR()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-danger"><b>SEDE OPERATIVA:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSedescelta().getIndirizzo() + " " + is1.getCorsobase().getSedescelta().getComune() + " " + is1.getCorsobase().getSedescelta().getProvincia()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-danger"><b>LEGALE RAPPRESENTANTE: </b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSoggetto().getRap_cognome()%> <%=is1.getCorsobase().getSoggetto().getRap_nome()%> (<%=is1.getCorsobase().getSoggetto().getRap_carica()%>)</b></span>
                                                    </label>
                                                </div>
                                                <hr>
                                                <div class="row col-md-12">
                                                    <h4>CERTIFICAZIONE</h4>
                                                </div>
                                                <div class="row col-md-12">
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>TIPO:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSchedaattivita().getCertificazioneuscita().getNome()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>LIVELLO EQF:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getRepertorio().getLivelloeqf().getNome()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>TITOLO RILASCIATO:</b></span>
                                                    </label>
                                                    <label class="col-md-10 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSchedaattivita().getTitoloattestato()%></b></span>
                                                    </label>
                                                </div>
                                                <hr>
                                                <div class="row col-md-12">
                                                    <h4>DATI CORSO
                                                        <%if (modify) {%>
                                                        <button class="btn btn-primary btn-sm" data-preload="false" data-bs-toggle="tooltip" title="SALVA DATI">
                                                            <i class="fa fa-save"></i>
                                                        </button>
                                                        <%}%></h4>
                                                </div>
                                                <div class="row col-md-12">
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>MODALITA' APPRENDIMENTO:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <input type="text" name="MODAPPR" id="MODAPPR" class="form-control" required placeholder="..."/>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>ID CORSO:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getIdentificativocorso()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>ORE PREVISTE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getDurataore()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>ORE SVOLTE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <input type="text" name="ORESVOLTE" id="ORESVOLTE" class="form-control intvalue" required placeholder="..."/>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>ORE STAGE PREVISTE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getStageore()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>DATA INIZIO:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=Constant.sdf_PATTERNDATE4.format(is1.getDatainizio())%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>DATA FINE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=Constant.sdf_PATTERNDATE4.format(is1.getDatafine())%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>PROVVEDIMENTO ISTITUZIONE:</b></span>
                                                    </label>
                                                    <%
                                                        String DDS = is1.getCorsobase().getIstanza().getDecreto().split("§")[0];
                                                        String DDSDATA = Utils.datemysqltoita(is1.getCorsobase().getIstanza().getDecreto().split("§")[1]);
                                                    %>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b>D.D.S. n° <%=DDS%> del <%=DDSDATA%></b></span>
                                                    </label>
                                                    <label class="col-md-12 col-form-label fw-bold">
                                                        <span class="text-danger"><b>PERSONA INCARICATA DI RAPPRESENTARE LA STRUTTURA AGLI ESAMI:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <input type="text" name="INCARICATO_NC" id="INCARICATO_NC" class="form-control" required placeholder="..."/>
                                                        <span class="help-block">1. COGNOME E NOME</span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <select aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                                class="form-select form-select-lg" 
                                                                name="INCARICATO_S" id="INCARICATO_S" required>                                                                    
                                                            <option value="">...</option>  
                                                            <option value="M">M</option>  
                                                            <option value="F">F</option>  
                                                        </select>
                                                        <span class="help-block">2. SESSO</span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <input type="text" name="INCARICATO_Q" id="INCARICATO_1" class="form-control" required placeholder="..."/>
                                                        <span class="help-block">3. QUALIFICA</span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>PROCEDURA DI ATTIVAZIONE:</b></span>
                                                    </label>
                                                    <label class="col-md-10 col-form-label fw-bold">
                                                        <input type="text" name="INCARICATO_NC" id="INCARICATO_NC" class="form-control" required placeholder="..."/>
                                                    </label>
                                                </div>

                                                <hr>
                                                <div class="row col-md-12">
                                                    <h4>DATI ESAME
                                                        <%if (modify) {%>
                                                        <button class="btn btn-primary btn-sm" data-preload='false'
                                                                data-bs-toggle="tooltip" title="SALVA DATI" 
                                                                >
                                                            <i class="fa fa-save"></i></button>
                                                        <%}%></h4>
                                                </div>    
                                                <div class="row col-md-12">
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>VERBALE PROT. NUMERO:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <input type="text" name="VERBPROT" id="VERBPROT" class="form-control" required placeholder="..."/>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>VERBALE PROT. DATA:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <input type="date" name="VERBDATA" id="VERBDATA" class="form-control" required placeholder="..."/>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>PRESIDENTE COMMISSIONE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=is1.getPresidentecommissione().getCognome()%> <%=is1.getPresidentecommissione().getNome()%> </b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>COMPONENTI COMMISSIONE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b>
                                                                <%=com.getTitolare1().getCognome()%> <%=com.getTitolare1().getNome()%> <br/>
                                                                <%=com.getTitolare2().getCognome()%> <%=com.getTitolare2().getNome()%>
                                                            </b>
                                                        </span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>SUPPLENTI SEGNALATI:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b>
                                                                <%=com.getSostituto1().getCognome()%> <%=com.getSostituto1().getNome()%> <br/>
                                                                <%=com.getSostituto2().getCognome()%> <%=com.getSostituto2().getNome()%>
                                                            </b>
                                                        </span>
                                                    </label>
                                                         <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>DATA ESAME:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <input type="date" name="DATAESAME" id="DATAESAME" class="form-control" required placeholder="..."/>
                                                    </label>   
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
            <!--begin::Javascript-->
            <script>var hostUrl = "assets/";</script>

            <!--begin::Global Javascript Bundle(used by all pages)-->

            <!--end::Global Javascript Bundle-->
            <!--begin::Page Vendors Javascript(used by this page)-->
            <!--end::Page Vendors Javascript-->
            <!--begin::Page Custom Javascript(used by this page)-->
            <script src="assets/plugins/global/plugins.bundle.js"></script>
            <script src="assets/js/scripts.bundle.js"></script>
            <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
            <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
            <script src="assets/fontawesome-6.0.0/js/all.js"></script>
            <script src="assets/plugins/jquery-confirm.min3.3.2.js"></script>

            <script src="assets/js/PRE_gestioneesami.js"></script>

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