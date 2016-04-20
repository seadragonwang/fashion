var page = require('webpage').create();
page.onResourceRequested = function (request) {
    console.log('Request ' + JSON.stringify(request, undefined, 4));
};
page.onResourceReceived = function (response) {
    console.log('Receive ' + JSON.stringify(response, undefined, 4));
};
page.open("https://www.jcrew.com/mens_feature/tobedeleted/catalogjcrewcomexclusives/pants/PRD~32756/99102160688/32756.jsp?N=21+16&Nbrd=J&Nloc=en_US&Nrpp=48&Npge=1&Nsrt=3&isFromSale=true&isSaleItem=true&isNewSearch=true");