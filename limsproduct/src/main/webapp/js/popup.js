/**
 * Created by Administrator on 2018/9/17.
 */
$(".popup_box").hide();
$(".popup_show").click(
    function(){
        $(".popup_box").show().ready(
            function(){
                $(".popup_content").slideDown(400);
            }
        );
    }
);
$(".popup_close").click(
    function(){
        $(".popup_content").slideUp().ready(
            function(){
                $(".popup_box").slideUp(400);
            }
        );
    }
);