package ORM.repositories;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ORM.models.*;
import jakarta.transaction.Transactional;

@Repository
public interface userRepository extends CrudRepository<users, Integer>{


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO USERS(name, last_name, email, rol, phone, password) VALUES(:name, :last_name,:email, :rol, :phone, :password)", nativeQuery = true)
    int NewUserRegister(@Param("name") String name,
    		            @Param("last_name") String last_name,
                        @Param("email") String email,
                        @Param("rol") String rol,
                        @Param("phone") String phone,
                        @Param("password") String password

                       );
    @Query(value= "SELECT password FROM users WHERE email = :email", nativeQuery=true)
    String NewUserLogin(@Param("email") String email);

    @Query(value= "SELECT * FROM users WHERE email = :email", nativeQuery=true)
    users UserInfo(@Param("email") String email);
}


