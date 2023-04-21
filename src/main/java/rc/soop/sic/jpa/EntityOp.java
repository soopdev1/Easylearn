/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import static rc.soop.sic.Utils.convmd5;

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
        this.em.getTransaction().commit();
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
        TypedQuery q = em.createNamedQuery("user.UsernamePwd", User.class);
        q.setParameter("username", user);
        q.setParameter("password", convmd5(pwd));
        return q.getResultList().isEmpty() ? null : (User) q.getSingleResult();
    }

    public Istanza getIstanza(SoggettoProponente s, String codice) {
        TypedQuery q = em.createNamedQuery("istanza.codice", Istanza.class);
        q.setParameter("soggetto", s);
        q.setParameter("codice", codice);
        return q.getResultList().isEmpty() ? null : (Istanza) q.getSingleResult();
    }

    public Istanza getIstanza(String codice) {
        TypedQuery q = em.createNamedQuery("istanza.onlycodice", Istanza.class);
        q.setParameter("codice", codice);
        return q.getResultList().isEmpty() ? null : (Istanza) q.getSingleResult();
    }

    public Istanza getIstanzaWaiting(SoggettoProponente s) {
        TypedQuery q = em.createNamedQuery("istanza.soggettowaiting", Istanza.class);
        q.setParameter("soggetto", s);
        q.setMaxResults(1);
        return q.getResultList().isEmpty() ? null : (Istanza) q.getSingleResult();
    }

    public List<Corso> getCorsiIstanza(Istanza is) {
        TypedQuery q = em.createNamedQuery("corso.istanza", Istanza.class);
        q.setParameter("soggetto", is.getSoggetto());
        q.setParameter("codiceistanza", is.getCodice());
        return (List<Corso>) q.getResultList();
    }

    public List<Istanza> getIstanzedaGestire() {
        TypedQuery q = em.createNamedQuery("istanza.dagestire", Istanza.class);
        return (List<Istanza>) q.getResultList();
    }

    public List<Istanza> getIstanzeGestite() {
        TypedQuery q = em.createNamedQuery("istanza.gestite", Istanza.class);
        return (List<Istanza>) q.getResultList();
    }

    public List<Istanza> getIstanzeAccettateAvvioCorsi(HttpSession session) {
        SoggettoProponente so = ((User) session.getAttribute("us_memory")).getSoggetto();
        TypedQuery q = em.createNamedQuery("istanza.listaaccettate", Istanza.class);
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
        TypedQuery q = em.createNamedQuery("corsoavviato.soggetto", Corsoavviato.class);
        q.setParameter("soggetto", so);
        return (List<Corsoavviato>) q.getResultList();
    }

    public List<Corsoavviato> getCorsiAvviati_Admin() {
        TypedQuery q = em.createNamedQuery("corsoavviato.stato", Corsoavviato.class);
        CorsoStato c1 = new CorsoStato();
        c1.setCodice("20");
        q.setParameter("stato", c1);
        return (List<Corsoavviato>) q.getResultList();
    }

    public List<Corsoavviato> getCorsiConclusi_Admin() {
        TypedQuery q = em.createNamedQuery("corsoavviato.stato", Corsoavviato.class);
        CorsoStato c1 = new CorsoStato();
        c1.setCodice("21");
        q.setParameter("stato", c1);
        return (List<Corsoavviato>) q.getResultList();
    }

}
