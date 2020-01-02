import React from "react";
import { Table, TableHeader, TableBody } from "@patternfly/react-table";
import { ErrorCircleOIcon } from "@patternfly/react-icons";
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
  retry: any;
}

export interface State {}

class ErrorTable extends React.Component<Props, State> {
  render() {
    return (
      <React.Fragment>
        <Table cells={this.props.columns} rows={[]} aria-label="Error table">
          <TableHeader />
          <TableBody />
        </Table>
        <Card>
          <CardBody>
            <Bullseye>
              <EmptyState>
                <EmptyStateIcon icon={ErrorCircleOIcon} />
                <Title headingLevel="h5" size="lg">
                  An error occured while fetching data!
                </Title>
                <EmptyStateBody>Try again or reload the page.</EmptyStateBody>
                <EmptyStateSecondaryActions>
                  <Button variant="link" onClick={this.props.retry}>
                    Try again
                  </Button>
                </EmptyStateSecondaryActions>
              </EmptyState>
            </Bullseye>
          </CardBody>
        </Card>
      </React.Fragment>
    );
  }
}

export default ErrorTable;
