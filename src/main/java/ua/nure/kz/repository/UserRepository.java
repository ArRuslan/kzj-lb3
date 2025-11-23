package ua.nure.kz.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.nure.kz.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByLogin(String login);

    User findUserById(long id);

    void deleteById(long id);

    @Query("SELECT u FROM User u JOIN u.groups g WHERE g.id = :group_id")
    List<User> findUsersByGroupId(@Param("group_id") long groupId, Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u JOIN u.groups g WHERE g.id = :group_id")
    long countUsersByGroupId(@Param("group_id") long groupId);

}
