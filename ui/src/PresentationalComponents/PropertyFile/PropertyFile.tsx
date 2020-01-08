import React from "react";
import {
  InputGroup,
  TextInput,
  Split,
  SplitItem
} from "@patternfly/react-core";
import { ImportIcon } from "@patternfly/react-icons";
import { ConfigPropertyRepresentation } from "../../models/xml-builder";

export interface Props {
  defaultValue: string;
  property: ConfigPropertyRepresentation;
  onChange: (fileData: string) => any;
}

export interface State {
  fileData: string;
}

class PropertyFile extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      fileData: props.defaultValue
    };
  }

  handleChange = (event: any) => {
    const inputValue = event.target;
    const file: File = inputValue.files[0];

    const reader: FileReader = new FileReader();
    reader.onloadend = () => {
      const data: any = reader.result;
      this.setState({ fileData: data });
      this.props.onChange(data);
    };
    reader.readAsText(file);
  };

  render() {
    const { property } = this.props;
    const { fileData } = this.state;

    return (
      <React.Fragment>
        <Split gutter="md">
          <SplitItem isFilled>
            <TextInput
              id={`${property.type}-${property.name}`}
              name={property.name}
              type="text"
              aria-label="key input"
              value={fileData}
            />
          </SplitItem>
          <SplitItem>
            <InputGroup>
              <label>
                <span className="pf-c-button pf-m-secondary">
                  Seleccionar archivo <ImportIcon />
                  <input
                    type="file"
                    onChange={this.handleChange}
                    style={{ display: "none" }}
                  />
                </span>
              </label>
            </InputGroup>
          </SplitItem>
        </Split>
      </React.Fragment>
    );
  }
}

export default PropertyFile;
