jQuery.divselect = function(divselectid,inputselectid,defaultValue) {
	var a=0;
	var inputselect = $(inputselectid);
	$(divselectid+" cite").html(defaultValue);
	inputselect.val(defaultValue);
	$(divselectid+" cite").click(function(e){
		a=1;
		var ul = $(divselectid+" ul");
		if(ul.css("display")=="none"){
			ul.slideDown("fast");
		}else{
			ul.slideUp("fast");
		}
		e = e||event; stopFunc(e);
	});
	$(divselectid+" ul li a").click(function(e){
		var txt = $(this).text();
		$(divselectid+" cite").html(txt);
		var value = $(this).attr("selectid");
		inputselect.val(value);
		$(divselectid+" ul").hide();
		e = e||event; stopFunc(e);
		
	});
	$(document).click(function(e){
	  $(divselectid+" ul").hide();
	});
};

function stopFunc(e){	
	e.stopPropagation?e.stopPropagation():e.cancelBubble = true;		
}