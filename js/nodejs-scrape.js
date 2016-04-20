var request = require('request');
var cheerio = require('cheerio');

var url = 'https://www.jcrew.com/mens_feature/tobedeleted/catalogjcrewcomexclusives/pants/PRD~32756/99102160688/32756.jsp?N=21+16&Nbrd=J&Nloc=en_US&Nrpp=48&Npge=1&Nsrt=3&isFromSale=true&isSaleItem=true&isNewSearch=true';
request(url, function(err, resp, body) {
	if (err)
		throw err;
	$ = cheerio.load(body);
	console.log($('div.full-price span.price-soldout'));
});
