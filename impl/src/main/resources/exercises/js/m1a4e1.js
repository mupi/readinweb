$(document).ready(function(){
	$('.wrong, .right').click(function () {
		$(this).toggleClass('clicked');
	});
	$('#resposta').click(function(){
		$('.right').addClass('blue');
		
		$('.wrong').each(function(){
			if($(this).hasClass('clicked')){
				$(this).addClass('red');
			}
		});
        $('.attention').each(function(){
            $(this).addClass('grey');
        });
        $("#answer_comment").css("visibility", "visible");
	});
});
