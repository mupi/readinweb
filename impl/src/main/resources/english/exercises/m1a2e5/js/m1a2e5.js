$(document).ready(function(){
	var flashvars ={
		'file': '/readinweb-course-tool/content/modulos/exercicios/schizophrenia.flv',
		'image': '/readinweb-course-tool/content/modulos/exercicios/preview.jpg',
		'plugins': 'captions-1',
		'captions.file': '/readinweb-course-tool/content/modulos/exercicios/schizophrenia.xml'
	};
	var params ={
		'allowscriptaccess': 'always'
	};
	swfobject.embedSWF('/readinweb-course-tool/content/modulos/exercicios/player.swf', 'myAlternativeContent', '420', '300', '9.0.124', 'expressInstall.swf', flashvars, params);

	$("#butQuest1").click(function(){
		$("#answer1").fadeIn(400);
	});
	$("#butQuest2").click(function(){
		$("#answer2").fadeIn(400);
	});

}); 