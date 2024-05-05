package glacier.actions.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matcher;

public class Api {


    private static void checkApiResponseIsOK(HttpResponse response) {
        if (response.getStatusLine().getStatusCode() != 200)
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode() + " - " + response
                    .getStatusLine().getReasonPhrase());
    }

    public static JsonElement postCall(String endpoint, JsonObject reqBody, Header[] headers) {
        JsonElement jsonResponse = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(endpoint);
            StringEntity requestBody = new StringEntity(reqBody.toString());
            request.setHeaders(headers);
            request.setEntity((HttpEntity)requestBody);
            CloseableHttpResponse closeableHttpResponse = httpClient.execute((HttpUriRequest)request);
            checkApiResponseIsOK((HttpResponse)closeableHttpResponse);
            JsonParser parser = new JsonParser();
            jsonResponse = parser.parse(EntityUtils.toString(closeableHttpResponse.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonResponse;
    }

    public static JsonElement getCall(String endpoint) {
        JsonElement jsonResponse = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpGet request = new HttpGet(endpoint);
            CloseableHttpResponse closeableHttpResponse = httpClient.execute((HttpUriRequest)request);
            checkApiResponseIsOK((HttpResponse)closeableHttpResponse);
            JsonParser parser = new JsonParser();
            jsonResponse = parser.parse(EntityUtils.toString(closeableHttpResponse.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    public static JsonElement getCall(String endpoint, Header[] headers) {
        JsonElement jsonResponse = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpGet request = new HttpGet(endpoint);
            request.setHeaders(headers);
            CloseableHttpResponse closeableHttpResponse = httpClient.execute((HttpUriRequest)request);
            checkApiResponseIsOK((HttpResponse)closeableHttpResponse);
            JsonParser parser = new JsonParser();
            jsonResponse = parser.parse(EntityUtils.toString(closeableHttpResponse.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    public static JsonElement putCall(String endpoint, JsonObject reqBody, Header[] headers) {
        JsonElement jsonResponse = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPut request = new HttpPut(endpoint);
            StringEntity requestBody = new StringEntity(reqBody.toString());
            request.setHeaders(headers);
            request.setEntity((HttpEntity)requestBody);
            CloseableHttpResponse closeableHttpResponse = httpClient.execute((HttpUriRequest)request);
            checkApiResponseIsOK((HttpResponse)closeableHttpResponse);
            JsonParser parser = new JsonParser();
            jsonResponse = parser.parse(EntityUtils.toString(closeableHttpResponse.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonResponse;
    }

    public static void ValidateResponseWithScema(String url, String schema) {
        ((ValidatableResponse)((ValidatableResponse)RestAssured.get(url, new Object[0]).then()).assertThat())
                .body((Matcher)JsonSchemaValidator.matchesJsonSchemaInClasspath(schema), new Matcher[0]);
    }
}
