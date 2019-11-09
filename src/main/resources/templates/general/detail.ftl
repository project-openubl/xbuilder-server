        <cbc:LineExtensionAmount currencyID="${moneda}">${item.subtotal}</cbc:LineExtensionAmount>
        <cac:PricingReference>
            <#list item.preciosDeReferencia as precioDeReferencia>
            <cac:AlternativeConditionPrice>
                <cbc:PriceAmount currencyID="${moneda}">${precioDeReferencia.precio}</cbc:PriceAmount>
                <cbc:PriceTypeCode listAgencyName="PE:SUNAT" listName="SUNAT:Indicador de Tipo de Precio" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16">${precioDeReferencia.tipoPrecio.code}</cbc:PriceTypeCode>
            </cac:AlternativeConditionPrice>
            </#list>
        </cac:PricingReference>
        <cac:TaxTotal>
            <cbc:TaxAmount currencyID="${moneda}">${item.igv.importe}</cbc:TaxAmount>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="${moneda}">${item.igv.baseImponible}</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="${moneda}">${item.igv.importe}</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cbc:ID schemeAgencyName="United Nations Economic Commission for Europe" schemeID="UN/ECE 5305" schemeName="Tax Category Identifier">${item.igv.categoria.categoria}</cbc:ID>
                    <cbc:Percent>${item.igvPorcentual}</cbc:Percent>
                    <cbc:TaxExemptionReasonCode listAgencyName="PE:SUNAT" listName="SUNAT:Codigo de Tipo de Afectación del IGV" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07">${item.igv.tipo.code}</cbc:TaxExemptionReasonCode>
                    <cac:TaxScheme>
                        <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">${item.igv.categoria.code}</cbc:ID>
                        <cbc:Name>${item.igv.categoria.nombre}</cbc:Name>
                        <cbc:TaxTypeCode>${item.igv.categoria.tipo}</cbc:TaxTypeCode>
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
