package com.eventsvk.util.function;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.List;

public interface MethodCaller {
    List<?> callMethod(String[] args) throws ClientException, ApiException;
}
