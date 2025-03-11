package com.example.benefly.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for creating consistent JSON responses
 */
public class JsonResponse {
    
    /**
     * Create a success response with data
     * 
     * @param data Response data
     * @return Map representing the JSON response
     */
    public static Map<String, Object> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "Success");
        response.put("data", data);
        return response;
    }
    
    /**
     * Create a success response with data and custom message
     * 
     * @param data Response data
     * @param message Success message
     * @return Map representing the JSON response
     */
    public static Map<String, Object> success(Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", message);
        response.put("data", data);
        return response;
    }
    
    /**
     * Create an error response
     * 
     * @param code Error code
     * @param message Error message
     * @return Map representing the JSON response
     */
    public static Map<String, Object> error(String code, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        return response;
    }
}
