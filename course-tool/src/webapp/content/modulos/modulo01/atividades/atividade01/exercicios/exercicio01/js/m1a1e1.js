 $(document).ready(function(){
	
    $("label").removeClass("p_rosa");
    $(".p_rosa").click(function () {
      $(this).toggleClass("rosa");
    });
    $(".v_rosa").click(function () {
      $(this).toggleClass("rosa");
    });
    $(".v_verde_escuro").click(function () {
      $(this).toggleClass("verde_escuro");
    });
    $(".v_verde_claro").click(function () {
    $(this).toggleClass("verde_claro");
    });
    $(".v_azul_claro").click(function () {
      $(this).toggleClass("azul_claro");
    });
    $(".v_roxo").click(function () {
      $(this).toggleClass("roxo");
    });
    $(".v_amarelo").click(function () {
      $(this).toggleClass("amarelo");
    });
    $(".v_vermelho").click(function () {
      $(this).toggleClass("vermelho");
    });
    $(".v_cinza").click(function () {
      $(this).toggleClass("cinza");
    });
    $(".v_azul_escuro").click(function () {
      $(this).toggleClass("azul_escuro");
    });

    $(".willallow").click(function () {
      $(".willallow").toggleClass("vermelho");
    });

   
    $("#resposta").click(function(){
      $(".willallow").addClass("vermelho");
      $(".v_rosa").addClass("rosa");
      $(".v_verde_escuro").addClass("verde_escuro");
      $(".v_verde_claro").addClass("verde_claro");
      $(".v_vermelho").addClass("vermelho");
      $(".v_cinza").addClass("cinza");
      $(".v_amarelo").addClass("amarelo");
      $(".v_azul_escuro").addClass("azul_escuro");
      $(".v_azul_claro").addClass("azul_claro");
      $(".v_roxo").addClass("roxo");
    });

  });
