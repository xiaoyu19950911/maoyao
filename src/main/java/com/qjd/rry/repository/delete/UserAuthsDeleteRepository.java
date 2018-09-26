package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.Role;
import com.qjd.rry.entity.delete.UserAuthsDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAuthsDeleteRepository extends JpaRepository<UserAuthsDelete,Integer>{
    UserAuthsDelete findByUsername(String username);


    UserAuthsDelete findFirstByUsername(String username);

    UserAuthsDelete findUserAuthsById(Integer id);

    List<UserAuthsDelete> findAllByIdentityTypeAndRolesIn(Integer type, List<Role> roleList);

    Page<UserAuthsDelete> findAllByRoles(Role role, Pageable pageable);

    List<UserAuthsDelete> findAllByRolesIn(List<Role> roleList);

/*    @Query(value = "SELECT * FROM maoyao.test",nativeQuery = true)
    List<Map<String,Integer>> findUserAuthsRole();

    @Query(value = "insert into maoyao.test(user_auths_id,roles_id) value(?1,?2) ;",nativeQuery = true)
    void insert(Integer userAuthsId, Integer rolesId);*/

    UserAuthsDelete findFirstByUsernameAndIdentityType(String username, Integer type);

    UserAuthsDelete findUserAuthsByUsernameAndIdentityType(String userName, Integer type);

    List<UserAuthsDelete> findAllByRoles(Role role);

    @Query(value = "SELECT coalesce(count(*),0) FROM maoyao.user_auths_roles_delete where roles_id=?1",nativeQuery = true)
    Integer findCountByRolesId(Integer roleId);

    List<UserAuthsDelete> findAllByIdInAndRoles(List<Integer> idList, Role role);
}
