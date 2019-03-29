package TTT;

import com.stylefeng.guns.rest.common.persistence.model.BannerT;
import com.stylefeng.guns.rest.common.persistence.dao.BannerTMapper;
import TTT.IBannerTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * banner信息表 服务实现类
 * </p>
 *
 * @author zhangjingcheng
 * @since 2019-03-29
 */
@Service
public class BannerTServiceImpl extends ServiceImpl<BannerTMapper, BannerT> implements IBannerTService {

}
