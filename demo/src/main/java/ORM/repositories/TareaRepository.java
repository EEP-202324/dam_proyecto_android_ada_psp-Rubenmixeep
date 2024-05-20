package ORM.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ORM.models.tasks;
import jakarta.transaction.Transactional;

@Repository
public interface TareaRepository extends CrudRepository<tasks, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tasks (description, id_usuario) VALUES (:description, :id_usuario)", nativeQuery = true)
    int newTask(@Param("description") String description, @Param("id_usuario") int id_usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tasks SET description = :description WHERE id_usuario = :id_usuario", nativeQuery = true)
    int updateTareaDescripcion(@Param("id_usuario") int id_usuario, @Param("description") String description);

    @Query(value = "SELECT * FROM tasks WHERE id_usuario = :id_usuario", nativeQuery = true)
    List<tasks> findTareasByUserId(@Param("id_usuario") int id_usuario);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tasks WHERE id = :id", nativeQuery = true)
    int deleteTareaById(@Param("id") int id);
}
