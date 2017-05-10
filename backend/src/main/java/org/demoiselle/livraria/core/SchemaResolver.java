package org.demoiselle.livraria.core;

import javax.inject.Inject;
import org.demoiselle.jee.core.api.security.DemoiselleUser;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * This class get the Tenant in MultiTenantContext for hibernate uses,
 * implementation of @CurrentTenantIdentifierResolver in Hibernate.
 *
 * @author SERPRO
 *
 */
public class SchemaResolver implements CurrentTenantIdentifierResolver {

    @Inject
    private DemoiselleUser dml;

    @Override
    public String resolveCurrentTenantIdentifier() {
        return (dml == null || dml.getParams("tenant") == null || dml.getParams("tenant").isEmpty()) ? "public" : dml.getIdentity();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

}
