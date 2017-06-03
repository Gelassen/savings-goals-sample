package android.security;


import android.annotation.SuppressLint;

// https://github.com/square/okhttp/issues/2533#issuecomment-223093100
public class NetworkSecurityPolicy {
    private static final android.security.NetworkSecurityPolicy INSTANCE = new NetworkSecurityPolicy();
    @SuppressLint("NewApi") public static android.security.NetworkSecurityPolicy getInstance() { return INSTANCE; }
    public boolean isCleartextTrafficPermitted() { return true; }
}
