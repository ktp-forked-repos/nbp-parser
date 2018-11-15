package pl.parser.nbp.http.model;


public class HttpServiceResponse {
    private final int status;
    private final byte[] response;

    public HttpServiceResponse(int status, byte[] response) {
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public byte[] getResponse() {
        return response;
    }
}
