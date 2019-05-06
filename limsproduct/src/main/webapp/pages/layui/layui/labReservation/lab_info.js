layui.use(['form', 'layedit', 'laydate'], function() {
    var form = layui.form,
        layer = layui.layer,
        layedit = layui.layedit,
        laydate = layui.laydate;

    //日期范围
    laydate.render({
        elem: '#date-range'
        ,range: true
    });

    //时间范围
    laydate.render({
        elem: '#time-range'
        ,type: 'time'
        ,range: true
    });

    //表单初始赋值
    form.val('cappointment_tab', {
        "term": "默认显示当前学期"
    });

});

layui.use(['layer', 'form','element','jquery','layer'], function(){
    var layer = layui.layer
        ,form = layui.form
        ,element = layui.element
        ,$= layui.$
        ,layer=layui.layer;
    /*$("#section_box .layui-form-checkbox").each(function(i,j){
        j.onclick=function(){
            this.classList.toggle('layui-form-checked');
            // oldChecked toggle
            if(this.getAttribute('oldChecked')==null){
                this.setAttribute('oldChecked','');
            }else{
                this.removeAttribute('oldChecked');
            }
        }
    });*/
    // 节次
    refreshRule("section_box");
    // 周次
    refreshRule("week_box");
    /*$("#week_box .layui-form-checkbox").each(function(i,j){
        j.onclick=function(){
            this.classList.toggle('layui-form-checked');
            // oldChecked toggle
            if(this.getAttribute('oldChecked')==null){
                this.setAttribute('oldChecked','');
            }else{
                this.removeAttribute('oldChecked');
            }
        }
    });*/
    // 周次
    allRule('week_all','week_box');
    oppositeRule('week_opposite','week_box');
    noneRule('week_none','week_box');
    // 节次
    allRule('section_all','section_box');
    oppositeRule('section_opposite','section_box');
    noneRule('section_none','section_box');
    /*$("#week_all").click(function(){//周次全选
        $("#week_box .layui-form-checkbox").addClass('layui-form-checked');
    })

    $("#week_opposite").click(function(){//周次反选
        $("#week_box .layui-form-checkbox").toggleClass('layui-form-checked');
    })

    $("#week_none").click(function(){//周次全不选
        $("#week_box .layui-form-checkbox").removeClass('layui-form-checked');
    })

    // 节次
    $("#section_all").click(function(){//节次全选
        $("#section_box .layui-form-checkbox").addClass('layui-form-checked');
    })

    $("#section_opposite").click(function(){//节次反选
        $("#section_box .layui-form-checkbox").toggleClass('layui-form-checked');
    })

    $("#section_none").click(function(){//节次全不选
        $("#section_box .layui-form-checkbox").removeClass('layui-form-checked');
    })*/


});


<!-- 执行渲染, 把原始select美化~~ -->
<!-- 这里的weekend是xm-select的属性，是多选的ID, 如多处使用请保证全局唯一 -->
formSelects.render('weekend');

$(".breadcrumb_top a").click(
    function(){
        $(this).addClass("breadcrumb_select").siblings("a").removeClass("breadcrumb_select");
    }
);

<!-- 模式切换 -->
$(".date_item").hide();
$(".time_item").hide();

$(".section_btn").click(
    function(){
        $(".date_item").hide();
        $(".time_item").hide();
        $(".day_item").show();
        $(".week_item").show();
        $(".section_item").show();
    }
);

$(".date_btn").click(
    function(){
        $(".day_item").hide();
        $(".week_item").hide();
        $(".section_item").hide();
        $(".date_item").show();
        $(".time_item").show();
    }
);