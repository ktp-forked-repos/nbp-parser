package pl.parser.nbp.http;


import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import pl.parser.nbp.http.model.HttpServiceResponse;

import java.io.IOException;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public abstract class AbstractHttpClient {
    protected final CloseableHttpClient httpClient = getHttpClient();

    protected abstract String  getHost();
    protected abstract Integer getPort();
    protected abstract String  getServiceName();
    protected abstract CloseableHttpClient getHttpClient();


    protected String get(String url) throws IOException {
        HttpGet request = new HttpGet(getBaseUrl() + url);
        return handleResponse(getResponse(request));
    }

    private HttpServiceResponse getResponse(HttpGet request) throws IOException {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            byte[] body = response.getEntity() == null ? new byte[0] : EntityUtils.toByteArray(response.getEntity());
            return new HttpServiceResponse(statusCode, body);
        } finally {
            close(response);
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

    private String getBaseUrl() {
        if (allNotNull(getHost(), getPort())) {
            return format("http://%s:%s", getHost(), getPort());
        }
        if (isNotEmpty(getHost())) {
            return format("http://%s", getHost());
        }
        throw new RuntimeException(format("Bad request: http://%s:%s ",getHost(), getPort()));
    }

    private void close(CloseableHttpResponse response) {
        if (nonNull(response)) {
            try{
                response.close();
            } catch (IOException e) {
                throw new RuntimeException("Unable to close http client");
            }
        }
    }
}
