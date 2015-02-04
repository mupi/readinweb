package br.unicamp.iel.tool;

import java.util.Date;

import lombok.Data;
import lombok.Setter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.Strategy;
import br.unicamp.iel.tool.commons.CourseComponents;

@Data
public class AdminActivityBean {
	private Long activityId;
	private Long activityModule;
	private Integer position;
	private String image;
	private String title;
	private String text;
	private String preReading;
	private Integer etaRead;

	@Setter
	private ReadInWebAdminLogic logic;

	public String updateActivity() {
		if (activityDataSent()) {
			Activity activity = logic.getActivity(activityId);

			if (position != null) {
				activity.setPosition(position);
			}
			if (title != null) {
				activity.setTitle(title);
			}
			if (text != null) {
				activity.setText(text);
			}
			if (preReading != null) {
				activity.setPrereading(preReading);
			}
			if (etaRead != null) {
				activity.setEtaRead(etaRead);
			}
			activity.setModified(new Date());
			try {
				logic.saveActivity(activity);
			} catch (Exception e) {
				e.printStackTrace();
				return CourseComponents.UPDATE_FAIL;
			}
		} else {
			return CourseComponents.DATA_EMPTY;
		}
		return CourseComponents.ACTIVITY_UPDATED;
	}

	public String addActivity() {
		Activity activity = new Activity(logic.getModule(activityModule), 1,
				"", "", "", "", 0, new Date());

		if (position != null) {
			activity.setPosition(position);
		}
		if (title != null) {
			activity.setTitle(title);
		}
		activity.setEtaRead(0);
		activity.setPrereading("");
		activity.setText("");
		activity.setModified(new Date());
		try {
			logic.saveActivity(activity);
			return CourseComponents.ACTIVITY_ADDED;
		} catch (Exception e) {
			e.printStackTrace();
			return CourseComponents.SAVE_FAIL;
		}
	}

	public String deleteActivity() {
		Activity activity = logic.getActivity(activityId);
		try {
			logic.deleteActivity(activity);
		} catch (Exception e) {
			e.printStackTrace();
			return CourseComponents.DELETE_FAIL;
		}
		return CourseComponents.ACTIVITY_DELETED;
	}

	public boolean activityDataSent() {
		return position != null || image != null || title != null
				|| text != null || preReading != null || etaRead != null;
	}

	/**
	 * Strategy data handling
	 */

	private Long strategyId;
	private Integer strategyPosition;
	private Integer strategyType;
	private Long strategyActivity;
	private String strategyData;

	public String updateStrategy() {
		if (strategyDataSent()) {
			Strategy strategy = logic.getStrategy(strategyId);
			if (strategyPosition != null) {
				strategy.setPosition(strategyPosition);
			}
			if (strategyType != null) {
				strategy.setType(strategyType);
			}
			if (strategyData != null) {
				strategy.setBody(strategyData);
			}
			try {
				logic.saveStrategy(strategy);
				logic.updateActivity(logic.getActivity(strategy.getActivity()
						.getId()));
				return CourseComponents.UPDATED;
			} catch (Exception e) {
				e.printStackTrace();
				return CourseComponents.UPDATE_FAIL;
			}
		} else {
			return CourseComponents.DATA_EMPTY;
		}
	}

	public String saveStrategy() {
		Strategy strategy = new Strategy(1, 1,
				logic.getActivity(strategyActivity), "", new Date());

		if (strategyPosition != null) {
			strategy.setPosition(strategyPosition);
		}
		if (strategyType != null) {
			strategy.setType(strategyType);
		}
		if (strategyData != null) {
			strategy.setBody(strategyData);
		}
		try {
			logic.saveStrategy(strategy);
			logic.updateActivity(logic.getActivity(strategy.getActivity()
					.getId()));
			return CourseComponents.SAVED;
		} catch (Exception e) {
			e.printStackTrace();
			return CourseComponents.SAVE_FAIL;
		}
	}

