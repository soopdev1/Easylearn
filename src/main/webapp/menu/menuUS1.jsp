<%-- 
    Created on : 11 dic 2022, 11:28:09
    Author     : Raffaele
--%>

<%@page import="rc.soop.sic.Utils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--begin::Aside-->

<%
    String pageName = Utils.getPagename(request);

    String m_1s = "";
    String m_1 = "";
    String m_1A = "";

    String m_2s = "";
    String m_2 = "";
    String m_2A = "";
    String m_2B = "";

    String m_3s = "";
    String m_3 = "";
    String m_3A = "";
    String m_3B = "";

    String m_4s = "";
    String m_4 = "";
    String m_4A = "";
    
    String m_5s = "";
    String m_5 = "";
    String m_5A = "";
    String m_5B = "";
    
    String m_6s = "";
    String m_6 = "";
    String m_6A = "";
    String m_6B = "";

    switch (pageName) {
        case "US_dashboard.jsp": {
            m_1s = "show";
            m_1 = "menu-active-bg";
            m_1A = "active";
            break;
        }
        case "US_nuovaistanza.jsp":
        case "US_compilacorsi.jsp": {
            m_2s = "show";
            m_2 = "menu-active-bg";
            m_2A = "active";
            break;
        }
        case "US_gestioneistanza.jsp": {
            m_2s = "show";
            m_2 = "menu-active-bg";
            m_2B = "active";
            break;
        }
        case "US_nuovocorso.jsp": {
            m_3s = "show";
            m_3 = "menu-active-bg";
            m_3A = "active";
            break;
        }
        case "US_gestionecorsi.jsp": {
            m_3s = "show";
            m_3 = "menu-active-bg";
            m_3B = "active";
            break;
        }
        case "US_gestionedocenti.jsp": {
            m_4s = "show";
            m_4= "menu-active-bg";
            m_4A = "active";
            break;
        }
        case "US_gestioneallievi.jsp": {
            m_5s = "show";
            m_5= "menu-active-bg";
            m_5B = "active";
            break;
        }
        case "US_gestionesedi.jsp": {
            m_6s = "show";
            m_6= "menu-active-bg";
            m_6A = "active";
            break;
        }
        default: {
            Utils.redirect(request, response, "login.jsp");
            break;
        }
    }
%>



<div id="kt_aside" class="aside card" data-kt-drawer="true" data-kt-drawer-name="aside" 
     data-kt-drawer-activate="{default: true, lg: false}" data-kt-drawer-overlay="true" 
     data-kt-drawer-width="{default:'200px', '300px': '250px'}" data-kt-drawer-direction="start" data-kt-drawer-toggle="#kt_aside_toggle">
    <!--begin::Aside menu-->
    <div class="aside-menu flex-column-fluid px-5">
        <!--begin::Aside Menu-->
        <div class="hover-scroll-overlay-y my-5 pe-4 me-n4" id="kt_aside_menu_wrapper" data-kt-scroll="true" data-kt-scroll-activate="{default: false, lg: true}" data-kt-scroll-height="auto" data-kt-scroll-dependencies="#kt_header, #kt_aside_footer" data-kt-scroll-wrappers="#kt_aside, #kt_aside_menu" data-kt-scroll-offset="{lg: '75px'}">
            <!--begin::Menu-->
            <div class="menu menu-column menu-rounded fw-bold fs-6" id="#kt_aside_menu" data-kt-menu="true">
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
                                                </svg>
                                                </span>
                                                <!--end::Svg Icon-->
                                                </span>
                                                <span class="menu-title">DASHBOARD</span>
                                                </span>
                                                <div class="menu-sub menu-sub-accordion <%=m_1%>">
                                                    <div class="menu-item">
                                                        <a class="menu-link <%=m_1A%>" href="US_dashboard.jsp">
                                                            <span class="menu-bullet">
                                                                <span class="bullet bullet-dot"></span>
                                                            </span>
                                                            <span class="menu-title">Homepage</span>
                                                        </a>
                                                    </div>                        
                                                </div>
                                                </div>
                                                <div data-kt-menu-trigger="click" class="menu-item here <%=m_2s%> menu-accordion">
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
                                                    <div class="menu-sub menu-sub-accordion <%=m_2%>">
                                                        <div class="menu-item">
                                                            <a class="menu-link <%=m_2A%>" href="US_nuovaistanza.jsp">
                                                                <span class="menu-bullet">
                                                                    <span class="bullet bullet-dot"></span>
                                                                </span>
                                                                <span class="menu-title">Nuova Istanza</span>
                                                            </a>
                                                        </div>
                                                        <div class="menu-item">
                                                            <a class="menu-link <%=m_2B%>" href="US_gestioneistanza.jsp">
                                                                <span class="menu-bullet">
                                                                    <span class="bullet bullet-dot"></span>
                                                                </span>
                                                                <span class="menu-title">Gestione Istanze</span>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div data-kt-menu-trigger="click" class="menu-item here <%=m_3s%> menu-accordion">
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
                                                            <a class="menu-link <%=m_3A%>" href="US_nuovocorso.jsp">
                                                                <span class="menu-bullet">
                                                                    <span class="bullet bullet-dot"></span>
                                                                </span>
                                                                <span class="menu-title">Aggiungi Nuovo Corso</span>
                                                            </a>
                                                        </div>                          
                                                        <div class="menu-item">
                                                            <a class="menu-link <%=m_3B%>" href="US_gestionecorsi.jsp">
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
                                                        <span class="menu-title">DOCENTI</span>
                                                    </span>
                                                    <div class="menu-sub menu-sub-accordion <%=m_4%>">                        
                                                        <div class="menu-item">
                                                            <a class="menu-link <%=m_4A%>" href="US_gestionedocenti.jsp">
                                                                <span class="menu-bullet">
                                                                    <span class="bullet bullet-dot"></span>
                                                                </span>
                                                                <span class="menu-title">Gestione Docenti</span>
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
                                                        <!--
                                                        <div class="menu-item">
                                                            <a class="menu-link <%=m_5A%>" href="US_nuovocorso.jsp">
                                                                <span class="menu-bullet">
                                                                    <span class="bullet bullet-dot"></span>
                                                                </span>
                                                                <span class="menu-title">Aggiungi Nuovo Allievo</span>
                                                            </a>
                                                        </div>
                                                        -->
                                                        <div class="menu-item">
                                                            <a class="menu-link <%=m_5B%>" href="US_gestioneallievi.jsp">
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
                                                            <a class="menu-link <%=m_6A%>" href="US_gestionesedi.jsp">
                                                                <span class="menu-bullet">
                                                                    <span class="bullet bullet-dot"></span>
                                                                </span>
                                                                <span class="menu-title">Gestione Sedi Formative</span>
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