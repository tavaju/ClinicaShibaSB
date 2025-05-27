package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpStatus;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor());
    }

    /**
     * Simple rate limiting interceptor.
     * Limits API requests to 100 requests per IP address per minute.
     */
    private static class RateLimitInterceptor implements HandlerInterceptor {
        private final Map<String, RequestCount> requestCounts = new ConcurrentHashMap<>();
        private static final int MAX_REQUESTS_PER_MINUTE = 100;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            // Only rate limit API requests
            if (!request.getRequestURI().startsWith("/api/")) {
                return true;
            }

            String clientIp = getClientIp(request);
            RequestCount count = requestCounts.computeIfAbsent(clientIp,
                    k -> new RequestCount(System.currentTimeMillis()));

            long currentTime = System.currentTimeMillis();
            if (currentTime - count.getStartTime() > TimeUnit.MINUTES.toMillis(1)) {
                // Reset if more than a minute has passed
                count.setCount(1);
                count.setStartTime(currentTime);
            } else if (count.getCount() >= MAX_REQUESTS_PER_MINUTE) {
                // Too many requests
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Rate limit exceeded. Please try again later.");
                return false;
            } else {
                // Increment request count
                count.incrementCount();
            }

            return true;
        }

        private String getClientIp(HttpServletRequest request) {
            String xForwardedFor = request.getHeader("X-Forwarded-For");
            if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                return xForwardedFor.split(",")[0].trim();
            }
            return request.getRemoteAddr();
        }
    }

    /**
     * Helper class to track request counts for rate limiting
     */
    private static class RequestCount {
        private int count;
        private long startTime;

        public RequestCount(long startTime) {
            this.count = 1;
            this.startTime = startTime;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void incrementCount() {
            this.count++;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }
    }
}