<%-- 
    Document   : ADM_dashboard
    Created on : 18-feb-2022, 14.01.46
    Author     : raf
--%>

<%@page import="rc.soop.sic.jpa.Moduli_Docenti"%>
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
        <title><%=Constant.NAMEAPP%>: Dettagli Istanza</title>
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
                        <!--begin::Container-->
                        <div class="d-flex flex-column flex-column-fluid container-fluid">
                            <!--begin::Post-->
                            <div class="content flex-column-fluid" id="kt_content">
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
                                                        <button class="nav-link" id="pills-autoc1-tab" data-bs-toggle="pill" data-bs-target="#pills-autoc1" 
                                                                type="button" role="tab" aria-controls="pills-autoc1" aria-selected="false">AUTOCERTIFICAZIONE</button>
                                                    </li>
                                                    <li class="nav-item" role="presentation">
                                                        <button class="nav-link" id="pills-patt1-tab" data-bs-toggle="pill" data-bs-target="#pills-patt1" 
                                                                type="button" role="tab" aria-controls="pills-patt1" aria-selected="false">PATTO DI INTEGRIT&#192;</button>
                                                    </li>
                                                    <li class="nav-item" role="presentation">
                                                        <button class="nav-link" id="pills-stag1-tab" data-bs-toggle="pill" data-bs-target="#pills-stag1" 
                                                                type="button" role="tab" aria-controls="pills-stag1" aria-selected="false">DICH. IMPEGNO STAGE</button>
                                                    </li>

                                                    <%
                                                        for (int i = 0; i < list.size(); i++) {
                                                            String letteracorso = Engine.letterecorsi(i);
                                                    %>
                                                    <li class="nav-item" role="presentation">
                                                        <button class="nav-link" id="pills-defcorso<%=letteracorso%>-tab" data-bs-toggle="pill" data-bs-target="#pills-defcorso<%=letteracorso%>" 
                                                                type="button" role="tab" aria-controls="pills-defcorso<%=letteracorso%>" aria-selected="false">CORSO <%=letteracorso%></button>
                                                    </li>
                                                    <%}
                                                    %>

                                                </ul>
                                                <div class="tab-content" id="pills-tabContent">
                                                    <div class="tab-pane fade show active" id="pills-ist1" role="tabpanel" aria-labelledby="pills-ist1-tab">
                                                        <span class="text-center">
                                                            <%if (autofinanz) {%>
                                                            <h2 class="title">OGGETTO: Istanza di autorizzazione allo svolgimento di corsi di formazione professionale AUTOFINANZIATI</h2>
                                                            <%} else {%>
                                                            <h2 class="title">OGGETTO: Istanza di autorizzazione allo svolgimento di corsi di formazione professionale FINANZIATI</h2>
                                                            <%}%>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
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
                                                        </p>
                                                        <br/>
                                                        <br/>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            consapevole del fatto che, in caso di dichiarazioni mendaci, saranno applicate nei suoi riguardi, ai sensi degli artt. 47 e 76 del DPR n. 445/2000 e s.m.i., le sanzioni previste dal codice penale e dalle leggi speciali in materia di falsità negli atti,
                                                        </p>
                                                        <hr>
                                                        <h3 class="text-center">
                                                            DICHIARA
                                                        </h3>
                                                        <br/>
                                                        <ul align="justify">
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
                                                        <ul align="justify">
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
                                                            che a fronte di 1 autorizzazioni concesse, sono stati autorizzati X corsi, dei quali X sono stati avviati e conclusi e X in fase di attuazione. I corsi non avviati, pertanto, sono pari a X.    
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
                                                    <div class="tab-pane fade" id="pills-autoc1" role="tabpanel" aria-labelledby="pills-autoc1-tab">
                                                        <span class="text-center">
                                                            <h2 class="title">AUTOCERTIFICAZIONE</h2>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
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
                                                        </p>
                                                        <br/>
                                                        <br/>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            consapevole del fatto che, in caso di dichiarazioni mendaci, saranno applicate nei suoi riguardi, ai sensi degli artt. 47 e 76 del DPR n. 445/2000 e s.m.i., le sanzioni previste dal codice penale e dalle leggi speciali in materia di falsità negli atti,
                                                        </p>
                                                        <hr>
                                                        <h3 class="text-center">
                                                            DICHIARA
                                                        </h3>
                                                        <br/>
                                                        <ul align="justify">
                                                            <li>di possedere affidabilità economica e finanziaria provata con avvenuta adozione del bilancio d'esercizio nei termini di legge; di non essere in stato di fallimento, liquidazione coatta, concordato preventivo o altra situazione liquidatoria, anche volontaria (salve le eccezioni di legge);
                                                            </li>
                                                            <li>che il Soggetto attuatore è in regola con le disposizioni della legge 68/1999 e s.m.i. sulle "Norme per il diritto al lavoro dei disabili" laddove previsto, di essere iscritto al repertorio delle notizie economiche e amministrative (REA) di cui al decreto del Presidente della Repubblica del 7 dicembre 1995, n.581 e successive modifiche e integrazioni;
                                                            </li>
                                                            <li>che i soggetti che amministrano il Soggetto attuatore hanno qualità morali e professionali;</li>
                                                            <li>che gli amministratori del Soggetto attuatore muniti di potere di rappresentanza non hanno subito condanne penali - anche non definitive - per delitti considerati all'art. 80 del d.lgs. n. 50/2016 e che non sono sottoposti alle misure di prevenzione od ostative previste rispettivamente all'art. 6 ed all'art. 67 del d.lgs. n. 159/2011;
                                                            </li>
                                                            <li>di rispettare le norme dell’ordinamento giuridico in materia di prevenzione degli infortuni sul luogo di lavoro e delle malattie professionali, della sicurezza sui luoghi di lavoro, dei contratti collettivi di lavoro e della normativa relativa alla tutela dell’ambiente.
                                                            </li>
                                                            <li>di aver preso visione e delle ulteriori disposizioni adottate da parte dell’Amministrazione e di accettarne tutti i contenuti e le condizioni;
                                                            </li>
                                                            <li>di possedere capacità tecnico-professionali e organizzative tali da garantire il regolare svolgimento delle attività previste nell’ambito della proposta progettuale presentata;
                                                            </li>
                                                            <li>di fornire, su richiesta dell’Amministrazione, la documentazione necessaria ad attestare la correttezza dei dati forniti ai fini della valutazione della proposta ai sensi del D.P.R.S. n. 25 del 01/10/2015 e di essere consapevole che, a fronte di dati non veritieri o di mancata trasmissione della documentazione richiesta, sarà revocata l’autorizzazione rilasciata e trasmessa segnalazione all’Autorità Giudiziaria competente;
                                                            </li>
                                                        </ul>
                                                    </div>
                                                    <div class="tab-pane fade" id="pills-patt1" role="tabpanel" aria-labelledby="pills-patt1-tab">
                                                        <span class="text-center">
                                                            <h2 class="title">Patto di Integrit&#224;</h2><br/>
                                                            <h4 class="title">tra</h4><br/>
                                                            <h3 class="title">Regione Siciliana - Assessorato Regionale dell'Istruzione e della Formazione Professionale</h3><br/>
                                                            <h4 class="title">e</h4><br/>
                                                        </span>
                                                        <p class="text-justify text-uppercase" style="text-align: justify-all;" align="justify">
                                                            SOGGETTO ATTUATORE: <strong><u><%=s0.getRAGIONESOCIALE()%></u></strong> 
                                                            Sede legale: <strong><%=s0.getSedelegale().getComune()%> (<%=s0.getSedelegale().getProvincia()%>)</strong> 
                                                            Indirizzo:  <strong><%=s0.getSedelegale().getIndirizzo()%> <%=s0.getSedelegale().getCap()%></strong>
                                                            Codice fiscale: <strong><%=s0.getCODICEFISCALE()%></strong> 
                                                            Partita IVA: <strong><%=s0.getPARTITAIVA()%></strong>
                                                            in persona di: 
                                                            <strong><%=s0.getRap_cognome()%> <%=s0.getRap_nome()%> </strong> 
                                                            Codice fiscale: <strong><%=s0.getRap_cf()%></strong> 
                                                            nato/a a: <strong><%=s0.getRap_luogonascita()%></strong> il: <strong><%=s0.getRap_datanascita()%></strong> 
                                                            residente in: <strong><%=s0.getRap_sede().getComune()%> (<%=s0.getRap_sede().getProvincia()%>)</strong> 
                                                            indirizzo: <strong><%=s0.getRap_sede().getIndirizzo()%></strong> in qualit&#224; di: <strong><%=s0.getRap_carica()%></strong> 
                                                            munito dei relativi poteri,
                                                        </p>
                                                        <hr>
                                                        <span class="text-center">
                                                            <h4 class="title">premesso che</h4><br/>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            la Regione Siciliana Assessorato dell'Istruzione e della Formazione professionale, nell'ambito delle materie di competenza, gestisce procedimenti finalizzati a erogare servizi al cittadino, che rivestono il carattere della pubblica utilità, anche avvalendosi di soggetti privati. 
                                                        </p>
                                                        <span class="text-center">
                                                            <h4 class="title">considerato che</h4><br/>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            al fine di garantire il rispetto della legalità ed il corretto agire della pubblica amministrazione nel settore, si ritiene necessario incentivare e valorizzare l'utilizzo da parte dell'Amministrazione di appositi strumenti per prevenire e/o reprimere il manifestarsi di fenomeni di illecito che possano determinare anche lo sviamento dell'azione amministrativa dai suoi fini propri; analogamente si ritiene opportuno obbligare i soggetti attuatori del settore della formazione professionale che intendono instaurare rapporti contrattuali con la Regione Siciliana quali erogatori di servizi, attraverso la sottoscrizione di appositi patti di integrità, ad adottare analoghe iniziative anche per responsabilizzarli sulle conseguenze interdittive di determinati comportamenti riguardanti un ventaglio di situazioni a rischio che, sebbene non individuate specificamente dalla normativa di settore, delineano fattispecie che possono dar luogo a comportamenti illeciti o favorire le infiltrazioni della criminalità organizzata; un consolidato orientamento giurisprudenziale consente alle Amministrazioni pubbliche, per operare più incisivamente e per tutelare interessi pubblici aventi specifica rilevanza, di individuare nuovi strumenti da affiancare a quelli normativamente previsti da introdurre nei propri atti amministrativi come specifiche clausole di gradimento - clausole di tutela - tese a responsabilizzare i soggetti che entrano in rapporto con essa sulle conseguenze interdittive di comportamenti illeciti;
                                                        </p>
                                                        <span class="text-center">
                                                            <h4 class="title">ritenuto</h4><br/>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            di assumere consapevolmente formali obbligazioni per assicurare, in generale, la prevenzione da possibili fenomeni di devianza e di vulnerazione dei principi di trasparenza, libertà di impresa e leale concorrenza e concorrere a prevenire il rischio delle infiltrazioni criminali nel mercato nonché specifici impegni per salvaguardare i livelli occupazionali.
                                                            <br/>
                                                            Tutto ciò premesso, le Parti concordano e stipulano quanto segue.
                                                        </p>
                                                        <span class="text-center">
                                                            <h4 class="title">ART. 1</h4><br/>
                                                        </span>
                                                        <span class="text-justify">
                                                            Le premesse di cui sopra costituiscono parte integrante e sostanziale del presente Patto.
                                                        </span><hr>
                                                        <span class="text-center">
                                                            <h4 class="title">ART. 2</h4><br/>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            Il Patto di integrità stabilisce la reciproca, formale obbligazione della <b>Regione Siciliana - Assessorato Regionale dell'Istruzione e della Formazione professionale</b>
                                                            e del Soggetto attuatore <strong class="text-uppercase"><u><%=s0.getRAGIONESOCIALE()%></u></strong> per l'erogazione di servizi nel settore della formazione professionale in Sicilia a conformare i propri comportamenti ai principi di lealtà, trasparenza, correttezza, ed espresso impegno anticorruzione e antimafia.
                                                        </p><hr>
                                                        <span class="text-center">
                                                            <h4 class="title">ART. 3</h4><br/>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            La Regione Siciliana - Assessorato Regionale dell'Istruzione e della Formazione professionale si impegna a rendere pubblici i dati riguardanti il procedimento di selezione delle proposte formative nel settore della formazione professionale, permettendo ad ogni operatore economico di conoscere e verificare gli aspetti tecnici dell'intervento e quelli amministrativi del suddetto procedimento, a effettuare i controlli e a condividere anche esso Io spirito etico e moralizzatore che è insito nell'adozione dello strumento, assumendosi le responsabilità connesse e conseguenti. L'Assessorato si impegna, inoltre, a semplificare le procedure amministrative, a renderle trasparenti e a rispettare i termini previsti dalle vigenti norme per tutti gli adempimenti a proprio carico.
                                                        </p><hr>
                                                        <span class="text-center">
                                                            <h4 class="title">ART. 4</h4><br/>
                                                        </span>
                                                        <span class="text-justify">
                                                            Il Soggetto attuatore si impegna a:<br/>
                                                            <ul align="justify">
                                                                <li>ispirare la propria attività ai principi di onestà, trasparenza, lealtà, integrità e correttezza, nel rispetto delle leggi e dei regolamenti vigenti;</li>
                                                                <li>adottare uno specifico Codice Etico, affinché la condotta di tutti coloro che, ai vari livelli di responsabilità, concorrono con i propri atti allo svolgimento della complessiva attività del Soggetto attuatore sia improntata sui seguenti valori:</li>
                                                                <ul><li>integrità morale, onestà personale e correttezza nei rapporti interni ed esterni;</li>
                                                                    <li>trasparenza nei confronti dei dipendenti, dei portatori di interessi correlati e del mercato; </li>
                                                                    <li>rispetto dei dipendenti e impegno a valorizzarne le capacità professionali;</li>
                                                                    <li>impegno sociale;</li>
                                                                    <li>tutela della salute, della sicurezza e dell'ambiente;</li>
                                                                </ul>
                                                                <li>favorire la massima diffusione del Codice Etico, provvedendo al suo approfondimento ed aggiornamento e a garantire il rispetto; selezionare, assumere, retribuire, formare e valutare i dipendenti in base a criteri di merito, di competenza e professionalità, senza alcuna discriminazione politica, sindacale, religiosa, razziale, di lingua e di sesso;</li>
                                                                <li>combattere, in particolare, qualsiasi forma di intimidazione, ostilità, isolamento, indebita interferenza o condizionamento, molestia sessuale;</li>
                                                                <li>rappresentare i fatti gestionali in modo completo, trasparente, veritiero, accurato e tempestivo;</li>
                                                                <li>conservare adeguata documentazione di ogni operazione relativa ai percorsi formativi avviati, in modo da rendere agevole la verifica;</li>
                                                                <li>consentire l'effettuazione dei controlli che attestino le caratteristiche e le motivazioni dell'operazione;</li>
                                                                <li>fornire all'Amministrazione regionale le informazioni necessarie in modo veritiero e completo;</li>
                                                                <li>non offrire, accettare o richiedere somme di denaro o qualsiasi altra ricompensa, vantaggio o beneficio, sia direttamente che indirettamente tramite intermediari, al fine dell'ottenimento dell’autorizzazione o al fine di distorcere l'espletamento corretto della successiva attività o valutazione da parte dell'Amministrazione regionale;</li>
                                                                <li>denunciare immediatamente alle Forze di Polizia ogni illecita richiesta di denaro o altra utilità ovvero  offerta di protezione o estorsione di qualsiasi natura che venga avanzata nei loro confronti o nei confronti di propri rappresentanti o dipendenti, di loro familiari o di eventuali soggetti legati al Soggetto attuatore da rapporti professionali;</li>
                                                                <li>comunicare ogni variazione delle informazioni concernenti la compagine sociale;</li>
                                                                <li>comunicare le generalità del/i proprietario/i e dei soggetti che hanno a qualsiasi titolo la disponibilità degli immobili su cui verranno realizzati gli interventi formativi concessi da parte dell'Amministrazione Regionale e che si è consapevoli che, nel caso in cui la Regione Siciliana Assessorato dell'Istruzione e della Formazione professionale dovesse comunque acquisire nei confronti degli stessi elementi di fatto o indicazioni comunque negative da farli ritenere collegati direttamente o indirettamente ad associazioni di tipo mafioso, si impegnano a recedere dal contratto;</li>
                                                                <li>non attribuire al personale dipendente del Soggetto attuatore incarichi di responsabilità di qualsiasi genere qualora ricorra una delle condizioni di cui agli articoli 7 e 8 del D. Lgs. n. 235/2012 e/o sia accertata la sussistenza di forme di condizionamento, attraverso collegamenti diretti o indiretti con la criminalità di tipo mafioso, tali da compromettere il buon andamento e l'imparzialità dell'amministrazione del Soggetto attuatore, nonché il regolare funzionamento dei servizi ad esso affidati;</li>
                                                                <li>non instaurare rapporti di consulenza, collaborazione, studio, ricerca, o rapporti di lavoro disciplinati dal D. Lgs. 10 settembre 2003, n. 276 né a conferire incarico di componente di organi di controllo o altri incarichi a soggetti esterni nei cui confronti ricorrano le condizioni di cui al punto precedente;</li>
                                                                <li>dichiarare di non trovarsi in situazioni di controllo o di collegamento formale o sostanziale con altri concorrenti e che non si è accordato e non si accorderà con altri partecipanti alla procedura in oggetto;</li>
                                                                <li>informare tutto il personale di cui si avvale del presente patto di integrità e degli obblighi in esso convenuti;</li>
                                                                <li>vigilare affinché gli impegni sopra indicati siano osservati da tutti i collaboratori e dipendenti nell'esercizio dei compiti loro assegnati;</li>
                                                                <li>denunziare alla Pubblica Autorità competente ogni irregolarità o distorsione di cui sia venuto a conoscenza per quanto attiene all'oggetto della presente procedura;</li>
                                                                <li>ai fini dell'applicazione dell'art. 53, comma 16 ter, D.Lgs. n. 165/2001, non intrattenere, né avere intrattenuto, contratti di lavoro subordinato o autonomo e/o attribuito incarichi ad ex dipendenti dell'Amministrazione regionale che hanno esercitato poteri autoritativi o negoziali per conto della stessa Amministrazione nei loro confronti, per il triennio successivo alla cessazione del rapporto;</li>
                                                                <li>ai fini dell'applicazione dell'art. 1 , comma 9, lettera e), Legge n. 190/2012, non trovarsi in rapporti  di coniugio, parentela o affinità, né lui né i propri dipendenti, con i dipendenti dell'Amministrazione  regionale coinvolti a qualunque titolo nella trattazione e/o istruttoria del procedimento di selezione delle proposte formative nel settore della formazione professionale (direttiva del Responsabile per la prevenzione della corruzione e per la trasparenza prot. n. 133740 del 24.1.2014).</li>
                                                            </ul>
                                                        </span>
                                                        <hr>
                                                        <span class="text-center">
                                                            <h4 class="title">ART. 5</h4><br/>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            Fatte salve specifiche disposizioni normative nazionali e/o regionali poste a salvaguardia dell'imparzialità e del buon andamento della pubblica amministrazione, le parti si impegnano reciprocamente nel caso concreto e ciascuno nell'esercizio delle proprie competenze a evitare la sussistenza di situazioni di conflitto di interesse derivanti da rapporti di lavoro o di consulenza, collaborazione, studio, ricerca, anche occasionali instaurati con parenti entro il quarto grado o affini entro il terzo di soggetti che prestano attività lavorativa a qualunque titolo presso uffici dell'Amministrazione regionale esercitanti competenze aventi refluenze nel settore della Formazione professionale. A tal fine, le parti si impegnano ad adottare ogni misura idonea a rimuovere la situazione di conflitto.
                                                        </p><hr>
                                                        <span class="text-center">
                                                            <h4 class="title">ART. 6</h4><br/>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            Il Soggetto attuatore prende atto e accetta che, nel caso di mancato rispetto degli impegni assunti con questo Patto di integrità, la Regione Siciliana Assessorato Regionale dell'Istruzione e della Formazione professionale recederà dal rapporto e revocherà le autorizzazioni concesse.
                                                        </p><hr>
                                                        <span class="text-center">
                                                            <h4 class="title">ART. 7</h4><br/>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            Il Soggetto attuatore è consapevole che la Regione Siciliana - Assessorato Regionale dell'Istruzione e della Formazione professionale, al fine di prevenire o reprimere possibili irregolarità e/o abusi nella gestione delle attività economiche autorizzate, nonché per verificare il rispetto degli obblighi assunti con il presente Patto, oltre ad utilizzare uffici interni, si avvarrà anche della collaborazione dell’Autorità Giudiziaria.
                                                        </p><hr>
                                                        <span class="text-center">
                                                            <h4 class="title">ART. 8</h4><br/>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
                                                            Il presente Patto di Integrità e le relative sanzioni applicabili resteranno in vigore per tutta la durata del rapporto instaurato fra le parti a seguito dell’autorizzazione concessa di cui il presente Patto forma parte integrante e sostanziale.
                                                        </p>
                                                    </div>
                                                    <div class="tab-pane fade" id="pills-stag1" role="tabpanel" aria-labelledby="pills-stag1-tab">
                                                        <span class="text-center">
                                                            <h2 class="title">DICHIARAZIONE D'IMPEGNO ALLA STIPULA DELLE CONVENZIONI DI STAGE</h2>
                                                        </span>
                                                        <p class="text-justify" style="text-align: justify-all;" align="justify">
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
                                                            D.D.G. n° <strong class="text-uppercase"><%=s0.getDDGNUMERO()%></strong>  del <strong class="text-uppercase"><%=s0.getDDGDATA()%></strong> 
                                                        <hr>
                                                        <h3 class="text-center">
                                                            SI IMPEGNA A
                                                        </h3>
                                                        <br/>
                                                        <ul align="justify">
                                                            <li>stipulare convenzioni di stage con aziende operanti nei settori di riferimento per tutti gli allievi frequentanti i corsi per i quali é stata chiesta autorizzazione;</li>
                                                            <li>trasmettere, almeno 30 giorni prima dell’avvio degli stage, tutte le convenzioni con posta certificata al Servizio Gestione UOB II e al Centro per l’Impiego territorialmente competente.</li>
                                                        </ul>
                                                        <hr>
                                                        <h3 class="text-center">
                                                            DICHIARA
                                                        </h3>
                                                        <br/>
                                                        <ul align="justify">
                                                            <li>di essere a conoscenza delle modalità di vigilanza e controllo della Regione Siciliana - Dipartimento Regionale dell'istruzione e Formazione Professionale che, ai sensi della normativa vigente, si avvale dei servizi Centro per l’Impiego per la vigilanza didattico – amministrativa e degli Ispettorati territoriali del lavoro per la vigilanza tecnico-amministrativa.</li>
                                                        </ul>
                                                        <i>Autorizza il trattamento dei dati forniti ai sensi D.Lgs. n. 196/2003 e s.m.i.</i>
                                                        </p>
                                                    </div>

                                                    <%
                                                        for (int i = 0; i < list.size(); i++) {
                                                            Corso c1 = list.get(i);
                                                            String letteracorso = Engine.letterecorsi(i);
                                                    %>
                                                    <div class="tab-pane fade" id="pills-defcorso<%=letteracorso%>" role="tabpanel" aria-labelledby="pills-defcorso<%=letteracorso%>-tab">
                                                        <span class="text-center">
                                                            <h3 class="title">PIANO DIDATTICO DEL CORSO DI <%=c1.getSchedaattivita().getCertificazioneuscita().getNome()%> PER</h3>
                                                            <br/>
                                                            <h2 class="title"><u><%=c1.getSchedaattivita().getTitoloattestato()%></u></h2>
                                                            <br/>
                                                        </span>
                                                        <div class="row col-md-12">
                                                            <div class="col-md-4">
                                                                <span class="text-center">
                                                                    <h3 class="title">SOGGETTO PROPONENTE</h3>
                                                                </span>
                                                                <p class="text-justify text-uppercase" style="text-align: justify-all;" align="justify">
                                                                    SOGGETTO ATTUATORE: <strong><u><%=s0.getRAGIONESOCIALE()%></u></strong> 
                                                                    Sede legale: <strong><%=s0.getSedelegale().getComune()%> (<%=s0.getSedelegale().getProvincia()%>)</strong> 
                                                                    Indirizzo:  <strong><%=s0.getSedelegale().getIndirizzo()%> <%=s0.getSedelegale().getCap()%></strong>
                                                                    Codice fiscale: <strong><%=s0.getCODICEFISCALE()%></strong> 
                                                                    Partita IVA: <strong><%=s0.getPARTITAIVA()%></strong>
                                                                    avente natura giuridica di: <strong class="text-uppercase"><%=s0.getTIPOLOGIA()%></strong>
                                                                    con sede legale in: <strong class="text-uppercase"><%=s0.getSedelegale().getComune()%> (<%=s0.getSedelegale().getProvincia()%>)</strong> 
                                                                    indirizzo: <strong class="text-uppercase"><%=s0.getSedelegale().getIndirizzo()%> - <%=s0.getSedelegale().getCap()%></strong> 
                                                                    numero di telefono: <strong class="text-uppercase"><%=s0.getTELEFONO()%></strong>
                                                                    Posta Elettronica Certificata: <strong class="text-uppercase"><%=s0.getPEC()%></strong>
                                                                    CIR: <strong class="text-uppercase"><%=s0.getCIR()%></strong> 
                                                                </p>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <span class="text-center">
                                                                    <h3 class="title">DIRETTORE RESPONSABILE</h3>
                                                                </span>
                                                                <p class="text-justify text-uppercase" style="text-align: justify-all;" align="justify">
                                                                    Cognome e Nome: <strong class="text-uppercase"><%=s0.getRap_cognome()%> <%=s0.getRap_nome()%></strong>
                                                                    nato/a: <strong class="text-uppercase"><%=s0.getRap_luogonascita()%></strong>
                                                                    il: <strong class="text-uppercase"><%=s0.getRap_datanascita()%></strong>
                                                                    Codice Fiscale: <strong class="text-uppercase"><%=s0.getRap_cf()%></strong>
                                                                    residente in: <strong class="text-uppercase"><%=s0.getRap_sede().getComune()%> (<%=s0.getRap_sede().getProvincia()%>)</strong> 
                                                                    indirizzo: <strong class="text-uppercase"><%=s0.getRap_sede().getIndirizzo()%> - <%=s0.getRap_sede().getCap()%></strong> 
                                                                </p>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <span class="text-center">
                                                                    <h3 class="title">SEDE OPERATIVA</h3>
                                                                </span>
                                                                <p class="text-justify text-uppercase" style="text-align: justify-all;" align="justify">
                                                                    Comune: <strong class="text-uppercase"><%=c1.getSedescelta().getComune()%> (<%=c1.getSedescelta().getProvincia()%>)</strong><br/>
                                                                    Indirizzo: <strong class="text-uppercase"><%=c1.getSedescelta().getIndirizzo()%> - <%=c1.getSedescelta().getCap()%></strong><br/>
                                                                    Telefono: <strong class="text-uppercase"><%=s0.getTELEFONO()%></strong>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <span class="text-center">
                                                                <h2 class="title">DATI SPECIFICI</h2>
                                                            </span>
                                                            <p class="text-justify text-uppercase" style="text-align: justify-all;" align="justify">
                                                                <%if (!c1.getRepertorio().getProfessioni().isEmpty()) {%>
                                                                CODICE ISTAT: 
                                                                <%for (Professioni pr1 : c1.getRepertorio().getProfessioni()) {%>
                                                                <strong><%=pr1.getCodiceProfessioni()%> - <%=pr1.getNome()%> </strong>, 
                                                                <%}%>
                                                                <%}%> 
                                                                TIPO: <strong><%=c1.getSchedaattivita().getCertificazioneuscita().getNome()%></strong>
                                                                CERTIFICAZIONE: <strong><%=c1.getSchedaattivita().getCertificazioneuscita().getNome()%></strong> - <strong><%=c1.getRepertorio().getLivelloeqf().getNome()%></strong>
                                                                TITOLO CONSEGUITO:  <strong><%=c1.getSchedaattivita().getTitoloattestato()%></strong>
                                                                NUMERO EDIZIONI: <strong><%=c1.getQuantitarichiesta()%></strong> 
                                                                requisiti d'ingresso: <%=c1.getSchedaattivita().getPrerequisiti()%>
                                                                <%if (!c1.getSchedaattivita().getLivellomassimoscolarita().equals("")) {%>
                                                                - <%=c1.getSchedaattivita().getLivellomassimoscolarita()%>
                                                                <%}%>
                                                                ORE: <strong><%=(c1.getDurataore() + c1.getStageore())%></strong> - GIORNI: <strong><%=c1.getDuratagiorni()%></strong> 
                                                                - ORE STAGE IN IMPRESA: <strong><%=c1.getStageore()%></strong>
                                                                DESTINATARI AMMISSIBILI: <strong><%=c1.getNumeroallievi()%></strong>
                                                                <br/>
                                                                costo previsto per allievo: <strong><%=Utils.roundDoubleandFormat(c1.getCostostimatoallievo(), 2)%> &#8364;</strong>
                                                            </p>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <span class="text-center">
                                                                <h2 class="title">ARTICOLAZIONE DIDATTICA IN ORE</h2>
                                                            </span>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <table class="table table-responsive table-hover" id="tab_dtcorso<%=letteracorso%>">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Modulo</th>
                                                                        <th>Ore Aula - Teorica</th>
                                                                        <th>Ore Aula - Elearning</th>
                                                                        <th>Ore Area Tecnica-Operativa</th>
                                                                        <th>Ore Aula</th>
                                                                        <th>Ore Totali</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <%
                                                                        double t_comp_base = 0.0;
                                                                        double t_aula_teorica = 0.0;
                                                                        double t_aula_el = 0.0;
                                                                        double t_aula_tecnope = 0.0;
                                                                        double t_aula = 0.0;
                                                                        double t_tot = 0.0;
                                                                        double t_compl = Utils.fd(String.valueOf(c1.getStageore()) + ".0");
                                                                        List<Calendario_Formativo> calendar = eo.calendario_formativo_corso(c1);
                                                                        for (Calendario_Formativo c2 : calendar) {
                                                                            if (c2.getTipomodulo().equals("BASE")) {
                                                                                t_comp_base = t_comp_base + c2.getOre();
                                                                            }
                                                                            t_aula_teorica = t_aula_teorica + c2.getOre_teorica_aula();
                                                                            t_aula_el = t_aula_el + c2.getOre_teorica_el();
                                                                            t_aula_tecnope = t_aula_tecnope + c2.getOre_tecnica_operativa();
                                                                            t_aula = t_aula + c2.getOre_aula();
                                                                            t_tot = t_tot + c2.getOre();
                                                                    %>
                                                                    <tr>
                                                                        <td><%=c2.getNomemodulo()%> (<%=Utils.roundDoubleandFormat(c2.getOre(), 2)%> ORE)</td>
                                                                        <td><%=Utils.roundDoubleandFormat(c2.getOre_teorica_aula(), 2)%></td>
                                                                        <td><%=Utils.roundDoubleandFormat(c2.getOre_teorica_el(), 2)%></td>
                                                                        <td><%=Utils.roundDoubleandFormat(c2.getOre_tecnica_operativa(), 2)%></td>
                                                                        <td><%=Utils.roundDoubleandFormat(c2.getOre_aula(), 2)%></td>
                                                                        <td><%=Utils.roundDoubleandFormat(c2.getOre(), 2)%></td>
                                                                    </tr>
                                                                    <%}
                                                                    %>
                                                                </tbody>
                                                                <tfoot>
                                                                    <tr>
                                                                        <th>TOTALI</th>
                                                                        <th><%=Utils.roundDoubleandFormat(t_aula_teorica, 2)%></th>
                                                                        <th><%=Utils.roundDoubleandFormat(t_aula_el, 2)%></th>
                                                                        <th><%=Utils.roundDoubleandFormat(t_aula_tecnope, 2)%></th>
                                                                        <th><%=Utils.roundDoubleandFormat(t_aula, 2)%></th>
                                                                        <th><%=Utils.roundDoubleandFormat(t_tot, 2)%></th>
                                                                    </tr>
                                                                    <tr>
                                                                        <th colspan="5">STAGE</th>
                                                                        <th><%=c1.getStageore()%></th>
                                                                    </tr>

                                                                    <tr>
                                                                        <th colspan="5">TOTALE COMPLESSIVO</th>
                                                                        <th><%=Utils.roundDoubleandFormat(t_compl + t_tot, 2)%></th>
                                                                    </tr>
                                                                </tfoot>
                                                            </table>
                                                        </div>
                                                        <hr>
                                                        <div class="row col-md-12">
                                                            <h3 class="title text-center">RIEPILOGO ARTICOLAZIONE DIDATTICA</h3>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <div class="col-md-6">
                                                                <p align="right">
                                                                    <strong>AREA TEORICA</strong>
                                                                </p>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <p>
                                                                    <strong><%=Utils.roundDoubleandFormat(t_aula_teorica, 2)%></strong>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <div class="col-md-6">
                                                                <p align="right">
                                                                    <strong>AREA TECNICA OPERATIVA</strong>
                                                                </p>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <p>
                                                                    <strong><%=Utils.roundDoubleandFormat(t_aula_tecnope, 2)%></strong>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <div class="col-md-6">
                                                                <p align="right">
                                                                    <strong>ELEARNING</strong>
                                                                </p>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <p>
                                                                    <strong><%=Utils.roundDoubleandFormat(t_aula_el, 2)%></strong>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <div class="col-md-6">
                                                                <p align="right">
                                                                    <strong>COMPETENZE DI BASE OBBLIGATORIE</strong>
                                                                </p>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <p>
                                                                    <strong><%=Utils.roundDoubleandFormat(t_comp_base, 2)%></strong>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <div class="col-md-6">
                                                                <p align="right">
                                                                    <strong>AULA</strong>
                                                                </p>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <p>
                                                                    <strong><%=Utils.roundDoubleandFormat(t_aula, 2)%></strong>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <div class="col-md-6">
                                                                <p align="right">
                                                                    <strong>STAGE</strong>
                                                                </p>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <p>
                                                                    <strong><%=c1.getStageore()%></strong>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <h3 class="title text-center">DESTINATARI E REQUISITI</h3>
                                                        <div class="row col-md-12">
                                                            <p class="text-justify" align="justify">
                                                                Sono destinatari delle attività formative autofinanziate, le persone in età lavorativa, le persone in cerca di prima occupazione, i disoccupati, inclusi i disoccupati di lunga durata, e gli occupati che, al momento della candidatura per la partecipazione al percorso formativo, dovranno produrre la documentazione attestante il possesso dei seguenti requisiti:
                                                            <ul align="justify">
                                                                <li>essere residenti o domiciliati in Sicilia;</li>
                                                                <li>possedere il titolo di studio minimo di accesso indicato nel Repertorio delle qualificazioni in corrispondenza del profilo di riferimento;</li>
                                                                <li>in caso di percorsi di aggiornamento o specializzazione, essere in possesso della qualifica di provenienza prevista.</li>
                                                                <li>attestazione di servizio del datore di lavoro o impresa propria, per coloro che si rivolgono ai percorsi formativi per occupati;</li>
                                                                <li>in caso di cittadini non comunitari, possedere regolare permesso di soggiorno in corso di validità e traduzione o equipollenza del titolo di studio;</li>
                                                            </ul>
                                                            I suddetti requisiti devono essere posseduti alla data di presentazione della candidatura e, ad eccezione di quelli previsti ai punti 4 e 5, possono essere comprovati con dichiarazioni, contestuali all’istanza, sottoscritte dall’interessato e prodotte in sostituzione delle normali certificazioni, secondo le modalità previste dal D.P.R. 28 dicembre 2000 n. 445.
                                                            </p>
                                                        </div>
                                                        <hr>
                                                        <h3 class="title text-center">CALENDARIO DELLE ATTIVITÀ DIDATTICHE</h3>
                                                        <div class="row col-md-12">
                                                            <p class="text-justify" align="justify">
                                                                La sequenza delle competenze dovrà essere formulata come proposta dal Repertorio delle qualificazioni della Regione Siciliana.
                                                                Il piano didattico allegato all’istanza dovrà essere redatto e calendarizzato secondo l’organizzazione sopra citata.
                                                            </p>
                                                        </div>
                                                        <hr>
                                                        <h3 class="title text-center">VERIFICHE INTERMEDIE</h3>
                                                        <div class="row col-md-12">
                                                            <p class="text-justify" align="justify">
                                                                Al termine dell’erogazione di tutti i moduli riconducibili  a ciascuna delle competenze, dovrà essere effettuata la relativa prova di apprendimento, che avrà ad oggetto l’accertamento del raggiungimento degli obiettivi prefissati e rappresenterà elemento utile per il riconoscimento della certificazione delle competenze.
                                                                La prova di apprendimento consisterà in un questionario di composto da 25 domande a cui saranno assegnati 4 punti per ogni risposta esatta; nel caso di competenza pratica dovrà effettuarsi una prova dedicata.
                                                                Il questionario di apprendimento costituisce monte ore della prova di valutazione finale e dovrà essere costruito con domande a scelta multipla con tre opzioni di risposta, di cui una sola corretta; il superamento della prova è subordinato al conseguimento del risultato di 60 punti su 100.
                                                                Il superamento della prova pratica è subordinato all’effettiva dimostrazione delle competenze professionali acquisite (saper combinare e applicare conoscenze, capacità e abilità per svolgere mansioni in ambito lavorativo).
                                                            </p>
                                                        </div>
                                                        <hr>
                                                        <h3 class="title text-center">STAGE</h3>
                                                        <div class="row col-md-12">
                                                            <p class="text-justify" align="justify">
                                                                Lo stage è formalizzato tramite la stipula di convenzione tra la struttura formativa e la struttura ospitante e la sottoscrizione del progetto formativo di stage, sottoscritto dall’Allievo, dal Tutor aziendale e dal Responsabile didattico organizzativo (Tutor del corso).
                                                                Lo stage, ha una valenza orientativa in quanto serve a verificare, in situazione reale di lavoro, l’attitudine a svolgere la professione.
                                                                In questa fase l’allievo ha l’opportunità di conoscere direttamente il mondo del lavoro, e consente alla struttura formativa di valutare la capacità di tradurre le competenze acquisite durante il corso in una reale capacità lavorativa.
                                                                L’andamento delle attività di stage sarà oggetto di costante valutazione e concorrerà alla determinazione del giudizio utile per l’ammissione agli esami finali.
                                                                Per ogni allievo sarà individuato il Tutor aziendale e, da parte della struttura formativa, il Responsabile didattico/organizzativo, i quali, congiuntamente, redigeranno il “Progetto formativo di stage”, necessario alla definizione degli obiettivi formativi.
                                                                il Tutor aziendale sostiene l’allievo nelle attività di stage e il Responsabile didattico/organizzativo ne  verifica la rispondenza con quelle previste dal progetto formativo sottoscritto.
                                                                Le attività di stage sono valutate, congiuntamente alle strutture ospitanti, in termini di:
                                                            </p>
                                                            <ul>
                                                                <li>Conoscenza diretta del mondo del lavoro;</li>
                                                                <li>Conseguimento degli obiettivi prefissati;</li>
                                                                <li>Acquisizione dei comportamenti propri della figura professionale;</li>
                                                                <li>Capacità di trasformare le conoscenze acquisite durante il corso in reali abilità lavorative;</li>
                                                                <li>Capacità analitiche e di sintesi;</li>
                                                                <li>Autonomia e senso di responsabilità;</li>
                                                                <li>Manifestazione di spirito di iniziativa e di integrazione;</li>
                                                                <li>Comprensione dei processi produttivi;</li>
                                                                <li>Capacità relazionali in genere;</li>
                                                                <li>Acquisizione di capacità di relazionarsi con altre figure professionali;</li>
                                                                <li>Comprensione dei ruoli delle figure a monte e a valle della propria;</li>
                                                                <li>Capacità di integrarsi nel contesto dell’ambito nel quale deve rendere la prestazione.</li>
                                                            </ul>
                                                        </div>
                                                        <hr>
                                                        <h3 class="title text-center">AUTOVALUTAZIONE</h3>
                                                        <div class="row col-md-12">
                                                            <p class="text-justify" align="justify">
                                                                La struttura formativa si impegna ad adottare un sistema di autovalutazione, articolato per indicatori specifici e misurabili.
                                                                Sarà attuato tramite la somministrazione, in forma anonima, di almeno due schede di rilevazione per anno formativo, una intermedia ed una finale.
                                                                Le schede di valutazione consentiranno di ottenere una valutazione in forma anonima dell’efficacia della struttura formativa nel suo complesso, con particolare riferimento al raggiungimento degli obiettivi, all’organizzazione delle attività e all’efficacia dell’intervento dei docenti e delle altre figure professionali che intervengono a vario titolo nella erogazione del servizio. 
                                                                Pertanto, saranno incentrate sulla valutazione dell’utente rispetto alle prestazioni della Struttura formativa, del Tutor, del Responsabile del corso e dei Formatori.
                                                                Per i formatori sarà richiesta la valutazione per ognuno dei moduli insegnati.
                                                                Le schede di valutazione finale saranno consegnate in plico chiuso al Presidente della commissione degli esami finali, che la produrrà agli atti contestualmente ai relativi verbali.
                                                            </p>
                                                        </div>
                                                        <hr>
                                                        <h3 class="title text-center">MONITORAGGIO DELLE PRESENZE DEGLI ALLIEVI</h3>
                                                        <div class="row col-md-12">
                                                            <p class="text-justify" align="justify">
                                                                Al fine di evitare involontarie dimissioni per superamento dei limiti di assenza consentiti, le presenze degli allievi saranno costantemente monitorate e, con cadenza mensile, gli stessi riceveranno il relativo report personale.
                                                                Il report mensile individuale conterrà in calce la dichiarazione dell’allievo relativa alla veridicità dei dati esposti e sarà resa ai sensi del DPR n. 445/2000.
                                                                La struttura formativa è onerata a produrre mensilmente il riepilogo delle presenze ed i relativi report sottoscritti dagli allievi nonché la scansione del registro di classe e di stage.
                                                                La struttura formativa, inoltre, è onerata a trasmette mensilmente il riepilogo delle presenze corredato dalla dichiarazione sulla veridicità dei dati sottoscritta dal Legale rappresentante nelle forme previste dal DPR n. 445/2000.
                                                                La scansione diverrà giornaliera con l'istituzione del registro digitale e della piattaforma informatica dedicata ai corsi autofinanziati.
                                                                In assenza di tali adempimenti, l’Amministrazione avvierà il procedimento di revoca o sospensione.
                                                                Tali provvedimenti costituiranno elemento di valutazione per le successive richieste di autorizzazione.
                                                            </p>
                                                        </div>
                                                        <hr>
                                                        <h3 class="title text-center">CERTIFICAZIONE FINALE</h3>
                                                        <div class="row col-md-12">
                                                            <p class="text-justify" align="justify">
                                                                A seguito del superamento dell’esame finale, a cui saranno ammessi solo gli allievi che hanno frequentato almeno il 
                                                                <b><%=(100 - c1.getSchedaattivita().getOreassenzamassime_perc())%>%</b> delle ore complessivamente previste, 
                                                                sarà rilasciata il seguente attestato di qualifica, specializzazione o frequenza e profitto:<br/>
                                                                <b><u><%=c1.getSchedaattivita().getTitoloattestato()%></u></b><br/>
                                                                in coerenza con il Repertorio delle qualificazioni della Regione Siciliana adottato con decreto assessoriale n. 2570 del 26 maggio 2016.
                                                            </p>
                                                        </div>
                                                        <hr>
                                                        <h3 class="title text-center">ESPERIENZA DIDATTICA E PROFESSIONALE DEI FORMATORI COINVOLTI</h3>
                                                        <div class="row col-md-12">
                                                            <table class="table table-responsive table-hover" id="tab_dtdocenti">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Cognome e Nome</th>
                                                                        <th>Codice Fiscale</th>
                                                                        <th>Data di Nascita</th>
                                                                        <th>Ore Impegno Previste</th>
                                                                        <th>Titolo di Studio</th>
                                                                        <th>Moduli</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <%
                                                                        for (Calendario_Formativo cf1 : calendar) {
                                                                            List<Moduli_Docenti> md1 = eo.list_moduli(cf1);
                                                                            for (Moduli_Docenti md2 : md1) {
                                                                                Docente d0 = md2.getDocente();
                                                                                md2.getOrepreviste();
                                                                    %>
                                                                    <tr>
                                                                        <td><%=d0.getCognome()%> <%=d0.getNome()%></td>
                                                                        <td><%=d0.getCodicefiscale()%></td>
                                                                        <td><%=Constant.sdf_PATTERNDATE4.format(d0.getDatanascita())%></td>
                                                                        <td><%=Utils.roundDoubleandFormat(md2.getOrepreviste(), 2)%></td>
                                                                        <td><%=d0.getTitolostudio()%></td>
                                                                        <td><%=cf1.getNomemodulo()%></td>
                                                                    </tr>   
                                                                    <%}
                                                                        }
                                                                    %>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <hr>

                                                        <h3 class="title text-center">CONVENZIONI CON LE IMPRESE PER ACCOGLIENZA DEGLI ALLIEVI IN STAGE</h3>
                                                        <div class="row col-md-12">
                                                            <p class="text-justify" align="justify">
                                                                Si allega la dichiarazione obbligatoria di impegno del Soggetto proponente a stipulare convenzioni di stage con aziende del settore, specificando tipologia di settore e numero di allievi ospitati per ogni struttura ospitante.
                                                                Le convenzioni saranno trasmesse al Servizio Gestione UOB II, con posta certificata, e al Centro per l’Impiego territorialmente competente almeno 30 giorni prima dell’avvio dello stage.
                                                            </p>
                                                        </div>
                                                        <hr>
                                                        <h3 class="title text-center">DICHIARAZIONE SOSTITUTIVA DI AUTOCERTIFICAZIONE
                                                            (ai sensi dell’art. 46 del D.P.R. 28 dicembre 2000, n. 445)</h3>
                                                        <div class="row col-md-12">
                                                            <p class="text-justify" align="justify">
                                                                Il/La sottoscritto/a: <strong class="text-uppercase"><%=s0.getRap_cognome()%> <%=s0.getRap_nome()%></strong>
                                                                nato/a: <strong class="text-uppercase"><%=s0.getRap_luogonascita()%></strong>
                                                                il: <strong class="text-uppercase"><%=s0.getRap_datanascita()%></strong>
                                                                Codice Fiscale: <strong class="text-uppercase"><%=s0.getRap_cf()%></strong>
                                                                legale rappresentante del soggetto proponente: <strong class="text-uppercase"><%=s0.getRAGIONESOCIALE()%></strong>
                                                                con sede legale in: <strong class="text-uppercase"><%=s0.getSedelegale().getComune()%> (<%=s0.getSedelegale().getProvincia()%>)</strong> 
                                                                indirizzo: <strong class="text-uppercase"><%=s0.getSedelegale().getIndirizzo()%> - <%=s0.getSedelegale().getCap()%></strong> 
                                                                <br/>
                                                                <b>avvalendosi delle disposizioni in materia di autocertificazione e consapevole delle pene stabilite per false attestazioni e mendaci dichiarazioni previste dagli artt. 483, 495 e 496 del Codice Penale, sotto la propria personale responsabilità,</b>
                                                            </p>
                                                            <h4 class="title text-center">DICHIARA</h4>
                                                            <h4 class="title">
                                                                che per il corso <%=letteracorso%> di cui all'istanza prot. <%=is1.getProtocollosoggetto()%> del <%=is1.getProtocollosoggettodata()%></h4>
                                                            <ul align="justify">
                                                                <li>l’intervento formativo proposto	non è sottoposto a specifiche tutele da parte dell’ordinamento giuridico</li>
                                                                <li>e a particolari normative di settore (in caso affermativo, allegare la specifica normativa di settore).
                                                                </li>
                                                                <li>tipo, quantità, titolo di disponibilità, registro e numero inventario delle attrezzature destinate alla realizzazione dell'intervento formativo in oggetto, con esclusione di quelle previste e già dichiarate in sede di accreditamento, sono:
                                                                </li>
                                                            </ul>
                                                        </div>
                                                        <div class="row col-md-12">
                                                            <table class="table table-responsive table-hover" id="tab_dtattr">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Descrizione</th>
                                                                        <th>Modalità di acquisizione</th>
                                                                        <th>Quantità</th>
                                                                        <th>Dal</th>
                                                                        <th>Registro e Numero Inventario</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <%
                                                                        for (Attrezzature at1 : c1.getAttrezzature()) {%>
                                                                    <tr>
                                                                        <td><%=at1.getDescrizione()%></td>
                                                                        <td><%=at1.getModalita().name()%></td>
                                                                        <td><%=at1.getQuantita()%></td>
                                                                        <td><%=Constant.sdf_PATTERNDATE4.format(at1.getDatainizio())%></td>
                                                                        <td><%=at1.getRegistroinventario()%></td>
                                                                    </tr>
                                                                    <%}%>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <hr>
                                                    </div>
                                                </div>
                                                <%}
                                                %>



                                            </div>
                                        </div>
                                    </div>
                                </div>

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
            <!--end::Col-->
            <!--begin::Col-->
            <!--end::Col-->
            <!--end::Row-->

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