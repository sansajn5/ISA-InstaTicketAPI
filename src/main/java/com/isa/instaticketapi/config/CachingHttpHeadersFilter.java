package com.isa.instaticketapi.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;


/**
 * This filter is used in production, to put HTTP cache headers with a long (4 years) expiration time.
 */
public class CachingHttpHeadersFilter implements Filter {

    public static final int DEFAULT_DAYS_TO_LIVE = 1461;

    public static final long DEFAULT_SECONDS_TO_LIVE = TimeUnit.DAYS.toMillis(DEFAULT_DAYS_TO_LIVE);

    public final static long LAST_MODIFIED = System.currentTimeMillis();

    private long cacheTimeToLive = DEFAULT_SECONDS_TO_LIVE;

    private ApplicationProperties applicationProperties;

    public CachingHttpHeadersFilter(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        cacheTimeToLive = TimeUnit.DAYS.toMillis(applicationProperties.getHttp().getCache().getTimeToLiveInDays());
    }

    @Override
    public void destroy() {
        // Nothing to destroy
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Cache-Control", "max-age=" + cacheTimeToLive + ", public");
        httpResponse.setHeader("Pragma", "cache");

        // Setting Expires header, for proxy caching
        httpResponse.setDateHeader("Expires", cacheTimeToLive + System.currentTimeMillis());

        // Setting the Last-Modified header, for browser caching
        httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);

        chain.doFilter(request, response);
    }
}
