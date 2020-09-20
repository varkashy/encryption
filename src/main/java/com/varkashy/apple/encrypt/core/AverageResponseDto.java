package com.varkashy.apple.encrypt.core;

/*
DTO object for representing the run time evaluation of
standard deviation and average value
 */
public class AverageResponseDto {
    String standardDeviation;
    String averageValue;

    public AverageResponseDto(String _average, String _standardDeviation) {
        this.averageValue = _average;
        this.standardDeviation = _standardDeviation;
    }

    public String getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(String standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public String getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(String averageValue) {
        this.averageValue = averageValue;
    }

    @Override
    public String toString() {
        return "{" +"averageValue=" + averageValue +
                ", standardDeviation=" + standardDeviation +
                '}';
    }
}
