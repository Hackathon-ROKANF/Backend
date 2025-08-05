package com.springboot.knockbackend.dto;

public class SimpleSearchRequest {
    private String phoneNo;
    private String password;
    private String address;
    //----- getters & setters -----
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
