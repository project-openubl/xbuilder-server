/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.xmlbuilder.resources;

import io.github.project.openubl.xmlbuilder.config.qualifiers.CDIProvider;
import io.github.project.openubl.xmlbuilder.idm.ServerInfoRepresentation;
import io.github.project.openubl.xmlbuilderlib.clock.SystemClock;
import io.github.project.openubl.xmlbuilderlib.models.output.sunat.RetentionOutputModel;
import io.github.project.openubl.xmlbuilderlib.utils.InputToOutput;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
@Path("/server-info")
@Consumes(MediaType.APPLICATION_JSON)
public class ServerInfoResource {

    @Inject
    @CDIProvider
    SystemClock systemClock;

    @Inject
    @ConfigProperty(name = "openubl.igv")
    BigDecimal igv;

    @Inject
    @ConfigProperty(name = "openubl.ivap")
    BigDecimal ivap;

    @Inject
    @ConfigProperty(name = "openubl.icb")
    BigDecimal icb;

    @Inject
    @ConfigProperty(name = "openubl.defaults.moneda")
    String defaultMoneda;

    @Inject
    @ConfigProperty(name = "openubl.defaults.unidad-medida")
    String defaultUnidadMedida;

    @Inject
    @ConfigProperty(name = "openubl.defaults.tipo-igv")
    String defaultTipoIgv;

    @Inject
    @ConfigProperty(name = "openubl.defaults.tipo-nota-credito")
    String defaultTipoNotaCredito;

    @Inject
    @ConfigProperty(name = "openubl.defaults.tipo-nota-debito")
    String defaultTipoNotaDebito;

    @Inject
    @ConfigProperty(name = "openubl.defaults.regimen-percepcion")
    String defaultRegimenPercepcion;

    @Inject
    @ConfigProperty(name = "openubl.defaults.regimen-retencion")
    String defaultRegimenRetencion;

    @Inject
    @ConfigProperty(name = "openubl.timezone")
    String timeZone;

    @Inject
    @ConfigProperty(name = "openubl.server.keystore.location")
    Optional<String> keystoreLocation;

    @Inject
    @ConfigProperty(name = "openubl.server.keystore.password")
    Optional<String> keystorePassword;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ServerInfoRepresentation getServerInfo() {
        ServerInfoRepresentation.Clock clockRep = new ServerInfoRepresentation.Clock();
        clockRep.setTime(systemClock.getCalendarInstance().getTime());
        clockRep.setTimeZone(systemClock.getTimeZone());

        ServerInfoRepresentation.ApplicationConfig applicationConfigRep = new ServerInfoRepresentation.ApplicationConfig();
        applicationConfigRep.setIgv(igv);
        applicationConfigRep.setIvap(ivap);
        applicationConfigRep.setIcb(icb);
        applicationConfigRep.setDefaultMoneda(defaultMoneda);
        applicationConfigRep.setDefaultUnidadMedida(defaultUnidadMedida);
        applicationConfigRep.setDefaultTipoIgv(defaultTipoIgv);
        applicationConfigRep.setDefaultTipoNotaCredito(defaultTipoNotaCredito);
        applicationConfigRep.setDefaultTipoNotaDebito(defaultTipoNotaDebito);
        applicationConfigRep.setDefaultRegimenPercepcion(defaultRegimenPercepcion);
        applicationConfigRep.setDefaultRegimenRetencion(defaultRegimenRetencion);
        applicationConfigRep.setDefaultRegimenRetencion(defaultRegimenRetencion);
        applicationConfigRep.setTimeZone(timeZone);
        applicationConfigRep.setServerKeystoreLocation(keystoreLocation.orElse(null));
        applicationConfigRep.setServerKeystorePassword(keystorePassword.orElse(null));

        ServerInfoRepresentation serverInfoRep = new ServerInfoRepresentation();
        serverInfoRep.setClock(clockRep);
        serverInfoRep.setApplicationConfig(applicationConfigRep);
        return serverInfoRep;
    }

}
