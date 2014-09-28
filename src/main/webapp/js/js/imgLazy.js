//京东
$("img[data-lazyload]").each(function() {
	var $this = $(this);
	$this.attr("src", $this.attr("data-lazyload"));
});
// 淘宝
$("img[data-ks-lazyload]").each(function() {
	var $this = $(this);
	$this.attr("src", $this.attr("data-ks-lazyload"));
});
// 苏宁
$("img[src2]").each(function() {
	var $this = $(this);
	$this.attr("src", $this.attr("src2"));
});
$("#product-detail a").each(function() {
	$(this).attr("href", "javascript:void(0)");
});
// 亚马逊
$("img[data-original]").each(function() {
	var $this = $(this);
	$this.attr("src", $this.attr("data-original"));
	$this.removeAttr("height");
});
// 国美
$("img[gome-src]").each(function() {
	var $this = $(this);
	$this.attr("src", $this.attr("gome-src"));
});