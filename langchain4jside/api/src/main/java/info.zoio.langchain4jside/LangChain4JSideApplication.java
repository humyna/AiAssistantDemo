package info.zoio.langchain4jside;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author humyna
 * @date 2024/11/29 10:00
 */
@MapperScan("info.zoio.langchain4jside.**.mapper")
@SpringBootApplication
public class LangChain4JSideApplication {
    public static void main(String[] args) {
        SpringApplication.run(LangChain4JSideApplication.class, args);
    }
}
