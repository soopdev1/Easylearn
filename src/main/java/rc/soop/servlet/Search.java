package rc.soop.servlet;

import com.google.common.base.Splitter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import rc.soop.sic.Constant;
import static rc.soop.sic.Constant.PATTERNDATE4;
import static rc.soop.sic.Constant.sdf_PATTERNDATE4;
import static rc.soop.sic.Constant.sdf_PATTERNDATE5;
import rc.soop.sic.Utils;
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.getRequestValue;
import static rc.soop.sic.Utils.isAdmin;
import static rc.soop.sic.Utils.redirect;
import rc.soop.sic.jpa.Allievi;
import rc.soop.sic.jpa.Altropersonale;
import rc.soop.sic.jpa.CommissioneEsame;
import rc.soop.sic.jpa.Corso;
import rc.soop.sic.jpa.CorsoAvviato_Docenti;
import rc.soop.sic.jpa.Corsoavviato;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EnteStage;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.PresidenteCommissione;
import rc.soop.sic.jpa.Sede;
import rc.soop.sic.jpa.SoggettoProponente;
import rc.soop.sic.jpa.Stati;
import rc.soop.sic.jpa.Tipologia_Percorso;
import rc.soop.sic.jpa.User;

/**
 *
 * @author Administrator
 */
public class Search extends HttpServlet {

    private static final String ITOTALRECORDS = "iTotalRecords";
    private static final String ITOTALDISPLAY = "iTotalDisplayRecords";
    private static final String SECHO = "sEcho";
    private static final String SCOLUMS = "sColumns";
    private static final String AADATA = "aaData";
    private static final String RECORDID = "RecordID";
    private static final String APPJSON = "application/json";
    private static final String CONTENTTYPE = "Content-Type";

    protected void LISTSEDESTAGE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder o1 = new StringBuilder("");
        try {
            EntityOp eop = new EntityOp();
            EnteStage es = eop.getEm().find(EnteStage.class, Utils.parseLongR(getRequestValue(request, "IDENTE")));
            for (Sede c1 : es.getSedistage()) {
                o1.append("<option value='")
                        .append(c1.getIdsede()).append("'>")
                        .append(c1.getIndirizzo())
                        .append(" ")
                        .append(c1.getCap())
                        .append(" ")
                        .append(c1.getComune())
                        .append(" ")
                        .append(c1.getProvincia())
                        .append("</option>");
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(o1);
        }
    }

