package ims.nlp.classifier.weka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.stemmers.NullStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * 制作Weka专门的解析文件arff;weka_step:02
 * 
 * @author superhy
 * 
 */
public class WekaProduceArffFile {

	public void produceArffFile(String analyzedContentPath, String dataRawPath,
			String dataFilteredPath) {
		try {

			WekaDiyTextDirectoryLoader loader = new WekaDiyTextDirectoryLoader();
			loader.setDirectory(new File(analyzedContentPath));
			Instances dateRaw = loader.getDataSet();
			{
				FileWriter fw = new FileWriter(dataRawPath);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(dateRaw.toString());

				bw.close();
				fw.close();
			}

			StringToWordVector filter = new StringToWordVector();
			filter.setStemmer(new NullStemmer());
			filter.setInputFormat(dateRaw);
			Instances dataFiltered = Filter.useFilter(dateRaw, filter);
			{
				FileWriter fw = new FileWriter(dataFilteredPath);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(dataFiltered.toString());

				bw.close();
				fw.close();
			}

			// PS:手动生成的arff文件类别是在第一行，下标为0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
