package com.qjd.rry.repository;

import com.qjd.rry.entity.Role;
import com.qjd.rry.entity.UserAuths;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface UserAuthsRepository extends JpaRepository<UserAuths,Integer>{
    UserAuths findByUsername(String username);


    UserAuths findFirstByUsername(String username);

    UserAuths findUserAuthsById(Integer id);

    List<UserAuths> findAllByIdentityTypeAndRolesIn(Integer type,List<Role> roleList);

    Page<UserAuths> findAllByRoles(Role role, Pageable pageable);

    List<UserAuths> findAllByRolesIn(List<Role> roleList);

    @Query(value = "SELECT * FROM maoyao.test",nativeQuery = true)
    List<Map<String,Integer>> findUserAuthsRole();

    @Query(value = "insert into maoyao.test(user_auths_id,roles_id) value(?1,?2) ;",nativeQuery = true)
    void insert(Integer userAuthsId,Integer rolesId);

    UserAuths findFirstByUsernameAndIdentityType(String username,Integer type);

    UserAuths findUserAuthsByUsernameAndIdentityType(String userName,Integer type);

    List<UserAuths> findAllByRoles(Role role);

    @Query(value = "SELECT coalesce(count(id),0) FROM maoyao.user_auths_roles where roles_id=?1",nativeQuery = true)
    Integer findCountByRolesId(Integer roleId);

    @Query(value = "SELECT coalesce(count(id),0) FROM maoyao.user_auths_roles where roles_id in ?1",nativeQuery = true)
    Integer findCountByRolesIdIn(List<Integer> roleId);

    List<UserAuths> findAllByIdInAndRoles(List<Integer> idList,Role role);

    List<UserAuths> findAllByIdInAndRolesIn(List<Integer> idList,List<Role> role);

}
