$(document).ready(function(){
  	var partsPos 	= new Array();	//Vetor com os posteriores de cada 'rightWord'
  	var actualPartPos;				//Posição da peça atual no vetor partsPos[x][0]
	var actualPart;					//Peça atual
	
	var numHoles 	= 25;	//Numero de 'holes' que serao criados
	var top 		= 10;	//Margem superior para 'holes' e peças
	var left 		= 80;	//Margem esquerda para 'holes' e peças
	var aux 		= 0;	//Contador auxiliar
	var direction	= 1;	//Direção do dominó (1 = direita, -1 = esquerda)  
	
	var dominoType 	= $('#container').attr('type');
  
  	//Cria os holes
	for(aux = 1; aux < numHoles +1; aux++){
		var hole = document.createElement('div');
	  
		//Definindo posicionamento de cada 'hole'
		hole.style.left = left+'px';
		hole.style.top = top+'px';
	  
		//Definindo direção de preenchimento de cada hole (-1: esq, 1: dir)
		hole.setAttribute('direction', direction);
		
		//Se o 'hole' for o último de alguma linha
		if(aux%6 == 0){
			//Entra na classe 'holeLast' que é 'na vertical'
			hole.setAttribute('class', 'holeLast');
			
			//leva o 'hole' mais par abaixo, para centralizá-lo na vertical
			hole.style.top = (top)+'px';
			
			//Muda de linha
			top += 70;
			
			//Dependando da direção
			if(direction == 1){
				//trata posicionamento horizontal
				left -= 145;
				//muda a direção
				direction = -1;
			}
			else{
				/*Se for o último 'hole' para a esquerda, arruma a margem 
				(para nao ficar fora da tela)*/
				hole.style.left = '0px';
				
				//trata posicionamento horizontal
				left += 145;
				//muda a direção
				direction = 1;
			}
		}
		
		//Se o 'hole' não for o último da linha
		else{
			//Entra na classe 'hole'
			hole.setAttribute('class', 'hole');
			
			//Avança para a esquerda se a direção for 1 (direita)
			if(direction == 1)
				left +=145;
			//CC, avança para a esquerda
			else
				left -=145;
		}
	  
		//Seta o id do primeiro 'hole' para que ele seja ativado
		if(aux == 1)
			hole.setAttribute('id','hole');
		
		//Coloca o hole no html
		$('#board').append(hole);
	}
	
  	//Pega as propriedades de cada peça (imagem) e as coloca no html
	$.get('../../../../readinweb-tool/content/modulos/exercicios/xml/parts'+dominoType+'.xml', function(xml){
		var partN	= 0;	//Contador para peças

		//Setando variáveis de posicionamento das peças
		top = 290;
		left = 10;
		
		aux = 0;	//Setando contador auxiliar
	
		//Para cada 'part' encontrado no xml
		$(xml).find('part').each(
			function(){					
				//Criando a tag div
				var divTag = document.createElement('div');
			
				//'leftWord' é a 'palavra esquerda' da peça
				var leftWord = $(this).find('leftWord').text();
				
				//'rightWord' é a 'palavra direita' da peça
				var rightWord = $(this).find('rightWord').text();

				//Setando os atributos 'leftWord' e 'rightWord'
				divTag.setAttribute('leftWord', leftWord);
				divTag.setAttribute('rightWord', rightWord);

				//Criando div para ficar à esquerda da peça
				var divTagL = document.createElement('div');
				divTagL.setAttribute('class', 'left');
				divTagL.setAttribute('id', 'left'+partN);
				
				//Criando div para ficar à direita da peça
				var divTagR = document.createElement('div');
				divTagR.setAttribute('class', 'right');
				divTagR.setAttribute('id', 'right'+partN);
				
				//Se for a primeira peça do jogo
				if($(this).attr('id') == 'first'){
					//Entra na classe 'first' que a coloca na primeira posição da tela
					divTag.setAttribute('class', 'first');
					
					//Coloca a div no documento
					$('#container').append(divTag);
				
					//Appenda a div que vai à direita na div da peça					
					$('.first').append(divTagR);
					//Seta o texto que vai dentro da parte direita
					$('#right'+partN).text(rightWord);
					
					//Setando a posicao da peça atual no vetor de posteriores
					actualPartPos = partN;
					
					//Seta a peça atual
					actualPart = divTag;
				}
			  
				//Se não for a primeira peça
				else{					
					//Criando a tag div e setando suas propriedades
					divTag.setAttribute('class', 'part');
					divTag.setAttribute('id', 'part'+partN);
					divTag.style.left = left+'px';
					divTag.style.top = top+'px';

					//Pula de linha quando não couberem mais peças
					if(partN%6 == 0){
						top += 65;
						left = 10;
					}
					//CC, avança para a esquerda
					else
						left += 145;
						
					//Seta a propriedade posVect na div, que diz qual sua linha no vetor partsPos
					divTag.setAttribute('posVect', partN);
					  
					//Coloca a div no documento
					$('#container').append(divTag);
					
					//Seta a posição que a div terá na peça
					$('#left'+partN).css('float', 'left');
					//Appenda a div que vai à esquerda na div da peça	
					$('#part'+partN).append(divTagL);
					//Seta o texto que vai dentro da parte esquerad
					$('#left'+partN).text(leftWord);
				
					//Seta a posição que a div terá na peça
					$('#right'+partN).css('float', 'right');
					//Appenda a div que vai à direita na div da peça	
					$('#part'+partN).append(divTagR);
					//Seta o texto que vai dentro da parte direita
					$('#right'+partN).text(rightWord);
				}

				//Zerando contador para o 'each' acima
				aux = 0;
				
				//Aloca espaço na matriz de posteriores para um novo vetor deles
				partsPos[partN] = new Array();
				
				//Coloca-se cada posterior numa posição
				$(this).find('pos').each(
					function(){
						partsPos[partN][aux] = new Array();
						partsPos[partN][aux][0] = $(this).find('word').text();
						partsPos[partN][aux][1] = $(this).find('phrase').text();
						aux++;
					}
				);

				//Atualiza numero de peças criadas
				partN++;
			}
		);
		
	

		$('.part').draggable({
			revert:true,
			scroll:false
		});


	
		//Propriedades de 'soltar'(drop)
		$('.hole, .holeLast').droppable({
			'accept': 	'.part',
			'tolerance': 	'touch',
			'activeClass':'border',
			//Quando a peça estiver sobre o 'hole'
			'over': function(event, ui){
				transform($(this), $(ui.draggable), $(actualPart));
			},
		  
			//Quando a peça sai do 'hole'
			'out': function(event, ui) {
				deTransform($(this), $(ui.draggable), $(actualPart));
			},

			//Quando a peça é solta em um holem que o aceita
			'drop': function(event, ui){
				var aux = 0;	//Contador auxiliar
				var match = 0; 	//Diz se já se encontrou a peça que encaixa  
				
				//Procura em toda a lista de posteriores da peça atual
				for(aux = 0; (aux < partsPos[actualPartPos].length) && (match != 1); aux++){

					//Se houver uma palavra posterior que combina com a da peça arrastada
					if(partsPos[actualPartPos][aux][0] == $(ui.draggable).attr('leftWord')){		
						//Encaixou!
						match = 1;
						
						//Id da peça atual
						var partID = $(ui.draggable).attr('id');
						actualPart = $(ui.draggable);
					
						//Arruma posição da peça atual
						$('#'+partID).css('top', $(this).css('top'));
						$('#'+partID).css('left', $(this).css('left'));

						//ativa o próximo droppable
						$(this).next().droppable('enable');
						
						//destroy o droppable atual (não se pode mais dropar nele)
						$(this).droppable('destroy');

						//Mostra frase com palavra formada pelos dominós
						popUp(partsPos[actualPartPos][aux][1]);
						
						//Setando 'actualPartPos'
						actualPartPos = $(ui.draggable).attr('posVect');
						
						//Tira-se o revert da peça para que ela se fixe
						$(ui).draggable({'revert':'false'});
					}
				}
				
				//Se não encaixar a peça
				if(match == 0){
					deTransform($(this), $(ui.draggable), $(actualPart));
					popUp(null);
				}
			}
		});
		
		//Desativa todos os drops
		$('.hole').droppable('disable');
		$('.holeLast').droppable('disable');

		//Ativa só o primeiro (que desencadeia os outros automaticamente)
		$('#hole').droppable('enable');
	});
});


