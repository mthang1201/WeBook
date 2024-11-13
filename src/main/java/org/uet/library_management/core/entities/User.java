package org.uet.library_management.core.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The User class represents a user in the system with various attributes such as ID, name, phone number,
 * email, address, membership status, privileges, and password hash.
 * It provides constructors for creating new User instances and includes Lombok annotations for automated
 * generation of boilerplate code like getters, setters, and constructors.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;

    private String name;

    private String phoneNumber;

    private String email;

    private String address;

    private String membershipStatus;
    
    private String privileges;

    private String passwordHash;

    /**
     * Constructs a new User instance with the provided attributes.
     *
     * @param name the user's name
     * @param phoneNumber the user's phone number
     * @param email the user's email address
     * @param address the user's physical address
     * @param membershipStatus the user's membership status
     * @param privileges the user's privileges
     * @param passwordHash the hashed version of the user's password
     */
    public User(String name, String phoneNumber, String email, String address, String membershipStatus, String privileges, String passwordHash) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.membershipStatus = membershipStatus;
        this.privileges = privileges;
        this.passwordHash = passwordHash;
    }
}