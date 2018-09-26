package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.UserDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDeleteRepository extends JpaRepository<UserDelete, Integer>,JpaSpecificationExecutor<UserDelete> {
    UserDelete findUserById(Integer id);

    List<UserDelete> findAllByWeiHouUserIdIsNotNull();

    Page<UserDelete> findUserByIdInAndStatusInOrderByAttentionCountDesc(List<Integer> idList, List<Integer> statusList, Pageable pageable);

    Page<UserDelete> findUserByIdInAndStatusInAndNicknameLikeOrderByAttentionCountDesc(List<Integer> idList, List<Integer> statusList, String nickName, Pageable pageable);

    UserDelete findUserByToken(String token);

    UserDelete findUserByInvitationCode(Integer code);

    List<UserDelete> findAllByIsBanner(Boolean banner);

    List<UserDelete> findAllByIdLikeOrNicknameLike(Integer searchInfo, String searchInfo2);

    @Query(value = "SELECT coalesce(count(*),0) FROM maoyao.user_delete where accept_invitation_code=?1",nativeQuery = true)
    Integer findCountByAcceptInvitationCode(Integer code);

    @Query(value = "SELECT coalesce(count(*),0) FROM maoyao.user_delete where status=?1",nativeQuery = true)
    Integer findCountByStatus(Integer status);

    List<UserDelete> findAllByNicknameLike(String searchInfo2);


    Page<UserDelete> findAllByStatusOrderByCertificationTimeDesc(Integer status, Pageable pageable);

    Page<UserDelete> findAllByStatusAndNicknameLikeOrderByCertificationTimeDesc(Integer status, String nickName, Pageable pageable);

    List<UserDelete> findAllByStatus(Integer status);

    List<UserDelete> findAllByAcceptInvitationCodeAndIdInAndStatusIn(Integer code, List<Integer> id, List<Integer> status);

    Page<UserDelete> findAllByStatusInAndIdIn(List<Integer> statusList, List<Integer> idList, Pageable pageable);

    Page<UserDelete> findAllByStatusIn(List<Integer> statusList, Pageable pageable);

    Page<UserDelete> findAllByStatusInAndNicknameLike(List<Integer> statusList, String nickName, Pageable pageable);

    UserDelete findFirstByInvitationCode(Integer code);

    Page<UserDelete> findAllByInvitationCodeNotNull(Pageable pageable);

    Page<UserDelete> findAllByStatusInAndInvitationCodeNotNull(List<Integer> statusList, Pageable pageable);

    List<UserDelete> findAllByAcceptInvitationCodeAndStatusIn(Integer code, List<Integer> statusList);

    Page<UserDelete> findAllByAcceptInvitationCode(Integer code, Pageable pageable);

    Page<UserDelete> findAllByAcceptInvitationCodeAndInvitationCodeIsNull(Integer code, Pageable pageable);

    Page<UserDelete> findAllByIdIn(List<Integer> idList, Pageable pageable);

    Page<UserDelete> findAllByIdInAndStatusIn(List<Integer> idList, List<Integer> status, Pageable pageable);

    Page<UserDelete> findAllByIdInAndStatusInAndNicknameLike(List<Integer> idList, List<Integer> status, String nickName, Pageable pageable);

    List<UserDelete> findAllByIdIn(List<Integer> idList);

}