function transform(droppable, draggable, actualPart){
	//Variável que diz se o 'hole' anterior é da classe 'holeLast'
	var lastHoleLast = 0;
	//Variável que diz se o 'hole' atual é da classe 'holeLast'
	var holeLast = 0;
  
	if($(droppable).prev().attr('class').search('holeLast')!= '-1'){
		//Altera a variável 'lastHoleLast'
		lastHoleLast = 1;
	}

	if($(droppable).attr('class').search('holeLast') != -1){
		//muda variável 'holeLast'
		holeLast = 1;
	}
  
	/*Se a direção é -1 (esquerda), temos de fazer a inversão 
	de valores para que o dominó não perca a consistencia*/
	if($(droppable).attr('direction') == '-1'){		
		//Pega as divs dentro da peça atual
		$(actualPart).find('div').each(
			function(){
				//Se ela estiver na classe 'right'
				if($(this).attr('class').search('right') != '-1' &&
				$(this).attr('class').search('chngd') == '-1'){
					//a coloca na classe 'chngd'
					$(this).addClass('chngd');
					//a alinha à direita
					$(this).css('float', 'right');
					//arruma altura e largura
					$(this).css('height', '100%');
					$(this).css('width', '50%');
					//a coloca na peça arrastada
					$(draggable).append($(this));
				}
			}
		);
		//Pega as divs dentro da peça arrastada
		$(draggable).find('div').each(
			function(){
				//Se ela estiver na classe 'right' e não na 'chngd'
				if($(this).attr('class').search('right') != '-1' &&
				$(this).attr('class').search('chngd') == '-1'){
				  //a alinha à esquerda
				  $(this).css('float', 'left');
				}
				//Se ela estiver na classe 'left'
				if($(this).attr('class').search('left') != '-1'){
					//a coloca na classe 'chngd'
					$(this).addClass('chngd');
				  
					//Se a peça atual eatá em um 'holeLast'
					if(lastHoleLast == '1'){
						$(this).css('height', '50%');
						$(this).css('width', '100%');
					}
				  
					//a appenda na peça atual
					$(actualPart).append($(this));
				}
			}
		);
	  
		//Se é o último 'hole' da linha
		if(holeLast == 1){
			//para cada div do arrastado
			$(draggable).find('div').each(
				function(){
					//Se ela estiver na classe 'right' e nao na 'chngd'
					if($(this).attr('class').search('right') != '-1' &&
					$(this).attr('class').search('chngd') == '-1'){
						//POG!! - não consegui arrumar o posicionamento direito
						$(this).parent().append($(this));
					}
				}
			);
		}
	}
	//Se a peça for da classe 'holeLast'
	if(holeLast == 1){
		//Muda suas dimensões
		$(draggable).css('background', 'url("../../../../../readinweb-tool/content/modulos/exercicios/imagens/dominov.gif")');
		$(draggable).css('height', '130px');
		$(draggable).css('width', '60px');
		$(draggable).find('div').each(
			function(){
				$(this).css('width', '100%');
				$(this).css('height', '50%');
			}
		);
	}
}

