package info.zoio.langchain4jside.config.db;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.h2.server.web.WebServlet;

/**
 * H2Console 配置类
 *
 * @author humyna
 * @date 2025/01/08 15:45
 */

@Configuration
public class H2ConsoleConfiguration {
    @Bean
    public ServletRegistrationBean<WebServlet> h2ConsoleServlet() {
        ServletRegistrationBean<WebServlet> servletRegistration = new ServletRegistrationBean<>(new WebServlet());
        servletRegistration.addUrlMappings("/h2/*");
        servletRegistration.addInitParameter("-webAdminPassword", "humyna");
        return servletRegistration;
    }
}
