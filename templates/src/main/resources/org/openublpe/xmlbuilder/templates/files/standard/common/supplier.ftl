    <cac:AccountingSupplierParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="6" schemeAgencyName="PE:SUNAT" schemeName="SUNAT:Identificador de Documento de Identidad" schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">${proveedor.ruc}</cbc:ID>
            </cac:PartyIdentification>
            <#if proveedor.nombreComercial??>
            <cac:PartyName>
                <cbc:Name>${proveedor.nombreComercial}</cbc:Name>
            </cac:PartyName>
            </#if>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName><![CDATA[${proveedor.razonSocial}]]></cbc:RegistrationName>
                <#if proveedor.direccion??>
                <cac:RegistrationAddress>
                    <#import "./address.ftl" as adressMacro/>
                    <@adressMacro address=proveedor.direccion/>
                </cac:RegistrationAddress>
                </#if>
            </cac:PartyLegalEntity>
            <#if proveedor.contacto??>
            <cac:Contact>
                <#if proveedor.contacto.telefono??>
                <cbc:Telephone>${proveedor.contacto.telefono}</cbc:Telephone>
                </#if>
                <#if proveedor.contacto.email??>
                <cbc:ElectronicMail>${proveedor.contacto.email}</cbc:ElectronicMail>
                </#if>
            </cac:Contact>
            </#if>
        </cac:Party>
    </cac:AccountingSupplierParty>
