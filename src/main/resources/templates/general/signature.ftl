    <cac:Signature>
    <#if firmante??>
    <cbc:ID>${firmante.ruc}</cbc:ID>
        <cac:SignatoryParty>
            <cac:PartyIdentification>
                <cbc:ID>${firmante.ruc}</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyName>
                <cbc:Name><![CDATA[${firmante.razonSocial}]]></cbc:Name>
            </cac:PartyName>
        </cac:SignatoryParty>
        <cac:DigitalSignatureAttachment>
            <cac:ExternalReference>
                <cbc:URI>#SIGN-ID</cbc:URI>
            </cac:ExternalReference>
        </cac:DigitalSignatureAttachment>
    </#if>
    </cac:Signature>
