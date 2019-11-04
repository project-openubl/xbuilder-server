    <cac:DiscrepancyResponse>
        <cbc:ReferenceID>${serieNumeroInvoiceReference}</cbc:ReferenceID>
        <cbc:ResponseCode listAgencyName="PE:SUNAT" listName="SUNAT: Identificador de tipo de nota de debito" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo10">${tipoNota.code}</cbc:ResponseCode>
        <cbc:Description><![CDATA[${descripcionSustentoInvoiceReference}]]</cbc:Description>
    </cac:DiscrepancyResponse>
    <cac:BillingReference>
        <cac:InvoiceDocumentReference>
            <cbc:ID>${serieNumeroInvoiceReference}</cbc:ID>
            <cbc:DocumentTypeCode listAgencyName="PE:SUNAT" listName="SUNAT:Identificador de Tipo de Documento" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01">${tipoComprobanteInvoiceReference.code}</cbc:DocumentTypeCode>
        </cac:InvoiceDocumentReference>
    </cac:BillingReference>
