package com.varkashy.apple.encrypt.resources;

import com.varkashy.apple.encrypt.api.CryptoApi;
import com.varkashy.apple.encrypt.api.SimpleMathsApi;
import com.varkashy.apple.encrypt.core.AverageResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleMathsResourceTest {
    private static SimpleMathsApi simpleMathsApi;

    @BeforeEach
    public void setup(){
        CryptoApi cryptoApi = new CryptoApi();
        simpleMathsApi = new SimpleMathsApi(cryptoApi);
    }

    @Test
    void pushAndRecalculate() {
        AverageResponseDto response = simpleMathsApi.pushAndRecalculate(4);
        assertTrue(Double.parseDouble(response.getAverageValue())==0.0
                && Double.parseDouble(response.getStandardDeviation()) == 0.0);
        response = simpleMathsApi.pushAndRecalculate(7);
        assertTrue(Double.parseDouble(response.getAverageValue())==5.5
                && Double.parseDouble(response.getStandardDeviation()) == 1.5);
    }

    @Test
    void pushRecalculateEncrypt() throws Exception {
        AverageResponseDto response = simpleMathsApi.pushRecalculateEncrypt(4);
        assertEquals("0.0",simpleMathsApi.decrypt(response.getAverageValue()));
        assertEquals("0.0",simpleMathsApi.decrypt(response.getStandardDeviation()));
    }
}