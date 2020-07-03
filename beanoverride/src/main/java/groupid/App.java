package groupid;

import groupid.component.Text;
import groupid.config.MyConfig1.MyConfig1ExclusiveBean;
import groupid.config.MyConfig2.MyConfig2ExclusiveBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@ComponentScan
public class App 
{
    public static void main( String[] args)  {
        //CONFIGURATION ALLOWING OVERRIDING
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        //CONFIGURATION WITH OVERRIDING DISABLED
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(App.class);
        ctx.setAllowBeanDefinitionOverriding(false);
        ctx.refresh();

        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        Object text = ctx.getBean("text");
        System.out.println(((Text)text).getText());
        System.out.println(((MyConfig1ExclusiveBean)ctx.getBean("myConfig1ExclusiveBean")).sayHello());
        System.out.println(((MyConfig2ExclusiveBean)ctx.getBean("myConfig2ExclusiveBean")).sayHello());
        System.out.println( "\nEND" );
    }
}
