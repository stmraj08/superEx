package com.client.superex.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.client.superex.entity.Admin;
import com.client.superex.entity.Course;
import com.client.superex.entity.Purchase;
import com.client.superex.entity.User;
import com.client.superex.entity.WalletTransaction;
import com.client.superex.repository.AdminRepo;
import com.client.superex.repository.CourseRepo;
import com.client.superex.repository.PurchaseRepo;
import com.client.superex.repository.UserRepo;
import com.client.superex.repository.WalletTransactionRepo;
import com.client.superex.util.FileUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppController {

    // ✅ Manual Logger
    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired 
    AdminRepo adminRepo;
    
    @Autowired 
    UserRepo userRepo;
    
    @Autowired 
    CourseRepo courseRepo;
    
    @Autowired 
    PurchaseRepo purchaseRepo;
    
    @Autowired
    WalletTransactionRepo walletTxnRepo;

    // ================= HOME =================
    @GetMapping("/")
    public String home(){
        log.info("Accessing Home Page");
        return "home";
    }

    // ================= ADMIN =================
    @GetMapping("/admin")
    public String adminLogin(){
        log.info("Admin login page accessed");
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String adminLoginPost(String email, String password, HttpSession s){

        log.info("Admin login attempt: {}", email);

        Admin a = adminRepo.findByEmail(email);

        if(a != null && a.getPassword().equals(password)){
            log.info("Admin login SUCCESS: {}", email);
            s.setAttribute("admin", email);
            return "redirect:/admin/dashboard";
        }

        log.warn("Admin login FAILED: {}", email);
        return "admin-login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model m, HttpSession s){

        if(s.getAttribute("admin") == null){
            log.warn("Unauthorized admin dashboard access");
            return "redirect:/admin";
        }

        log.info("Admin dashboard loaded");

        m.addAttribute("courses", courseRepo.findAll());
        return "admin-dashboard";
    }

    @PostMapping("/admin/add")
    public String addCourse(Course c, HttpSession s){

        if(s.getAttribute("admin") == null){
            log.warn("Unauthorized course add attempt");
            return "redirect:/admin";
        }

        courseRepo.save(c);
        log.info("Course added: {}", c.getName());

        return "redirect:/admin/dashboard";
    }

    // ================= USER =================
    @GetMapping("/user")
    public String userLogin(){
        log.info("User login page accessed");
        return "user-login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model){
        log.info("Register page loaded");
        model.addAttribute("user", new User());
        return "register";
    }

	/*
	 * @PostMapping("/user/register") public String register(User u,
	 * RedirectAttributes ra){
	 * 
	 * log.info("User registration attempt: {}", u.getEmail());
	 * 
	 * try { User existing = userRepo.findByEmail(u.getEmail());
	 * 
	 * if(existing != null){ log.warn("Registration FAILED - Email exists: {}",
	 * u.getEmail()); ra.addFlashAttribute("error", "Email already registered!");
	 * return "redirect:/user/register"; }
	 * 
	 * userRepo.save(u); log.info("User registered successfully: {}", u.getEmail());
	 * 
	 * ra.addFlashAttribute("success", "Registration successful! Please login.");
	 * return "redirect:/user";
	 * 
	 * } catch (Exception e) { log.error("Registration ERROR for email: {}",
	 * u.getEmail(), e); ra.addFlashAttribute("error", "Something went wrong.");
	 * return "redirect:/user/register"; } }
	 */

    @GetMapping("/user/register")
    public String showRegisterPage() {
        return "register"; // register.html in templates
    }
    
    @PostMapping("/user/register")
    public String register(User u, RedirectAttributes ra) {

        log.info("User registration attempt: {}", u.getEmail());

        try {

            // ✅ ONLY EXISTING USER CHECK
            User existing = userRepo.findByEmail(u.getEmail());

            if (existing != null) {
                log.warn("Registration FAILED - Email exists: {}", u.getEmail());
                ra.addFlashAttribute("error", "Email already registered!");
                return "redirect:/user/register";
            }

            // ✅ SAVE DIRECTLY (frontend handles password rules)
            userRepo.save(u);

            log.info("User registered successfully: {}", u.getEmail());

            ra.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/user";

        } catch (Exception e) {
            log.error("Registration ERROR for email: {}", u.getEmail(), e);
            ra.addFlashAttribute("error", "Something went wrong.");
            return "redirect:/user/register";
        }
    }
    
    @PostMapping("/user/login")
    public String userLoginPost(String email, String password, HttpSession s){

        log.info("User login attempt: {}", email);

        User u = userRepo.findByEmail(email);

        if(u != null && u.getPassword().equals(password)){
            log.info("User login SUCCESS: {}", email);
            s.setAttribute("user", email);
            return "redirect:/courses";
        }

        log.warn("User login FAILED: {}", email);
        return "user-login";
    }

    @GetMapping("/courses")
    public String courses(Model m, HttpSession s){

        if(s.getAttribute("user") == null){
            log.warn("Unauthorized courses access");
            return "redirect:/user";
        }

        log.info("Courses page loaded for user: {}", s.getAttribute("user"));

        m.addAttribute("courses", courseRepo.findAll());
        return "courses";
    }

    // ================= PAYMENT =================
    @PostMapping("/payment")
    public String goToPayment(@RequestParam Long courseId, Model model){

        log.info("Payment request for courseId: {}", courseId);

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> {
                    log.error("Course not found: {}", courseId);
                    return new RuntimeException("Course not found");
                });

        model.addAttribute("course", course);
        return "payment";
    }

	/*
	 * @PostMapping("/pay") public String processPayment(@RequestParam Long
	 * courseId, Model model){
	 * 
	 * log.info("Processing payment for courseId: {}", courseId);
	 * 
	 * model.addAttribute("message", "Payment Successful!");
	 * model.addAttribute("courseId", courseId);
	 * 
	 * log.info("Payment SUCCESS for courseId: {}", courseId);
	 * 
	 * return "success"; }
	 */

    // ================= LOGOUT =================
    @GetMapping("/logout")
    public String logout(HttpSession s){
        log.info("Logout triggered");
        s.invalidate();
        return "redirect:/";
    }
    
	/*
	 * @GetMapping("/profile") public String profile(Model m, HttpSession s){
	 * 
	 * String email = (String) s.getAttribute("user"); if(email == null) return
	 * "redirect:/user";
	 * 
	 * User u = userRepo.findByEmail(email); m.addAttribute("user", u);
	 * 
	 * return "profile"; }
	 * 
	 * @PostMapping("/profile/update") public String updateProfile(@ModelAttribute
	 * User formUser, HttpSession s, RedirectAttributes ra){
	 * 
	 * String email = (String) s.getAttribute("user"); if(email == null) return
	 * "redirect:/user";
	 * 
	 * User u = userRepo.findByEmail(email);
	 * 
	 * try { // ✅ Editable fields u.setFullName(formUser.getFullName());
	 * u.setMobile(formUser.getMobile()); u.setDob(formUser.getDob());
	 * u.setAadhaar(formUser.getAadhaar()); u.setCountry(formUser.getCountry());
	 * u.setCity(formUser.getCity());
	 * 
	 * u.setBankName(formUser.getBankName());
	 * u.setAccountNumber(formUser.getAccountNumber());
	 * 
	 * userRepo.save(u);
	 * 
	 * ra.addFlashAttribute("success", "Profile updated successfully!");
	 * 
	 * } catch (Exception e) { ra.addFlashAttribute("error",
	 * "Something went wrong!"); }
	 * 
	 * return "redirect:/profile"; }
	 */
    
    
    // ================= PROFILE VIEW =================
    @GetMapping("/profile")
    public String profile(Model m, HttpSession s){

        String email = (String) s.getAttribute("user");
        if(email == null) return "redirect:/user";

        User u = userRepo.findByEmail(email);

        // Aadhaar masking
        String masked = "";
        if(u.getAadhaar() != null && u.getAadhaar().length() >= 4){
            masked = "********" + u.getAadhaar().substring(u.getAadhaar().length() - 4);
        }

        m.addAttribute("user", u);
        m.addAttribute("maskedAadhaar", masked);

        return "profile";
    }

    // ================= UPDATE PROFILE =================
	/*
	 * @PostMapping("/profile/update") public String
	 * updateProfile(@ModelAttribute("formUser") User formUser,
	 * 
	 * @RequestParam("profilePhoto") MultipartFile profilePhoto,
	 * 
	 * @RequestParam("aadhaarFile") MultipartFile aadhaarFile, HttpSession s,
	 * RedirectAttributes ra){
	 */

    @PostMapping("/profile/update")
    public String updateProfile(
            User user,
            @RequestParam("profilePhotoFile") MultipartFile photo,
            @RequestParam("aadhaarFileUpload") MultipartFile aadhaar,
            HttpSession s,
            RedirectAttributes ra) {

        try {
            String email = (String) s.getAttribute("user");
            User u = userRepo.findByEmail(email);

            // Update normal fields
            u.setFullName(user.getFullName());
            u.setMobile(user.getMobile());
            u.setDob(user.getDob());
            u.setCountry(user.getCountry());
            u.setCity(user.getCity());
            u.setBankName(user.getBankName());
            u.setAccountNumber(user.getAccountNumber());

            // ✅ PROFILE PHOTO
            if (!photo.isEmpty()) {

                // Save file
                String path = FileUtil.saveFile(photo, "photo", user.getFullName());
                u.setProfilePhotoPath(path);

                // Save Base64
                u.setProfilePhotoBase64(FileUtil.convertToBase64(photo));
            }

            // ✅ AADHAAR
            if (!aadhaar.isEmpty()) {

                String path = FileUtil.saveFile(aadhaar, "aadhaar", user.getFullName());
                u.setAadhaarPath(path);

                u.setAadhaarBase64(FileUtil.convertToBase64(aadhaar));
            }

            userRepo.save(u);

            ra.addFlashAttribute("success", "Profile updated successfully");

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Upload failed");
            e.printStackTrace();
        }

        return "redirect:/profile";
    }

    // ================= PHOTO VIEW =================

    @GetMapping("/user/photo")
    @ResponseBody
    public ResponseEntity<byte[]> getPhoto(HttpSession s) {

        try {
            String email = (String) s.getAttribute("user");
            if (email == null) return ResponseEntity.status(401).build();

            User u = userRepo.findByEmail(email);

            if (u.getProfilePhotoPath() == null) {
                return ResponseEntity.notFound().build();
            }

            Path path = Paths.get(u.getProfilePhotoPath());

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            byte[] file = Files.readAllBytes(path);

            String contentType = Files.probeContentType(path);
            if (contentType == null) contentType = "image/jpeg";

            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .body(file);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/user/aadhaar")
    @ResponseBody
    public ResponseEntity<byte[]> getAadhaar(HttpSession s) {

        try {
            String email = (String) s.getAttribute("user");
            if (email == null) return ResponseEntity.status(401).build();

            User u = userRepo.findByEmail(email);

            if (u.getAadhaarPath() == null) {
                return ResponseEntity.notFound().build();
            }

            Path path = Paths.get(u.getAadhaarPath());

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            byte[] file = Files.readAllBytes(path);

            String contentType = Files.probeContentType(path);
            if (contentType == null) contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .header("Content-Disposition", "attachment; filename=\"aadhaar\"")
                    .body(file);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/ewallet")
    public String wallet(Model m, HttpSession s){

        String email = (String) s.getAttribute("user");
        User u = userRepo.findByEmail(email);

        m.addAttribute("balance", u.getWalletBalance());
        m.addAttribute("transactions", walletTxnRepo.findByUserEmailOrderByCreatedAtDesc(email));

        return "wallet";
    }

    @PostMapping("/ewallet/withdraw")
    public String withdraw(@RequestParam double amount,
                           HttpSession s,
                           RedirectAttributes ra) {

        String email = (String) s.getAttribute("user");
        User user = userRepo.findByEmail(email);

        if (amount <= 0 || amount > user.getWalletBalance()) {
            ra.addFlashAttribute("error", "Invalid amount!");
            return "redirect:/ewallet";
        }

        // Deduct balance
        user.setWalletBalance(user.getWalletBalance() - amount);
        userRepo.save(user);

        // ✅ Save wallet transaction WITH BANK DETAILS
        WalletTransaction txn = new WalletTransaction();
        txn.setUserEmail(email);
        txn.setAmount(amount);
        txn.setType("DEBIT");
        txn.setStatus("SUCCESS");
        txn.setRemark("Transferred to Bank");

        // ✅ IMPORTANT
        txn.setBankName(user.getBankName());
        txn.setAccountNumber(user.getAccountNumber());

        walletTxnRepo.save(txn);

        // ✅ Better success message
        ra.addFlashAttribute("success",
            "₹" + amount + " transferred to " 
            + user.getBankName() + " (A/C ****" 
            + user.getAccountNumber().substring(user.getAccountNumber().length()-4)
            + ") successfully ✅");

        return "redirect:/transactions";
    }
    
    @GetMapping("/myplan")
    public String myPlan(Model m, HttpSession s){

        String email = (String) s.getAttribute("user");
        if(email == null) return "redirect:/user";

        User user = userRepo.findByEmail(email);

        m.addAttribute("plans", purchaseRepo.findByUser(user));

        return "myplan";
    }
    
    
    @PostMapping("/pay")
    public String processPayment(@RequestParam Long courseId, HttpSession s, Model model){

        String email = (String) s.getAttribute("user");
        User user = userRepo.findByEmail(email);

        Course course = courseRepo.findById(courseId).orElseThrow();

        // Wallet reward
        double reward = course.getPrice() * 0.1;
        user.setWalletBalance(user.getWalletBalance() + reward);
        userRepo.save(user);

        // ✅ Create Purchase object properly
        Purchase p = new Purchase();
        p.setUser(user);
        p.setCourse(course);

        p.setUserEmail(email);
        p.setCourseName(course.getName());

        p.setOriginalPrice(course.getPrice());
        p.setFinalPrice(course.getPrice()); // update if coupon applied
        p.setCoupon("NA");

        purchaseRepo.save(p);

        model.addAttribute("message", "Payment Successful!");
        return "success";
    }
    
    
    @GetMapping("/transactions")
    public String transactions(Model model, HttpSession session) {

        String email = (String) session.getAttribute("user");
        if (email == null) return "redirect:/user";

        // Course transactions
        model.addAttribute("transactions",
            purchaseRepo.findByUserEmail(email));

        // ✅ ADD THIS HERE
        model.addAttribute("walletTxns",
        		walletTxnRepo.findByUserEmailOrderByCreatedAtDesc(email));

        return "transactions";
    }
    
    
}