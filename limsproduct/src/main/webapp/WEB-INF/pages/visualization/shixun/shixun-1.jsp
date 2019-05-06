<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!doctype html>
<html>
<head>
	<meta name="decorator" content="iframe"/>
    <title>实训楼1层</title>
	    
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/visualization/shixun/floorview.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/visualization/shixun/istyle-Rmenu.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/visualization/shixun/allpages.css"/>
</head>

<body >
    <div class="htt"><img src="${pageContext.request.contextPath}/images/visualization/shixun/ttt.png"></div>
	<div id="conteiner">
        <div id="viewbox">
        <div id="menubox">
            <div class="istyle-Rmenu">
                <div class="Rmenu-list">
                    <span class="unlist">楼层信息</span>
                    <div>
                        <div class="titlelee">实训楼</div>
                    </div>
                </div>
                <div class="Rmenu-list">
                    <span class="unlist">切换楼层</span>
                    <div>

                        <div class="unlist">
                            <a href="${pageContext.request.contextPath}/visualization/shixunBuildingThreeFloor">点击查看三层</a>
                      </div>
                        <div class="unlist">    
                            <a href="${pageContext.request.contextPath}/visualization/shixunBuildingTwoFloor">点击查看二层</a>
                      </div>
                        <div class="actlist">
                            <a href="${pageContext.request.contextPath}/visualization/shixunBuildingOneFloor">点击查看一层</a>
                        </div>

                    </div>
                </div>
                <div class="Rmenu-list">
                    <span class="unlist">查看选项</span>
                     <div id="buttonbox">
                        <div class="actlist" onClick="shown(0)">查看分布地图</div>
                        <div class="unlist" onClick="shown(1)">查看实训室利用率</div>
                        <!-- <div class="unlist" onClick="shown(2)">查看实验项开出率</div>
                        <div class="unlist" onClick="shown(3)">查看综合实验比率</div>
                        <div class="unlist" onClick="shown(4)">查看大型设备机时率</div>
                        <div class="unlist" onClick="shown(5)">查看大型设备利用率</div>
                        <div class="unlist" onClick="shown(6)">查看教师参与率率</div>
                        <div class="unlist" onClick="shown(7)">查看人才培养率</div> -->
                    </div>
                </div>
            </div>
        </div>
        <img id="useper" src="${pageContext.request.contextPath}/images/visualization/shixun/useper.png"/>
       	  <img class="backgroundimg" src="${pageContext.request.contextPath}/images/visualization/shixun/01.png"/>
          





           <div class="roomdiv" u0="0" u1="0" u2="1" u3="0.1" u4="0.7" u5="0.66" u6="0.74">
            	<div class="roomtitle">113-111</div>
            	<img class="roomimgy" style="left: 808px; top: 131px;" src="${pageContext.request.contextPath}/images/visualization/shixun/113-111-2.png">
            	<img class="roomimg" style="left: 808px; top: 131px;" src="${pageContext.request.contextPath}/images/visualization/shixun/113-111-1.png">
                
                <div class="roomtext">
                	<div>
                    	<div class="sh">
							<span>编号：</span><br/>
							<span>名称：</span><br/>
							<span>使用面积：</span><br/>
							<span>实训室容量：</span><br/>
							<span>实训室利用率：</span><br/>
                        </div>
					</div>
                </div>
            </div>

           <div class="roomdiv" u0="0" u1="0" u2="1" u3="0.1" u4="0.7" u5="0.66" u6="0.74">
            	<div class="roomtitle">115</div>
            	<img class="roomimgy" style="left: 608px; top: 90px;" src="${pageContext.request.contextPath}/images/visualization/shixun/115-2.png">
            	<img class="roomimg" style="left: 608px; top: 90px;" src="${pageContext.request.contextPath}/images/visualization/shixun/115-1.png">
                
                <div class="roomtext">
                	<div>
                    	<div class="sh">
							<span>编号：</span><br/>
							<span>名称：</span><br/>
							<span>使用面积：</span><br/>
							<span>实训室容量：</span><br/>
							<span>实训室利用率：</span><br/>
                        </div>
					</div>
                </div>
            </div>






			
           <div class="roomdiv" u0="0" u1="0" u2="1" u3="0.1" u4="0.7" u5="0.66" u6="0.74">
            	<div class="roomtitle" style="left:550px !important;">117</div>
            	<img class="roomimgy" style="left: 469px; top: 73px;" src="${pageContext.request.contextPath}/images/visualization/shixun/117-2.png">
            	<img class="roomimg" style="left: 469px; top: 73px;" src="${pageContext.request.contextPath}/images/visualization/shixun/117-1.png">
                
                <div class="roomtext">
                	<div>
                    	<div class="sh">
							<span>编号：</span><br/>
							<span>名称：</span><br/>
							<span>使用面积：</span><br/>
							<span>实训室容量：</span><br/>
							<span>实训室利用率：</span><br/>
                        </div>
					</div>
                </div>
            </div>


            
            
            <div class="roomdiv" u0="0" u1="${labRoom119.labRoomPhone}" u2="1" u3="0.1" u4="0.7" u5="0.66" u6="0.74">
           	  <div class="roomtitle"><a href="${pageContext.request.contextPath}/visualization/labRoomDetail?id=${labRoom119.id}" target="_blank">119</a></div>
            	<img class="roomimgy" style="left: 406px; top: 63px;" src="${pageContext.request.contextPath}/images/visualization/shixun/119-2.png">
            	<img class="roomimg" style="left: 406px; top: 63px;" src="${pageContext.request.contextPath}/images/visualization/shixun/119-1.png">
                
                <div class="roomtext">
                	<div>
                    	<div class="sh">
							<span>编号：${labRoom119.labRoomNumber}</span><br/>
							<span>名称：${labRoom119.labRoomName}</span><br/>
							<span>使用面积：${labRoom119.labRoomArea}平方米</span><br/>
							<span>实训室容量：${labRoom119.labRoomCapacity}人</span><br/>
							<span>实训室利用率：${labRoom119.labRoomPhone}%</span><br/>
                        </div>
				  </div>
              </div>
            </div>
            
    </div>
    </div>
    
<script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/shixun/istyle-Rmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/shixun/floor.js"></script>
</body>
</html>
