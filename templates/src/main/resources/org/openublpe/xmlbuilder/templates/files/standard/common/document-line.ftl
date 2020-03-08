        <cbc:LineExtensionAmount currencyID="${moneda}">${item.subtotal}</cbc:LineExtensionAmount>
        <cac:PricingReference>
            <cac:AlternativeConditionPrice>
                <cbc:PriceAmount currencyID="${moneda}">${precioDeReferencia.precio}</cbc:PriceAmount>
                <cbc:PriceTypeCode listAgencyName="PE:SUNAT" listName="SUNAT:Indicador de Tipo de Precio" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16">${precioDeReferencia.tipoPrecio.code}</cbc:PriceTypeCode>
            </cac:AlternativeConditionPrice>
        </cac:PricingReference>
        <#list item.cargos as cargo>
        <cac:AllowanceCharge>
            <cbc:ChargeIndicator>true</cbc:ChargeIndicator>
            <cbc:AllowanceChargeReasonCode schemeName="SUNAT:Codigo de cargos o descuentos" schemeAgencyName="PE:SUNAT" schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53">${cargo.tipo.code}</cbc:AllowanceChargeReasonCode>
            <cbc:MultiplierFactorNumeric>${cargo.factor}</cbc:MultiplierFactorNumeric>
            <cbc:Amount currencyID="${moneda}">${cargo.monto}</cbc:Amount>
            <cbc:BaseAmount currencyID="${moneda}">${cargo.montoBase}</cbc:BaseAmount>
        </cac:AllowanceCharge>
        </#list>
        <#list item.descuentos as descuento>
        <cac:AllowanceCharge>
            <cbc:ChargeIndicator>false</cbc:ChargeIndicator>
            <cbc:AllowanceChargeReasonCode schemeName="SUNAT:Codigo de cargos o descuentos" schemeAgencyName="PE:SUNAT" schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53">${descuento.tipo.code}</cbc:AllowanceChargeReasonCode>
            <cbc:MultiplierFactorNumeric>${descuento.factor}</cbc:MultiplierFactorNumeric>
            <cbc:Amount currencyID="${moneda}">${descuento.monto}</cbc:Amount>
            <cbc:BaseAmount currencyID="${moneda}">${descuento.montoBase}</cbc:BaseAmount>
        </cac:AllowanceCharge>
        </#list>
        <cac:TaxTotal>
            <cbc:TaxAmount currencyID="${moneda}">${importeTotalImpuestos}</cbc:TaxAmount>
            <#if item.isc??>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="${moneda}">${item.isc.baseImponible}</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="${moneda}">${item.isc.importe}</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cbc:Percent>${item.isc.porcentaje}</cbc:Percent>
                    <cbc:TierRange>${item.isc.tipo.code}</cbc:TierRange>
                    <cac:TaxScheme>
                        <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">${item.igv.categoria.code}</cbc:ID>
                        <cbc:Name>${item.isc.categoria.nombre}</cbc:Name>
                        <cbc:TaxTypeCode>${item.isc.categoria.tipo}</cbc:TaxTypeCode>
                    </cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
            </#if>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="${moneda}">${item.igv.baseImponible}</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="${moneda}">${item.igv.importe}</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cbc:ID schemeAgencyName="United Nations Economic Commission for Europe" schemeID="UN/ECE 5305" schemeName="Tax Category Identifier">${item.igv.categoria.categoria}</cbc:ID>
                    <cbc:Percent>${item.igv.porcentaje}</cbc:Percent>
                    <cbc:TaxExemptionReasonCode listAgencyName="PE:SUNAT" listName="SUNAT:Codigo de Tipo de Afectacion del IGV" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07">${item.igv.tipo.code}</cbc:TaxExemptionReasonCode>
                    <cac:TaxScheme>
                        <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">${item.igv.categoria.code}</cbc:ID>
                        <cbc:Name>${item.igv.categoria.nombre}</cbc:Name>
                        <cbc:TaxTypeCode>${item.igv.categoria.tipo}</cbc:TaxTypeCode>
                    </cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
            <#if item.otroTributo??>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="${moneda}">${item.otroTributo.baseImponible}</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="${moneda}">${item.otroTributo.importe}</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cbc:Percent>${item.otroTributo.porcentaje}</cbc:Percent>
                    <cac:TaxScheme>
                        <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">${item.otroTributo.categoria.code}</cbc:ID>
                        <cbc:Name>${item.otroTributo.categoria.nombre}</cbc:Name>
                        <cbc:TaxTypeCode>${item.otroTributo.categoria.tipo}</cbc:TaxTypeCode>
                    </cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
            </#if>
            <#if item.icb??>
            <cac:TaxSubtotal>
                <cbc:TaxAmount currencyID="${moneda}">${item.icb.importe}</cbc:TaxAmount>
                <cbc:BaseUnitMeasure unitCode="${item.unidadMedida}">${item.cantidad}</cbc:BaseUnitMeasure>
                <cbc:PerUnitAmount currencyID="${moneda}">${item.icbAplicado}</cbc:PerUnitAmount>
                <cac:TaxCategory>
                    <cac:TaxScheme>
                        <cbc:ID schemeAgencyName="PE:SUNAT" schemeID="UN/ECE 5153" schemeName="Codigo de tributos">${item.icb.categoria.code}</cbc:ID>
                        <cbc:Name>${item.icb.categoria.nombre}</cbc:Name>
                        <cbc:TaxTypeCode>${item.icb.categoria.tipo}</cbc:TaxTypeCode>
                    </cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
            </#if>
        </cac:TaxTotal>
        <cac:Item>
            <cbc:Description><![CDATA[${item.descripcion}]]></cbc:Description>
        </cac:Item>
        <cac:Price>
            <cbc:PriceAmount currencyID="${moneda}">${item.valorUnitario}</cbc:PriceAmount>
        </cac:Price>
