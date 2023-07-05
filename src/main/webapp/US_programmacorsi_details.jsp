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
                String idcorso = (String) session.getAttribute("ses_idcorso");
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
            <form method="POST" action="Operations" onsubmit="return verificasalvataggiodati();">
                <input type="hidden" name="type" value="SALVAPIANIFICAZIONE"/>
                <input type="hidden" name="idcorsodasalvare" value="<%=co1.getIdcorso()%>"/>
                <div class="col-xl-12">
                    <!--begin::Tables Widget 3-->
                    <div class="card h-xl-100">
                        <!--begin::Header-->
                        <div class="card-header border-0 pt-5">
                            <h3 class="card-title align-items-start flex-column">
                                <span class="card-label fw-bolder fs-3 mb-1">INSERISCI DETTAGLI MODULO FORMATIVO</span>
                            </h3>
                            <button class="btn btn-lg btn-success"><i class="fa fa-save"></i> SALVA DATI</button>
                        </div>
                        <div class="card-body py-3">
                            <div class="row">
                                <div class="row row-border col-md-12 p-5">
                                    <!--begin::Label-->
                                    <label class="col-lg-2 col-form-label fw-bold fs-6" >
                                        <span class="text-primary"><b>NOME MODULO</b></span>
                                    </label>
                                    <div class="col-md-6 fv-row">
                                        <input type="text" name="NOMEMODULO" id="NOMEMODULO"
                                               class="form-control" 
                                               placeholder="..." required />
                                    </div>
                                    <!--begin::Label-->
                                    <label class="col-lg-2 col-form-label fw-bold fs-6" >
                                        <span class="text-primary"><b>ORE TOTALI</b></span>
                                    </label>
                                    <div class="col-md-2 fv-row">
                                        <input type="text" name="ORETOTALI" id="ORETOTALI"
                                               class="form-control" 
                                               placeholder="..." required />
                                    </div>
                                </div>
                                <div class="row row-border col-md-12 p-5">
                                    <!--begin::Label-->
                                    <label class="col-lg-2 col-form-label fw-bold fs-6" >
                                        <span class="text-primary"><b>ORE AULA TEORICA</b></span>
                                    </label>
                                    <div class="col-md-2 fv-row">
                                        <input type="text" name="NOMEMODULO" id="NOMEMODULO"
                                               class="form-control" 
                                               placeholder="..." required />
                                    </div>
                                    <!--begin::Label-->
                                    <label class="col-lg-2 col-form-label fw-bold fs-6" >
                                        <span class="text-primary"><b>ORE AULA TECNICO/OPERATIVA</b></span>
                                    </label>
                                    <div class="col-md-2 fv-row">
                                        <input type="text" name="ORETOTALI" id="ORETOTALI"
                                               class="form-control" 
                                               placeholder="..." required />
                                    </div>
                                    <label class="col-lg-2 col-form-label fw-bold fs-6" >
                                        <span class="text-dark"><b>ORE E-LEARNING</b></span>
                                    </label>
                                    <div class="col-md-2 fv-row">
                                        <input type="text" name="ORETOTALI" id="ORETOTALI"
                                               class="form-control" 
                                               placeholder="..." required />
                                    </div>
                                </div>
                                <div class="row row-border col-md-12 p-5">

                                    <div class="col-md-2 fv-row">   
                                        <select name="ctlingua_" aria-label="Scegli..." 
                                                data-control="select2" data-placeholder="Lingua Straniera" 
                                                class="form-select"
                                                required>
                                            <option value="">Scegli Lingua</option>
                                        </select>
                                    </div>
                                    <div class="col-md-3 fv-row">
                                        <input type="text" name="ctdescr_" id="ctdescr_"
                                               class="form-control" 
                                               placeholder="..." required />
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
        <script type="text/javascript" src="assets/js/common.js"></script>
        <script src="assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>

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