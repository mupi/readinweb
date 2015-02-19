$j(document).ready(function(){

	$j('#resposta').click(function(){
		$j("#div_resposta").show();
	});

	var clicou = new Array(9);
	var cor = new Array(4);

	/* cor[1] = '#87CEFA';
				cor[2] = '#9ACD32';
				cor[3] = '#FFA500';
				cor[4] = '#FF6A6A'; */

	$j('.clicavel').click(function(){
		$jnum = parseInt($j(this).attr("id").charAt(1)) - 1;
		$jnum = "p" + $jnum;

		if (clicou[$jnum] != true)          //verifica se a palavra já foi clicada, para não desenhar nada por cima sem necessidade
		{
			clicou[$jnum] = true;

			var position = $j(this).position();
			var position2 = $j("span[id="+$jnum+"]").position();
			//alert (position2);

			$j(this).children('.pa1').addClass("azul");
			$j(this).children('.pa2').addClass("verde");
			$j(this).children('.pa3').addClass("amarelo");
			$j(this).children('.pa4').addClass("vermelho");
			$j("span[id="+$jnum+"]").children('.pa1').addClass("azul");
			$j("span[id="+$jnum+"]").children('.pa2').addClass("verde");
			$j("span[id="+$jnum+"]").children('.pa3').addClass("amarelo");
			$j("span[id="+$jnum+"]").children('.pa4').addClass("vermelho");


			if ((position.left)<(position2.left)){
				var l1 = position.left;
				var l2 = position2.left;
				var p1_esquerda = true;
			} else {
				var l1 = position2.left;
				var l2 = position.left;
				var p1_esquerda = false;
			};
			//l1 sempre vai ser o valor da esquerda


			if ((position.top)<(position2.top)){
				var t1 = position.top;
				var t2 = position2.top;
				var p1_emcima = true;          //se a palavra1.top < palavra2.top, palavra1 está em cima
			} else {
				var t1 = position2.top;
				var t2 = position.top;
				var p1_emcima = false;         //se a palavra1.top > palavra2.top, palavra2 está em cima
			};
			//t1 sempre vai ser o valor de cima


			var d = l2 - l1;   //distancia na horizontal entre as duas palavras
			var h = t2 - t1;   //distancia na vertical entre as duas palavras

			/*	if (p1_emcima){
							if (p1_esquerda){ //palavra de onde sai a flecha está na esquerda e em cima

							}else { //palavra de onde sai a flecha está na direita e em cima

							};

						} else { */
			if (p1_esquerda){ //palavra de onde sai a flecha está na esquerda e embaixo, a seta vai sair de cima da palavra
				var x1 = l1 + 25;  		   //meio da palavra da esquerda    OK
				var x2 = 1/2 * (d + 60) + l1;     //metade da distancia mais o left da palavra da esquerda (assim fica no meio das duas palavras) OK
				var x3 = x2;     			//os dois pontos de controle iguais     OK
				var x4 = l2 + 30;  		   //meio da palavra da direita (início da flecha)    OK
				var x5 = x4 - 10;  			   //  OK
				var x6 = x2;               //x do ponto2 de controle - todos iguais OK
				var x7 = x2;    			//x do ponto2 de controle - todos iguais OK
				var x8 = x1;          // fim da flecha
				var y1 = t2 - 5;		   //ponto acima da palavra da direita (início da flecha)     OK
				var y2 = t1 - 15;          // OK
				var y3 = y2;               // OK
				var y4 = t1 -3; 		   // fim da primeira curva OK
				var y5 = t1;		   // início da segunda curva (fim da flecha) OK
				var y6 = t1 - 5;
				var y7 = y6;
				var y8 = y1;

				// Ponta da flecha

				var x9 = x5 - 10;
				var x10 = x5 + 5;
				var x11 = x5 + 10;
				var y9 = y5 + 1;
				var y10 = y5 + 3;
				var y11 = y5 - 8;

			}else { //palavra de onde sai a flecha está na direita e embaixo
				var x1 = l2 + 30;  		   //meio da palavra da direita (início da flecha)    OK
				var x2 = 1/2 * (d + 60) + l1;     //metade da distancia mais o left da palavra da esquerda (assim fica no meio das duas palavras) OK
				var x3 = x2;     			//os dois pontos de controle iguais     OK
				var x4 = l1 + 30;  		   //meio da palavra da esquerda    OK
				var x5 = x4 + 10;  			   //  OK
				var x6 = x2;               //x do ponto2 de controle - todos iguais OK
				var x7 = x2;    			//x do ponto2 de controle - todos iguais OK
				var x8 = x1;          // fim da flecha
				var y1 = t2 - 5;		   //ponto acima da palavra da direita (início da flecha)     OK
				var y2 = t1 - 15;          // OK
				var y3 = y2;               // OK
				var y4 = t1 - 5; 		   // fim da primeira curva OK
				var y5 = t1 - 2;		   // início da segunda curva (fim da flecha) OK
				var y6 = t1 - 5;
				var y7 = y6;
				var y8 = y1;

				// Ponta da flecha

				var x9 = x4 - 5;
				var x10 = x4 + 5;
				var x11 = x4 + 18;
				var y9 = y4 - 5;
				var y10 = y4 + 8;
				var y11 = y4 + 4;
			};
			// };

			$j("#div1").fillBezier_modificado([ x1, x2, x3, x4],[y1, y2, y3, y4], [x5, x6, x7, x8],[y5, y6, y7, y8],{color: '#696969' , stroke: 2});
			$j("#div1").fillPolygon([x9, x10 , x11], [y9, y10, y11], {color:'#696969'});
		};
	});
})