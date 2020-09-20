package com.varkashy.apple.encrypt.api;

import com.varkashy.apple.encrypt.core.AverageResponseDto;

/**
 * Core API that handles approaches for evaluating
 * Standard deviation and Mean calculation
 * Note: I thought about defining an abstract class with
 * Standard deviation and average calculation and implementing
 * encrypted and plain version of same.
 * Considering these were very similar and the only thing differs is encryption
 * I have kept this as it is
 */
public class SimpleMathsApi {

    private final CryptoApi cryptoApi;
    private Double average;
    private Double standardDeviation;
    private int numbersProcessed;

    /*
    We keep track of current standard deviation & mean and calculate running values based on
    Welford's online algorithm
    https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Welford's_online_algorithm
    This allows to do away with storage needs and only focus on current number and keeping track of
    only previous values
     */
    public SimpleMathsApi(CryptoApi cryptoApi){
        this.cryptoApi = cryptoApi;
        average = 0.0;
        standardDeviation = 0.0;
        numbersProcessed = 0;
    }

    /**
     * Calculates the average and deviation for a number
     * returns in plain text format
     * @param number which is added to the current values
     * @return AverageResponseDto object
     */
    public AverageResponseDto pushAndRecalculate(int number){
        numbersProcessed++;
        if(numbersProcessed==1){
            average = (double) number;
            return new AverageResponseDto(String.valueOf(0.0),String.valueOf(0.0));
        }
        findAverageAndDeviation(number);
        return new AverageResponseDto(String.valueOf(average),String.valueOf(standardDeviation));
    }

    /**
     * Calculates the average and deviation and returns encrypted result
     * @param number which is added to the current values
     * @return AverageResponseDTO object with encrypted values
     * @throws Exception Throws exception if any errors with encryption
     */
    public AverageResponseDto pushRecalculateEncrypt(Integer number) throws Exception {
        numbersProcessed++;
        if(numbersProcessed==1){
            average = Double.valueOf(number);
            return new AverageResponseDto(cryptoApi.encrypt(String.valueOf(0.0)),
                    cryptoApi.encrypt(String.valueOf(0.0)));
        }
        findAverageAndDeviation(number);
        return new AverageResponseDto(cryptoApi.encrypt(String.valueOf(average)),
                cryptoApi.encrypt(String.valueOf(standardDeviation)));
    }

    /**
     * Decrypts and returns plain text for provided encrypted values
     * @param encryptedValue encrypted value that needs to be converted to plain text
     * @return plain text value
     * @throws Exception for invalid cipher text
     */
    public String decrypt(String encryptedValue) throws Exception  {
        return cryptoApi.decrypt(encryptedValue);
    }

    /**
     * utility method that invokes run time evaluation of average and standard deviation
     * @param number
     */
    private void findAverageAndDeviation(int number) {
        Double averageNew = findNewAverage(number);
        Double standardDeviationNew = findNewStandardDeviation(number,averageNew);
        average = averageNew;
        standardDeviation = standardDeviationNew;
    }

    /**
     * Implementation of Welford's Algorithm for standard deviation
     * The formula is
     * (sigma_n)^2 = ((sigma_n-1)^2) + ((number-oldMean)(number-newMean)-(sigma_n-1)^2)/n
     * and standard deviation = sigma_n so we take square root of above
     * @param number
     * @param average_new
     * @return
     */
    private Double findNewStandardDeviation(int number,Double average_new) {
        double variance = Math.pow(standardDeviation,2);
        double newVariance = variance + ((number-average)*(number-average_new)-variance)/numbersProcessed;
        return Math.sqrt(newVariance);
    }

    /**
     * Online calculation for new average value
     */
    private Double findNewAverage(int number) {
        return (average*(numbersProcessed-1)+number)/numbersProcessed;
    }
}