	public String deleteStrategy() {
		Strategy strategy = logic.getStrategy(strategyId);
		try {
			logic.deleteEntity(strategy);
			logic.updateActivity(logic.getActivity(strategy.getActivity()
					.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return CourseComponents.DELETE_FAIL;
		}
		return CourseComponents.DELETED;
	}

	public boolean strategyDataSent() {
		return strategyPosition != null || strategyType != null
				|| strategyData != null;
	}

	/**
	 * Exercise data handling
	 */

	private Long exerciseId;
	private Integer exercisePosition;
	private Long exerciseActivity;
	private String exerciseTitle;
	private String exerciseDescription;
	private String exerciseAnswer;

	public String updateExercise() {
		if (exerciseDataSent()) {
			Exercise exercise = logic.getExercise(exerciseId);
			if (exercisePosition != null) {
				exercise.setPosition(exercisePosition);
			}
			if (exerciseTitle != null) {
				exercise.setTitle(exerciseTitle);
			}
			if (exerciseDescription != null) {
				exercise.setDescription(exerciseDescription);
			}
			if (exerciseAnswer != null) {
				exercise.setAnswer(exerciseAnswer);
			}
			exercise.setModified(new Date());

			try {
				logic.saveExercise(exercise);
				return CourseComponents.UPDATED;
			} catch (Exception e) {
				e.printStackTrace();
				return CourseComponents.UPDATE_FAIL;
			}

		}
		return CourseComponents.DATA_EMPTY;
	}

	public String saveExercise() {
		Exercise exercise = new Exercise(logic.getActivity(exerciseActivity));
		if (exercisePosition != null) {
			exercise.setPosition(exercisePosition);
		}
		if (exerciseTitle != null) {
			exercise.setTitle(exerciseTitle);
		}
		if (exerciseDescription != null) {
			exercise.setDescription(exerciseDescription);
		}
		if (exerciseAnswer != null) {
			exercise.setAnswer(exerciseAnswer);
		}
		exercise.setModified(new Date());
		try {
			logic.saveExercise(exercise);
			return CourseComponents.SAVED;
		} catch (Exception e) {
			e.printStackTrace();
			return CourseComponents.SAVE_FAIL;
		}
	}

	public String deleteExercise() {
		Exercise exercise = logic.getExercise(exerciseId);
		try {
			logic.updateActivity(logic.getActivity(exercise.getActivity()
					.getId()));
			logic.deleteEntity(exercise);
		} catch (Exception e) {
			e.printStackTrace();
			return CourseComponents.DELETE_FAIL;
		}
		return CourseComponents.DELETED;
	}

	public boolean exerciseDataSent() {
		return exercisePosition != null || exerciseTitle != null
				|| exerciseDescription != null || exerciseAnswer != null;
	}

	/**
	 * Grammar content handling
	 */

	private Long grammarModule;
	private String grammarData;

	public String updateGrammar() {
		if (grammarDataSent()) {
			Module module = logic.getModule(grammarModule);
			if (grammarData != null) {
				module.setGrammar(grammarData);
			}
			try {
				logic.saveModule(module);
				return CourseComponents.SAVED;
			} catch (Exception e) {
				e.printStackTrace();
				return CourseComponents.SAVE_FAIL;
			}
		}
		return CourseComponents.DATA_EMPTY;
	}

	public boolean grammarDataSent() {
		return grammarData != null;
	}

	/**
	 * Question data handling
	 */

	private Long questionId;
	private Long questionActivity;
	private Integer questionPosition;
	private String questionData;
	private String questionSuggested;

	public String updateQuestion() {
		if (questionDataSent()) {
			Question question = logic.getQuestion(questionId);
			if (questionPosition != null) {
				question.setPosition(questionPosition);
			}
			if (questionData != null) {
				question.setQuestion(questionData);
			}
			if (questionSuggested != null) {
				question.setSuggestedAnswer(questionSuggested);
			}

			try {
				logic.updateActivity(logic.getActivity(question.getActivity()
						.getId()));
				logic.saveQuestion(question);
				return CourseComponents.ACTIVITY_UPDATED;
			} catch (Exception e) {
				e.printStackTrace();
				return CourseComponents.UPDATE_FAIL;
			}
		}
		return CourseComponents.DATA_EMPTY;
	}

	public String saveQuestion() {
		Question question = new Question(logic.getActivity(questionActivity),
				1, "", "");
		if (questionPosition != null) {
			question.setPosition(questionPosition);
		}
		if (questionData != null) {
			question.setQuestion(questionData);
		}
		if (questionSuggested != null) {
			question.setSuggestedAnswer(questionSuggested);
		}

		try {
			logic.updateActivity(logic.getActivity(question.getActivity()
					.getId()));
			logic.saveQuestion(question);
			return CourseComponents.ACTIVITY_UPDATED;
		} catch (Exception e) {
			e.printStackTrace();
			return CourseComponents.SAVE_FAIL;
		}
	}

	public String deleteQuestion() {
		Question question = logic.getQuestion(questionId);
		try {
			logic.updateActivity(logic.getActivity(question.getActivity()
					.getId()));
			logic.deleteEntity(question);
		} catch (Exception e) {
			e.printStackTrace();
			return CourseComponents.DELETE_FAIL;
		}
		return CourseComponents.ACTIVITY_UPDATED;
	}

	public boolean questionDataSent() {
		return questionPosition != null || questionData != null
				|| questionSuggested != null;
	}
}
