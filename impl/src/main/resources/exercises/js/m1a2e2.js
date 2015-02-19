
  $(document).ready(function(){
    //    $('span').removeClass();
	$('input[name=tipo]').change(function(){
		$('span').removeClass('azul_claro');
		$('span').removeClass('grifado');
		$('span').removeClass('resp_errada');
	});

    $('.palavra').click(function () { 
    	$(this).toggleClass('grifado'); 
    });
    
    $('#resposta').click(function(){
	    if ($('input[name=tipo]:checked').val() == 'suj') {
	  	  //$('span').removeClass('grifado');
		  //$('span').removeClass('azul_claro');
		  
		  $('.complemento .grifado').addClass('resp_errada');
		  $('.verbo .grifado').addClass('resp_errada');
		  $('.sujeito').addClass('azul_claro');
	    }
	    else if ($('input[name=tipo]:checked').val() == 'ver')
	    {		
	  	  //$('span').removeClass('grifado');

		  $('.complemento .grifado').addClass('resp_errada');
		  $('.sujeito .grifado').addClass('resp_errada');
		  $('.verbo').addClass('azul_claro');
	    }
	    else if ($('input[name=tipo]:checked').val() == 'com')
	    {
	  	  //$('span').removeClass('grifado');
		  $('.verbo .grifado').addClass('resp_errada');
		  $('.sujeito .grifado').addClass('resp_errada');
		  $('.complemento').addClass('azul_claro');
	    };
    });
    $('#limpar').click(function(){
 	  $('span').removeClass('grifado');
	  $('span').removeClass('azul_claro');
	  $('span').removeClass('resp_errada');
    });

  });