    protected void SELECTDOCENTE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String q = request.getParameter("q");
        List<Docente> result = new EntityOp().list_select_Docenti(q);
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"items\": [ ";
            String fine = "]}";
            String valore = "";
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    valore = valore + "{"
                            + "      \"id\": \"" + result.get(i).getIddocente() + "\","
                            + "      \"name\": \"" + result.get(i).getCognome() + " " + result.get(i).getNome() + "\","
                            + "      \"full_name\": \"" + result.get(i).getCognome() + " " + result.get(i).getNome() + "\""
                            + "},";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
            }
        }

    }

    protected void GETDIRETTORE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String data = "KO";
        try {
            EntityOp eop = new EntityOp();
            Corsoavviato co1 = eop.getEm().find(Corsoavviato.class, Utils.parseLongR(getRequestValue(request, "IDCORSO")));
            data = co1.getDirettorecorso();
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(data);
        }

    }

    protected void LISTDOCENTICORSO(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder o1 = new StringBuilder("");
        try {
            EntityOp eop = new EntityOp();
            Corso co1 = eop.getEm().find(Corso.class, Utils.parseLongR(getRequestValue(request, "IDCORSO")));
            List<Docente> result = eop.list_docenti_corso(co1);
            for (Docente c1 : result) {
                o1.append("<option value='")
                        .append(c1.getIddocente()).append("' selected>")
                        .append(c1.getCognome())
                        .append(" ")
                        .append(c1.getNome())
                        .append("</option>");
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(o1);
        }
    }

    protected void LISTDOCENTICORSOPERCOMMISSIONE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder o1 = new StringBuilder("");
        try {
            EntityOp eop = new EntityOp();

            List<String> SELECTED = Splitter.on(",").splitToList(getRequestValue(request, "SELECTED"));

            Corsoavviato co1 = eop.getEm().find(Corsoavviato.class, Utils.parseLongR(getRequestValue(request, "IDCORSO")));
            List<CorsoAvviato_Docenti> result = eop.list_cavv_docenti(co1);

            for (CorsoAvviato_Docenti cd1 : result) {

                Docente c1 = cd1.getDocente();

                if (SELECTED.contains(String.valueOf(c1.getIddocente()))) {
                    continue;
                }

                o1.append("<option value='")
                        .append(c1.getIddocente()).append("'>")
                        .append(c1.getCognome())
                        .append(" ")
                        .append(c1.getNome())
                        .append("</option>");
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(o1);
        }
    }

    protected void LISTCORSIISTANZA(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder o1 = new StringBuilder("<option value=''>Scegli...</option>");
        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            EntityOp eop = new EntityOp();
            Istanza is1 = eop.getEm().find(Istanza.class, Utils.parseLongR(getRequestValue(request, "IDIST")));
            List<Corso> result = eop.getCorsiIstanza(is1, so);
            for (Corso c1 : result) {
                int edizionirichieste = c1.getQuantitarichiesta();
                List<Corsoavviato> list = eop.getCorsiAvviati_Corsobase(c1);
                if (edizionirichieste > list.size()) {
                    o1.append("<option ")
                            .append("data-maxday='").append(c1.getDuratagiorni()).append("'")
                            .append("data-numall='").append(c1.getNumeroallievi()).append("'")
                            .append(" value='").append(c1.getIdcorso()).append("'>")
                            .append(c1.getRepertorio().getDenominazione()).append(" - Edizioni richieste: ")
                            .append(edizionirichieste).append(" - Già Avviati: ").append(list.size())
                            .append("</option>");
                }
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(o1);
        }
    }

    protected void list_corso_admin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityOp ep = new EntityOp();

        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            String soggetto = getRequestValue(request, "soggetto");

            SoggettoProponente so;
            try {
                so = ep.getEm().find(SoggettoProponente.class, Utils.parseLongR(soggetto));
            } catch (Exception ex1) {
                so = null;
            }

            String statocorso = getRequestValue(request, "statocorso");
            String tipopercorso = getRequestValue(request, "tipopercorso");
            List<Corsoavviato> result = ep.list_corso_user(so, tipopercorso, statocorso);
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);

            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("stato", res.getStatocorso().getHtmldescr());
                data_value.addProperty("soggetto", res.getCorsobase().getSoggetto().getRAGIONESOCIALE());
                data_value.addProperty("id", res.getIdcorsoavviato());
                String nome = "<b>Tipologia Percorso: " + res.getCorsobase().getIstanza().getTipologiapercorso().getNometipologia() + "</b><br/><u>" + res.getCorsobase().getRepertorio().getDenominazione() + "</u>";
                data_value.addProperty("nome", nome);
                data_value.addProperty("datainizio", sdf_PATTERNDATE4.format(res.getDatainizio()));
                data_value.addProperty("datafine", sdf_PATTERNDATE4.format(res.getDatafine()));
                data_value.addProperty("datainserimento", sdf_PATTERNDATE5.format(res.getDatainserimento()));
                String azioni
                        = "<form action=\"US_showcorsoavviato.jsp\" method=\"POST\" target=\"_blank\">"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + res.getIdcorsoavviato() + "\"/>"
                        + "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA DETTAGLI CORSO\"data-preload='false'><i class=\"fa fa-file-text\"></i></button>"
                        + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"GESTIONE ALLEGATI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-secondary\""
                        + "onclick=\"return document.getElementById('gestall_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-file-clipboard\"></i></button>"
                        + "</form>"
                        + "<form action=\"US_allegaticorso.jsp\" method=\"POST\" target=\"_blank\" id=\"gestall_" + res.getIdcorsoavviato() + "\">"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "\"/>"
                        + "</form>";

                switch (res.getStatocorso().getCodicestatocorso()) {
                    case "41": {
                        azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"AUTORIZZA AVVIO CORSO\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-success\" "
                                + "onclick=\"return approvacorso('" + res.getIdcorsoavviato() + "')\"><i class=\"fa fa-check-circle\"></i></button>"
                                + "<a href='ADM_rigettacorso.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-danger fan1\" data-bs-toggle=\"tooltip\" title=\"RIGETTA CORSO\" data-preload='false' "
                                + "\"><i class=\"fa fa-remove\"></i></a>";
                        break;
                    }
                    case "46": {
                        azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"AUTORIZZA CAMBIO SEDE\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-success\" "
                                + "onclick=\"return approvacambiosede('" + res.getIdcorsoavviato() + "')\"><i class=\"fa fa-check-circle\"></i></button>"
                                + "<a href='ADM_rigettacorso.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-danger fan1\" data-bs-toggle=\"tooltip\" title=\"RIGETTA CORSO\" data-preload='false' "
                                + "\"><i class=\"fa fa-remove\"></i></a>";
                        break;
                    }
                    case "48": {
                        azioni += "<a href='ADM_verificacommissione.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato()))
                                + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-warning fan1\" data-bs-toggle=\"tooltip\" title=\"VERIFICA COMMISSIONE ESAMI\" data-preload='false' "
                                + "\"><i class=\"fa fa-edit\"></i></a>";
                        break;
                    }
                    case "49": {
                        azioni
                                += "<a href='ADM_verificacommissione.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato()))
                                + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-primary fan1\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA COMMISSIONE ESAMI\" data-preload='false' "
                                + "\"><i class=\"fa fa-users\"></i></a>"
                                + "<a href='ADM_designapresidente.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato()))
                                + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-success fan1\" data-bs-toggle=\"tooltip\" title=\"DESIGNA PRESIDENTE\" data-preload='false' "
                                + "\"><i class=\"fa fa-user-alt\"></i></a>";

//                                + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"IMPOSTA STATO 'IN DESIGNAZIONE PRESIDENTE'\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-success\" "
//                                + "onclick=\"return impostadesignazione('" + res.getIdcorsoavviato() + "')\"><i class=\"fa fa-check-circle\"></i></button>";
                        break;
                    }
//                    case "50": {
//                        azioni += "<a href='ADM_designapresidente.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato()))
//                                + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
//                                + " class=\"btn btn-sm btn-bg-light btn-success fan1\" data-bs-toggle=\"tooltip\" title=\"DESIGNA PRESIDENTE\" data-preload='false' "
//                                + "\"><i class=\"fa fa-user-alt\"></i></a>";
//                        break;
//                    }
                    case "51": {
                        azioni
                                += "<a href='ADM_designapresidente.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato()))
                                + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-primary fan1\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA COMMISSIONE E PRESIDENTE\" data-preload='false' "
                                + "\"><i class=\"fa fa-users\"></i></a>"
                                + "<a href='ADM_notanomina.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato()))
                                + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-dark fan1\" data-bs-toggle=\"tooltip\" title=\"NOTA NOMINA\" data-preload='false' "
                                + "\"><i class=\"fa fa-file-alt\"></i></a>";
                        break;
                    }
                    case "52": {
                        azioni += "<a href='ADM_notanomina.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato()))
                                + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-dark fan1\" data-bs-toggle=\"tooltip\" title=\"NOTA NOMINA\" data-preload='false' "
                                + "\"><i class=\"fa fa-file-alt\"></i></a>";
                        break;
                    }
                }

                data_value.addProperty("azioni", azioni);
                data_value.addProperty("statovisual", res.getStatocorso().getNome());
                data_value.addProperty("sede", res.getCorsobase().getSedescelta().getIndirizzo() + " - " + res.getCorsobase().getSedescelta().getComune());

                CommissioneEsame com = ep.getCommissioneEsameCorso(res);

                if (com == null) {
                    data_value.addProperty("commissione", "");
                    data_value.addProperty("protrichiestacommissione", "");
                } else {
                    data_value.addProperty("commissione",
                            com.getTitolare1().getCognome() + " " + com.getTitolare1().getNome() + ", " + com.getTitolare2().getCognome() + " " + com.getTitolare2().getNome() + " - "
                            + com.getSostituto1().getCognome() + " " + com.getSostituto1().getNome() + ", " + com.getSostituto2().getCognome() + " " + com.getSostituto2().getNome()
                    );

                    if (com.getNumprotrichiesta() != null) {
                        data_value.addProperty("protrichiestacommissione", "PROT. " + com.getNumprotrichiesta() + " del " + sdf_PATTERNDATE4.format(com.getDataprotrichiesta()));
                    } else {
                        data_value.addProperty("protrichiestacommissione", "");
                    }
                }

                data_value.addProperty("presidente", res.getPresidentecommissione() == null ? "" : (res.getPresidentecommissione().getCognome()
                        + " " + res.getPresidentecommissione().getNome()));

