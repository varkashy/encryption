package com.varkashy.apple.encrypt.api;

import com.varkashy.apple.encrypt.core.AverageResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMathsApiTest {

    private final int[] TEST_NUMS = new int[]{4,7,6,11,15};
    private final double EXPECTED_MEAN = 8.6;
    private final double EXPECTED_DEVIATION = 3.929;
    private static CryptoApi cryptoApi;
    private static SimpleMathsApi simpleMathsApi;

    @BeforeEach
    public void setup(){
        cryptoApi = new CryptoApi();
        simpleMathsApi = new SimpleMathsApi(cryptoApi);
    }

    @Test
    void pushAndRecalculate() {
        try {
            AverageResponseDto responseDto = runFlowForAllNumbers(false);
            BigDecimal roundedValue = new BigDecimal(responseDto.getStandardDeviation());
            roundedValue = roundedValue.setScale(3, RoundingMode.HALF_UP);
            assertNotNull(responseDto);
            assertEquals(EXPECTED_MEAN, Double.valueOf(responseDto.getAverageValue()));
            assertEquals(EXPECTED_DEVIATION, roundedValue.doubleValue());
        }
        catch(Exception ex){
            fail();
        }
    }

    @Test
    void pushRecalculateEncrypt() {
        try {
        AverageResponseDto responseDto = runFlowForAllNumbers(true);
        BigDecimal roundedValue = new BigDecimal(cryptoApi.decrypt(responseDto.getStandardDeviation()));
        roundedValue = roundedValue.setScale(3, RoundingMode.HALF_UP);
        assertNotNull(responseDto);
        assertEquals(EXPECTED_MEAN,Double.valueOf(cryptoApi.decrypt(responseDto.getAverageValue())));
        assertEquals(EXPECTED_DEVIATION,roundedValue.doubleValue());
        } catch (Exception exception) {
           fail();
        }
    }

    private AverageResponseDto runFlowForAllNumbers(boolean isEncrypt) throws Exception {
        AverageResponseDto responseDto = null;
        for (int num : TEST_NUMS) {
            if(isEncrypt){
                responseDto = simpleMathsApi.pushRecalculateEncrypt(num);
            }
            else {
                responseDto = simpleMathsApi.pushAndRecalculate(num);
            }
        }
        return responseDto;
    }
}