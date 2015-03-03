package br.unicamp.iel.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import lombok.Setter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.tool.api.Session;

import br.unicamp.iel.logic.ReadInWebCommonLogic;
import br.unicamp.iel.logic.SakaiProxy;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.Strategy;
import br.unicamp.iel.model.sets.ActivitySets;
import br.unicamp.iel.model.sets.CourseSets;
import br.unicamp.iel.model.sets.ModuleSets;

public class PreloadDataImpl {

	private static Log log = LogFactory.getLog(PreloadDataImpl.class);

	private final List<String> prefixes = Arrays.asList("riw_");

	@Setter
	private SakaiProxy sakaiProxy;

	@Setter
	private ReadInWebCommonLogic common;

	@Setter
	private ReadInWebDao dao;

	@Setter
	ServerConfigurationService configuration;

	public void init() {
		log.info("Preloading Read in Web Data");
		if (!common.idiomCourseExists("english"))
			loadCSVData();

		boolean setup = configuration.getBoolean("readinweb.upgradecourse",
				false);
		if (setup) {
			upgradeEnglishCourse();
		}

		Session session = sakaiProxy.adminSessionStart();
		common.addReadInWebAdmin();
		sakaiProxy.adminSessionStop(session);
	}

	/**
	 * This is a temporary method. It is useful to upgrade the content we
	 * already have to new content format. Not to be used after first English
	 * course packaging. The readinweb.upgradecourse variable is not to be used
	 * anymore
	 */
	private void upgradeEnglishCourse() {
		// save audio file name on each activity
		Course c = common.getCourse(1L);
		CourseSets cs = new CourseSets(c);
		List<Module> modules = cs.getModules(dao);
		for (Module m : modules) {
			ModuleSets ms = new ModuleSets(m);
			List<Activity> activities = ms.getActivities(dao);
			for (Activity a : activities) {
				// Setup audio
				a.setAudiofile("m" + m.getPosition() + "a" + a.getPosition()
						+ ".mp3");

				// Setup images
				if (m.getPosition() == 1 || m.getPosition() == 3
						|| m.getPosition() == 4) {
					if (a.getPosition() == 1 || a.getPosition() == 3
							|| (m.getPosition() == 4 && a.getPosition() == 2)) {
						a.setImage(m.getPosition() + "_" + a.getPosition()
								+ ".gif");
					}
				}

				// setup exercises
				ActivitySets as = new ActivitySets(a);
				List<Exercise> exercises = as.getExercises(dao);
				for (Exercise e : exercises) {
					e.setExercise_path("m" + m.getPosition() + "a"
							+ a.getPosition() + "e" + e.getPosition());
					common.saveExercise(e);
				}
				common.saveActivity(a);
			}
		}
	}

