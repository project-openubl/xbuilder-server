import React from "react";
import { Table, TableHeader, TableBody } from "@patternfly/react-table";
import { SearchIcon } from "@patternfly/react-icons";
import {
  Card,
  CardBody,
  Bullseye,
  EmptyState,
  EmptyStateIcon,
  Title,
  EmptyStateBody,
  EmptyStateSecondaryActions,
  Button
} from "@patternfly/react-core";

export interface Props {
  columns: any[];
  onClearAllFilters?: any;
}

export interface State {}

class EmptyTable extends React.Component<Props, State> {
  render() {
    return (
      <React.Fragment>
        <Table cells={this.props.columns} rows={[]} aria-label="Empty table">
          <TableHeader />
          <TableBody />
        </Table>
        <Card>
          <CardBody>
            <Bullseye>
              <EmptyState>
                <EmptyStateIcon icon={SearchIcon} />
                <Title headingLevel="h5" size="lg">
                  No results found
                </Title>
                <EmptyStateBody>
                  No results match this filter criteria. Remove all filters or
                  clear all filters to show results.
                </EmptyStateBody>
                <EmptyStateSecondaryActions>
                  {this.props.onClearAllFilters && (
                    <Button
                      variant="link"
                      onClick={this.props.onClearAllFilters}
                    >
                      Clear all filters
                    </Button>
                  )}
                </EmptyStateSecondaryActions>
              </EmptyState>
            </Bullseye>
          </CardBody>
        </Card>
      </React.Fragment>
    );
  }
}

export default EmptyTable;
