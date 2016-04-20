var page = new WebPage();
page.viewportSize = {
		  width: 1024,
		  height: 1080
};
var url = 'https://www.jcrew.com/womens_category/sweaters/jcrewcashmere/PRD~47920/47920.jsp?Nbrd=J&Nloc=en_US&Nrpp=48&Npge=1&Ntrm=Collection%20cashmere%20boyfriend%20V-neck%20sweater&isFromSearch=true&isNewSearch=true'
page.onLoadStarted = function () {
    console.log('loading:' + url);
};

page.onConsoleMessage = function(msg) {
    console.log(msg);
};

page.onLoadFinished = function (status) {
    console.log(status);
    console.log('loaded:' + url);
    page.injectJs('jquery-1.11.0.min.js');

//    page.evaluate(function() {
//        var ev = document.createEvent("MouseEvents");
//        ev.initEvent("click", true, true);
//        document.querySelector("a#BK0001").dispatchEvent(ev);
//    });
//    BK0001, offset.left + 5, offset.top -> PK5737
//    BK0001, offset.left -240, offset.top -> PK6115
//    PK6115, offset.left - 105, offset.top -> PK5737
//    var colorCode = 'BK0001';
    var colorCode = 'NA6660';
//  var colorCode = 'PK5737';
//    var colorCode = 'PK6115';
	var offset = page.evaluate(function(c) {
		return $('a#' + c).offset();
    }, colorCode);
	console.log(offset.left + ', ' + offset.top);
	
//	page.sendEvent('mousedown', offset.left+1, offset.top+1, 'left');				        	
//	page.sendEvent('mouseup', offset.left+1, offset.top+1, 'left');				        	
//	page.sendEvent('click', offset.left+1, offset.top+1);				        	
	page.sendEvent('click', offset.left+1, offset.top+1);				        	
//	page.sendEvent('hover', offset.left+1, offset.top+1, 'left');
	var clazz;
	setTimeout(function(){
		clazz = page.evaluate(function(){
			return $("section#sizes0 div.size-box[data-size='SMALL']").attr('class');
		});
	    console.log(clazz);
		page.render('foo.png');
	    phantom.exit();
	}, 10000);
};
page.open(url);