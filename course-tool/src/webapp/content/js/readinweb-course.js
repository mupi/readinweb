var RIW = RIW || {};
(function() {
    RIW.saveUserAnswer = function (answer, question, submit, elBinding) {
        var answer_field = document.getElementById(answer);
        var question_field = document.getElementById(question);
        var submit_field = document.getElementById(submit);

        // get the URL from the form
        var url = answer_field.form.action;
        var callback = function(results) {
            //var result = RSF.decodeRSFStringArray(results.EL[elBinding]);
            if("error" == results.EL[elBinding][0]){
                submit_field.className =
                    submit_field.className + " alert-danger";
            } else {
                submit_field.className =
                    submit_field.className + " alert-success";
            }
        }

        // Function which initiates the AJAX request
        var updater = RSF.getAJAXUpdater([answer_field, question_field], url,
                [elBinding], callback);

        // Event to trigger the Ajax request function
        submit_field.onclick = function(){
            updater(); // send request when form submited
            return false;
        }
    }

    RIW.saveTextRead = function(activity, time, elBinding){
        // get the field we are working with
        var activity_field = document.getElementById(activity);
        var time_field = document.getElementById(time);
        // get the URL from the form
        var url = activity_field.form.action;

        var callback = function(results) {
            console.log(results);
        }

        // Function which initiates the AJAX request
        var updater = RSF.getAJAXUpdater([activity_field], url,
                [elBinding], callback);

        // Event to trigger the Ajax request function
        window.setTimeout(updater, time_field.value);
    }

    RIW.redirectToJustification = function (url){
        console.log(url);
            var ua        = navigator.userAgent.toLowerCase(),
                isIE      = ua.indexOf('msie') !== -1,
                version   = parseInt(ua.substr(4, 2), 10);

            // IE8 and lower
            if (isIE && version < 9) {
                var link = document.createElement('a');
                link.href = url;
                document.body.appendChild(link);
                link.click();
            }

            // All other browsers
            else { window.location.replace(url); }
    }

})();