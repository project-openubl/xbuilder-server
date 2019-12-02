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
                <cac:RegistrationAddress>
                    <cbc:AddressTypeCode>${proveedor.codigoPostal}</cbc:AddressTypeCode>
                </cac:RegistrationAddress>
            </cac:PartyLegalEntity>
        </cac:Party>
    </cac:AccountingSupplierParty>
