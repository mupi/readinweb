$(document).ready(function(){
	$(".answerButton").click(function(){
		$(this).parent().next().children().show('fast');
		$(this).parent().next().next().children().show('fast');
		$(this).parent().next().next().next().children().show('fast');
	});
});
 