//收放筛选条件
$(".tagbox_toggle_btn").click(
	function() {
		$(".tag_box").slideToggle(200);
		$(".tagbox_toggle_btn span").toggleClass("layui-hide");
	}
);

//筛选条件框中,单击复合二级条件,则该条件父栏目被选中
$(".tag_text .layui-tab-title li").removeClass("layui-this");

$(".tag_nav .layui-nav-child a").click(
	function() {
		$(this).parent().parent("dl").siblings("a").addClass("tag_nav_select");
		$(this).parent().parent().parent().parent().parent().siblings(".item_select").find(".layui-nav-item>a").removeClass("tag_nav_select");
		$(this).parent().parent().parent().parent().parent().siblings(".normal_tag").removeClass("layui-this");
		$(this).parent().parent().parent().parent().parent().siblings(".normal_tag").find("span").removeClass("layui-this");
		$(this).parent().parent().parent().parent().parent().siblings(".item_select").find(".layui-nav-child>dd").removeClass("layui-this");
	}
);

$(".tag_text .normal_tag").click(
	function() {
		$(this).find("span").addClass("layui-this");
		$(this).siblings(".normal_tag").removeClass("layui-this");
		$(this).siblings(".normal_tag").find("span").removeClass("layui-this");
		$(this).siblings(".item_select").find(".layui-nav-item>a").removeClass("tag_nav_select");
		$(this).siblings(".item_select").find(".layui-nav-item dd").removeClass("layui-this");
	}
);

$(".item_select").stop().click(
	function() {
		return false;
	}
);