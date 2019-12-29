import React from "react";
import { Form, FormGroup, TextInput } from "@patternfly/react-core";
import { useForm } from "react-hook-form";
import * as yup from "yup";

export type FormData = {
  name: string;
  description: string;
};

interface Props {
  defaultFormValue: FormData | undefined;
  onChange: (isValid: boolean, value: FormData) => void;
}

const OrganizationForm: React.FC<Props> = ({ defaultFormValue, onChange }) => {
  const validationSchema = yup.object().shape({
    name: yup
      .string()
      .trim()
      .required("Este dato es obligatorio.")
      .min(
        3,
        "El nombre de la organización debe de contener al menos 3 caracteres."
      )
      .max(
        250,
        "El nombre de la organización debe de contener menos de 250 caracteres."
      ),
    description: yup
      .string()
      .nullable()
      .trim()
      .max(
        250,
        "La descripción de la organización debe de contener menos de 250 caracteres."
      )
  });

  const { register, errors, getValues, triggerValidation } = useForm<FormData>({
    mode: "onSubmit",
    defaultValues: defaultFormValue,
    validationSchema
  });

  // Handlers

  const handleOnFormChange = () => {
    triggerValidation().then((isValid: boolean) => {
      onChange(isValid, getValues());
    });
  };

  return (
    <React.Fragment>
      <Form onChange={handleOnFormChange}>
        <FormGroup
          label="Name"
          isRequired
          fieldId="name"
          helperText="Nombre de la organización"
          isValid={!errors.name}
          helperTextInvalid={errors.name && errors.name.message}
        >
          <TextInput
            isRequired
            type="text"
            id="name"
            name="name"
            aria-describedby="name"
            ref={register}
            isValid={!errors.name}
          />
        </FormGroup>
        <FormGroup
          label="Descripción"
          fieldId="description"
          isValid={!errors.description}
          helperTextInvalid={errors.description && errors.description.message}
        >
          <textarea
            className="pf-c-form-control"
            aria-invalid="false"
            id="description"
            name="description"
            placeholder="Breve descripción de la organización."
            ref={register}
          ></textarea>
        </FormGroup>
      </Form>
    </React.Fragment>
  );
};

export default OrganizationForm;
