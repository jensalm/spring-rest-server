package com.captechconsulting.facade.v1_0.data;

import com.captechconsulting.facade.v1_0.validators.*;

import javax.validation.Valid;

@MatchingPackageNumbers
public class TicketVO {

    private Long id;

    @Alphabetic
    private String firstName;

    @Alphabetic
    private String lastName;

    @Valid
    private AddressVO address;

    @PhoneNumber
    private String phoneNumber;

    @PackageNumber
    private String packageNumber;

    private String confirmPackageNumber;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressVO getAddress() {
        return address;
    }

    public void setAddress(AddressVO address) {
        this.address = address;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getConfirmPackageNumber() {
        return confirmPackageNumber;
    }

    public void setConfirmPackageNumber(String confirmPackageNumber) {
        this.confirmPackageNumber = confirmPackageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketVO ticketVO = (TicketVO) o;

        return !(phoneNumber != null ? !phoneNumber.equals(ticketVO.phoneNumber) : ticketVO.phoneNumber != null) &&
                !(firstName != null ? !firstName.equals(ticketVO.firstName) : ticketVO.firstName != null) &&
                !(id != null ? !id.equals(ticketVO.id) : ticketVO.id != null) &&
                !(lastName != null ? !lastName.equals(ticketVO.lastName) : ticketVO.lastName != null) &&
                !(packageNumber != null ? !packageNumber.equals(ticketVO.packageNumber) : ticketVO.packageNumber != null) &&
                !(confirmPackageNumber != null ? !confirmPackageNumber.equals(ticketVO.confirmPackageNumber) : ticketVO.confirmPackageNumber != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (packageNumber != null ? packageNumber.hashCode() : 0);
        result = 31 * result + (confirmPackageNumber != null ? confirmPackageNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TicketVO{ id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' + ", packageNumber='[protected]'" + ", confirmPackageNumber='[protected]'" + '}';
    }
}
