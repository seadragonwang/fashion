var page = require('webpage').create();

address = "https://www.jcrew.com/mens_feature/tobedeleted/catalogjcrewcomexclusives/pants/PRD~32756/99102160688/32756.jsp?N=21+16&Nbrd=J&Nloc=en_US&Nrpp=48&Npge=1&Nsrt=3&isFromSale=true&isSaleItem=true&isNewSearch=true";
output = "jcrew.png";

page.paperSize = {
	format : "A4",
	orientation : "portait",
	margin : {
		left : "1cm",
		right : "1cm",
		top : "1cm",
		bottom : "1cm"
	}
};
page.settings.userAgent = 'Mozilla';
var pageRendered = false;
var renderPage = function() {
	if (pageRendered === true)
		return;
	pageRendered = true;
	page.render(output);
	phantom.exit();
};

page.open(address, function(status) {
	if (status !== 'success') {
		console.log('Unable to load the address!');
		console.log(JSON.stringify(response));
		phantom.exit();
	} else {
		page.onCallback = function(data) {
			if (data.ready === true)
				renderPage();
		};
		window.setTimeout(renderPage, 10000);
	}
});