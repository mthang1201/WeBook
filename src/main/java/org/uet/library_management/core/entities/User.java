package org.uet.library_management.core.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * User.
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