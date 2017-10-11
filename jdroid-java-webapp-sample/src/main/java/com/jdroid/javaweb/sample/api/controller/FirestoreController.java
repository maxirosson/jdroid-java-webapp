package com.jdroid.javaweb.sample.api.controller;

import com.jdroid.java.http.MimeType;
import com.jdroid.java.utils.LoggerUtils;
import com.jdroid.java.utils.RandomUtils;
import com.jdroid.javaweb.api.AbstractController;
import com.jdroid.javaweb.sample.firebase.SampleEntity;
import com.jdroid.javaweb.sample.firebase.SampleFirestoreRepository;
import com.jdroid.javaweb.sample.firebase.SampleInnerEntity;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/firestore")
public class FirestoreController extends AbstractController {

	private static final Logger LOGGER = LoggerUtils.getLogger(FirestoreController.class);

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
	public String add() {
		SampleEntity entity = new SampleEntity();
		lastId = RandomUtils.getLong().toString();
		entity.setId(lastId);
		entity.setStringField(RandomUtils.getLong().toString());
		entity.setLongField(RandomUtils.getLong());
		
		SampleInnerEntity sampleInnerEntity = new SampleInnerEntity();
		sampleInnerEntity.setStringField(RandomUtils.getLong().toString());
		sampleInnerEntity.setLongField(RandomUtils.getLong());
		
		entity.setComposite(sampleInnerEntity);
		
		repository.add(entity);
		return autoMarshall(entity);
	}
	
	@RequestMapping(value = "/addWithoutId", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	public String addWithoutId() {
		SampleEntity entity = new SampleEntity();
		entity.setStringField(RandomUtils.getLong().toString());
		entity.setLongField(RandomUtils.getLong());
		
		SampleInnerEntity sampleInnerEntity = new SampleInnerEntity();
		sampleInnerEntity.setStringField(RandomUtils.getLong().toString());
		sampleInnerEntity.setLongField(RandomUtils.getLong());
		
		entity.setComposite(sampleInnerEntity);
		
		repository.add(entity);
		lastId = entity.getId();
		return autoMarshall(entity);
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
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