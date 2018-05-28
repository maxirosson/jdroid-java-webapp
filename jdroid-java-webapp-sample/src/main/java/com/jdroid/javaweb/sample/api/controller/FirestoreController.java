package com.jdroid.javaweb.sample.api.controller;

import com.google.cloud.firestore.GeoPoint;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;
import com.jdroid.java.date.DateUtils;
import com.jdroid.java.http.MimeType;
import com.jdroid.java.utils.RandomUtils;
import com.jdroid.javaweb.api.AbstractController;
import com.jdroid.javaweb.sample.firebase.SampleEntity;
import com.jdroid.javaweb.sample.firebase.SampleFirestoreRepository;
import com.jdroid.javaweb.sample.firebase.SampleInnerEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/firestore")
public class FirestoreController extends AbstractController {

	private SampleFirestoreRepository repository = new SampleFirestoreRepository();

	private static String lastId;
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String getAll() {
		return autoMarshall(repository.getAll());
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String get(@RequestParam String id) {
		return autoMarshall(repository.get(id));
	}
	
	@RequestMapping(value = "/getByField", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String getByField(@RequestParam String field, @RequestParam String value) {
		return autoMarshall(repository.getByField(field, value));
	}
	
	@RequestMapping(value = "/getByFieldMultipleValues", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String getByField(@RequestParam String field, @RequestParam String value1, @RequestParam String value2) {
		return autoMarshall(repository.getByField(field, value1, value2));
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String add(@RequestParam(required = false, defaultValue = "false") Boolean autoId, @RequestParam(required = false, defaultValue = "false") Boolean withSubcollection) {
		SampleEntity entity = new SampleEntity();
		if (!autoId) {
			entity.setId(RandomUtils.getLong().toString());
		}
		entity.setStringField(RandomUtils.getLong().toString());
		entity.setLongField(RandomUtils.getLong());
		entity.setFloatField(1.12345F);
		entity.setTimestamp(DateUtils.now());
		Map<String, String> stringMap = Maps.newHashMap();
		stringMap.put("key1", "value1");
		stringMap.put("key2", "value2");
		entity.setStringMap(stringMap);
		Map<String, Object> objectMap = Maps.newHashMap();
		objectMap.put("key1", "value1");
		objectMap.put("key2", 2);
		objectMap.put("key3", new GeoPoint(-12.323434D, 34.34534543D));
		entity.setObjectMap(objectMap);
		entity.setGeoPoint(new GeoPoint(-12.323434D, 34.34534543D));
		entity.setStringArray(Lists.newArrayList("a", "b"));
		if (withSubcollection) {
			entity.setSubCollection(Lists.newArrayList(new SampleEntity(), new SampleEntity()));
		}
		
		SampleInnerEntity sampleInnerEntity = new SampleInnerEntity();
		sampleInnerEntity.setStringField(RandomUtils.getLong().toString());
		sampleInnerEntity.setLongField(RandomUtils.getLong());
		
		entity.setComposite(sampleInnerEntity);
		
		repository.add(entity);
		lastId = entity.getId();
		
		return autoMarshall(entity);
	}
	
	@RequestMapping(value = "/addAll", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String addAll(@RequestParam(required = false, defaultValue = "false") Boolean autoId) {
		
		List<SampleEntity> items = Lists.newArrayList();
		SampleEntity entity = new SampleEntity();
		if (!autoId) {
			entity.setId(RandomUtils.getLong().toString());
		}
		entity.setStringField(RandomUtils.getLong().toString());
		items.add(entity);
		entity = new SampleEntity();
		if (!autoId) {
			entity.setId(RandomUtils.getLong().toString());
		}
		entity.setStringField(RandomUtils.getLong().toString());
		items.add(entity);
		
		repository.addAll(items);
		
		return autoMarshall(items);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String update() {
		SampleEntity entity = new SampleEntity();
		entity.setId(lastId);
		entity.setStringField(RandomUtils.getLong().toString());
		repository.update(entity);
		return autoMarshall(entity);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public void remove(@RequestParam String id) {
		repository.remove(id);
	}

	@RequestMapping(value = "/removeAll", method = RequestMethod.GET)
	public void removeAll() {
		repository.removeAll();
		lastId = null;
	}
}