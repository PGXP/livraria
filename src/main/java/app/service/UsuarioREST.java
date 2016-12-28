/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Usuario;
import io.swagger.annotations.Api;
import javax.ws.rs.Path;
import org.demoiselle.jee.persistence.crud.AbstractREST;
import org.demoiselle.jee.security.annotation.RequiredRole;

/**
 *
 * @author gladson
 */
@Api("Usuario")
@Path("usuario")
@RequiredRole("Administrador")
public class UsuarioREST extends AbstractREST<Usuario, Long> {

}
