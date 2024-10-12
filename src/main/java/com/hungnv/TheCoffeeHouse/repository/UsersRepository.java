package com.hungnv.TheCoffeeHouse.repository;
import com.hungnv.TheCoffeeHouse.model.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    
    Users findByEmail(String email);

    @Query("SELECT u FROM Users u WHERE (u.roleId = 'R1' OR u.roleId = 'R2') AND u.isApproved = :isApproved")
    List<Users> findAllApprovedAdmins(@Param("isApproved") int isApproved);

    Long countByRoleIdInAndIsApproved(List<String> roleIds, Integer isApproved);
}
