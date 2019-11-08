<?xml version="1.0" encoding="ISO-8859-1"?>
<Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
        <#include "./general/namespaces.ftl">
>
    <#include "./general/ubl-extensions.ftl">
    <#include "./general/general-data.ftl">
    <cbc:InvoiceTypeCode listID="0101" listAgencyName="PE:SUNAT" listName="SUNAT:Identificador de Tipo de Documento" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01">${tipoComprobante.code}</cbc:InvoiceTypeCode>
    <cbc:DocumentCurrencyCode listID="ISO 4217 Alpha" listAgencyName="United Nations Economic Commission for Europe" listName="Currency">${moneda}</cbc:DocumentCurrencyCode>
    <cbc:LineCountNumeric>${detalleSize}</cbc:LineCountNumeric>
    <#include "./general/signature.ftl">
    <#include "./general/supplier.ftl">
    <#include "./general/customer.ftl">
    <#include "./general/tax-total.ftl">
    <cac:LegalMonetaryTotal>
    <#include "./general/monetary-total.ftl">
    </cac:LegalMonetaryTotal>
    <#list detalle as item>
    <cac:InvoiceLine>
        <cbc:ID>${item?index + 1}</cbc:ID>
        <cbc:InvoicedQuantity unitCode="${item.unidadMedida}" unitCodeListAgencyName="United Nations Economic Commission for Europe" unitCodeListID="UN/ECE rec 20">${item.cantidad}</cbc:InvoicedQuantity>
        <cbc:LineExtensionAmount currencyID="${moneda}">${item.subtotal}</cbc:LineExtensionAmount>
        <#include "./general/detail.ftl">
    </cac:InvoiceLine>
    </#list>
</Invoice>