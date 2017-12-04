package com.devxpress.behavioural;

/**
 * Visitor - Behavioural
 * <p>
 * Used to separate an algorithm from an object structure on which it operates.
 * <p>
 * A practical result of this separation is the ability to add new operations to existent object
 * structures without modifying the structures. It is one way to follow the open/closed principle.
 * <p>
 * In essence, the visitor allows adding new virtual functions to a family of classes, without
 * modifying the classes. Instead, a visitor class is created that implements all of the appropriate
 * specializations of the virtual function.
 * <p>
 * Recognizeable by two different abstract/interface types which has methods defined which each take
 * the other abstract/interface type; the one actually calls the method of the other and the other
 * executes the desired strategy.
 * <p>
 * Usage examples in Java APIs:
 * <p>
 * javax.lang.model.element.AnnotationValue and AnnotationValueVisitor
 * javax.lang.model.element.Element and ElementVisitor
 * javax.lang.model.type.TypeMirror and TypeVisitor
 * java.nio.file.FileVisitor and SimpleFileVisitor
 * javax.faces.component.visit.VisitContext and VisitCallback
 */

public class VisitorPattern {

    public static void main(String[] args) {
        ItemElement[] items = new ItemElement[]{
                new Book(20, "1234"),
                new Book(100, "5678"),
                new Fruit(10, 2, "Banana"),
                new Fruit(5, 5, "Apple")
        };

        int total = calculatePrice(items);

        System.out.println("Total Cost = " + total);
    }

    private static int calculatePrice(ItemElement[] items) {
        ShoppingCartVisitor visitor = new ShoppingCartVisitorImpl();

        int sum = 0;

        for (ItemElement item : items) {
            sum = sum + item.accept(visitor);
        }

        return sum;
    }
}

// Interface of elements to be visited
interface ItemElement {
    int accept(ShoppingCartVisitor visitor);
}

// A concrete element
class Book implements ItemElement {

    private int price;
    private String isbnNumber;

    public Book(int cost, String isbn) {
        this.price = cost;
        this.isbnNumber = isbn;
    }

    public int getPrice() {
        return price;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    @Override
    public int accept(ShoppingCartVisitor visitor) {
        return visitor.visit(this);
    }
}

// A concrete element
class Fruit implements ItemElement {

    private int pricePerKg;
    private int weight;
    private String name;

    public Fruit(int priceKg, int wt, String nm) {
        this.pricePerKg = priceKg;
        this.weight = wt;
        this.name = nm;
    }

    public int getPricePerKg() {
        return pricePerKg;
    }


    public int getWeight() {
        return weight;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int accept(ShoppingCartVisitor visitor) {
        return visitor.visit(this);
    }
}

// Interface of the Visitor that will visit elements
interface ShoppingCartVisitor {

    int visit(Book book);

    int visit(Fruit fruit);
}

// The concrete visitor
class ShoppingCartVisitorImpl implements ShoppingCartVisitor {

    @Override
    public int visit(Book book) {
        int cost = 0;
        //apply 5$ discount if book price is greater than 50
        if (book.getPrice() > 50) {
            cost = book.getPrice() - 5;
        } else cost = book.getPrice();
        System.out.println("Book ISBN::" + book.getIsbnNumber() + " cost =" + cost);
        return cost;
    }

    @Override
    public int visit(Fruit fruit) {
        int cost = fruit.getPricePerKg() * fruit.getWeight();
        System.out.println(fruit.getName() + " cost = " + cost);
        return cost;
    }
}
