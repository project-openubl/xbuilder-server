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
<#--    <cac:TaxTotal>-->
<#--        <cbc:TaxAmount currencyID="PEN">0.00</cbc:TaxAmount>-->
<#--        <cac:TaxSubtotal>-->
<#--            <cbc:TaxableAmount currencyID="PEN">12076.26</cbc:TaxableAmount>-->
<#--            <cbc:TaxAmount currencyID="PEN">0.00</cbc:TaxAmount>-->
<#--            <cac:TaxCategory>-->
<#--                <cbc:ID schemeAgencyName="United Nations Economic Commission for Europe" schemeID="UN/ECE 5305"-->
<#--                        schemeName="Tax Category Identifie">S-->
<#--                </cbc:ID>-->
<#--                <cac:TaxScheme>-->
<#--                    <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">9997-->
<#--                    </cbc:ID>-->
<#--                    <cbc:Name>EXO</cbc:Name>-->
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
<#--    <cac:RequestedMonetaryTotal>-->
<#--        <cbc:PayableAmount currencyID="PEN">12076.26</cbc:PayableAmount>-->
<#--    </cac:RequestedMonetaryTotal>-->
    <cac:DebitNoteLine>
        <cbc:ID>${item?index + 1}</cbc:ID>
        <cbc:DebitedQuantity unitCode="${item.unidadMedida}" unitCodeListAgencyName="United Nations Economic Commission for Europe" unitCodeListID="UN/ECE rec 20">${item.cantidad}</cbc:DebitedQuantity>
        <cbc:LineExtensionAmount currencyID="${moneda}">${item.subtotal}</cbc:LineExtensionAmount>
        <#include "./general/detail.ftl">
    </cac:DebitNoteLine>
</DebitNote>