var server = require('webserver').create(),
        system = require('system'),
        fs     = require('fs'),
        port   = system.env.PORT || 8888;

var service = server.listen(port, function(request, response) {
    console.log(JSON.stringify(request, null, 4));

    if(request.method == 'POST' && request.post && request.post.url){
        var url = request.post.url;

        request_page(url, function(item){
            response.statusCode = 200;
            response.write(JSON.stringify(item));
            response.close();
        })

    }
});

if(service) console.log("server started - http://localhost:" + server.port);

function request_page(url, callback){

    var page = new WebPage();

    page.onLoadStarted = function () {
        console.log('loading:' + url);
    };

    page.onLoadFinished = function (status) {
        console.log(status);
        console.log('loaded:' + url);
        page.injectJs('jquery-1.11.0.min.js');
        var item = page.evaluate(function () {
            var itemObject = new Object();
            itemObject.products = [];
			itemObject.itemImages = [];
			var originalPrice;
            if($('div#variant')){
                $('div.variant-wrapper').each(function(index, ele){
                if($(ele).find("input.product-details-variants:checked").length > 0){
                    itemObject.type = $(ele).find("div.product-pricing span:not([class])").text().trim();
					if($(ele).find("div.product-pricing-wrapper span.full-price").length > 0){
						originalPrice = $(ele).find("div.product-pricing-wrapper span.full-price").text().trim().replace('$','');
					}else if($(ele).find("div.product-pricing-wrapper span.price-soldout").length > 0){
						originalPrice = $(ele).find("div.product-pricing-wrapper span.price-soldout").text().trim().replace('$','');
					}
                }
                });
            }
			if($('div.price-wrapper div.product-detail-price').length > 0){
			    for(var i=0; i<$('div.price-wrapper div.product-detail-price').length;i++){
					var price = $('div.price-wrapper div.product-detail-price')[i].textContent.trim().replace('$','');
					var colorGroup = $('div.price-wrapper section.color-row')[i];
					$(colorGroup).find('div.color-box').each(function(){
						var colorCode = $(this).attr('data-color');
						$('section.sizes div.size-box').each(function(){
							var product = new Object();
							product.size = $(this).attr('data-size');
							product.colorCode = colorCode;
							product.originalPrice = originalPrice;
							product.productDetails = [];
							var productDetail = new Object();
							productDetail.price = price;
							productDetail.available = true;
							product.productDetails.push(productDetail);
							itemObject.products.push(product);
						});
					});	
				}
			}else{
			    for(var i=0; i<$('div.price-wrapper section.color-row').length;i++){
					var colorGroup = $('div.price-wrapper section.color-row')[i];
					$(colorGroup).find('div.color-box').each(function(){
						var colorCode = $(this).attr('data-color');
						var element = 'div.price-wrapper section.color-row div.color-box a#' + colorCode + ' img.product-detail-images';
						var colorImage = $(element);
						var event = document.createEvent('MouseEvent');
						event.initMouseEvent('click', true, true, window, 1, 0, 0);
						
						colorImage.dispatchEvent(event);
						$('section.sizes div.size-box').each(function(){
							var product = new Object();
							product.size = $(this).attr('data-size');
							product.colorCode = colorCode;
							product.originalPrice = originalPrice;
							product.productDetails = [];
							var productDetail = new Object();
							productDetail.price = originalPrice;
							if($('section.sizes div.size-box.unavailable').length > 0){
								productDetail.available = false;
							}else{
								productDetail.available = true;
							}
							product.productDetails.push(productDetail);
							itemObject.products.push(product);
						});
					});	
				}
			
			}
            itemObject.name = $('section.description header h1').text();
            itemObject.number = $('span.item-num').text().replace("item", "").trim();
            $('div.product-detail-img img.product-detail-images').each(function() {
				var itemImage = new Object();
                itemImage.bigImageUrl = $(this).attr("data-imgurl");
				itemImage.smallImageUrl = $(this).attr("src");
				itemObject.itemImages.push(itemImage);
            });
            itemObject.description = $('div.product_desc').html();
            return itemObject;
        });
        setTimeout(function(){
            callback(item);
            page.close();
        }, 5000);
    };

    page.open(url);
}
