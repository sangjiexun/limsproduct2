$(document).ready(function () {
    $('#submitbtn').click(function(){
        $("form").ajaxSubmit(function(data){
            //window.parent.location.reload();
            window.location.reload();
            /*var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);*/
        })
    })
});