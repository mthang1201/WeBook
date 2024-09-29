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

    private String userName;

    private String phoneNumber;

    private String email;

    private String membershipStatus;
    
    private String privileges;
}