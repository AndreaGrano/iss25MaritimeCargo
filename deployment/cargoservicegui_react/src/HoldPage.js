import { useState, useEffect, useRef } from 'react';
import LoadForm from './LoadForm';
import FormContext from './FormContext';

function HoldPage() {
    const [slots, setSlots] = useState({
        1: { PID: 0, weight: 0.0 },
        2: { PID: 0, weight: 0.0 },
        3: { PID: 0, weight: 0.0 },
        4: { PID: 0, weight: 0.0 },
    });
    const [showForm, setShowForm] = useState(false);
    const [isFormEnabled, setIsFormEnabled] = useState(true);
    const [reason, setReason] = useState(true);
    const socketRef = useRef(null);

    useEffect(() => {
        const socket = new WebSocket('ws://127.0.0.1:8011');
        socketRef.current = socket;

        socket.onopen = () => {
            console.log('WebSocket connection established.');
            socket.send(JSON.stringify({type: 'getHoldStatus'}))
        };

        socket.onmessage = (event) => {
            try {
                const incoming = JSON.parse(event.data);

                console.log(event.data);
                console.log(["loadAccepted", "holdStatus", "updateHold"].includes(incoming.type));

                if(["loadAccepted", "holdStatus", "updateHold"].includes(incoming.type)) {
                    console.log('ciao1');
                    setSlots(prev => {
                        console.log('ciao2');
                        const next = { ...prev };

                        Object.entries(incoming).filter(([key, _]) => !isNaN(key)).forEach(([slotKey, value]) => {
                            const slotNum = +slotKey;
                            console.log(slotNum);
                            console.log(value);
                            if (slotNum >= 1 && slotNum <= 4 && value) {
                                const pid = Number(value.PID) || 0;
                                const weight = parseFloat(value.weight) || 0;
                                next[slotNum] = { PID: pid, weight: weight };
                            }
                        });

                        return next;
                    });
                }
                if(incoming.type === "loadAccepted") setReason("loadProduct was successful!")

                if(incoming.type === "loadRejected") setReason(`loadProduct was unsuccessful. reason: ${incoming.reason}`)

                if(incoming.type === "haltCargo") setIsFormEnabled(false);

                if(incoming.type === "resumeCargo") setIsFormEnabled(true);

                if (incoming.type === "holdStatus") console.log('holdStatus')
            } catch (err) {
                console.error('Bad JSON payload:', err);
            }
        };

        socket.onerror = (err) => {
            console.error('WebSocket error:', err);
        };

        socket.onclose = () => {
            console.log('WebSocket connection closed.');
        };

        return () => {
            socket.close();
        };
    }, []);

    const sendMessage = (msgObj) => {
        const json = JSON.stringify(msgObj);
        if (socketRef.current?.readyState <= WebSocket.OPEN) {
            socketRef.current.send(json);
        } else {
            console.log(socketRef.current?.readyState)
            console.warn('WebSocket not connected. Message not sent.');
        }
    };

    const totalWeight = Object.values(slots).reduce((sum, s) => sum + s.weight, 0.0);

    return (
        <FormContext.Provider value={{ showForm, setShowForm, sendMessage, isFormEnabled }}>
            <div className="hold_page">
                <h1>Hold status</h1>
                {
                    showForm
                    ?
                    <LoadForm />
                    :
                    <input type='button' value='Send a load request' onClick={() => setShowForm(true)} />
                }
                <div className='loadResult'>
                    <p>{reason}</p>
                </div>
                <p>Total weight: {totalWeight.toFixed(2)}</p>
                <table>
                    <thead>
                        <tr>
                            <th>Slot number</th>
                            <th>PID</th>
                            <th>Weight</th>
                        </tr>
                    </thead>
                    <tbody>
                        {[1, 2, 3, 4].map((slotNum) => (
                            <tr key={slotNum}>
                                <td>{slotNum}</td>
                                <td>{slots[slotNum].PID}</td>
                                <td>{slots[slotNum].weight}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </FormContext.Provider>
    );
}

export default HoldPage;