<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.TipoCorso"%>
<%@page import="rc.soop.sic.jpa.Corso"%>
<%@page import="java.util.List"%>
<%@page import="org.joda.time.DateTime"%>
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
                String idist = Utils.getRequestValue(request, "idist");
                if (idist.equals("")) {
                    idist = (String) session.getAttribute("ses_idist");
                }
                EntityOp eo = new EntityOp();
                Long idc1 = Long.valueOf(Utils.dec_string(idist));
                Istanza is1 = eo.getEm().find(Istanza.class, idc1);
                List<Corso> c1 = eo.getCorsiIstanza(is1);
    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Approva Istanza</title>
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
        <link rel="stylesheet" href="assets/plugins/jquery-confirm.3.3.2.min.css">
    </head>
    <!--end::Head-->
    <!--begin::Body-->
    <body id="kt_body" class="header-fixed header-tablet-and-mobile-fixed">
        <!--begin::Main-->
        <!--begin::Root-->


        <!--begin::Header-->
        <!--end::Header-->
        <!--begin::Content wrapper-->

        <!--begin::Post-->
        <div id="kt_content">
            <!--begin::Row-->
            <!--end::Row-->
            <!--begin::Row-->
            <!--end::Row-->
            <!--begin::Row-->
            <div class="row">
                <!--begin::Col-->
                <div class="row card-lg-stretch">
                    <div class="card-header border-0 pt-5">
                        <h3 class="card-title align-items-start flex-column">
                            <span class="card-label fw-bolder fs-3 mb-1">
                                Istanza di autorizzazione allo svolgimento di corsi di formazione professionale - ID: <%=is1.getIdistanza()%> - INVIATA IN DATA: <%=is1.getDatainvio()%> -
                                TIPO: <%=is1.getTipologiapercorso().getNometipologia()%>
                            </span>
                        </h3>
                    </div>
                    <!--begin::Col-->
                    <div class="card-body">
                        <form class="form-control" action="Operations" method="POST" onsubmit="return checkapprovaistanza();">
                            <input type="hidden" name="type" value="APPROVAISTANZA" /> 
                            <input type="hidden" name="idist" value="<%=idist%>" /> 
                            <div class="card-body border-0">

                                <%for (Corso cor : c1) {%>
                                <div class="row row-border">
                                    <!--begin::Label-->
                                    <label class="col-lg-3 col-form-label fw-bold fs-6" >
                                        <span class="text-info"><b>NOME CORSO</b></span>
                                    </label>
                                    <label class="col-lg-3 col-form-label fw-bold fs-6">
                                        <span class="text-dark"><%=cor.getRepertorio().getDenominazione()%></span>
                                    </label>
                                    <!--begin::Label-->
                                    <label class="col-lg-3 col-form-label fw-bold fs-6" >
                                        <span class="text-info"><b>QUANTITA RICHIESTA</b></span>
                                    </label>
                                    <label class="col-lg-3 col-form-label fw-bold fs-6">
                                        <span class="text-dark">Edizioni: <%=cor.getQuantitarichiesta()%></span>
                                    </label>
                                </div>
                                <div class="row row-border">
                                    <!--begin::Label-->
                                    <label class="col-lg-3 col-form-label fw-bold fs-6" >
                                        <span class="text-info"><b>APPROVATO</b></span>
                                    </label>
                                    <div class="col-lg-3 col-form-label form-check form-switch checkboxesr">
                                        <input class="form-check-input" type="checkbox" role="switch" checked
                                               id="OK_<%=cor.getIdcorso()%>" 
                                               name="OK_<%=cor.getIdcorso()%>" 
                                               onchange="return abilitaapprovazione('<%=cor.getIdcorso()%>')"
                                               />
                                    </div>
                                    <!--begin::Label-->
                                    <label class="col-lg-3 col-form-label fw-bold fs-6" >
                                        <span class="text-info"><b>CODICE CORSO</b></span>
                                    </label>
                                    <label class="col-lg-3 col-form-label fw-bold fs-6">
                                        <%if (is1.getTipologiapercorso().getTipocorso().equals(TipoCorso.FINANZIATO)) {%>
                                        <table>
                                            <tr>
                                                <th>CIP</th>
                                                <td><input type="text" name="CIP_<%=cor.getIdcorso()%>" id="CIP_<%=cor.getIdcorso()%>"
                                                           class="form-control" 
                                                           required /></td>
                                            </tr>
                                            <tr>
                                                <th>CUP</th>
                                                <td><input type="text" name="CUP_<%=cor.getIdcorso()%>" id="CUP_<%=cor.getIdcorso()%>"
                                                           class="form-control" 
                                                           required /></td>
                                            </tr>
                                            <tr>
                                                <th>ID</th>
                                                <td><input type="text" name="ID_<%=cor.getIdcorso()%>" id="ID_<%=cor.getIdcorso()%>"
                                                           class="form-control" 
                                                           required /></td>
                                            </tr>
                                            <tr>
                                                <th>CS</th>
                                                <td><input type="text" name="CS_<%=cor.getIdcorso()%>" id="CS_<%=cor.getIdcorso()%>"
                                                           class="form-control" 
                                                           required /></td>
                                            </tr>
                                            <tr>
                                                <th>ED</th>
                                                <td><input type="text" name="ED_<%=cor.getIdcorso()%>" id="ED_<%=cor.getIdcorso()%>"
                                                           class="form-control" 
                                                           required /></td>
                                            </tr>
                                        </table>
                                        <%} else {%>
                                        <span class="text-dark"><u>GENERATO DAL SISTEMA</u></span>
                                                <%}%>
                                    </label>
                                </div>    
                                <%}%>



                                <hr>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-large btn-success"><i class="fa fa-save"></i> Salva Dati e Approva Istanza</button>
                                </div>
                                <!--begin::Table container-->
                            </div> 
                        </form>
                    </div>
                </div>
                <!--end::Col-->


            </div>
            <!--end::Row-->
        </div>
        <!--end::Post-->
        <!--begin::Footer-->


        <!--end::Content wrapper-->
        <!--end::Wrapper-->

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
        <!--end::Global Javascript Bundle-->
        <!--begin::Page Vendors Javascript(used by this page)-->
        <!--end::Page Vendors Javascript-->
        <!--begin::Page Custom Javascript(used by this page)-->
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <script src="assets/plugins/jquery-confirm.min3.3.2.js"></script>
        <script src="assets/js/ADM_approvaistanza.js"></script>

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