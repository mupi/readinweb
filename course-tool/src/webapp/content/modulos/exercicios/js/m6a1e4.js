$j(document).ready(function(){
	var left = 240;


	$j('.hole').each(function(){
		$j(this).css('left', left);
		left += 120;
	});

	left = 240;

	$j('.image').each(function(){
		$j(this).css('left', left);
		left += 120;
	});

	left = 240;

	$j('.subtitle').each(function(){
		$j(this).css('left', left);
		left += 120;
	});

	//Função para implementar um 'wait'
	$j.fn.wait = function(time, type){
	    time = time || 1000;
	    type = type || "fx";
	    return this.queue(type, function() {
	        var self = this;
	        setTimeout(function() {
	            $j(self).dequeue();
	        }, time);
	    });
	};


	$j('.hole').droppable({
		'accept': 		'.image',
		'tolerance': 	'pointer',
		'drop': function(event, ui){
			if($j(ui.draggable).attr('id') == $j(this).attr('id')){
				$j(ui.draggable).css('top', $j(this).css('top'));
				$j(ui.draggable).css('left', $j(this).css('left'));
				$j('#message').html('Encaixe correto!<br/>Parab&eacute;ns!');
				$j('#popUp').fadeIn('fast').wait(1000).fadeOut('fast');
				$j(ui).draggable({'revert':'false'});
			}
			else{
				$j('#message').html('Encaixe incorreto!<br/>Tente novamente!');
				$j('#popUp').fadeIn('fast').wait(1000).fadeOut('fast');
			}
		}
	});

	$j('.image').draggable({
		revert:true,
		scroll:false
	});
});