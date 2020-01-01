import React from "react";
import { useForm, Controller } from "react-hook-form";
import * as yup from "yup";
import {
  Form,
  FormGroup,
  TextInput,
  Switch,
  SelectVariant,
  SelectOption,
  Select
} from "@patternfly/react-core";
import { CubeIcon } from "@patternfly/react-icons";
import {
  ComponentRepresentation,
  ComponentTypeRepresentation,
  ConfigPropertyRepresentation
} from "../../models/xml-builder";

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

const ProviderForm: React.FC<Props> = ({ provider, component }) => {
  const validationSchema = yup.object().shape({
    name: yup
      .string()
      .trim()
      .required("Este dato es obligatorio.")
      .min(3, "Este campo debe de contener al menos 3 caracteres.")
      .max(250, "Este campo debe de contener menos de 250 caracteres."),
    priority: yup
      .number()
      .required("Este campo es requerido")
      .min(1, "El valor minimo es 1")
  });

  const { register, errors, triggerValidation, getValues, setValue } = useForm<
    FormData
  >({
    mode: "onSubmit",
    defaultValues: undefined,
    validationSchema
  });

  //

  React.useEffect(() => {
    register({ name: "AntdInput" });
  }, [register])

  // Handlers

  const handleOnFormChange = () => {
    triggerValidation().then(() => {
      // onChange(isValid, getValues());
    });
  };

  // renders

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
        const handleSwitchChange = (checked: boolean): void => {
          setValue(property.name, checked);
        };

        return (
          <Switch
            id={property.name}
            key={property.name}
            name={property.name}
            aria-describedby={property.helpText}
            label="Activated"
            labelOff="Desactivated"
            // isChecked={isChecked}
            onChange={handleSwitchChange}
          />
        );
      case "List":
        return (
          <Select
            // toggleIcon={isToggleIcon && <CubeIcon />}
            variant={SelectVariant.single}
            aria-label={property.label}
            onToggle={() => {}}
            // onSelect={this.onSelect}
            // selections={selected}
            // isExpanded={isExpanded}
            // ariaLabelledBy={titleId}
            // isDisabled={isDisabled}
            // direction={direction}
          >
            {property.options.map((option: string, index) => (
              <SelectOption key={index} value={option} />
            ))}
          </Select>
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
