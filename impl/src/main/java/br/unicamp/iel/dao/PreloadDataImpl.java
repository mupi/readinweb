package br.unicamp.iel.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PreloadDataImpl {

	private static Log log = LogFactory.getLog(PreloadDataImpl.class);

	private final List<String> prefixes = Arrays.asList("riw_");

	private ReadInWebDao dao;
	public void setDao(ReadInWebDao dao) {
		this.dao = dao;
	}

	public void init() {
		log.info("Preloading Read in Web Data");
		if(addReadInWebCourse("riw_")){
			log.info("Preloading succeed");
		} else {
			log.error("Preloading failed");
		}
	}

	/**
	 * This method is exclusive to Read in Web English Course.
	 * Another method, based on prefix list is avaiable
	 * 
	 * @see addCourses
	 * 
	 * @return
	 */
	private boolean addReadInWebCourse(String prefix){
		String ext = ".csv";
		// Get data from CSV files
		try {
			URI s = getClass().getResource("/riw_atividades.csv").toURI();
			File f = new File(s);
			Reader in = new FileReader(f);
			CSVFormat format = CSVFormat
					.newFormat(';')
					.withQuote('"')
					.withHeader((String[])null);
			Iterable<CSVRecord> records = format.parse(in);
			log.info("Olha os modules galera fica loka");
			for(CSVRecord record : records){
				log.info(record.get(0));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Add Course to database
		// For Course, add FunctionalWords
		// 		add Modules
		// 		For each module, set Grammar
		// 			Add Activities
		// 			For each Activity set strategies and text
		// 				add Questions
		// 				add DictionaryWords 
		// 				add Exercises
		return false;
	}

	private boolean addCourses(){
		return false;
	}

}
