package flavio.silva.cadastro.processos.dto;

import org.springframework.http.HttpStatus;

public class ApiResponse {
	private String message;
	private HttpStatus status;
	private Object response;

	public ApiResponse(String message, HttpStatus status, Object response) {
		this.message = message;
		this.status = status;
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
