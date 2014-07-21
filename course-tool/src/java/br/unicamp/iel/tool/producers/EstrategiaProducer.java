package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Strategy;
import br.unicamp.iel.model.StrategyType;
import br.unicamp.iel.tool.commons.CourseComponents;

public class EstrategiaProducer implements ViewComponentProducer, ViewParamsReporter {

    private static Log logger = LogFactory.getLog(EstrategiaProducer.class);
    @Setter
    private ReadInWebCourseLogic logic;

    // The VIEW_ID must match the html template (without the .html)
    public static final String VIEW_ID = "estrategias";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
        CourseViewParameters parameters = (CourseViewParameters) viewparams;
        Course course; 
        Activity activity;
        Module module;

        if(logic == null){
            System.out.println("Logic bean is null");
            return;
        } else {
            activity = logic.getActivity(parameters.activity);
            module = logic.getModule(parameters.module);
            course = logic.getCourse(parameters.course);
        }
        
        CourseComponents.loadMenu(parameters, tofill);
        CourseComponents.createModulesMenu(tofill, course, this.getViewID(), logic);
        
        UIOutput.make(tofill, "current_mod", 
                Long.toString(module.getPosition()));
        UIOutput.make(tofill, "current_act", 
                Long.toString(activity.getPosition()));
        UIVerbatim.make(tofill, "current_title", activity.getTitle());

        logic.registerAccess("Acesso as estrategias.", 
                this.getViewID(), activity);

        List<Strategy> strategies = 
                new ArrayList<Strategy>(logic.getStrategies(activity));

        // Show Strategies
        for(Strategy strategy : strategies){
            if (strategy.getType()
                    .equals(StrategyType.LANGUAGE.getValue())) { //aquisicao
                UIBranchContainer rowMod = UIBranchContainer.make(tofill,
                        "li-rowsEstLi:");
                UIVerbatim.make(rowMod,
                        "divli", strategy.getBody());
            } else if (strategy.getType()
                    .equals(StrategyType.READING.getValue())) { //leitura
                UIBranchContainer rowMod = UIBranchContainer.make(tofill,
                        "li-rowsEstLe:");
                UIVerbatim.make(rowMod,
                        "divle", strategy.getBody());
            }
        }
        
        // Fill the form
        UIForm.make(tofill, "input_form_tst_m");

        /*
             CommonMethods.makeModulesLinks(form_tst_m, "li-rowsMod:", this.logic, this.session);

             //
             CommonMethods.makeActivitiesLinks(form_tst_m, "div_atividades:", "p-rowsAct:", this.logic, this.session);
         */
        //
        //            // Para voltar pro sumario
        //            CommonMethods.makeReturnToSumaryDiv(tofill, "div_redirectSummary", this.session);
        //
        //            //PARA DROP DOWN NOVO
        //            CommonMethods.makeDropDownLinks(form_tst_m, "li-rowsMod:", "div_atividades:", "p-rowsAct:", this.logic, this.session);
        //
        //            //Para o link do rodape
        //            CommonMethods.makeFotterSumLink(tofill, "frm_fotterSum", "hid_fotterMod", "hid_fotterAct", "hid_fotterExe", "but_fotterSum");
        //
        //            // OFF - Setting the common things
    }

    @Override
    public ViewParameters getViewParameters() {
        return new CourseViewParameters(this.getViewID());
    }
}
