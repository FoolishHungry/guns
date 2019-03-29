package TTT;

import com.stylefeng.guns.rest.common.persistence.model.ActorT;
import com.stylefeng.guns.rest.common.persistence.dao.ActorTMapper;
import TTT.IActorTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 演员表 服务实现类
 * </p>
 *
 * @author zhangjingcheng
 * @since 2019-03-29
 */
@Service
public class ActorTServiceImpl extends ServiceImpl<ActorTMapper, ActorT> implements IActorTService {

}
