package io.swagger.api;

@SuppressWarnings("unused")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-12T11:24:44.655297665Z[GMT]")
public class ApiException extends Exception {
	
	private static final long serialVersionUID = -2174637728997742359L;
	
	private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
