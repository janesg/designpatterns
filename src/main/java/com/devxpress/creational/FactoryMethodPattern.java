package com.devxpress.creational;

/**
 * Factory Method - Creational
 *
 * Deals with the problem of creating objects without having to specify the exact class of the
 * object that will be created. This is done by creating objects by calling a factory method
 *   — either specified in an interface and implemented by child classes
 *   - or implemented in a base class and optionally overridden by derived classes
 * rather than by calling a constructor.
 *
 * Recognizeable by creational methods returning an implementation of an abstract/interface type
 *
 * Usage examples in Java APIs:
 *
 * java.util.Calendar#getInstance()
 * java.util.ResourceBundle#getBundle()
 * java.text.NumberFormat#getInstance()
 * java.nio.charset.Charset#forName()
 * java.net.URLStreamHandlerFactory#createURLStreamHandler(String) (Returns singleton object per protocol)
 * java.util.EnumSet#of()
 * javax.xml.bind.JAXBContext#createMarshaller() and other similar methods
 */

public class FactoryMethodPattern {

    public static void main(String[] args)
    {
        // create a small dog
        Dog dog = DogFactory.getDog(DogType.SMALL);
        System.out.println(dog.speak());

        // create a big dog
        dog = DogFactory.getDog(DogType.BIG);
        System.out.println(dog.speak());

        // create a working dog
        dog = DogFactory.getDog(DogType.WORKING);
        System.out.println(dog.speak());
    }
}

enum DogType {
    SMALL,
    BIG,
    WORKING
}

// The abstract/interface for the 'Product'
interface Dog {
    String speak();
}

class Poodle implements Dog {
    public String speak() {
        return "The poodle says \"arf\"";
    }
}

class Rottweiler implements Dog {
    public String speak() {
        return "The Rottweiler says (in a very deep voice) \"WOOF!\"";
    }
}

class SiberianHusky implements Dog {
    public String speak() {
        return "The husky says \"Dude, what's up?\"";
    }
}

// The Product Factory
class DogFactory {
    static Dog getDog(DogType type) {
        Dog dog = null;

        switch (type) {
            case SMALL:
                dog = new Poodle();
                break;
            case BIG:
                dog = new Rottweiler();
                break;
            case WORKING:
                dog = new SiberianHusky();
                break;
        }

        return dog;
    }
}