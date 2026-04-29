package com.client.superex.entity;
import jakarta.persistence.*;

@Entity
public class Admin {
 @Id @GeneratedValue private Long id;
 private String email;
 private String password;
 public Long getId(){return id;}
 public String getEmail(){return email;}
 public void setEmail(String email){this.email=email;}
 public String getPassword(){return password;}
 public void setPassword(String password){this.password=password;}
}
