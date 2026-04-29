package com.client.superex.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Purchase {
 
 public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne
 private User user;

 @ManyToOne
 private Course course;
 
 private String userEmail;
 private String courseName;
 private double originalPrice;
 private double finalPrice;
 private String coupon;
 
 @Override
public String toString() {
	return "Purchase [id=" + id + ", user=" + user + ", course=" + course + ", userEmail=" + userEmail + ", courseName="
			+ courseName + ", originalPrice=" + originalPrice + ", finalPrice=" + finalPrice + ", coupon=" + coupon
			+ "]";
}
 public Long getId() {
	return id;
 }
 public void setId(Long id) {
	this.id = id;
 }
 public String getUserEmail() {
	return userEmail;
 }
 public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
 }
 public String getCourseName() {
	return courseName;
 }
 public void setCourseName(String courseName) {
	this.courseName = courseName;
 }
 public double getOriginalPrice() {
	return originalPrice;
 }
 public void setOriginalPrice(double originalPrice) {
	this.originalPrice = originalPrice;
 }
 public double getFinalPrice() {
	return finalPrice;
 }
 public void setFinalPrice(double finalPrice) {
	this.finalPrice = finalPrice;
 }
 public String getCoupon() {
	return coupon;
 }
 public void setCoupon(String coupon) {
	this.coupon = coupon;
 }


}
