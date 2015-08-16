package ${package};
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;

@ComponentScan
@EnableAutoConfiguration
public class Application {

public static void main(String[] args) {
SpringApplication.run(Application.class);
}
}