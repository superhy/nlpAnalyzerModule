package ims.analyze.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * MongoDB���ݿ����������(����Ŀ���У�ֻ�ǽ���һ��)
 * 
 * @author superhy
 * 
 */
public class MongoDbBean {

	// ����MongoDB���ݿ����Ӷ���
	public static Mongo mongoConnection = null;
	// ����MongoDB�������ݿ�Ķ���
	public static DB dbConnection = null;

	// ����ģʽ����̬�ڲ���
	private static class MongoDbBeanHolder {
		private static final MongoDbBean MONGO_DB_BEAN = new MongoDbBean();
	}

	private MongoDbBean() {
		try {
			if (mongoConnection == null || dbConnection == null) {
				Map<String, Object> mongoDataSourceMap = MongoDateSource
						.getMongoDateSourceConfig();
				String connectHost = (String) mongoDataSourceMap
						.get("connectHost");
				String dbName = (String) mongoDataSourceMap.get("dbName");

				this.mongoConnection = new Mongo(connectHost);
				this.dbConnection = mongoConnection.getDB(dbName);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ������ʵ��
	public static final MongoDbBean getMongoDbBean() {
		return MongoDbBeanHolder.MONGO_DB_BEAN;
	}

	// MongoDB�Ļ������ݲ���

	/**
	 * �����µ����ݿ⼯��
	 */
	public boolean createNewCollection(String collectionName) {
		try {
			DBObject dbCollection = new BasicDBObject();
			this.dbConnection.createCollection(collectionName, dbCollection);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ��ѯ���ݿ⼯������
	 * 
	 * code by:dd
	 */
	public Set<String> getCollectionNames() {
		Set<String> colls = this.dbConnection.getCollectionNames();
		return colls;
	}

	/**
	 * ��ָ���������������
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	public boolean insert(DBObject dbs, String collectionName) {

		try {
			// ��ö�Ӧ����
			DBCollection collection = this.dbConnection
					.getCollection(collectionName);
			// ��������
			collection.insert(dbs);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ��ָ�������������������
	 * 
	 * @param dbses
	 * @param collectionName
	 * @return
	 */
	public boolean batchInsert(List<DBObject> dbses, String collectionName) {
		try {
			// �õ���Ӧ����
			DBCollection collection = this.dbConnection
					.getCollection(collectionName);
			// ������������
			collection.insert(dbses);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ɾ��ָ�������е��ض�����
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	public int deleteByDbs(DBObject dbs, String collectionName) {

		// ���Ҫ���в����ļ���
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// ִ��ɾ������������Ӱ�������
		int effectNum = collection.remove(dbs).getN();

		return effectNum;
	}

	/**
	 * ��ѯָ�������еļ�¼����
	 * 
	 * @param collectionName
	 * @return
	 */
	public int countInColl(String collectionName) {

		// ���Ҫ���в����ļ���
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// ��ѯ�������еļ�¼��Ŀ
		int collRecCountNum = collection.find().count();

		return collRecCountNum;
	}

	/**
	 * ��ѯָ�������з��������ļ�¼��Ŀ
	 * 
	 * @param ref
	 * @param keys
	 * @param collectionName
	 * @return
	 */
	public int countByKeyAndRefInColl(DBObject ref, DBObject keys,
			String collectionName) {
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// ��ѯ�������з��������ļ�¼��Ŀ
		int collRecCountNum = collection.find(ref, keys).count();

		return collRecCountNum;
	}

	/**
	 * ͨ��find����������������������һ���ǲ�ѯ���������ڶ����ǲ�ѯ���ֶ����ƣ��������ǲ�ѯ�ļ�����������ֵ��DBObject��ӳ�伯��
	 * exp:������ҳ�Ĳ�ѯ
	 * 
	 * @return
	 */
	public List<DBObject> findByKeyAndRef(DBObject ref, DBObject keys,
			String collectionName) {

		// ׼��Ҫ���ص�ӳ�伯��
		List<DBObject> dbObjects = new ArrayList<DBObject>();

		// ���Ҫ���в����ļ���
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// ������ѯ���α�
		DBCursor cursor = collection.find(ref, keys);

		// �����α��ò�ѯ�������
		while (cursor.hasNext()) {
			dbObjects.add(cursor.next());
		}

		return dbObjects;
	}

	/**
	 * ͨ��find����������������������һ���ǲ�ѯ���������ڶ����ǲ�ѯ���ֶ����ƣ��������ǲ�ѯ���ֶο�ʼ�±꣬
	 * ���ĸ��ǲ�ѯ���ֶ�����������ǲ�ѯ�ļ�����������ֵ��DBObject��ӳ�伯�� exp:����ҳ�Ĳ�ѯ
	 * 
	 * @return
	 */
	public List<DBObject> findByKeyAndRefWithPage(DBObject ref, DBObject keys,
			int skip, int limit, String collectionName) {

		// ׼��Ҫ���ص�ӳ�伯��
		List<DBObject> dbObjects = new ArrayList<DBObject>();

		// ���Ҫ���в����ļ���
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		DBCursor cursor = collection.find(ref, keys).skip(skip).limit(limit);

		// �����α��ò�ѯ�������
		while (cursor.hasNext()) {
			dbObjects.add(cursor.next());
		}

		return dbObjects;
	}
}
