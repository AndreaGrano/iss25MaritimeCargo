const socket = new WebSocket('ws://localhost:8013');

socket.onopen = () => {
    console.log('WebSocket connection established.');
};

socket.onmessage = (event) => {
    try {
        const incoming = JSON.parse(event.data); 
		
		var result
		if(incoming.type === 'createdProduct') {
        	result = `${incoming.type}: ${incoming.response ? incoming.PID : 'rejected'}`;
		} else {
			result = `${incoming.type}: ${incoming.response ? 'accepted' : 'rejected'}`;
		}
		
		const element = document.getElementById('result-p');
		element.innerHTML = result;
        console.log(result);
        
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

function sendCreateProduct() {
    const name = document.getElementById("name-field").value;
    const weight = parseFloat(document.getElementById("weight-field").value);
    const requestBody = {
        type: 'createProduct',
        name: name,
        weight: weight
    }
    socket.send(JSON.stringify(requestBody));
}

function sendDeleteProduct() {
    const PID = parseInt(document.getElementById("pid-field").value);
    const requestBody = {
        type: 'deleteProduct',
        PID: PID
    }
    socket.send(JSON.stringify(requestBody));
}