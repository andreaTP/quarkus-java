package com.microsoft.kiota.http;

import com.microsoft.kiota.RequestHeaders;
import com.microsoft.kiota.ResponseHeaders;
import io.vertx.core.MultiMap;
import jakarta.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;

/**
 * Compatibility class to bridge OkHttp Headers and Kiota Headers
 */
public class HeadersCompatibility {
    private HeadersCompatibility() {}

    /**
     * INTERNAL METHOD, DO NOT USE DIRECTLY
     * Get the response headers from the okhttp3 headers and convert them to a ResponseHeaders object
     * @param headers the okhttp3 headers
     * @return the ResponseHeaders object
     */
    @Nonnull
    public static ResponseHeaders getResponseHeaders(@Nonnull final MultiMap headers) {
        Objects.requireNonNull(headers);
        final ResponseHeaders responseHeaders = new ResponseHeaders();
        headers.names()
                .forEach(
                        (name) -> {
                            Objects.requireNonNull(name);
                            responseHeaders.put(name, new HashSet<>(headers.getAll(name)));
                        });
        return responseHeaders;
    }

    /**
     * INTERNAL METHOD, DO NOT USE DIRECTLY
     * Get the request headers from the okhttp3 headers and convert them to a RequestHeaders object
     * @param headers the okhttp3 headers
     * @return the RequestHeaders object
     */
    @Nonnull
    public static RequestHeaders getRequestHeaders(@Nonnull final MultiMap headers) {
        Objects.requireNonNull(headers);
        final RequestHeaders requestHeaders = new RequestHeaders();
        headers.names()
                .forEach(
                        (name) -> {
                            Objects.requireNonNull(name);
                            requestHeaders.put(name, new HashSet<>(headers.getAll(name)));
                        });
        return requestHeaders;
    }

    @Nonnull
    public static MultiMap getMultiMap(@Nonnull final RequestHeaders headers) {
        MultiMap result = MultiMap.caseInsensitiveMultiMap();
        headers.entrySet().forEach((elem) -> result.add(elem.getKey(), elem.getValue()));
        return result;
    }
}
