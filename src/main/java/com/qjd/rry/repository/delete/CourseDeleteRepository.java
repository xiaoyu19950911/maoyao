package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.CourseDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CourseDeleteRepository extends JpaRepository<CourseDelete, Integer> {

    Page<CourseDelete> findAllByUserId(Integer userId, Pageable pageable);

    Page<CourseDelete> findAllByUserIdAndPriceAfterOrderByCreateTimeDesc(Integer userId, BigDecimal price, Pageable pageable);

    Page<CourseDelete> findAllByUserIdOrderByPriceDescCreateTimeDesc(Integer userId, Pageable pageable);

    Page<CourseDelete> findAllByUserIdAndPlayTypeIn(Integer userId, List<Integer> playType, Pageable pageable);

    Page<CourseDelete> findAllByStartTimeAfterOrderByCreateTimeDesc(Date date, Pageable pageable);

    Page<CourseDelete> findAllByPlayTypeAndStartTimeAfterOrderByCreateTimeDesc(Integer type, Date date, Pageable pageable);

    CourseDelete findFirstByLiveRoomId(Integer liveRoomId);

    CourseDelete findFirstById(Integer id);

    Page<CourseDelete> findAllByLiveRoomIdInOrderByCreateTime(List<Integer> liveRoomIdList, Pageable pageable);

    Page<CourseDelete> findAllByUseVip(Boolean isTrue, Pageable pageable);

    Page<CourseDelete> findAllByPriceAfterAndUseVipAndUserIdIn(BigDecimal price, Boolean isTrue, List<Integer> userIdList, Pageable pageable);

    List<CourseDelete> findAllByUserId(Integer userId);

    List<CourseDelete> findTop3ByUserIdAndStartTimeAfterOrderByStartTimeDesc(Integer userId, Date date);

    List<CourseDelete> findAllByNextTimeBetweenAndCountAfter(Date date, Date date1, Integer count);

    List<CourseDelete> findAllByTitleLike(String searchInfo);

    @Query(value = "SELECT count(*) FROM maoyao.course_delete where user_id=?1", nativeQuery = true)
    Integer findCountByUserId(Integer userId);

    @Query(value = "SELECT count(*) FROM maoyao.course_delete where play_type=?1", nativeQuery = true)
    Integer findCountByCourseType(Integer playType);

    @Query(value = "SELECT count(*) FROM maoyao.course_delete where user_id=?1 and play_type=?2", nativeQuery = true)
    Integer findCourseCountByUserIdAndCourseType(Integer userId, Integer playType);

    @Query(value = "select max(update_time),user_id from maoyao.course_delete where user_id in ?1 group by user_id order by max(update_time) desc limit 9; ", nativeQuery = true)
    List<Map<String, Object>> findMapsByUpdateTimeAndUserId(List<Integer> userIdList);

    @Query(value = "SELECT * FROM maoyao.course_delete where play_type=1 ORDER BY live_room_id NOT IN ?1 ,start_time desc ", nativeQuery = true,countQuery = "SELECT count(id) FROM maoyao.course_delete where play_type=1;")
    Page<CourseDelete> findAllByTime(List<Integer> liveRoomList, Pageable pageable);

    Page<CourseDelete> findAllByLiveRoomIdInOrRecordStatusOrderByCreateTimeDesc(List<Integer> idList, Integer status, Pageable pageable);

    Page<CourseDelete> findAllByIdInOrderByCreateTimeDesc(List<Integer> idList, Pageable pageable);

    Page<CourseDelete> findAllByPrice(BigDecimal price, Pageable pageable);

    Page<CourseDelete> findAllByPriceAfter(BigDecimal price, Pageable pageable);

    void deleteAllByIdIn(List<Integer> idList);

    Page<CourseDelete> findAllByUserIdAndStartTimeAfterOrderByCreateTimeDesc(Integer userId, Date classStartTime, Pageable pageable);

    Page<CourseDelete> findAllByPlayTypeAndUserIdAndStartTimeAfterOrderByCreateTimeDesc(Integer type, Integer userId, Date classStartTime, Pageable pageable);

    Page<CourseDelete> findAllByUserIdAndPlayTypeInAndStartTimeBefore(Integer userId, List<Integer> playTypeList, Date classStartTime, Pageable pageable);

    @Query(value = "SELECT * FROM maoyao.course_delete where user_id=?1 and (play_type=?2 or start_time < ?3) order by create_time desc ", nativeQuery = true, countQuery = "SELECT count(*) FROM maoyao.course_delete where user_id=?1 and (play_type=?2 or start_time > ?3)")
    Page<CourseDelete> findAllByUserIdAndPlayTypeOrStartTimeBefore(Integer userId, Integer playType, Date classStartTime, Pageable pageable);

    Page<CourseDelete> findAllByPlayTypeIn(List<Integer> typeList, Pageable pageable);

    Page<CourseDelete> findAllByPlayTypeOrderByStartTimeDesc(Integer playType, Pageable pageable);

    Page<CourseDelete> findAllByRecordStatusOrderByCreateTimeDesc(Integer status, Pageable pageable);

}
