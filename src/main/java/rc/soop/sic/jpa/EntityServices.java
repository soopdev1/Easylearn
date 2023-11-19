/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 *
 * @author Administrator
 */
public class EntityServices {
    
    EntityManager entityManager;
 
    public EntityServices(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
 
    @Transactional
    public long saveCertificati(CertificatiCompetenze cc){
        this.entityManager.persist(cc);
        
        return cc.getNumerocertificato();
    }

}
