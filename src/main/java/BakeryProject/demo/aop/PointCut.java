package BakeryProject.demo.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCut {
    @Pointcut("execution(* BakeryProject.demo.service.UserService.registerUser(..))")
    public void userNewRegistration() {}


    }

