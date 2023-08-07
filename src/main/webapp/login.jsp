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
        <script src="assets/spid/js/spid-idps.js"></script>

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
                        <img alt="Logo" src="assets/media/logos/logo.png" class="h-40px" /> 
                    </div>
                    <!--end::Logo-->
                    <!--begin::Wrapper-->
                    <div class="w-lg-500px bg-body rounded shadow-sm p-10 p-lg-15 mx-auto">
                        <!--begin::Form-->
                        <form class="form w-100" novalidate="novalidate" id="kt_sign_in_form" action="#">
                            <!--begin::Heading-->
                            <div class="text-center mb-10">
                                <!--begin::Title-->
                                <h1 class="text-dark mb-3">Log In</h1>
                                <!--end::Title-->
                            </div>

                            <form name="spid_idp_access" action="#" method="post">
                                <input type="hidden" name="param_001" value="" />
                                <input type="hidden" name="param_002" value="" />
                                <input type="hidden" name="param_003" value="" />
                                <a href="#" class="btn btn-lg w-100 mb-5 italia-it-button italia-it-button-size-m button-spid" spid-idp-button="#spid-idp-button-medium-post" 
                                   aria-haspopup="true" aria-expanded="false">
                                    <span class="italia-it-button-icon"><img src="assets/spid/img/spid-ico-circle-bb.svg" 
                                                                             onerror="this.src='assets/spid/img/spid-ico-circle-bb.png'; this.onerror=null;" alt="" /></span>
                                    <span class="italia-it-button-text">Entra con SPID</span>
                                </a>
                                <div id="spid-idp-button-medium-post" class="spid-idp-button spid-idp-button-tip spid-idp-button-relative">
                                    <ul id="spid-idp-list-medium-root-post" class="spid-idp-button-menu"  data-spid-remote aria-labelledby="spid-idp">
                                    </ul>
                                </div>
                            </form>

                            <%if (false) {%>
                            <!--begin::Heading-->
                            <!--begin::Input group-->
                            <div class="fv-row mb-10">
                                <!--begin::Label-->
                                <label class="form-label fs-6 fw-bolder text-dark">Username</label>
                                <!--end::Label-->
                                <!--begin::Input-->
                                <input class="form-control form-control-lg form-control-solid" type="text" name="username" autocomplete="off" />
                                <!--end::Input-->
                            </div>
                            <!--end::Input group-->
                            <!--begin::Input group-->
                            <div class="fv-row mb-10">
                                <!--begin::Wrapper-->
                                <div class="d-flex flex-stack mb-2">
                                    <!--begin::Label-->
                                    <label class="form-label fw-bolder text-dark fs-6 mb-0">Password</label>
                                    <!--end::Label-->
                                </div>
                                <!--end::Wrapper-->
                                <!--begin::Input-->
                                <input class="form-control form-control-lg form-control-solid" type="password" name="password" autocomplete="off" />
                                <!--end::Input-->
                            </div>
                            <!--end::Input group-->
                            <!--begin::Actions-->
                            <div class="text-center">
                                <!--begin::Submit button-->
                                <button type="submit" id="kt_sign_in_submit" class="btn btn-lg btn-primary w-100 mb-5">
                                    <span class="indicator-label"><i class="fa fa-key"></i> ACCEDI</span>
                                    <span class="indicator-progress">Attendi...
                                        <span class="spinner-border spinner-border-sm align-middle ms-2"></span></span>
                                </button>
                                <!--end::Submit button-->
                            </div>
                            <!--end::Actions-->
                            <%}%>

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
