$(document).ready(function(){
	$(".answerButton").click(function(){
		
		var right = "../../../../readinweb-tool/content/modulos/exercicios/imagens/certo.gif";
	    var wrong = "../../../../readinweb-tool/content/modulos/exercicios/imagens/errado.gif";
		
		$('.image').hide();
		$('.comment, .translation').show('fast');
		
		if ($('input:checked').val() == 'b' ||
		   $('input:checked').val() == 'c' ||
		   $('input:checked').val() == 'd'){
			$('input:checked').parent().next().children().attr('src', wrong);
			$('input:checked').parent().next().children().show('fast');
		}
		else if ($('input:checked').val() == 'a'){
			$('input:checked').parent().next().children().attr('src', right);
			$('input:checked').parent().next().children().show('fast');
		}
	});
});
 