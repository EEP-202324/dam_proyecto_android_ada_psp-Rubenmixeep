package ORM.controllers;	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.RestController;
	import ORM.models.users;
import ORM.services.TareaService;

	@RestController
	@RequestMapping("tasks")
	public class TareaController {

	    @Autowired
	    TareaService TareaService;

	    @PostMapping("/NewTarea")
	    public ResponseEntity<String> NewTarea
	    		(@RequestParam("description") String description,
	            @RequestParam("id_usuario") int id_usuario
              
               
) {





	        int resultado = TareaService.newTask(description, id_usuario); 

	        if(resultado != 1) {
	            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
	        }else {
	            return new ResponseEntity<>("Registered", HttpStatus.OK);
	        }


	    }

	}


