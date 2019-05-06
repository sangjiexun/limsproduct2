<link href="${pageContext.request.contextPath}/assets/js/wangEditor/css/wangEditor.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/assets/js/wangEditor/js/wangEditor.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/wangEditor/plupload/plupload.full.min.js"></script>

<script type="text/javascript">
	var wangEditor;

	$(function(){
		//获取dom节点
		var expressionArr = new Array();
        var path = '${pageContext.request.contextPath}/assets/js/wangEditor/expressions/'
        var fileNames = [1,60]// [1,100]
        var firstName = fileNames[0]
        var lastName = fileNames[1]
        var ext ='.gif'
	
        for(var i=1; i<=lastName; i++){
            expressionArr.push( path + i + ext );
        }		
		
	    var $uploadContainer = $('#uploadContainer'),
	        $fileList = $('#fileList'),
	        $btnUpload = $('#btnUpload');

	    wangEditor = $('.inputContent').wangEditor({
	    	uploadImgComponent: $uploadContainer,
	    	menuConfig: [
   	   	        ['bold', 'underline', 'italic', 'justify', 'list'],
   	   	        // ['insertExpression','bold', 'underline', 'italic', 'foreColor', 'strikethrough'],
   	   		    // ['blockquote', 'fontFamily', 'fontSize', 'setHead',  'justify'],
   	   		    // ['insertTable', 'insertImage'],
   	   		    // ['fullScreen']
   	   	    ],
   	   		expressions:expressionArr
	    });

	    //实例化一个上传对象
	    var uploader = new plupload.Uploader({
	        browse_button: 'btnBrowse',
	        url: '${pageContext.request.contextPath}/ueditor/jsp/imageUp.jsp', 
	        flash_swf_url: '${pageContext.request.contextPath}/assets/js/wangEditor/plupload/Moxie.swf',
	        sliverlight_xap_url: '${pageContext.request.contextPath}/assets/js/wangEditor/plupload/Moxie.xap',
	        filters: {
                    mime_types: [
                      //只允许上传图片文件 （注意，extensions中，逗号后面不要加空格）
                      { title: "图片文件", extensions: "jpg,gif,png,bmp,jpeg" }
                    ]
            }
	    });

	    //存储所有图片的url地址
	    var urls = [];

	    var event;

	    //初始化
	    uploader.init();

	    //绑定文件添加到队列的事件
	    uploader.bind('FilesAdded', function (uploader, files) {
	            //显示添加进来的文件名
	        $.each(files, function(key, value){
	            var fileName = value.name,
	                html = '<li>' + fileName + '</li>';
	            $fileList.append(html);
	        });
	    });

	    //单个文件上传之后
	    uploader.bind('FileUploaded', function (uploader, file, responseObject) {
	        //注意，要从服务器返回图片的url地址，否则上传的图片无法显示在编辑器中
	        var url = responseObject.response;
	        //先将url地址存储来，待所有图片都上传完了，再统一处理
	        urls.push(url);
	    });

	    //全部文件上传时候
	    uploader.bind('UploadComplete', function (uploader, files) {
	    	// 用 try catch 兼容IE低版本的异常情况
	    	try {
	    		//打印出所有图片的url地址
	    		$.each(urls, function (key, value) {
	    			wangEditor.command(event, 'insertHTML', '<img src="' + value + '"/>');
	    		});
	    	} catch (ex) {
	    		// 此处可不写
	    	} finally {
	    		//清空url数组
	    		urls = [];

	    		//清空显示列表
	    		$fileList.html('');
	    	}
	    });

	    //上传事件
	    $btnUpload.click(function(e){
	    	event = e;
	        uploader.start();
	    });
	});
</script>