function printOperationItem(operationItemId){
    jsonStr = "";
    totalCodes = 0;
    searchOperationItemPrint(operationItemId);
    var ePrintStr = "";

    var newT = "";
    for(var j=0; j<totalCodes; j++){
        //LODOP.NewPage();

        var schoolAcademy=jsonStr["schoolAcademy"];
        var year=jsonStr["year"];
        var month=jsonStr["month"];
        var subject12Name=jsonStr["subject12Name"];
        var day=jsonStr["day"];
        var majorFitNum=jsonStr["majorFitNum"];
        var majorFit=jsonStr["majorFit"];
        var lpCodeCustom=jsonStr["lpCodeCustom"];
        if(lpCodeCustom == null) {
            lpCodeCustom = "";
        }
        var lpName=jsonStr["lpName"];
        var totalHours1=jsonStr["totalHours1"];
        var totalHours2=jsonStr["totalHours2"];
        var totalHours3=jsonStr["totalHours3"];
        var subject12Num=jsonStr["subject12Num"];
        var labRoomName=jsonStr["labRoomName"];
        var labRoomNum=jsonStr["labRoomNum"];
        var majorName=jsonStr["majorName"];
        var majorNum=jsonStr["majorNum"];
        var courseName=jsonStr["courseName"];
        var courseNum=jsonStr["courseNum"];
        var totalHours=jsonStr["totalHours"];
        var totalPeoples=jsonStr["totalPeoples"];
        if(totalPeoples == null) {
            totalPeoples = ""
        }
        var studentNum=jsonStr["studentNum"];
        var setNum=jsonStr["setNum"];
        var groupNum=jsonStr["groupNum"];
        var cycNum=jsonStr["cycNum"];
        if(cycNum == null) {
            cycNum = "";
        }
        var nature=jsonStr["nature"];
        var natureName="";
        if(nature == 1) {
            natureName="&nbsp;&nbsp;&nbsp;■课内实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□独立设置实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□集中实践&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□实训&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□实习";
        } else if(nature == 2) {
            natureName="&nbsp;&nbsp;&nbsp;□课内实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■独立设置实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□集中实践&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□实训&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□实习";
        } else if(nature == 3) {
            natureName="&nbsp;&nbsp;&nbsp;□课内实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□独立设置实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■集中实践&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□实训&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□实习";
        } else if(nature == 4) {
            natureName="&nbsp;&nbsp;&nbsp;□课内实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□独立设置实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□集中实践&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■实训&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□实习";
        } else if(nature == 5) {
            natureName="&nbsp;&nbsp;&nbsp;□课内实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□独立设置实验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□集中实践&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□实训&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■实习";
        }
        var main=jsonStr["main"];
        var mainName="";
        if(main == 1) {
            mainName="&nbsp;&nbsp;&nbsp;■基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□科研<br>&nbsp;&nbsp;&nbsp;□毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□社会服务";
        } else if(main == 2) {
            mainName="&nbsp;&nbsp;&nbsp;□基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□科研<br>&nbsp;&nbsp;&nbsp;□毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□社会服务";
        } else if(main == 3) {
            mainName="&nbsp;&nbsp;&nbsp;□基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□科研<br>&nbsp;&nbsp;&nbsp;□毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□社会服务";
        } else if(main == 4) {
            mainName="&nbsp;&nbsp;&nbsp;□基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□科研<br>&nbsp;&nbsp;&nbsp;□毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□社会服务";
        } else if(main == 5) {
            mainName="&nbsp;&nbsp;&nbsp;□基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□科研<br>&nbsp;&nbsp;&nbsp;□毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□社会服务";
        } else if(main == 6) {
            mainName="&nbsp;&nbsp;&nbsp;□基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■科研<br>&nbsp;&nbsp;&nbsp;□毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□社会服务";
        } else if(main == 7) {
            mainName="&nbsp;&nbsp;&nbsp;□基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□科研<br>&nbsp;&nbsp;&nbsp;■毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□社会服务";
        } else if(main == 8) {
            mainName="&nbsp;&nbsp;&nbsp;□基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□科研<br>&nbsp;&nbsp;&nbsp;□毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□社会服务";
        } else if(main == 9) {
            mainName="&nbsp;&nbsp;&nbsp;□基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□科研<br>&nbsp;&nbsp;&nbsp;□毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□社会服务";
        } else if(main == 10) {
            mainName="&nbsp;&nbsp;&nbsp;□基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业基础&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□专业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□生产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□科研<br>&nbsp;&nbsp;&nbsp;□毕业论文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□毕业设计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□技术开发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■社会服务";
        }
        var app=jsonStr["app"];
        var appName="";
        if(app == 1) {
            appName="&nbsp;■演示性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□验证性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□综合性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□设计研究性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□网络实验";
        } else if(app == 2) {
            appName="&nbsp;□演示性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■验证性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□综合性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□设计研究性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□网络实验";
        } else if(app == 3) {
            appName="&nbsp;□演示性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□验证性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■综合性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□设计研究性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□网络实验";
        } else if(app == 4) {
            appName="&nbsp;□演示性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□验证性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□综合性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■设计研究性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□网络实验";
        } else if(app == 5) {
            appName="&nbsp;□演示性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□验证性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□综合性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□设计研究性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□网络实验";
        } else if(app == 6) {
            appName="&nbsp;□演示性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□验证性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□综合性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□设计研究性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■网络实验";
        }
        var operator=jsonStr["operator"];
        var operatorName="";
        if(operator == 1) {
            operatorName = "&nbsp;■博士生&nbsp;&nbsp;&nbsp;□硕士生&nbsp;&nbsp;&nbsp;□本科生&nbsp;&nbsp;&nbsp;□专科生&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;□工程与实验技术&nbsp;&nbsp;&nbsp;□研究人员&nbsp;&nbsp;&nbsp;□教师";
        } else if(operator == 2) {
            operatorName = "&nbsp;□博士生&nbsp;&nbsp;&nbsp;■硕士生&nbsp;&nbsp;&nbsp;□本科生&nbsp;&nbsp;&nbsp;□专科生&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;□工程与实验技术&nbsp;&nbsp;&nbsp;□研究人员&nbsp;&nbsp;&nbsp;□教师";
        } else if(operator == 3) {
            operatorName = "&nbsp;□博士生&nbsp;&nbsp;&nbsp;□硕士生&nbsp;&nbsp;&nbsp;■本科生&nbsp;&nbsp;&nbsp;□专科生&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;□工程与实验技术&nbsp;&nbsp;&nbsp;□研究人员&nbsp;&nbsp;&nbsp;□教师";
        } else if(operator == 4) {
            operatorName = "&nbsp;□博士生&nbsp;&nbsp;&nbsp;□硕士生&nbsp;&nbsp;&nbsp;□本科生&nbsp;&nbsp;&nbsp;■专科生&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;□工程与实验技术&nbsp;&nbsp;&nbsp;□研究人员&nbsp;&nbsp;&nbsp;□教师";
        } else if(operator == 5) {
            operatorName = "&nbsp;□博士生&nbsp;&nbsp;&nbsp;□硕士生&nbsp;&nbsp;&nbsp;□本科生&nbsp;&nbsp;&nbsp;□专科生&nbsp;&nbsp;&nbsp;■其它&nbsp;&nbsp;&nbsp;□工程与实验技术&nbsp;&nbsp;&nbsp;□研究人员&nbsp;&nbsp;&nbsp;□教师";
        } else if(operator == 6) {
            operatorName = "&nbsp;□博士生&nbsp;&nbsp;&nbsp;□硕士生&nbsp;&nbsp;&nbsp;□本科生&nbsp;&nbsp;&nbsp;□专科生&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;■工程与实验技术&nbsp;&nbsp;&nbsp;□研究人员&nbsp;&nbsp;&nbsp;□教师";
        } else if(operator == 7) {
            operatorName = "&nbsp;□博士生&nbsp;&nbsp;&nbsp;□硕士生&nbsp;&nbsp;&nbsp;□本科生&nbsp;&nbsp;&nbsp;□专科生&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;□工程与实验技术&nbsp;&nbsp;&nbsp;■研究人员&nbsp;&nbsp;&nbsp;□教师";
        } else if(operator == 8) {
            operatorName = "&nbsp;□博士生&nbsp;&nbsp;&nbsp;□硕士生&nbsp;&nbsp;&nbsp;□本科生&nbsp;&nbsp;&nbsp;□专科生&nbsp;&nbsp;&nbsp;□其它&nbsp;&nbsp;&nbsp;□工程与实验技术&nbsp;&nbsp;&nbsp;□研究人员&nbsp;&nbsp;&nbsp;■教师";
        }
        var changeStatus=jsonStr["changeStatus"];
        var changeStatusName="";
        if(changeStatus == 1) {
            changeStatusName = "&nbsp;■未变动&nbsp;&nbsp;&nbsp;□改进&nbsp;&nbsp;&nbsp;□新开&nbsp;&nbsp;&nbsp;□撤销&nbsp;&nbsp;&nbsp;□未开";
        } else if(changeStatus == 2) {
            changeStatusName = "&nbsp;□未变动&nbsp;&nbsp;&nbsp;■改进&nbsp;&nbsp;&nbsp;□新开&nbsp;&nbsp;&nbsp;□撤销&nbsp;&nbsp;&nbsp;□未开";
        } else if(changeStatus == 3) {
            changeStatusName = "&nbsp;□未变动&nbsp;&nbsp;&nbsp;□改进&nbsp;&nbsp;&nbsp;■新开&nbsp;&nbsp;&nbsp;□撤销&nbsp;&nbsp;&nbsp;□未开";
        } else if(changeStatus == 4) {
            changeStatusName = "&nbsp;□未变动&nbsp;&nbsp;&nbsp;□改进&nbsp;&nbsp;&nbsp;□新开&nbsp;&nbsp;&nbsp;■撤销&nbsp;&nbsp;&nbsp;□未开";
        } else if(changeStatus == 5) {
            changeStatusName = "&nbsp;□未变动&nbsp;&nbsp;&nbsp;□改进&nbsp;&nbsp;&nbsp;□新开&nbsp;&nbsp;&nbsp;□撤销&nbsp;&nbsp;&nbsp;■未开";
        }
        var pub=jsonStr["pub"];
        var pubName="";
        if(pub == 1) {
            pubName="&nbsp;■校内开放&nbsp;&nbsp;□校外开放";
        } else if(pub == 2) {
            pubName="&nbsp;□校内开放&nbsp;&nbsp;■校外开放";
        }
        var reward=jsonStr["reward"];
        var rewardName="";
        if(reward == 1) {
            rewardName = "&nbsp;■未获奖&nbsp;&nbsp;&nbsp;□国际奖&nbsp;&nbsp;&nbsp;□国家级奖&nbsp;&nbsp;&nbsp;□省级奖&nbsp;&nbsp;&nbsp;□校级奖";
        } else if(reward == 2) {
            rewardName = "&nbsp;□未获奖&nbsp;&nbsp;&nbsp;■国际奖&nbsp;&nbsp;&nbsp;□国家级奖&nbsp;&nbsp;&nbsp;□省级奖&nbsp;&nbsp;&nbsp;□校级奖";
        } else if(reward == 3) {
            rewardName = "&nbsp;□未获奖&nbsp;&nbsp;&nbsp;□国际奖&nbsp;&nbsp;&nbsp;■国家级奖&nbsp;&nbsp;&nbsp;□省级奖&nbsp;&nbsp;&nbsp;□校级奖";
        } else if(reward == 4) {
            rewardName = "&nbsp;□未获奖&nbsp;&nbsp;&nbsp;□国际奖&nbsp;&nbsp;&nbsp;□国家级奖&nbsp;&nbsp;&nbsp;■省级奖&nbsp;&nbsp;&nbsp;□校级奖";
        } else if(reward == 5) {
            rewardName = "&nbsp;□未获奖&nbsp;&nbsp;&nbsp;□国际奖&nbsp;&nbsp;&nbsp;□国家级奖&nbsp;&nbsp;&nbsp;□省级奖&nbsp;&nbsp;&nbsp;■校级奖";
        }
        var require=jsonStr["require"];
        var requireName="";
        if(require == 1) {
            requireName="&nbsp;■必修&nbsp;&nbsp;&nbsp;&nbsp;□选修&nbsp;&nbsp;&nbsp;&nbsp;□其它";
        } else if(require == 2) {
            requireName="&nbsp;□必修&nbsp;&nbsp;&nbsp;&nbsp;■选修&nbsp;&nbsp;&nbsp;&nbsp;□其它";
        } else if(require == 3) {
            requireName="&nbsp;□必修&nbsp;&nbsp;&nbsp;&nbsp;□选修&nbsp;&nbsp;&nbsp;&nbsp;■其它";
        }
        var majorFit=jsonStr["majorFit"];
        var introduction=jsonStr["introduction"];
        var guideBook=jsonStr["guideBook"];
        var guideBookName="";
        if(guideBook == 1) {
            guideBookName="&nbsp;■购置&nbsp;&nbsp;□自编";
        } else if(guideBook == 2) {
            guideBookName="&nbsp;□购置&nbsp;&nbsp;■自编";
        }
        var guideBookTitle=jsonStr["guideBookTitle"];
        var guideBookAuthor=jsonStr["guideBookAuthor"];
        var assessment=jsonStr["assessment"];
        var teacherSpeaker=jsonStr["teacherSpeaker"];
        var teacherSpeakerName = "";
        if(teacherSpeaker == null || teacherSpeaker == "") {
            teacherSpeakerName="&nbsp;□指导教师：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        } else {
            teacherSpeakerName="&nbsp;■指导教师：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+teacherSpeaker;
        }
        var teacherAssistant=jsonStr["teacherAssistant"];

        var teacherAssistantName = "";
        if(teacherAssistant == null || teacherAssistant == "") {
            teacherAssistantName="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□辅导教师：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        } else {
            teacherAssistantName="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;■辅导教师：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+teacherAssistant;
        }
        var preparation=jsonStr["preparation"];

        var strBodyStyle="<style>table,th{border:none;height:18px} td{border: 1px solid #000;height:23px}</style>";


        strHTML=strBodyStyle + "<table border=0 cellSpacing=0 cellPadding=0  width='100%' height='200' bordercolor='#000000' style='border-collapse:collapse'>";

        strHTML=strHTML + "<thead><tr>";
        strHTML=strHTML + "<th colspan=8 align=center style='line-height:30px;'><b><font face='黑体' size='5'>上&nbsp;海&nbsp;建&nbsp;桥&nbsp;学&nbsp;院&nbsp;实&nbsp;验&nbsp;项&nbsp;目&nbsp;信&nbsp;息&nbsp;卡</font></b></th></tr></thead>";

        strHTML=strHTML + "<tbody>"
        strHTML=strHTML + "<tr style='border:none;height:36px'><td colspan=8 style='border:none;height:36px'>&nbsp;<br>&nbsp;<br></td></tr>";
        strHTML=strHTML + "<tr style='border:none;height:80px'><td colspan=4 style='border:none;'>&nbsp;学院（系）："+schoolAcademy+"</td><td colspan=4 style='border:none;' align=right>填表日期："+year+"&nbsp年"+month+"&nbsp月"+day+"&nbsp;日&nbsp;</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px; width:12%'><b>实验编号</b></td><td style='width:13%'>"+lpCodeCustom +"</td><td style='width:12%' align='center'><b>实验名称</b></td><td colspan=5>"+lpName +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>所属学科</b></td><td colspan=2>"+subject12Num +"</td><td style='width:13%' align='center'><b>所在实验室</b></td><td colspan=2>"+labRoomName +"</td><td style='width:12%' align='center'><b>实验室编号</b></td><td style='width:13%'>"+labRoomNum +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>专业名称</b></td><td colspan=3>"+majorName +"</td><td align='center'><b>专业分类号</b></td><td colspan=3>"+majorNum +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>课程名称</b></td><td colspan=3>"+courseName +"</td><td align='center'><b>课程编号</b></td><td colspan=3>"+courseNum +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>实际学时数</b></td><td colspan=3>"+totalHours +"</td><td align='center'><b>计划学年总人数</b></td><td colspan=3>"+totalPeoples +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>实验者人数</b></td><td>"+studentNum +"</td><td align='center'><b>实验套数</b></td><td>"+setNum +"</td><td align='center'><b>每组人数</b></td><td style='width:13%'>"+groupNum +"</td><td align='center'><b>循环次数</b></td><td>"+cycNum +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>实验性质</b></td><td colspan=7 style='text-align: left;'>"+natureName +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:50px;'><b>实验类别</b></td><td colspan=7 style='text-align: left;'>"+mainName +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>实验类型</b></td><td colspan=7 style='text-align: left;'>"+appName +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>实验者类型</b></td><td colspan=7 style='text-align: left;'>"+operatorName +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>变动状态</b></td><td colspan=4 style='text-align: left;'>"+changeStatusName +"</td><td style='line-height:36px;'><b>开放实验</b></td><td colspan=2 style='text-align: left;'>"+pubName+"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>获奖等级</b></td><td colspan=4 style='text-align: left;'>"+rewardName +"</td><td style='line-height:36px;'><b>实验要求</b></td><td colspan=2 style='text-align: left;'>"+requireName+"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>面向专业</b></td><td colspan=7 style='text-align: left;'>&nbsp;&nbsp;&nbsp;"+majorFit +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:60px;'><b>实验项目<br>简介</b></td><td colspan=7 style='text-align: left'>原理、方法、目的（100个汉字）：<br>"+introduction+"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>实验指导书</b></td><td style='text-align: left'>"+guideBookName +"</td><td style='line-height:36px;'><b>指导书名称</b></td><td colspan=3>"+guideBookTitle+"</td><td style='line-height:36px;'><b>编者</b></td><td>"+guideBookAuthor+"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>考核方法</b></td><td colspan=7 style='text-align: left'>&nbsp;&nbsp;&nbsp;"+assessment +"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>开课教师</b></td><td colspan=7 style='text-align: left'>"+teacherSpeakerName+teacherAssistantName+"</td></tr>";
        strHTML=strHTML + "<tr align='center'><td style='line-height:36px;'><b>课前准备</b></td><td colspan=7 style='text-align: left'>&nbsp;&nbsp;&nbsp;"+preparation +"</td></tr>";
        strHTML=strHTML + "<tr><td colspan=2 style='border:none;' align=left>批准：</td><td colspan=4 style='border:none;' align=center>审核：</td><td colspan=2 style='border:none;' align=center>编制：</td></tr>";
        strHTML=strHTML + "</tbody>"

        newT +="<caption>上海建桥学院实验项目信息卡</caption>"
        newT +="<tr>"
        newT +="<td colspan='4' style='text-align:left;border:0px'>学院（系）："+schoolAcademy+"</td>"
        newT +="<td colspan='4' style='text-align:right;border:0px'>填表日期："+year+"&nbsp年"+month+"&nbsp月"+day+"&nbsp;日&nbsp;</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>实验编号</th>"
        newT +="<td>"+lpCodeCustom +"</td>"
        newT +="<th>实验名称</th>"
        newT +="<td colspan='5'>"+lpName +"</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>所属学科</th>"
        newT +="<td colspan='1'>"+subject12Name +"</td>"
        newT +="<th>所属学科编号</th>"
        newT +="<td colspan='1'>"+subject12Num +"</td>"
        newT +="<th>所在实验室</th>"
        newT +="<td colspan='1'>"+labRoomName +"</td>"
        newT +="<th>实验室编号</th>"
        newT +="<td>"+labRoomNum+"</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>所属专业名称</th>"
        newT +="<td colspan='3'>"+majorName +"</td>"
        newT +="<th>专业分类号</th>"
        newT +="<td colspan='3'>"+majorNum +"</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>课程名称</th>"
        newT +="<td colspan='3'>"+courseName +"</td>"
        newT +="<th>课程编号</th>"
        newT +="<td colspan='3'>"+courseNum +"</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>实际总学时</th>"
        newT +="<td colspan='1'>"+totalHours1 +"</td>"
        newT +="<th>实验学时</th>"
        newT +="<td colspan='1'>"+totalHours2 +"</td>"
        newT +="<th>课程总学时</th>"
        newT +="<td colspan='3'>"+totalHours3 +"</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th style='width:12.5%'>实验者人数</th>"
        newT +="<td style='width:12.5%'>"+studentNum +"</td>"
        newT +="<th style='width:12.5%'>实验套数</th>"
        newT +="<td style='width:12.5%'>"+setNum +"</td>"
        newT +="<th style='width:12.5%'>每组人数</th>"
        newT +="<td  colspan='3' style='width:12.5%'>"+groupNum +"</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>实验性质</th>"
        newT +="<td colspan='7'>"
        newT +=natureName

        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>实验类别</th>"
        newT +="<td colspan='7'>"
        newT +=mainName

        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>实验类型</th>"
        newT +="<td colspan='7'>"
        newT +=appName
        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>实验者类型</th>"
        newT +="<td colspan='7'>"
        newT +=operatorName

        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>变动状态</th>"
        newT +="<td colspan='3'>"
        newT +=changeStatusName

        newT +="</td>"
        newT +="<th>开放实验</th>"
        newT +="<td colspan='3'>"
        newT +=pubName
        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>获奖等级</th>"
        newT +="<td colspan='3'>"
        newT +=rewardName
        newT +="</td>"
        newT +="<th>实验要求</th>"
        newT +="<td colspan='3'>"
        newT +=requireName
        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>面向专业</th>"
        newT +="<td colspan='2'>"
        newT +=majorFit
        newT +="</td>"
        newT +="<th>面向专业编号</th>"
        newT +="<td colspan='4'>"
        newT +=majorFitNum
        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>实验项目<br>介绍</th>"
        newT +="<td colspan='7'>"
        newT +="原理方法目的（100个汉字）"
        newT +="统计量的描述"+"<br>"+introduction

        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>实验指导书</th>"
        newT +="<td>"
        newT +=guideBookName
        newT +="</td>"
        newT +="<th>指导书名称</th>"
        newT +="<td colspan='2'>"+guideBookTitle+"</td>"
        newT +="<th>编者</th>"
        newT +="<td colspan='2'>"+guideBookAuthor+"</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>考核方法</th>"
        newT +="<td colspan='7'>"
        newT +=assessment
        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>开课教师</th>"
        newT +="<td colspan='7'>"
        newT +=teacherSpeakerName+teacherAssistantName
        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<th>课前准备</th>"
        newT +="<td colspan='7'>"
        newT +=preparation
        newT +="</td>"
        newT +="</tr>"
        newT +="<tr>"
        newT +="<td style='border:0px'>批准：</td> "
        newT +="<td colspan='2' style='border:0px'></td> "
        newT +="<td style='border:0px'> 审核：</td> "
        newT +="<td colspan='2' style='border:0px'></td> "
        newT +="<td style='border:0px'>编制：</td> "
        newT +="<td style='border:0px'></td> "
        newT +="</tr>"

        $("#toPrint").html("")
        $("#toPrint").append(newT)

        $("#toPrint").jqprint({
            debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
            importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
            printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
            operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
        });
    }

    // 查找打印信息
    function searchOperationItemPrint(operationItemId){
        $.ajax({
            type: 'POST',
            url: '../operation/operationItemPrint?idKey='+operationItemId,
            async: false,

            dataType:'json',
            success:function(json){
                jsonStr = json;
                totalCodes = json.totalCodes;
            }
        });
    }


}