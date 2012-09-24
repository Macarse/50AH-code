/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class SimpleSSLSocketFactory implements SocketFactory,
    LayeredSocketFactory {

  private SSLContext sslContext = null;

  private static SSLContext createSimpleSSLContext() throws IOException {
    try {
      SSLContext context = SSLContext.getInstance("TLS");
      context.init(null,
          new TrustManager[] { new AcceptInvalidX509TrustManager() },
          null);
      return context;
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  private SSLContext getSSLContext() throws IOException {
    if (sslContext == null) {
      sslContext = createSimpleSSLContext();
    }
    return sslContext;
  }

  public Socket connectSocket(Socket sock, String host, int port,
      InetAddress localAddress, int localPort, HttpParams params)
      throws IOException, UnknownHostException, ConnectTimeoutException {
    int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
    int soTimeout = HttpConnectionParams.getSoTimeout(params);

    InetSocketAddress remoteAddress = new InetSocketAddress(host, port);
    SSLSocket sslsock = (SSLSocket) ((sock != null) ? sock
        : createSocket());

    if ((localAddress != null) || (localPort > 0)) {
      if (localPort < 0) {
        localPort = 0;
      }
      InetSocketAddress isa = new InetSocketAddress(localAddress,
          localPort);
      sslsock.bind(isa);
    }

    sslsock.connect(remoteAddress, connTimeout);
    sslsock.setSoTimeout(soTimeout);
    return sslsock;
  }

  public Socket createSocket() throws IOException {
    return getSSLContext().getSocketFactory().createSocket();
  }

  public boolean isSecure(Socket socket)
      throws IllegalArgumentException {
    return true;
  }

  public Socket createSocket(Socket socket, String host, int port,
      boolean autoClose) throws IOException, UnknownHostException {
    return getSSLContext().getSocketFactory().createSocket(socket,
        host, port, autoClose);
  }
}
