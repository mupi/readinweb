$(document).ready(function(){
	$(".answerButton").click(function(){
		
		var right = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
	    var wrong = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";
		
		$('.image').hide();
		
		if ($('input[name=ra]:checked').val() == '1'){
			$('input[name=ra]:checked').parent().next().children().attr('src', right);
			$('input[name=ra]:checked').parent().next().children().show('fast');
		}
		else{
			$('input[name=ra]:checked').parent().next().children().attr('src', wrong);
			$('input[name=ra]:checked').parent().next().children().show('fast');
		}
		
		if ($('input[name=rb]:checked').val() == '1'){
			$('input[name=rb]:checked').parent().next().children().attr('src', wrong);
			$('input[name=rb]:checked').parent().next().children().show('fast');
		}
		else{
			$('input[name=rb]:checked').parent().next().children().attr('src', right);
			$('input[name=rb]:checked').parent().next().children().show('fast');
		}
		
		if ($('input[name=rc]:checked').val() == '1'){
			$('input[name=rc]:checked').parent().next().children().attr('src', right);
			$('input[name=rc]:checked').parent().next().children().show('fast');
		}
		else{
			$('input[name=rc]:checked').parent().next().children().attr('src', wrong);
			$('input[name=rc]:checked').parent().next().children().show('fast');
		}
		
		if ($('input[name=rd]:checked').val() == '1'){
			$('input[name=rd]:checked').parent().next().children().attr('src', wrong);
			$('input[name=rd]:checked').parent().next().children().show('fast');
		}
		else{
			$('input[name=rd]:checked').parent().next().children().attr('src', right);
			$('input[name=rd]:checked').parent().next().children().show('fast');
		}
		
		if ($('input[name=re]:checked').val() == '1'){
			$('input[name=re]:checked').parent().next().children().attr('src', wrong);
			$('input[name=re]:checked').parent().next().children().show('fast');
		}
		else{
			$('input[name=re]:checked').parent().next().children().attr('src', right);
			$('input[name=re]:checked').parent().next().children().show('fast');
		}
	});
});