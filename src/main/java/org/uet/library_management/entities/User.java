package org.uet.library_management.entities;

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
}