package ims.weka.classifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.stemmers.NullStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * ����Wekaר�ŵĽ����ļ�arff;step:02
 * 
 * @author superhy
 * 
 */
public class ProduceArffFile {

	public void produceArffFile(String analyzerContentPath, String dateRawPath,
			String dateFilteredPath) {
		try {

			DiyTextDirectoryLoader loader = new DiyTextDirectoryLoader();
			loader.setDirectory(new File(analyzerContentPath));
			Instances dateRaw = loader.getDataSet();
			{
				FileWriter fw = new FileWriter(dateRawPath);
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
				FileWriter fw = new FileWriter(dateFilteredPath);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(dataFiltered.toString());

				bw.close();
				fw.close();
			}

			// PS:�ֶ����ɵ�arff�ļ�������ڵ�һ�У��±�Ϊ0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
