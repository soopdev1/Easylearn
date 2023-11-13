<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.PresidenteCommissione"%>
<%@page import="rc.soop.sic.jpa.Presenze_Tirocinio_Allievi"%>
<%@page import="rc.soop.sic.jpa.TirocinioStage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.soop.sic.jpa.User"%>
<%@page import="rc.soop.sic.jpa.SoggettoProponente"%>
<%@page import="rc.soop.sic.jpa.EnteStage"%>
<%@page import="java.util.List"%>
<%@page import="rc.soop.sic.jpa.Allievi"%>
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
                String idtirociniostage = Utils.getRequestValue(request, "idtirociniostage");
                EntityOp eo = new EntityOp();
                TirocinioStage ts1 = eo.getEm().find(TirocinioStage.class, Utils.parseLongR(idtirociniostage));
                Allievi is1 = ts1.getAllievi();
                boolean modify = false;
                if (!Utils.isAdmin(request.getSession())) {
                    PresidenteCommissione pc = ((User) session.getAttribute("us_memory")).getPresidente();
                    if (pc != null) {
                        modify = false;
                    } else {
                        SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
                        if (so.getIdsoggetto().equals(ts1.getEntestage().getSoggetto().getIdsoggetto())) {
                            modify = true;
                        }
                    }
                }

                List<Presenze_Tirocinio_Allievi> lpr = eo.list_presenzetirocinio_allievo(ts1);

                String presenzeinserite = Utils.countOreTirocinio(lpr, "60");
                String presenzeconvalid = Utils.countOreTirocinio(lpr, "61");

    %>
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Calendario Tirocinio Allievo</title>
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
    <body class="bg-white">
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
                            <span class="card-label fw-bolder fs-3 mb-1">DETTAGLIO PRESENZE TIROCINIO ALLIEVO - <%=is1.getCognome()%> <%=is1.getNome()%></span>
                        </h3>
                    </div>
                    <div class="card-body py-3">
                        <div class="row col-md-12">
                            <label class="col-md-2 col-form-label fw-bold">
                                <span class="text-danger"><b>COGNOME</b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold">
                                <span><b><%=is1.getCognome()%></b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold fs-6">
                                <span class="text-danger"><b>NOME</b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold">
                                <span><b><%=is1.getNome()%></b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold fs-6">
                                <span class="text-danger"><b>CODICE FISCALE</b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold">
                                <span><b><%=is1.getCodicefiscale()%></b></span>
                            </label>
                        </div>
                        <div class="row col-md-12">
                            <label class="col-md-2 col-form-label fw-bold">
                                <span class="text-danger"><b>ENTE OSPITANTE</b></span>
                            </label>
                            <label class="col-md-4 col-form-label fw-bold">
                                <span><b><%=ts1.getEntestage().getRAGIONESOCIALE()%></b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold fs-6">
                                <span class="text-danger"><b>SEDE</b></span>
                            </label>
                            <label class="col-md-4 col-form-label fw-bold">
                                <span><b><%=ts1.getSedestage().getIndirizzo()%> <%=ts1.getSedestage().getCap()%> - <%=ts1.getSedestage().getComune()%>, <%=ts1.getSedestage().getProvincia()%></b></span>
                            </label>
                        </div>
                        <div class="row col-md-12">
                            <label class="col-md-2 col-form-label fw-bold">
                                <span class="text-danger"><b>ORE PREVISTE</b></span>
                            </label>
                            <label class="col-md-2 col-form-label fw-bold">
                                <span><b><%=ts1.getOrepreviste()%></b></span>
                            </label>
                        </div>
                        <%if (modify) {%>
                        <hr>
                        <form action="Operations" method="POST" id="forminsert">


                            <label class="col-form-label fw-bold fs-6 text-primary">AGGIUNGI PRESENZA TIROCINIO:</label> 
                            <button class="btn btn-success btn-sm" data-preload='false' type="sumbit"
                                    data-bs-toggle="tooltip" title="SALVA ED INSERISCI PRESENZA" 
                                    >
                                <i class="fa fa-save"></i></button>


                            <input type="hidden" name="type" value="INSERISCIPRESENZATIROCINIO"/>
                            <input type="hidden" name="idtirociniostage" value="<%=idtirociniostage%>"/>

                            <div class="row col-md-12">
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class=" text-primary"><b>DATA LEZIONE:</b></span>
                                </label>
                                <div class="col-md-2 col-form-label fw-bold fs-6">
                                    <input type="date" class="form-control" id="datetiroc" name="datetiroc" required />
                                </div>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class=" text-primary"><b>ORA INIZIO:</b></span>
                                </label>
                                <div class="col-md-2 col-form-label fw-bold fs-6">
                                    <select 
                                        id="orai"
                                        name="orai" aria-label="Scegli..." 
                                        data-control="select2" data-placeholder="Ora Inizio"
                                        class="form-select" required onchange="return populatedatafine(this);">
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
                                </div>
                                <label class="col-md-2 col-form-label fw-bold fs-6">
                                    <span class=" text-primary"><b>ORA FINE:</b></span>
                                </label>
                                <div class="col-md-2 col-form-label fw-bold fs-6">
                                    <select 
                                        id="oraf"
                                        name="oraf" aria-label="Scegli..." 
                                        data-control="select2" data-placeholder="Ora Fine" 
                                        class="form-select" required>
                                    </select>
                                </div>                                
                            </div>
                        </form>
                        <%}%>
                        <hr>
                        <label class="col-form-label fw-bold fs-6">PRESENZE INSERITE: <%=lpr.size()%> - ORE INSERITE (DA CONVALIDARE): <%=presenzeinserite%> - ORE CONVALIDATE: <%=presenzeconvalid%></label>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th class="p-2 w-10px">#</th>
                                    <th class="p-2 w-50px">Data</th>
                                    <th class="p-2 w-50px">Orario</th>
                                    <th class="p-2 w-50px">Ora</th>
                                    <th class="p-2 w-50px">Stato</th>
                                    <th class="p-2 w-50px">Azioni</th>
                                </tr>
                            </thead>
                            <tbody>

                                <%
                                    int i = 1;
                                    for (Presenze_Tirocinio_Allievi pta1 : lpr) {%>
                                <tr>
                                    <td class="p-2 w-10px"><%=i%></td>
                                    <td class="p-2 w-50px"><%=Constant.sdf_PATTERNDATE4.format(pta1.getDatapresenza())%></td>
                                    <td class="p-2 w-50px"><%=pta1.getOrainizio()%> - <%=pta1.getOrafine()%></td>                                    
                                    <td class="p-2 w-50px"><%=Utils.roundDoubleandFormat(pta1.getOre(), 1)%></td>
                                    <td class="p-2 w-50px"><%=pta1.getStatolezione().getHtmldescr()%></td>
                                    <td class="p-2 w-50px">
                                        <%if (modify && pta1.getStatolezione().getCodicestatocorso().equals("60")) {%>
                                        <button onclick="return convalidapresenzatirocinio('<%=pta1.getIdpresenzetirocinioallievi()%>')" 
                                                class="btn btn-sm btn-bg-light btn-success" data-bs-toggle="tooltip" 
                                                title="CONVALIDA PRESENZA TIROCINIO" data-preload='false'>
                                            <i class="fa fa-check"></i>
                                        </button>
                                        <button type="button"class="btn btn-sm btn-danger" data-bs-toggle="tooltip" 
                                                title="ELIMINA PRESENZA TIROCINIO" data-preload='false' 
                                                onclick="return rimuovipresenzatirocinio('<%=pta1.getIdpresenzetirocinioallievi()%>')">
                                            <i class="fa fa-trash-arrow-up"></i>
                                        </button>

                                        <%}%>
                                    </td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
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
        <script src="assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>
        <script src="assets/plugins/jquery-confirm.min3.3.2.js"></script>

        <script src="assets/js/US_deattaglitirocinioallievo.js"></script>

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