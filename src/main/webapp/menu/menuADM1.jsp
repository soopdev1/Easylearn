<%-- 
    Created on : 11 dic 2022, 11:28:09
    Author     : Raffaele
--%>

<%@page import="rc.soop.sic.Utils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    String pageName = Utils.getPagename(request);

    String m_1s = "";
    String m_1 = "";
    String m_1A = "";

    String m_2s = "";
    String m_2 = "";
    String m_2A = "";

    String m_3s = "";
    String m_3 = "";
    String m_3A = "";

    String m_4s = "";
    String m_4 = "";
    String m_4A = "";
    String m_4B = "";

    String m_5s = "";
    String m_5 = "";
    String m_5A = "";
    String m_5B = "";

    String m_6s = "";
    String m_6 = "";
    String m_6A = "";

    String m_7s = "";
    String m_7 = "";
    String m_7A = "";

    String m_8s = "";
    String m_8 = "";
    String m_8A = "";

    String m_10s = "";
    String m_10 = "";
    String m_10A = "";
    
    String m_9s = "";
    String m_9 = "";
    String m_9A = "";

    switch (pageName) {
        case "ADM_dashboard.jsp": {
            m_1s = "show";
            m_1 = "menu-active-bg";
            m_1A = "active";
            break;
        }
        case "ADM_istanze.jsp": {
            m_2s = "show";
            m_2 = "menu-active-bg";
            m_2A = "active";
            break;
        }
        case "ADM_gestionecorsi.jsp": {
            m_3s = "show";
            m_3 = "menu-active-bg";
            m_3A = "active";
            break;
        }
        case "US_gestionedocenti.jsp": {
            m_4s = "show";
            m_4 = "menu-active-bg";
            m_4A = "active";
            break;
        }
        case "US_gestionealtropersonale.jsp": {
            m_4s = "show";
            m_4 = "menu-active-bg";
            m_4B = "active";
            break;
        }
        case "ADM_gestioneallievi.jsp": {
            m_5s = "show";
            m_5 = "menu-active-bg";
            m_5B = "active";
            break;
        }
        case "ADM_gestionesedi.jsp": {
            m_6s = "show";
            m_6 = "menu-active-bg";
            m_6A = "active";
            break;
        }
        case "ADM_entistage.jsp": {
            m_8s = "show";
            m_8 = "menu-active-bg";
            m_8A = "active";
            break;
        }
        case "ADM_presidenti.jsp": {
            m_9s = "show";
            m_9 = "menu-active-bg";
            m_9A = "active";
            break;
        }
        case "ADM_backoffice.jsp": {
            m_10s = "show";
            m_10 = "menu-active-bg";
            m_10A = "active";
            break;
        }

        default: {
            Utils.redirect(request, response, "login.jsp");
            break;
        }
    }
%>

