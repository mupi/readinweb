package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Strategy;
import br.unicamp.iel.model.types.StrategyType;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */
public class AdminEstrategiasProducer implements ViewComponentProducer, ViewParamsReporter, NavigationCaseReporter {

    private static Log logger = LogFactory.getLog(AdminEstrategiasProducer.class);

    @Setter
    private ReadInWebAdminLogic logic;

    public static final String VIEW_ID = "admin_estrategias";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {

        Activity activity;
        Module module;
        Course course;

        CourseViewParameters parameters = (CourseViewParameters) viewparams;

        if(parameters.newdata){ // Nothing setted
            activity = logic.getLastActivityAdded();
        } else if(parameters.dataupdated){ // Nothing setted
            activity = logic.getLastUpdatedActivity();
        } else if(parameters.datadeleted){
            activity = logic.getCourseFirstActivity(logic.getCourseId());
        } else if(parameters.activity != null){
            activity = logic.getActivity(parameters.activity);
        } else { // Nothing setted =(
            return;
        }

        module = logic.getModule(activity.getModule().getId());
        course = logic.getCourse(module.getCourse().getId());

        CourseComponents.checkParameters(course, module, activity);
        CourseComponents.loadMenu(activity, course, null, tofill);
        CourseComponents.createModulesMenu(tofill, course, this.getViewID(),
                logic);
        CourseComponents.createBreadCrumb(tofill, activity, module,
                this.getViewID());

        if(parameters.errortoken != null){
            System.out.println(parameters.errortoken);
        }

        List<Strategy> strategies = logic.getStrategies(activity);
        StrategyType [] strategyTypes = StrategyType.values();

        String[] strategyOptions = new String[strategyTypes.length];
        String[] strategyLabels = new String[strategyTypes.length];
        for(int i = 0; i < strategyTypes.length; i++){
            strategyOptions[i] = Integer.toString(strategyTypes[i].getValue());
            strategyLabels[i] = strategyTypes[i].getTitle();
        }
        for(Strategy strategy : strategies){
            if (strategy.getType()
                    .equals(StrategyType.LANGUAGE.getValue())) { //aquisicao
                UIBranchContainer rowMod = UIBranchContainer.make(tofill,
                        "li-rowsEstLi:");

                UIForm langForm = UIForm.make(rowMod, "language_strategy_form");
                langForm.parameters.add(
                        new UIELBinding("#{AdminActivityBean.strategyId}",
                                Long.toString(strategy.getId())));

                UIInput.make(langForm, "language_strategy_data",
                        "#{AdminActivityBean.strategyData}",
                        strategy.getBody());

                UIInput.make(langForm, "language_strategy_position",
                        "#{AdminActivityBean.strategyPosition}",
                        Integer.toString(strategy.getPosition()));

                UISelect.make(langForm, "language_strategy_type",
                        strategyOptions, strategyLabels,
                        "#{AdminActivityBean.strategyType}",
                        Integer.toString(StrategyType.LANGUAGE.getValue()));

                UICommand.make(langForm, "language_strategy_save",
                        "#{AdminActivityBean.updateStrategy}");
                UICommand.make(langForm, "language_strategy_delete",
                        "#{AdminActivityBean.deleteStrategy}");
            } else if (strategy.getType()
                    .equals(StrategyType.READING.getValue())) { //leitura
                UIBranchContainer rowMod = UIBranchContainer.make(tofill,
                        "li-rowsEstLe:");

                UIForm readForm = UIForm.make(rowMod, "reading_strategy_form");
                readForm.parameters.add(
                        new UIELBinding("#{AdminActivityBean.strategyId}",
                                Long.toString(strategy.getId())));

                UIInput.make(readForm, "reading_strategy_data",
                        "#{AdminActivityBean.strategyData}",
                        strategy.getBody());

                UIInput.make(readForm, "reading_strategy_position",
                        "#{AdminActivityBean.strategyPosition}",
                        Integer.toString(strategy.getPosition()));

                UISelect.make(readForm, "reading_strategy_type",
                        strategyOptions, strategyLabels,
                        "#{AdminActivityBean.strategyType}",
                        Integer.toString(StrategyType.READING.getValue()));

                UICommand.make(readForm, "reading_strategy_save",
                        "#{AdminActivityBean.updateStrategy}");
                UICommand.make(readForm, "reading_strategy_delete",
                        "#{AdminActivityBean.deleteStrategy}");

            }
        }

        UIForm addStrategyForm = UIForm.make(tofill, "strategy_form");

        addStrategyForm.parameters.add(
                new UIELBinding("#{AdminActivityBean.strategyActivity}",
                        Long.toString(activity.getId())));

        UIInput.make(addStrategyForm, "strategy_data",
                "#{AdminActivityBean.strategyData}");

        UIInput.make(addStrategyForm, "strategy_position",
                "#{AdminActivityBean.strategyPosition}");

        UISelect.make(addStrategyForm, "strategy_type", strategyOptions,
                strategyLabels, "#{AdminActivityBean.strategyType}");

        UICommand.make(addStrategyForm, "strategy_save",
                "#{AdminActivityBean.saveStrategy}");

    }

    @Override
    public ViewParameters getViewParameters() {
        return new CourseViewParameters(this.getViewID());
    }

    @Override
    public List<NavigationCase> reportNavigationCases() {
        List<NavigationCase> l = new ArrayList<NavigationCase>();
        l.add(new NavigationCase(CourseComponents.SAVED,
                new CourseViewParameters(AdminEstrategiasProducer.VIEW_ID, true,
                        false, false)));
        l.add(new NavigationCase(CourseComponents.UPDATED,
                new CourseViewParameters(AdminEstrategiasProducer.VIEW_ID, false,
                        true, false)));
        l.add(new NavigationCase(CourseComponents.DELETED,
                new CourseViewParameters(AdminEstrategiasProducer.VIEW_ID, false,
                        false, true)));
        return l;
    }

}