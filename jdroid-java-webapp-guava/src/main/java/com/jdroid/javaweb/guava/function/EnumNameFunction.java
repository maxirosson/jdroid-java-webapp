package com.jdroid.javaweb.guava.function;

import com.google.common.base.Function;

import java.io.Serializable;

/**
 * Transforms an enum in its name
 * 
 * @param <E> The Enum type
 * 
 */
public class EnumNameFunction<E extends Enum<E>> implements Function<E, String>, Serializable {
	
	/**
	 * @see com.google.common.base.Function#apply(Object)
	 */
	@Override
	public String apply(E from) {
		return from.name();
	}
}
