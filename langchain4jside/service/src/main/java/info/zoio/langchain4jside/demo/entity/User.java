package info.zoio.langchain4jside.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author humyna
 * @date 2025/01/16 17:11
 */
@Data
@TableName("users")
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
}
