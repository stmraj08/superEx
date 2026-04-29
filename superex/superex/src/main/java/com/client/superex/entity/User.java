package com.client.superex.entity;

import java.util.Arrays;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ❌ not editable

    private String fullName;
    private String email;
    private String password;
    private String mobile;
    private String dob;
    private String aadhaar;
    private String country;
    private String city;
    private String status = "Active";

    private String bankName;
    private String accountNumber;

    private double walletBalance; // ❌ not editable

    // ================= FILE STORAGE =================

    @Column(length = 2000)
    private String profilePhotoPath;

    @Column(columnDefinition = "LONGTEXT")
    private String profilePhotoBase64;

    @Column(length = 2000)
    private String aadhaarPath;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public String getProfilePhotoPath() {
		return profilePhotoPath;
	}

	public void setProfilePhotoPath(String profilePhotoPath) {
		this.profilePhotoPath = profilePhotoPath;
	}

	public String getProfilePhotoBase64() {
		return profilePhotoBase64;
	}

	public void setProfilePhotoBase64(String profilePhotoBase64) {
		this.profilePhotoBase64 = profilePhotoBase64;
	}

	public String getAadhaarPath() {
		return aadhaarPath;
	}

	public void setAadhaarPath(String aadhaarPath) {
		this.aadhaarPath = aadhaarPath;
	}

	public String getAadhaarBase64() {
		return aadhaarBase64;
	}

	public void setAadhaarBase64(String aadhaarBase64) {
		this.aadhaarBase64 = aadhaarBase64;
	}

	@Column(columnDefinition = "LONGTEXT")
    private String aadhaarBase64;

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", email=" + email + ", password=" + password + ", mobile="
				+ mobile + ", dob=" + dob + ", aadhaar=" + aadhaar + ", country=" + country + ", city=" + city
				+ ", status=" + status + ", bankName=" + bankName + ", accountNumber=" + accountNumber
				+ ", walletBalance=" + walletBalance + ", profilePhotoPath=" + profilePhotoPath
				+ ", profilePhotoBase64=" + profilePhotoBase64 + ", aadhaarPath=" + aadhaarPath + ", aadhaarBase64="
				+ aadhaarBase64 + "]";
	}
        
}