package bitcamp.myapp;

import bitcamp.myapp.annotation.LoginUserArgumentResolver;
import bitcamp.myapp.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * packageName    : bitcamp.myapp
 * fileName       : ServerApp
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 내장 Tomcat 서버를 설정하고 실행하는 클래스입니다.
 *                  이 클래스는 웹 애플리케이션을 위한 서버 환경을 구성하고 실행합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 10. 18.       narilee       스프링부트 적용
 */
@SpringBootApplication
@PropertySource("file:${user.home}/config/ncp.properties")
@EnableTransactionManagement
public class ServerApp implements WebMvcConfigurer {

  @Autowired
  ApplicationContext appCtx;

  public ServerApp() {
    //AWS 경고메세지 제거
    System.getProperties().setProperty("aws.java.v1.disableDeprecationAnnouncements", "true");
  }

  /**
   * 애플리케이션의 main 메서드입니다.
   * ServerApp의 인스턴스를 생성하고 excute를 호출합니다.
   *
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(ServerApp.class, args);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new AdminInterceptor())
        .addPathPatterns("/users*");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new LoginUserArgumentResolver());
  }
}
