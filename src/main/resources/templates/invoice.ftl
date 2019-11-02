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
                         listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01">${tipoComprobante.code}</cbc:InvoiceTypeCode>
    <cbc:DocumentCurrencyCode listID="ISO 4217 Alpha"
                              listAgencyName="United Nations Economic Commission for Europe"
                              listName="Currency">${moneda}</cbc:DocumentCurrencyCode>
    <cbc:LineCountNumeric>${detalleSize}</cbc:LineCountNumeric>
    <cac:Signature>
    </cac:Signature>
    <cac:AccountingSupplierParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="6"
                        schemeAgencyName="PE:SUNAT"
                        schemeName="SUNAT:Identificador de Documento de Identidad"
                        schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">${proveedor.ruc}</cbc:ID>
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
    <cac:AccountingCustomerParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="${cliente.tipoDocumentoIdentidad.code}"
                        schemeAgencyName="PE:SUNAT"
                        schemeName="SUNAT:Identificador de Documento de Identidad"
                        schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">${cliente.numeroDocumentoIdentidad}</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName><![CDATA[${cliente.nombre}]]></cbc:RegistrationName>
            </cac:PartyLegalEntity>
        </cac:Party>
    </cac:AccountingCustomerParty>
    <cac:TaxTotal>
    </cac:TaxTotal>
    <cac:LegalMonetaryTotal>
    </cac:LegalMonetaryTotal>
    <#list detalle as item>
    <cac:InvoiceLine>
        <cbc:ID>${item?index + 1}</cbc:ID>
        <cbc:InvoicedQuantity
                unitCode="${item.unidadMedida}"
                unitCodeListAgencyName="United Nations Economic Commission for Europe"
                unitCodeListID="UN/ECE rec 20">${item.cantidad}</cbc:InvoicedQuantity>
        <cbc:LineExtensionAmount currencyID="${moneda}">${item.subtotal}</cbc:LineExtensionAmount>
        <cac:PricingReference>
            <cac:AlternativeConditionPrice>
                <cbc:PriceAmount currencyID="${moneda}">${item.precioUnitario}</cbc:PriceAmount>
                <cbc:PriceTypeCode listAgencyName="PE:SUNAT"
                                   listName="SUNAT:Indicador de Tipo de Precio"
                                   listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16">${item.tipoPrecio.code}</cbc:PriceTypeCode>
            </cac:AlternativeConditionPrice>
        </cac:PricingReference>
        <cac:TaxTotal>
            <cbc:TaxAmount currencyID="${moneda}">${item.igv}</cbc:TaxAmount>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="${moneda}">${item.total}</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="${moneda}">${item.igv}</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cbc:ID schemeAgencyName="United Nations Economic Commission for Europe" schemeID="UN/ECE 5305" schemeName="Tax Category Identifier">S</cbc:ID>
                    <cbc:Percent>${igvPercent}</cbc:Percent>
                    <cbc:TaxExemptionReasonCode
                            listAgencyName="PE:SUNAT"
                            listName="SUNAT:Codigo de Tipo de Afectación del IGV"
                            listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07">10</cbc:TaxExemptionReasonCode>
                    <cac:TaxScheme>
                        <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">1000</cbc:ID>
                        <cbc:Name>IGV</cbc:Name>
                        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
                    </cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
        </cac:TaxTotal>
        <cac:Item>
            <cbc:Description><![CDATA[${item.descripcion}]]></cbc:Description>
        </cac:Item>
        <cac:Price>
            <cbc:PriceAmount currencyID="${moneda}">${item.valorUnitario}</cbc:PriceAmount>
        </cac:Price>
    </cac:InvoiceLine>
    </#list>
</Invoice>