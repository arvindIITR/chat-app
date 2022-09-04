package com.arvind.chatapp.repository;

import com.arvind.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByName(String name);

    @Query(" FROM"
            + "    User u"
            + "  WHERE"
            + "    u.email IS NOT :excludedUser")
    public List<User> findFriendsListFor(@Param("excludedUser") String excludedUser);
}
