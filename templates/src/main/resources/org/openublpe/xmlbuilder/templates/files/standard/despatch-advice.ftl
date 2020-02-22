<?xml version="1.0" encoding="ISO-8859-1"?>
<DespatchAdvice xmlns="urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2"
                xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
                xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
                xmlns:ccts="urn:un:unece:uncefact:documentation:2"
                xmlns:cec="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"
                xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
                xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"
                xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2"
                xmlns:sac="urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1"
                xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <ext:UBLExtensions>
        <ext:UBLExtension>
            <ext:ExtensionContent/>
        </ext:UBLExtension>
    </ext:UBLExtensions>
    <cbc:UBLVersionID>2.1</cbc:UBLVersionID>
    <cbc:CustomizationID>1.0</cbc:CustomizationID>
    <cbc:ID>${serieNumero}</cbc:ID>
    <cbc:IssueDate>${fechaEmision}</cbc:IssueDate>
    <cbc:IssueTime>${horaEmision}</cbc:IssueTime>
    <cbc:DespatchAdviceTypeCode>09</cbc:DespatchAdviceTypeCode>
    <cbc:Note><![CDATA[${observacion}]]></cbc:Note>
    {% if doc.docBaja %}
    <#if guiaRemisionDadaDeBaja??>
    <cac:OrderReference>
        <cbc:ID>${guiaRemisionDadaDeBaja.serieNumero}</cbc:ID>
        <cbc:OrderTypeCode listAgencyName="PE:SUNAT" listName="SUNAT:Identificador de Tipo de Documento" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01">${guiaRemisionDadaDeBaja.tipoDocumento.code}</cbc:OrderTypeCode>
    </cac:OrderReference>
    </#if>
    <#if documentoAdicionalRelacionado??>
    <cac:AdditionalDocumentReference>
        <cbc:ID>${documentoAdicionalRelacionado.serieNumero}</cbc:ID>
        <cbc:DocumentTypeCode listAgencyName="PE:SUNAT" listName="SUNAT:Identificador de documento relacionado" listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo21">${documentoAdicionalRelacionado.tipoDocumento.code}</cbc:DocumentTypeCode>
    </cac:AdditionalDocumentReference>
    </#if>
    <#include "../signature.ftl">
    <cac:DespatchSupplierParty>
        <cbc:CustomerAssignedAccountID schemeID="6">${remitente.ruc}</cbc:CustomerAssignedAccountID>
        <cac:Party>
            <#if remitente.codigoPostal??>
            <cac:PostalAddress>
                <cbc:ID>${remitente.codigoPostal}</cbc:ID>
            </cac:PostalAddress>
            </#if>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName><![CDATA[${remitente.razonSocial}]]></cbc:RegistrationName>
            </cac:PartyLegalEntity>
        </cac:Party>
    </cac:DespatchSupplierParty>
    <cac:DeliveryCustomerParty>
        <cbc:CustomerAssignedAccountID schemeID="${destinatario.tipoDocumentoIdentidad.code}">${destinatario.numeroDocumentoIdentidad}</cbc:CustomerAssignedAccountID>
        <cac:Party>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName><![CDATA[${destinatario.nombre}]]></cbc:RegistrationName>
            </cac:PartyLegalEntity>
        </cac:Party>
    </cac:DeliveryCustomerParty>
    <cac:Shipment>
        <cbc:ID>1</cbc:ID>
        <cbc:HandlingCode>${traslado.motivo.code}</cbc:HandlingCode>
        <#if traslado.descripcion??>
        <cbc:Information>${traslado.descripcion}</cbc:Information>
        </#if>
        <cbc:GrossWeightMeasure unitCode="${traslado.pesoBrutoUnidadMedida}">${traslado.pesoBrutoTotal}</cbc:GrossWeightMeasure>
        <#if traslado.numeroBultos??>
        <cbc:TotalTransportHandlingUnitQuantity>${traslado.numeroBultos}</cbc:TotalTransportHandlingUnitQuantity>
        </#if>
        <cbc:SplitConsignmentIndicator>${traslado.transbordorProgramado}</cbc:SplitConsignmentIndicator>
        <cac:ShipmentStage>
            <cbc:TransportModeCode>${traslado.modalidad.code}</cbc:TransportModeCode>
            <cac:TransitPeriod>
                <cbc:StartDate>${traslado.fechaInicio}</cbc:StartDate>
            </cac:TransitPeriod>
            <#if transportista??>
            <cac:CarrierParty>
                <cac:PartyIdentification>
                    <cbc:ID schemeID="${transportista.tipoDocumentoIdentidad.code}">${transportista.numeroDocumentoIdentidad}</cbc:ID>
                </cac:PartyIdentification>
                <cac:PartyName>
                    <cbc:Name><![CDATA[${transportista.nombre}]]></cbc:Name>
                </cac:PartyName>
            </cac:CarrierParty>
            </#if>
            <#if vehiculo??>
            <cac:TransportMeans>
                <cac:RoadTransport>
                    <cbc:LicensePlateID>${vehiculo.placaVehiculo}</cbc:LicensePlateID>
                </cac:RoadTransport>
            </cac:TransportMeans>
            </#if>
            <#if conductor??>
            <cac:DriverPerson>
                <cbc:ID schemeID="${conductor.tipoDocumentoIdentidad.code}">${conductor.numeroDocumentoIdentidad}</cbc:ID>
            </cac:DriverPerson>
            </#if>
        </cac:ShipmentStage>

        // direccion de entrega
        <cac:Delivery>
            <cac:DeliveryAddress>
                <cbc:ID>050107</cbc:ID>
                <cbc:StreetName>direccion</cbc:StreetName>
                <cac:Country>
                    <cbc:IdentificationCode>PE</cbc:IdentificationCode>
                </cac:Country>
            </cac:DeliveryAddress>
        </cac:Delivery>

        // Direccion origen
        <cac:OriginAddress>
            <cbc:ID>050101</cbc:ID>
            <cbc:StreetName>direccion</cbc:StreetName>
            <cac:Country>
                <cbc:IdentificationCode>PE</cbc:IdentificationCode>
            </cac:Country>
        </cac:OriginAddress>
    </cac:Shipment>
    <cac:DespatchLine>
        <cbc:ID>1</cbc:ID>
        <cbc:DeliveredQuantity unitCode="NIU">10</cbc:DeliveredQuantity>
        <cac:OrderLineReference>
            <cbc:LineID>1</cbc:LineID>
        </cac:OrderLineReference>
        <cac:Item>
            <cbc:Name>JABON LIQUIDO AVAL</cbc:Name>
            <cac:SellersItemIdentification>
                <cbc:ID>254126</cbc:ID>
            </cac:SellersItemIdentification>
        </cac:Item>
    </cac:DespatchLine>
</DespatchAdvice>
