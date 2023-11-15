<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>
<%@page import="rc.soop.sic.jpa.CommissioneEsameSostituzione"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.soop.sic.jpa.CommissioneEsame"%>
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
                String idcommS = Utils.getRequestValue(request, "idcomm");
                if (idcommS.equals("")) {
                    idcommS = (String) session.getAttribute("ses_idcomm");
                } else {
                    session.setAttribute("ses_idcomm", idcommS);
                }
                String iddocenteS = Utils.getRequestValue(request, "iddocente");
                if (iddocenteS.equals("")) {
                    iddocenteS = (String) session.getAttribute("ses_iddocente");
                } else {
                    session.setAttribute("ses_iddocente", iddocenteS);
                }

                EntityOp eo = new EntityOp();

                CommissioneEsame ce = eo.getEm().find(CommissioneEsame.class, Long.valueOf(Utils.dec_string(idcommS)));
                List<CommissioneEsameSostituzione> sost = eo.list_sostituzioni_comm(ce);
                Docente comp1 = eo.getEm().find(Docente.class, Long.valueOf(Utils.dec_string(iddocenteS)));

                Corsoavviato is1 = ce.getCorsodiriferimento();
                boolean sost1_giapres = false;
                boolean sost2_giapres = false;

                for (CommissioneEsameSostituzione ces1 : sost) {
                    if (ces1.getSostituto().getIddocente().equals(ce.getSostituto1().getIddocente())) {
                        sost1_giapres = true;
                    } else if (ces1.getSostituto().getIddocente().equals(ce.getSostituto2().getIddocente())) {
                        sost2_giapres = true;
                    }
                }
    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Sostituzione commissione d'esame</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="assets/css/gfont.css" />
        <!--end::Fonts-->
        <!--begin::Page Vendor Stylesheets(used by this page)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="assets/plugins/jquery-confirm.3.3.2.min.css">
        <link href="assets/plugins/select2/select2_v4.1.0.min.css" rel="stylesheet" type="text/css" />
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
                                <span class="card-label fw-bolder fs-3 mb-1">SOSTITUZIONE COMMISSIONE - CORSO ID: <%=is1.getIdcorsoavviato()%> - <%=is1.getCorsobase().getIstanza().getTipologiapercorso().getNometipologia()%> - <u><%=is1.getCorsobase().getRepertorio().getDenominazione()%></u></span>
                            </h3>
                        </div>
                        <div class="card-body py-3">
                            <form action="Operations" method="POST">
                                <input type="hidden" name="type" value="SOSTITUISCICOMMISSIONE" />
                                <input type="hidden" ID="IDCORSO" name="IDCORSO" value="<%=is1.getIdcorsoavviato()%>" />
                                <input type="hidden" ID="IDCOMM" name="IDCOMM" value="<%=ce.getIdcommissione()%>" />
                                <input type="hidden" ID="IDCOMPORIGINALE" name="IDCOMPORIGINALE" value="<%=comp1.getIddocente()%>" />
                                <div class="row col-md-12">
                                    <label class="col-md-3 col-form-label fw-bold fs-6">
                                        <span><b>SOSTITUISCI <u class="text-danger"><%=comp1.getCognome()%> <%=comp1.getNome()%></u> CON:</b></span>
                                    </label>
                                    <div class="col-md-6">
                                        <select aria-label="Scegli..." 
                                                data-placeholder="..." 
                                                class="form-select form-select-lg form-select-transparent" 
                                                name="SOSTITUTO"
                                                id="SOSTITUTO" 
                                                required
                                                >
                                            <option value="">...</option>
                                            <%if (!sost1_giapres) {%>
                                            <option value="<%=ce.getSostituto1().getIddocente()%>"><%=ce.getSostituto1().getCognome()%> <%=ce.getSostituto1().getNome()%></option>  
                                            <%}%>
                                            <%if (!sost2_giapres) {%>
                                            <option value="<%=ce.getSostituto2().getIddocente()%>"><%=ce.getSostituto2().getCognome()%> <%=ce.getSostituto2().getNome()%></option>  
                                            <%}%>
                                        </select>
                                    </div>
                                </div>
                                <br/>
                                <div class="row col-md-12">
                                    <label class="col-md-3 col-form-label fw-bold fs-6">
                                        <span class="text-danger">NUMERO NOTA:</span>
                                    </label>
                                    <div class="col-md-3">
                                        <input type="text" class="form-control form-control-lg" 
                                               name="NOTASOSTITUZIONE"
                                               id="NOTASOSTITUZIONE" required
                                               />
                                    </div>
                                    <label class="col-md-3 col-form-label fw-bold fs-6">
                                        <span class="text-danger">DATA NOTA:</span>
                                    </label>
                                    <div class="col-md-3">
                                        <input type="date" class="form-control form-control-lg" 
                                               name="DATANOTASOSTITUZIONE"
                                               id="DATANOTASOSTITUZIONE" required
                                               />
                                    </div>
                                </div>
                                <br/>
                                <div class="row col-md-3">
                                    <button type="submit" class="btn btn-success btn-sm"> <i class="fa fa-save" ></i> SALVA DATI</button>
                                </div>
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
        <script type="text/javascript" src="assets/plugins/select2/select2_v4.1.0.min.js"></script>
        <script type="text/javascript" src="assets/plugins/select2/i18n/it.js"></script>
        <script type="text/javascript" src="assets/plugins/jquery-confirm.min3.3.2.js"></script>
        <script type="text/javascript" src="assets/js/US_richiedicommissione.js"></script>

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