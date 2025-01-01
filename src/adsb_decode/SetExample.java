/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsb_decode;

import java.util.*;

public class SetExample {
    public static void main(String[] args) {
        // Creating a HashSet to store integers
        Set<Integer> numbers = new HashSet<>();

        // Adding elements to the set
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(10); // Duplicate value, will not be added

        // Display the set (note: the order is not guaranteed)
        System.out.println("Numbers in the set: " + numbers);

        // Check if a value exists in the set
        if (numbers.contains(20)) {
            System.out.println("The set contains the number 20.");
        } else {
            System.out.println("The set does not contain the number 20.");
        }

        // Remove an element from the set
        numbers.remove(20);
        System.out.println("Numbers after removal of 20: " + numbers);

        // Iterate over the set using a for-each loop
        System.out.println("Iterating over the set:");
        for (Integer number : numbers) {
            System.out.println(number);
        }

        // Getting the size of the set
        System.out.println("Size of the set: " + numbers.size());

        // Check if the set is empty
        System.out.println("Is the set empty? " + numbers.isEmpty());
    }
}
