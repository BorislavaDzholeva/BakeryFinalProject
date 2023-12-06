package BakeryProject.demo.aop;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class AfterRegister {
    private final Logger logger = Logger.getLogger(AfterRegister.class.getName());

    @After("PointCut.userNewRegistration()")
    public void alertForNewRegistration() {
        logger.info("New user registered in Bakery Website!");
    }

}
