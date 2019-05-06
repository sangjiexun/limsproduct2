<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="back_main" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lab_project.css" />
</head>
<body>    
   <div class="main_container cf rel w95p ma mb60">
        <div class="img_box">
        	<p class="more">
     <a class="a1 aml" href="${pageContext.request.contextPath}/basic_data/schoolDeviceUse?currpage=1">贵重仪器设备表填写</a>  
     <a   href="${pageContext.request.contextPath}/basic_data/labRoomReportBasic?currpage=1">实验室基本情况表填写</a>
     <a href="${pageContext.request.contextPath}/basic_data/labReportFund?currpage=1">实验室经费情况表填写</a> 	
      </p>
            <p class="triangle">
                <span>◥<sapn>
            </p>	   
               
  
  <div class="w95p ma">
						
	
	<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/basic_data/schoolDeviceUse?currpage=1">
		<table class="tab2 w100p font pb">
            		<tr>
            			<td class="w24p p0" >
            				<span class="l ml20 mt3">学年：</span>
            				 <select class="chzn-select l"  name="yearCode" style="width:200px">
							  <option value="">-------------请选择------------</option>
							  <c:forEach items="${schoolYearMap}" var="item">
							  		<option value="${item.key }">${item.value}</option>
				              </c:forEach>
				              </select>
            			</td>
            			<td class="w25p">
							<input class="btn r mr15" type="button" value="取消" onclick="cancel();">
							<input class="btn r mr5" type="submit" value="查询" >
            			</td>
            		</tr>
            	</table>
	</form:form>
	
   <table class="title w100p p5">
            		<tr>
            			
            		</tr>
            	</table>
    <div class="bg ptb5 mb15 overflow">
            			<table class="tab w97p font">
            <thead>
                 <tr>                   
                        <th rowspan="2" class="td-bg">学校代码</th>
                        <th rowspan="2" class="td-bg">仪器编号</th>
                        <th rowspan="2" class="td-bg">分类号</th>
                        <th rowspan="2" class="td-bg">仪器名称</th>
                        <th rowspan="2" class="td-bg">单价</th>
                        <th rowspan="2" class="td-bg">型号</th>
                        <th rowspan="2" class="td-bg">规格</th>
                        <th colspan="4" class="td-bg">使用机时</th>
                        <th rowspan="2" class="td-bg">测样数</th>
                        <th colspan="3" class="td-bg">培训人员数</th>
                        <th rowspan="2" class="td-bg">教学实验项目数</th>
                        <th rowspan="2" class="td-bg">科研项目数</th>
                        <th rowspan="2" class="td-bg">社会服务项目数</th>
                        <th colspan="2" class="td-bg">获奖情况</th>
                        <th colspan="2" class="td-bg">发明专利</th>
                        <th colspan="2" class="td-bg">论文情况</th>
                        <th rowspan="2" class="td-bg">负责人姓名</th>  
                        <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
                        <th rowspan="4" class="td-bg">操作</th>    
                        </sec:authorize>
                    </tr>
                        <tr>
                        <th class="td-bg">教学</th>
                        <th class="td-bg">科研</th>                     
                        <th class="td-bg">社会服务</th>
                        <th class="td-bg">其中开放使用机时</th>
                        <th class="td-bg">学生</th>
                        <th class="td-bg">教师</th>
                        <th class="td-bg">其他</th>
                        <th class="td-bg">国家级</th>
                        <th class="td-bg">省部级</th>                     
                        <th class="td-bg">教师</th>
                        <th class="td-bg">学生</th>
                        <th class="td-bg">三大检索</th>
                        <th class="td-bg">核心刊物</th>
                    </tr>          
            </thead> 
            <tbody>
						 <c:forEach items="${schoolDeviceValueLists}" var="curr" varStatus="i">
						<tr>
						 <td>${curr[0]}</td>
						 <td>${curr[1]}</td>
						 <td>${curr[2]}</td>
						 <td>${curr[3]}</td>
						 <td>${curr[4]}</td>
						 <td>${curr[5]}</td>
					     <td>${curr[6]}</td>
					     <td>${curr[7]}</td>
						 <td>${curr[8]}</td>
						 <td>${curr[9]}</td>
						 <td>${curr[10]}</td>
						 <td>${curr[11]}</td>
						 <td>${curr[12]}</td>
					     <td>${curr[13]}</td>
					     <td>${curr[14]}</td>
						 <td>${curr[15]}</td>
						 <td>${curr[16]}</td>
						 <td>${curr[17]}</td>
						 <td>${curr[18]}</td>
						 <td>${curr[19]}</td>
					     <td>${curr[20]}</td>
						 <td>${curr[21]}</td>
						 <td>${curr[22]}</td>
						 <td>${curr[23]}</td>
					     <td>${curr[24]}</td>		
					    <!--  <td> <button data-new class="btn r mr15">新建</button></td>	  -->
					    <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
					    <td>
					    <c:if test="${curr[27] eq null}">
					  <a href="${pageContext.request.contextPath}/basic_data/newSchoolDeviceValues?deviceNumber=${curr[1]}&yearId=${yearId}">填写</a>				
						 </c:if>
						 <c:if test="${curr[27] ne  null}">
					  <a href="${pageContext.request.contextPath}/basic_data/editSchoolDeviceValues?deviceNumber=${curr[1]}&yearId=${yearId}">编辑</a>				
						 </c:if>
						 </td>
						 </sec:authorize>
						 </tr>						
		     		</c:forEach>           
            </tbody>
    </table>
    </div>
    </div>
    </div>
    
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
	    //新建实验项目
 		$(document).ready(
	    	function (){ 
	    		   //获得编辑栏目的id
	    		  $('[data-new]').each(function(i,e){
	    		          $(e).on('click',function(){
	    		         layer.open({
	    		                    type: 2,
	    		                    title: '新建贵重仪器设备情况',
	    		                    maxmin: true,
	    		                    shadeClose: true, 
	    		                    area : ['800px' , '650px'],
	    		                    content: '${pageContext.request.contextPath}/basic_data/newSchoolDeviceValues?deviceNumber=${curr[1]}&yearId=${yearId}'
	    		                    })  
	    		      });                                              
	    		          $('[data-edit]').each(function(i,e){
	    				         $(e).on('click',function(){
	    				         layer.open({
	    		                    type: 2,
	    		                    title: '编辑实验项目',
	    		                    maxmin: true,
	    		                    shadeClose: true,                                                                               
	    		                    area : ['800px' , '650px'],
	    		                    content: '${pageContext.request.contextPath}/operation/editOperationItem?operationItemId='+$(e).attr('data-edit')+'&&isMine=1&&flagId=1&&orderBy=${orderBy}'	    		                                                                                                                                
	    		                    })  
	    		   		      });                                              
	    		   			});
	    		  });
	    		  });
	</script>
    </div>
    </body>