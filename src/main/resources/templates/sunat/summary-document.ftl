<?xml version="1.0" encoding="ISO-8859-1"?>
<SummaryDocuments xmlns="urn:sunat:names:specification:ubl:peru:schema:xsd:SummaryDocuments-1"
                  xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
                  xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
                  xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
                  xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"
                  xmlns:ns11="urn:sunat:names:specification:ubl:peru:schema:xsd:Perception-1"
                  xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2"
                  xmlns:sac="urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1"
                  xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2"
                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <ext:UBLExtensions>
        <ext:UBLExtension>
            <ext:ExtensionContent />
        </ext:UBLExtension>
    </ext:UBLExtensions>
    <cbc:UBLVersionID>2.0</cbc:UBLVersionID>
    <cbc:CustomizationID>1.1</cbc:CustomizationID>
    <cbc:ID>${serieNumero}</cbc:ID>
    <cbc:ReferenceDate>${fechaEmisionDocumentReference}</cbc:ReferenceDate>
    <cbc:IssueDate>${fechaEmision}</cbc:IssueDate>
    <#include "../signature.ftl">
    <#include "./common/supplier.ftl">
    <#list detalle as item>
    <sac:SummaryDocumentsLine>
        <cbc:LineID>${item?index + 1}</cbc:LineID>
        <cbc:DocumentTypeCode>${item.tipoComprobante.code}</cbc:DocumentTypeCode>
        <cbc:ID>${item.serieNumero}</cbc:ID>
        <cac:AccountingCustomerParty>
            <cbc:CustomerAssignedAccountID>${item.cliente.numeroDocumentoIdentidad}</cbc:CustomerAssignedAccountID>
            <cbc:AdditionalAccountID>${item.cliente.tipoDocumentoIdentidad}</cbc:AdditionalAccountID>
        </cac:AccountingCustomerParty>
        <cac:Status>
            <cbc:ConditionCode>${item.operacion}</cbc:ConditionCode>
        </cac:Status>
        <sac:TotalAmount currencyID=${moneda}>${item.importeTotal}</sac:TotalAmount>
        <#list item.totales as total>
        <sac:BillingPayment>
            <cbc:PaidAmount currencyID="${moneda}">${total.valor}</cbc:PaidAmount>
            <cbc:InstructionID>${total.tipo.code}</cbc:InstructionID>
        </sac:BillingPayment>
        </#list>
        <#list item.impuestos as impuesto>
        <cac:TaxTotal>
            <cbc:TaxAmount currencyID=${moneda}>${impuesto.total}</cbc:TaxAmount>
            <cac:TaxSubtotal>
                <cbc:TaxAmount currencyID=${moneda}>${impuesto.total}</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cac:TaxScheme>
                        <cbc:ID>1000</cbc:ID>
                        <cbc:Name>IGV</cbc:Name>
                        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
                    </cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
        </cac:TaxTotal>
        </#list>
    </sac:SummaryDocumentsLine>
    </#list>
</SummaryDocuments>
