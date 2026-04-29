/*
 * package com.client.superex.controller; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.*; import
 * jakarta.servlet.http.HttpSession; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * com.client.superex.repository.*; import com.client.superex.entity.*;
 * 
 * @Controller public class AppController_Old {
 * 
 * @Autowired AdminRepo adminRepo;
 * 
 * @Autowired UserRepo userRepo;
 * 
 * @Autowired CourseRepo courseRepo;
 * 
 * @Autowired PurchaseRepo purchaseRepo;
 * 
 * @GetMapping("/") public String home(){ return "home"; }
 * 
 * @GetMapping("/admin") public String adminLogin(){ return "admin-login"; }
 * 
 * @PostMapping("/admin/login") public String adminLoginPost(String email,String
 * password,HttpSession s){ Admin a=adminRepo.findByEmail(email); if(a!=null &&
 * a.getPassword().equals(password)){s.setAttribute("admin",email);return
 * "redirect:/admin/dashboard";} return "admin-login"; }
 * 
 * @GetMapping("/admin/dashboard") public String adminDashboard(Model
 * m,HttpSession s){ if(s.getAttribute("admin")==null) return "redirect:/admin";
 * m.addAttribute("courses",courseRepo.findAll()); return "admin-dashboard"; }
 * 
 * @PostMapping("/admin/add") public String addCourse(Course c,HttpSession s){
 * if(s.getAttribute("admin")==null) return "redirect:/admin";
 * courseRepo.save(c); return "redirect:/admin/dashboard"; }
 * 
 * @GetMapping("/user") public String userLogin(){return "user-login";}
 * 
 * @PostMapping("/user/register") public String register(User
 * u){userRepo.save(u);return "user-login";}
 * 
 * @PostMapping("/user/login") public String userLoginPost(String email,String
 * password,HttpSession s){ User u=userRepo.findByEmail(email); if(u!=null &&
 * u.getPassword().equals(password)){s.setAttribute("user",email);return
 * "redirect:/courses";} return "user-login"; }
 * 
 * @GetMapping("/courses") public String courses(Model m,HttpSession s){
 * if(s.getAttribute("user")==null) return "redirect:/user";
 * m.addAttribute("courses",courseRepo.findAll()); return "courses"; }
 * 
 * 
 * 
 * @PostMapping("/buy") public String buy(Long courseId,HttpSession s){
 * if(s.getAttribute("user")==null) return "redirect:/user"; Course
 * c=courseRepo.findById(courseId).orElse(null); if(c==null) return
 * "redirect:/courses"; Purchase p=new Purchase();
 * p.setUserEmail(s.getAttribute("user").toString());
 * p.setCourseName(c.getName()); p.setOriginalPrice(c.getPrice());
 * p.setFinalPrice(c.getDiscountedPrice()); p.setCoupon(c.getCoupon());
 * purchaseRepo.save(p); return "redirect:/courses"; }
 * 
 * @GetMapping("/logout") public String logout(HttpSession
 * s){s.invalidate();return "redirect:/";} }
 */