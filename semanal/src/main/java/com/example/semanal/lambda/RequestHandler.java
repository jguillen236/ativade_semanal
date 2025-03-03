package com.example.semanal.lambda;

import java.util.Map;

public interface RequestHandler<T, T1> {
    String handleRequest(Map<String, Object> input, Context context);
}
