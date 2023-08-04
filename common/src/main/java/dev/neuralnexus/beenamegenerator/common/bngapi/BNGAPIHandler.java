package dev.neuralnexus.beenamegenerator.common.bngapi;

import dev.neuralnexus.taterlib.lib.gson.Gson;
import dev.neuralnexus.taterlib.lib.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class BNGAPIHandler {
    private final String baseUri;
    private final String dataSource;
    private final String authToken;

    /**
     * Creates a new BNGAPIHandler instance.
     * @param baseURI The base URI of the API.
     */
    public BNGAPIHandler(String baseURI) {
        this.baseUri = baseURI;
        this.authToken = null;

        if (baseURI.charAt(baseURI.length() - 1) == '/') {
            this.dataSource = this.baseUri;
        } else {
            this.dataSource = this.baseUri + "/";
        }
    }

    /**
     * Creates a new BNGAPIHandler instance.
     * @param baseURI The base URI of the API.
     * @param authToken The auth token to use.
     */
    public BNGAPIHandler(String baseURI, String authToken) {
        this.baseUri = baseURI;
        this.authToken = authToken;

        if (baseURI.charAt(baseURI.length() - 1) == '/') {
            this.dataSource = this.baseUri;
        } else {
            this.dataSource = this.baseUri + "/";
        }
    }

    /**
     * General API call method.
     * @param endpoint The endpoint to call.
     * @param requestMethod The request method to use.
     * @param returnClass The class to use when serializing the response.
     */
    public Object APICall(String endpoint, String requestMethod, Class<?> returnClass) {
        try {
            URL url = new URL(this.dataSource + endpoint);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod(requestMethod);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            // If authToken is not null, set the Authorization header.
            if (this.authToken != null) {
                con.setRequestProperty("Authorization", "Bearer " + this.authToken);
            }

            con.setRequestProperty("User-Agent", "bee-name-generator-plugin");
            con.setConnectTimeout(5000);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            Gson gson = new GsonBuilder().create();
            return gson.fromJson(br.readLine(), returnClass);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * General API call method.
     * @param endpoint The endpoint to call.
     * @param requestMethod The request method to use.
     */
    public Map<?,?> APICall(String endpoint, String requestMethod) {
        return (Map<?, ?>) this.APICall(endpoint, requestMethod, Map.class);
    }

    /**
     * Get a bee name.
     */
    public Map<?,?> getBeeName() {
        return this.APICall("name", "GET");
    }

    /**
     * Upload a bee name (Authentication Required).
     * @param name The name to upload.
     */
    public Map<?,?> uploadBeeName(String name) {
        return this.APICall("name/" + name, "POST");
    }

    /**
     * Submit a bee name.
     * @param name The name to submit.
     */
    public Map<?,?> submitBeeName(String name) {
        return this.APICall("suggestion/" + name, "POST");
    }

    /**
     * Get bee name suggestions (Authentication Required).
     * @param amount The amount of suggestions to get.
     */
    public Map<?,?> getSuggestions(int amount) {
        return this.APICall("suggestion/" + amount, "GET");
    }

    /**
     * Accept a bee name suggestion (Authentication Required).
     * @param name The name to accept.
     */
    public Map<?,?> acceptSuggestion(String name) {
        return this.APICall("suggestion/" + name, "PUT");
    }

    /**
     * Reject a bee name suggestion (Authentication Required).
     * @param name The name to reject.
     */
    public Map<?,?> rejectSuggestion(String name) {
        return this.APICall("suggestion/" + name, "DELETE");
    }
}
