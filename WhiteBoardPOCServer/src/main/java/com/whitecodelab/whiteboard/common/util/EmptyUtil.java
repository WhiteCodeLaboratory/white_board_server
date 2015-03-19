package com.whitecodelab.whiteboard.common.util;

import java.util.Collection;
import java.util.Map;

public class EmptyUtil {

	public static <T> boolean isNotCollectionEmpty(Collection<T> targetCollection){
		return !isCollectionEmpty(targetCollection);
	}
	public static <T> boolean isCollectionEmpty(Collection<T> targetCollection){
		return targetCollection == null || targetCollection.isEmpty() ? true : false;
	}
	
	public static boolean isNotMapEmpty(Map<? ,?> targetMap){
		return !isMapEmpty(targetMap);
	}
	public static boolean isMapEmpty(Map<? ,?> targetMap){
		return targetMap == null || targetMap.isEmpty() ? true : false;
	}
	
	public static boolean isNotStringEmpty(String targetString) {
		return !isStringEmpty(targetString);
	}
	public static boolean isStringEmpty(String targetString){
		return targetString == null || targetString.isEmpty() ? true : false;
	}
	
}