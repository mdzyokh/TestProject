package com.vibeit.util;

import java.lang.reflect.ParameterizedType;

/**
 * @author Andrii Kovalov
 */
public class ReflectionUtil {
	public static Class getGenericParameterClass(Class actualClass, int parameterIndex) {
		return (Class) ((ParameterizedType) actualClass.getGenericSuperclass()).getActualTypeArguments()[parameterIndex];
	}
}
