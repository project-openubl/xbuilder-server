export interface OrganizationRepresentation {
  id: string;
  name: string;
  description: string;
  type: string;
  useMasterKeys: boolean;
}

export interface KeysMetadataRepresentation {
  active: { [key: string]: string };
  keys: KeyMetadataRepresentation[];
}

export interface KeyMetadataRepresentation {
  providerId: string;
  providerPriority: number;
  kid: string;
  status: string;
  type: string;
  algorithm: string;
  publicKey: string;
  certificate: string;
}

export interface ComponentRepresentation {
  id: string;
  name: string;
  providerId: string;
  providerType: string;
  parentId: string;
  subType: string;
  config: { [key: string]: string[] };
}
