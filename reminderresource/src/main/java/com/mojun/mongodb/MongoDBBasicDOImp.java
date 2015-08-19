package com.mojun.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBBasicDOImp {
	private static MongoClient MONGODB_CLIENT = null;
	
	private final MongoDatabase MG_DB;
	private final MongoCollection<Document> MG_COLLECTION;
	
	// ini
	public MongoDBBasicDOImp(String dbName, String collectionName, String url, int port, ObjectMapper objMapper) throws Exception {
		if(dbName == null || collectionName == null || url == null) {
			throw new Exception("dbName and url should not be null");
		}
		if(MONGODB_CLIENT == null) {
			MONGODB_CLIENT = new MongoClient(url, port);
		}
		MG_DB = MONGODB_CLIENT.getDatabase(dbName);
		MG_COLLECTION = MG_DB.getCollection(collectionName);
	} 
	
	// CURD
	// Create
	public void upsertWithJstring(List<String> records) {
		List<Document> documents = new ArrayList<>();
		for(String record : records){
			documents.add(Document.parse(record));
		}
		MG_COLLECTION.insertMany(documents);
	}
	// update
	public void updateWithConditions(String conditions, String record) {
		Document filterDocument = Document.parse(conditions);
		Document updateDocument = Document.parse(record);
		MG_COLLECTION.replaceOne(filterDocument, updateDocument);
	}
	// find
	public List<Document> fetchWithConditions(String conditions) {
		List<Document> documents = new ArrayList<>();
		Document filterDocument = Document.parse(conditions);
		FindIterable<Document> cursor = MG_COLLECTION.find(filterDocument);
		while(cursor.iterator().hasNext()) {
			documents.add(cursor.iterator().next());
		}
		return documents;
	}
	// delete
	public void deleteWithConditions(String conditions) {
		Document filterDocument = Document.parse(conditions);
		MG_COLLECTION.deleteMany(filterDocument);
	}

}
