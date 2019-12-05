package com.nieroda.networking;

import android.net.Uri;

import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

class OkHttpClientUtil {

    private static final int TIMEOUT = 30;
    private static final TimeUnit TIMEOUT_TIME_UNIT = TimeUnit.SECONDS;

    static OkHttpClient createOkHttpClient(final Uri uri) {
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    final X509Certificate[] chain,
                    final String authType) {
            }

            @Override
            public void checkServerTrusted(
                    final X509Certificate[] chain,
                    final String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        try {
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new java.security.SecureRandom());

            final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
            httpClient.sslSocketFactory(sslContext.getSocketFactory(), trustManager);
            httpClient.hostnameVerifier((hostname, session) -> hostname.contains(uri.getHost()));
            httpClient.connectTimeout(TIMEOUT, TIMEOUT_TIME_UNIT);
            httpClient.readTimeout(TIMEOUT, TIMEOUT_TIME_UNIT);
            return httpClient.build();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
