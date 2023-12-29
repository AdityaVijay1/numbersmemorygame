package com.example.numbersmemorygamea2;

// Class definition for User
public class User {
    // Member variables for user information
    private String name, email, pass;
    private final long score = 0; // Default score is set to 0

    // Default constructor
    public User() {
    }

    // Parameterized constructor for initializing user information
    public User(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    // Getter method for retrieving the user's name
    public String getName() {
        return name;
    }

    // Setter method for updating the user's name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for retrieving the user's email
    public String getEmail() {
        return email;
    }

    // Setter method for updating the user's email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter method for retrieving the user's password
    public String getPass() {
        return pass;
    }

    // Setter method for updating the user's password
    public void setPass(String pass) {
        this.pass = pass;
    }
}
