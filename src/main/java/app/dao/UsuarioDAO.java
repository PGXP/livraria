/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dao;

import app.entity.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.demoiselle.jee.persistence.crud.AbstractDAO;

/**
 *
 * @author gladson
 */
public class UsuarioDAO extends AbstractDAO<Usuario, Long> {

    @PersistenceContext(unitName = "TenantPU")
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
