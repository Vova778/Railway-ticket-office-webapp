package com.railway.ticket.office.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    public static User.Builder newBuilder() {
        return new User().new Builder();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be < 0");
        }
        this.id = id;
    }

    public String getLogin() {
        return login;
    }


    public String getPassword() {
        return password;
    }


    public Role getRole() {
        return role;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getPhone() {
        return phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!Objects.equals(login, user.login)) return false;
        return Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone=" + phone +
                '}';
    }


    public enum Role {
        USER(1,"User"),
        ADMIN(2, "Admin");
        private final int id;
        private final String roleName;

        Role(int id, String roleName) {
            this.id = id;
            this.roleName = roleName;
        }

        public int getId() {
            return id;
        }

        public String getRoleName() {
            return roleName;
        }
    }


    public class Builder {

        private Builder() {
        }

        public Builder setPhone(String phone) {
            if (phone.length() > 10) {
                throw new IllegalArgumentException("invalid phone number!");
            }
            if (phone.matches("[0-9]")) {
                throw new IllegalArgumentException("invalid phone number 22!");
            }
            User.this.phone = phone;
            return this;
        }

        public Builder setLastName(String lastName) {
            if (lastName == null) {
                throw new IllegalArgumentException("lastName can't be null!");
            }
            User.this.lastName = lastName;
            return this;
        }

        public Builder setFirstName(String firstName) {
            if (firstName == null) {
                throw new IllegalArgumentException("firstName can't be null!");
            }

            User.this.firstName = firstName;
            return this;
        }

        public Builder setRole(Role role) {
            if (role == null) {
                throw new IllegalArgumentException("role can't be null!");
            }
            User.this.role = role;
            return this;
        }

        public Builder setPassword(String password) {
            if (password == null) {
                throw new IllegalArgumentException("password can't be null!");
            }
            if (password.length() < 4) {
                throw new IllegalArgumentException("password must not be less than 4!");
            }
            User.this.password = password;
            return this;
        }


        public Builder setLogin(String login) {
            if (login == null) {
                throw new IllegalArgumentException("login can't be null!");
            }
            if (login.length() < 4) {
                throw new IllegalArgumentException("login must not be less than 4!");
            }
            User.this.login = login;
            return this;
        }


        public Builder setId(int id) {
            if (id < 0) {
                throw new IllegalArgumentException("ID cannot be < 0");
            }
            User.this.id = id;
            return this;
        }


        public User build() {
            return User.this;
        }

    }


}

