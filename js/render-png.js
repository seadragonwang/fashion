var url = 'https://medium.com/art-of-product-design/353076395187';

//https://www.jcrew.com/search2/index.jsp?N=21+17&Ntrm=&Nsrt=3&Npge=1
// https://medium.com/art-of-product-design/353076395187
var page = new WebPage();
//page.onResourceRequested = function(req) {
//	console.log('requested: ' + JSON.stringify(req, undefined, 4));
//};
//
//page.onResourceReceived = function(res) {
//	console.log('received: ' + JSON.stringify(res, undefined, 4));
//};
page.onLoadFinished = function(status) {
//    page.includeJs('myjs.js');
    page.injectJs('jquery-1.11.0.min.js');
	var promotion = page.evaluate(function(){
		return $('.categoryPlusPromoArray').text();
	});
	console.log('Promotion Code :' + promotion);
	window.setTimeout(function() {
		page.render("example.png");
		phantom.exit();
	}, 5000);
};
page.open(url);
