package ims.crawler.cache;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextFactory {

	// ȫ��ͨ�õ�applicationContext
	public static ApplicationContext appContext;

	// ��̬�����һ���Գ�ʼ��
	static {
		appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}

}
