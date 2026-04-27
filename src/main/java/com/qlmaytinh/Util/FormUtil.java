package com.qlmaytinh.Util;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class FormUtil {
private static final Logger logger = Logger.getLogger(FormUtil.class.getName());
	
	@SuppressWarnings("unchecked")
	public static <T> T toModel(Class<T> tClass, HttpServletRequest request) {
		try {
			T object = tClass.getDeclaredConstructor().newInstance();
			BeanUtils.populate(object, request.getParameterMap());
			return object;
		} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
			logger.severe(e.getMessage());
			return null;
		}
	}
}
