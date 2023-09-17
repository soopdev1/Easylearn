<%@page import="rc.soop.sic.Constant"%>
<!DOCTYPE html>
<html lang="en">
    <!--begin::Head-->
    <head><base href="">
        <title><%=Constant.NAMEAPP%>: Login</title>
        <meta charset="utf-8" />
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
        <!--begin::Fonts-->
        <link rel="stylesheet" href="assets/css/gfont.css" />
        <!--end::Fonts-->
        <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
        <!--end::Global Stylesheets Bundle-->

        <link type="text/css" rel="stylesheet" href="assets/spid/css/spid-sp-access-button.min.css" />

    </head>
    <!--end::Head-->
    <!--begin::Body-->
    <body id="kt_body" class="bg-body">
        <!--begin::Main-->
        <!--begin::Root-->
        <div class="d-flex flex-column flex-root">
            <!--begin::Authentication - Sign-in -->
            <div class="d-flex flex-column flex-column-fluid bgi-position-y-bottom position-x-center bgi-no-repeat bgi-size-contain bgi-attachment-fixed" 
                 style="background-image: url(assets/media/illustrations/sketchy-1/4.png">
                <!--begin::Content-->
                <div class="d-flex flex-center flex-column flex-column-fluid p-10 pb-lg-20">
                    <!--begin::Logo-->
                    <div class="mb-12">
                        <img alt="Logo" src="assets/media/logos/logo_v1.jpg" class="h-100px" /> 
                    </div>
                    <!--end::Logo-->
                    <!--begin::Wrapper-->
                    <div class="w-lg-500px bg-body rounded shadow-sm p-10 p-lg-15 mx-auto">
                        <!--begin::Form-->
                            <!--begin::Heading-->
                            <div class="text-center mb-10">
                                <!--begin::Title-->
                                <h1 class="text-dark mb-3">Log In</h1>
                                <!--end::Title-->
                            </div>

                            <form action="LoginOperations" method="post">
                                <input type="hidden" name="type" value="spid" />
                                <button type="submit" class="btn btn-lg w-100 mb-5 italia-it-button italia-it-button-size-m button-spid">
                                    <span class="italia-it-button-icon">
                                        <img src="assets/spid/img/spid-ico-circle-bb.svg"  onerror="this.src='assets/spid/img/spid-ico-circle-bb.png'; this.onerror=null;" alt="" />
                                    </span>
                                    <span class="italia-it-button-text">Entra con SPID</span>
                                </button>
                            </form>
                            <div class="fv-row mb-10">
                                <hr><!-- comment -->
                            </div>
                            <div class="fv-row mb-10">
                                <!--begin::Wrapper-->
                                <div class="d-flex flex-stack">
                                    <a href="login_adpre.jsp" class="btn btn-lg btn-dark w-100 mb-5">
                                        <span class="indicator-label"><i class="fa fa-forward"></i> Accesso tramite credenziali</span>
                                    </a>
                                </div>
                            </div>
                            <!--end::Form-->
                    </div>
                    <%if (Constant.ISDEMO) {%>
                    <div class="mb-12">
                        <img alt="Logo" src="assets/media/logos/demo.png" class="h-40px" /> 
                    </div>
                    <%}%>
                    <!--end::Wrapper-->
                </div>
                <!--end::Content-->
                <!--begin::Footer-->
                <div class="d-flex flex-center flex-column-auto p-10">
                    <!--begin::Links-->
                    <div class="d-flex align-items-center fw-bold fs-6">
                        <span class="text-muted text-hover-primary px-2"><%=Constant.VERSIONAPP%></span>
                    </div>
                    <!--end::Links-->
                </div>
                <!--end::Footer-->
            </div>
            <!--end::Authentication - Sign-in-->
        </div>
        <!--end::Root-->
        <!--end::Main-->
        <!--begin::Javascript-->
        <script>var hostUrl = "assets/";</script>
        <!--begin::Global Javascript Bundle(used by all pages)-->
        <script src="assets/plugins/global/plugins.bundle.js"></script>
        <script src="assets/js/scripts.bundle.js"></script>
        <!--end::Global Javascript Bundle-->
        <!--begin::Page Custom Javascript(used by this page)-->
        <!--end::Page Custom Javascript-->
        <script type="text/javascript" src="assets/spid/js/jquery.min.js"></script>
        <script type="text/javascript" src="assets/spid/js/spid-sp-access-button.min.js"></script>

        <!--end::Javascript-->
    </body>
    <!--end::Body-->
</html>
