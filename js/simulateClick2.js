var page = new WebPage();

var url = 'https://www.jcrew.com/womens_category/sweaters/jcrewcashmere/PRD~47920/47920.jsp?Nbrd=J&Nloc=en_US&Nrpp=48&Npge=1&Ntrm=Collection%20cashmere%20boyfriend%20V-neck%20sweater&isFromSearch=true&isNewSearch=true';
page.onLoadStarted = function () {
    console.log('loading:' + url);
};

page.onLoadFinished = function (status) {
    console.log(status);
    console.log('loaded:' + url);
    page.injectJs('jquery-1.11.0.min.js');
    page.evaluate(function() {
		function click(el){
			var ev = document.createEvent("MouseEvent");
			ev.initMouseEvent(
				"click",
				true /* bubble */, true /* cancelable */,
				window, null,
				0, 0, 0, 0, /* coordinates */
				false, false, false, false, /* modifier keys */
				0 /*left*/, null
			);
			document.dispatchEvent(ev, el);
		}
        click($('div.price-wrapper section.color-row div.color-box[data-color=BK0001]'));
    });
	setTimeout(function(){
		var clazz = page.evaluate(function(){
			return $('section#sizes0 div.size-box').attr('class');
		});
	    console.log(clazz);
	    phantom.exit();
	}, 5000);
};

page.open(url);
