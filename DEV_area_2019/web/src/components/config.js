const IP = "https://areaepitechrennes.azurewebsites.net";
import axios from 'axios';
import { cornsilk } from 'color-name';
export default {
	IP: IP,
	callServerApi: (route, method = "GET", body = {}, resultCallback = undefined, responseCallback = undefined) =>
	{
		var myHeaders = new Headers();
        myHeaders.append('Content-Type', 'application/json');

        var raw = JSON.stringify(body);
        var requestOptions = {
            method: method,
            headers: myHeaders,
            mode: "cors",
            body: method != "GET" ? raw : undefined,
            redirect: 'follow',
            credentials: "include",
            cache: "default"
        };
        console.log("requestOptions",requestOptions)   
        
        fetch(`${IP}/${route}`, requestOptions)
		.then(response => {
			if (responseCallback)
				responseCallback(response);
		 	return response.text();
        })
        .then(result => {
            if (resultCallback)
            	resultCallback(result);
            return result;
        })
        .catch(err => alert(err));
    }
};