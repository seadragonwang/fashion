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
    page.viewportSize = {
  		  width: 1024,
  		  height: 1080
    };
    page.onLoadStarted = function () {
        console.log('loading:' + url);
    };

    page.onConsoleMessage = function(msg) {
    	system.stderr.writeLine( 'console: ' + msg );
    };
    
    page.onLoadFinished = function (status) {
        console.log(status);
        console.log('loaded:' + url);
        page.injectJs('jquery-1.11.0.min.js');
        
        var colorObjects = page.evaluate(function(){
        	var colors = [];
		    $('div.price-wrapper section.color-row').each(function(){
		    	var colorCodes = [];
		    	$(this).find('div.color-box').each(function(){
		    		colorCodes.push($(this).attr('data-color'));
	        	});
		    	colors.push(colorCodes);
		    });
        	return colors;
        });

        var item = page.evaluate(function () {
            var itemObject = new Object();
            itemObject.products = [];
			itemObject.itemImages = [];
            if($('div#variants').length > 0){
                $('div.variant-wrapper').each(function(index, ele){
	                if($(ele).find("input.product-details-variants:checked").length > 0){
	                    itemObject.type = $(ele).find("div.product-pricing span:not([class])").text().trim();
						if($(ele).find("div.product-pricing-wrapper span.full-price").length > 0){
							itemObject.originalPrice = $(ele).find("div.product-pricing-wrapper span.full-price").text().trim().replace('$','');
						}else if($(ele).find("div.product-pricing-wrapper span.price-soldout").length > 0){
							itemObject.originalPrice = $(ele).find("div.product-pricing-wrapper span.price-soldout").text().trim().replace('$','');
						}
	                }
                });
            }else{
            	itemObject.originalPrice = $('section.description div.full-price span:not([class])').text().trim().replace('$','');
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
        console.log(colorObjects.length);
        for(var i = 0; i < colorObjects.length; i++){
        	console.log('+++++' + colorObjects[i].length);
        	for(var j = 0; j < colorObjects[i].length; j++){
        		console.log(i + ', ' + j);
        		var colorCode = colorObjects[i][j];
        		var originalPrice = item.originalPrice;
        		console.log('Color code: ' + colorCode);
        		console.log('Original Price : ' + originalPrice);
		        var offset = page.evaluate(function(colorCode) {
		    		return $('a#' + colorCode).offset();
		        }, colorCode);
		        page.sendEvent('click', offset.left+5, offset.top+5);
		    	setTimeout(function(){
		    		console.log('hello, world!');
		    	}, 3000);

		        var productList = page.evaluate(function (i, j, colorCode, originalPrice) {
		        	console.log('i = ' + i);
		        	console.log('j = ' + j);
		        	console.log('----' + colorCode);
		        	console.log('++++' + originalPrice);
		        	var productList = [];
					var color = $('span.color-name').text().trim();
					if($('div.price-wrapper div.product-detail-price').length > 0){
						var priceObject = $('div.price-wrapper div.product-detail-price')[i];
						var price = $(priceObject).text().trim().replace('$','');
						$('section.sizes div.size-box').each(function(){
				        	var productObject = new Object();
							productObject.productSize = $(this).attr('data-size');
							productObject.colorCode = colorCode;
							productObject.color = color;
							productObject.originalPrice = originalPrice;
							productObject.productDetails = [];
							var productDetail = new Object();
							productDetail.price = price;
							if($(this).hasClass('unavailable')){
								productDetail.available = false;
							}else{
								productDetail.available = true;
							}
							productObject.productDetails.push(productDetail);
							productList.push(productObject);
						});	
					}else{
						$('section.sizes div.size-box').each(function(){
				        	var productObject = new Object();
							productObject.productSize = $(this).attr('data-size');
							productObject.colorCode = colorCode;
							productObject.color = color;
							productObject.originalPrice = originalPrice;
							productObject.productDetails = [];
							var productDetail = new Object();
							productDetail.price = originalPrice;
							if($(this).hasClass('unavailable')){
								productDetail.available = false;
							}else{
								productDetail.available = true;
							}
							productObject.productDetails.push(productDetail);
							productList.push(productObject);
						});	
					}
		            return productList;
		        }, i, j, colorCode, originalPrice);
		        item.products = item.products.concat(productList);
        	}
        }
        setTimeout(function(){
            callback(item);
            page.close();
        }, 10000);
    };

    page.open(url);
}