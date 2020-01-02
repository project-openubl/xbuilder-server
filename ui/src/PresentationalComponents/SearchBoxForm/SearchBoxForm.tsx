import React from "react";
import {
  Form,
  TextInput,
  InputGroup,
  Button,
  ButtonVariant
} from "@patternfly/react-core";
import { SearchIcon } from "@patternfly/react-icons";
import { useForm } from "react-hook-form";
import * as yup from "yup";

export type FormData = {
  filterText: string;
};

interface Props {
  handleOnSubmit: (data: FormData) => void;
}

const SearchBoxForm: React.FC<Props> = ({ handleOnSubmit }) => {
  const validationSchema = yup.object().shape({
    filterText: yup
      .string()
      .trim()
      .max(250, "Este campo debe de contener menos de 250 caracteres.")
  });

  const { register, errors, handleSubmit } = useForm<FormData>({
    validationSchema
  });

  // Handlers

  return (
    <React.Fragment>
      <Form onSubmit={handleSubmit(handleOnSubmit)}>
        <InputGroup>
          <TextInput
            type="search"
            placeholder="Filtrar por nombre..."
            id="filterText"
            name="filterText"
            aria-describedby="Filter text"
            ref={register}
            isValid={!errors.filterText}
          />
          <Button
            type="submit"
            variant={ButtonVariant.tertiary}
            aria-label="search button for search input"
          >
            <SearchIcon />
          </Button>
        </InputGroup>
      </Form>
    </React.Fragment>
  );
};

export default SearchBoxForm;
