package com.mojun.mongodb;

import com.mongodb.MongoClient;

/**
 * This is used as basic mongo_client creator
 * @author mozhu
 *
 */
public class MongoDBBasic {
	protected static MongoClient MG_CLIENT = null;
	
	public static void MONGOCLIENTINITIALIZER() {
		if(MG_CLIENT == null) {
			MG_CLIENT = new MongoClient("127.0.0.1", 27017);
		}
	}
	
	public void CLOSEMONGOCLIENT() {
		if(MG_CLIENT != null) {
			MG_CLIENT.close();
		}
	}
}
