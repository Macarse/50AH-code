/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.manning.androidhacks.hack023.exception.AndroidHacksException;

public class HttpHelper {

  private static final String ACCEPT = "Accept";
  private static final String TAG = HttpHelper.class.getCanonicalName();
  private static final int CONN_TIMEOUT = 20000;
  private static final String POST_METHOD = "POST";
  private static final String GET_METHOD = "GET";
  private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
  private static DefaultHttpClient mHttpClient;

  public static void maybeCreateHttpClient() {
    if (mHttpClient == null) {
      mHttpClient = setupHttpClient();
    }
  }

  public static String getHttpResponseAsStringUsingPOST(String url,
      String requestBodyString) throws AndroidHacksException {
    return getHttpResponseAsString(url, requestBodyString, true);
  }

  private static String getHttpResponseAsString(String url,
      String requestBodyString, boolean usePost)
      throws AndroidHacksException {

    maybeCreateHttpClient();

    String method = usePost ? POST_METHOD : GET_METHOD;
    return getHttpResponseAsString(url, method, DEFAULT_CONTENT_TYPE,
        requestBodyString);
  }

  public static String getHttpResponseAsString(String url,
      String requestbodyString) throws AndroidHacksException {
    return getHttpResponseAsString(url, GET_METHOD,
        DEFAULT_CONTENT_TYPE, requestbodyString);
  }

  public static String getHttpResponseAsString(String url,
      String method, String contentType, String requestBodyString)
      throws AndroidHacksException {
    maybeCreateHttpClient();

    String responseString = null;
    try {
      responseString = handleRequest(url, method, contentType,
          requestBodyString, new BasicResponseHandler());
    } catch (Exception e) {
      handleException(e);
    }

    return responseString;
  }

  private static void handleException(Exception exception)
      throws AndroidHacksException {
    if (exception instanceof HttpResponseException) {
      throw new AndroidHacksException("Response from server: "
          + ((HttpResponseException) exception).getStatusCode() + ""
          + exception.getMessage());
    } else {
      throw new AndroidHacksException(exception.getMessage());
    }
  }

  private static String handleRequest(String url, String method,
      String contentType, String requestBodyString,
      ResponseHandler<String> responseHandler)
      throws UnsupportedEncodingException, IOException,
      ClientProtocolException {
    String responseString;

    if (POST_METHOD.equals(method)) {
      responseString = doPost(url, contentType, requestBodyString,
          responseHandler);
    } else {
      responseString = doGet(url, contentType, requestBodyString,
          responseHandler);
    }

    return responseString;
  }

  private static String doGet(String url, String contentType,
      String requestBodyString, ResponseHandler<String> responseHandler)
      throws IOException, ClientProtocolException {
    if (requestBodyString != null) {
      url += "?" + requestBodyString;
    }

    Log.d(TAG, "URL: " + url);
    HttpGet getRequest = new HttpGet(url);
    getRequest.setHeader(HTTP.CONTENT_TYPE, contentType);
    getRequest.setHeader(ACCEPT, contentType);
    return mHttpClient.execute(getRequest, responseHandler);
  }

  private static String doPost(String url, String contentType,
      String requestBodyString, ResponseHandler<String> responseHandler)
      throws UnsupportedEncodingException, IOException,
      ClientProtocolException {
    HttpPost postRequest = new HttpPost(url);
    postRequest.setHeader(HTTP.CONTENT_TYPE, contentType);
    postRequest.setHeader(ACCEPT, contentType);
    postRequest.setHeader("POSTDATA", requestBodyString);

    Log.d(TAG, "URL: " + url + " with params " + requestBodyString);

    return mHttpClient.execute(postRequest, responseHandler);
  }

  private static DefaultHttpClient setupHttpClient() {
    HttpParams httpParams = new BasicHttpParams();
    setConnectionParams(httpParams);
    SchemeRegistry schemeRegistry = registerFactories();
    ClientConnectionManager clientConnectionManager = new ThreadSafeClientConnManager(
        httpParams, schemeRegistry);

    DefaultHttpClient client = new DefaultHttpClient(
        clientConnectionManager, httpParams);
    client.setRedirectHandler(new FollowPostRedirectHandler());

    return client;
  }

  private static SchemeRegistry registerFactories() {
    SchemeRegistry schemeRegistry = new SchemeRegistry();
    schemeRegistry.register(new Scheme("http", PlainSocketFactory
        .getSocketFactory(), 80));
    schemeRegistry.register(new Scheme("https",
        new SimpleSSLSocketFactory(), 443));
    return schemeRegistry;
  }

  private static void setConnectionParams(HttpParams httpParams) {
    HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
    HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMEOUT);
    HttpConnectionParams.setSoTimeout(httpParams, CONN_TIMEOUT);
  }
}
