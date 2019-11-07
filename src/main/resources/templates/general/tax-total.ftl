    <cac:TaxTotal>
        <cbc:TaxAmount currencyID="PEN">${importeTotalImpuestos}</cbc:TaxAmount>
<#list totalImpuestos as impuesto>
        <cac:TaxSubtotal>
            <cbc:TaxableAmount currencyID="PEN">${impuesto.baseImponible}</cbc:TaxableAmount>
            <cbc:TaxAmount currencyID="PEN">${impuesto.importe}</cbc:TaxAmount>
            <cac:TaxCategory>
                <cbc:ID schemeAgencyName="United Nations Economic Commission for Europe" schemeID="UN/ECE 5305" schemeName="Tax Category Identifie">${impuesto.categoria.categoria}</cbc:ID>
                <cac:TaxScheme>
                    <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">${impuesto.categoria.code}</cbc:ID>
                    <cbc:Name>${impuesto.categoria.nombre}</cbc:Name>
                    <cbc:TaxTypeCode>${impuesto.categoria.tipo}</cbc:TaxTypeCode>
                </cac:TaxScheme>
            </cac:TaxCategory>
        </cac:TaxSubtotal>
</#list>
    </cac:TaxTotal>
