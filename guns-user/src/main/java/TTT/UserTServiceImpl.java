package TTT;

import com.stylefeng.guns.rest.common.persistence.model.UserT;
import com.stylefeng.guns.rest.common.persistence.dao.UserTMapper;
import TTT.IUserTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhangjingcheng
 * @since 2019-03-19
 */
@Service
public class UserTServiceImpl extends ServiceImpl<UserTMapper, UserT> implements IUserTService {

}
