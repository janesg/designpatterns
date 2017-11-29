package com.devxpress.behavioural;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy - Behavioural
 *
 * Enables selection of an algorithm at runtime:
 *  - defines a family of algorithms
 *  - encapsulates each algorithm
 *  - makes the algorithms interchangeable within that family
 *
 * Strategy pattern lets the algorithm vary independently from clients that use it.
 *
 * An object controls which of a family of methods is called. Each method is in its own class
 * that extends a common abstract base class/interface.
 *
 * Recognizeable by behavioral methods in an abstract/interface type
 * which invokes a method in an implementation of a different abstract/interface
 * type which has been passed-in as method argument into the strategy implementation.
 *
 * Usage examples in Java APIs:
 *
 * java.util.Comparator#compare()
 *  - executed by among others Collections#sort().
 *
 * javax.servlet.http.HttpServlet
 *  - the service() and all doXXX() methods take HttpServletRequest and HttpServletResponse
 *    and the implementor has to process them (and not to get hold of them as instance variables!).
 *
 * javax.servlet.Filter#doFilter()
 */

public class StrategyPattern {

    public static void main(final String[] arguments) {
        Customer firstCustomer = new Customer(new NormalStrategy());

        // Normal billing
        firstCustomer.add(1.0, 1);

        // Start Happy Hour
        firstCustomer.setStrategy(new HappyHourStrategy());
        firstCustomer.add(1.0, 2);

        // New Customer
        Customer secondCustomer = new Customer(new HappyHourStrategy());
        secondCustomer.add(0.8, 1);
        // The Customer pays
        firstCustomer.printBill();

        // End Happy Hour
        secondCustomer.setStrategy(new NormalStrategy());
        secondCustomer.add(1.3, 2);
        secondCustomer.add(2.5, 1);
        secondCustomer.printBill();
    }
}

class Customer {

    private List<Double> drinks;
    private BillingStrategy strategy;

    public Customer(final BillingStrategy strategy) {
        this.drinks = new ArrayList<Double>();
        this.strategy = strategy;
    }

    public void add(final double price, final int quantity) {
        drinks.add(strategy.getActPrice(price*quantity));
    }

    // Payment of bill
    public void printBill() {
        double sum = 0;
        for (Double i : drinks) {
            sum += i;
        }
        System.out.println("Total due: " + sum);
        drinks.clear();
    }

    // Set Strategy
    public void setStrategy(final BillingStrategy strategy) {
        this.strategy = strategy;
    }

}

interface BillingStrategy {
    double getActPrice(final double rawPrice);
}

// Normal billing strategy (unchanged price)
class NormalStrategy implements BillingStrategy {

    @Override
    public double getActPrice(final double rawPrice) {
        return rawPrice;
    }
}

// Strategy for Happy hour (50% discount)
class HappyHourStrategy implements BillingStrategy {

    @Override
    public double getActPrice(final double rawPrice) {
        return rawPrice*0.5;
    }
}
