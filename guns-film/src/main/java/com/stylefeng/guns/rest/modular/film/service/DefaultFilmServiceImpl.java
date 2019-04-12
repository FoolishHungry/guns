package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
@Service(interfaceClass = FilmServiceApi.class)
public class DefaultFilmServiceImpl implements FilmServiceApi {

    @Autowired
    private BannerTMapper bannerTMapper;
    @Autowired
    private FilmTMapper filmTMapper;
    @Autowired
    private CatDictTMapper catDictTMapper;
    @Autowired
    private YearDictTMapper yearDictTMapper;
    @Autowired
    private SourceDictTMapper sourceDictTMapper;
    @Override
    public List<BannerVO> getBanners() {
        List<BannerVO> result = new ArrayList<>();
        List<BannerT> banners = bannerTMapper.selectList(null);

        for(BannerT bannerT : banners){
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerId(bannerT.getUuid()+"");
            bannerVO.setBannerUrl(bannerT.getBannerUrl());
            bannerVO.setBannerAddress(bannerT.getBannerAddress());
            result.add(bannerVO);
        }

        return result;
    }

    private List<FilmInfo> getFilmInfos(List<FilmT> films){
        List<FilmInfo> filmInfos = new ArrayList<>();
        for(FilmT filmT : films){
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(filmT.getFilmScore());
            filmInfo.setImgAddress(filmT.getImgAddress());
            filmInfo.setFilmType(filmT.getFilmType());
            filmInfo.setFilmScore(filmT.getFilmScore());
            filmInfo.setFilmName(filmT.getFilmName());
            filmInfo.setFilmId(filmT.getUuid()+"");
            filmInfo.setExpectNum(filmT.getFilmPresalenum());
            filmInfo.setBoxNum(filmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(filmT.getFilmTime()));
            //将转换的对象放入结果集
            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {

        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        // 热映影片的限制条件
        EntityWrapper<FilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");
        // 判断是否是首页需要的内容
        if(isLimit){
            //如果是，则限制条数、限制内容为热映影片
            Page<FilmT> page = new Page<>(1, nums);
            List<FilmT> films = filmTMapper.selectPage(page,entityWrapper);
            // 组织filmInfos
            filmInfos = getFilmInfos(films);
            filmVO.setFilmNum(films.size());
            filmVO.setFilmInfo(filmInfos);
        }else{
            // 如果不是，则是列表页，同样需要限制内容为热映影片
            Page<FilmT> page = null;
            // 根据sortId的不同，来组织不同的Page对象
            switch (sortId){
                case 1 :
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2 :
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3 :
                    page = new Page<>(nowPage, nums, "film_score");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
            }
            // 如果sourceId, yearId, catId 不为99，则表示要按照对应的编号进行查询
            if(sourceId != 99){
                entityWrapper.eq("film_source", sourceId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date", yearId);
            }
            if(catId != 99){
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }
            List<FilmT> films = filmTMapper.selectPage(page,entityWrapper);
            // 组织filmInfos
            filmInfos = getFilmInfos(films);
            filmVO.setFilmNum(films.size());

            // 需要总页数
            int totalCounts = filmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts/nums) + 1;

            filmVO.setFilmInfo(filmInfos);
            filmVO.setNowPage(nowPage);
            filmVO.setTotalPage(totalPages);
        }

        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        // 即将上映影片的限制条件
        EntityWrapper<FilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");
        // 判断是否是首页需要的内容
        if(isLimit){
            //如果是，则限制条数、限制内容为热映影片
            Page<FilmT> page = new Page<>(1, nums);
            List<FilmT> films = filmTMapper.selectPage(page,entityWrapper);
            // 组织filmInfos
            filmInfos = getFilmInfos(films);
            filmVO.setFilmNum(films.size());
            filmVO.setFilmInfo(filmInfos);
        }else{
            // 如果不是，则是列表页，同样需要限制内容为即将上映影片
            Page<FilmT> page = null;
            // 根据sortId的不同，来组织不同的Page对象
            switch (sortId){
                case 1 :
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                case 2 :
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3 :
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
            }
            // 如果sourceId, yearId, catId 不为99，则表示要按照对应的编号进行查询
            if(sourceId != 99){
                entityWrapper.eq("film_source", sourceId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date", yearId);
            }
            if(catId != 99){
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }
            List<FilmT> films = filmTMapper.selectPage(page,entityWrapper);
            // 组织filmInfos
            filmInfos = getFilmInfos(films);
            filmVO.setFilmNum(films.size());

            // 需要总页数
            int totalCounts = filmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts/nums) + 1;

            filmVO.setFilmInfo(filmInfos);
            filmVO.setNowPage(nowPage);
            filmVO.setTotalPage(totalPages);
        }

        return filmVO;
    }

    @Override
    public FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {

        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        // 即将上映影片的限制条件
        EntityWrapper<FilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "3");
        // 如果不是，则是列表页，同样需要限制内容为即将上映影片
        Page<FilmT> page = null;
        // 根据sortId的不同，来组织不同的Page对象
        switch (sortId){
            case 1 :
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
            case 2 :
                page = new Page<>(nowPage, nums, "film_time");
                break;
            case 3 :
                page = new Page<>(nowPage, nums, "film_score");
                break;
            default:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
        }
        // 如果sourceId, yearId, catId 不为99，则表示要按照对应的编号进行查询
        if(sourceId != 99){
            entityWrapper.eq("film_source", sourceId);
        }
        if(yearId != 99){
            entityWrapper.eq("film_date", yearId);
        }
        if(catId != 99){
            String catStr = "%#" + catId + "#%";
            entityWrapper.like("film_cats", catStr);
        }
        List<FilmT> films = filmTMapper.selectPage(page,entityWrapper);
        // 组织filmInfos
        filmInfos = getFilmInfos(films);
        filmVO.setFilmNum(films.size());

        // 需要总页数
        int totalCounts = filmTMapper.selectCount(entityWrapper);
        int totalPages = (totalCounts/nums) + 1;

        filmVO.setFilmInfo(filmInfos);
        filmVO.setNowPage(nowPage);
        filmVO.setTotalPage(totalPages);
        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        // 条件 -> 正在上映的，票房前10名
        EntityWrapper<FilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<FilmT> page = new Page<>(1,10,"film_box_office");
        List<FilmT> films = filmTMapper.selectPage(page,entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(films);

        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        // 条件 -> 即将上映的，预售前10名
        EntityWrapper<FilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");
        Page<FilmT> page = new Page<>(1,10,"film_preSaleNum");
        List<FilmT> films = filmTMapper.selectPage(page,entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(films);

        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        // 条件 -> 正在上映的，评分前10名
        EntityWrapper<FilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<FilmT> page = new Page<>(1,10,"film_score");
        List<FilmT> films = filmTMapper.selectPage(page,entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(films);

        return filmInfos;
    }

    @Override
    public List<CatVO> getCats() {
        List<CatVO> cats = new ArrayList<>();
        // 查询实体对象 - CatDictT
        List<CatDictT> catTs = catDictTMapper.selectList(null);
        // 将实体对象转换为业务对象 - CatVO
        for(CatDictT catDictT : catTs){
            CatVO catVO = new CatVO();
            catVO.setCatId(catDictT.getUuid()+"");
            catVO.setCatName(catDictT.getShowName());

            cats.add(catVO);
        }
        return cats;
    }

    @Override
    public List<SourceVO> getSource() {
        List<SourceVO> sources = new ArrayList<>();
        List<SourceDictT> sourceDictTS = sourceDictTMapper.selectList(null);
        for(SourceDictT sourceDictT : sourceDictTS){
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(sourceDictT.getUuid()+"");
            sourceVO.setSourceName(sourceDictT.getShowName());
            sources.add(sourceVO);
        }
        return sources;
    }

    @Override
    public List<YearVO> getYears() {
        List<YearVO> years = new ArrayList<>();
        // 查询实体对象 -
        List<YearDictT> yearTs = yearDictTMapper.selectList(null);
        // 将实体对象转换为业务对象
        for(YearDictT yearDictT:yearTs){
            YearVO yearVO = new YearVO();
            yearVO.setYearId(yearDictT.getUuid()+"");
            yearVO.setYearName(yearDictT.getShowName());

            years.add(yearVO);
        }
        return years;
    }

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String searchParam) {
        return null;
    }
}