function deTransform(droppable, draggable, actualPart){
	//Variável que diz se o 'hole' anterior é da classe 'holeLast'
	var lastHoleLast = 0;
	//atualiza a variavel lastHoleLast caso a direção seja '-1'
	if($(droppable).attr('direction') == '-1'){
		if($(droppable).prev().attr('class').search('holeLast')!= '-1'){
			//Altera a variável 'holeLast'
			lastHoleLast = 1;
		}
	}
	//Se a peça for da classe 'holeLast' 
	if($(droppable).attr('class').search('holeLast') != -1){
		//Muda suas dimensões
		$(draggable).css('background', 'url("../../../../../readinweb-tool/content/modulos/exercicios/imagens/dominoh.gif")');
		$(draggable).css('height', '60px');
		$(draggable).css('width', '130px');
		$(draggable).find('div').each(
			function(){
				$(this).css('width', '50%');
				$(this).css('height', '100%');
			}
		);
	}
  
	/*Se a direção é -1 (esquerda), temos de fazer a desinversão 
	de valores para que o dominó não perca a consistencia*/
	if($(droppable).attr('direction') == '-1'){

		//Pega as divs dentro da peça atual
		$(actualPart).find('div').each(
			function(){
				//Se ela estiver na classe 'left' e na 'chngd'
				if($(this).attr('class').search('left') != '-1' &&
				$(this).attr('class').search('chngd') != '-1'){ 
					//a tira da classe 'chngd'
					$(this).removeClass('chngd');
					//a alinha à esquerda
					$(this).css('float', 'left');
				
					//Arruma altura e largura
					$(this).css('height', '100%');
					$(this).css('width', '50%');
				  
					//a coloca na peça arrastada
					$(draggable).append($(this));
				}
			}
		);
	
		//Pega as divs dentro da peça arrastada
		$(draggable).find('div').each(
			function(){
				//Se ela estiver na classe 'right'
				if($(this).attr('class').search('right') != '-1'){
					//Se ela não estiver na classe 'chngd'
					if($(this).attr('class').search('chngd') == '-1'){
						//a alinha à direita
						$(this).css('float', 'right');
					}
					//CC, tira-a da classe 'chngd' e a manda para a peça atual
					else{
						$(this).removeClass('chngd');
					  
						if(lastHoleLast == '1'){
							//Arruma altura e largura
							$(this).css('height', '50%');
							$(this).css('width', '100%');
						}
						//a coloca na peça atual
						$(actualPart).append($(this));
					}
				}
			}
		
		);
	  
		//Se é o último 'hole' da linha
		if($(droppable).attr('class').search('holeLast') != '-1'){
			//para cada div do arrastado
			$(draggable).find('div').each(
				function(){
					//Se ela estiver na classe 'right' e nao na 'chngd'
					if($(this).attr('class').search('right') != '-1' &&
					$(this).attr('class').search('chngd') == '-1'){
						//POG!! - não consegui arrumar o posicionamento direito
						$(this).parent().append($(this));
					}
				}
			);
		}
	}
	
}

function popUp(phrase){
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
	
	//Se está errado (não encaixou)
	if(phrase == null){
		$('#message').text('Esta combina\u00e7\u00e3o n\u00e3o existe!');
		$('#message').css('margin-top', '40px');
		$('#message').css('color', 'black');
		$('#close').css('display', 'none');
		$('#popUp').css('background', "*url('../../../../../readinweb-tool/content/modulos/exercicios/imagens/fundo_prompt.gif') repeat");
		$('#popUp').fadeIn('fast').wait(1000).fadeOut('fast');
	}
	
	//Caso contrário
	else{
		$('#message').text('Exemplo:\u00A0\u00A0'+phrase);
		$('#message').css('color', 'black');
		$('#message').css('margin', '12 12 0 12px');
		
		$('#close').css('display', 'block');
		
		$('#popUp').css('background', "*url('../../../../../readinweb-tool/content/modulos/exercicios/imagens/fundo_prompt.gif') repeat");
		$('#popUp').fadeIn('fast');
		$('#close:first').click(function(){
			$('#popUp').fadeOut('fast');
		});
	}
}
