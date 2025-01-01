/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tranduc
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class MD5Example {
    public static String getMD5Checksum(byte[] inputBytes) {
        try {
            // Create an MD5 MessageDigest instance
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Compute the MD5 hash
            byte[] hashBytes = md.digest(inputBytes);

            // Convert the byte array to a hexadecimal string
            BigInteger number = new BigInteger(1, hashBytes);
            String md5Hash = number.toString(16);

            // Ensure the result is 32 characters long
            while (md5Hash.length() < 32) {
                md5Hash = "0" + md5Hash;
            }

            return md5Hash;

        } catch (NoSuchAlgorithmException e) {
            // Handle the case where MD5 algorithm is not available
            System.err.println("MD5 algorithm not found.");
            return null;
        }
    }

    public static void main(String[] args) {
        // Example byte array (can be replaced with your own byte array)
        byte[] data = "Hello, World!".getBytes();
        
        // Get the MD5 checksum
        String md5Checksum = getMD5Checksum(data);
        
        // Print the MD5 checksum
        System.out.println("MD5 Checksum: " + md5Checksum);
    }}
