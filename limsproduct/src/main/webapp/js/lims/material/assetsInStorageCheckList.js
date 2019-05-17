var appId=$('#appId').val();
var contextPath = $("meta[name='contextPath']").attr("content");
$(function () {
    $.ajax({
        url: contextPath + "/lims/api/material/getInStorageCheckListInfo?appId="+appId,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        type: "post",
        async: false,
        success: function (data) {
            $.each(data.storageItemList, function (index, item) {
                var tr = $(['<tr >', '<td >' + item.name + '</td>', '<td>' + item.type + '</td>', '<td>' + item.unit + '</td>',  '<td>' + item.amount + '</td>', '<td>' + item.price + '</td>', '<td>' + item.totalPrice + '</td>','<td>' + item.invoiceNumber + '</td>','<td>' + item.info + '</td>', '</tr>'].join(''));
                $("#itemList").append(tr);
            });
            var trSum=$(['<tr >', '<td ></td>', '<td></td>','<td></td>', '<td></td>',  '<td></td>', '<td>共' + data.totalPrice + '</td>', '<td></td>','<td></td>', '</tr>'].join(''));
            $("#itemList").append(trSum);
            $("#supplier").html(data.supplier);
            $("#auditDate").html(data.auditDate);
            $("#auditUser").html(data.auditUser);
            $("#appUser").html(data.appUser);
        }
    });
});
var downPdf = document.getElementById("renderPdf");
downPdf.onclick=function() {
    var ui =document.getElementById("renderPdf");
    ui.style.display="none";
    html2canvas(document.body, {
        onrendered:function(canvas) {
            var contentWidth = canvas.width;
            var contentHeight = canvas.height;
            canvas.getContext("2d").translate(100,100);
            //一页pdf显示html页面生成的canvas高度;
            var pageHeight = contentWidth / 592.28 * 841.89;
            //未生成pdf的html页面高度
            var leftHeight = contentHeight;
            //pdf页面偏移
            var position = 0;
            //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
            var imgWidth = 595.28;
            var imgHeight = 592.28/contentWidth * contentHeight;

            var pageData = canvas.toDataURL('image/jpeg', 1.0);

            var pdf = new jsPDF('', 'pt', 'a4');

            //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
            //当内容未超过pdf一页显示的范围，无需分页
            if (leftHeight < pageHeight) {
                pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight );
            } else {
                while(leftHeight > 0) {
                    pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
                    leftHeight -= pageHeight;
                    position -= 841.89;
                    //避免添加空白页
                    if(leftHeight > 0) {
                        pdf.addPage();
                    }
                }
            }
            pdf.save('content.pdf');
        }
    })
};