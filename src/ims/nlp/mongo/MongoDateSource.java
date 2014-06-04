package ims.nlp.mongo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 这一部分内容总项目里有，只是借用一下
 * 
 * @author superhy
 * 
 */
public class MongoDateSource {
	public static SAXReader xmlReader = new SAXReader();

	public static Document docXml;
	public static Element rootElement;
	public static String xmlResourcePath;

	public static void initXmlInfo() {
		try {
			docXml = xmlReader.read(new File("./src/mongo-config.xml"));
			rootElement = docXml.getRootElement();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<String, Object> getMongoDateSourceConfig() {
		initXmlInfo();

		String connectionHostXpath = "//property[@name='connectionHost']";
		String dbNameXpath = "//property[@name='dbName']";

		Element eleConnectHost = (Element) docXml.selectNodes(
				connectionHostXpath).get(0);
		Element eleDbName = (Element) docXml.selectNodes(dbNameXpath).get(0);

		String connectHost = eleConnectHost.attributeValue("value");
		String dbName = eleDbName.attributeValue("value");

		Map<String, Object> mongoDataSourceMap = new HashMap<String, Object>();
		mongoDataSourceMap.put("connectHost", connectHost);
		mongoDataSourceMap.put("dbName", dbName);

		return mongoDataSourceMap;
	}
}
