package ORM.controllers;

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.security.crypto.bcrypt.BCrypt;
	import ORM.services.userService;

	@RestController
	@RequestMapping("main")
	public class RegisterUserController {

	    @Autowired
	    userService userService;

	    @PostMapping("/userregister")
	    public ResponseEntity NewUserRegister
	    		(@RequestParam("name") String name,
	            @RequestParam("last_name") String last_name,
                @RequestParam("email") String email,
                @RequestParam("rol") String rol,
                @RequestParam("phone") String phone,
                @RequestParam("password") String password
) {


	    	String PasswordHashed;
	    	PasswordHashed = BCrypt.hashpw(password, BCrypt.gensalt());



	        int resultado = userService.ServiceNewUserRegister(name, last_name, email, rol, phone, PasswordHashed); //Esto es la linea de codigo que crea el nuevo usuario

	        if(resultado != 1) {
	            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
	        }else {
	            return new ResponseEntity<>("Registered", HttpStatus.OK);
	        }


	    }

	}


