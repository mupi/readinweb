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
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.unicamp.iel.logic.ReadInWebCommonLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.Strategy;


public class PreloadDataImpl {

    private static Log log = LogFactory.getLog(PreloadDataImpl.class);

    private final List<String> prefixes = Arrays.asList("riw_");

    @Setter
    private ReadInWebCommonLogic common;

    @Setter
    private ReadInWebDao dao;

    public void init() {
        log.info("Preloading Read in Web Data");
        if(addReadInWebCourse("riw_")){
            log.info("Preloading succeed");
        } else {
            log.error("Preloading failed");
        }
        loadCSVData();
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
            CSVFormat format = CSVFormat
                    .newFormat(';')
                    .withQuote('"')
                    .withEscape('\\')
                    .withHeader((String[])null);

            records = format.parse(in);
            log.info("Creating modules");
            HashMap<Integer, Module> modules = new HashMap<Integer, Module>();
            for(CSVRecord record : records){
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
            for(CSVRecord record : records){
                FunctionalWord fw = new FunctionalWord(c, record.get(0),
                        record.get(1));
                common.saveFunctionalWord(fw);
            }
            in.close();

            s = getClass().getResource("/riw_atividades.csv").toURI();
            in = new FileReader(new File(s));
            records = format.parse(in);
            log.info("Create activities");
            HashMap<ImmutablePair<Integer, Integer>, Activity> activities =
                    new HashMap<ImmutablePair<Integer, Integer>, Activity>();
            for(CSVRecord record : records){
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

                System.out.println("Saved activity: " + a.getTitle()
                        + " under id " + a.getId() + " and added pair: "
                        + p.toString() + " (activity object): "
                        + activities.get(
                                new ImmutablePair<Integer, Integer>(
                                        m.getPosition(),
                                        a.getPosition()
                                        )
                                )
                        );
            }
            in.close();

            s = getClass().getResource("/riw_questoes.csv").toURI();
            in = new FileReader(new File(s));
            records = format.parse(in);
            log.info("Create questions");
            for(CSVRecord record : records){
                Question q = new Question();

                ImmutablePair<Integer, Integer> p;
                p = new ImmutablePair<Integer, Integer>(
                        Integer.parseInt(record.get(0)),
                        Integer.parseInt(record.get(1)
                                )
                        );

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
            for(CSVRecord record : records){
                Exercise e = new Exercise();
                ImmutablePair<Integer, Integer> p;
                p = new ImmutablePair<Integer, Integer>(
                        Integer.parseInt(record.get(0)),
                        Integer.parseInt(record.get(1)
                                )
                        );
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
            for(CSVRecord record : records){
                DictionaryWord dw = new DictionaryWord();
                ImmutablePair<Integer, Integer> p;
                p = new ImmutablePair<Integer, Integer>(
                        Integer.parseInt(record.get(0)),
                        Integer.parseInt(record.get(1)
                                )
                        );
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
            for(CSVRecord record : records){
                Strategy strategy = new Strategy();
                ImmutablePair<Integer, Integer> p;
                p = new ImmutablePair<Integer, Integer>(
                        Integer.parseInt(record.get(0)),
                        Integer.parseInt(record.get(1)
                                )
                        );
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
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private boolean addCourses(){
        return false;
    }

}
