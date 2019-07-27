package com.jdroid.javaweb.search;

import com.jdroid.java.collections.CollectionUtils;
import com.jdroid.java.json.JsonMap;
import com.jdroid.java.marshaller.Marshaller;
import com.jdroid.java.marshaller.MarshallerMode;
import com.jdroid.java.search.PagedResult;

import java.util.Map;

public class PagedResultJsonMarshaller implements Marshaller<PagedResult<?>, JsonMap> {
	
	private static final String LAST_PAGE = "lastPage";
	private static final String LIST = "list";
	
	@Override
	public JsonMap marshall(PagedResult<?> pagedResult, MarshallerMode mode, Map<String, String> extras) {
		JsonMap map = new JsonMap(mode, extras);
		if (CollectionUtils.INSTANCE.isNotEmpty(pagedResult.getResults())) {
			map.put(LAST_PAGE, pagedResult.isLastPage());
			map.put(LIST, pagedResult.getResults());
		}
		return map;
	}
	
}
