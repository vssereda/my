package utils;

public class InputValidator {
    public boolean validateCoordinatesX(Integer x) {
        return x <= 394;
    }

    public boolean validatePositiveNumber(Number value) {
        return value == null || value.doubleValue() > 0;
    }
}