package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmAsyncServiceApi;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmAsyncServiceApi.class)
public class DefaultFilmAsyncServiceImpl implements FilmAsyncServiceApi {

    @Autowired
    private FilmInfoTMapper filmInfoTMapper;
    @Autowired
    private ActorTMapper actorTMapper;


    private FilmInfoT getFilmInfo(String filmId){

        FilmInfoT filmInfoT = new FilmInfoT();
        filmInfoT.setFilmId(filmId);

        filmInfoT = filmInfoTMapper.selectOne(filmInfoT);

        return filmInfoT;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {

        FilmInfoT filmInfoT = getFilmInfo(filmId);

        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(filmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);
        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        FilmInfoT filmInfoT = getFilmInfo(filmId);
        // 图片地址是五个以逗号为分隔的链接URL
        String filmImgStr = filmInfoT.getFilmImgs();
        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);
        return imgVO;
    }

    @Override
    public ActorVO getDectInfo(String filmId) {

        FilmInfoT filmInfoT = getFilmInfo(filmId);
        //获取导演编号
        Integer directId = filmInfoT.getDirectorId();
        ActorT actorT = actorTMapper.selectById(directId);

        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(actorT.getActorImg());
        actorVO.setDirectorName(actorT.getActorName());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {

        List<ActorVO> actors = actorTMapper.getActors(filmId);

        return actors;
    }
}
