package com.devxpress.creational;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Factory - Creational
 *
 * Used to provide an interface for creating families of related objects, without specifying concrete classes.
 *
 * Recognizeable by creational methods returning the factory itself which in turn
 * can be used to create another abstract/interface type
 *
 * Usage examples in Java APIs:
 *
 * javax.xml.parsers.DocumentBuilderFactory#newInstance()
 * javax.xml.transform.TransformerFactory#newInstance()
 * javax.xml.xpath.XPathFactory#newInstance()
 */

public class AbstractFactoryPattern {

    public static void main(String[] args) {
        ArchitectureFactory factory = ArchitectureFactory.getFactory(Architecture.EMBER);
        CPU cpu = factory.createCPU();
        System.out.println(cpu.getClass().getName());

        factory = ArchitectureFactory.getFactory(Architecture.ENGINOLA);
        MMU mmu = factory.createMMU();
        System.out.println(mmu.getClass().getName());
    }
}

enum Architecture {
    ENGINOLA, EMBER
}

// This is the 1st abstract Product
abstract class CPU {}

// These are concrete implementations of the 1st Product
class EmberCPU extends CPU {}
class EnginolaCPU extends CPU {}

// This is the 2nd abstract Product
abstract class MMU {}

// These are concrete implementations of the 2nd Product
class EmberMMU extends MMU {}
class EnginolaMMU extends MMU {}

// This is the Abstract Factory
abstract class ArchitectureFactory {

    private static Map<Architecture, ArchitectureFactory> FACTORY_CACHE = new HashMap<>();

    // Returns a concrete factory object that is an instance of the
    // concrete factory class appropriate for the given architecture.
    static ArchitectureFactory getFactory(Architecture architecture) {

        ArchitectureFactory factory = FACTORY_CACHE.get(architecture);

        if (factory == null) {
            switch (architecture) {
                case ENGINOLA:
                    factory = new EnginolaToolkit();
                    break;
                case EMBER:
                    factory = new EmberToolkit();
                    break;
            }

            FACTORY_CACHE.put(architecture, factory);
        }

        return factory;
    }

    public abstract CPU createCPU();
    public abstract MMU createMMU();
}

// This is a concrete implementation of the Factory
class EmberToolkit extends ArchitectureFactory {
    @Override
    public CPU createCPU() {
        return new EmberCPU();
    }

    @Override
    public MMU createMMU() {
        return new EmberMMU();
    }
}

// This is a concrete implementation of the Factory
class EnginolaToolkit extends ArchitectureFactory {
    @Override
    public CPU createCPU() {
        return new EnginolaCPU();
    }

    @Override
    public MMU createMMU() {
        return new EnginolaMMU();
    }
}
