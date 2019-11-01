<?xml version="1.0" encoding="ISO-8859-1"?>
<Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
         xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
         xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
         xmlns:ccts="urn:un:unece:uncefact:documentation:2"
         xmlns:cec="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"
         xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
         xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"
         xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2"
         xmlns:sac="urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1"
         xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2"
         xmlns:xs="http://www.w3.org/2001/XMLSchema"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>
    <ext:UBLExtensions>
        <ext:UBLExtension>
            <ext:ExtensionContent>
            </ext:ExtensionContent>
        </ext:UBLExtension>
    </ext:UBLExtensions>
    <cbc:UBLVersionID>2.1</cbc:UBLVersionID>
    <cbc:CustomizationID>2.0</cbc:CustomizationID>
    <cbc:ID>${serieNumero}</cbc:ID>
    <cbc:IssueDate>${fechaEmision}</cbc:IssueDate>
    <#if horaEmision??><cbc:IssueTime>${horaEmision}</cbc:IssueTime></#if>
    <cbc:InvoiceTypeCode listID="0101"
                         listAgencyName="PE:SUNAT"
                         listName="SUNAT:Identificador de Tipo de Documento"
                         listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01">${codigoTipoComprobante}</cbc:InvoiceTypeCode>
    <cbc:DocumentCurrencyCode listID="ISO 4217 Alpha"
                              listAgencyName="United Nations Economic Commission for Europe"
                              listName="Currency">${moneda}</cbc:DocumentCurrencyCode>
<#--    <cbc:LineCountNumeric>${cantidadItemsVendidos}</cbc:LineCountNumeric>-->
    <cac:Signature>
    </cac:Signature>
<#--    <cac:AccountingSupplierParty>-->
<#--        <cac:Party>-->
<#--            <cac:PartyIdentification>-->
<#--                <cbc:ID schemeID="6"-->
<#--                        schemeAgencyName="PE:SUNAT"-->
<#--                        schemeName="SUNAT:Identificador de Documento de Identidad"-->
<#--                        schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">-->
<#--                    ${proveedor.ruc}-->
<#--                </cbc:ID>-->
<#--            </cac:PartyIdentification>-->
<#--            <cac:PartyName>-->
<#--                <cbc:Name>${proveedor.nombreComercial}</cbc:Name>-->
<#--            </cac:PartyName>-->
<#--            <cac:PartyLegalEntity>-->
<#--                <cbc:RegistrationName><![CDATA[${proveedor.razonSocial}]]></cbc:RegistrationName>-->
<#--                <cac:RegistrationAddress>-->
<#--                    <cbc:AddressTypeCode>${proveedor.codigoPostal}</cbc:AddressTypeCode>-->
<#--                </cac:RegistrationAddress>-->
<#--            </cac:PartyLegalEntity>-->
<#--        </cac:Party>-->
<#--    </cac:AccountingSupplierParty>-->
<#--    <cac:AccountingCustomerParty>-->
<#--        <cac:Party>-->
<#--            <cac:PartyIdentification>-->
<#--                <cbc:ID schemeID="${cliente.codigoDocumentoIdentidad}"-->
<#--                        schemeAgencyName="PE:SUNAT"-->
<#--                        schemeName="SUNAT:Identificador de Documento de Identidad"-->
<#--                        schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">-->
<#--                    ${cliente.numeroDocumentoIdentidad}-->
<#--                </cbc:ID>-->
<#--            </cac:PartyIdentification>-->
<#--            <cac:PartyLegalEntity>-->
<#--                <cbc:RegistrationName><![CDATA[${cliente.nombre}]]></cbc:RegistrationName>-->
<#--            </cac:PartyLegalEntity>-->
<#--        </cac:Party>-->
<#--    </cac:AccountingCustomerParty>-->
</Invoice>