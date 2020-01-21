export type DocumentType =
  | "invoice"
  | "credit-note"
  | "debit-note"
  | "voided-document"
  | "summary-document";

export interface SearchResultsRepresentation<T> {
  items: T[];
  totalSize: number;
}

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
  // This does not come from backend but from UI
  provider?: ComponentRepresentation;
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

export interface ServerInfoRepresentation {
  componentTypes: ComponentTypes;
}

export interface ComponentTypes {
  keyProviders: ComponentTypeRepresentation[];
}

export interface ComponentTypeRepresentation {
  id: string;
  helpText: string;
  properties: ConfigPropertyRepresentation[];
}

export interface ConfigPropertyRepresentation {
  name: string;
  label: string;
  helpText: string;
  type: string;
  defaultValue: string;
  options: string[];
  secret: boolean;
}
