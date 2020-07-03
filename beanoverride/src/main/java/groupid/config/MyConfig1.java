package groupid.config;

import groupid.component.Foo;
import groupid.component.Text;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig1 {


  @Bean
  public Text text() {
    return new Foo();
  }

  @Bean
  public MyConfig1ExclusiveBean myConfig1ExclusiveBean(){
    return new MyConfig1ExclusiveBean();
  }

  public static class MyConfig1ExclusiveBean {
    public String sayHello() {
      return "MyConfig1ExclusiveBean 1";
    }
  }

}
