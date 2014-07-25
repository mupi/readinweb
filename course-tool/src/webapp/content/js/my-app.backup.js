/**
 * 
 * @param hid_Id
 * @param ajaxUrl
 * @param userId
 * @param int_atividadeId
 * @param int_moduloId
 * @param elBinding_registerTextRead
 * @return - Nada
 */
function registerTextRead(hid_actId, hid_modId, hid_usrId, elBinding_registerTextRead){
    // local variables
    var ajaxUrl, updater;
    var hidActObj, hidModObj, hidUsrObj;
    var callbackLoaded, callbackLoading;
	
    // get the field we are working with and check it
    hidActObj = document.getElementById(hid_actId);
    hidModObj = document.getElementById(hid_modId);
    hidUsrObj = document.getElementById(hid_usrId);
    if ((hidActObj == null) || (hidModObj == null) || (hidUsrObj == null) ){
        return;
    } else {
        ajaxUrl = hidUsrObj.form.action;
    }
	
    // this is a nOOb function (it doesnt do anything relevant). It just exists to complete RSF.getAJAXUpdater arguments
    callbackLoading = function(){
        return;
    }
	
    // this is a nOOb function (it doesnt do anything relevant). It just exists to complete RSF.getAJAXUpdater arguments
    callbackLoaded = function(results) {
        return;
    }
	
    // call ajax function
    updater = RSF.getAJAXUpdater([hidActObj, hidModObj, hidUsrObj], ajaxUrl, [elBinding_registerTextRead], callbackLoaded, callbackLoading);
    //updater = RSF.getAJAXUpdater([hidActObj, hidModObj, hidUsrObj], ajaxUrl, [elBinding_registerTextRead], callbackLoaded);
    updater();
    return;
}

/**
 * 
 * @param hid_actId
 * @param hid_modId
 * @param hid_usrId
 * @param elBinding_registerExerciseMade
 * @return
 */
function registerExerciseMade(hid_actId, hid_modId, hid_usrId, elBinding_registerExerciseMade){
    var ajaxUrl;
    var updater;
    var hidActObj, hidModObj, hidUsrObj;
    var callbackLoaded, callbackLoading;
	
    // get the field we are working with and check it
    hidActObj = document.getElementById(hid_actId);
    hidModObj = document.getElementById(hid_modId);
    hidUsrObj = document.getElementById(hid_usrId);
    if ((hidActObj == null) || (hidModObj == null) || (hidUsrObj == null) ){
        return;
    } else {
        ajaxUrl = hidUsrObj.form.action;
    }
	
    // this is a nOOb function (it doesnt do anything relevant). It just exists to complete RSF.getAJAXUpdater arguments
    callbackLoading = function(){
        return;
    }
	
    // this is a nOOb function (it doesnt do anything relevant). It just exists to complete RSF.getAJAXUpdater arguments
    callbackLoaded = function(results) {
        return;
    }
	
    // call ajax function
    updater = RSF.getAJAXUpdater([hidActObj, hidModObj, hidUsrObj], ajaxUrl, [elBinding_registerExerciseMade], callbackLoaded, callbackLoading);
    updater();
    return;
}

/**
 * 
 * @param hid_Id - ID do 'INPUT HIDDEN' que transmitira o codigo da questao acessada
 * @param links_Id - String contendo os ID's dos 'A HREF' referentes aos links das questoes separados pelo caracter ';' (devem estar ordenados crescentemente, comecando da questao 1)
 * @param quest_enunciado_id - ID da div que recebera o enunciado das questoes retornado pelo AJAX
 * @param user_answer_field_id - ID do textarea onde a resposta salva do usuario sera exibida
 * @param p_hidden_resp_sugerida - ID do objeto P que receberá a resposta sugerida
 * @param elBinding_reqQuestion - SDS (Só Deus Sabe)
 * @return - Nada
 */
function initMyAjaxStuff(hid_Id, links_Id, quest_enunciado_id, user_answer_field_id, p_hidden_resp_sugerida, button_sendAnswer_id, elBinding_reqQuestion, elBinding_userAnswer) {
    // set variables
    var ajaxUrl;
    var updater;
    var int_countLinks, int_iterator;
    var hidden_questId_obj, links_strings, links_array;
   
    // get the field we are working with and check it
    hidden_questId_obj = document.getElementById(hid_Id);
    if (hidden_questId_obj == null){
        return;
    }

    // get the URL from the form
    ajaxUrl = hidden_questId_obj.form.action;
   
    // set the javascript function to each question link
    internal_initAjax_questions(links_Id, ajaxUrl, quest_enunciado_id, p_hidden_resp_sugerida, user_answer_field_id, hidden_questId_obj, elBinding_reqQuestion);
   
    // set the javascript function to the 'send response' button
    internal_initAjax_answer(hidden_questId_obj, button_sendAnswer_id, user_answer_field_id, ajaxUrl, elBinding_userAnswer);
}


/**
 * Função Ajax executada quando o usuário clica no link das questões
 * links_Id - String contlinks_Idando o ID de todos os links concatenados com ';'
 * ajaxUrl - 
 * quest_enunciado_id - 
 * p_hidden_resp_sugerida_id - 
 * user_answer_field_id -
 * hidden_questId_obj -
 * elBinding_reqQuestion -   
 * @return
 */
