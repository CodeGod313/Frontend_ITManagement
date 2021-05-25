package by.vita02.frontend.services;

import com.google.gson.JsonObject;

public class LastQueryService {
    private static JsonObject lastQuery;
    private static Long userId;

    public static JsonObject getLastQuery() {
        return lastQuery;
    }

    public static void setLastQuery(JsonObject lastQuery) {
        LastQueryService.lastQuery = lastQuery;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long userId) {
        LastQueryService.userId = userId;
    }
}
