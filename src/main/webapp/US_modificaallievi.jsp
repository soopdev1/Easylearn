<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>
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
                String idistS = Utils.getRequestValue(request, "idcorso");
                if (idistS.equals("")) {
                    idistS = (String) session.getAttribute("ses_idcorso");
                } else {
                    session.setAttribute("ses_idcorso", idistS);
                }
                EntityOp eo = new EntityOp();
                Corsoavviato is1 = eo.getEm().find(Corsoavviato.class, Long.valueOf(idistS));
                boolean modify = false;
                List<Allievi> allievi = new ArrayList<>();
                if (!Utils.isAdmin(session)) {
                    SoggettoProponente so = ((User) session.getAttribute("us_memory")).getSoggetto();
                    if (so.getIdsoggetto().equals(is1.getCorsobase().getSoggetto().getIdsoggetto())
                            && (is1.getStatocorso().getCodicestatocorso().equals("43") || is1.getStatocorso().getCodicestatocorso().equals("44"))) {
                        modify = true;
                        allievi = eo.getAllieviSoggettoAvvioCorso(so);
                    }
                }
                List<Allievi> allieviok = eo.getAllieviCorsoAvviato(is1);
    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Modifica allievi corso</title>
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
                                <span class="card-label fw-bolder fs-3 mb-1">MODIFICA ALLIEVI CORSO ID: <%=is1.getIdcorsoavviato()%> - <%=is1.getCorsobase().getIstanza().getTipologiapercorso().getNometipologia()%> - <u><%=is1.getCorsobase().getRepertorio().getDenominazione()%></u></span>
                            </h3>
                        </div>
                        <div class="card-body py-3">
                            <div class="row col-md-12">
                                <label class="col-md-4 col-form-label fw-bold fs-6">
                                    <span class="text-danger"><b>ALLIEVI ATTUALI:</b></span>
                                </label>
                                <label class="col-md-8 col-form-label fw-bold">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Cognome</th>
                                                <th>Nome</th>
                                                <th>Codice Fiscale</th>
                                                <th>Tirocinio/Stage</th>
                                                <th>Stato</th>
                                                <th>Azioni</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%for (Allievi a1 : allieviok) {%>
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
                                                    <%=Utils.getEtichettastato(a1.getStatotirocinio())%>
                                                </td>
                                                <td>
                                                    <%=Utils.getEtichettastato(a1.getStatoallievo())%>
                                                </td>
                                                <td>
                                                    <%
                                                    if (a1.getStatoallievo().equals(Stati.ATTIVO) || a1.getStatoallievo().equals(Stati.AVVIO)) {%>
                                                    <button type="button" data-bs-toggle="tooltip" title="MODIFICA STATO ALLIEVO" 
                                                            data-preload="false" class="btn btn-sm btn-bg-light btn-primary"
                                                            onclick="return modificastatoallievo('<%=a1.getIdallievi()%>',
                                                                            '<%=Base64.encodeBase64String((a1.getCognome() + " " + a1.getNome()).getBytes())%>',
                                                                            '<%=Base64.encodeBase64String(Utils.getEtichettastato(a1.getStatoallievo()).getBytes())%>')"
                                                            >
                                                        <i class="fa fa-edit"></i>
                                                    </button>
                                                    <%}%>
                                                    <%if (a1.getStatotirocinio().equals(Stati.AVVIARE)) {%>
                                                    <a data-bs-toggle="tooltip" title="AVVIA TIROCINIO/STAGE ALLIEVO" 
                                                       href="US_tirocinioallievo.jsp?idallievo=<%=Utils.enc_string(String.valueOf(a1.getIdallievi()))%>"
                                                       data-preload="false" class="btn btn-sm btn-bg-light btn-secondary">
                                                        <i class="fa fa-forward"></i>
                                                    </a>
                                                    <%}%>
                                                    <%if (a1.getStatotirocinio().equals(Stati.ATTIVO)) {%>
                                                    <a data-bs-toggle="tooltip" title="VISUALIZZA TIROCINIO/STAGE ALLIEVO" 
                                                       href="US_tirocinioallievo.jsp?idallievo=<%=Utils.enc_string(String.valueOf(a1.getIdallievi()))%>"
                                                       data-preload="false" class="btn btn-sm btn-bg-light btn-secondary">
                                                        <i class="fa fa-file-alt"></i>
                                                    </a>
                                                    <%}%>
                                                </td>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                    </table>
                                </label>
                            </div>
                            <hr>
                            <form action="Operations" method="POST">
                                <input type="hidden" name="type" value="AGGIUNGIALLIEVICORSO" />
                                <input type="hidden" name="IDCORSO" value="<%=is1.getIdcorsoavviato()%>" />
                                <div class="row col-md-12">
                                    <label class="col-md-4 col-form-label fw-bold fs-6">
                                        <span class="text-danger"><b>NUOVI ALLIEVI:</b></span>
                                    </label>
                                    <label class="col-md-8 col-form-label fw-bold">
                                        <select name="ALLIEVI" id="ALLIEVI" aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..." 
                                                class="form-select form-select-solid form-select-lg fw-bold" required multiple>
                                            <option value="">Scegli...</option>
                                            <%for (Allievi al1 : allievi) {%>
                                            <option value="<%=al1.getIdallievi()%>">
                                                <%=al1.getCodicefiscale()%> - <%=al1.getCognome()%> <%=al1.getNome()%>
                                            </option>                                                                
                                            <%}%>
                                        </select>
                                    </label>
                                    <span class="help-block">NUMERO MASSIMO ALLIEVI: <%=is1.getCorsobase().getNumeroallievi()%>. E' POSSIBILE AGGIUNGERE NUOVI ALLIEVI ENTRO IL 30% DI AVANZAMENTO DEL CORSO.</span>
                                </div>
                                <input type="hidden" id="allievimax" value="<%=is1.getCorsobase().getNumeroallievi()%>" />
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
        <script src="assets/js/US_modificallievi.js"></script>
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