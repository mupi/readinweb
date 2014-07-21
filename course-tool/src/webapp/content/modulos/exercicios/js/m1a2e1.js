$(document).ready(function(){
	$('.wrong, .right').click(function () {
		$(this).toggleClass('clicked');
	});
	
	$(".palavra").mouseover(function(e){
        $(this).addClass("txt_underline");
    }); 
    $(".palavra").mouseout(function(e){
        $(this).removeClass("txt_underline");
    }); 
    
	$('#resposta').click(function(){
		$('.right').addClass('blue');
		
		$('.wrong').each(function(){
			if($(this).hasClass('clicked')){
				$(this).addClass('red');
			}
		});
	});
});