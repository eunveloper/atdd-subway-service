package nextstep.subway.path.domain;

public class Price {

    private final int BASIC_DISTANCE = 10;
    private final int EXCESS_DISTANCE = 50;
    private final int BASIC_PRICE = 1250;

    private int price;

    public int value() {
        return price;
    }

    public void calculatorPrice(int distance) {
        this.price = calculatorPriceByDistance(distance);
    }

    private int calculatorPriceByDistance(int distance) {
        if (distance <= BASIC_DISTANCE) {
            return BASIC_PRICE;
        }
        if (distance > BASIC_DISTANCE && distance <= EXCESS_DISTANCE) {
            return BASIC_PRICE + calculateOverFare(5, distance - BASIC_DISTANCE);
        }
        return BASIC_PRICE + calculateOverFare(8, distance - BASIC_DISTANCE);
    }

    private int calculateOverFare(int overStandard, int distance) {
        return (int) ((Math.ceil((distance - 1) / overStandard) + 1) * 100);
    }
}
