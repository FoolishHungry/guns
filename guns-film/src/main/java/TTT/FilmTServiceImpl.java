package TTT;

import com.stylefeng.guns.rest.common.persistence.model.FilmT;
import com.stylefeng.guns.rest.common.persistence.dao.FilmTMapper;
import TTT.IFilmTService;
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
public class FilmTServiceImpl extends ServiceImpl<FilmTMapper, FilmT> implements IFilmTService {

}