//                data_value.addProperty("protnomina", res.getProtnomina() == null ? "" : res.getProtnomina());
//                data_value.addProperty("dataprotnomina", res.getDataprotnomina() == null ? "" : sdf_PATTERNDATE4.format(res.getDataprotnomina()));
                data.add(data_value);
                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());

        }
    }

    protected void list_corso_pres(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityOp ep = new EntityOp();

        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            String soggetto = getRequestValue(request, "soggetto");

            PresidenteCommissione pc = ((User) request.getSession().getAttribute("us_memory")).getPresidente();

            SoggettoProponente so;
            try {
                so = ep.getEm().find(SoggettoProponente.class, Utils.parseLongR(soggetto));
            } catch (Exception ex1) {
                so = null;
            }

            String statocorso = getRequestValue(request, "statocorso");
            String tipopercorso = getRequestValue(request, "tipopercorso");
            List<Corsoavviato> result = ep.list_corso_pres(so, tipopercorso, statocorso, pc);
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);

            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("stato", res.getStatocorso().getHtmldescr());
                data_value.addProperty("soggetto", res.getCorsobase().getSoggetto().getRAGIONESOCIALE());
                data_value.addProperty("id", res.getIdcorsoavviato());
                String nome = "<b>Tipologia Percorso: " + res.getCorsobase().getIstanza().getTipologiapercorso().getNometipologia() + "</b><br/><u>" + res.getCorsobase().getRepertorio().getDenominazione() + "</u>";
                data_value.addProperty("nome", nome);
                data_value.addProperty("datainizio", sdf_PATTERNDATE4.format(res.getDatainizio()));
                data_value.addProperty("datafine", sdf_PATTERNDATE4.format(res.getDatafine()));
                data_value.addProperty("datainserimento", sdf_PATTERNDATE5.format(res.getDatainserimento()));
                String azioni
                        = "<form action=\"US_showcorsoavviato.jsp\" method=\"POST\" target=\"_blank\">"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + res.getIdcorsoavviato() + "\"/>"
                        + "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA DETTAGLI CORSO\"data-preload='false'><i class=\"fa fa-file-text\"></i></button>"
                        + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"GESTIONE ALLEGATI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-secondary\""
                        + "onclick=\"return document.getElementById('gestall_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-file-clipboard\"></i></button>";

                switch (res.getStatocorso().getCodicestatocorso()) {
                    case "51": {
                        azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"GESTIONE ESAMI FINALI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-success\""
                                + "onclick=\"return document.getElementById('gestesami_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-edit\"></i></button>";
                        break;
                    }
                    case "52": {
                        azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"SCARICA PDF VERBALE\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-danger\""
                                + "onclick=\"return document.getElementById('pdfverbale_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"SCARICA ATTESTATI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-warning\""
                                + "onclick=\"return document.getElementById('attestati_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-file-archive\"></i></button>";
                        break;
                    }
                }

                azioni += "</form>"
                        + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"pdfverbale_" + res.getIdcorsoavviato() + "\">"
                        + "<input type=\"hidden\" name=\"type\" value=\"PDFVERBALE\"/>"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "\"/>"
                        + "</form>"
                        + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"attestati_" + res.getIdcorsoavviato() + "\">"
                        + "<input type=\"hidden\" name=\"type\" value=\"ATTESTATI\"/>"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "\"/>"
                        + "</form>"
                        + "<form action=\"US_allegaticorso.jsp\" method=\"POST\" target=\"_blank\" id=\"gestall_" + res.getIdcorsoavviato() + "\">"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "\"/>"
                        + "</form>"
                        + "<form action=\"PRE_gestioneesami.jsp\" method=\"POST\" target=\"_blank\" id=\"gestesami_" + res.getIdcorsoavviato() + "\">"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + String.valueOf(res.getIdcorsoavviato()) + "\"/>"
                        + "</form>";

                data_value.addProperty("azioni", azioni);
                data_value.addProperty("statovisual", res.getStatocorso().getNome());
                data_value.addProperty("presidente", res.getPresidentecommissione() == null ? "NON PRESENTE" : (res.getPresidentecommissione().getCognome()
                        + " " + res.getPresidentecommissione().getNome()));

                data_value.addProperty("protnomina", res.getProtnomina() == null ? "NON PRESENTE" : res.getProtnomina());
                data_value.addProperty("dataprotnomina", res.getDataprotnomina() == null ? "NON PRESENTE" : sdf_PATTERNDATE4.format(res.getDataprotnomina()));

                data.add(data_value);
                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());

        }
    }

    protected void list_corso_user(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityOp ep = new EntityOp();

        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            String statocorso = getRequestValue(request, "statocorso");
            String tipopercorso = getRequestValue(request, "tipopercorso");
            List<Corsoavviato> result = ep.list_corso_user(so, tipopercorso, statocorso);
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);

            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("stato", res.getStatocorso().getHtmldescr());
                data_value.addProperty("id", res.getIdcorsoavviato());
                String nome = "<b>Tipologia Percorso: " + res.getCorsobase().getIstanza().getTipologiapercorso().getNometipologia() + "</b><br/><u>" + res.getCorsobase().getRepertorio().getDenominazione() + "</u>";
                data_value.addProperty("nome", nome);
                data_value.addProperty("datainizio", sdf_PATTERNDATE4.format(res.getDatainizio()));
                data_value.addProperty("datafine", sdf_PATTERNDATE4.format(res.getDatafine()));
                data_value.addProperty("datainserimento", sdf_PATTERNDATE5.format(res.getDatainserimento()));
                String azioni = "<form action=\"US_showcorsoavviato.jsp\" method=\"POST\" target=\"_blank\">"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + res.getIdcorsoavviato() + "\"/>";

                switch (res.getStatocorso().getCodicestatocorso()) {
                    case "47": {
                        azioni += "<a href=\"US_richiedicommissione.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "\" data-fancybox data-type='iframe' "
                                + "data-bs-toggle=\"tooltip\" title=\"RICHIESTA NOMINA COMMISSIONE ESAMI FINAL\" data-preload='false' data-width='100%' data-height='100%' "
                                + "class=\"btn btn-sm btn-bg-light btn-warning fan1\"><i class=\"fa fa-envelope\"></i></a> | ";
                        break;
                    }
                    case "40":
                    case "42": {
                        azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"RICHIEDI AVVIO CORSO\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-success\" "
                                + "onclick=\"return sendcorso('" + res.getIdcorsoavviato() + "')\"><i class=\"fa fa-envelope\"></i></button> | ";
                        break;
                    }
                    case "52": {
                        azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"GESTIONE ESAMI FINALI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-success\""
                                + "onclick=\"return document.getElementById('gestesami_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-edit\"></i></button> | ";
//                        azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"SCARICA PDF VERBALE\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-danger\""
//                                + "onclick=\"return document.getElementById('pdfverbale_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
//                                + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"SCARICA ATTESTATI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-warning\""
//                                + "onclick=\"return document.getElementById('attestati_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-file-archive\"></i></button>";
                        break;
                    }
                    case "53": {
                        azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"SCARICA PDF VERBALE\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-danger\""
                                + "onclick=\"return document.getElementById('pdfverbale_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                + "<a href=\"US_.jsp?idcorso=" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "\" data-fancybox data-type='iframe' "
                                + "data-bs-toggle=\"tooltip\" title=\"RICHIESTA NOMINA COMMISSIONE ESAMI FINAL\" data-preload='false' data-width='100%' data-height='100%' "
                                + "class=\"btn btn-sm btn-bg-light btn-warning fan1\"><i class=\"fa fa-envelope\"></i></a> | ";
//+ "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"SCARICA ATTESTATI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-warning\""
                        //+ "onclick=\"return document.getElementById('attestati_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-file-archive\"></i></button> | ";
                        break;
                    }
                    default:
                        break;
                }
                azioni += "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA DETTAGLI CORSO\"data-preload='false'><i class=\"fa fa-file-text\"></i></button> | "
                        + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"GESTIONE ALLEGATI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-secondary\""
                        + "onclick=\"return document.getElementById('gestall_" + res.getIdcorsoavviato() + "').submit();\"><i class=\"fa fa-file-clipboard\"></i></button>"
                        + "</form>"
                        + "<form action=\"US_allegaticorso.jsp\" method=\"POST\" target=\"_blank\" id=\"gestall_" + res.getIdcorsoavviato() + "\">"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "\"/>"
                        + "</form>"
                        + "<form action=\"US_gestioneesami.jsp\" method=\"POST\" id=\"gestesami_" + res.getIdcorsoavviato() + "\">"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + String.valueOf(res.getIdcorsoavviato()) + "\"/>"
                        + "</form>"
                        + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"pdfverbale_" + res.getIdcorsoavviato() + "\">"
                        + "<input type=\"hidden\" name=\"type\" value=\"PDFVERBALE\"/>"
                        + "<input type=\"hidden\" name=\"idcorso\" value=\"" + Utils.enc_string(String.valueOf(res.getIdcorsoavviato())) + "\"/>"
                        + "</form>";

                data_value.addProperty("azioni", azioni);
                data_value.addProperty("statovisual", res.getStatocorso().getNome());
                data.add(data_value);
                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());

        }

    }

    protected void list_istanze_user(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityOp ep = new EntityOp();

        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            String statoistanza = getRequestValue(request, "statoistanza");
            String tipopercorso = getRequestValue(request, "tipopercorso");
            List<Istanza> result = ep.list_istanze_user(so, tipopercorso, statoistanza);
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);

            result.forEach(res -> {

                String labelallegati = "GESTIONE ALLEGATI";
                int maxcorsi = res.getTipologiapercorso().getMaxcorsi();
                List<Corso> c1 = ep.getCorsiIstanza(res);
                boolean addcorso = (maxcorsi > c1.size());
                boolean salvaistanza = true;
                boolean eliminaistanza = true;
                boolean modificacorso = true;
                boolean inviaistanza = false;
                boolean consultaistanza = false;

                if (!res.getStatocorso().getCodicestatocorso().equals("01")) {
                    addcorso = false;
                    salvaistanza = false;
                    modificacorso = false;
                }

                switch (res.getStatocorso().getCodicestatocorso()) {
                    case "02": {
                        inviaistanza = true;
                        consultaistanza = true;
                        break;
                    }
                    case "07":
                    case "08":
                    case "09": {
                        eliminaistanza = false;
                        consultaistanza = true;
                        break;
                    }
                    case "10": {
                        //SOCCORSO ISTRUTTORIO
                        eliminaistanza = false;
                        consultaistanza = true;
                        inviaistanza = true;
                        labelallegati = "GESTISCI SOCCORSO ISTRUTTORIO";
                        break;
                    }
                    default:
                        break;
                }

                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("stato", res.getStatocorso().getHtmlicon());
                data_value.addProperty("id", res.getIdistanza());

                String corsi = "<b>Tipologia Percorsi: " + res.getTipologiapercorso().getNometipologia() + "</b><br/><hr>";
                for (Corso cor : c1) {
                    corsi += "<u>" + cor.getRepertorio().getDenominazione() + "</u>- Edizioni: " + cor.getQuantitarichiesta() + "<br/>";

                    if (modificacorso) {
                        corsi += "<form action=\"US_programmacorsi.jsp\" method=\"POST\" target=\"_blank\">"
                                + "<input type=\"hidden\" name=\"idcorso\" value=\"" + Utils.enc_string(String.valueOf(cor.getIdcorso())) + "\"/>"
                                + cor.getStatocorso().getHtmlicon() + " | "
                                + "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"MODIFICA DETTAGLI CORSO\" data-preload='false'><i class=\"fa fa-edit\"></i></button>";

                        if (c1.size() > 1) {
                            corsi += " | <button type=\"button\"class=\"btn btn-sm btn-danger\" data-bs-toggle=\"tooltip\" "
                                    + "title=\"RIMUOVI CORSO DA ISTANZA\" data-preload='false'"
                                    + "onclick=\"return deletecorsofromistance('" + cor.getIdcorso() + "')\"><i class=\"fa fa-trash-arrow-up\"></i></button>";
                        }
                        corsi += "</form>";

                    }
                }

                data_value.addProperty("corsi", corsi);
                data_value.addProperty("data", res.getProtocollosoggettodata());

                String azioni = "<form action=\"US_showistanza.jsp\" method=\"POST\" target=\"_blank\">"
                        + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>";

                if (consultaistanza) {
                    azioni += "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ISTANZA\"data-preload='false'><i class=\"fa fa-file-text\"></i></button> | ";
                }

                if (addcorso) {
                    azioni += "<a href=\"US_addcorsoistanza.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\" data-fancybox data-type='iframe' "
                            + "data-bs-toggle=\"tooltip\" title=\"AGGIUNGI CORSO AD ISTANZA\" data-preload='false' data-width='100%' data-height='100%' "
                            + "class=\"btn btn-sm btn-bg-light btn-primary fan1\"><i class=\"fa fa-plus-circle\"></i></a> | ";
                }

                if (salvaistanza) {
                    azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"VERIFICA E SALVA ISTANZA\" data-preload='false' "
                            + "class=\"btn btn-sm btn-bg-light btn-success\" onclick=\"return saveistanza('" + res.getIdistanza() + "')\"><i class=\"fa fa-save\"></i></button> | ";
                } else if (inviaistanza) {
                    azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"INVIA ISTANZA\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-success\" "
                            + "onclick=\"return sendistanza('" + res.getIdistanza() + "')\"><i class=\"fa fa-envelope\"></i></button> |";
                }

                if (eliminaistanza) {
                    azioni += "<button type=\"button\"class=\"btn btn-sm btn-bg-light btn-danger\" data-bs-toggle=\"tooltip\" title=\"ELIMINA ISTANZA\" data-preload='false' " + ""
                            + "onclick=\"return deleteistanza('<%=is1.getIdistanza()%>')\"><i class=\"fa fa-remove\"></i></button> | ";
                }

                azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"" + labelallegati + "\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-secondary\""
                        + "onclick=\"return document.getElementById('gestall_" + res.getIdistanza() + "').submit();\"><i class=\"fa fa-file-clipboard\"></i></button>"
                        + "</form>"
                        + "<form action=\"US_gestioneallegati.jsp\" method=\"POST\" target=\"_blank\" id=\"gestall_" + res.getIdistanza() + "\">"
                        + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                        + "</form>";

                data_value.addProperty("azioni", azioni);
                data_value.addProperty("statovisual", res.getStatocorso().getNome());
                data.add(data_value);
                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());

        }

    }

    protected void list_istanze_adm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String statoistanza = getRequestValue(request, "statoistanza");
        String tipopercorso = getRequestValue(request, "tipopercorso");
        EntityOp ep = new EntityOp();
        List<Istanza> result;
        try {
            if (isAdmin(request.getSession())) {
                result = ep.list_istanze_adm(tipopercorso, statoistanza);
            } else {
                result = new ArrayList<>();
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
            result = new ArrayList<>();
        }
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("id", res.getIdistanza());
                data_value.addProperty("soggetto", res.getSoggetto().getRAGIONESOCIALE());
                String corsi = "<b>Tipologia Percorsi: " + res.getTipologiapercorso().getNometipologia() + "</b><br/><hr>";
                List<Corso> c1 = ep.getCorsiIstanza(res);
                if (res.getStatocorso().getCodicestatocorso().equals("08")) {
                    for (Corso cor : c1) {
                        if (cor.getStatocorso().getCodicestatocorso().equals("24")) {
                            corsi += "CODICE: " + cor.getIdentificativocorso() + "<br><u>" + cor.getRepertorio().getDenominazione() + "</u> - Edizioni: " + cor.getQuantitarichiesta() + "<br/>";
                        }
                    }
                } else {
                    for (Corso cor : c1) {
                        corsi += "<u>" + cor.getRepertorio().getDenominazione() + "</u>- Edizioni: " + cor.getQuantitarichiesta() + "<br/>";
                    }
                }
                data_value.addProperty("corsi", corsi);
                data_value.addProperty("protocollo", res.getProtocollosoggetto() + " " + res.getProtocollosoggettodata());
                data_value.addProperty("data", res.getDatainvio());
                data_value.addProperty("stato", res.getStatocorso().getHtmldescr());
                data_value.addProperty("statovisual", res.getStatocorso().getNome());

                String azioni = "<i class='fa fa-hourglass'></i>";

                switch (res.getStatocorso().getCodicestatocorso()) {
                    case "07": {
                        //DA GESTIRE
                        azioni = "<div class=\"p-2 min-w-150px btn-group btn-group-justified\" role=\"group\" aria-label=\"Basic example\">"
                                + "<form action=\"US_showistanza.jsp\" method=\"POST\" target=\"_blank\">"
                                + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                + "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ISTANZA PRESENTATA\" data-preload='false'>"
                                + "<i class=\"fa fa-file-text\"></i></button>"
                                + "<a href='ADM_allegati.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-secondary fan1\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ALLEGATI\" data-preload='false' "
                                + "\"><i class=\"fa fa-file-clipboard\"></i></a>"
                                //+ "<a href='ADM_protocollaistanza.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                //+ " class=\"btn btn-sm btn-bg-light btn-warning text-dark fan1\" data-bs-toggle=\"tooltip\" title=\"PROTOCOLLA ISTANZA\" data-preload='false' "
                                //+ "\"><i class=\"fa fa-stamp\"></i></a>"
                                + "&nbsp;&nbsp;"
                                + "<a href='ADM_approvaistanza.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-success fan1\" data-bs-toggle=\"tooltip\" title=\"APPROVA ISTANZA\" data-preload='false' "
                                + "\"><i class=\"fa fa-check\"></i></a>"
                                + "<a href='ADM_rigettaistanza.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-danger fan1\" data-bs-toggle=\"tooltip\" title=\"RIGETTA ISTANZA\" data-preload='false' "
                                + "\"><i class=\"fa fa-remove\"></i></a>"
                                + "</form></div>";
                        break;
                    }
                    case "09":
                    case "10": {
                        azioni = "<div class=\"p-2 min-w-150px btn-group btn-group-justified\" role=\"group\" aria-label=\"Basic example\">"
                                + "<form action=\"US_showistanza.jsp\" method=\"POST\" target=\"_blank\">"
                                + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                + "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ISTANZA PRESENTATA\" data-preload='false'>"
                                + "<i class=\"fa fa-file-text\"></i></button>"
                                + "<a href='ADM_allegati.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-secondary fan1\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ALLEGATI\" data-preload='false' "
                                + "\"><i class=\"fa fa-file-clipboard\"></i></a>"
                                + "</form></div>";
                        break;
                    }
                    case "08": {
                        //APPROVATA
                        azioni = "<div class=\"p-2 min-w-150px btn-group btn-group-justified\" role=\"group\" aria-label=\"Basic example\">"
                                + "<form action=\"US_showistanza.jsp\" method=\"POST\" target=\"_blank\">"
                                + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                + "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ISTANZA PRESENTATA\" data-preload='false'>"
                                + "<i class=\"fa fa-file-text\"></i></button>"
                                + "<a href='ADM_allegati.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-secondary fan1\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ALLEGATI\" data-preload='false' "
                                + "\"><i class=\"fa fa-file-clipboard\"></i></a>"
                                + "<br>";
                        if (res.getPathfirmato() != null) {
                            azioni
                                    += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA DECRETO AUT.\" data-preload='false' "
                                    + "class=\"btn btn-sm btn-bg-light btn-dark\" "
                                    + " onclick=\"return document.getElementById('generadecretotemplate1_" + res.getIdistanza()
                                    + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                    + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA DECRETO AUT.FIRMATO\" data-preload='false' "
                                    + "class=\"btn btn-sm btn-bg-light btn-success\" "
                                    + " onclick=\"return document.getElementById('scaricadecretofirmato1_" + res.getIdistanza()
                                    + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                    + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA DECRETO AUT. V2\" data-preload='false' "
                                    + "class=\"btn btn-sm btn-bg-light btn-danger\" "
                                    + " onclick=\"return document.getElementById('scaricadecretofto1_" + res.getIdistanza()
                                    + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                    + "<button type=\"button\"class=\"btn btn-sm btn-warning\"\n"
                                    + "data-bs-toggle=\"tooltip\" title=\"NOTIFICA SOGGETTO PROPONENTE\" data-preload='false' "
                                    + "onclick=\"return invianotificaddecreto('" + res.getIdistanza() + "')\" ><i class=\"fa fa-envelope\"></i></button>"
                                    + "</form>"
                                    + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"generadecretotemplate1_" + res.getIdistanza() + "\">"
                                    + "<input type=\"hidden\" name=\"type\" value=\"GENERADECRETOBASE\"/>"
                                    + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                    + "</form>"
                                    + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"scaricadecretofirmato1_" + res.getIdistanza() + "\">"
                                    + "<input type=\"hidden\" name=\"type\" value=\"SCARICADECRETOFIRMATO\"/>"
                                    + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                    + "</form>"
                                    + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"scaricadecretofto1_" + res.getIdistanza() + "\">"
                                    + "<input type=\"hidden\" name=\"type\" value=\"GENERADECRETODDSFTO\"/>"
                                    + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                    + "</form>";
                        } else {
                            azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"SCARICA DECRETO AUT.\" data-preload='false' "
                                    + "class=\"btn btn-sm btn-bg-light btn-dark\" "
                                    + " onclick=\"return document.getElementById('generadecretotemplate1_" + res.getIdistanza() + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                    + "<a href='ADM_uploaddecretofirmato.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza()))
                                    + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                    + " class=\"btn btn-sm btn-bg-light btn-warning fan1\" data-bs-toggle=\"tooltip\" title=\"UPLOAD DECRETO FIRMATO\" data-preload='false' "
                                    + "><i class=\"fa fa-upload\"></i></a>"
                                    + "</form>"
                                    + "<form action=\"ADM_allegati.jsp\" method=\"POST\" target=\"_blank\" id=\"gestall_" + res.getIdistanza() + "\">"
                                    + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                    + "</form>"
                                    + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"generadecretotemplate1_" + res.getIdistanza() + "\">"
                                    + "<input type=\"hidden\" name=\"type\" value=\"GENERADECRETOBASE\"/>"
                                    + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                    + "</form>";
                        }
                        azioni += "</div>";
                        break;
                    }
                    default:
                        break;
                }

                data_value.addProperty("azioni", azioni);
                data.add(data_value);

                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }

    }

    protected void list_altropersonale(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();

            List<Altropersonale> result = new EntityOp().list_all_AltroPersonale();

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
//                String pdf
//                        = "<a href='javascript:void(0)' onclick='return $(\"#frm\").submit();' class='btn btn-sm btn-outline-primary m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
//                        + "data-toggle='popover' data-placement='right' title='Visualizza File' data-content='Visualizza file documento.'><i class='far fa-file-pdf'></i></a>"
//                        + "<form id='frm_' target='_blank' method='POST' action='Operations'>"
//                        + "<input type='hidden' name='type' value='showPDF'/>"
//                        + "<input type='hidden' name='ido' value='' />"
//                        + "</form>";
//
//                String edit = "";
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("ruolo", res.getTipologia());
//                data_value.addProperty("stato", res.getEtichettastato());
                data_value.addProperty("cognome", res.getCognome());
                data_value.addProperty("nome", res.getNome());
                data_value.addProperty("cf", res.getCodicefiscale());
                data_value.addProperty("titolo", res.getTitolostudio());
                data_value.addProperty("profiloprof", res.getProfiloprof());
//                data_value.addProperty("azioni", "<i class='fa fa-hourglass'></i>");
                data.add(data_value);
                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }
    }

    protected void list_presidenti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();

            List<PresidenteCommissione> result = new EntityOp().findAll(PresidenteCommissione.class);

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("cognome", res.getCognome());
                data_value.addProperty("nome", res.getNome());
                data_value.addProperty("qualifica", res.getQualifica());
                data_value.addProperty("dipartimento", res.getDipartimento());
                data_value.addProperty("area", res.getAreaservizio());
                data_value.addProperty("stato", res.getStatopresidente().name());
                data_value.addProperty("azioni", "<i class='fa fa-hourglass'></i>");
                data.add(data_value);
                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }
    }

    protected void list_docenti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();

            List<Docente> result = new EntityOp().findAll(Docente.class);

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
//                String pdf
//                        = "<a href='javascript:void(0)' onclick='return $(\"#frm\").submit();' class='btn btn-sm btn-outline-primary m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
//                        + "data-toggle='popover' data-placement='right' title='Visualizza File' data-content='Visualizza file documento.'><i class='far fa-file-pdf'></i></a>"
//                        + "<form id='frm_' target='_blank' method='POST' action='Operations'>"
//                        + "<input type='hidden' name='type' value='showPDF'/>"
//                        + "<input type='hidden' name='ido' value='' />"
//                        + "</form>";
//
//                String edit = "";
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("ruolo", res.getTipologia());
//                data_value.addProperty("stato", res.getEtichettastato());
                data_value.addProperty("cognome", res.getCognome());
                data_value.addProperty("nome", res.getNome());
                data_value.addProperty("cf", res.getCodicefiscale());
                data_value.addProperty("titolo", res.getTitolostudio());
                data_value.addProperty("profiloprof", res.getProfiloprof());
