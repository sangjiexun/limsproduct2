<!-- 这个是个页面，没有跳转，只能使用这样的方法调用清空cas的地址 -->
<iframe src="http://cas.xzhmu.edu.cn/cas/logout" style="display: none"></iframe>
<%-- <%
	String redirectURL = "${pageContext.request.contextPath}/cms/cindex";
%> --%>
<script type="text/javascript">
 function Logout(){
 	<%
 		//清空session
   		session.invalidate();
	%>
	//设置地址
 	var casLogoutURL = "http://cas.xzhmu.edu.cn/cas/login";
	var redirectURL = casLogoutURL+"?service=http://222.193.95.179/xzyxy/";
 	location = redirectURL;
 }
 //设置时间跳转
setInterval('Logout()',2000);  
</script>