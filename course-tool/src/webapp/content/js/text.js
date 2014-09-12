$(document).ready(function(){

    $('.translation').tooltip();

    var gmodule = 0;
    var question = 1;
    var menu_switch = 0;
    /*$('#tabs').tabs();*/
    /* $('a.media').media( { width: 300, height: 20 } ); */
    $(".menu").click(function(){
        if(menu_switch == 0){
            $(".bloco").fadeIn("fast");
            menu_switch = 1;
        }
        else{
            $(".bloco").fadeOut("fast");
            menu_switch = 0;
        }
    });
    
    $(".modulo").click(function(){
        var module = $(this).val();
        if(module != gmodule){
            gmodule = module;
            $("div .atividades").hide(0);
            $("div ." + module).fadeIn("slow");
        }
        else {
            gmodule = 0;
            $("div ." + module).fadeOut("slow");
        }
    });
    $("#border_dicionario").click(function () {
        if(question == 1){
            //$("#questions").fadeOut("fast");
            $("#questions").hide(0);
            //$("#tabs").show("fast");
            $("#tabs").fadeIn(300);
            $(this).text("QUEST\u00d5ES");
            question = 0;
        }
        else{
            //$("#tabs").fadeOut("fast");
            $("#tabs").hide(0);
            //$("#questions").show("fast");
            $("#questions").fadeIn(300);
            $(this).text("DICION\u00c1RIO");
            question = 1;
        }
    });

    $(".switch").click(function(){
        $("#textactimage").slideToggle();
    });

    /*$("#number_questions > ul li").corner("top");*/
    $("#number_questions > ul li").click(function(){
        $(".selected").removeClass("selected");
        $(this).addClass("selected");
    });

    $("#tabs-1 > ul li strong").each(function(){
        /*switch(($(this).text())[0]){
            case a:
            alert($(this).text());
                    $(this).parent().addClass('aFirst');
                    break;
            case b:
                    $(this).addClass('bFirst');
                    break;
            case c:
                    $(this).addClass('cFirst');
                    break;
            case c:
                    $(this).addClass('cFirst');
                    break;
            case d:
                    $(this).addClass('dFirst');
                    break;
            case e:
                    $(this).addClass('eFirst');
                    break;
            case f:
                    $(this).addClass('fFirst');
                    break;
            case g:
                    $(this).addClass('gFirst');
                    break;
            case h:
                    $(this).addClass('hFirst');
                    break;
            case i:
                    $(this).addClass('iFirst');
                    break;
            case j:
                    $(this).addClass('jFirst');
                    break;
            case k:
                    $(this).addClass('kFirst');
                    break;
            case l:
                    $(this).addClass('lFirst');
                    break;
            case m:
                    $(this).addClass('mFirst');
                    break;
            case n:
                    $(this).addClass('nFirst');
                    break;
            case o:
                    $(this).addClass('oFirst');
                    break;
            case p:
                    $(this).addClass('pFirst');
                    break;
            case q:
                    $(this).addClass('qFirst');
                    break;
            case r:
                    $(this).addClass('rFirst');
                    break;
            case s:
                    $(this).addClass('sFirst');
                    break;
            case t:
                    $(this).addClass('tFirst');
                    break;
            case u:
                    $(this).addClass('uFirst');
                    break;
            case v:
                    $(this).addClass('vFirst');
                    break;
            case w:
                    $(this).addClass('wFirst');
                    break;
            case x:
                    $(this).addClass('xFirst');
                    break;
            case y:
                    $(this).addClass('yFirst');
                    break;
            case z:
                    $(this).addClass('zFirst');
                    break;
    }*/
    });

});


/**
 * This function just changes the visibility of the object 
 */
function show_answer(el_id) {
    var el = document.getElementById(el_id).style;
    el.visibility = 'visible';
}

/**
 * 
 * submiter_obj - OBJETO do tipo 'input submit' que ira alterar o valor da coisa toda - deve ter o ID no formato ''
 * hidden_module_id - ID do objeto 'hidden' que ira armazenar o valor do modulo de coisa toda
 * hidden_activity_id - ID do objeto 'hidden' que ira armazenar o valor da atividade de coisa toda
 * hidden_exercise_id - ID do objeto 'hidden' que ira armazenar o valor do exercicio como zero
 */
function update_module( submiter_obj, hidden_module_id, hidden_activity_id, hidden_exercise_id ){
    var submiter_id;
    var hidden_mod_obj;
    var hidden_act_obj;
    var hidden_exe_obj;
    var submiter_splited;
    var module_id, activity_id;
        
    submiter_id = submiter_obj.id;
    submiter_splited = submiter_id.split('_');
        
    activity_id = submiter_splited[submiter_splited.length - 2];
    module_id = submiter_splited[submiter_splited.length - 1];

    hidden_mod_obj = document.getElementById(hidden_module_id);
    hidden_act_obj = document.getElementById(hidden_activity_id);
    hidden_exe_obj = document.getElementById(hidden_exercise_id);
    
    if (hidden_mod_obj != null){
        hidden_mod_obj.value = module_id;
        if (hidden_act_obj != null){
            hidden_act_obj.value = activity_id;
            if (hidden_exe_obj != null){
                hidden_exe_obj.value = 0;
            } else {
                alert('Problemas com H de exe.');
            }
        } else {
            alert('Problemas com H de act.');
        }
    } else {
        alert('Problemas com H de mod.');
    }
    return;
}

//
$(document).ready(function() {
    var readTimeLimit = (document.getElementById("time_text").innerHTML) * 1000;

    // this is a time counter. When the time ends, ir registers the text reading
    setTimeout("registerTextRead('hid_regTxtRead_actId','hid_regTxtRead_modId','hid_regTxtRead_usrId','TextReadingAjaxBean.results');", readTimeLimit );
});