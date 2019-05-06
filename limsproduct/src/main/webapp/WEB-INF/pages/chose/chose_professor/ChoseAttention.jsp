<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  <script type="text/javascript">
  $(document).ready(function(){
	});

  </script>
<script type="text/javascript">
</script>	
</head>
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师互选</a></li>
		<li class="end"><a href="javascript:void(0)">导师互选列表</a></li>
	  </ul>
	</div>
  </div>
  <div id="TabbedPanels1" class="TabbedPanels">
   <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <div class="title">
	  <div id="title">${choseDissertation.tittle }</div>
	  <!-- <a class="btn btn-new" onclick="agree();">添加导师</a> -->
	</div>
	<div class="tool-box">
		  <p>示例一：要求用户阅读完条款内容才能激活按钮</p>  
           <%-- <form action="#" method="post" name="agree">  
           <input type="submit" class="button" value="请认真查看<服务条款和声明> (30)" id="agree_btn" name="agreeb">  
           </form>  --%>
           <p id="">
           		${choseDissertation.content}
           </p>
           <input type="button" value="请认真查看<服务条款和声明> (20)" id="agree" name="agree" onclick="agree()"/>
	  </tbody>
	</table>
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<!-- 下拉框的js -->
	<script type="text/javascript">
	var secs = 20; 
	function update(num) {
		//alert(num);    
	    // 倒计时结束后  
	    if(num == secs) {  
	        // 按钮样式改为我同意，并且设置按钮为有效  
	       document.getElementById("agree").value =" 我 同 意 ";  
	        document.getElementById("agree").disabled=false;  
	    }  
	    else {  
	        // 计算倒计时还剩的时间，并显示出来  
	        var printnr = secs-num;  
	        document.getElementById("agree").value = "请认真查看<服务条款和声明> (" + printnr +")";  
	    }  
	}
	$(function(){
	 document.getElementById("agree").disabled=true;
	// 更新数字  
	// 开始倒计时，更新  
	for(var i=1;i<=20;i++) {
	    window.setTimeout("update("+i+")",i*1000);  
	}  
	})
	//我同意
   function agree(){       
         //将要所有要添加的数据传给后台处理   
         parent.window.location.reload();
         var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
         //parent.layer.close(index);
         //window.location.href="${pageContext.request.contextPath}/toAddBatch?i=1&currpage=1&themeId=${themeId}"; 
       	  window.location.href="${pageContext.request.contextPath}/nwuChose/newChoseAttentionRecord?themeId=${themeId}&type=${type}";
       }   
   </script>
</body>
</html>
