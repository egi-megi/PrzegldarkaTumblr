package com.example.android.przegldarkatumblr;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egi-megi on 10.10.18.
 */

public class QueryUtilis {
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = QueryUtilis.class.getSimpleName();

    private QueryUtilis() {
    }

    public static List<Post> fetchPostData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Post} object
        List<Post> posts = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Post} objects
        return posts;
    }

    public static List<Post> fetchPostData(InputStream stream) {


        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = readFromStream(stream);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Post} object
        List<Post> posts = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Post} objects
        return posts;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            System.setProperty("http.agent", "curl/7.47.0");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent","curl/7.47.0");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the posts in Tumbir JSON results." + e.getMessage(), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Post} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Post> extractFeatureFromJson(String postJSON) {

        if (TextUtils.isEmpty(postJSON)) {
            return null;
        }

        if (postJSON.startsWith("var tumblr_api_read = ")) {
            postJSON = postJSON.substring(22);
        }

        // Create an empty ArrayList that we can start adding posts to
        List<Post> posts = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonObj = new JSONObject(postJSON);

            // Getting JSON Array node
//            JSONObject tumblr = jsonObj.getJSONObject("tumblelog");
//            String userName = tumblr.getString("name");

            JSONArray postsJSON = jsonObj.getJSONArray("posts");

            // looping through All Contacts
            for (int i = 0; i < postsJSON.length(); i++) {
                JSONObject ps = postsJSON.getJSONObject(i);
                String postId = ps.getString("id");
                String numberOfNotes = ps.getString("note-count");
                String publicationDate = ps.getString("date-gmt");
                String webUrl = ps.getString("url");
                String slugTitle = ps.getString("slug");
                String description = ps.getString("regular-body");
                Spanned descriptionSpanned = Html.fromHtml(description);
//                JSONArray contributors = ps.getJSONArray("tags");
//                StringBuilder sb = new StringBuilder();
//
//                for (int j = 0; j < contributors.length(); j++) {
//                    if (j != 0) sb.append(", ");
//
//                    sb.append(contributors.getJSONObject(j).getString("webTitle"));
//                }
//
//                String authorsString = sb.toString();

                Post post = new Post(postId, slugTitle, descriptionSpanned, publicationDate, webUrl, numberOfNotes);

                // Adding contact to contact list
                posts.add(post);
            }
        } catch (JSONException e) {
            Log.e("QueryUtilis", "Problem parsing the post JSON results", e);
        }

        return posts;
    }
}
