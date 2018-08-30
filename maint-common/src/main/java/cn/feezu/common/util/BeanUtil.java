package cn.feezu.common.util;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {
	
	private BeanUtil(){
		
	}
	private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);
	/**
	 * 返回指定类集合的指定属性的集合
	 * @param object 要转换的集合类
	 * @param columnName 要转换的属性
	 * @param type 转换后的类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T,E>   List<E> convert(List<T> objectList,String columnName,Class<E> type){
		List<E> values=new ArrayList<>();
		if(objectList ==null || objectList.isEmpty()){
			return values;
		}
		for(Object s:objectList){
			 try {
			    Method getMethod = new PropertyDescriptor(columnName,
			    		s.getClass()).getReadMethod();
			    if(!getMethod.getReturnType().equals(type)){
			    	throw new ClassCastException("类型转换错误"+getMethod.getReturnType()+" to "+type);
			    }
	            E value =(E) getMethod.invoke(s);
	            values.add(value);
			} catch (IntrospectionException|InvocationTargetException|IllegalAccessException|ClassCastException e) {
				log.warn("反射转换错误，集合类型[List<"+s.getClass()+">],columnName["+columnName+"],type["+type.getName()+"]",e);
			 
			}
			 
		}
		return values;
	}
	/**
	 * 返回指定类集合的指定属性
	 * @param object 要转换的类
	 * @param columnName 要转换的属性
	 * @param type 转换后的类型
	 * @return
	 */
	public static <T,E> E convert(T objectList,String columnName,Class<E> type){
		List<T> objectLists =new ArrayList<>();
		objectLists.add(objectList);
		List<E> objs = convert(objectLists, columnName, type);
		if(objs==null ||objs.isEmpty()){
			return null;
		}
		 return objs.get(0);
	 
	}
	/**
	 * 返回指定类集合的指定属性的map
	 * @param objectList 要转换的集合类
	 * @param columnKey 要转换的属性KEY
	 * @param columnValue 要转换的属性VALUE
	 * @param mapKey 转换后的KEY类型
	 * @param mapValue 转换后的VALUE类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T,E,F>   Map<E,F> convert(List<T> objectList,String columnKey,String columnValue,Class<E> mapKey,Class<F> mapValue){
		Map<E,F> values=new LinkedMap();
		if(objectList ==null || objectList.isEmpty()){
			return values;
		}
		for(Object s:objectList){
			 try {
			    Method getkeyMethod = new PropertyDescriptor(columnKey,
			    		s.getClass()).getReadMethod();
			    E key =(E) getkeyMethod.invoke(s);
			    
			    if(!getkeyMethod.getReturnType().equals(mapKey)){
			    	throw new ClassCastException("类型转换错误"+getkeyMethod.getReturnType()+" to "+mapKey);
			    }    
	            Method getValueMethod = new PropertyDescriptor(columnValue,
	            		s.getClass()).getReadMethod();
	            if(!getValueMethod.getReturnType().equals(mapValue)){
			    	throw new ClassCastException("类型转换错误"+getkeyMethod.getReturnType()+" to "+mapValue);
			    } 
	            F value =(F) getValueMethod.invoke(s);
	            values.put(key, value);
			} catch (IntrospectionException|InvocationTargetException|IllegalAccessException|ClassCastException e) {
				log.warn("反射转换错误，集合类型["+s.getClass()+"],columnKey["+columnKey+"],columnValue["+columnValue+"],mapKey["+mapKey.getName()+"],mapValue["+mapValue.getName()+"]",e);
			}
			 
		}
		return values;
	}

}
