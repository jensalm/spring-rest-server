package com.captechconsulting.facade.v1_0.data;

import com.captechconsulting.facade.v1_0.validators.Alphanumeric;
import com.captechconsulting.facade.v1_0.validators.Email;
import com.captechconsulting.facade.v1_0.validators.Password;
import com.captechconsulting.facade.v1_0.validators.UserPasswords;

import javax.validation.constraints.NotNull;

@UserPasswords
public class UserVO {

    private Long id;

    @Alphanumeric
    private String firstName;

    @Alphanumeric
    private String lastName;

    @Email
    @NotNull
    private String email;

    @Password
    private String password;

    private String confirmPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserVO userVO = (UserVO) o;

        return !(email != null ? !email.equals(userVO.email) : userVO.email != null) &&
                !(firstName != null ? !firstName.equals(userVO.firstName) : userVO.firstName != null) &&
                !(id != null ? !id.equals(userVO.id) : userVO.id != null) &&
                !(lastName != null ? !lastName.equals(userVO.lastName) : userVO.lastName != null) &&
                !(password != null ? !password.equals(userVO.password) : userVO.password != null) &&
                !(confirmPassword != null ? !confirmPassword.equals(userVO.confirmPassword) : userVO.confirmPassword != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (confirmPassword != null ? confirmPassword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserVO{ id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' + ", password='[protected]'" + ", confirmPassword='[protected]'" + '}';
    }
}