function internal_initAjax_questions(links_Id, ajaxUrl, quest_enunciado_id, 
    p_hidden_resp_sugerida_id, user_answer_field_id, hidden_questId_obj, 
    elBinding_reqQuestion){
    var links_ids_array, links_objs_array;
    var int_countLinks, int_iterator;
    var updater;
    var callbackLoaded, callbackLoading;
    var showFunction;
	
    // get the link objects by their IDs
    links_ids_array = links_Id.split(";");
    int_countLinks = links_ids_array.length;
    links_objs_array = new Array();
    for(int_iterator = 0; int_iterator < int_countLinks; int_iterator++){
        links_objs_array[int_iterator] = document.getElementById(links_ids_array[int_iterator]);
        if (links_objs_array[int_iterator] == null){
            return (false);
        }
    }

    callbackLoaded = function(results) {
        // this function (callbackLoaded) defines what to do 
        // when the ajax response is received,
        // the response will be placed in the "results" variable

        // result.EL is a map of ELbinding -> JS Object
        var quest_enunciado_obj = document.getElementById(quest_enunciado_id);
        var resp_sugerida_obj = document.getElementById(p_hidden_resp_sugerida_id);
        var resp_usuario_obj = document.getElementById(user_answer_field_id);
        
        // get out the object (which is a string array in 
        // this case) and decode it
        
        //alert('o que tem em results!? -> ' + results.EL.toString());
        //alert('vai decodar:' + results.EL[elBinding_reqQuestion]);
        
        // FIXME
        //var resultArray = RSF.decodeRSFStringArray((results.EL[elBinding_reqQuestion]).join(":"));
        var resultArray = results.EL[elBinding_reqQuestion];        
        //alert('decodado');
        
        // output the array on the fields
        quest_enunciado_obj.innerHTML = resultArray[0];
        resp_sugerida_obj.innerHTML = resultArray[1];
        resp_usuario_obj.value = resultArray[2];
        resp_sugerida_obj.style.visibility = "hidden";
        
        // show the loaded div
        document.getElementById("number_questions").style.display = "block";
        document.getElementById("contentOff_questions").style.display = "none";
        document.getElementById("contentOn_questions").style.display = "block";
    }
    
    callbackLoading = function(){
        // this function (callbackLoading) defines what to do 
        // while the ajax response is being received
    	
        var tmp_object, tmp_function;
        var vet_objetosLinks, vet_functions;
        var int_count1, int_count2, int_total;
    	
        vet_objetosLinks = new Array();
        vet_functions = new Array();
    	
        // show the loading div
        document.getElementById("number_questions").style.display = "none";
        document.getElementById("contentOn_questions").style.display = "none";
        document.getElementById("contentOff_questions").style.display = "block";
    }
    
    // setup the function which initiates the AJAX request
    updater = myRSF.getAJAXUpdater([hidden_questId_obj], ajaxUrl, [elBinding_reqQuestion], callbackLoaded, callbackLoading);
    //updater = RSF.getAJAXUpdater([hidden_questId_obj], ajaxUrl, [elBinding_reqQuestion], callbackLoaded);
    
    // set the functions to the links
    for(int_iterator = 0; int_iterator < int_countLinks; int_iterator++){
        var int_indexOfTmp;
        var texto_pulverizado = int_iterator;
        var vet_idSplitedTmp;
 	   
        // when the button gets the focus, the hidden value is changed to
        // the last value of the buttons' ID (Ex: "input_link_q_2" -> "2")
        links_objs_array[int_iterator].onmouseover = function() {
            vet_idSplitedTmp = this.id.split("_");
            int_indexOfTmp = vet_idSplitedTmp[vet_idSplitedTmp.length -1];
            hidden_questId_obj.value = int_indexOfTmp;
        }
 	   
        // send request when field changes
        links_objs_array[int_iterator].onclick = updater; 
        links_objs_array[int_iterator].onmouseup = function() {
            document.getElementById("your_answer").style.display = "block";
            document.getElementById("suggested_answer").style.display = "block";
        }
        links_objs_array[int_iterator].onkeyup = function(e) {
            e = (e || window.event);
            //se o botao precionado for o 'enter'
            if (e.keyCode == 13) {
                document.getElementById("your_answer").style.display = "block";
                document.getElementById("suggested_answer").style.display = "block";
            }
        }
    }
    
    return (true);
}


/**
 * Função Ajax executada quando o usuário clica no botão de salvar resposta
 * hidden_questId_obj - 
 * button_sendAnswer_id
 * user_answer_field_id - 
 * ajaxUrl -
 * elBinding_userAnswer - 
 * @return
 */
function internal_initAjax_answer(hidden_questId_obj, button_sendAnswer_id, user_answer_field_id, ajaxUrl, elBinding_userAnswer){
    var updater;
    var callback;
    var button_sendAnswer_obj;
    var user_answer_field_obj;

    button_sendAnswer_obj = document.getElementById(button_sendAnswer_id);
    user_answer_field_obj = document.getElementById(user_answer_field_id);
   
    if ((button_sendAnswer_obj == null) || (user_answer_field_obj == null)){
        return (false);
    }

    callback = function(results){
        // this function (callback) defines what to do 
        // when the ajax response is received,
        // the response will be placed in the "results" variable
      
        // get out the object (which is a string array in 
        // this case) and decode it
        //FIXME
        //var resultArray = RSF.decodeRSFStringArray(results.EL[elBinding_userAnswer]);
        var resultArray = results.EL[elBinding_userAnswer];
    }
      
    // setup the function which initiates the AJAX request
    //updater = RSF.getAJAXUpdater([user_answer_field_obj], ajaxUrl, [elBinding_userAnswer], callback);
    updater = RSF.getAJAXUpdater([hidden_questId_obj, user_answer_field_obj], ajaxUrl, [elBinding_userAnswer], callback);
   
    button_sendAnswer_obj.onclick = updater;
}