	private void loadCSVData() {
		Reader in;
		Iterable<CSVRecord> records;

		try {
			log.info("Creating course");

			Course c = new Course();
			c.setTitle("Curso Read in Web");
			c.setIdiom("english");
			c.setDescription("Curso de Ingles auto-monitorado Read in Web");

			common.saveCourse(c);

			URI s = getClass().getResource("/riw_modulos.csv").toURI();
			in = new FileReader(new File(s));
			CSVFormat format = CSVFormat.newFormat(';').withQuote('"')
					.withEscape('\\').withHeader((String[]) null);

			records = format.parse(in);
			log.info("Creating modules");
			HashMap<Integer, Module> modules = new HashMap<Integer, Module>();
			for (CSVRecord record : records) {
				Module m = new Module();
				m.setCourse(c);
				m.setPosition(Integer.parseInt(record.get(0)));
				m.setGrammar(record.get(1));

				common.saveModule(m);
				modules.put(m.getPosition(), m);
			}
			in.close();

			s = getClass().getResource("/riw_palavrasfuncao.csv").toURI();
			in = new FileReader(new File(s));
			records = format.parse(in);
			log.info("Create functional words");
			for (CSVRecord record : records) {
				FunctionalWord fw = new FunctionalWord(c, record.get(0),
						record.get(1));
				common.saveFunctionalWord(fw);
			}
			in.close();

			s = getClass().getResource("/riw_atividades.csv").toURI();
			in = new FileReader(new File(s));
			records = format.parse(in);
			log.info("Create activities");
			HashMap<ImmutablePair<Integer, Integer>, Activity> activities = new HashMap<ImmutablePair<Integer, Integer>, Activity>();
			for (CSVRecord record : records) {
				Activity a = new Activity();
				Module m = modules.get(Integer.parseInt(record.get(0)));

				a.setModule(m);
				a.setPosition(Integer.parseInt(record.get(1)));
				a.setTitle(record.get(2));
				a.setText(record.get(3));
				a.setPrereading(record.get(4));
				a.setImage(record.get(5));
				a.setEtaRead(Integer.parseInt(record.get(6)));

				common.saveActivity(a);
				ImmutablePair<Integer, Integer> p;
				p = new ImmutablePair<Integer, Integer>(m.getPosition(),
						a.getPosition());
				activities.put(p, a);

				System.out.println("Saved activity: "
						+ a.getTitle()
						+ " under id "
						+ a.getId()
						+ " and added pair: "
						+ p.toString()
						+ " (activity object): "
						+ activities.get(new ImmutablePair<Integer, Integer>(m
								.getPosition(), a.getPosition())));
			}
			in.close();

			s = getClass().getResource("/riw_questoes.csv").toURI();
			in = new FileReader(new File(s));
			records = format.parse(in);
			log.info("Create questions");
			for (CSVRecord record : records) {
				Question q = new Question();

				ImmutablePair<Integer, Integer> p;
				p = new ImmutablePair<Integer, Integer>(Integer.parseInt(record
						.get(0)), Integer.parseInt(record.get(1)));

				Activity a = activities.get(p);

				q.setActivity(a);
				q.setPosition(Integer.parseInt(record.get(2)));
				q.setQuestion(record.get(3));
				q.setSuggestedAnswer(record.get(4));

				common.saveQuestion(q);

			}
			in.close();

			s = getClass().getResource("/riw_exercicios.csv").toURI();
			in = new FileReader(new File(s));
			records = format.parse(in);
			log.info("Create exercises");
			for (CSVRecord record : records) {
				Exercise e = new Exercise();
				ImmutablePair<Integer, Integer> p;
				p = new ImmutablePair<Integer, Integer>(Integer.parseInt(record
						.get(0)), Integer.parseInt(record.get(1)));
				Activity a = activities.get(p);

				e.setActivity(a);
				e.setPosition(Integer.parseInt(record.get(2)));
				e.setTitle(record.get(3));

				common.saveExercise(e);

			}
			in.close();

			s = getClass().getResource("/riw_glossario.csv").toURI();
			in = new FileReader(new File(s));
			records = format.parse(in);
			log.info("Create dictionary");
			for (CSVRecord record : records) {
				DictionaryWord dw = new DictionaryWord();
				ImmutablePair<Integer, Integer> p;
				p = new ImmutablePair<Integer, Integer>(Integer.parseInt(record
						.get(0)), Integer.parseInt(record.get(1)));
				Activity a = activities.get(p);

				dw.setActivity(a);
				dw.setWord(record.get(2));
				dw.setMeaning(record.get(3));

				common.saveDictionaryWord(dw);

			}
			in.close();

			s = getClass().getResource("/riw_estrategias.csv").toURI();
			in = new FileReader(new File(s));
			records = format.parse(in);
			log.info("Create strategies");
			for (CSVRecord record : records) {
				Strategy strategy = new Strategy();
				ImmutablePair<Integer, Integer> p;
				p = new ImmutablePair<Integer, Integer>(Integer.parseInt(record
						.get(0)), Integer.parseInt(record.get(1)));
				Activity a = activities.get(p);

				strategy.setActivity(a);
				strategy.setType(Integer.parseInt(record.get(2)));
				strategy.setPosition(Integer.parseInt(record.get(3)));
				strategy.setBody(record.get(4));

				common.saveStrategy(strategy);

			}
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	private boolean addCourses() {
		return false;
	}

}
