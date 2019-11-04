<?xml version="1.0" encoding="ISO-8859-1"?>
<Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
        <#include "./general/namespaces.ftl">
>
    <ext:UBLExtensions>
    </ext:UBLExtensions>
    <#include "./general/general-data.ftl">
    <cbc:InvoiceTypeCode listID="0101" listAgencyName="PE:SUNAT" listName="SUNAT:Identificador de Tipo de Documento" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01">${tipoComprobante.code}</cbc:InvoiceTypeCode>
    <cbc:DocumentCurrencyCode listID="ISO 4217 Alpha" listAgencyName="United Nations Economic Commission for Europe" listName="Currency">${moneda}</cbc:DocumentCurrencyCode>
    <cbc:LineCountNumeric>${detalleSize}</cbc:LineCountNumeric>
    <#include "./general/signature.ftl">
    <#include "./general/supplier.ftl">
    <#include "./general/customer.ftl">
    <#--    <cac:TaxTotal>-->
    <#--        <cbc:TaxAmount currencyID="PEN">7503.22</cbc:TaxAmount>-->
    <#--        <cac:TaxSubtotal>-->
    <#--            <cbc:TaxableAmount currencyID="PEN">41684.56</cbc:TaxableAmount>-->
    <#--            <cbc:TaxAmount currencyID="PEN">7503.22</cbc:TaxAmount>-->
    <#--            <cac:TaxCategory>-->
    <#--                <cbc:ID schemeAgencyName="United Nations Economic Commission for Europe" schemeID="UN/ECE 5305"-->
    <#--                        schemeName="Tax Category Identifie">S-->
    <#--                </cbc:ID>-->
    <#--                <cac:TaxScheme>-->
    <#--                    <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">1000-->
    <#--                    </cbc:ID>-->
    <#--                    <cbc:Name>IGV</cbc:Name>-->
    <#--                    <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>-->
    <#--                </cac:TaxScheme>-->
    <#--            </cac:TaxCategory>-->
    <#--        </cac:TaxSubtotal>-->
    <#--        <cac:TaxSubtotal>-->
    <#--            <cbc:TaxableAmount currencyID="PEN">0.00</cbc:TaxableAmount>-->
    <#--            <cbc:TaxAmount currencyID="PEN">0.00</cbc:TaxAmount>-->
    <#--            <cac:TaxCategory>-->
    <#--                <cbc:ID schemeAgencyName="United Nations Economic Commission for Europe" schemeID="UN/ECE 5305"-->
    <#--                        schemeName="Tax Category Identifie">S-->
    <#--                </cbc:ID>-->
    <#--                <cac:TaxScheme>-->
    <#--                    <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">9996-->
    <#--                    </cbc:ID>-->
    <#--                    <cbc:Name>GRA</cbc:Name>-->
    <#--                    <cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>-->
    <#--                </cac:TaxScheme>-->
    <#--            </cac:TaxCategory>-->
    <#--        </cac:TaxSubtotal>-->
    <#--    </cac:TaxTotal>-->
    <#--    <cac:LegalMonetaryTotal>-->
    <#--        <cbc:PayableAmount currencyID="PEN">49187.80</cbc:PayableAmount>-->
    <#--    </cac:LegalMonetaryTotal>-->
    <#list detalle as item>
    <cac:InvoiceLine>
        <cbc:ID>${item?index + 1}</cbc:ID>
        <cbc:InvoicedQuantity unitCode="${item.unidadMedida}" unitCodeListAgencyName="United Nations Economic Commission for Europe" unitCodeListID="UN/ECE rec 20">${item.cantidad}</cbc:InvoicedQuantity>
        <cbc:LineExtensionAmount currencyID="${moneda}">${item.subtotal}</cbc:LineExtensionAmount>
        <#include "./general/detail.ftl">
    </cac:InvoiceLine>
    </#list>
</Invoice>