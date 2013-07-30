//package com.clouck.repository;
//
//import java.lang.reflect.Method;
//import java.util.Arrays;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.amazonaws.AmazonServiceException;
//import com.clouck.exception.CloudVersionException;
//
////TODO: FIX ME, IT'S NOT PROPERED.
//@Aspect
//public class RetryRequiredAspect {
//    private static final Logger log = LoggerFactory.getLogger(RetryRequiredAspect.class);
//
//    @Around("@annotation(com.clouck.dao.RetryRequired)")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.debug("The method " + joinPoint.getSignature().getName()
//                + "() begins with " + Arrays.toString(joinPoint.getArgs()));
//
//        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
//        Method targetMethod = methodSignature.getMethod();
//        RetryRequired rr = targetMethod.getAnnotation(RetryRequired.class);
//        
//        int interval = rr.interval();
//        int waitMills = interval * 1000;
//        int time = rr.time();
//        
//        int i = 0;
//        while(i < time) {
//            try {
//                Object result = joinPoint.proceed();
//                log.debug("The method " + joinPoint.getSignature().getName()
//                        + "() ends with " + result);
//                return result;
//            } catch(AmazonServiceException ase) {
//                if (ase.getStatusCode() == 400 && ase.getMessage().matches("Snapshot '.*' is not 'completed'.")) {
//                    log.debug("receive exception, snapshot not completed from amazon. but I can continue");
//                    ase.printStackTrace();
//                    Thread.sleep(waitMills);
//                    continue;
//                } else if (ase.getStatusCode() == 400 && ase.getErrorCode().equals("IncorrectInstanceState")
//                        && ase.getMessage().matches("Instance with state 'pending' is not valid for this operation.")) {
//                    log.debug("receive exception, instance with status not valid for this operation. but I can continue");
//                    ase.printStackTrace();
//                    Thread.sleep(waitMills);
//                    continue;
//                } else if (ase.getStatusCode() == 400 && ase.getErrorCode().equals("DependencyViolation")
//                        && ase.getMessage().matches("resource sg-.* has a dependent object")) {
//                    log.debug("receive exception, security group has dependent object. but I can continue");
//                    ase.printStackTrace();
//                    Thread.sleep(waitMills);
//                    continue;
//                }
//                else {
//                    ase.printStackTrace();
//                    throw new CloudVersionException("something goes wrong. check it.");
//                }
//            } catch (Exception e) {
//                log.error("Illegal argument "
//                        + Arrays.toString(joinPoint.getArgs()) + " in "
//                        + joinPoint.getSignature().getName() + "()");
//                log.error("class name:"+e.getClass().getSimpleName());
//                log.error("message:"+e.getMessage());
//                e.printStackTrace();
//                throw e;
//            } finally {
//                i++;
//            }
//        }
//        throw new CloudVersionException("failed to retry 10 times.");
//    }
//}