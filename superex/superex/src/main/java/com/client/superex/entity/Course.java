package com.client.superex.entity;
import jakarta.persistence.*;

@Entity
public class Course {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 private String name;
 private double price;
 private String coupon;
 private String description;
 private String courseName;

 public double getDiscountedPrice(){
  if(coupon==null) return price;
  if(coupon.equalsIgnoreCase("SAVE10")) return price*0.9;
  if(coupon.equalsIgnoreCase("SAVE20")) return price*0.8;
  return price;
 }
 
 public String getCourseName() {
	return courseName;
}

 public void setCourseName(String courseName) {
	this.courseName = courseName;
 }

 public void setId(Long id) {
	this.id = id;
 }

 @Override
public String toString() {
	return "Course [id=" + id + ", name=" + name + ", price=" + price + ", coupon=" + coupon + ", description="
			+ description + ", courseName=" + courseName + "]";
}

 public Long getId(){return id;}
 public String getName(){return name;}
 public void setName(String name){this.name=name;}
 public double getPrice(){return price;}
 public void setPrice(double price){this.price=price;}
 public String getCoupon(){return coupon;}
 public void setCoupon(String coupon){this.coupon=coupon;}
 public String getDescription(){return description;}
 public void setDescription(String description){this.description=description;}
}
