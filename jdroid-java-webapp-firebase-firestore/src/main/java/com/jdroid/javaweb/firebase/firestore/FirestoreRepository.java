package com.jdroid.javaweb.firebase.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.domain.Entity;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.repository.Repository;
import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class FirestoreRepository<T extends Entity> implements Repository<T> {
	
	private static final Logger LOGGER = LoggerUtils.getLogger(FirestoreRepository.class);
	
	protected abstract String getPath();
	
	protected abstract Class<T> getEntityClass();
	
	protected abstract String getProjectId();
	
	protected Firestore createFirestore() {
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(getProjectId()).build();
		return firestoreOptions.getService();
	}
	
	private CollectionReference createCollectionReference() {
		return createFirestore().collection(getPath());
	}
	
	@Override
	public T get(String id) {
		DocumentSnapshot documentSnapshot = getDocumentSnapshot(createCollectionReference().document(id).get());
		T item = null;
		if (documentSnapshot.exists()) {
			item = documentSnapshot.toObject(getEntityClass());
			if (item.getId() == null) {
				item.setId(id);
			}
			LOGGER.info("Retrieved object from database with path [ " + getPath() + "]. [ " + item + " ]");
		} else {
			LOGGER.info("Object not found on database with path [ " + getPath() + " ] and id [ " + id + " ]");
		}
		return item;
	}
	
	@Override
	public void add(T item) {
		CollectionReference collectionReference = createCollectionReference();
		DocumentReference documentReference;
		if (item.getId() != null) {
			documentReference = collectionReference.document(item.getId());
		} else {
			documentReference = collectionReference.document();
		}
		
		item.setId(null);
		getWriteResult(documentReference.set(item));
		item.setId(documentReference.getId());
		
		LOGGER.info("Stored object in database: " + item);
	}
	
	@Override
	public void addAll(Collection<T> items) {
		// TODO See https://firebase.google.com/docs/firestore/manage-data/update-data#batch_multiple_write_operations
		for (T each : items) {
			add(each);
		}
	}
	
	@Override
	public void update(T item) {
		if (item.getId() == null) {
			throw new UnexpectedException("Item with null id can not be updated");
		}
		add(item);
	}
	
	@Override
	public List<T> getByField(String fieldName, Object... values) {
		if (values == null) {
			throw new UnexpectedException("Null values not supported");
		}
		
		List<T> results = Lists.newArrayList();
		CollectionReference collectionReference = createCollectionReference();
		for (Object value : values) {
			Query query = collectionReference.whereEqualTo(fieldName, value);
			for (DocumentSnapshot documentSnapshot : getQuerySnapshot(query.get()).getDocuments()) {
				T item = documentSnapshot.toObject(getEntityClass());
				if (item.getId() == null) {
					item.setId(documentSnapshot.getId());
				}
				results.add(item);
			}
		}
		
		LOGGER.info("Retrieved objects [" + results.size() + "] from database of path: " + getPath() + " field: " + fieldName);
		return results;
	}
	
	@Override
	public List<T> getAll() {
		List<T> results = Lists.newArrayList();
		for (DocumentSnapshot documentSnapshot : getQuerySnapshot(createCollectionReference().get()).getDocuments()) {
			T item = documentSnapshot.toObject(getEntityClass());
			if (item.getId() == null) {
				item.setId(documentSnapshot.getId());
			}
			results.add(item);		}
		LOGGER.info("Retrieved all objects [" + results.size() + "] from path: " + getPath());
		return results;
	}
	
	@Override
	public List<T> getByIds(List<String> ids) {
		List<T> results = Lists.newArrayList();
		for (String id : ids) {
			T item = get(id);
			if (item != null) {
				results.add(item);
			}
		}
		LOGGER.info("Retrieved all objects [" + results.size() + "] from path: " + getPath() + " and ids: " + ids);
		return null;
	}
	
	@Override
	public T getItemByField(String fieldName, Object... values) {
		List<T> items = getByField(fieldName, values);
		if (!items.isEmpty()) {
			return items.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public void remove(T item) {
		remove(item.getId());
	}
	
	@Override
	public void removeAll() {
		deleteCollection(createCollectionReference(), 100);
		LOGGER.trace("Deleted all objects in database");
	}
	
	/**
	 *  Delete a collection in batches to avoid out-of-memory errors.
	 *  Batch size may be tuned based on document size (atmost 1MB) and application requirements.
	 */
	private void deleteCollection(CollectionReference collectionReference, int batchSize) {
		// retrieve a small batch of documents to avoid out-of-memory errors
		ApiFuture<QuerySnapshot> future = collectionReference.limit(batchSize).get();
		int deleted = 0;
		// future.get() blocks on document retrieval
		List<DocumentSnapshot> documents = getQuerySnapshot(future).getDocuments();
		for (DocumentSnapshot document : documents) {
			document.getReference().delete();
			++deleted;
		}
		if (deleted >= batchSize) {
			// retrieve and delete another batch
			deleteCollection(collectionReference, batchSize);
		}
	}
	
	@Override
	public void removeAll(Collection<T> items) {
		for (T item : items) {
			remove(item);
		}
	}
	
	@Override
	public void remove(String id) {
		if (id == null) {
			throw new UnexpectedException("Item with null id can not be removed");
		}
		
		getWriteResult(createCollectionReference().document(id).delete());
		
		LOGGER.trace("Deleted object in database: with id: " + id);
	}
	
	@Override
	public Boolean isEmpty() {
		return getQuerySnapshot(createCollectionReference().limit(1).get()).getDocuments().isEmpty();
	}
	
	@Override
	public Long getSize() {
		return (long)getAll().size();
	}
	
	@Override
	public void replaceAll(Collection<T> items) {
		removeAll();
		addAll(items);
	}
	
	@Override
	public T getUniqueInstance() {
		List<DocumentSnapshot> documentSnapshots = getQuerySnapshot(createCollectionReference().limit(1).get()).getDocuments();
		if (!documentSnapshots.isEmpty()) {
			DocumentSnapshot documentSnapshot = documentSnapshots.get(0);
			T item = documentSnapshot.toObject(getEntityClass());
			if (item.getId() == null) {
				item.setId(documentSnapshot.getId());
			}
			return item;
		}
		return null;
	}
	
	
	private QuerySnapshot getQuerySnapshot(ApiFuture<QuerySnapshot> futureResult) {
		try {
			return futureResult.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new UnexpectedException(e);
		}
	}
	
	private DocumentSnapshot getDocumentSnapshot(ApiFuture<DocumentSnapshot> futureResult) {
		try {
			return futureResult.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new UnexpectedException(e);
		}
	}
	
	private WriteResult getWriteResult(ApiFuture<WriteResult> futureResult) {
		try {
			return futureResult.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new UnexpectedException(e);
		}
	}
}