//                data_value.addProperty("azioni", "<i class='fa fa-hourglass'></i>");
                data.add(data_value);
                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }
    }

    protected void list_enti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<EnteStage> result;
        AtomicInteger modify = new AtomicInteger(0);
        try {
            if (isAdmin(request.getSession())) {
                result = new EntityOp().findAll(EnteStage.class);
            } else {
                SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
                result = new EntityOp().getEntiStageSoggetto(so);
                modify.addAndGet(1);
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
            result = new ArrayList<>();
        }

        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("idente", res.getIdentestage());
                data_value.addProperty("soggetto", res.getSoggetto().getRAGIONESOCIALE());
                data_value.addProperty("ragionesociale", res.getRAGIONESOCIALE());
                data_value.addProperty("piva", res.getPARTITAIVA());
                data_value.addProperty("rapleg", res.getRap_cognome() + " " + res.getRap_nome());
                data_value.addProperty("sedeleg",
                        res.getSedelegale().getIndirizzo()
                        + " " + res.getSedelegale().getCap()
                        + " " + res.getSedelegale().getComune()
                        + " (" + res.getSedelegale().getProvincia() + ")");
                String azioni
                        = "<a href=\"US_showente.jsp?idente=" + Utils.enc_string(String.valueOf(res.getIdentestage())) + "\" data-fancybox data-type='iframe' "
                        + "data-bs-toggle=\"tooltip\" title=\"DETTAGLI\" "
                        + "data-preload='false' class=\"btn btn-sm btn-bg-light btn-primary fan1\">"
                        + "<i class=\"fa fa-user\"></i></a> | ";
                if (modify.get() > 0) {
                    azioni += "<a href=\"US_aggiungisedeente.jsp?idente=" + Utils.enc_string(String.valueOf(res.getIdentestage())) + "\" data-fancybox data-type='iframe' "
                            + "data-bs-toggle=\"tooltip\" title=\"AGGIUNGI SEDE TIROCINIO/STAGE\" "
                            + "data-preload='false' class=\"btn btn-sm btn-bg-light btn-warning fan1\">"
                            + "<i class=\"fa fa-plus\"></i></a> | ";
                }
                azioni += "<a href=\"US_allegatiente.jsp?idente=" + Utils.enc_string(String.valueOf(res.getIdentestage())) + "\" data-fancybox data-type='iframe' "
                        + "data-bs-toggle=\"tooltip\" title=\"GESTIONE ALLEGATI\" "
                        + "data-preload='false' class=\"btn btn-sm btn-bg-light btn-secondary fan1\">"
                        + "<i class=\"fa fa-file-clipboard\"></i></a>";
                //+ " | <button type=\"button\"class=\"btn btn-sm btn-bg-light btn-danger\""
                //+ " data-bs-toggle=\"tooltip\" title=\"DISABILITA ENTE\""
                //+ " data-preload='false'"
                //+ " onclick=\"return disabilitaente('" + res.getIdentestage() + "','" + res.getPARTITAIVA() + "')\"><i class=\"fa fa-remove\"></i>"
                //+ " </button>";

                data_value.addProperty("azioni", azioni);
                data.add(data_value);

                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }

    }

    protected void list_allievi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Allievi> result;
        try {

            if (isAdmin(request.getSession())) {
                result = new EntityOp().findAll(Allievi.class);
            } else {
                SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
                result = new EntityOp().getAllieviSoggetto(so);
            }

        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
            result = new ArrayList<>();
        }

        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("stato", res.getEtichettastato());
                data_value.addProperty("idallievo", res.getIdallievi());
                data_value.addProperty("cognome", res.getCognome());
                data_value.addProperty("nome", res.getNome());
                data_value.addProperty("cf", res.getCodicefiscale());
                data_value.addProperty("data", sdf_PATTERNDATE4.format(res.getDatanascita()));
                data_value.addProperty("email", res.getEmail());
                data_value.addProperty("telefono", res.getTelefono());
                data_value.addProperty("soggetto", res.getSoggetto().getRAGIONESOCIALE());

                String azioni
                        = "<a href=\"US_showallievo.jsp?idallievo=" + Utils.enc_string(String.valueOf(res.getIdallievi())) + "\" data-fancybox data-type='iframe' "
                        + "data-bs-toggle=\"tooltip\" title=\"DETTAGLI\" "
                        + "data-preload='false' class=\"btn btn-sm btn-bg-light btn-primary fan1\">"
                        + "<i class=\"fa fa-user\"></i></a> | "
                        + "<a href=\"US_allegatiallievi.jsp?idallievo=" + Utils.enc_string(String.valueOf(res.getIdallievi())) + "\" data-fancybox data-type='iframe' "
                        + "data-bs-toggle=\"tooltip\" title=\"GESTIONE ALLEGATI\" "
                        + "data-preload='false' class=\"btn btn-sm btn-bg-light btn-secondary fan1\">"
                        + "<i class=\"fa fa-file-clipboard\"></i></a>";

                if (res.getStatoallievo().equals(Stati.CHECK)) {
                    azioni += " | <button type=\"button\"class=\"btn btn-sm btn-bg-light btn-danger\""
                            + " data-bs-toggle=\"tooltip\" title=\"ELIMINA ANAGRAFICA ALLIEVO\""
                            + " data-preload='false'"
                            + " onclick=\"return rimuoviallievo('" + res.getIdallievi() + "','" + res.getCodicefiscale() + "')\"><i class=\"fa fa-remove\"></i>"
                            + " </button>";
                }

                data_value.addProperty("azioni", azioni);
                data_value.addProperty("statovisual", Utils.getVisualstato(res.getStatoallievo()));
                data.add(data_value);

                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }
    }

    protected void list_sedi_soggetto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Sede> result;
        try {

            if (isAdmin(request.getSession())) {
                result = new EntityOp().findAll(Sede.class);
                result = result.stream().filter(f1 -> f1.getSoggetto() != null).collect(Collectors.toList());
            } else {
                SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
                result = so.getSediformazione();
            }

        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
            result = new ArrayList<>();
        }

        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("stato", res.getEtichettastato());
                data_value.addProperty("soggetto", res.getSoggetto().getRAGIONESOCIALE());
                data_value.addProperty("tipo", res.getTipo().getNome());
                data_value.addProperty("indirizzo", res.getIndirizzo());
                data_value.addProperty("cap", res.getCap());
                data_value.addProperty("comune", res.getComune());
                data_value.addProperty("provincia", res.getProvincia());
                data_value.addProperty("statovisual", Utils.getVisualstato(res.getStatosede()));
                data.add(data_value);
                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }
    }

    protected void BO_LIST_TIPOLOGIAPERCORSO(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();

            List<Tipologia_Percorso> result = (List<Tipologia_Percorso>) new EntityOp().findAll(Tipologia_Percorso.class);

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {

                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("tipo", res.getTipocorso().name());
                data_value.addProperty("descrizione", res.getNometipologia());
                data_value.addProperty("datastart", new DateTime(res.getDatastart()).toString(PATTERNDATE4));
                data_value.addProperty("dataend", new DateTime(res.getDataend()).toString(PATTERNDATE4));
                data_value.addProperty("stato", res.getEtichettastato());
                data_value.addProperty("maxcorsi", res.getMaxcorsi());
                data_value.addProperty("maxedizioni", res.getMaxedizioni());
                String azioni = "<a href=\"ADM_edittipopercorso.jsp?idtipop=" + Utils.enc_string(String.valueOf(res.getIdtipopercorso())) + "\" data-fancybox data-type='iframe' "
                        + "data-bs-toggle=\"tooltip\" title=\"MODIFICA TIPOLOGIA PERCORSO\" data-preload='false' data-width='100%' data-height='100%' "
                        + "class=\"btn btn-sm btn-bg-light btn-warning fan1\"><i class=\"fa fa-edit\"></i></a>";
                data_value.addProperty("azioni", azioni);
                data.add(data_value);

                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String type = request.getParameter("type");
            switch (type) {
                case "list_docenti":
                    list_docenti(request, response);
                    break;
                case "list_presidenti":
                    list_presidenti(request, response);
                    break;
                case "list_altropersonale":
                    list_altropersonale(request, response);
                    break;
                case "list_allievi":
                    list_allievi(request, response);
                    break;
                case "list_enti":
                    list_enti(request, response);
                    break;
                case "list_sedi_soggetto":
                    list_sedi_soggetto(request, response);
                    break;
                case "list_istanze_adm":
                    list_istanze_adm(request, response);
                    break;
                case "list_istanze_user":
                    list_istanze_user(request, response);
                    break;
                case "list_corso_user":
                    list_corso_user(request, response);
                    break;
                case "list_corso_admin":
                    list_corso_admin(request, response);
                    break;
                case "list_corso_pres":
                    list_corso_pres(request, response);
                    break;
                case "BO_LIST_TIPOLOGIAPERCORSO":
                    BO_LIST_TIPOLOGIAPERCORSO(request, response);
                    break;
                case "LISTCORSIISTANZA":
                    LISTCORSIISTANZA(request, response);
                    break;
                case "LISTDOCENTICORSO":
                    LISTDOCENTICORSO(request, response);
                    break;
                case "LISTDOCENTICORSOPERCOMMISSIONE":
                    LISTDOCENTICORSOPERCOMMISSIONE(request, response);
                    break;
                case "GETDIRETTORE":
                    GETDIRETTORE(request, response);
                    break;
                case "SELECTDOCENTE":
                    SELECTDOCENTE(request, response);
                    break;
                case "LISTSEDESTAGE":
                    LISTSEDESTAGE(request, response);
                    break;
                default: {
                    String p = request.getContextPath();
                    request.getSession().invalidate();
                    redirect(request, response, p);
                    break;
                }
            }
        } catch (Exception ex) {
            EntityOp.trackingAction("SERVICE", estraiEccezione(ex));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String p = request.getContextPath();
        request.getSession().invalidate();
        redirect(request, response, p);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
