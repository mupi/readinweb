function initMyAjaxStuff(fieldId, elBinding) {
	// get the field we are working with
	var field = document.getElementById(fieldId);
	// get the URL from the form
	var url = field.form.action;

	var callback = function(results) {
		// this function (callback) defines what to do 
		// when the ajax response is received,
		// the response will be placed in the "results" variable
		console.log(results);

		// result.EL is a map of ELbinding -> JS Object
		//var resultField = document.getElementById("result_field");
		
		// get out the object (which is a string array in 
		// this case) and decode it
		//var resultArray = RSF.decodeRSFStringArray(results.EL[elBinding]);
		// output the array on the screen
		//resultField.innerHTML = resultArray;
	}

	// setup the function which initiates the AJAX request
	var updater = RSF.getAJAXUpdater([field], url, [elBinding], callback);
	// setup the input field event to trigger the ajax request function
	field.onchange = updater; // send request when field changes
}