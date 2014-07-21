    function frequenciaClicada(objeto){
        var obj_it;
        var str_objId, int_count, int_word, int_freq;

        str_objId = objeto.id;

        int_word = str_objId.charAt(1);
        int_freq = str_objId.charAt(4);

        // if the first frequency block was clicked, check if the other are just hiddened
        if (int_freq == 1){
          if (objeto.className=="div_freqOff"){
            objeto.className="div_freq1";
            return;
          } else {
            obj_it = document.getElementById("p"+int_word+"_f2");
            if (obj_it.className=="div_freqOff"){
              objeto.className = "div_freqOff";
              return;
            }
          }
        }

        for(int_count=1; int_count <= int_freq; int_count++){
          obj_it = document.getElementById("p"+int_word+"_f"+int_count);
          obj_it.className = "div_freq"+int_count;
        }

        for(int_count=int_count; int_count <= 5; int_count++){
          obj_it = document.getElementById("p"+int_word+"_f"+int_count);
          obj_it.className = "div_freqOff";
        }
      }

      /**
        *
        */
      function checaCorretudePalavra(int_codPalavra, str_binaryResponse){
        var obj_it, int_count;

        for(int_count = 1; int_count <= 5; int_count++){
          if ((str_binaryResponse.charAt(int_count-1)=="1") && 
              (document.getElementById("p"+int_codPalavra+"_f"+int_count).className==("div_freq"+int_count))
             ){
            continue;
          } else if ((str_binaryResponse.charAt(int_count-1)=="0") && 
                     (document.getElementById("p"+int_codPalavra+"_f"+int_count).className=="div_freqOff")) {
            continue;
          } else {
            return (false);
          }
        }
        return (true);
      }

      /**
        */
      function mostra_resposta_correta() {
	if (checaCorretudePalavra(1, "11111")){
          document.getElementById("p1_c").style.display="block";
          document.getElementById("p1_e").style.display="none";
        } else {
          document.getElementById("p1_c").style.display="none";
          document.getElementById("p1_e").style.display="block";
        }

	if(checaCorretudePalavra(2, "11110")){
          document.getElementById("p2_c").style.display="block";
          document.getElementById("p2_e").style.display="none";
        } else {
          document.getElementById("p2_c").style.display="none";
          document.getElementById("p2_e").style.display="block";
        }

	if(checaCorretudePalavra(3, "11100")){
          document.getElementById("p3_c").style.display="block";
          document.getElementById("p3_e").style.display="none";
        } else {
          document.getElementById("p3_c").style.display="none";
          document.getElementById("p3_e").style.display="block";
        }

	if(checaCorretudePalavra(4, "11000")){
          document.getElementById("p4_c").style.display="block";
          document.getElementById("p4_e").style.display="none";
        } else {
          document.getElementById("p4_c").style.display="none";
          document.getElementById("p4_e").style.display="block";
        }

	if(checaCorretudePalavra(5, "10000")){
          document.getElementById("p5_c").style.display="block";
          document.getElementById("p5_e").style.display="none";
        } else {
          document.getElementById("p5_c").style.display="none";
          document.getElementById("p5_e").style.display="block";
        }

	if(checaCorretudePalavra(6, "00000")){
          document.getElementById("p6_c").style.display="block";
          document.getElementById("p6_e").style.display="none";
        } else {
          document.getElementById("p6_c").style.display="none";
          document.getElementById("p6_e").style.display="block";
        }
      }