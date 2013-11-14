package ORM;
import Annotations.GetValue;
import Annotations.Id;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.annotation.Annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

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
        Set res = new HashSet();
        if(method.getName().contains("get")) {       //verific daca s-a apelat get
            Annotation annForGet = method.getAnnotation(GetValue.class);
            if(annForGet != null && ((GetValue) annForGet).foreign()){
                try{
                    res = GetForeign.getForeignValues(realObj,objects);             //chem metoda de aducere a valorilor din baza de date
                }catch(Exception e){
                    throw new RuntimeException("Can't get foreign values");
                }
            }
        }
        return res;
    }

} 

