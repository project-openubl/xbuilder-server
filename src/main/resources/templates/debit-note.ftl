<#setting number_format="computer">
<?xml version="1.0" encoding="ISO-8859-1"?>
<DebitNote xmlns="urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2"
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
    <cac:RequestedMonetaryTotal>
    <#include "./general/monetary-total.ftl">
    </cac:RequestedMonetaryTotal>
    <#list detalle as item>
    <cac:DebitNoteLine>
        <cbc:ID>${item?index + 1}</cbc:ID>
        <cbc:DebitedQuantity unitCode="${item.unidadMedida}" unitCodeListAgencyName="United Nations Economic Commission for Europe" unitCodeListID="UN/ECE rec 20">${item.cantidad}</cbc:DebitedQuantity>
        <#include "./general/detail.ftl">
    </cac:DebitNoteLine>
    </#list>
</DebitNote>