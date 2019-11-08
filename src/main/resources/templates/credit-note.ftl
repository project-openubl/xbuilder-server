<?xml version="1.0" encoding="ISO-8859-1"?>
<CreditNote xmlns="urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2"
            <#include "./general/namespaces.ftl">
>
    <#include "./general/ubl-extensions.ftl">
    <#include "./general/general-data.ftl">
    <cbc:DocumentCurrencyCode listID="ISO 4217 Alpha" listAgencyName="United Nations Economic Commission for Europe" listName="Currency">${moneda}</cbc:DocumentCurrencyCode>
    <cbc:LineCountNumeric>${detalleSize}</cbc:LineCountNumeric>
    <#include "./general/note/invoice-reference.ftl">
    <#include "./general/signature.ftl">
    <#include "./general/supplier.ftl">
    <#include "./general/customer.ftl">
    <#include "./general/tax-total.ftl">
    <cac:LegalMonetaryTotal>
    <#include "./general/monetary-total.ftl">
    </cac:LegalMonetaryTotal>
    <#list detalle as item>
    <cac:CreditNoteLine>
        <cbc:ID>${item?index + 1}</cbc:ID>
        <cbc:CreditedQuantity unitCode="${item.unidadMedida}" unitCodeListAgencyName="United Nations Economic Commission for Europe" unitCodeListID="UN/ECE rec 20">${item.cantidad}</cbc:CreditedQuantity>
        <cbc:LineExtensionAmount currencyID="${moneda}">${item.subtotal}</cbc:LineExtensionAmount>
        <#include "./general/detail.ftl">
    </cac:CreditNoteLine>
    </#list>
</CreditNote>