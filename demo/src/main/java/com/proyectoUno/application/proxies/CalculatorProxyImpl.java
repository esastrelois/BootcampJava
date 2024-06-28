package com.proyectoUno.application.proxies;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.webservice.schema.AddRequest;
import com.example.webservice.schema.AddResponse;
import com.proyectoUno.domains.contracts.proxies.CalculatorProxy;

public class CalculatorProxyImpl extends WebServiceGatewaySupport implements CalculatorProxy {
	public double add(double a, double b) {
		var request = new AddRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (AddResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
		return response.getAddResult();
	}

}