package pl.parser.nbp.http;


import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import pl.parser.nbp.http.model.HttpServiceResponse;

import java.io.IOException;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.http.util.EntityUtils.toByteArray;

public abstract class AbstractHttpClient {

    protected final CloseableHttpClient httpClient = HttpClients.createDefault();

    protected abstract String getBaseUrl();
    protected abstract String getServiceName();

    protected String get(String url) throws IOException {
        HttpGet request = new HttpGet(getBaseUrl() + url);
        return handleResponse(getResponse(request));
    }

    private HttpServiceResponse getResponse(HttpGet request) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            byte[] body = nonNull(response.getEntity()) ? toByteArray(response.getEntity()) : null;
            return new HttpServiceResponse(statusCode, body);
        }
    }

    private String handleResponse(HttpServiceResponse response) {
        if (response.getStatus() != HttpStatus.SC_OK) {
            throw new RuntimeException(format("HttpStatus not ok -> Status: %s: response: %s", response.getStatus(), new String(response.getResponse())));
        }
        if (response.getResponse() == null) {
            throw new RuntimeException(format("Empty response from %s", getServiceName()));
        }
        return new String(response.getResponse());
    }
}
