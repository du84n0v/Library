package dasturlash.uz.service;

import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.controller.AdminController;
import dasturlash.uz.controller.StaffController;
import dasturlash.uz.controller.StudentController;
import dasturlash.uz.dto.Profile;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.MD5Util;
import dasturlash.uz.util.ProfileValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private StudentController studentController;
    @Autowired
    private AdminController adminController;
    @Autowired
    private StaffController staffController;

    public void login(String login, String password) {
//        Profile profile = profileRepository.getByLogin(login);
//        if (profile == null) {
//            System.out.println("Login or Password wrong.");
//            return;
//        }
//        String md5Hash = MD5Util.encode(password);
//        if (!md5Hash.equals(profile.getPassword())) {
//            System.out.println("Login or Password wrong.");
//            return;
//        }
//        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
//            System.out.println("Wrong status mazgi.");
//            return;
//        }
//        System.out.println("** Welcome to library project **");
//        // redirect
//        ComponentContainer.currentProfile = profile;
//        if (profile.getRole().equals(ProfileRole.STUDENT)) {
//            studentController.start();
//        } else if (profile.getRole().equals(ProfileRole.ADMIN)) {
//            adminController.start();
//        } else if (profile.getRole().equals(ProfileRole.STAFF)) {
//            staffController.start();
//        }
    }

    public void registration(Profile profile) {
        // check
//        if (!ProfileValidationUtil.isValid(profile)) {
//            return;
//        }
//        // check login
//        Profile existProfile = profileRepository.getByLogin(profile.getLogin());
//        if (existProfile != null) {
//            System.out.println("Login exists. Please choose other login. Mazgi");
//            return;
//        }
//        // save
//        profile.setCreatedDate(LocalDateTime.now());
//        profile.setRole(ProfileRole.STUDENT);
//        profile.setStatus(ProfileStatus.ACTIVE);
//        profile.setPassword(MD5Util.encode(profile.getPassword()));
//        int effectedRow = profileRepository.create(profile);
//        if (effectedRow == 1) {
//            System.out.println("Registration completed.");
//        }
    }

    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    public void setStaffController(StaffController staffController) {
        this.staffController = staffController;
    }
}
