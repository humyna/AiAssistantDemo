package info.zoio.langchain4jside.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import info.zoio.langchain4jside.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author humyna
 * @date 2025/01/16 17:13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
