    <cac:AccountingCustomerParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="${cliente.tipoDocumentoIdentidad.code}" schemeAgencyName="PE:SUNAT" schemeName="SUNAT:Identificador de Documento de Identidad" schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">${cliente.numeroDocumentoIdentidad}</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName><![CDATA[${cliente.nombre}]]></cbc:RegistrationName>
                <#if cliente.direccion??>
                <cac:RegistrationAddress>
                    <#import "./address.ftl" as adressMacro/>
                    <@adressMacro address=cliente.direccion/>
                </cac:RegistrationAddress>
                </#if>
            </cac:PartyLegalEntity>
            <#if cliente.contacto>
            <cac:Contact>
                <#if cliente.contacto.telefono>
                <cbc:Telephone>${cliente.contacto.telefono}</cbc:Telephone>
                </#if>
                <#if cliente.contacto.email>
                <cbc:ElectronicMail>${cliente.contacto.email}</cbc:ElectronicMail>
                </#if>
            </cac:Contact>
            </#if>
        </cac:Party>
    </cac:AccountingCustomerParty>
