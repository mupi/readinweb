$(document).ready(function(){
	$('#resposta').click(function(){
		$('#div_resposta_A').show();
		$('#div_resposta_B').show();
		$('#div_resposta_C').show();
	});

	$('.coluna').mouseover(function (){
		$(this).addClass('greyHighlight');
	});
	
	$('.coluna').mouseout(function () {
		$(this).removeClass('greyHighlight');
	});
});
