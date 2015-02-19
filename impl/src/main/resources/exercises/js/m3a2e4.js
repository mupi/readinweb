$(document).ready(function(){

    $('#resposta1').click(function(){
        $('#div_resposta1').show();
        $(".grupo_nominal").addClass("grupo");
        $(".nucleo").addClass("classe_nucleo grupo");
    });
    $('#resposta2').click(function(){
        $('#div_resposta2').show();
        $(".grupo_maior2").addClass("grupo_maior");
        $(".grupo_nominal2").addClass("grupo");
        $(".nucleo2").addClass("classe_nucleo grupo");
    });
    $('#resposta3').click(function(){
        $('#div_resposta3').show();
        $(".grupo_maior3").addClass("grupo_maior");
        $(".grupo_nominal3").addClass("grupo");
        $(".nucleo3").addClass("classe_nucleo grupo");
    });

});
