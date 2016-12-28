package DesignPatterns.decorator.starbuzz;

public class PineApple extends CondimentDecorator {

    Beverage beverage;

    public PineApple(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Pineapple";
    }

    @Override
    public double cost() {
        return .20 + beverage.cost();
    }

}
