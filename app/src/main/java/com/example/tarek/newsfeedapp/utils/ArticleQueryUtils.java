package com.example.tarek.newsfeedapp.utils;

import android.util.Log;

import com.example.tarek.newsfeedapp.article.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ArticleQueryUtils {

    public static final int ZERO = 0;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int THE_REQUEST_IS_OK = 200;
    private static final int READ_TIME_OUT = 10000;
    private static final int CONNECT_TIME_OUT= 15000;
    private static final int SLEEP_TIME= 2000;
    private static final String MALFORMED_URL_EXCEPTION ="MalformedURLException";
    private static final String JSON_EXCEPTION ="JSONException";
    private static final String I_O_EXCEPTION ="IOException";
    private static final String INTERRUPTED_EXCEPTION ="InterruptedException";
    private static final String NULL_POINTER_EXCEPTION ="NullPointerException";
    private static final String REQUESTED_METHOD="GET";
    private static final String RESPONSE="response";
    private static final String RESULTS="results";
    private static final String WEB_TITLE="webTitle";
    private static final String SECTION_NAME="sectionName";
    private static final String WEB_PUBLICATION_DATE="webPublicationDate";
    private static final String WEB_URL="webUrl";
    private static final String TAG = ArticleQueryUtils.class.getSimpleName();
    public static final String EMPTY_NAME = "";

    public static List<Article> fetchArticlesData(String urlString){

        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            printLogException(TAG, e , INTERRUPTED_EXCEPTION);
        }

        List<Article> articles;
        try {
            URL url = createUrl(urlString);
            String jsonResponse = "";
            jsonResponse = makeHttpRequest(url);
            articles = getDataFromJsonResponse(jsonResponse);
        }catch (NullPointerException e){
            printLogException(TAG, e , NULL_POINTER_EXCEPTION);
            return null;
        }
        return articles;
    }

    private static List<Article> getDataFromJsonResponse(String jsonResponse){
        List<Article> articles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject response = jsonObject.getJSONObject(RESPONSE);
            JSONArray results = response.getJSONArray(RESULTS);

            for (int i = ZERO ; i < results.length() ; i++){
                JSONObject currentObject = results.getJSONObject(i);

                String title = currentObject.getString(WEB_TITLE);
                String section = currentObject.getString(SECTION_NAME);
                String author = EMPTY_NAME; // there is no author name in this API
                String date = currentObject.getString(WEB_PUBLICATION_DATE); // it is been formatted in this API
                String url = currentObject.getString(WEB_URL);

                articles.add(new Article(title,section,author,date,url));
            }
        } catch (JSONException e) {
            printLogException(TAG, e , JSON_EXCEPTION);
            return null;
        }
        return articles;
    }

    private static String readInputStream (InputStream inputStream) {
        String jsonResponse = "";
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream , Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();

        try {
            String line = reader.readLine();
            while (line != null){
                builder.append(line);
                line = reader.readLine();
            }
            jsonResponse = builder.toString();
        } catch (IOException e) {
            printLogException(TAG, e , I_O_EXCEPTION);
            return null;
        }
        return jsonResponse;
    }

    private static String makeHttpRequest (URL url){
        String jsonResponse = "";
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(REQUESTED_METHOD);
            urlConnection.setReadTimeout(READ_TIME_OUT);
            urlConnection.setConnectTimeout(CONNECT_TIME_OUT);
            urlConnection.connect();


            // The app checks whether the device is connected to the internet and responds appropriately.
            // The result of the request is validated to account for a bad server response or lack of server response.
            int response = urlConnection.getResponseCode();
            if (response == THE_REQUEST_IS_OK){
                InputStream stream = urlConnection.getInputStream();
                jsonResponse = readInputStream(stream);
            }else if (response == BAD_GATEWAY || response == SERVICE_UNAVAILABLE){
                return jsonResponse; // as empty
            }
        }catch (IOException e){
            printLogException(TAG, e , I_O_EXCEPTION);
            return null;
        }

        return jsonResponse;
    }

    private static URL createUrl(String urlString){
        URL url ;
        try{
            url = new URL (urlString);
        }catch (MalformedURLException e){
            printLogException(TAG, e , MALFORMED_URL_EXCEPTION);
            return null;
        }
        return url;
    }

    public static void printLogException(String tag ,Exception e , String msg){
        Log.e(tag , msg + e );
    }
}
