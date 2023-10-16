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
        <link href="assets/plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
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
                                                    <h4>STRUTTURA FORMATIVA</h4>
                                                </div>
                                                <div class="row col-md-12">
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>DENOMINAZIONE:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSoggetto().getRAGIONESOCIALE()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-primary"><b>VIA E COMUNE SEDE LEGALE:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSoggetto().getSedelegale().getIndirizzo()%> - <%=is1.getCorsobase().getSoggetto().getSedelegale().getCap()%> - <%=is1.getCorsobase().getSoggetto().getSedelegale().getComune()%> (<%=is1.getCorsobase().getSoggetto().getSedelegale().getProvincia()%>)</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-primary"><b>DDG ACCREDITAMENTO:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSoggetto().getDDGNUMERO()%> del <%=is1.getCorsobase().getSoggetto().getDDGDATA()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-primary"><b>CIR:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSoggetto().getCIR()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-primary"><b>SEDE OPERATIVA:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSedescelta().getIndirizzo() + " " + is1.getCorsobase().getSedescelta().getComune() + " " + is1.getCorsobase().getSedescelta().getProvincia()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold fs-6">
                                                        <span class="text-primary"><b>LEGALE RAPPRESENTANTE: </b></span>
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
                                                        <span class="text-primary"><b>TIPO:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSchedaattivita().getCertificazioneuscita().getNome()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>LIVELLO EQF:</b></span>
                                                    </label>
                                                    <label class="col-md-4 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getRepertorio().getLivelloeqf().getNome()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>TITOLO RILASCIATO:</b></span>
                                                    </label>
                                                    <label class="col-md-10 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getSchedaattivita().getTitoloattestato()%></b></span>
                                                    </label>
                                                </div>
                                                <hr>
                                                <div class="row col-md-12">
                                                    <h4>DATI CORSO</h4>
                                                </div>
                                                <div class="row col-md-12">
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>ID CORSO:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getIdentificativocorso()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>ORE PREVISTE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getDurataore()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>ORE STAGE PREVISTE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=is1.getCorsobase().getStageore()%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>DATA INIZIO:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=Constant.sdf_PATTERNDATE4.format(is1.getDatainizio())%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>DATA FINE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=Constant.sdf_PATTERNDATE4.format(is1.getDatafine())%></b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>PROVVEDIMENTO ISTITUZIONE:</b></span>
                                                    </label>
                                                    <%
                                                        String DDS = is1.getCorsobase().getIstanza().getDecreto().split("§")[0];
                                                        String DDSDATA = Utils.datemysqltoita(is1.getCorsobase().getIstanza().getDecreto().split("§")[1]);
                                                    %>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b>D.D.S. n° <%=DDS%> del <%=DDSDATA%></b></span>
                                                    </label>
                                                </div>

                                                <hr>
                                                <div class="row col-md-12">
                                                    <h4>DATI ESAME</h4>
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
                                                        <span class="text-primary"><b>PRESIDENTE COMMISSIONE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b><%=is1.getPresidentecommissione().getCognome()%> <%=is1.getPresidentecommissione().getNome()%> </b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>COMPONENTI COMMISSIONE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b>
                                                                <%=com.getTitolare1().getCognome()%> <%=com.getTitolare1().getNome()%> <br/>
                                                                <%=com.getTitolare2().getCognome()%> <%=com.getTitolare2().getNome()%>
                                                            </b>
                                                        </span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>SUPPLENTI SEGNALATI:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b>
                                                                <%=com.getSostituto1().getCognome()%> <%=com.getSostituto1().getNome()%> <br/>
                                                                <%=com.getSostituto2().getCognome()%> <%=com.getSostituto2().getNome()%>
                                                            </b>
                                                        </span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-primary"><b>NOTA NOMINA COMMISSIONE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span><b>
                                                                <%=is1.getProtnomina()%> del <%=Constant.sdf_PATTERNDATE4.format(is1.getDataprotnomina())%>
                                                            </b>
                                                        </span>
                                                    </label>

                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>DATA ESAME:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <input type="date" name="DATAESAME" id="DATAESAME" class="form-control" required placeholder="..."/>
                                                    </label>   
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <span class="text-danger"><b>ORE SVOLTE:</b></span>
                                                    </label>
                                                    <label class="col-md-2 col-form-label fw-bold">
                                                        <input type="text" name="ORESVOLTE" id="ORESVOLTE" class="form-control intvalue" required />
                                                    </label>   
                                                </div>
                                                <hr>
                                                <div class="row col-md-12">
                                                    <h4>DATI ALLIEVI</h4>
                                                </div>
                                                <div class="row col-md-12">
                                                    <div class="table-responsive ">
                                                        <!--begin::Table-->
                                                        <table class="table align-middle gy-3 table-bordered table-hover" id="tab_dt1" style="border-bottom: 2px;">
                                                            <!--begin::Table head-->
                                                            <thead>
                                                                <tr>
                                                                    <th class="p-2 w-20px">#</th>
                                                                    <th class="p-2 w-50px">COGNOME</th>
                                                                    <th class="p-2 w-50px">NOME</th>
                                                                    <th class="p-2 w-50px">DATA DI NASCITA</th>
                                                                    <th class="p-2 w-50px">ESTREMI DOC. ID.</th>
                                                                    <th class="p-2 w-50px">ORE PRESENZA</th>
                                                                    <th class="p-2 w-50px">GIUDIZIO AMMISSIONE</th>
                                                                    <th class="p-2 w-50px">VOTO (MAX 100)</th>
                                                                    <th class="p-2 w-150px">ESITO</th>
                                                                </tr>
                                                            </thead>
                                                            <!--end::Table head-->
                                                            <!--begin::Table body-->
                                                            <tbody> 
                                                                <%int i = 0;
                                                                    for (Allievi a1 : allievi) {
                                                                        i++;
                                                                %>
                                                                <tr>
                                                                    <td>
                                                                        <%=i%>
                                                                    </td>
                                                                    <td>
                                                                        <%=a1.getCognome()%>
                                                                    </td>
                                                                    <td>
                                                                        <%=a1.getNome()%>
                                                                    </td>
                                                                    <td>
                                                                        <%=Constant.sdf_PATTERNDATE4.format(a1.getDatanascita())%>
                                                                    </td>
                                                                    <td>
                                                                        <%=a1.getNumdocid()%>
                                                                    </td>
                                                                    <td>
                                                                        <label class="col-form-label fw-bold">
                                                                            <input type="text" name="OREPRES_<%=a1.getIdallievi()%>" id="OREPRES_<%=a1.getIdallievi()%>" class="form-control intvalue" required 
                                                                                   placeholder="..."/>
                                                                        </label>  
                                                                    </td>
                                                                    <td>
                                                                        <label class="col-form-label fw-bold">
                                                                            <input type="text" name="GIUDIZIO_<%=a1.getIdallievi()%>" id="GIUDIZIO_<%=a1.getIdallievi()%>" 
                                                                                   class="form-control" required 
                                                                                   placeholder="..."/>
                                                                        </label>  
                                                                    </td>
                                                                    <td>
                                                                        <label class="col-form-label fw-bold">
                                                                            <input type="text" name="VOTO_<%=a1.getIdallievi()%>" id="VOTO_<%=a1.getIdallievi()%>" 
                                                                                   class="form-control intvalue3" required 
                                                                                   placeholder="..."/>
                                                                        </label>  
                                                                    </td>
                                                                    <td>
                                                                        <select aria-label="Scegli..." 
                                                                                data-placeholder="..." 
                                                                                class="form-select form-select-white" 
                                                                                name="ESITO_<%=a1.getIdallievi()%>"
                                                                                id="ESITO_<%=a1.getIdallievi()%>"
                                                                                style="width: 100%;">
                                                                            <option value="">...</option>  
                                                                            <option value="IDONEO">IDONEO</option>  
                                                                            <option value="NON IDONEO">NON IDONEO</option>  
                                                                        </select>
                                                                    </td>
                                                                </tr>
                                                                <%}%>
                                                            </tbody>
                                                            <!--end::Table body-->
                                                        </table>
                                                        <!--end::Table-->
                                                    </div>
                                                </div>
                                                <hr>
                                                <div class="row col-md-12">
                                                    <button type="button" class="btn btn-success btn-lg"> <i class="fa fa-save"></i> SALVA DATI E GENERA DOCUMENTI</button>
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

            <script src="assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>
            <script src="assets/plugins/DataTables/jquery-3.5.1.js"></script>
            <script src="assets/plugins/DataTables/jquery.dataTables.min.js"></script>
            <script src="assets/plugins/DataTables/datatables.min.js"></script>
            <script src="assets/plugins/DataTables/date-eu.js"></script>
            <script src="assets/plugins/DataTables/date-euro.js"></script>
            <!--end::Page Vendors Javascript-->
            <!--begin::Page Custom Javascript(used by this page)-->
            <script src="assets/js/widgets.bundle.js"></script>
            <script src="assets/js/custom/widgets.js"></script>
            <script src="assets/fontawesome-6.0.0/js/all.js"></script>
            <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
            <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
            <script type="text/javascript" src="assets/plugins/select2/select2_v4.1.0.min.js"></script>
            <script type="text/javascript" src="assets/plugins/select2/i18n/it.js"></script>
            <script type="text/javascript" src="assets/plugins/jquery-confirm.min3.3.2.js"></script>

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