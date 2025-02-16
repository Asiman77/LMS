/*package com.example.library.repository;

import com.example.library.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Method to find a user by their name
    User findByName(String name);
    User findUserByNameAndRole(String username, String role);

}
*/

package com.example.library.repository;

import com.example.library.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndRole(String username, String role);
}


