package TTT;

import com.stylefeng.guns.rest.common.persistence.model.CatDictT;
import com.stylefeng.guns.rest.common.persistence.dao.CatDictTMapper;
import TTT.ICatDictTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类型信息表 服务实现类
 * </p>
 *
 * @author zhangjingcheng
 * @since 2019-03-29
 */
@Service
public class CatDictTServiceImpl extends ServiceImpl<CatDictTMapper, CatDictT> implements ICatDictTService {

}
