/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import static rc.soop.sic.Utils.convmd5;
import static rc.soop.sic.Utils.estraiEccezione;

/**
 *
 * @author raf
 */
public class EntityOp {

    EntityManagerFactory emf;
    EntityManager em;

    public EntityOp() {
        this.emf = Persistence.createEntityManagerFactory("easyle");
        this.em = this.emf.createEntityManager();
        this.em.clear();
    }

    public EntityManager getEm() {
        return em;
    }

    public void begin() {
        this.em.getTransaction().begin();
    }

    public void persist(Object o) {
        this.em.persist(o);
    }

    public void merge(Object o) {
        this.em.merge(o);
    }

    public void commit() {
        try {
            this.em.getTransaction().commit();
        } catch (Exception ex0) {
            ex0.printStackTrace();
        }
    }

    public void rollBack() {
        if (this.em.getTransaction().isActive()) {
            this.em.getTransaction().rollback();
        }
    }

    public void remove(Object o) {
        this.em.remove(o);
    }

    public void flush() {
        this.em.flush();
    }

    public void close() {
        this.em.close();
        this.emf.close();
    }

    public List findAll(Class c) {
        return this.em.createQuery("Select a FROM " + c.getSimpleName() + " a", c).getResultList();
    }

    public boolean pageaccess(String gruppo, String page) {
        Pages p = this.em.find(Pages.class, page);
        StringTokenizer st = new StringTokenizer(p == null ? "" : p.getPermessi(), "-");
        while (st.hasMoreTokens()) {
            if (gruppo.equals(st.nextToken())) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String user, String pwd) {
        TypedQuery q = this.em.createNamedQuery("user.UsernamePwd", User.class);
        q.setParameter("username", user);
        q.setParameter("password", convmd5(pwd));
        return q.getResultList().isEmpty() ? null : (User) q.getSingleResult();
    }

    public Istanza getIstanza(SoggettoProponente s, String codice) {
        TypedQuery q = this.em.createNamedQuery("istanza.codice", Istanza.class);
        q.setParameter("soggetto", s);
        q.setParameter("codice", codice);
        return q.getResultList().isEmpty() ? null : (Istanza) q.getSingleResult();
    }

    public Istanza getIstanza(String codice) {
        TypedQuery q = this.em.createNamedQuery("istanza.onlycodice", Istanza.class);
        q.setParameter("codice", codice);
        return q.getResultList().isEmpty() ? null : (Istanza) q.getSingleResult();
    }

    public Istanza getIstanzaWaiting(SoggettoProponente s) {
        TypedQuery q = this.em.createNamedQuery("istanza.soggettowaiting", Istanza.class);
        q.setParameter("soggetto", s);
        q.setMaxResults(1);
        return q.getResultList().isEmpty() ? null : (Istanza) q.getSingleResult();
    }

    public List<Sede> getSediStage(EnteStage es) {
        TypedQuery q = this.em.createNamedQuery("entestage.sediformative", Sede.class);
        q.setParameter("entestage", es);
        q.setParameter("tipo", this.em.find(TipoSede.class, 4L));
        return (List<Sede>) q.getResultList();
    }

    public List<Corso> getCorsiIstanza(Istanza is) {
        TypedQuery q = this.em.createNamedQuery("corso.istanza", Istanza.class);
        q.setParameter("codiceistanza", is);
        return (List<Corso>) q.getResultList();
    }

    public List<Istanza> getIstanzedaGestire() {
        TypedQuery q = this.em.createNamedQuery("istanza.dagestire", Istanza.class);
        return (List<Istanza>) q.getResultList();
    }

    public List<Istanza> getIstanzeGestite() {
        TypedQuery q = this.em.createNamedQuery("istanza.gestite", Istanza.class);
        return (List<Istanza>) q.getResultList();
    }

    public List<Istanza> getIstanzeAccettateAvvioCorsi(HttpSession session) {
        SoggettoProponente so = ((User) session.getAttribute("us_memory")).getSoggetto();
        TypedQuery q = this.em.createNamedQuery("istanza.listaaccettate", Istanza.class);
        q.setParameter("soggetto", so);
        return (List<Istanza>) q.getResultList();
    }

    public static List<Corsoavviato> getCorsiAvviati_pres() {
        try {
            List<Corsoavviato> corsi = ((List<Corsoavviato>) new EntityOp().findAll(Corsoavviato.class)).stream()
                    .filter(c1 -> c1.getPresidentecommissione() != null && c1.getPresidentecommissione().equals("A1")).collect(Collectors.toList());
            return corsi;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<Corsoavviato> getCorsiAvviati(HttpSession session) {
        SoggettoProponente so = ((User) session.getAttribute("us_memory")).getSoggetto();
        TypedQuery q = this.em.createNamedQuery("corsoavviato.soggetto", Corsoavviato.class);
        q.setParameter("soggetto", so);
        return (List<Corsoavviato>) q.getResultList();
    }

    public List<Corsoavviato> getCorsiAvviati_Admin() {
        TypedQuery q = this.em.createNamedQuery("corsoavviato.stato", Corsoavviato.class);
        CorsoStato c1 = new CorsoStato();
        c1.setCodicestatocorso("20");
        q.setParameter("stato", c1);
        return (List<Corsoavviato>) q.getResultList();
    }

    public List<Corsoavviato> getCorsiConclusi_Admin() {
        TypedQuery q = this.em.createNamedQuery("corsoavviato.stato", Corsoavviato.class);
        CorsoStato c1 = new CorsoStato();
        c1.setCodicestatocorso("21");
        q.setParameter("stato", c1);
        return (List<Corsoavviato>) q.getResultList();
    }

    public static void trackingAction(String user, String azione) {
        try {
            Track t1 = new Track();
            t1.setAzione(azione);
            t1.setUser(user);
            EntityOp e1 = new EntityOp();
            e1.begin();
            e1.persist(t1);
            e1.commit();
            e1.close();
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }
    }

    public List<Istanza> getIstanzeSoggetto(SoggettoProponente s) {
        TypedQuery q = this.em.createNamedQuery("istanza.soggetto", Istanza.class);
        q.setParameter("soggetto", s);
        return (List<Istanza>) q.getResultList();
    }

    public List<Allievi> getAllieviSoggetto(SoggettoProponente s) {
        TypedQuery q = this.em.createNamedQuery("allievi.soggetto", Allievi.class);
        q.setParameter("soggetto", s);
        q.setParameter("inattivo", Stati.INATTIVO);
        return (List<Allievi>) q.getResultList();
    }

    public List<EnteStage> getEntiStageSoggetto(SoggettoProponente s) {
        TypedQuery q = this.em.createNamedQuery("entestage.soggetto", EnteStage.class);
        q.setParameter("soggetto", s);
        return (List<EnteStage>) q.getResultList();
    }

    public boolean esisteAllievoCF(String codicefiscale) {
        TypedQuery q = this.em.createNamedQuery("allievi.attivi.cf", Allievi.class);
        q.setParameter("codicefiscale", codicefiscale);
        q.setParameter("inattivo", Stati.INATTIVO);
        q.setMaxResults(1);
        return !q.getResultList().isEmpty();
    }

    public boolean esisteEnteStageSoggetto(SoggettoProponente s, String partitaiva) {
        TypedQuery q = this.em.createNamedQuery("entestage.soggetto.pi", EnteStage.class);
        q.setParameter("soggetto", s);
        q.setParameter("partitaiva", partitaiva);
        q.setMaxResults(1);
        return !q.getResultList().isEmpty();
    }

    public Tipologia_Percorso getTipoPercorsoIstanza(Istanza is) {
        try {
            TypedQuery q = this.em.createNamedQuery("corso.istanza", Istanza.class);
            q.setParameter("codiceistanza", is);
            List<Corso> lc = (List<Corso>) q.getResultList();
            for (Corso c1 : lc) {
                return c1.getTipologiapercorso();
            }
        } catch (Exception e) {
        }
        return null;
    }

    public List<Lingua> getLingue() {
        TypedQuery q = this.em.createNamedQuery("lingua.order", Lingua.class);
        return (List<Lingua>) q.getResultList();
    }

    public List<Scheda_Attivita> repertorio_completo_scheda() {
        TypedQuery q = this.em.createNamedQuery("schedaattivita.all", Scheda_Attivita.class);
        return (List<Scheda_Attivita>) q.getResultList();
    }

    public List<Calendario_Formativo> calendario_formativo_corso(Corso c) {
        TypedQuery q = this.em.createNamedQuery("calendarioformativo.corso", Calendario_Formativo.class);
        q.setParameter("corsodiriferimento", c);
        return (List<Calendario_Formativo>) q.getResultList();
    }

    public List<Calendario_Formativo> calendario_formativo_corso_solomoduli(Corso c) {
        TypedQuery q = this.em.createNamedQuery("calendarioformativo.corsosolomoduli", Calendario_Formativo.class);
        q.setParameter("corsodiriferimento", c);
        return (List<Calendario_Formativo>) q.getResultList();
    }

    public List<Calendario_Formativo> calendario_formativo_corso_lezioni(Corso c) {
        TypedQuery q = this.em.createNamedQuery("calendarioformativo.pianificare", Calendario_Formativo.class);
        q.setParameter("corsodiriferimento", c);
        return (List<Calendario_Formativo>) q.getResultList();
    }

    public List<Competenze> competenze_correlate(Repertorio r) {
        TypedQuery q = this.em.createNamedQuery("competenze.repertorio", Competenze.class);
        q.setParameter("repertorio", r);
        return (List<Competenze>) q.getResultList();
    }

    public User getUserbyUSR(String username) {
        TypedQuery q = this.em.createNamedQuery("user.byUsername", User.class);
        q.setParameter("username", username);
        q.setMaxResults(1);
        return q.getResultList().isEmpty() ? null : (User) q.getSingleResult();
    }

    public Certificazione getCertif(String nome) {
        TypedQuery q = this.em.createNamedQuery("cert.name", Certificazione.class);
        q.setParameter("nome", nome);
        q.setMaxResults(1);
        return q.getResultList().isEmpty() ? null : (Certificazione) q.getSingleResult();
    }

    public IncrementalCorso getIC(Corso c1) {
        TypedQuery q = this.em.createNamedQuery("inc.corso", IncrementalCorso.class);
        q.setParameter("corso", c1);
        q.setMaxResults(1);
        return q.getResultList().isEmpty() ? null : (IncrementalCorso) q.getSingleResult();
    }

    public IstatCode getComuneCF(String codicecf) {
        TypedQuery q = this.em.createNamedQuery("istat.codice", IstatCode.class);
        q.setParameter("codicecf", codicecf);
        q.setMaxResults(1);
        return q.getResultList().isEmpty() ? null : (IstatCode) q.getSingleResult();
    }
    
    public TemplateDecretoAUT getContentTemplateDescretoAUT() {
        TypedQuery q = this.em.createNamedQuery("template.decreto.aut", TemplateDecretoAUT.class);
        q.setMaxResults(1);
        return q.getResultList().isEmpty() ? null : (TemplateDecretoAUT) q.getSingleResult();
    }

    public List<Moduli_Docenti> list_moduli(Calendario_Formativo c1) {
        TypedQuery q = this.em.createNamedQuery("md.elencobycalendarioformativo", Moduli_Docenti.class);
        q.setParameter("moduloformativo", c1);
        return (List<Moduli_Docenti>) q.getResultList();
    }

    public List<Information> list_info(Istanza is1) {
        TypedQuery q = this.em.createNamedQuery("info.istanza", Information.class);
        q.setParameter("istanza", is1);
        return (List<Information>) q.getResultList();
    }

    public List<Allegati> list_allegati(Istanza is1, Corso c1, Corsoavviato c2, Docente d1, Allievi a1, EnteStage es1) {
        List<Allegati> elenco = new ArrayList<>();       
        if (is1 != null) {
            try {
                TypedQuery q = this.em.createNamedQuery("allegati.istanza.ok", Allegati.class);
                q.setParameter("istanza", is1);
                elenco.addAll((List<Allegati>) q.getResultList());
            } catch (Exception ex0) {
                trackingAction("SERVICE", estraiEccezione(ex0));
            }
        } else if (es1 != null) {
            try {
                TypedQuery q = this.em.createNamedQuery("allegati.entestage.ok", Allegati.class);
                q.setParameter("entestage", es1);
                elenco.addAll((List<Allegati>) q.getResultList());
            } catch (Exception ex0) {
                trackingAction("SERVICE", estraiEccezione(ex0));
            }
        } else if (a1 != null) {
            try {
                TypedQuery q = this.em.createNamedQuery("allegati.allievi.ok", Allegati.class);
                q.setParameter("allievi", a1);
                elenco.addAll((List<Allegati>) q.getResultList());
            } catch (Exception ex0) {
                trackingAction("SERVICE", estraiEccezione(ex0));
            }
        }
        return elenco;
    }

    public List<Docente> list_docenti_moduli(List<Docente> eldoc, List<Calendario_Formativo> calendar) {

        List<Docente> out = new ArrayList<>();
        try {
            TypedQuery q = this.em.createNamedQuery("md.elenco", Moduli_Docenti.class);
            for (Docente d1 : eldoc) {
                for (Calendario_Formativo c1 : calendar) {
                    q.setParameter("docente", d1);
                    q.setParameter("moduloformativo", c1);
                    List<Moduli_Docenti> res = (List<Moduli_Docenti>) q.getResultList();
                    if (!res.isEmpty()) {

                        List<Moduli_Docenti> def = d1.getElencomoduli() == null ? new ArrayList<>() : d1.getElencomoduli();
                        def.addAll(res);
                        d1.setElencomoduli(def);
                        if (!out.contains(d1)) {
                            out.add(d1);
                        }
//                        } else {
//
//                        }

                    }
                }
            }
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }

        return out;
    }

    public List<Istanza> list_istanze_adm(String tipologiapercorso, String statoistanza) {
        HashMap<String, Object> param = new HashMap<>();
        String sql = "SELECT i FROM Istanza i  WHERE i.statocorso.codicestatocorso IN ('07','08','09','10') ";

        if (!tipologiapercorso.equals("")) {
            sql += !sql.toUpperCase().contains("WHERE") ? "WHERE " : " AND ";
            sql += "i.tipologiapercorso.idtipopercorso = :tipologiapercorso";
            param.put("tipologiapercorso", Long.valueOf(tipologiapercorso));
        }
        if (!statoistanza.equals("")) {
            sql += !sql.toUpperCase().contains("WHERE") ? "WHERE " : " AND ";
            sql += "i.statocorso.codicestatocorso = :statoistanza";
            param.put("statoistanza", statoistanza);
        }
        TypedQuery<Istanza> q = this.em.createQuery(sql, Istanza.class);

        if (param.isEmpty()) {
            q.setMaxResults(500);
        }

        param.entrySet().forEach(m -> {
            q.setParameter(m.getKey(), m.getValue());
        });

        return q.getResultList().isEmpty() ? new ArrayList() : (List<Istanza>) q.getResultList();
    }

    public List<Istanza> list_istanze_user(SoggettoProponente sp, String tipologiapercorso, String statoistanza) {
        HashMap<String, Object> param = new HashMap<>();
        String sql = "SELECT i FROM Istanza i WHERE i.soggetto=:soggetto ";
        param.put("soggetto", sp);

        if (!tipologiapercorso.equals("")) {
            sql += !sql.toUpperCase().contains("WHERE") ? "WHERE " : " AND ";
            sql += "i.tipologiapercorso.idtipopercorso = :tipologiapercorso";
            param.put("tipologiapercorso", Long.valueOf(tipologiapercorso));
        }
        if (!statoistanza.equals("")) {
            sql += !sql.toUpperCase().contains("WHERE") ? "WHERE " : " AND ";
            sql += "i.statocorso.codicestatocorso = :statoistanza";
            param.put("statoistanza", statoistanza);
        }
        sql += " ORDER BY i.idistanza DESC";

        TypedQuery<Istanza> q = this.em.createQuery(sql, Istanza.class);

        if (param.isEmpty()) {
            q.setMaxResults(500);
        }

        param.entrySet().forEach(m -> {
            q.setParameter(m.getKey(), m.getValue());
        });

        return q.getResultList().isEmpty() ? new ArrayList() : (List<Istanza>) q.getResultList();
    }

}
