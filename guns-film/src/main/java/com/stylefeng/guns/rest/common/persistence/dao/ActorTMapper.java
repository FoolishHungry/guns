package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.api.film.vo.ActorVO;
import com.stylefeng.guns.rest.common.persistence.model.ActorT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author zhangjingcheng
 * @since 2019-03-29
 */
public interface ActorTMapper extends BaseMapper<ActorT> {

    List<ActorVO> getActors(@Param("filmId") String filmId);

}
