var RIW = RIW || {};
(function() {
	RIW.saveUserAnswer = function (answer, question, submit, elBinding) {
		// get the field we are working with
		var answer_field = document.getElementById(answer);
		var question_field = document.getElementById(question);
		var submit_field = document.getElementById(submit);
		// get the URL from the form
		var url = answer_field.form.action;
	
		var callback = function(results) {
			//var result = RSF.decodeRSFStringArray(results.EL[elBinding]);
			if("error" == results.EL[elBinding][0]){
				submit_field.className = submit_field.className + " alert-danger";
			} else {
				submit_field.className = submit_field.className + " alert-success";
			}
		}
	
		// setup the function which initiates the AJAX request
		var updater = RSF.getAJAXUpdater([answer_field, question_field], url, [elBinding], callback);
		// setup the input field event to trigger the ajax request function
		submit_field.onclick = function(){ 
			updater(); // send request when field changes
			return false;
		}
	}
})();