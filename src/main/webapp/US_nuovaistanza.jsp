<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

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
        <title><%=Constant.NAMEAPP%> - Dashboard</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
        <!--end::Fonts-->
        <!--begin::Page Vendor Stylesheets(used by this page)-->
        <link href="assets/plugins/custom/fullcalendar/fullcalendar.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/plugins/custom/datatables/datatables.bundle.css" rel="stylesheet" type="text/css" />
        <!--end::Page Vendor Stylesheets-->
        <!--begin::Global Stylesheets Bundle(used by all pages)-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/fontawesome-6.0.0/css/all.css" rel="stylesheet" type="text/css" />
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
                        <jsp:include page="menu/menuUS2.jsp" /> 
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
                                    <div class="col-xl-12">
                                        <!--begin::Tables Widget 3-->
                                        <h1 class="text-center fs-4">Creazione nuova Istanza</h1>
                                        <form id="signUpForm" action="#!">
                                            <!-- start step indicators -->
                                            <div class="form-header d-flex mb-4">
                                                <span class="stepIndicator">Tipologia Percorso</span>
                                                <span class="stepIndicator">Seleziona Percorso</span>
                                                <span class="stepIndicator">Dettagli</span>
                                            </div>
                                            <!-- end step indicators -->

                                            <!-- step one -->
                                            <div class="step">
                                                <p class="text-center mb-4">Istanza di autorizzazione allo svolgimento di corsi di formazione professionale</p>
                                                <div class="mb-3">
                                                    <select aria-label="Scegli..." data-control="select2" 
                                                            data-placeholder="Scegli Tipologia percorso" 
                                                            class="form-select form-select-solid form-select-lg fw-bold" 
                                                            name="scelta"
                                                            id="scelta"
                                                            required>
                                                        <option value="">Scegli...</option>  
                                                        <option value="A">Percorsi Auto-Finanziati</option>  
                                                        <option value="B">Percorsi Finanziati Avviso 23/A</option>  
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- step two -->
                                            <div class="step">
                                                <p class="text-center mb-4">Your presence on the social network</p>
                                                <div class="mb-3">
                                                    <input type="text" placeholder="Linked In" oninput="this.className = ''" name="linkedin">
                                                </div>
                                                <div class="mb-3">
                                                    <input type="text" placeholder="Twitter" oninput="this.className = ''" name="twitter">
                                                </div>
                                                <div class="mb-3">
                                                    <input type="text" placeholder="Facebook" oninput="this.className = ''" name="facebook">
                                                </div>
                                            </div>

                                            <!-- step three -->
                                            <div class="step">
                                                <p class="text-center mb-4">We will never sell it</p>
                                                <div class="mb-3">
                                                    <input type="text" placeholder="Full name" oninput="this.className = ''" name="fullname">
                                                </div>
                                                <div class="mb-3">
                                                    <input type="text" placeholder="Mobile" oninput="this.className = ''" name="mobile">
                                                </div>
                                                <div class="mb-3">
                                                    <input type="text" placeholder="Address" oninput="this.className = ''" name="address">
                                                </div>
                                            </div>

                                            <div class="mb-3">
                                                <div aria-live="polite" id="errorMsgContainer"></div>
                                            </div>

                                            <!-- start previous / next buttons -->
                                            <div class="form-footer d-flex">
                                                <button type="button" id="prevBtn" onclick="nextPrev(-1)"

                                                        class="btn btn-circled btn-hover-rise"
                                                        ><i class="fa fa-backward-step"></i> INDIETRO</button>
                                                <button type="button" id="nextBtn" 
                                                        onclick="nextPrev(1)" 
                                                        class="btn btn-circled btn-hover-rise"
                                                        ><i class="fa fa-forward-step"></i> AVANTI</button>
                                            </div>
                                            <!-- end previous / next buttons -->
                                        </form>
                                        <!--end::Tables Widget 3-->
                                    </div>
                                    <!--end::Col-->
                                    <!--begin::Col-->
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
                </div>
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
        <script src="assets/plugins/custom/datatables/datatables.bundle.js"></script>
        <!--end::Page Vendors Javascript-->
        <!--begin::Page Custom Javascript(used by this page)-->
        <script src="assets/js/widgets.bundle.js"></script>
        <script src="assets/js/custom/widgets.js"></script>
        <script src="assets/js/custom/apps/chat/chat.js"></script>
        <script src="assets/js/custom/utilities/modals/create-app.js"></script>
        <script src="assets/js/custom/utilities/modals/create-campaign.js"></script>
        <script src="assets/js/custom/utilities/modals/users-search.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>

        <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
        <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
        <script type="text/javascript" src="assets/js/wiz_1.js"></script>


        <script type="text/javascript">
            $(document).ready(Fancybox.bind(".fan1", {
                groupAll: false, // Group all items
                on: {
                    closing: (fancybox) => {
                        window.location.reload();
                    }
                }
            }));
        </script>
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