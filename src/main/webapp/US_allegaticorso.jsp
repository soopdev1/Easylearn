<%-- 
    Document   : US_allegatiallievi
    Created on : 12 ago 2023, 14:18:44
    Author     : raf
--%>
<%@page import="rc.soop.sic.jpa.SoggettoProponente"%>
<%@page import="rc.soop.sic.jpa.Corsoavviato"%>
<%@page import="rc.soop.sic.jpa.EnteStage"%>
<%@page import="rc.soop.sic.jpa.Allievi"%>
<%@page import="rc.soop.sic.jpa.Information"%>
<%@page import="rc.soop.sic.jpa.Allegati"%>
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
        <title><%=Constant.NAMEAPP%>: Gestione Allegati</title>
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
        <link href="assets/plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="assets/plugins/jquery-confirm.3.3.2.min.css">

        <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/plus.css" rel="stylesheet" type="text/css" />

        <!--end::Global Stylesheets Bundle-->
    </head>
    <!--end::Head-->
    <!--begin::Body-->
    <%

        String idistS = Utils.getRequestValue(request, "idcorso");
        if (idistS.equals("")) {
            idistS = (String) session.getAttribute("ses_idcorso");
        } else {
            session.setAttribute("ses_idcorso", idistS);
        }
        EntityOp eo = new EntityOp();
        Long idist = Long.valueOf(Utils.dec_string(idistS));
        Corsoavviato is1 = eo.getEm().find(Corsoavviato.class, idist);
        List<Allegati> la = eo.list_allegati(null, null, is1, null, null, null);
        List<Information> info1 = eo.list_info(is1);
        boolean showinfo = !info1.isEmpty();

        boolean modify = true;
        if (Utils.isAdmin(session)) {
            modify = false;
        } else {
            SoggettoProponente so = ((User) session.getAttribute("us_memory")).getSoggetto();
            modify = so.getIdsoggetto().equals(is1.getCorsobase().getSoggetto().getIdsoggetto());

        }
    %>
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
                                            <h1 class="text-center fs-4">Corso di formazione ID: <%=is1.getIdcorsoavviato()%> - Nome: <%=is1.getCorsobase().getRepertorio().getDenominazione()%></h1>                                    
                                        </div>
                                    </div>
                                    <!--end::Row-->
                                    <div class="card h-xl-100">
                                        <%if (is1.getStatocorso().getCodicestatocorso().equals("42")) {%>    
                                        <div class="card-header border-0 pt-5 bg-warning">
                                            <h3 class="card-title align-items-start flex-column">
                                                <span class="card-label fw-bolder fs-3 mb-1">SOCCORSO ISTRUTTORIO</span>
                                            </h3>
                                        </div>
                                        <%if (showinfo) {%>
                                        <%
                                            for (Information info2 : info1) {
                                        %>

                                        <div class="card-body py-3 bg-warning">
                                            <hr>
                                            <div class="row row-border col-md-12 p-5">
                                                <!--begin::Label-->
                                                <label class="col-lg-3 col-form-label fw-bold fs-6" >
                                                    <span class="text-dark"><b>UTENTE:</b></span>
                                                </label>
                                                <div class="col-md-9 fv-row">
                                                    <span class="text-dark"><%=info2.getUtente()%></span>
                                                </div>
                                            </div>
                                            <div class="row row-border col-md-12 p-5">
                                                <!--begin::Label-->
                                                <label class="col-lg-3 col-form-label fw-bold fs-6" >
                                                    <span class="text-dark"><b>DATA:</b></span>
                                                </label>
                                                <div class="col-md-9 fv-row">
                                                    <span class="text-dark"><%=Constant.sdf_PATTERNDATE5.format(info2.getDatacreazione())%></span>
                                                </div>
                                            </div>
                                            <div class="row row-border col-md-12 p-5">
                                                <!--begin::Label-->
                                                <label class="col-lg-3 col-form-label fw-bold fs-6" >
                                                    <span class="text-dark"><b>MOTIVAZIONE:</b></span>
                                                </label>
                                                <div class="col-md-9 fv-row">
                                                    <span class="text-dark"><%=info2.getMotivazione()%></span>
                                                </div>
                                            </div>
                                        </div>

                                        <%}%>
                                        <%}%>
                                        <%}%>
                                        <%if (modify) {%>
                                        <!--begin::Header-->
                                        <form action="Operations?type=UPLDOCCORSO" method="post"  enctype="multipart/form-data">
                                            <input type="hidden" name="idcorsoavviato" value="<%=is1.getIdcorsoavviato()%>"/>
                                            <div class="card-header border-0 pt-5">
                                                <h3 class="card-title align-items-start flex-column">
                                                    <span class="card-label fw-bolder fs-3 mb-1">CARICA NUOVO ALLEGATO</span>
                                                </h3>
                                                <button class="btn btn-primary"><i class="fa fa-upload"></i> UPLOAD</button>
                                            </div>
                                            <div class="card-body py-3">
                                                <div class="row row-border col-md-12 p-5">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                        <span class="text-info"><b>SELEZIONA ALLEGATO</b></span>
                                                    </label>
                                                    <div class="col-md-8 fv-row">
                                                        <input class="form-control" type="file" id="formFile"name="formFile" required />
                                                    </div>
                                                </div>

                                                <div class="row row-border col-md-12 p-5">
                                                    <label class="col-lg-4 col-form-label fw-bold fs-6" >
                                                        <span class="text-info"><b>DESCRIZIONE ALLEGATO (MAX 50 CARATTERI)</b></span>
                                                    </label>
                                                    <div class="col-md-8 fv-row">
                                                        <input type="text" name="DESCRIZIONE" id="DESCRIZIONE"
                                                               class="form-control" maxlength="50"
                                                               placeholder="..." required />
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                        <hr>
                                        <%}%>

                                        <div class="card-header border-0 pt-5">
                                            <h3 class="card-title align-items-start flex-column">
                                                <span class="card-label fw-bolder fs-3 mb-1">ELENCO ALLEGATI</span>
                                            </h3>
                                        </div>

                                        <div class="card-body py-3">
                                            <!--begin::Table container-->
                                            <div class="table-responsive">
                                                <!--begin::Table-->
                                                <table class="table align-middle gy-3 table-bordered table-hover" 
                                                       id="tab_dt1" style="border-bottom: 2px;">
                                                    <!--begin::Table head-->
                                                    <thead>
                                                        <tr>
                                                            <th class="p-2 w-50px">ID</th>
                                                            <th class="p-2 w-50px">CODICE</th>
                                                            <th class="p-2 w-150px">DESCRIZIONE</th>
                                                            <th class="p-2 w-50px">DATA CARICAMENTO</th>
                                                            <th class="p-2 w-50px">TIPO FILE</th>
                                                            <th class="p-2 w-50px">STATO</th>
                                                            <th class="p-2 w-50px">AZIONI</th>
                                                        </tr>
                                                    </thead>
                                                    <!--end::Table head-->
                                                    <!--begin::Table body-->
                                                    <tbody>
                                                        <%for (Allegati d2 : la) {


                                                        %>
                                                        <tr>
                                                            <td class="p-2 w-50px"><%=d2.getIdallegati()%></td>
                                                            <td class="p-2 w-50px"><%=d2.getCodiceallegati()%></td>
                                                            <td class="p-2 w-150px"><%=d2.getDescrizione()%></td>
                                                            <td class="p-2 w-50px"><%=Constant.sdf_PATTERNDATE5.format(d2.getDatacaricamento())%></td>
                                                            <td class="p-2 w-50px"><%=d2.getMimetype()%></td>
                                                            <td class="p-2 w-50px"><%=d2.getStato().getHtmldescr()%></td>
                                                            <td class="p-2 w-150px">
                                                                <form method="POST" action="Operations" target="_blank">
                                                                    <input type="hidden" name="type" value="VISUALDOC"/>
                                                                    <input type="hidden" name="iddocument" value="<%=d2.getIdallegati()%>" />
                                                                    <button type="submit" class="btn btn-sm btn-bg-light btn-success"
                                                                            data-bs-toggle="tooltip" title="VISUALIZZA DOCUMENTO" 
                                                                            data-preload='false'
                                                                            ><i class="fa fa-file-alt"></i>
                                                                    </button>
                                                                    <%if (modify) {%>
                                                                    | 
                                                                    <button type="button"class="btn btn-sm btn-bg-light btn-danger"
                                                                            data-bs-toggle="tooltip" title="ELIMINA DOCUMENTO" 
                                                                            data-preload='false'
                                                                            onclick="return deletedoc('<%=d2.getIdallegati()%>')"><i class="fa fa-remove"></i>
                                                                    </button>

                                                                    <%if (d2.getStato().getCodicestatocorso().equals("31")) {%>

                                                                    <a href="US_sostituisciallegato.jsp?id_alleg=<%=d2.getIdallegati()%>"
                                                                       data-fancybox data-type='iframe' 
                                                                       data-bs-toggle="tooltip" title="SOSTITUISCI" 
                                                                       data-preload='false' data-width='50%' data-height='50%' 
                                                                       class="btn btn-sm btn-bg-light btn-warning text-dark fan1" >
                                                                        <i class="fa fa-arrow-right-arrow-left"></i>
                                                                    </a>
                                                                    <%}
                                                                    }%>
                                                                </form>
                                                            </td>      
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <%}%>
                                </div>
                                <!--end::Post-->
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
        <!--end::Global Javascript Bundle-->
        <!--begin::Page Vendors Javascript(used by this page)-->
        <script src="assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>
        <script src="assets/plugins/DataTables/jquery-3.5.1.js"></script>
        <script src="assets/plugins/DataTables/jquery.dataTables.min.js"></script>
        <script src="assets/plugins/DataTables/datatables.min.js"></script>
        <script src="assets/plugins/DataTables/date-euro.js"></script>
        <!--end::Page Vendors Javascript-->
        <!--begin::Page Custom Javascript(used by this page)-->
        <script src="assets/js/widgets.bundle.js"></script>
        <script src="assets/js/custom/widgets.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
        <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
        <script src="assets/plugins/jquery-confirm.min3.3.2.js"></script>
        <script src="assets/js/US_gestioneallegati.js"></script>
        <script src="assets/js/common.js"></script>

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
