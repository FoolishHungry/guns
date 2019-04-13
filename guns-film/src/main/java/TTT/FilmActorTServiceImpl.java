package TTT;

import com.stylefeng.guns.rest.common.persistence.model.FilmActorT;
import com.stylefeng.guns.rest.common.persistence.dao.FilmActorTMapper;
import TTT.IFilmActorTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 影片与演员映射表 服务实现类
 * </p>
 *
 * @author zhangjingcheng
 * @since 2019-04-13
 */
@Service
public class FilmActorTServiceImpl extends ServiceImpl<FilmActorTMapper, FilmActorT> implements IFilmActorTService {

}
