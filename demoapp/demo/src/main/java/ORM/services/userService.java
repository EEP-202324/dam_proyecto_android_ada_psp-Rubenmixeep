package ORM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ORM.models.users;
import ORM.repositories.userRepository;

@Service
public class userService {


    @Autowired
    userRepository userRepository;

    public int ServiceNewUserRegister(String name, String last_name, String email, String rol, String phone, String password) {
        return userRepository.NewUserRegister(name, last_name, email, rol, phone, password);
 }   

    public String NeServiceNewUserLogin(String email) {
    	return userRepository.NewUserLogin(email);
    }
    public users UserInfoByEmail(String email) {
    	return userRepository.UserInfo(email);
    }
}

