(function (){
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
            : match
            ;
        });
    };
    String.prototype.capitalize = function() {
        return this.charAt(0).toUpperCase() + this.slice(1);
    }
})();
var RIW = RIW || {};
(function() {
    RIW.updateWord = function(id, word, meaning, submit, elBinding){
        var id_field = document.getElementById(id);
        var word_field = document.getElementById(word);
        var meaning_field = document.getElementById(meaning);
        var submit_field = document.getElementById(submit);

        var url = id_field.form.action;
        var wordType = elBinding.indexOf("DictionaryAjaxBean") > -1 ?
                "dictionary" :
                    "functional";
        var callback = function(results) {
            jQuery("#" + wordType + "_row_" + id_field.value).children().each(
                    function(i){
                        switch (i) {
                        case 1:
                            jQuery(this).text(word_field.value);
                            break;
                        case 2:
                            jQuery(this).text(meaning_field.value);
                            break;
                        default:
                            break;
                        }
                    });
            id_field.form.reset();
            jQuery("#edit" + wordType.capitalize() + "Modal").modal('toggle');
        }


        var updater = RSF.getAJAXUpdater([id_field, word_field, meaning_field],
                url, [elBinding], callback);


        submit_field.onclick = function(){
            updater();
            return false;
        }
    }

    RIW.deleteWord = function(id, submit, elBinding){
        var id_field = document.getElementById(id);
        var submit_field = document.getElementById(submit);

        var url = id_field.form.action;
        var wordType = elBinding.indexOf("DictionaryAjaxBean") > -1 ?
                "dictionary" :
                    "functional";

        var callback = function(results) {
            var result = results.EL[elBinding];
            if("success" == result[0]){
                jQuery("#" + wordType + "_row_" + id_field.value).remove();
                id_field.form.reset();
                jQuery("#edit" + wordType.capitalize() + "Modal").modal('toggle');
            } else {
                console.log("error");
            }
        }

        var updater = RSF.getAJAXUpdater([id_field], url, [elBinding],
                callback);

        submit_field.onclick = function(){
            if(confirm('Tem certeza que deseja excluir palavra?')){
                updater();
            }
            return false;
        }
    }

    RIW.addWord = function(foreign, word, meaning, submit, elBinding){
        var foreign_field = document.getElementById(foreign);
        var word_field = document.getElementById(word);
        var meaning_field = document.getElementById(meaning);
        var submit_field = document.getElementById(submit);

        var url = foreign_field.form.action;
        var wordType = elBinding.indexOf("DictionaryAjaxBean") > -1 ?
                "dictionary" :
                    "functional";

        var callback = function(results) {

            var result = results.EL[elBinding];
            if("success" == result[0]){
                var template = RIW.templates.word_row.format(result[1],
                        word_field.value, meaning_field.value,
                        wordType.capitalize(), wordType);
                var d = jQuery(template);
                d.insertAfter("dictionary" == wordType ?
                        "#dictionary_table_header" :
                "#functional_table_header");
                foreign_field.form.reset();
            } else {
                console.log("error on insert");
            }
        }


        var updater = RSF.getAJAXUpdater([foreign_field, word_field, meaning_field],
                url, [elBinding], callback);

        submit_field.onclick = function(){
            updater();
            return false;
        }
    }

    RIW.templates = {
            word_row : "" +
            "<tr id='{4}_row_{0}'>" +
            "     <td>{0}</td>" +
            "     <td>{1}</td>" +
            "     <td>{2}</td>" +
            "     <td>" +
            "        <a data-target='#edit{3}Modal' data-toggle='modal'>" +
            "            <span class='glyphicon glyphicon-cog'></span>" +
            "        </a>" +
            "     </td>" +
            "</tr>"
    }

})();

jQuery(document).ready(
        function() {
            jQuery("#question-tabs a:first").tab('show');

            jQuery('#editDictionaryModal').on(
                    'show.bs.modal',
                    function(e) {
                        var row = jQuery(e.relatedTarget).parent().siblings();
                        jQuery("#edit_dictionary_id").val(jQuery(row[0]).text());
                        jQuery("#edit_dictionary_word").val(jQuery(row[1]).text());
                        jQuery("#edit_dictionary_meaning").val(jQuery(row[2]).text());
                    });

            jQuery('#editFunctionalModal').on(
                    'show.bs.modal',
                    function(e) {
                        var row = jQuery(e.relatedTarget).parent().siblings();
                        jQuery("#edit_functional_id").val(jQuery(row[0]).text());
                        jQuery("#edit_functional_word").val(jQuery(row[1]).text());
                        jQuery("#edit_functional_meaning").val(jQuery(row[2]).text());
                    });

        });
