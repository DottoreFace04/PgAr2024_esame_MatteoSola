package it.arnaldo.arnaldowest;

public enum Roles {
    SHERIFF     ("Scheriffo"),
    VICE        ("Vice"),
    RENEGADE    ("Rinnegato"),
    DESPERADO   ("Fuorilegge");

    private final String role;

    Roles(String role) {
        this.role = role;
    }
    
    public String get_role() {
        return role;
    }
}
