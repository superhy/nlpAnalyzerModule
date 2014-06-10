package ims.crawler.cache;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextFactory {

	// 全局通用的applicationContext
	public static ApplicationContext appContext;

	// 静态程序块一次性初始化
	static {
		appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}

}
