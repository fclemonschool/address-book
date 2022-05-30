package com.example.addressbook.handler;

import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 로깅 처리를 위한 핸들러.
 */
@Aspect
@Component
@Slf4j
public class LoggingHandler {

  protected static final String LOG_METHOD =
      "===================== method name = {} =====================";
  protected static final String LOG_END =
      "============================================================";

  /**
   * com.example.addressbook.controller 이하 패키지의 모든 클래스 이하 모든 메서드 호출 전에 적용
   *
   * @param joinPoint 발생 지점
   */
  @Before("execution(* com.example.addressbook.controller.*.*(..))")
  public void beforeParameterLog(JoinPoint joinPoint) {
    log.info(LOG_METHOD, getMethod(joinPoint).getName());

    // 파라미터 받아오기
    Object[] args = joinPoint.getArgs();
    if (args.length <= 0) {
      log.info("no parameter");
    }
    for (Object arg : args) {
      if (arg != null) {
        log.info("parameter type = {}", arg.getClass().getSimpleName());
        log.info("parameter value = {}", arg);
      }
    }
    log.info(LOG_END);
  }

  /**
   * com.example.addressbook.controller 이하 패키지의 모든 클래스 이하 모든 메서드 리턴 후에 적용
   *
   * @param joinPoint 발생 지점
   * @param returnObj 메서드 리턴 값
   */
  @AfterReturning(value = "execution(* com.example.addressbook.controller.*.*(..))",
      returning = "returnObj")
  public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
    log.info(LOG_METHOD, getMethod(joinPoint).getName());
    log.info("return type = {}", returnObj.getClass().getSimpleName());
    log.info("return value = {}", returnObj);
    log.info(LOG_END);
  }

  /**
   * JoinPoint로 메서드 정보 가져오기.
   *
   * @param joinPoint 발생 지점
   * @return 메서드 정보
   */
  private Method getMethod(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    return signature.getMethod();
  }

  /**
   * com.example.addressbook.service 이하 패키지의 모든 클래스에서 오류 발생 시 적용.
   *
   * @param joinPoint 발생 지점
   * @param exception 오류 정보
   */
  @AfterThrowing(pointcut = "execution(* com.example.addressbook.service.*.*(..))",
      throwing = "exception")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
    log.info(LOG_METHOD, getMethod(joinPoint).getName());
    log.error("An exception has been thrown in {}{}", joinPoint.getSignature().getName(), "()");
    log.error("Message : {}", exception.getMessage());
    log.error("Cause : {}", Arrays.toString(exception.getStackTrace()));
    log.error(LOG_END);
  }
}
