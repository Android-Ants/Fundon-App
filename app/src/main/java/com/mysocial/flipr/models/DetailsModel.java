package com.mysocial.flipr.models;

public class DetailsModel {
    public String email,userName,name,mobile,address,occupation,aadhaarNo,panNo,bankAccountNo,ifscCode,imgUrl,aadhaarUrl,panUrl,bankUrl;
    boolean isVerified;

    public DetailsModel(){}
    public DetailsModel(String email, String userName, String name, String mobile, String address, String occupation, String aadhaarNo, String panNo, String bankAccountNo, String ifscCode, String imgUrl, String aadhaarUrl, String panUrl, String bankUrl, boolean isVerified) {
        this.email = email;
        this.userName = userName;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.occupation = occupation;
        this.aadhaarNo = aadhaarNo;
        this.panNo = panNo;
        this.bankAccountNo = bankAccountNo;
        this.ifscCode = ifscCode;
        this.imgUrl = imgUrl;
        this.aadhaarUrl = aadhaarUrl;
        this.panUrl = panUrl;
        this.bankUrl = bankUrl;
        this.isVerified = isVerified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAadhaarUrl() {
        return aadhaarUrl;
    }

    public void setAadhaarUrl(String aadhaarUrl) {
        this.aadhaarUrl = aadhaarUrl;
    }

    public String getPanUrl() {
        return panUrl;
    }

    public void setPanUrl(String panUrl) {
        this.panUrl = panUrl;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
