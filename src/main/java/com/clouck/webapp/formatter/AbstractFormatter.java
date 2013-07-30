package com.clouck.webapp.formatter;
//package com.fleeio.webapp.formatter;
//
//import java.text.ParseException;
//import java.util.Locale;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.format.Formatter;
//
//import com.fleeio.model.AbstractEntityBean;
//import com.fleeio.service.BaseService;
//
//public abstract class AbstractFormatter<T extends AbstractEntityBean> implements Formatter<T> {
//
//    @Autowired
//    @Qualifier("baseServiceImpl")
//    protected BaseService baseService;
//    
//    private Class<T> entityClass;
//    
//    @Override
//    public String print(T t, Locale locale) {
//        return t.getId().toString();
//    }
//
//    @Override
//    public T parse(String id, Locale locale) throws ParseException {
//        return baseService.find(entityClass, Long.valueOf(id));
//    }
//}
//
