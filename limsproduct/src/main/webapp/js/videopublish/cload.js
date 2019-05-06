
	/*	var speed=30; //数字越大速度越慢
		var tab=document.getElementById('demo');
		var tab1=document.getElementById('demo1');
		var tab2=document.getElementById('demo2');
		tab2.innerHTML=tab1.innerHTML;
		function Marquee(){
		if(tab2.offsetWidth-tab.scrollLeft<=0)
		tab.scrollLeft-=tab1.offsetWidth
		else{
		tab.scrollLeft++;
		}
		}
		var MyMar=setInterval(Marquee,speed);
		//tab.onmouseover=function() {clearInterval(MyMar)};
		//tab.onmouseout=function() {MyMar=setInterval(Marquee,speed)};*/

		//<!-- 气球 -->
		 var timeout;
                function scroll(){
				   $('.oneImage').animate({'top':-20}, 1500);
				   $('.oneImage').animate({'top':15}, 1500);
				   $('.twoImage').animate({'top':-20}, 2000);
				   $('.twoImage').animate({'top':15}, 2000);
                    //清除定时器的值
                    clearTimeout(timeout);
                    //设置定时器的值
                    timeout = setTimeout(scroll,100);    
                }            
                //执行滚动函数
                scroll();