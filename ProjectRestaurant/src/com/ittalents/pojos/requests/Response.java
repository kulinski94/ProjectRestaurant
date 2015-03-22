package com.ittalents.pojos.requests;

public class Response {
	
	//private String message;
	private Object response;

	public Response(Object response) {
		super();
		/*this.message = message;*/
		this.response = response;
	}
	
	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	/*public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}*/

}
