<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.Allegati"%>
<%@page import="java.util.List"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="rc.soop.sic.jpa.EntityOp"%>
<%@page import="rc.soop.sic.jpa.Corsoavviato"%>
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
                List<Allegati> la = eo.list_allegati(is1, null, null, null, null);
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
        <link href="assets/plugins/custom/fullcalendar/fullcalendar.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
        <!--end::Page Vendor Stylesheets-->
        <!--begin::Global Stylesheets Bundle(used by all pages)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
        <script src="https://cdn.tiny.cloud/1/<%=Constant.TINYMCEKEY%>/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
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
                <!--begin::Header-->
                <!--end::Header-->
                <!--begin::Content wrapper-->
                <div class="d-flex flex-column-fluid">
                    <!--begin::Container-->
                    <div class="d-flex flex-column flex-column-fluid container-fluid">
                        <!--begin::Post-->
                        <div class="content flex-column-fluid" id="kt_content">
                            <!--begin::Row-->
                            <!--end::Row-->
                            <!--begin::Row-->
                            <!--end::Row-->
                            <!--begin::Row-->
                            <div class="row g-10">
                                <!--begin::Col-->
                                <div class="row g-5 g-lg-10">
                                    <div class="card-header border-0 pt-5">
                                        <h3 class="card-title align-items-start flex-column">
                                            <span class="card-label fw-bolder fs-3 mb-1">
                                                Istanza di autorizzazione allo svolgimento di corsi di formazione professionale - ID: <%=is1.getIdistanza()%> - INVIATA IN DATA: <%=is1.getDatainvio()%>
                                            </span>
                                        </h3>

                                    </div>
                                    <!--begin::Col-->
                                    <form action="Operations" method="POST">
                                        <input type="hidden" name="type" value="RIGETTAISTANZA" /> 
                                        <input type="hidden" name="idist" value="<%=idist%>" /> 
                                        <div class="card-body border-0 pt-5">
                                            <div class="row form-control">

                                                <div class="form-check form-switch">
                                                    <input class="form-check-input" type="checkbox" role="switch" id="soccorsoistr"
                                                           name="soccorsoistr" onchange="return abilitasoccorsoistr();"/>
                                                    <label class="form-check-label" for="soccorsoistr">Abilita Soccorso Istruttorio</label>
                                                </div>
                                                <div class="form-group" id="abilitasoccorso">
                                                    <hr>
                                                    <!--begin::Table container-->
                                                    <%if (la.isEmpty()) {%>

                                                    <div class="alert alert-warning">
                                                        NON &#200; PRESENTE DOCUMENTAZIONE ALLEGATA ALL'ISTANZA. IL SOGGETTO DOVR&#192; CARICARE NUOVA DOCUMENTAZIONE.
                                                    </div>

                                                    <%} else {%>

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
                                                                <th class="p-2 w-50px">VISUALIZZA</th>
                                                                <th class="p-2 w-50px">DA SOSTITUIRE</th>
                                                            </tr>
                                                        </thead>
                                                        <!--end::Table head-->
                                                        <!--begin::Table body-->
                                                        <tbody>
                                                            <%for (Allegati d2 : la) {%>
                                                            <tr>
                                                                <td class="p-2 w-50px"><%=d2.getIdallegati()%></td>
                                                                <td class="p-2 w-50px"><%=d2.getCodiceallegati()%></td>
                                                                <td class="p-2 w-150px"><%=d2.getDescrizione()%></td>
                                                                <td class="p-2 w-50px"><%=Constant.sdf_PATTERNDATE5.format(d2.getDatacaricamento())%></td>
                                                                <td class="p-2 w-50px"><%=d2.getMimetype()%></td>
                                                                <td class="p-2 w-50px">
                                                                    <form method="POST" action="Operations" target="_blank">
                                                                        <input type="hidden" name="type" value="VISUALDOC"/>
                                                                        <input type="hidden" name="iddocument" value="<%=d2.getIdallegati()%>" />
                                                                        <button type="submit" class="btn btn-sm btn-bg-light btn-success"
                                                                                data-bs-toggle="tooltip" title="VISUALIZZA DOCUMENTO" 
                                                                                data-preload='false'
                                                                                ><i class="fa fa-file-alt"></i>
                                                                        </button>
                                                                    </form>
                                                                </td>
                                                                <td class="p-2 w-50px">
                                                                    <div class="form-check form-switch">
                                                                        <input class="form-check-input" type="checkbox" role="switch" 
                                                                               id="soc_ko_<%=d2.getIdallegati()%>" name="soc_ko_<%=d2.getIdallegati()%>"/>
                                                                        <label class="form-check-label" for="soc_ko_<%=d2.getIdallegati()%>">
                                                                            SI/NO</label>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <%}
                                                            %>

                                                        </tbody>
                                                    </table>

                                                    <%}%>
                                                </div>
                                                <hr>
                                                <div class="form-group">
                                                    <label>Inserire indicazioni per il Soggetto Proponente o la motivazione del rigetto</label>
                                                    <textarea name="motivazione"></textarea>
                                                </div>
                                                <hr>
                                                <div class="form-group">
                                                    <button type="submit" class="btn btn-large btn-primary"><i class="fa fa-save"></i> Salva Dati e Rigetta Istanza</button>
                                                </div>
                                            </div>
                                            <!--begin::Table container-->
                                        </div> 
                                    </form>
                                </div>
                                <!--end::Col-->


                            </div>
                            <!--end::Row-->
                        </div>
                        <!--end::Post-->
                        <!--begin::Footer-->
                        <jsp:include page="menu/footer1.jsp" /> 
                        <!--end::Footer-->
                    </div>
                    <!--end::Container-->
                </div>
                <!--end::Content wrapper-->
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
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <script src="assets/js/ADM_rigettaistanza.js"></script>

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