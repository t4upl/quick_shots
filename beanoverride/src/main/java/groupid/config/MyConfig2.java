package groupid.config;

import groupid.component.Bar;
import groupid.component.Text;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig2 {

  @Bean
  public Text text() {
    return new Bar();
  }

  @Bean
  public MyConfig2ExclusiveBean myConfig2ExclusiveBean(){
    return new MyConfig2ExclusiveBean();
  }

  public static class MyConfig2ExclusiveBean {
    public String sayHello() {
      return "MyConfig1ExclusiveBean 2";
    }
  }

}