<!--begin::Aside-->
<div id="kt_aside" class="aside card" data-kt-drawer="true" data-kt-drawer-name="aside" data-kt-drawer-activate="{default: true, lg: false}" data-kt-drawer-overlay="true" data-kt-drawer-width="{default:'200px', '300px': '250px'}" data-kt-drawer-direction="start" data-kt-drawer-toggle="#kt_aside_toggle">
    <!--begin::Aside menu-->
    <div class="aside-menu flex-column-fluid px-5">
        <!--begin::Aside Menu-->
        <div class="hover-scroll-overlay-y my-5 pe-4 me-n4" id="kt_aside_menu_wrapper" data-kt-scroll="true" data-kt-scroll-activate="{default: false, lg: true}" data-kt-scroll-height="auto" data-kt-scroll-dependencies="#kt_header, #kt_aside_footer" data-kt-scroll-wrappers="#kt_aside, #kt_aside_menu" data-kt-scroll-offset="{lg: '75px'}">
            <!--begin::Menu-->
            <div class="menu menu-column menu-rounded fw-bold fs-6" id="#kt_aside_menu" data-kt-menu="true">
                <div class="row">
                    <img alt="Logo" src="assets/media/logos/logo_v2.jpg" class="h-50px" /> 
                    <hr>
                </div>
                <div data-kt-menu-trigger="click" class="menu-item here <%=m_1s%> menu-accordion">
                    <span class="menu-link">
                        <span class="menu-icon">
                            <!--begin::Svg Icon | path: icons/duotune/general/gen025.svg-->
                            <span class="svg-icon svg-icon-2">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                        <g id="Dribbble-Light-Preview" transform="translate(-419.000000, -720.000000)" fill="#000000">
                                            <g id="icons" transform="translate(56.000000, 160.000000)">
                                                <path d="M379.79996,578 L376.649968,578 L376.649968,574 L370.349983,574 L370.349983,578 L367.19999,578 L367.19999,568.813 L373.489475,562.823 L379.79996,568.832 L379.79996,578 Z M381.899955,568.004 L381.899955,568 L381.899955,568 L373.502075,560 L363,569.992 L364.481546,571.406 L365.099995,570.813 L365.099995,580 L372.449978,580 L372.449978,576 L374.549973,576 L374.549973,580 L381.899955,580 L381.899955,579.997 L381.899955,570.832 L382.514204,571.416 L384.001,570.002 L381.899955,568.004 Z" id="home-[#1391]">
                                                </path>
                                            </g>
                                        </g>
                                    </g>
                                </svg>
                            </span>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="menu-title">HOMEPAGE</span>
                    </span>
                    <div class="menu-sub menu-sub-accordion <%=m_1%>">
                        <div class="menu-item">
                            <a class="menu-link active <%=m_1A%>" href="ADM_dashboard.jsp">
                                <span class="menu-bullet">
                                    <span class="bullet bullet-dot"></span>
                                </span>
                                <span class="menu-title">Homepage</span>
                            </a>
                        </div>                        
                    </div>
                </div>
                <div data-kt-menu-trigger="click" class="menu-item here  <%=m_2s%> menu-accordion">
                    <span class="menu-link">
                        <span class="menu-icon">
                            <!--begin::Svg Icon | path: icons/duotune/general/gen025.svg-->
                            <span class="svg-icon svg-icon-2">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                                    <path d="M15 11L15 17" stroke="#323232" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M12 12L12 17" stroke="#323232" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M9 14L9 17" stroke="#323232" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M17.8284 6.82843C18.4065 7.40649 18.6955 7.69552 18.8478 8.06306C19 8.4306 19 8.83935 19 9.65685L19 17C19 18.8856 19 19.8284 18.4142 20.4142C17.8284 21 16.8856 21 15 21H9C7.11438 21 6.17157 21 5.58579 20.4142C5 19.8284 5 18.8856 5 17L5 7C5 5.11438 5 4.17157 5.58579 3.58579C6.17157 3 7.11438 3 9 3H12.3431C13.1606 3 13.5694 3 13.9369 3.15224C14.3045 3.30448 14.5935 3.59351 15.1716 4.17157L17.8284 6.82843Z" stroke="#323232" stroke-width="2" stroke-linejoin="round"/>
                                </svg>
                            </span>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="menu-title">ISTANZA</span>
                    </span>
                    <div class="menu-sub menu-sub-accordion <%=m_2%> ">
                        <div class="menu-item">
                            <a class="menu-link  <%=m_2A%> " href="ADM_istanze.jsp">
                                <span class="menu-bullet">
                                    <span class="bullet bullet-dot"></span>
                                </span>
                                <span class="menu-title">Gestione Istanze</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div data-kt-menu-trigger="click" class="menu-item here menu-accordion <%=m_3s%>">
                    <span class="menu-link">
                        <span class="menu-icon">
                            <!--begin::Svg Icon | path: icons/duotune/general/gen025.svg-->
                            <span class="svg-icon svg-icon-2">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                                    <path d="M10.05 2.53004L4.03002 6.46004C2.10002 7.72004 2.10002 10.54 4.03002 11.8L10.05 15.73C11.13 16.44 12.91 16.44 13.99 15.73L19.98 11.8C21.9 10.54 21.9 7.73004 19.98 6.47004L13.99 2.54004C12.91 1.82004 11.13 1.82004 10.05 2.53004Z" stroke="#292D32" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M5.63 13.08L5.62 17.77C5.62 19.04 6.6 20.4 7.8 20.8L10.99 21.86C11.54 22.04 12.45 22.04 13.01 21.86L16.2 20.8C17.4 20.4 18.38 19.04 18.38 17.77V13.13" stroke="#292D32" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M21.4 15V9" stroke="#292D32" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                            </span>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="menu-title">CORSI</span>
                    </span>
                    <div class="menu-sub menu-sub-accordion <%=m_3%>">                        
                        <div class="menu-item">
                            <a class="menu-link <%=m_3A%>" href="ADM_gestionecorsi.jsp">
                                <span class="menu-bullet">
                                    <span class="bullet bullet-dot"></span>
                                </span>
                                <span class="menu-title">Gestione Corsi</span>
                            </a>
                        </div>                          
                    </div>
                </div>
                <div data-kt-menu-trigger="click" class="menu-item here <%=m_4s%> menu-accordion">
                    <span class="menu-link">
                        <span class="menu-icon">
                            <!--begin::Svg Icon | path: icons/duotune/general/gen025.svg-->
                            <span class="svg-icon svg-icon-2">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" 
                                     viewBox="0 0 31.312 31.312"
                                     xml:space="preserve">
                                    <g>
                                        <g>
                                            <polygon points="2.931,0.463 2.931,3.416 4.221,3.018 4.221,1.753 30.023,1.753 30.023,18.201 9.491,18.201 9.424,19.49 
                                                     31.312,19.49 31.312,0.463 		"/>
                                            <g>
                                                <circle cx="4.984" cy="7.526" r="3.821"/>
                                                <polygon points="8.227,29.104 8.227,22.302 8.227,21.661 8.227,20.791 8.47,20.791 8.825,13.885 15.416,10.471 14.55,8.801 
                                                         8.524,11.922 6.374,11.922 4.917,13.611 3.508,11.922 0.412,12.483 0.214,19.365 1.526,19.365 1.597,20.791 1.797,20.791 
                                                         1.797,21.661 1.797,22.302 1.797,29.104 1.534,29.104 0,29.438 0,30.85 1.306,30.85 2.83,30.6 2.844,30.85 4.531,30.85 
                                                         4.531,29.275 4.531,29.104 4.531,22.302 5.492,22.302 5.492,29.104 5.492,29.275 5.492,30.85 7.181,30.85 7.195,30.6 
                                                         8.718,30.85 10.024,30.85 10.024,29.438 8.491,29.104 			"/>
                                            </g>
                                            <rect x="11.039" y="6.235" width="16.523" height="0.483"/>
                                            <rect x="11.039" y="4.542" width="16.523" height="0.484"/>
                                            <rect x="11.039" y="7.887" width="16.523" height="0.484"/>
                                            <polygon points="27.562,9.459 15.535,9.459 15.786,9.942 27.562,9.942 		"/>
                                            <polygon points="14.427,11.594 27.562,11.594 27.562,11.111 15.423,11.111 		"/>
                                            <polygon points="11.039,13.255 11.039,13.279 27.562,13.279 27.562,12.796 11.863,12.796 		"/>
                                            <rect x="23.719" y="15.145" width="5.051" height="1.878"/>
                                        </g>
                                    </g>
                                </svg>
                            </span>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="menu-title">PERSONALE</span>
                    </span>
                    <div class="menu-sub menu-sub-accordion <%=m_4%>">                        
                        <div class="menu-item">
                            <a class="menu-link <%=m_4A%>" href="US_gestionedocenti.jsp">
                                <span class="menu-bullet">
                                    <span class="bullet bullet-dot"></span>
                                </span>
                                <span class="menu-title">Gestione Docenti/Tutor</span>
                            </a>
                        </div>                          
                    </div>
                    <div class="menu-sub menu-sub-accordion <%=m_4%>">                        
                        <div class="menu-item">
                            <a class="menu-link <%=m_4B%>" href="US_gestionealtropersonale.jsp">
                                <span class="menu-bullet">
                                    <span class="bullet bullet-dot"></span>
                                </span>
                                <span class="menu-title">Gestione Altro Personale</span>
                            </a>
                        </div>                          
                    </div>
                </div>
                <div data-kt-menu-trigger="click" class="menu-item here <%=m_5s%> menu-accordion">
                    <span class="menu-link">
                        <span class="menu-icon">
                            <span class="svg-icon svg-icon-2">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                                    <g>
                                        <path d="M20,21V19a4,4,0,0,0-4-4H8a4,4,0,0,0-4,4v2" fill="none" stroke="#000000" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                                        <circle cx="12" cy="7" fill="none" r="4" stroke="#000000" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                                    </g>
                                </svg>
                            </span>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="menu-title">ALLIEVI</span>
                    </span>
                    <div class="menu-sub menu-sub-accordion <%=m_5%>">  
                        <div class="menu-item">
                            <a class="menu-link <%=m_5B%>" href="ADM_gestioneallievi.jsp">
                                <span class="menu-bullet">
                                    <span class="bullet bullet-dot"></span>
                                </span>
                                <span class="menu-title">Gestione Allievi</span>
                            </a>
                        </div>                          
                    </div>
                </div>
                <div data-kt-menu-trigger="click" class="menu-item here <%=m_6s%> menu-accordion">
                    <span class="menu-link">
                        <span class="menu-icon">
                            <!--begin::Svg Icon | path: icons/duotune/general/gen025.svg-->
                            <span class="svg-icon svg-icon-2 info">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="#000000" viewBox="0 0 512 512">
                                    <path d="M496 128v16a8 8 0 0 1-8 8h-24v12c0 6.627-5.373 12-12 12H60c-6.627 0-12-5.373-12-12v-12H24a8 8 0 0 1-8-8v-16a8 8 0 0 1 4.941-7.392l232-88a7.996 7.996 0 0 1 6.118 0l232 88A8 8 0 0 1 496 128zm-24 304H40c-13.255 0-24 10.745-24 24v16a8 8 0 0 0 8 8h464a8 8 0 0 0 8-8v-16c0-13.255-10.745-24-24-24zM96 192v192H60c-6.627 0-12 5.373-12 12v20h416v-20c0-6.627-5.373-12-12-12h-36V192h-64v192h-64V192h-64v192h-64V192H96z"/>
                                </svg>
                            </span>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="menu-title">SEDI</span>
                    </span>
                    <div class="menu-sub menu-sub-accordion <%=m_6%>">                        
                        <div class="menu-item">
                            <a class="menu-link <%=m_6A%>" href="ADM_gestionesedi.jsp">
                                <span class="menu-bullet">
                                    <span class="bullet bullet-dot"></span>
                                </span>
                                <span class="menu-title">Gestione Sedi Formative</span>
                            </a>
                        </div>                          
                    </div>
                </div>

                <div data-kt-menu-trigger="click" class="menu-item here <%=m_8s%> menu-accordion">
                    <span class="menu-link">
                        <span class="menu-icon">
                            <!--begin::Svg Icon | path: icons/duotune/general/gen025.svg-->
                            <span class="svg-icon svg-icon-2 info">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" 
                                     fill="#000000"viewBox="0 0 100 100" enable-background="new 0 0 100 100" xml:space="preserve">
                                    <path d="M37.3,31.9h21.8c1.1,0,2-0.9,2-2v-4c0-3.3-2.7-5.9-5.9-5.9H41.3c-3.3,0-5.9,2.7-5.9,5.9v4
                                          C35.3,31,36.2,31.9,37.3,31.9z"/>
                                    <path d="M70,24.9h-2c-0.6,0-1,0.4-1,1v4c0,4.4-3.6,7.9-7.9,7.9H37.3c-4.4,0-7.9-3.6-7.9-7.9v-4c0-0.6-0.4-1-1-1h-2
                                          c-3.3,0-5.9,2.7-5.9,5.9v40.6c0,3.3,2.7,5.9,5.9,5.9h20c2.8,0,3.1-2.3,3.1-3.1V52.8c0-2.3,1.3-2.8,2-2.8h21.6c2.4,0,2.8-2.1,2.8-2.8
                                          V31C76,27.6,73.3,24.9,70,24.9z"/>
                                    <path d="M78.4,60.4H56.6c-0.6,0-1.1-0.5-1.1-1.1v-2.2c0-0.6,0.5-1.1,1.1-1.1h21.8c0.6,0,1.1,0.5,1.1,1.1v2.2
                                          C79.5,59.9,79,60.4,78.4,60.4z M78.4,70.2H56.6c-0.6,0-1.1-0.5-1.1-1.1v-2.2c0-0.6,0.5-1.1,1.1-1.1h21.8c0.6,0,1.1,0.5,1.1,1.1v2.2
                                          C79.5,69.7,79,70.2,78.4,70.2z M78.4,80H56.6c-0.6,0-1.1-0.5-1.1-1.1v-2.2c0-0.6,0.5-1.1,1.1-1.1h21.8c0.6,0,1.1,0.5,1.1,1.1v2.2
                                          C79.5,79.5,79,80,78.4,80z"/>
                                </svg>
                            </span>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="menu-title">ENTI OSPITANTI STAGE</span>
                    </span>
                    <div class="menu-sub menu-sub-accordion <%=m_8%>">                        
                        <div class="menu-item">
                            <a class="menu-link <%=m_8A%>" href="ADM_entistage.jsp">
                                <span class="menu-bullet">
                                    <span class="bullet bullet-dot"></span>
                                </span>
                                <span class="menu-title">Gestione Enti</span>
                            </a>
                        </div>                          
                    </div>
                </div>
                <div data-kt-menu-trigger="click" class="menu-item here <%=m_9s%> menu-accordion">
                    <span class="menu-link">
                        <span class="menu-icon">
                            <!--begin::Svg Icon | path: icons/duotune/general/gen025.svg-->
                            <span class="svg-icon svg-icon-2 info">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-bounding-box" viewBox="0 0 16 16">
                                    <path d="M1.5 1a.5.5 0 0 0-.5.5v3a.5.5 0 0 1-1 0v-3A1.5 1.5 0 0 1 1.5 0h3a.5.5 0 0 1 0 1h-3zM11 .5a.5.5 0 0 1 .5-.5h3A1.5 1.5 0 0 1 16 1.5v3a.5.5 0 0 1-1 0v-3a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 1-.5-.5zM.5 11a.5.5 0 0 1 .5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 1 0 1h-3A1.5 1.5 0 0 1 0 14.5v-3a.5.5 0 0 1 .5-.5zm15 0a.5.5 0 0 1 .5.5v3a1.5 1.5 0 0 1-1.5 1.5h-3a.5.5 0 0 1 0-1h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 1 .5-.5z"/>
                                    <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm8-9a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                </svg>
                            </span>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="menu-title">PRESIDENTI COMM.</span>
                    </span>
                    <div class="menu-sub menu-sub-accordion <%=m_9%>">                        
                        <div class="menu-item">
                            <a class="menu-link <%=m_9A%>" href="ADM_presidenti.jsp">
                                <span class="menu-bullet">
                                    <span class="bullet bullet-dot"></span>
                                </span>
                                <span class="menu-title">Gestione Presidenti</span>
                            </a>
                        </div>                          
                    </div>
                </div>
                <hr>
                    <div data-kt-menu-trigger="click" class="menu-item here menu-accordion <%=m_10s%>">
                        <span class="menu-link">
                            <span class="menu-icon">
                                <!--begin::Svg Icon | path: icons/duotune/general/gen025.svg-->
                                <span class="svg-icon svg-icon-2">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 32 32">
                                        <path d="M1.5 1a.5.5 0 0 0-.5.5v3a.5.5 0 0 1-1 0v-3A1.5 1.5 0 0 1 1.5 0h3a.5.5 0 0 1 0 1h-3zM11 .5a.5.5 0 0 1 .5-.5h3A1.5 1.5 0 0 1 16 1.5v3a.5.5 0 0 1-1 0v-3a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 1-.5-.5zM.5 11a.5.5 0 0 1 .5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 1 0 1h-3A1.5 1.5 0 0 1 0 14.5v-3a.5.5 0 0 1 .5-.5zm15 0a.5.5 0 0 1 .5.5v3a1.5 1.5 0 0 1-1.5 1.5h-3a.5.5 0 0 1 0-1h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 1 .5-.5z"/>
                                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm8-9a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                    </svg>
                                </span>
                                <!--end::Svg Icon-->
                            </span>
                            <span class="menu-title">BACK OFFICE</span>
                        </span>
                        <div class="menu-sub menu-sub-accordion <%=m_10%>">                        
                            <div class="menu-item">
                                <a class="menu-link <%=m_10A%>" href="ADM_backoffice.jsp">
                                    <span class="menu-bullet">
                                        <span class="bullet bullet-dot"></span>
                                    </span>
                                    <span class="menu-title">Configurazione Dati</span>
                                </a>
                            </div>                          
                        </div>
                    </div>
            </div>
        </div>
    </div>
    <!--end::Aside menu-->
</div>
<!--end::Aside-->