var divRatio = 0.3;

window.onload = main;
function main() {
	test();
	initSize();
	window.onresize=initSize;
}

function initSize(){
	var gap = 2;
	$("#div_header").css({"width":window.innerWidth});
	$("#div_siderBar").css({
		"width": ( window.innerWidth*divRatio-gap ),
		"height": window.innerHeight-$("#div_header").height()
	});
	$("#div_main").css({
		"width": ( window.innerWidth*(1-divRatio)-gap ),
		"height": window.innerHeight-$("#div_header").height()
	})
}

function test(){
	$.post("http://localhost:8919","{'userID':123,'userCode':123}", function(data){

	});
}