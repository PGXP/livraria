/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package app.dao;

import app.tenant.Tenant;
import app.tenant.Sgdb;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import org.demoiselle.jee.persistence.crud.AbstractDAO;

/**
 * This class contains the methods required to interact with Tenant entity in
 * database.
 *
 * @author SERPRO
 *
 */
public class TenantDAO extends AbstractDAO<Tenant, Long> {

    private DataSource dataSource;

    @Inject
    private SgdbDAO sgdbDAO;

    @PersistenceContext(unitName = "MasterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em.getEntityManagerFactory().createEntityManager();
    }

    /**
     * Find Tenant by name
     *
     * @param name Tenant name
     * @return Tenant
     */
    public Tenant findByName(String name) {
        Tenant tenant = getEntityManager().createNamedQuery("Tenant.findByName", Tenant.class)
                .setParameter("name", name).getSingleResult();

        return tenant;
    }

    @Override
    public Tenant persist(Tenant tenant) {

        Connection conn = null;

        // Infos of Config
        String createCommand = "CREATE DATABASE IF NOT EXISTS";
        String setCommand = "USE";
        String masterDatabase = "master";

        try {

            // Create Schema
            final Context init = new InitialContext();
            dataSource = (DataSource) init.lookup("java:jboss/datasources/MasterDS");

            conn = dataSource.getConnection();

            // Create the database for tenant
            conn.createStatement().execute(createCommand + " " + tenant.getName());

            // Set USE database
            conn.createStatement().execute(setCommand + " " + tenant.getName());

            // Run o DDL - CREATE
            createDatabase(conn);

        } catch (NamingException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                // Closes the connection
                if (conn != null && !conn.isClosed()) {
                    // Set master database
                    conn.createStatement().execute(setCommand + " " + masterDatabase);
                    // Close connection
                    conn.close();
                }

                // Add Tenancy in table/master schema
                super.persist(tenant);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Execute SQL for CREATE DATABASE of @Tenant.
     *
     * @param conn
     * @throws SQLException Error on execute SQL
     * @throws IOException Error with DDL file
     *
     */
    private void createDatabase(Connection conn) {

        List<Sgdb> lista = (List<Sgdb>) sgdbDAO.find().getContent();

        lista.forEach(line -> {
            try {
                conn.createStatement().execute(line.getComando());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

}
