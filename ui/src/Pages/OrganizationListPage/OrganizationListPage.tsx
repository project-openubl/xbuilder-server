import React from "react";
import { AxiosError } from "axios";
import {
  PageSection,
  Button,
  Toolbar,
  ToolbarGroup,
  ToolbarItem
} from "@patternfly/react-core";
import {
  Table,
  TableHeader,
  TableBody,
  ICell,
  IRow,
  cellWidth,
  IAction,
  IRowData,
  IExtraData
} from "@patternfly/react-table";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../../store/common";
import { Link } from "react-router-dom";

interface Props {
  match: any;
  history: any;
  location: any;
  fetchOrganizations: any;
  organizations: OrganizationRepresentation[];
  error: AxiosError<any> | null;
  status: FetchStatus;
}

interface State {
  filterText: string;
  page: number;
  pageSize: number;
  rows: IRow[];
  columns: ICell[];
  actions: IAction[];
}

class OrganizationListPage extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      filterText: "",
      page: 1,
      pageSize: 10,
      rows: [],
      columns: [
        { title: "Nombre", transforms: [cellWidth("30")] },
        { title: "Descripcion", transforms: [] },
        { title: "Tipo", transforms: [cellWidth("10")] }
      ],
      actions: [
        {
          title: "Editar",
          onClick: this.handleEditar
        },
        {
          title: "Eliminar",
          onClick: (event, rowId, rowData, extra) =>
            console.log("clicked on Third action, on row: ", rowId)
        }
      ]
    };
  }

  componentDidMount() {
    this.refreshData();
  }

  refreshData = (
    filterText: string = this.state.filterText,
    page: number = this.state.page,
    pageSize: number = this.state.pageSize
  ) => {
    const { fetchOrganizations } = this.props;
    fetchOrganizations(filterText, page, pageSize).then(
      (data: OrganizationRepresentation[]) => {
        this.processRows(data);
      }
    );
  };

  processRows = (data: OrganizationRepresentation[]) => {
    const rows: (IRow | string[])[] = data.map(
      (item: OrganizationRepresentation, index: number) => {
        return {
          cells: [
            {
              title: (
                <Link to={`/organizations/${item.id}/keys`}>{item.name}</Link>
              )
            },
            {
              title: item.description ? (
                <span>{item.description}</span>
              ) : (
                <small>No description</small>
              )
            },
            {
              title: item.type
            }
          ]
        };
      }
    );

    this.setState({
      rows
    });
  };

  handleEditar = (
    event: React.MouseEvent,
    rowIndex: number,
    rowData: IRowData,
    extraData: IExtraData
  ): void => {
    const { history, organizations } = this.props;
    history.push("/edit-organization/" + organizations[rowIndex].id);
  };

  renderTable = () => {
    const { columns, rows, actions } = this.state;

    return (
      <Table
        aria-label="Organization List Table"
        cells={columns}
        rows={rows}
        actions={actions}
      >
        <TableHeader />
        <TableBody />
      </Table>
    );
  };

  render() {
    return (
      <React.Fragment>
        <PageSection>
          <div className="ins-c-table__toolbar">
            <Toolbar className="pf-l-toolbar pf-u-justify-content-space-between pf-u-mx-xl pf-u-my-md">
              <ToolbarGroup>
                <ToolbarItem className="pf-u-mx-md">
                  <Link to="/create-organization">
                    <Button aria-label="Action 2">Crear organización</Button>
                  </Link>
                </ToolbarItem>
              </ToolbarGroup>
            </Toolbar>
          </div>

          {this.renderTable()}
        </PageSection>
      </React.Fragment>
    );
  }
}

export default OrganizationListPage;
