$(document).ready(function(){

	$("#tabs_m1a2e4").tabs();
	
	var certo = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
    var errado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";

    $('#btn_resposta1').click(function(){
		if ($('#opcao1').val() == '3') {
			$('#img_resp1').attr({ src: certo});
		}
		else {
			$('#img_resp1').attr({ src: errado});
		};
    });

    $('#btn_resposta2').click(function(){
		if ($('#opcao2').val() == '1') {
			$('#img_resp2').attr({ src: certo});
		}
		else {
			$('#img_resp2').attr({ src: errado});
		};
    });
    
    $('#btn_resposta3').click(function(){
		if ($('#opcao3').val() == '1') {
			$('#img_resp3').attr({ src: certo});
		}
		else {
			$('#img_resp3').attr({ src: errado});
		};
    });
    
    $('#btn_resposta4').click(function(){
		if ($('#opcao4').val() == '2') {
			$('#img_resp4').attr({ src: certo});
		}
		else {
			$('#img_resp4').attr({ src: errado});
		};
    });
    
    $('#btn_resposta5').click(function(){
		if ($('#opcao5').val() == '1') {
			$('#img_resp5').attr({ src: certo});
		}
		else {
			$('#img_resp5').attr({ src: errado});
		};
		
		if ($('#opcao6').val() == '3') {
			$('#img_resp6').attr({ src: certo});
		}
		else {
			$('#img_resp6').attr({ src: errado});
		};
    });
    
    $('#btn_resposta6').click(function(){
		if ($('#opcao7').val() == '2') {
			$('#img_resp7').attr({ src: certo});
		}	
		else {
			$('#img_resp7').attr({ src: errado});
		};
    });
    
    $('#btn_resposta7').click(function(){
		if ($('#opcao8').val() == '2') {
			$('#img_resp8').attr({ src: certo});
		}
		else {
			$('#img_resp8').attr({ src: errado});
		};
    });

}); 