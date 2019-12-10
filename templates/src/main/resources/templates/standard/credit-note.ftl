<#setting number_format="computer">
<?xml version="1.0" encoding="ISO-8859-1"?>
<CreditNote xmlns="urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2"
            <#include "./common/namespaces.ftl">
>
    <#include "./common/ubl-extensions.ftl">
    <#include "./common/general-data.ftl">
    <cbc:DocumentCurrencyCode listID="ISO 4217 Alpha" listAgencyName="United Nations Economic Commission for Europe" listName="Currency">${moneda}</cbc:DocumentCurrencyCode>
    <cbc:LineCountNumeric>${detalleSize}</cbc:LineCountNumeric>
    <#include "./common/note/invoice-reference.ftl">
    <#include "../signature.ftl">
    <#include "./common/supplier.ftl">
    <#include "./common/customer.ftl">
    <#include "./common/tax-total.ftl">
    <cac:LegalMonetaryTotal>
    <#include "./common/monetary-total.ftl">
    </cac:LegalMonetaryTotal>
    <#list detalle as item>
    <cac:CreditNoteLine>
        <cbc:ID>${item?index + 1}</cbc:ID>
        <cbc:CreditedQuantity unitCode="${item.unidadMedida}" unitCodeListAgencyName="United Nations Economic Commission for Europe" unitCodeListID="UN/ECE rec 20">${item.cantidad}</cbc:CreditedQuantity>
        <#include "./common/document-line.ftl">
    </cac:CreditNoteLine>
    </#list>
</CreditNote>
