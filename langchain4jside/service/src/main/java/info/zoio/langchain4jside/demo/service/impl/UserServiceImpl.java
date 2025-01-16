package info.zoio.langchain4jside.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import info.zoio.langchain4jside.demo.entity.User;
import info.zoio.langchain4jside.demo.mapper.UserMapper;
import info.zoio.langchain4jside.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author humyna
 * @date 2025/01/16 17:15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
