package TTT;

import com.stylefeng.guns.rest.common.persistence.model.FilmInfoT;
import com.stylefeng.guns.rest.common.persistence.dao.FilmInfoTMapper;
import TTT.IFilmInfoTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 影片主表 服务实现类
 * </p>
 *
 * @author zhangjingcheng
 * @since 2019-03-29
 */
@Service
public class FilmInfoTServiceImpl extends ServiceImpl<FilmInfoTMapper, FilmInfoT> implements IFilmInfoTService {

}
