package cn.iocoder.yudao.module.fx.utils;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author tll
 * @date 2024-12-30 10:28:12
 */
@Slf4j
public class ObjectUtils {

    /**
     * 获取属性为null的属性名称
     * @param source 对象
     * @return 数组
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            /*Field f=Reflections.getAccessibleField(source,pd.getName());
            if(f!=null){
                if(f.isAnnotationPresent(ForceCopyField.class)){
                    // emptyNames.add(pd.getName());
                    continue;
                }
            }*/
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || "".equals(srcValue)){
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    /**
     * 校验对象是否为基础类型
     * @param obj 对象
     * @return boolean
     */
    public static Boolean validFieldBaseFlag(Object obj){
        return obj instanceof String  || obj instanceof Integer
                || obj instanceof Double || obj instanceof Float
                || obj instanceof Long || obj instanceof Boolean
                || obj instanceof Date || obj instanceof BigDecimal;
    }

    public static Boolean equalsVal(Object object1, Object object2){
        return ObjectUtil.equals(object1, object2);
    }

    /**
     * 单个对象属性拷贝 copy性能最好的浅copy工具
     * @param source 源对象
     * @param clazz 目标对象Class
     * @param <T> 目标对象类型
     * @param <M> 源对象类型
     * @return 目标对象
     */
    public static <T, M> T copyProperties(M source, Class<T> clazz){
        return copyProperties(source, clazz, (String[]) null);
    }

    /**
     * 单个对象属性拷贝 copy性能最好的浅copy工具
     * @param source 源对象
     * @param Obj 目标对象Class
     * @param <T> 目标对象类型
     * @param <M> 源对象类型
     */
    public static <T, M> void copyProperties(M source, T Obj){
        copyProperties(source, Obj, (String[]) null);
    }

    /**
     * 单个对象属性拷贝
     * @param source 源对象
     * @param clazz 目标对象Class
     * @param ignoreProperties 不copy属性名称
     * @param <T> 目标对象类型
     * @param <M> 源对象类型
     * @return 目标对象
     */
    public static <T, M> T copyProperties(M source, Class<T> clazz, String... ignoreProperties){
        T t = null;
        try {
            t = clazz.newInstance();
            copyProperties(source, t, ignoreProperties);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return t;
    }

    /**
     * 单个对象属性拷贝 copy性能最好的浅copy工具
     * @param source 源对象
     * @param t 目标对象
     * @param ignoreProperties 不copy属性名称
     * @param <T> 目标对象类型
     * @param <M> 源对象类型
     */
    public static <T, M> void copyProperties(M source, T t, String... ignoreProperties){
        try {
            if(null != source){
                BeanUtils.copyProperties(source, t, ignoreProperties);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 判断对象是否为空
     * @param obj 对象
     * @return boolean
     */
    public static Boolean isBlankOrNull(Object obj){
        return null == obj || "".equals(obj);
    }

    /**
     * 获取默认属性值
     * @param val 属性值
     * @param def 属性值
     * @return Object
     */
    public static <T> T defaultValue(T val, T def){
        return isBlankOrNull(val) ? def : val;
    }
}