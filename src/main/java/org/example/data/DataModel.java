package org.example.data;

public class DataModel {
    public record UserCredentials(String username, String password, Boolean loginStatus) {}
}
