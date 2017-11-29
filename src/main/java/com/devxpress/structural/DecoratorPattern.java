package com.devxpress.structural;

/**
 * Decorator - Structural
 *
 * Allows behavior to be added to an individual object, either statically or dynamically,
 * without affecting the behavior of other objects from the same class.
 *
 * One class takes in another class, both of which extend the same abstract class, and
 * adds functionality.
 *
 * Recognizeable by creational methods taking an instance of same abstract/interface type
 * which adds additional behaviour
 *
 * Usage examples in Java APIs:
 *
 * All subclasses of java.io.InputStream, OutputStream, Reader and Writer have a constructor
 * taking an instance of same type.
 *
 * java.util.Collections, the checkedXXX(), synchronizedXXX() and unmodifiableXXX() methods.
 *
 * javax.servlet.http.HttpServletRequestWrapper and HttpServletResponseWrapper
 */

public class DecoratorPattern {

    public static void main(String[] args) {
        Coffee c = new SimpleCoffee();
        printInfo(c);

        c = new WithMilk(c);
        printInfo(c);

        c = new WithSprinkles(c);
        printInfo(c);
    }

    private static void printInfo(Coffee c) {
        System.out.println("Cost: " + c.getCost() + "; Ingredients: " + c.getIngredients());
    }
}

// The interface Coffee defines the functionality of Coffee implemented by decorator
interface Coffee {
    double getCost(); // Returns the cost of the coffee
    String getIngredients(); // Returns the ingredients of the coffee
}

// Extension of a simple coffee without any extra ingredients
class SimpleCoffee implements Coffee {
    @Override
    public double getCost() {
        return 1;
    }

    @Override
    public String getIngredients() {
        return "Coffee";
    }
}

// Abstract decorator class - note that it implements Coffee interface
abstract class CoffeeDecorator implements Coffee {
    protected final Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee c) {
        this.decoratedCoffee = c;
    }

    public double getCost() { // Implementing methods of the interface
        return decoratedCoffee.getCost();
    }

    public String getIngredients() {
        return decoratedCoffee.getIngredients();
    }
}

// Decorator WithMilk mixes milk into coffee.
// Note it extends CoffeeDecorator.
class WithMilk extends CoffeeDecorator {
    public WithMilk(Coffee c) {
        super(c);
    }

    public double getCost() { // Overriding methods defined in the abstract superclass
        return super.getCost() + 0.5;
    }

    public String getIngredients() {
        return super.getIngredients() + ", Milk";
    }
}

// Decorator WithSprinkles mixes sprinkles onto coffee.
// Note it extends CoffeeDecorator.
class WithSprinkles extends CoffeeDecorator {
    public WithSprinkles(Coffee c) {
        super(c);
    }

    public double getCost() {
        return super.getCost() + 0.2;
    }

    public String getIngredients() {
        return super.getIngredients() + ", Sprinkles";
    }
}