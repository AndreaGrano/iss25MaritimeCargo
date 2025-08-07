import React, { useState, useContext } from 'react';
import FormContext from './FormContext';

function LoadForm() {
    const [PID, setPID] = useState(1);

    const { setShowForm, sendMessage, isFormEnabled } = useContext(FormContext);

    const handleCancel = (e) => {
        e.preventDefault();
        setShowForm(false);
    };

    const sendForm = (e) => {
        e.preventDefault();

        const pidInt = parseInt(PID);
        if (isNaN(pidInt)) return alert("Enter a valid PID");

        const message = {
            type: "loadProduct",
            PID: pidInt
        };

        sendMessage(message);
        setShowForm(false);
    };

    return (
        <div className="formContainer">
            <input type='number' value={PID} onChange={(e) => setPID(e.target.value)} min={1} />
            <input type="button" value="Cancel" onClick={handleCancel} />
            <input type="button" value="Send" onClick={sendForm} disabled={!isFormEnabled} />
            {
                isFormEnabled
                ?
                <></>
                :
                <div className='guasto'>
                    <p>It's not possible to send load requests at this tie due to technical issues</p>
                </div>
            }
        </div>
    )
}

export default LoadForm;