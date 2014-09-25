<?php 
    $tool_root = "http://readinweb.cel.unicamp.br/readinweb-course-tool";
?>
<!DOCTYPE html      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml">
<head ><script  language="JavaScript" src="<?php echo $tool_root; ?>/content/js/rsf.js" type="text/javascript"></script>
<script  language="JavaScript" src="<?php echo $tool_root; ?>/content/js/rsfsakaiportal.js" type="text/javascript"></script>

<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
<title >Projeto Read in Web - Exerc&iacute;cios</title>

<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Rufina:400,700"/>
<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Open+Sans:400,600,700"/>

<!-- Bootstrap -->
<link  rel="stylesheet" type="text/css" href="<?php echo $tool_root; ?>/content/assets/bootstrap/css/bootstrap.min.css"/>
<!-- End Bootstrap -->
<link  rel="stylesheet" type="text/css" href="<?php echo $tool_root; ?>/content/assets/font-awesome-4.1.0/css/font-awesome.min.css"/>

<link  rel="stylesheet" type="text/css" href="<?php echo $tool_root; ?>/content/css/main.css"/>

<link  rel="stylesheet" type="text/css" href="<?php echo $tool_root; ?>/content/css/exercicios.css"/>
</head>
<body  onload="if (typeof(addSakaiRSFDomModifyHook) != 'undefined'){ addSakaiRSFDomModifyHook('Maina8bb5c38x3233x4304x9b9dxb41d1dd73857');}setMainFrameHeight('Maina8bb5c38x3233x4304x9b9dxb41d1dd73857');setFocus(focus_path);">
    <div class="container-fluid">
        <ol class="breadcrumb">
            <li >
                Voc&ecirc; est&aacute; no M&oacute;dulo
                <span >1</span>
            </li>
            <li >
                Atividade
                <span >1</span>
            </li>
            <li class="active">
                <span >How green is my planet?</span>
            </li>
        </ol>
    </div>
    <nav role="navigation" class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button data-target="#lista_menu_principal" class="navbar-toggle" data-toggle="collapse" type="button">
                <span class="sr-only">Menu principal</span>
                <span >MENU</span>
            </button>
        </div>
        <div id="lista_menu_principal" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li >
                    <a  title="Leia o texto da atividade" name="TEXTO" href="http://readinweb.cel.unicamp.br/portal/tool/a8bb5c38-3233-4304-9b9d-b41d1dd73857/text?course=1&amp;module=1&amp;activity=1">TEXTO</a>
                </li>
                <li >
                    <a  title="Veja as estratégias de leitura para essa atividade" name="ESTRATEGIA" href="http://readinweb.cel.unicamp.br/portal/tool/a8bb5c38-3233-4304-9b9d-b41d1dd73857/estrategias?course=1&amp;module=1&amp;activity=1">ESTRAT&#201;GIAS</a>
                </li>
                <li >
                    <a  title="Exercícios para aquisição de língua" name="EXERCICIOS" class="active" href="http://readinweb.cel.unicamp.br/portal/tool/a8bb5c38-3233-4304-9b9d-b41d1dd73857/exercicios?course=1&amp;module=1&amp;exercise=1&amp;activity=1">EXERC&#205;CIOS</a>
                </li>
                <li >
                    <a  title="Dicionário gramatical" name="GRAMÁTICA" href="http://readinweb.cel.unicamp.br/portal/tool/a8bb5c38-3233-4304-9b9d-b41d1dd73857/gramatica?course=1&amp;module=1&amp;activity=1">GRAM&#193;TICA</a>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        Lista de módulos <b class="caret"></b>
                    </a>
                    <ul id="lista_menu_modulos" class="dropdown-menu">
                        <li>
                            <a  data-parent="#lista_menu_modulos" class="link-modulo" data-toggle="collapse" type="button" href="/#module_1_activities">Módulo 1</a>
                            <ul id="module_1_activities" class="lista_menu_atividades panel-collapse collapse">
                                <li>
                                    <a  id="input_link_a_1" class="link_atividade" href="http://readinweb.cel.unicamp.br/portal/tool/a8bb5c38-3233-4304-9b9d-b41d1dd73857/exercicios?course=1&amp;module=1&amp;activity=1">1 - How green is my planet?</a>
                                </li><li>
                                    <a  id="input_link_a_2" class="link_atividade" href="http://readinweb.cel.unicamp.br/portal/tool/a8bb5c38-3233-4304-9b9d-b41d1dd73857/exercicios?course=1&amp;module=1&amp;activity=2">2 - Freud: A brief biography</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    </nav>
    <div class="container-fluid">
        <div class="row-fluid">
            <div id="function">
                <div id="exercicios">
                    <div id="indice_exercicios">
                        <div id="lista_indice_exercicios">
                            <ul class="nav nav-pills">
                                <li class="active">
                                    <a  id="input_link_e_1" class="btn submitModule" href="#">Exercício 1</a>
                                </li><li>
                                    <a  id="input_link_e_2" class="btn submitModule" href="#">Exercício 2</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div id="conteudo_exercicios">
                        <div id="conteudo_conteudo_exercicios">
                            <div  id="html_exercicio_div">
<?php
    $exercise = file_get_contents($_GET['e'] . ".html");
    $exercise = str_replace("/readinweb-course-tool/content/modulos/exercicios/", "", $exercise); 
    echo $exercise;
?>

</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script  src="<?php echo $tool_root; ?>/content/js/jquery-2.1.0.min.js" type="text/javascript"></script>
    <script  src="<?php echo $tool_root; ?>/content/assets/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script  src="<?php echo $tool_root; ?>/content/js/dropdown-menu.js" type="text/javascript"></script>
    <script src="http://readinweb.cel.unicamp.br/library/js/headscripts.js" type="text/javascript"></script>
    <script  src="<?php echo $tool_root; ?>/content/js/rsf.js" type="text/javascript"></script>
</body>
</html>


