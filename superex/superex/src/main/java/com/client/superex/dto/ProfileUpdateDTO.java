package com.client.superex.dto;

class ProfileUpdateDTO {
    private String fullName;
    private String mobile;

    
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
    
	@Override
	public String toString() {
		return "ProfileUpdateDTO [fullName=" + fullName + ", mobile=" + mobile + "]";
	}
}