var $content = $("#content_all");
if ($content.length > 0) {
	$content.html($content.siblings("textarea").text());

	var $catalog = $("#catalog_all");
	$catalog.html($catalog.siblings("textarea").text());
	$catalog.css("display", "block");

	var $detailall = $("#detail_all");
	$detailall.html($detailall.find("textarea").text());
	$detailall.find("a").each(function() {
		$(this).removeAttr("href").removeAttr("target");
	});
} else {
	if ($("#product-detail textarea").length > 0) {
		$("#product-detail").html($("textarea").text());
	}
}