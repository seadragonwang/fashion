var page = require('webpage').create();
page.open("https://www.jcrew.com/search2/index.jsp?N=21+17&Ntrm=&Nsrt=3&Npge=1", function (status) {
    page.viewportSize = { width:2000, height:2000 };
    page.render('screenshot.png')
});