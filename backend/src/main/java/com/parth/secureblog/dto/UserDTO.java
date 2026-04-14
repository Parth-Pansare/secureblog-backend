package com.parth.secureblog.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public static class UserDTOBuilder {
        private Long id;
        private String name;
        private String email;
        private String role;

        public UserDTOBuilder id(Long id) { this.id = id; return this; }
        public UserDTOBuilder name(String name) { this.name = name; return this; }
        public UserDTOBuilder email(String email) { this.email = email; return this; }
        public UserDTOBuilder role(String role) { this.role = role; return this; }

        public UserDTO build() {
            return new UserDTO(id, name, email, role);
        }
    }
}
