package com.qjd.rry.dao;

import com.qjd.rry.entity.Live;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-10 22:16
 **/
public interface LiveDao {

    /**
     *
     * @param live
     * @return Live
     */
    public Live createLive(Live live);

    /**
     *
     * @param id
     * @return Live
     */
    public Live getLiveById(Integer id);

    /**
     *
     * @param courseId
     * @return Live
     */
    Live getLiveByCourseId(Integer courseId);

    /**
     *
     * @param live
     * @return  Page<Live>
     */
    public Page<Live> getLiveInBatch(Live live, Pageable pageable);


    /**
     *
     * @param live
     * @return Live
     */
    public Live updateLive(Live live);

    /**
     *
     * @param liveList
     */
    void deleteInBatch(List<Live> liveList);
}
