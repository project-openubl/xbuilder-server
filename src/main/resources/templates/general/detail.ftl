        <cac:PricingReference>
            <#if !item.tipoIgv.operacionOnerosa>
                <cac:AlternativeConditionPrice>
                    <cbc:PriceAmount currencyID="${moneda}">0</cbc:PriceAmount>
                    <cbc:PriceTypeCode listAgencyName="PE:SUNAT" listName="SUNAT:Indicador de Tipo de Precio" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16">01
                    </cbc:PriceTypeCode>
                </cac:AlternativeConditionPrice>
            </#if>
            <cac:AlternativeConditionPrice>
                <cbc:PriceAmount currencyID="${moneda}">${item.precioUnitario}</cbc:PriceAmount>
                <cbc:PriceAmount currencyID="PEN">12076.26</cbc:PriceAmount>
                <cbc:PriceTypeCode listAgencyName="PE:SUNAT" listName="SUNAT:Indicador de Tipo de Precio" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16">${item.tipoPrecio.code}</cbc:PriceTypeCode>
            </cac:AlternativeConditionPrice>
        </cac:PricingReference>
        <cac:TaxTotal>
            <cbc:TaxAmount currencyID="${moneda}">${item.igv}</cbc:TaxAmount>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="${moneda}">${item.total}</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="${moneda}">${item.igv}</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cbc:ID schemeAgencyName="United Nations Economic Commission for Europe" schemeID="UN/ECE 5305" schemeName="Tax Category Identifier">S
                    </cbc:ID>
                    <cbc:Percent>${item.igv}</cbc:Percent>
                    <cbc:TaxExemptionReasonCode listAgencyName="PE:SUNAT" listName="SUNAT:Codigo de Tipo de Afectación del IGV" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07">${item.tipoIgv.code}</cbc:TaxExemptionReasonCode>
                    <cac:TaxScheme>
                        <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">1000</cbc:ID>
                        <cbc:Name>IGV</cbc:Name>
                        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
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
