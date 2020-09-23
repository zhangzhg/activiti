package com.fk.common.util;

public class ExceptionUtils {

    /**
     * 判断异常链中是否存在某种类型的异常
     * @param expected
     * @param exc
     * @return
     */
    public static boolean isCause(Class<? extends Throwable> expected, Throwable exc) {
        return expected.isInstance(exc) || (exc != null && isCause(expected, exc.getCause()));
    }

    /**
     * 获取期望的Exception
     * @param expected
     * @param exc
     * @return
     */
    public static Throwable getExpectedThrowable(Class<? extends Throwable> expected, Throwable exc){
        if(exc==null){
            return exc;
        }
        if(!expected.isInstance(exc)){
            exc  = getExpectedThrowable(expected,exc.getCause());
        }
        return exc;
    }
}
