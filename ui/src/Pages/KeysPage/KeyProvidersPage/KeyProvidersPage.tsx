import React from "react";
import { AxiosError } from "axios";
import {
  IRow,
  ICell,
  Table,
  TableHeader,
  TableBody,
  IAction
} from "@patternfly/react-table";
import {
  Button,
  Card,
  CardHeader,
  Toolbar,
  ToolbarGroup,
  ToolbarItem
} from "@patternfly/react-core";
import { PlusCircleIcon, InfoAltIcon } from "@patternfly/react-icons";
import KeysPageTabs from "../../../PresentationalComponents/KeysPageTabs";
import { FetchStatus } from "../../../store/common";
import {
  ComponentRepresentation,
  ServerInfoRepresentation,
  ComponentTypeRepresentation
} from "../../../models/xml-builder";
import { Link } from "react-router-dom";

interface Props {
  match: any;
  history: any;
  location: any;

  serverInfo: ServerInfoRepresentation | undefined;
  serverInfoFetchStatus: FetchStatus | undefined;
  serverInfoError: AxiosError<any> | undefined;

  organizationComponents: ComponentRepresentation[];
  organizationComponentsFetchStatus: FetchStatus | undefined;
  organizationComponentsError: AxiosError<any> | undefined;

  fetchServerInfo: () => any;
  fetchOrganizationComponents: (organizationId: string) => any;
}

interface State {
  rows: IRow[];
  columns: ICell[];
  actions: IAction[];
}

class KeyProvidersPage extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      rows: [],
      columns: [
        { title: "Tipo", transforms: [] },
        { title: "Id", transforms: [] },
        { title: "Proveedor", transforms: [] },
        { title: "Prioridad", transforms: [] }
      ],
      actions: [
        {
          title: "Editar",
          onClick: () => {}
        },
        {
          title: "Eliminar",
          onClick: (event, rowId) =>
            console.log("clicked on Third action, on row: ", rowId)
        }
      ]
    };
  }

  componentDidMount() {
    const { fetchServerInfo, fetchOrganizationComponents } = this.props;
    fetchServerInfo();
    fetchOrganizationComponents(this.getOrganizationId()).then(() => {
      this.processRows();
    });
  }

  getOrganizationId = () => {
    return this.props.match.params.organizationId;
  };

  processRows = (
    components: ComponentRepresentation[] = this.props.organizationComponents
  ) => {
    const rows: (IRow | string[])[] = components.map(
      (component: ComponentRepresentation) => ({
        cells: [
          {
            title: component.name
          },
          {
            title: component.id
          },
          {
            title: component.providerId
          },
          {
            title: component.config["priority"][0]
          }
        ]
      })
    );

    this.setState({
      rows
    });
  };

  // handle

  // render

  renderTable = () => {
    const { columns, rows, actions } = this.state;
    return (
      <React.Fragment>
        <Table
          aria-label="Keys List Table"
          cells={columns}
          rows={rows}
          actions={actions}
        >
          <TableHeader />
          <TableBody />
        </Table>
      </React.Fragment>
    );
  };

  render() {
    const { serverInfo, match } = this.props;

    return (
      <React.Fragment>
        <KeysPageTabs activeKey={2}>
          <Card>
            <CardHeader>
              <Toolbar className="pf-l-toolbar pf-u-justify-content-space-between pf-u-mx-xl pf-u-my-md">
                <ToolbarGroup>
                  <ToolbarItem className="pf-u-mr-xl">
                    <Button variant="link" icon={<InfoAltIcon />} isDisabled>
                      Crear certificado digital
                    </Button>
                  </ToolbarItem>
                </ToolbarGroup>
                <ToolbarGroup>
                  {serverInfo && (
                    <React.Fragment>
                      {serverInfo.componentTypes.keyProviders.map(
                        (provider: ComponentTypeRepresentation) => (
                          <ToolbarItem key={provider.id} className="pf-u-mx-md">
                            <Link to={`${match.url}/${provider.id}`}>
                              <Button variant="link" icon={<PlusCircleIcon />}>
                                {provider.id}
                              </Button>
                            </Link>
                          </ToolbarItem>
                        )
                      )}
                    </React.Fragment>
                  )}
                </ToolbarGroup>
              </Toolbar>
            </CardHeader>
          </Card>
          {this.renderTable()}
        </KeysPageTabs>
      </React.Fragment>
    );
  }
}

export default KeyProvidersPage;
