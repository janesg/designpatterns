package com.devxpress.creational;

/**
 * Builder - Creational
 *
 * Deals with the problem where the same construction process has to be able to create different
 * representations of a complex object.
 *
 * It is a solution to the problem that occurs when the increase of object constructor parameter
 * combinations leads to an exponential list of constructors. Instead of using numerous constructors,
 * the builder pattern uses another object, a builder, that receives each initialization parameter
 * step-by-step and then returns the resulting constructed object at once.
 *
 * Recognizeable by creational methods returning the instance itself
 *
 * Usage examples in Java APIs:
 *
 * java.lang.StringBuilder#append() (unsynchronized)
 * java.lang.StringBuffer#append() (synchronized)
 * java.nio.ByteBuffer#put() (also on CharBuffer, ShortBuffer, IntBuffer, LongBuffer, FloatBuffer and DoubleBuffer)
 * javax.swing.GroupLayout.Group#addComponent()
 * All implementations of java.lang.Appendable
 */

public class BuilderPattern {

    public static void main(String[] args) {

        User.UserBuilder builder = new User.UserBuilder("Bob", "Ajob");
        User user1 = builder.build();
        System.out.println("User 1 : " + user1.toString());
        User user2 = builder.age(26).phone("0635889229").build();
        System.out.println("User 2 : " + user2.toString());
    }

}

// The c
class User {
    // All fields can be marked as final since object created in one hit
    private final String firstName;     //required
    private final String lastName;      //required

    private final int age;              //optional
    private final String phone;         //optional
    private final String address;       //optional

    // Private constructor that takes a Builder object
    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    // Nested builder class
    static class UserBuilder {
        // Has same member variables as class it is responsible for building
        private final String firstName;     //required
        private final String lastName;      //required

        private int    age;              //optional
        private String phone;         //optional
        private String address;       //optional

        // Builder constructor takes the mandatory fields
        UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        User build() {
            //
            // >>> Stick any field validation in here prior to creating User <<<
            //

            // If all validation passes then we go ahead and create the User
            return new User(this);
        }
    }
}