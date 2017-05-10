package org.demoiselle.livraria.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author 70744416353
 */
public class TenantDAO {

    //@PersistenceContext(unitName = "TenantPU")
    private EntityManager em;

    public void createSchema(String script) {
        em.createNativeQuery(script).executeUpdate();
    }

}
