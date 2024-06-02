package ORM.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ORM.models.tasks;
import ORM.repositories.TareaRepository;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Transactional
    public int newTask(String description, int id_usuario) {
        return tareaRepository.newTask(description, id_usuario);
    }

    @Transactional
    public int cambiarDescripcionTarea(int id_usuario, String description) {
        return tareaRepository.updateTareaDescripcion(id_usuario, description);
    }

    @Transactional
    public List<tasks> getTareasByUser(int id_usuario) {
        return tareaRepository.findTareasByUserId(id_usuario);
    }

    @Transactional
    public int deleteTareaById(int id) {
        return tareaRepository.deleteTareaById(id);
    }
}
