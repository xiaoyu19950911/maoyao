package com.qjd.rry.repository;

import com.qjd.rry.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {
    User findUserById(Integer id);

    List<User> findAllByWeiHouUserIdIsNotNull();

    Page<User> findUserByIdInAndStatusInOrderByAttentionCountDesc(List<Integer> idList,List<Integer> statusList,Pageable pageable);

    Page<User> findUserByIdInAndStatusInAndNicknameLikeOrderByAttentionCountDesc(List<Integer> idList,List<Integer> statusList,String nickName,Pageable pageable);

    User findUserByToken(String token);

    User findUserByInvitationCode(Integer code);

    Page<User> findAllByIsBanner(Boolean banner,Pageable pageable);

    List<User> findAllByIdLikeOrNicknameLike(Integer searchInfo,String searchInfo2);

    @Query(value = "SELECT coalesce(count(id),0) FROM maoyao.user where accept_invitation_code=?1 and invitation_code is null",nativeQuery = true)
    Integer findCountByAcceptInvitationCode(Integer code);

    @Query(value = "SELECT coalesce(count(id),0) FROM maoyao.user where status=?1",nativeQuery = true)
    Integer findCountByStatus(Integer status);

    List<User> findAllByNicknameLike(String searchInfo2);


    Page<User> findAllByStatusOrderByCertificationTimeDesc(Integer status,Pageable pageable);

    Page<User> findAllByStatusAndNicknameLikeOrderByCertificationTimeDesc(Integer status,String nickName,Pageable pageable);

    List<User> findAllByStatus(Integer status);

    List<User> findAllByAcceptInvitationCodeAndIdInAndStatusIn(Integer code,List<Integer> id,List<Integer> status);

    Page<User> findAllByStatusInAndIdIn(List<Integer> statusList,List<Integer> idList,Pageable pageable);

    Page<User> findAllByStatusIn(List<Integer> statusList,Pageable pageable);

    Page<User> findAllByStatusInAndNicknameLike(List<Integer> statusList,String nickName,Pageable pageable);

    User findFirstByInvitationCode(Integer code);

    Page<User> findAllByInvitationCodeNotNull(Pageable pageable);

    Page<User> findAllByStatusInAndInvitationCodeNotNull(List<Integer> statusList,Pageable pageable);

    List<User> findAllByAcceptInvitationCodeAndStatusIn(Integer code,List<Integer> statusList);

    Page<User> findAllByAcceptInvitationCode(Integer code, Pageable pageable);

    Page<User> findAllByAcceptInvitationCodeAndInvitationCodeIsNull(Integer code, Pageable pageable);

    Page<User> findAllByIdIn(List<Integer> idList,Pageable pageable);

    Page<User> findAllByIdInAndStatusIn(List<Integer> idList,List<Integer> status,Pageable pageable);

    Page<User> findAllByIdInAndStatusInAndNicknameLike(List<Integer> idList,List<Integer> status,String nickName,Pageable pageable);

    List<User> findAllByIdIn(List<Integer> idList);

    List<User> findAllByIsBanner(Boolean isBanner);

    User findFirstByAcceptInvitationCode(Integer invitationCode);

    @Query(value = "SELECT coalesce(count(id),0) FROM maoyao.user where status in ?1",nativeQuery = true)
    Integer findCountByStatusIn(List<Integer> statusList);
}
