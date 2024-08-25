package io.swagger.api;

@SuppressWarnings("unused")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-12T11:24:44.655297665Z[GMT]")
public class NotFoundException extends ApiException {
    private static final long serialVersionUID = -8000903005649088467L;
	private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
