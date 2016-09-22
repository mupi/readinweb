$(document).ready(function(){
	var left = 240;


	$('.hole').each(function(){
		$(this).css('left', left);
		left += 120;
	});
	
	left = 240;
	
	$('.image').each(function(){
		$(this).css('left', left);
		left += 120;
	});
	
	left = 240;
	
	$('.subtitle').each(function(){
		$(this).css('left', left);
		left += 120;
	});

	//Função para implementar um 'wait'
	$.fn.wait = function(time, type){
	    time = time || 1000;
	    type = type || "fx";
	    return this.queue(type, function() {
	        var self = this;
	        setTimeout(function() {
	            $(self).dequeue();
	        }, time);
	    });
	};
	

	$('.hole').droppable({
		'accept': 		'.image',
		'tolerance': 	'pointer',
		'drop': function(event, ui){
			if($(ui.draggable).attr('id') == $(this).attr('id')){
				$(ui.draggable).css('top', $(this).css('top'));
				$(ui.draggable).css('left', $(this).css('left'));
				$('#message').html('Encaixe correto!<br/>Parab&eacute;ns!');
				$('#popUp').fadeIn('fast').wait(1000).fadeOut('fast');
				$(ui).draggable({'revert':'false'});
			}
			else{
				$('#message').html('Encaixe incorreto!<br/>Tente novamente!');
				$('#popUp').fadeIn('fast').wait(1000).fadeOut('fast');
			}
		}
	});
	
	$('.image').draggable({
		revert:true,
		scroll:false
	});
});