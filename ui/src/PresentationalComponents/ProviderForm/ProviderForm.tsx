import React from "react";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import { Form, FormGroup, TextInput } from "@patternfly/react-core";
import {
  ComponentRepresentation,
  ComponentTypeRepresentation,
  ConfigPropertyRepresentation
} from "../../models/xml-builder";
import PropertySwitch from "../PropertySwitch";
import PropertySelect from "../PropertySelect";
import PropertyFile from "../PropertyFile";

// export type FormData = {
//   id: string;
//   name: string;
//   config: { [key: string]: string[] };
// };

export type FormData = { [key: string]: string | boolean };

interface Props {
  provider: ComponentTypeRepresentation | undefined;
  component: ComponentRepresentation | undefined;
  onChange: (isValid: boolean, value: FormData) => void;
}

const ProviderForm: React.FC<Props> = ({ provider, component, onChange }) => {
  const schema: any = {
    id: yup
      .string()
      .trim()
      .required("Este dato es requerido.")
      .max(250, "Este campo debe de contener menos de 250 caracteres."),
    name: yup
      .string()
      .trim()
      .required("Este dato es requerido.")
      .min(3, "Este campo debe de contener al menos 3 caracteres.")
      .max(250, "Este campo debe de contener menos de 250 caracteres.")
  };
  if (provider) {
    provider.properties.forEach((property: ConfigPropertyRepresentation) => {
      switch (property.type) {
        case "boolean":
          schema[property.name] = yup
            .boolean()
            .typeError("Este dato debe de ser true/false")
            .required("Este dato es requerido.");
          break;
        default:
          schema[property.name] = yup
            .string()
            .trim()
            .required("Este dato es requerido.");
          break;
      }
    });
  }
  const validationSchema = yup.object().shape(schema);

  const defaultValues: any = {
    priority: 0,
    active: true,
    enabled: true,
    keySize: 2048
  };

  if (component) {
    defaultValues.id = component.id;
    defaultValues.name = component.name;
    Object.keys(component.config).forEach(key => {
      defaultValues[key] = component.config[key][0];
    });
  }

  const { register, errors, triggerValidation, setValue, getValues } = useForm<
    FormData
  >({
    mode: "onSubmit",
    defaultValues,
    validationSchema
  });

  // Lyfe cycle

  React.useEffect(() => {
    if (provider) {
      provider.properties.forEach((property: ConfigPropertyRepresentation) => {
        register({ name: property.name });
      });
    }
  }, [register, provider]);

  // Handlers

  const handleOnFormChange = () => {
    triggerValidation().then((isValid: boolean) => {
      onChange(isValid, getValues());
    });
  };

  // Render

  const renderConfigProperty = (property: ConfigPropertyRepresentation) => {
    switch (property.type) {
      case "String":
        return (
          <TextInput
            type="text"
            key={property.name}
            id={property.name}
            name={property.name}
            aria-describedby={property.helpText}
            ref={register}
            isValid={!errors[property.name]}
          />
        );
      case "Password":
        return (
          <TextInput
            type="password"
            key={property.name}
            id={property.name}
            name={property.name}
            aria-describedby={property.helpText}
            ref={register}
            isValid={!errors[property.name]}
          />
        );
      case "boolean":
        const handleSwitchChange = (isChecked: boolean): void => {
          setValue(property.name, isChecked);
        };
        return (
          <PropertySwitch
            property={property}
            onChange={handleSwitchChange}
            defaultValue={defaultValues[property.name]}
          />
        );
      case "List":
        const handleSelectChange = (selected: string): void => {
          setValue(property.name, selected);
          handleOnFormChange();
        };
        return (
          <PropertySelect
            property={property}
            onChange={handleSelectChange}
            defaultValue={defaultValues[property.name]}
          />
        );
      case "File":
        const handleFileChange = (fileData: string): void => {
          setValue(property.name, fileData);
          handleOnFormChange();
        };
        return (
          <PropertyFile
            property={property}
            onChange={handleFileChange}
            defaultValue={defaultValues[property.name]}
          />
        );
    }
  };

  return (
    <React.Fragment>
      <Form onSubmit={() => {}} onChange={handleOnFormChange} isHorizontal>
        {component && (
          <FormGroup
            label="Id"
            fieldId="id"
            isValid={!errors.id}
            helperTextInvalid={errors.id && errors.id.message}
          >
            <TextInput
              type="text"
              id="id"
              name="id"
              aria-describedby="id"
              ref={register}
              isValid={!errors.id}
              isReadOnly
            />
          </FormGroup>
        )}
        <FormGroup
          label="Name"
          fieldId="name"
          helperText="Nombre"
          isValid={!errors.name}
          helperTextInvalid={errors.name && errors.name.message}
        >
          <TextInput
            type="text"
            id="name"
            name="name"
            aria-describedby="name"
            ref={register}
            isValid={!errors.name}
          />
        </FormGroup>
        {provider &&
          provider.properties.map((property: ConfigPropertyRepresentation) => {
            const propertyError = errors[property.name];
            return (
              <FormGroup
                key={property.name}
                label={property.label}
                fieldId={property.name}
                helperText={property.helpText}
                isValid={!propertyError}
                helperTextInvalid={propertyError && propertyError.message}
              >
                {renderConfigProperty(property)}
              </FormGroup>
            );
          })}
      </Form>
    </React.Fragment>
  );
};

export default ProviderForm;
