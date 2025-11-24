package ua.nure.kz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.kz.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    Group findGroupByName(String name);

    Group findGroupById(long id);

    @Transactional
    void deleteById(long id);

}
