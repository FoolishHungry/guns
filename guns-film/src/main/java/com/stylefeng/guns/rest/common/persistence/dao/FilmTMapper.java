package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.api.film.vo.FilmDetailVO;
import com.stylefeng.guns.rest.common.persistence.model.FilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author zhangjingcheng
 * @since 2019-03-29
 */
public interface FilmTMapper extends BaseMapper<FilmT> {

    FilmDetailVO getFilmDetailByName(@Param("filmName")String filmName);

    FilmDetailVO getFilmDetailById(@Param("uuid") String uuid);

}
