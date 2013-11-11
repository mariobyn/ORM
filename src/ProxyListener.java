 
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyListener implements MethodInterceptor {
    // the real object      
    private Object realObj;
    
    // constructor - the supplied parameter is an
    // object whose proxy we would like to create     
    public ProxyListener(Object obj) {
        this.realObj = obj;
    }
    
    // this method will be called each time      
    // when the object proxy calls any of its methods     
    public Object intercept(Object o,
                            Method method,
                            Object[] objects,
                            MethodProxy methodProxy) throws Throwable {
        // just print that we're about to execute the method         
        System.out.println("Before");         
        // measure the current time         
        long time1 = System.currentTimeMillis();
        // invoke the method on the real object with the given params         
        Object res = method.invoke(realObj, objects);
        // print that the method is finished         
        System.out.println("After");
        // print how long it took to execute the method on the proxified object
        System.out.println("Took: " + (System.currentTimeMillis() - time1) + " ms");
        // return the result         
        return res;
    }    
} 

