import { createContext } from "react";

const FormContext = createContext({
    showForm: false,
    setShowForm: () => {},
    sendMessage: () => {},
    isFormEnabled: true
});

export default FormContext;