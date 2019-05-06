package net.zjcclims.web.aop;

import net.zjcclims.domain.LabCenter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

public class SystemLogAspect
{
  private LabCenter c;

  @Pointcut("@annotation(SystemServiceLog)")
  public void serviceAspect()
  {
  }

  public static String getServiceMthodDescription(JoinPoint joinPoint)
  {
    String description = "";
    try {
      String targetName = joinPoint.getTarget().getClass().getName();
      String methodName = joinPoint.getSignature().getName();
      Object[] arguments = joinPoint.getArgs();
      Class targetClass = Class.forName(targetName);
      Method[] methods = targetClass.getMethods();
      for (Method method : methods)
        if (method.getName().equals(methodName)) {
          Class[] clazzs = method.getParameterTypes();
          if (clazzs.length == arguments.length) {
            description = ((SystemServiceLog)method.getAnnotation(SystemServiceLog.class)).value();
            break;
          }
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return description;
  }
}