<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.Professioni"%>
<%@page import="rc.soop.sic.jpa.SoggettoProponente"%>
<%@page import="rc.soop.sic.jpa.TipoCorso"%>
<%@page import="rc.soop.sic.jpa.Attrezzature"%>
<%@page import="rc.soop.sic.jpa.Docente"%>
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
                String idist = Utils.getRequestValue(request, "idist");
                if (idist.equals("")) {
                    idist = (String) session.getAttribute("ses_idist");
                }
                EntityOp eo = new EntityOp();
                Long idc1 = Long.valueOf(Utils.dec_string(idist));
                Istanza is1 = eo.getEm().find(Istanza.class, idc1);
                List<Corso> list = new EntityOp().getCorsiIstanza(is1);
                SoggettoProponente s0 = is1.getSoggetto();

                int numerocorsi = list.size();
                boolean autofinanz = list.get(0).getTipologiapercorso().getTipocorso().equals(TipoCorso.AUTOFINANZIATO);

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
            <div class="col-xl-12">
                <!--begin::Tables Widget 3-->
                <div class="card h-xl-100">
                    <!--begin::Header-->
                    <div class="card-header border-0 pt-5">
                        <h3 class="card-title align-items-start flex-column">
                            <span class="card-label fw-bolder fs-3 mb-1">
                                Istanza di autorizzazione allo svolgimento di corsi di formazione professionale - INVIATA IN DATA: <%=is1.getDatainvio()%>
                            </span>
                        </h3>
                    </div>
                    <div class="card-body py-3">

                        <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="pills-ist1-tab" data-bs-toggle="pill" data-bs-target="#pills-ist1"
                                        type="button" role="tab" aria-controls="pills-ist1" aria-selected="true">ISTANZA</button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="pills-profile-tab" data-bs-toggle="pill" data-bs-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile" aria-selected="false">Profile</button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="pills-contact-tab" data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact" aria-selected="false">Contact</button>
                            </li>
                        </ul>
                        <div class="tab-content" id="pills-tabContent">
                            <div class="tab-pane fade show active" id="pills-ist1" role="tabpanel" aria-labelledby="pills-ist1-tab">
                                <%if (autofinanz) {%>
                                <h2 class="title">OGGETTO: Istanza di autorizzazione allo svolgimento di corsi di formazione professionale AUTOFINANZIATI</h2>
                                <%} else {%>
                                <h2 class="title">OGGETTO: Istanza di autorizzazione allo svolgimento di corsi di formazione professionale FINANZIATI</h2>
                                <%}%>
                                <span class="text-justify">
                                    Il/La sottoscritto/a: <strong class="text-uppercase"><%=s0.getRap_cognome()%> <%=s0.getRap_nome()%></strong>
                                    nato/a: <strong class="text-uppercase"><%=s0.getRap_luogonascita()%></strong>
                                    il: <strong class="text-uppercase"><%=s0.getRap_datanascita()%></strong>
                                    Codice Fiscale: <strong class="text-uppercase"><%=s0.getRap_cf()%></strong>
                                    residente in: <strong class="text-uppercase"><%=s0.getRap_sede().getComune()%> (<%=s0.getRap_sede().getProvincia()%>)</strong> 
                                    indirizzo: <strong class="text-uppercase"><%=s0.getRap_sede().getIndirizzo()%> - <%=s0.getRap_sede().getCap()%></strong> 
                                    nella qualità di: <strong class="text-uppercase"><%=s0.getRap_carica()%></strong>
                                    e legale rappresentante dell'organismo denominato: <strong class="text-uppercase"><%=s0.getRAGIONESOCIALE()%></strong>
                                    costituito il: <strong class="text-uppercase"><%=s0.getDATADICOSTITUZIONE()%></strong>
                                    Codice Fiscale: <strong class="text-uppercase"><%=s0.getCODICEFISCALE()%></strong>
                                    Partita IVA: <strong class="text-uppercase"><%=s0.getPARTITAIVA()%></strong>
                                    avente natura giuridica di: <strong class="text-uppercase"><%=s0.getTIPOLOGIA()%></strong>
                                    con sede legale in: <strong class="text-uppercase"><%=s0.getSedelegale().getComune()%> (<%=s0.getSedelegale().getProvincia()%>)</strong> 
                                    indirizzo: <strong class="text-uppercase"><%=s0.getSedelegale().getIndirizzo()%> - <%=s0.getSedelegale().getCap()%></strong> 
                                    numero di telefono: <strong class="text-uppercase"><%=s0.getTELEFONO()%></strong>
                                    Posta Elettronica Certificata: <strong class="text-uppercase"><%=s0.getPEC()%></strong>
                                    CIR: <strong class="text-uppercase"><%=s0.getCIR()%></strong>
                                </span>
                                <br/>
                                <br/>
                                <span class="text-justify">
                                    consapevole del fatto che, in caso di dichiarazioni mendaci, saranno applicate nei suoi riguardi, ai sensi degli artt. 47 e 76 del DPR n. 445/2000 e s.m.i., le sanzioni previste dal codice penale e dalle leggi speciali in materia di falsità negli atti,
                                </span>
                                <hr>
                                <h3 class="text-center">
                                    DICHIARA
                                </h3>
                                <br/>
                                <ul>
                                    <li>di disporre in Sicilia di una sede direzionale e organizzativa permanente;</li>
                                    <li>di essere cittadino italiano;</li>
                                    <li>che il legale rappresentante (e i componenti degli eventuali organi collegiali) non hanno riportato sentenze penali di condanna passate in giudicato per i reati di cui all'art.11 del D.lgs. 24/07/1992 n°358;</li>
                                    <li>che il legale rappresentante (e i componenti degli eventuali organi collegiali) non sono stati dichiarati  falliti e/o non hanno in corso una procedura fallimentare,  non sono sottoposti a misure di prevenzione e che non sussistono cause di divieto, decadenza o sospensione di cui alla Legge 31/05/1965, n.575 e successive modificazioni (informazioni antimafia);</li>
                                    <li>che le sedi formative di seguito riportate sono state accreditate allo svolgimento di attività formativa nella Regione Siciliana con Decreto del Dirigente Generale del Dipartimento Formazione Professionale;</li>
                                </ul>
                                <hr>
                                <h3 class="text-center">
                                    CHIEDE
                                </h3>
                                <br/>
                                <h4 class="text-center">
                                    <strong>
                                        <%if (autofinanz) {%>
                                        l'autorizzazione a svolgere i seguenti corsi di formazione professionale autofinanziati:
                                        <%} else {%>
                                        l'autorizzazione a svolgere i seguenti corsi di formazione professionale finanziati:
                                        <%}%>
                                    </strong>
                                </h4>
                                <br/>

                                <%
                                    for (int indice = 0; indice < numerocorsi; indice++) {

                                        Corso c1 = list.get(indice);
                                %>
                                <hr>
                                <span class="text-center">
                                    <h4><u>CORSO <%=Engine.letterecorsi(indice)%></u></h4>
                                    <br>
                                    di: <strong><%=c1.getSchedaattivita().getCertificazioneuscita().getNome()%> </strong>
                                    <%if (c1.getRepertorio().getProfessioni().isEmpty()) {%>
                                    <%} else {%>
                                    CODICE ISTAT: 
                                    <%    for (Professioni pr1 : c1.getRepertorio().getProfessioni()) {%>
                                    <strong><%=pr1.getCodiceProfessioni()%> - <%=pr1.getNome()%> </strong>, 
                                    <%}%>
                                    <%}%>
                                    per: <strong><u><%=c1.getSchedaattivita().getTitoloattestato()%></u></strong>
                                    - Numero edizioni: <strong><%=c1.getQuantitarichiesta()%></strong> 
                                    con certificazione in uscita di: <strong><%=c1.getSchedaattivita().getCertificazioneuscita().getNome()%> </strong> 
                                    livello: <strong><%=c1.getRepertorio().getLivelloeqf().getNome()%></strong> 
                                    della durata di ore: <strong><%=(c1.getDurataore() + c1.getStageore())%></strong> 
                                    di cui stage: <strong><%=c1.getStageore()%></strong> 
                                    e giorni complessivi:  <strong><%=c1.getDuratagiorni()%></strong> 
                                    destinato a:  <strong><%=c1.getNumeroallievi()%></strong> allievi <br/>
                                    in possesso dei seguenti requisiti d'ingresso: <%=c1.getSchedaattivita().getPrerequisiti()%>
                                </span>
                                <hr>
                                <%}%>
                                <h3 class="text-center">
                                    SI IMPEGNA
                                </h3>
                                <br/>
                                <ul>
                                    <li>ad applicare al personale dipendente utilizzato nelle attività formative il C.C.N.L. della formazione professionale;</li>
                                    <li>a selezionare gli allievi in base al titolo di studio secondo quanto previsto dai profili formativi inseriti nel Repertorio delle qualificazioni della Regione Siciliana;</li>
                                    <li>a selezionare, per ciascun corso, non più di n. 20 allievi, ovvero un numero inferiore proporzionato alle capacità logistiche e funzionali della sede, conformemente agli standard della Regione Siciliana, approvate con D.P.R.  25/2015 art. 4 comma 5, così come accertato da apposita perizia giurata a firma di Tecnico abilitato già validata dal S.AC. che attesti:</li>
                                    <ul>
                                        <li><b>la rispondenza dei locali e delle attrezzature alla normativa in vigore;</b></li>
                                        <li><b>la presenza di tutti i nulla osta e/o certificazioni di cui i locali devono essere in possesso ai sensi della normativa in vigore.</b></li>
                                    </ul>
                                    <li>a portare a termine l'attività corsuale autorizzata anche in caso di riduzione degli allievi ad una sola unità.</li>
                                    <li>ad inviare la presente istanza entro venti giorni dalla chiusura dello step in argomento in formato cartaceo con allegato:</li>
                                    <ul>
                                        <li>supporto informatico in formato .pdf che conterrà l'autocertificazione, il patto d'integrità e, per ogni singolo corso, il piano didattico e la dichiarazione di disponibilità delle attrezzature;</li>
                                        <li>curricula formatori;</li>
                                    </ul>
                                    <li>a mantenere invariate le sedi di erogazione autorizzate sino agli esami finali.</li>
                                </ul>
                                <hr>
                                <b>"Tabella di riepilogo di tutti i corsi autorizzati, avviati e conclusi, in fase di attuazione o non avviati nel triennio precedente a partire dalla data della presente istanza, incluso l'anno in corso."</b>
                                <br/>
                                <table class="table table-responsive table-hover" id="tab_dt1">
                                    <thead>
                                        <tr>
                                            <th>Autorizzazione</th>
                                            <th>Data</th>
                                            <th>Corsi autorizzati</th>
                                            <th>Corsi avviati e conclusi</th>
                                            <th>Corsi non avviati</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                                <hr>
                                <table class="table table-responsive table-hover" id="tab_dt2">
                                    <thead>
                                        <tr>
                                            <th colspan="5"> RIEPILOGO ATTIVAZIONE CORSI</th>
                                        </tr>
                                        <tr>
                                            <th>Autorizzazioni</th>
                                            <th>Corsi autorizzati</th>
                                            <th>Corsi avviati e conclusi</th>
                                            <th>Corsi in fase di svolgimento</th>
                                            <th>Corsi non avviati</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                                <hr>
                                <strong>
                                    Il sottoscritto, consapevole del fatto che, in caso di dichiarazioni mendaci, saranno applicate nei suoi riguardi, ai sensi degli artt. 47 e 76 del DPR n. 445/2000 e s.m.i., le sanzioni previste dal codice penale e dalle leggi speciali in materia di falsità negli atti,
                                </strong>
                                <br/>
                                <h3 class="text-center">
                                    DICHIARA
                                </h3>
                                <br/>
                                <strong>
                                che a fronte di 1 autorizzazioni concesse, sono stati autorizzati 7 corsi, dei quali 0 sono stati avviati e conclusi e 5 in fase di attuazione. I corsi non avviati, pertanto, sono pari a 2.    
                                </strong>
                                <br>
                                Allega:
                                <br>
                                <ul>
                                    <li>Autocertificazione, patto d'integrità, dichiarazione d'impegno relativa agli stage e, per ciascuno  dei corsi proposti, il relativo piano didattico con la dichiarazione di disponibilità delle attrezzature compilata.</li>
                                    <li>Autorizza il trattamento dei dati forniti ai sensi D.Lgs. n. 196/2003 e s.m.i.</li>
                                    <li>Dichiara di essere a conoscenza delle modalità di vigilanza e controllo della Regione Siciliana - Dipartimento Regionale dell'istruzione e Formazione Professionale che, ai sensi della normativa vigente, si avvale dei servizi Centro per l’Impiego per la vigilanza didattico – amministrativa e degli Ispettorati territoriali del lavoro per la vigilanza tecnico-amministrativa.</li>
                                </ul>
                            </div>

                            <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">...</div>
                            <div class="tab-pane fade" id="pills-contact" role="tabpanel" aria-labelledby="pills-contact-tab">...</div>
                        </div>
                    </div>
                </div>
                <!--end::Tables Widget 3-->
            </div>

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
        <script src="assets/plugins/DataTables/jquery-3.5.1.js"></script>
        <script src="assets/plugins/DataTables/jquery.dataTables.min.js"></script>
        <script src="assets/plugins/DataTables/datatables.min.js"></script>
        <script src="assets/plugins/DataTables/date-eu.js"></script>
        <script src="assets/fontawesome-6.0.0/js/all.js"></script>
        <link rel="stylesheet" href="assets/plugins/fancybox.v4.0.31.css"/>
        <script type="text/javascript" src="assets/plugins/fancybox.v4.0.31.js"></script>
        <script type="text/javascript" src="assets/js/common.js"></script>
        <script src="assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>

        <script src="assets/js/US_showistanza.js"></script>

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