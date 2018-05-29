package com.jdroid.javaweb.firebase.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteBatch;
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
	
	private static final int ADD_BATCH_LIMIT = 500;
	private static final int DELETE_BATCH_SIZE = 100;
	
	protected abstract String getPath();
	
	protected abstract Class<T> getEntityClass();
	
	protected abstract String getProjectId();
	
	protected Firestore createFirestore() {
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(getProjectId()).build();
		return firestoreOptions.getService();
	}
	
	private CollectionReference createCollectionReference() {
		return createCollectionReference(createFirestore());
	}
	
	private CollectionReference createCollectionReference(Firestore firestore) {
		return firestore.collection(getPath());
	}
	
	@Override
	public void add(T item) {
		CollectionReference collectionReference = createCollectionReference();
		DocumentReference documentReference;
		if (item.getId() != null) {
			documentReference = collectionReference.document(item.getId());
		} else {
			documentReference = collectionReference.document();
			item.setId(documentReference.getId());
		}
		
		getWriteResult(documentReference.set(item));
		
		LOGGER.debug("[" + getPath() + "] Stored object in database: " + item);
	}
	
	@Override
	public void addAll(Collection<T> items) {
		Firestore firestore = createFirestore();
		List<T> pendingItems = Lists.newArrayList(items);
		while (!pendingItems.isEmpty()) {
			processBatch(firestore, pendingItems.subList(0, Math.min(pendingItems.size(), ADD_BATCH_LIMIT)));
			if (pendingItems.size() > ADD_BATCH_LIMIT) {
				pendingItems = pendingItems.subList(ADD_BATCH_LIMIT, pendingItems.size());
			} else {
				break;
			}
		}
		
		LOGGER.debug("[" + getPath() + "] Stored objects in database: " + items);
	}
	
	private void processBatch(Firestore firestore, Collection<T> items) {
		WriteBatch batch = firestore.batch();
		CollectionReference collectionReference = createCollectionReference(firestore);
		
		for (T item : items) {
			DocumentReference documentReference;
			if (item.getId() != null) {
				documentReference = collectionReference.document(item.getId());
			} else {
				documentReference = collectionReference.document();
				item.setId(documentReference.getId());
			}
			
			batch.set(documentReference, item);
		}
		
		try {
			batch.commit().get();
		} catch (InterruptedException | ExecutionException e) {
			throw new UnexpectedException(e);
		}
	}
	
	@Override
	public void update(T item) {
		if (item.getId() == null) {
			throw new UnexpectedException("Item with null id can not be updated");
		}
		
		CollectionReference collectionReference = createCollectionReference();
		DocumentReference documentReference = collectionReference.document(item.getId());
		
		getWriteResult(documentReference.set(item));
		
		LOGGER.debug("[" + getPath() + "] Updated object in database: " + item);
	}
	
	@Override
	public T get(String id) {
		DocumentSnapshot documentSnapshot = getDocumentSnapshot(createCollectionReference().document(id).get());
		T item = null;
		if (documentSnapshot.exists()) {
			item = getItem(documentSnapshot);
			if (item.getId() == null) {
				item.setId(id);
			}
			LOGGER.debug("[" + getPath() + "] Retrieved object from database: [" + item + "]");
		} else {
			LOGGER.debug("[" + getPath() + "] Object not found on database with id [" + id + "]");
		}
		return item;
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
			if (getOrderByField() != null) {
				query = query.orderBy(getOrderByField(), getOrderByDirection());
			}
			if (getLimit() != null) {
				query = query.limit(getLimit());
			}
			for (DocumentSnapshot documentSnapshot : getQuerySnapshot(query.get()).getDocuments()) {
				T item = getItem(documentSnapshot);
				if (item.getId() == null) {
					item.setId(documentSnapshot.getId());
				}
				results.add(item);
			}
		}
		
		LOGGER.debug("[" + getPath() + "] Retrieved objects [" + results.size() + "] from database with field [" + fieldName + "], values [" + values + "]");
		
		return results;
	}
	
	protected String getOrderByField() {
		return null;
	}
	
	protected Query.Direction getOrderByDirection() {
		return Query.Direction.ASCENDING;
	}
	
	protected Integer getLimit() {
		return null;
	}
	
	@Override
	public List<T> getAll() {
		List<T> results = Lists.newArrayList();
		Query collectionReference = createCollectionReference();
		if (getOrderByField() != null) {
			collectionReference = collectionReference.orderBy(getOrderByField(), getOrderByDirection());
		}
		if (getLimit() != null) {
			collectionReference = collectionReference.limit(getLimit());
		}
		for (DocumentSnapshot documentSnapshot : getQuerySnapshot(collectionReference.get()).getDocuments()) {
			T item = getItem(documentSnapshot);
			if (item.getId() == null) {
				item.setId(documentSnapshot.getId());
			}
			results.add(item);		}
		LOGGER.debug("[" + getPath() + "] Retrieved all objects [" + results.size() + "]");
		return results;
	}
	
	@Override
	public List<T> getByIds(List<String> ids) {
		return getByField("id", ids.toArray());
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
	public T getUniqueInstance() {
		List<QueryDocumentSnapshot> documentSnapshots = getQuerySnapshot(createCollectionReference().limit(1).get()).getDocuments();
		if (!documentSnapshots.isEmpty()) {
			DocumentSnapshot documentSnapshot = documentSnapshots.get(0);
			T item = getItem(documentSnapshot);
			if (item.getId() == null) {
				item.setId(documentSnapshot.getId());
			}
			return item;
		}
		return null;
	}
	
	private T getItem(DocumentSnapshot documentSnapshot) {
		T item = documentSnapshot.toObject(getEntityClass());
		onItemLoaded(item);
		return item;
		
	}
	
	protected void onItemLoaded(T item) {
		// Do nothing
	}
	
	@Override
	public void remove(T item) {
		remove(item.getId());
	}
	
	@Override
	public void removeAll() {
		deleteCollection(createCollectionReference(), getDeleteBatchSize());
		LOGGER.debug("[" + getPath() + "] Deleted all objects in database");
	}
	
	/**
	 *  Delete a collection in batches to avoid out-of-memory errors.
	 *  Batch size may be tuned based on document size (atmost 1MB) and application requirements.
	 */
	private void deleteCollection(CollectionReference collectionReference, int batchSize) {
		// retrieve a small batch of documents to avoid out-of-memory errors
		ApiFuture<QuerySnapshot> future = collectionReference.limit(batchSize).get();
		int deleted = 0;
		for (DocumentSnapshot document : getQuerySnapshot(future).getDocuments()) {
			document.getReference().delete();
			++deleted;
		}
		if (deleted >= batchSize) {
			// retrieve and delete another batch
			deleteCollection(collectionReference, batchSize);
		}
	}
	
	protected int getDeleteBatchSize() {
		return DELETE_BATCH_SIZE;
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
		
		LOGGER.debug("[" + getPath() + "] Deleted object in database with id: " + id);
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
