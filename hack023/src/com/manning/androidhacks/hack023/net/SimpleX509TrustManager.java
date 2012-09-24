/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.net;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SimpleX509TrustManager implements X509TrustManager {
  private X509TrustManager standardTrustManager = null;

  public SimpleX509TrustManager(KeyStore keystore)
      throws NoSuchAlgorithmException, KeyStoreException {
    TrustManagerFactory factory = TrustManagerFactory
        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
    factory.init(keystore);
    TrustManager[] trustmanagers = factory.getTrustManagers();
    if (trustmanagers.length == 0) {
      throw new NoSuchAlgorithmException("No trust manager found");
    }
    this.standardTrustManager = (X509TrustManager) trustmanagers[0];
  }

  /**
   * @see javax.net.ssl.X509TrustManager#checkClientTrusted(X509Certificate[],
   *      String authType)
   */
  public void checkClientTrusted(X509Certificate[] certificates,
      String authType) throws CertificateException {
    standardTrustManager.checkClientTrusted(certificates, authType);
  }

  /**
   * @see javax.net.ssl.X509TrustManager#checkServerTrusted(X509Certificate[],
   *      String authType)
   */
  public void checkServerTrusted(X509Certificate[] certificates,
      String authType) throws CertificateException {
    if ((certificates != null) && (certificates.length == 1)) {
      certificates[0].checkValidity();
    } else {
      standardTrustManager.checkServerTrusted(certificates, authType);
    }
  }

  /**
   * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
   */
  public X509Certificate[] getAcceptedIssuers() {
    return this.standardTrustManager.getAcceptedIssuers();
  }
}
