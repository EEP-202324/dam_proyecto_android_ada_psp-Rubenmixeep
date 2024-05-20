package ORM.controllers;

	import java.util.Map;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.data.repository.query.Param;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.security.crypto.bcrypt.BCrypt;

import ORM.models.users;
import ORM.services.userService;

	@RestController
	@RequestMapping("main")
	public class LoginController {

	    @Autowired
	    userService userService;

	    @PostMapping("/userlogin")
	    public ResponseEntity <?> CheckUser (@RequestBody Map <String,String> request){
	    	String email= request.get("email");
	    	String password = request.get("password");
	    	String HassedPassword= userService.NeServiceNewUserLogin(email);
	    	if (!BCrypt.checkpw(password, HassedPassword)) {
	    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña o email incorrectos");
	    		
	    	}
	    	users user = userService.UserInfoByEmail(email);
	    	return ResponseEntity.ok(user);
	    }
	    		
              
 


	    
	}


