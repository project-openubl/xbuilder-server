import React from "react";
import { Link } from "react-router-dom";
import { AxiosError } from "axios";
import {
  PageSection,
  Button,
  Toolbar,
  ToolbarGroup,
  ToolbarItem,
  PageSectionVariants,
  TextContent,
  Text,
  Card,
  CardHeader,
  Pagination
} from "@patternfly/react-core";
import {
  Table,
  TableHeader,
  TableBody,
  ICell,
  IRow,
  cellWidth,
  IAction
} from "@patternfly/react-table";
import {
  OrganizationRepresentation,
  SearchResultsRepresentation
} from "../../models/xml-builder";
import { FetchStatus } from "../../store/common";
import SearchBoxForm from "../../PresentationalComponents/SearchBoxForm";
import { XmlBuilderRouterProps } from "../../models/routerProps";
import SkeletonTable from "../../PresentationalComponents/SkeletonTable";
import ErrorTable from "../../PresentationalComponents/ErrorTable";
import EmptyTable from "../../PresentationalComponents/EmptyTable";
import { deleteDialogActions } from "../../store/deleteDialog";

interface StateToProps {
  organizations: SearchResultsRepresentation<OrganizationRepresentation>;
  error: AxiosError<any> | null;
  fetchStatus: FetchStatus;
}

interface DispatchToProps {
  fetchOrganizations: (
    filterText: string,
    page: number,
    pageSize: number
  ) => Promise<void>;
  deleteOrganization: (organizationId: string) => Promise<void>;
  showDeleteDialog: typeof deleteDialogActions.openModal;
  closeDeleteDialog: typeof deleteDialogActions.closeModal;
}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

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
          onClick: this.handleEliminar
        }
      ]
    };
  }

  componentDidMount() {
    this.refreshData();
  }

  refreshData = async (
    page: number = this.state.page,
    pageSize: number = this.state.pageSize,
    filterText: string = this.state.filterText
  ) => {
    const { fetchOrganizations } = this.props;

    await fetchOrganizations(filterText, page, pageSize);
    this.filtersInRowsAndCells();
  };

  filtersInRowsAndCells = (
    data: SearchResultsRepresentation<OrganizationRepresentation> = this.props
      .organizations
  ) => {
    const rows: (IRow | string[])[] = data.items.map(
      (item: OrganizationRepresentation) => {
        return {
          cells: [
            {
              title: (
                <Link to={`/organizations/manage/${item.id}/keys`}>
                  {item.name}
                </Link>
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

    this.setState({ rows });
  };

  // handlers

  handleEditar = (event: React.MouseEvent, rowIndex: number): void => {
    const { history, organizations } = this.props;
    history.push("/organizations/edit/" + organizations.items[rowIndex].id);
  };

  handleEliminar = (event: React.MouseEvent, rowIndex: number) => {
    const {
      showDeleteDialog,
      closeDeleteDialog,
      deleteOrganization
    } = this.props;

    const { organizations } = this.props;
    const organization = organizations.items[rowIndex];

    showDeleteDialog({
      name: organization.name,
      type: "organización",
      onDelete: () => {
        deleteOrganization(organization.id).then(() => {
          closeDeleteDialog();
        });
      },
      onCancel: () => {
        closeDeleteDialog();
      }
    });
  };

  handleSearchSubmit = (values: any) => {
    const page = 1;
    const { pageSize } = this.state;
    const filterText: string = values.filterText.trim();

    this.setState({ filterText }, () => {
      this.refreshData(page, pageSize, filterText);
    });
  };

  onPageChange = (event: any, page: number) => {
    this.setState({ page }, () => {
      this.refreshData(page);
    });
  };

  handleOnSetPage = (event: any, page: number) => {
    return this.onPageChange(event, page);
  };

  handleOnPageInput = (event: any, page: number) => {
    return this.onPageChange(event, page);
  };

  handleOnPerPageSelect = (_event: any, pageSize: number) => {
    let page = this.state.page;
    const total = this.props.organizations.totalSize;

    // If current page and perPage would request data beyond total, show last available page
    if (page * pageSize > total) {
      page = Math.floor(total / pageSize) + 1;
    }

    this.setState({ page, pageSize }, () => {
      this.refreshData(page, pageSize);
    });
  };

  // render

  renderSearchBox = () => {
    return <SearchBoxForm handleOnSubmit={this.handleSearchSubmit} />;
  };

  renderPagination = (isCompact: boolean) => {
    const { page, pageSize } = this.state;
    const { organizations } = this.props;
    return (
      <Pagination
        itemCount={organizations.totalSize}
        page={page}
        perPage={pageSize}
        onPageInput={this.handleOnPageInput}
        onSetPage={this.handleOnSetPage}
        widgetId="pagination-options-menu-top"
        onPerPageSelect={this.handleOnPerPageSelect}
        isCompact={isCompact}
      />
    );
  };

  renderTable = () => {
    const { error, fetchStatus } = this.props;
    const { columns, rows, actions, pageSize } = this.state;

    if (fetchStatus !== "complete") {
      return <SkeletonTable columns={columns} rowSize={pageSize} />;
    }

    if (error) {
      const retry = () => {
        this.refreshData();
      };
      return <ErrorTable columns={columns} retry={retry} />;
    }

    if (rows.length === 0) {
      return <EmptyTable columns={columns} />;
    }

    return (
      <React.Fragment>
        <Table
          aria-label="Organization List Table"
          cells={columns}
          rows={rows}
          actions={actions}
        >
          <TableHeader />
          <TableBody />
          {rows.length > 0 && (
            <tfoot>
              <tr>
                <td colSpan={10}>{this.renderPagination(false)}</td>
              </tr>
            </tfoot>
          )}
        </Table>
      </React.Fragment>
    );
  };

  render() {
    return (
      <React.Fragment>
        <PageSection variant={PageSectionVariants.light}>
          <TextContent>
            <Text component="h1">Organizaciones</Text>
            <Text component="small">
              Acá podrás administrar las organizaciones del sistema.
            </Text>
          </TextContent>
        </PageSection>
        <PageSection>
          <Card>
            <CardHeader>
              <Toolbar className="pf-l-toolbar pf-u-justify-content-space-between pf-u-mx-xl pf-u-my-md">
                <ToolbarGroup>
                  <ToolbarItem className="pf-u-mr-xl">
                    {this.renderSearchBox()}
                  </ToolbarItem>
                  <ToolbarItem>
                    <Link to="/organizations/create">
                      <Button aria-label="Crear organización">
                        Crear organización
                      </Button>
                    </Link>
                  </ToolbarItem>
                </ToolbarGroup>
                <ToolbarGroup>
                  <ToolbarItem>{this.renderPagination(true)}</ToolbarItem>
                </ToolbarGroup>
              </Toolbar>
            </CardHeader>
          </Card>
          {this.renderTable()}
        </PageSection>
      </React.Fragment>
    );
  }
}

export default OrganizationListPage;
