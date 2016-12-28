/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package app.core;

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
        return dml.getParams("tenant");
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

}
