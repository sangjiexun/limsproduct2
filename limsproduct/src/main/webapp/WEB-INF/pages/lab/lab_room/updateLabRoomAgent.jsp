 <%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
 <jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
 
 <html >  
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

<style type="text/css">
.select-box{overlow:hidden;}
.left-box,.right-box{float:left;
margin-right:15px;}
.right-box a{color:#333;
	font-size:12px;
	}
.right-box{width:250px;
	border:1px solid #333;}
.right-box select{width:250px;
	overflow:scroll;}
.select-action a{color:#333;
	text-decoration:none;}
.chzn-container{width: 200px;}
.chzn-container ,#build_chzn{width:100%;}
.chzn-container{width:100% !important;}

</style>
	<script type="text/javascript">
        function changeDoor() {
            // if ($(obj).options.selected(0)){
            //    console.log(1);
            // }
            var count = 0;
            if($("#harewareModule").val() == "1拖1"){
                count = 1;
            }
            if($("#harewareModule").val() == "1拖4"){
                count = 4;
            }
            if($("#harewareModule").val() == "1拖8"){
                count = 8;
            }
            var doorindex = document.getElementById("doorindex");
            doorindex.options.length = 0;
            doorindex.add(new Option("请选择", "", true, true));
            for(var i = 1; i <= count; i++){
                doorindex.add(new Option(i, i, false, false));
            }

            // 关键点：触发 select 的 `chosen:updated` 事件
            $("#doorindex").trigger('liszt:updated');
            $("#doorindex").chosen();
        }
	</script>
</head>
<body>
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<div class="title">
				<div id="title">修改物联硬件</div>
				
				</div> 
<form:form action="${pageContext.request.contextPath}/labRoom/saveLabRoomAgent?roomId=${agent.labRoom.id}&type=${type}" method="POST" modelAttribute="agent">
		<div class="new-classroom">
					<fieldset>
					<form:hidden path="id"/>
						    <label>硬件名称：</label>
						       	<%-- <form:hidden path="id"/>
							   	<form:select path="CAgentType.id" class="chzn-select">
								<form:options items="${types}" itemLabel="name" itemValue="id"/>
								</form:select> --%>
							   	<form:select path="CDictionary.id" class="chzn-select">
								<form:options items="${types}" itemLabel="CName" itemValue="id"/>
								</form:select>
							</td>
					</fieldset>
					<fieldset>
						    <label>Ip：</label>
							   	<form:input path="hardwareIp" />
					</fieldset>
			        <fieldset>
				       <label>规格：</label>
						<form:select id="harewareModule" path="harewareModule"  class="chzn-select" onchange="changeDoor()">
							<form:option value="">请选择</form:option>
							<form:option value="1拖1">1拖1</form:option>
							<form:option value="1拖4">1拖4</form:option>
							<form:option value="1拖8">1拖8</form:option>
						</form:select>
					</fieldset>
			        <fieldset>
				     <label>设备号(门号)：：</label>
						<form:select id="doorindex" path="doorindex" class="chzn-select">
							<form:option value="">请选择</form:option>
							<form:option value="1">1</form:option>
							<form:option value="2">2</form:option>
							<form:option value="3">3</form:option>
							<form:option value="4">4</form:option>
							<form:option value="5">5</form:option>
							<form:option value="6">6</form:option>
							<form:option value="7">7</form:option>
							<form:option value="8">8</form:option>
						</form:select>
			       </fieldset>
					<fieldset>
						    <label>制造商：</label>
						<form:select path="manufactor" class="chzn-select">
							<form:option value="">请选择</form:option>
							<form:option value="adkfp">adkfp</form:option>
							<form:option value="aopu">aopu</form:option>
							<form:option value="gvsun">gvsun</form:option>
							<form:option value="wiegand">wiegand</form:option>
						</form:select>
					</fieldset>
					<fieldset>
						    <label>SN/电表号：</label>
							   	<form:input path="snNo" />
					</fieldset>
					<c:if test="${agent.CDictionary.id eq 548}">
						<fieldset>
							<label>门号：</label>
							<form:select path="doorindex" class="chzn-select">
								<form:option value="1">1号门</form:option>
								<form:option value="2">2号门</form:option>
								<form:option value="3">3号门</form:option>
								<form:option value="4">4号门</form:option>
							</form:select>
						</fieldset>
						<fieldset>
							<label>version：</label>
							<form:input path="hardwareVersion" />
						</fieldset>
					</c:if>
					<fieldset>
						    <label>物联服务器：</label>
							   	<form:select path="commonServer.id" class="chzn-select">
								<form:options items="${serverList}" itemLabel="serverName" itemValue="id"/>
								</form:select>
					</fieldset>
					
		</div>	 
			<div class="moudle_footer">
			        <div class="submit_link">
			          <input class="btn" id="save" type="submit" value="确定">
					<input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
			        </div>
			    </div>
		</table>
		
		<!-- 下拉框的js -->
					<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
					<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
					<script type="text/javascript">
						    var config = {
						      '.chzn-select': {search_contains : true},
						      '.chzn-select-deselect'  : {allow_single_deselect:true},
						      '.chzn-select-no-single' : {disable_search_threshold:10},
						      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
						      '.chzn-select-width'     : {width:"95%"}
						    }
						    for (var selector in config) {
						      $(selector).chosen(config[selector]);
						    }
						</script>
					<!-- 下拉框的js -->	
		
	</form:form>

  
     
     </div>
		</div>
		</div>
	</div>
</div>  

</body>
</html>