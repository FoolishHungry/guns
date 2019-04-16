package com.stylefeng.guns.rest.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.AreaDictT;
import com.stylefeng.guns.rest.common.persistence.model.BrandDictT;
import com.stylefeng.guns.rest.common.persistence.model.CinemaT;
import com.stylefeng.guns.rest.common.persistence.model.HallDictT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = CinemaServiceAPI.class,executes = 10)
public class DefaultCinemaServiceImpl implements CinemaServiceAPI {

    @Autowired
    private CinemaTMapper cinemaTMapper;
    @Autowired
    private AreaDictTMapper areaDictTMapper;
    @Autowired
    private BrandDictTMapper brandDictTMapper;
    @Autowired
    private HallDictTMapper hallDictTMapper;
    @Autowired
    private HallFilmInfoTMapper hallFilmInfoTMapper;
    @Autowired
    private FieldTMapper fieldTMapper;

    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {

        // 业务实体集合
        List<CinemaVO> cinemas = new ArrayList<>();

        Page<CinemaT> page = new Page<>(cinemaQueryVO.getNowPage(),cinemaQueryVO.getPageSize());
        //判断是否传入查询条件 -> brandId,distId,hallType 是否==99
        EntityWrapper<CinemaT> entityWrapper = new EntityWrapper<>();
        if(cinemaQueryVO.getBrandId() != 99){
            entityWrapper.eq("brand_id",cinemaQueryVO.getBrandId());
        }
        if(cinemaQueryVO.getDistrictId() != 99){
            entityWrapper.eq("area_id",cinemaQueryVO.getDistrictId());
        }
        if(cinemaQueryVO.getHallType() != 99){
            entityWrapper.like("hall_ids","%#"+cinemaQueryVO.getHallType()+"#%");//改动
        }
        // 将数据实体转换为业务实体
        List<CinemaT> cinemaTS = cinemaTMapper.selectPage(page,entityWrapper);
        for(CinemaT cinemaT : cinemaTS){
            CinemaVO cinemaVO = new CinemaVO();

            cinemaVO.setUuid(cinemaT.getUuid()+"");
            cinemaVO.setMinimumPrice(cinemaT.getMinimumPrice()+"");
            cinemaVO.setCinemaName(cinemaT.getCinemaName()+"");
            cinemaVO.setAddress(cinemaT.getCinemaAddress()+"");

            cinemas.add(cinemaVO);
        }
        // 根据条件，判断影院列表总数
        long counts = cinemaTMapper.selectCount(entityWrapper);
        // 组织返回值对象
        Page<CinemaVO> result = new Page<>();
        result.setRecords(cinemas);
        result.setSize(cinemaQueryVO.getPageSize());
        result.setTotal(counts);

        return result;
    }

    @Override
    public List<BrandVO> getBrands(int brandId) {
        boolean flag = false;
        List<BrandVO> brandVOS = new ArrayList<>();
        // 判断brandId是否存在
        BrandDictT brandDictT = brandDictTMapper.selectById(brandId);
        // 判断brandId 是否等于 99
        if(brandId == 99||brandDictT==null||brandDictT.getUuid()==null){
            flag = true;
        }
        List<BrandDictT> brandDictTS = brandDictTMapper.selectList(null);

        // 判断flag如果为true,则将99置为isActive
        for(BrandDictT brand : brandDictTS){
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandName(brand.getShowName());
            brandVO.setBrandId(brand.getUuid()+"");
            if(flag){
                if(brand.getUuid() == 99){
                    brandVO.setActive(true);
                }
            }else{
                if(brand.getUuid() == brandId){
                    brandVO.setActive(true);
                }
            }
            brandVOS.add(brandVO);
        }
        return brandVOS;
    }

    @Override
    public List<AreaVO> getAreas(int areaId) {
        boolean flag = false;
        List<AreaVO> areaVOS = new ArrayList<>();
        // 判断brandId是否存在
        AreaDictT areaDictT = areaDictTMapper.selectById(areaId);
        // 判断brandId 是否等于 99
        if(areaId == 99||areaDictT==null||areaDictT.getUuid()==null){
            flag = true;
        }
        List<AreaDictT> areaDictTS = areaDictTMapper.selectList(null);

        // 判断flag如果为true,则将99置为isActive
        for(AreaDictT area : areaDictTS){
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaName(area.getShowName());
            areaVO.setAreaId(area.getUuid()+"");
            if(flag){
                if(area.getUuid() == 99){
                    areaVO.setActive(true);
                }
            }else{
                if(area.getUuid() == areaId){
                    areaVO.setActive(true);
                }
            }
            areaVOS.add(areaVO);
        }
        return areaVOS;
    }

    @Override
    public List<HallTypeVO> getHallTypes(int hallType) {
        boolean flag = false;
        List<HallTypeVO> hallTypeVOS = new ArrayList<>();
        // 判断brandId是否存在
        HallDictT hallDictT = hallDictTMapper.selectById(hallType);
        // 判断brandId 是否等于 99
        if(hallType == 99||hallDictT==null||hallDictT.getUuid()==null){
            flag = true;
        }
        List<HallDictT> hallDictTS = hallDictTMapper.selectList(null);

        // 判断flag如果为true,则将99置为isActive
        for(HallDictT hall : hallDictTS){
            HallTypeVO hallTypeVO = new HallTypeVO();
            hallTypeVO.setHalltypeName(hall.getShowName());
            hallTypeVO.setHalltypeId(hall.getUuid()+"");
            if(flag){
                if(hall.getUuid() == 99){
                    hallTypeVO.setActive(true);
                }
            }else{
                if(hall.getUuid() == hallType){
                    hallTypeVO.setActive(true);
                }
            }
            hallTypeVOS.add(hallTypeVO);
        }
        return hallTypeVOS;
    }
// 5、根据影院编号，获取影院信息
    @Override
    public CinemaInfoVO getCinemaInfoById(int cinemaId) {

        // 数据实体
        CinemaT cinemaT = cinemaTMapper.selectById(cinemaId);
        // 将数据实体转换为业务实体
        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();
        cinemaInfoVO.setCinemaAdress(cinemaT.getCinemaAddress());
        cinemaInfoVO.setImgUrl(cinemaT.getImgAddress());
        cinemaInfoVO.setCinemaPhone(cinemaT.getCinemaPhone());
        cinemaInfoVO.setCinemaName(cinemaT.getCinemaName());
        cinemaInfoVO.setCinemaId(cinemaT.getUuid()+"");

        return cinemaInfoVO;
    }

    //6、获取所有电影的信息和对应的放映场次信息，根据影院编号
    @Override
    public List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId){

        List<FilmInfoVO> filmInfos = fieldTMapper.getFilmInfos(cinemaId);

        return filmInfos;
    }
    //7、根据放映场次ID获取放映信息
    @Override
    public HallInfoVO getFilmFieldInfo(int fieldId){

        HallInfoVO hallInfoVO = fieldTMapper.getHallInfo(fieldId);

        return hallInfoVO;
    }

    //8、根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId){

        FilmInfoVO filmInfoVO = fieldTMapper.getFilmInfoById(fieldId);

        return filmInfoVO;
    }
}
