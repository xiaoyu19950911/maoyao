package com.qjd.rry.repository;

import com.qjd.rry.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CourseRepository extends JpaRepository<Course, Integer>,JpaSpecificationExecutor<Course> {

    Page<Course> findAllByUserId(Integer userId, Pageable pageable);

    Page<Course> findAllByUserIdAndPriceAfterOrderByCreateTimeDesc(Integer userId, BigDecimal price, Pageable pageable);

    Page<Course> findAllByUserIdOrderByPriceDescCreateTimeDesc(Integer userId,Pageable pageable);

    Page<Course> findAllByUserIdAndPlayTypeIn(Integer userId, List<Integer> playType, Pageable pageable);

    Page<Course> findAllByStartTimeAfterOrderByCreateTimeDesc(Date date, Pageable pageable);

    Page<Course> findAllByPlayTypeAndStartTimeAfterOrderByCreateTimeDesc(Integer type,Date date, Pageable pageable);

    Course findFirstByLiveRoomId(Integer liveRoomId);

    Course findFirstById(Integer id);

    Page<Course> findAllByLiveRoomIdInOrderByCreateTime(List<Integer> liveRoomIdList, Pageable pageable);

    Page<Course> findAllByUseVip(Boolean isTrue, Pageable pageable);

    Page<Course> findAllByPriceAfterAndUseVipAndUserIdIn(BigDecimal price, Boolean isTrue, List<Integer> userIdList, Pageable pageable);

    Page<Course> findAllByPriceAfterAndUseVipAndUserIdInOrderByCreateTimeDesc(BigDecimal price, Boolean isTrue, List<Integer> userIdList, Pageable pageable);

    List<Course> findAllByUserId(Integer userId);

    List<Course> findTop3ByUserIdAndStartTimeAfterOrderByStartTimeDesc(Integer userId, Date date);

    List<Course> findAllByNextTimeBetweenAndCountAfter(Date date, Date date1, Integer count);

    List<Course> findAllByTitleLike(String searchInfo);

    Page<Course> findAllByTitleLike(String searchInfo,Pageable pageable);

    Page<Course> findAllByPlayTypeInAndTitleLike(List<Integer> typeList,String searchInfo,Pageable pageable);

    @Query(value = "SELECT count(*) FROM maoyao.course where user_id=?1", nativeQuery = true)
    Integer findCountByUserId(Integer userId);

    @Query(value = "SELECT count(*) FROM maoyao.course where play_type=?1", nativeQuery = true)
    Integer findCountByCourseType(Integer playType);

    @Query(value = "SELECT count(*) FROM maoyao.course where user_id=?1 and play_type=?2", nativeQuery = true)
    Integer findCourseCountByUserIdAndCourseType(Integer userId, Integer playType);

    @Query(value = "select max(update_time),user_id from maoyao.course where user_id in ?1 group by user_id order by max(update_time) desc limit 9; ", nativeQuery = true)
    List<Map<String, Object>> findMapsByUpdateTimeAndUserId(List<Integer> userIdList);

    @Query(value = "SELECT * FROM maoyao.course where play_type=1 ORDER BY live_room_id NOT IN ?1 ,start_time desc ", nativeQuery = true,countQuery = "SELECT count(id) FROM maoyao.course where play_type=1;")
    Page<Course> findAllByTime(List<Integer> liveRoomList,Pageable pageable);

    Page<Course> findAllByLiveRoomIdInOrRecordStatusOrderByCreateTimeDesc(List<Integer> idList,Integer status,Pageable pageable);

    Page<Course> findAllByIdInOrderByCreateTimeDesc(List<Integer> idList, Pageable pageable);

    Page<Course> findAllByPrice(BigDecimal price, Pageable pageable);

    Page<Course> findAllByPriceOrderByCreateTimeDesc(BigDecimal price, Pageable pageable);

    Page<Course> findAllByPriceAfter(BigDecimal price, Pageable pageable);

    void deleteAllByIdIn(List<Integer> idList);

    Page<Course> findAllByUserIdAndStartTimeAfterOrderByCreateTimeDesc(Integer userId, Date classStartTime, Pageable pageable);

    Page<Course> findAllByPlayTypeAndUserIdAndStartTimeAfterOrderByCreateTimeDesc(Integer type,Integer userId, Date classStartTime, Pageable pageable);

    Page<Course> findAllByUserIdAndPlayTypeInAndStartTimeBefore(Integer userId, List<Integer> playTypeList, Date classStartTime, Pageable pageable);

    @Query(value = "SELECT * FROM maoyao.course where user_id=?1 and (play_type=?2 or start_time < ?3) order by create_time desc ", nativeQuery = true, countQuery = "SELECT count(*) FROM maoyao.course where user_id=?1 and (play_type=?2 or start_time > ?3)")
    Page<Course> findAllByUserIdAndPlayTypeOrStartTimeBefore(Integer userId, Integer playType, Date classStartTime, Pageable pageable);

    Page<Course> findAllByPlayTypeIn(List<Integer> typeList, Pageable pageable);

    Page<Course> findAllByPlayTypeOrderByStartTimeDesc(Integer playType, Pageable pageable);

    Page<Course> findAllByRecordStatusOrderByCreateTimeDesc(Integer status, Pageable pageable);

    Page<Course> findAllByRecordStatusOrderByStartTimeDesc(Integer status, Pageable pageable);

    List<Course> findAllByIdIn(List<Integer> idList);

    List<Course> findAllByPlayTypeAndStartTimeAfter(Integer playType,Date time);
}
