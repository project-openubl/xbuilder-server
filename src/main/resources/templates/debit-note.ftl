<?xml version="1.0" encoding="ISO-8859-1"?>
<DebitNote xmlns="urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2"
        <#include "./general/namespaces.ftl">
>
    <ext:UBLExtensions>
    </ext:UBLExtensions>
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
    <cac:DebitNoteLine>
        <cbc:ID>${item?index + 1}</cbc:ID>
        <cbc:DebitedQuantity unitCode="${item.unidadMedida}" unitCodeListAgencyName="United Nations Economic Commission for Europe" unitCodeListID="UN/ECE rec 20">${item.cantidad}</cbc:DebitedQuantity>
        <cbc:LineExtensionAmount currencyID="${moneda}">${item.subtotal}</cbc:LineExtensionAmount>
        <#include "./general/detail.ftl">
    </cac:DebitNoteLine>
</DebitNote>