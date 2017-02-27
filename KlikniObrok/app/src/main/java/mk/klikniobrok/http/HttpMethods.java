package mk.klikniobrok.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import mk.klikniobrok.models.User;

/**
 * Created by gjorgjim on 1/16/17.
 */

public class HttpMethods {
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";

    public static String doGet(String url, HashMap<String, String> params) {
        return doGet(url, params, null);
    }

    public static String doGet(String url, HashMap<String, String> params, String authToken) {
        URL connUrl;
        StringBuilder response = new StringBuilder();
        try {
            StringBuilder newUrl = new StringBuilder(url);
            if(params != null) {
                newUrl.append(createParamString(params));
            }
            connUrl = new URL(newUrl.toString());
            Log.d("newurl", newUrl.toString());
            HttpsURLConnection httpConnection = getHttpUrlConnection(connUrl, HTTP_GET);

            if(authToken != null) {
                httpConnection.setRequestProperty("Authorization", "Bearer: " + authToken);
            }

            String line;
            BufferedReader reader;
            if(httpConnection.getResponseCode() >= HttpsURLConnection.HTTP_OK
                    && httpConnection.getResponseCode() < HttpsURLConnection.HTTP_BAD_REQUEST) {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public static String doPost(String url, HashMap<String, String> params) {
        URL connUrl;
        StringBuilder response = new StringBuilder();

        try {
            connUrl = new URL(url);

            HttpsURLConnection httpConnection = getHttpUrlConnection(connUrl, HTTP_POST);

            OutputStream outputStream = httpConnection.getOutputStream();
            BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            outputWriter.write(createParamString(params));
            outputWriter.flush();
            outputWriter.close();
            outputStream.close();

            String line;
            BufferedReader reader;
            if(httpConnection.getResponseCode() >= HttpsURLConnection.HTTP_OK
                    && httpConnection.getResponseCode() < HttpsURLConnection.HTTP_BAD_REQUEST) {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public static String doPost(String url, User body) {
        URL connUrl;
        StringBuilder response = new StringBuilder();
        try {
            connUrl = new URL(url);

            HttpsURLConnection httpConnection = getHttpUrlConnection(connUrl, HTTP_POST);
            httpConnection.setRequestProperty("Content-Type", "application/json");

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            OutputStream outputStream = httpConnection.getOutputStream();
            BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            outputWriter.write(gson.toJson(body));
            outputWriter.flush();
            outputWriter.close();
            outputStream.close();

            String line;
            BufferedReader reader;
            if(httpConnection.getResponseCode() >= HttpsURLConnection.HTTP_OK
                    && httpConnection.getResponseCode() < HttpsURLConnection.HTTP_BAD_REQUEST) {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    private static String createParamString(HashMap<String, String> params) {
        StringBuilder paramString = new StringBuilder();
        paramString.append("?");
        boolean first = true;
        for(Map.Entry<String, String> param : params.entrySet()) {
            if(first) {
                first = false;
            } else {
                paramString.append("&");
            }
            try {
                paramString.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                paramString.append("=");
                paramString.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            } catch (Exception e) {
                paramString.append("");
                e.printStackTrace();
            }
        }
        return paramString.toString();
    }

    private static HttpsURLConnection getHttpUrlConnection(URL connUrl, String httpMethod) {
        HttpsURLConnection httpConnection = null;
        try {
            httpConnection = (HttpsURLConnection) connUrl.openConnection();
            httpConnection.setReadTimeout(15000);
            httpConnection.setConnectTimeout(15000);
            httpConnection.setRequestMethod(httpMethod);
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(httpMethod.equals(HTTP_POST));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpConnection;
    }
}
