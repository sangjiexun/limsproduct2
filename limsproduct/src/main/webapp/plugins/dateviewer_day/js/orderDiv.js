/*
* @Author: Marte
* @Date:   2016-12-02 10:16:43
* @Last Modified by:   Marte
* @Last Modified time: 2017-05-03 15:02:43
*/

function iStyle_TimeDiv(start_time,end_time,interval){
    this.start_time = start_time;
    this.end_time = end_time;
    this.interval = interval;
    this.initialize();
}
iStyle_TimeDiv.prototype = {
    initialize:function(){
        var shour = this.start_time.getHours()+0;
        var sminute = this.start_time.getMinutes()+0;
        var ehour = this.end_time.getHours()+0;
        if(ehour==0){
            ehour=24;
        }
        var eminute = this.end_time.getMinutes()+0;
        var interval = this.interval;
        var orderDiv = document.getElementById("orderDiv");
        var AllMinute=ehour*60+eminute-shour*60-sminute
        var divNum = parseInt(AllMinute/interval);
        if(divNum * interval==AllMinute){
            var nTM = 0;
            for(var i = 0;i<divNum;i++){
                var timeDiv = document.createElement('div');
                timeDiv.className="timeDiv bggrey";
                timeDiv.id="timeDiv"+i;
                if(i==0){
                    var tempHour = shour;
                    var tempMinute = sminute;
                }
                if(typeof(tempMinute)=="string"){
                    nTM = parseInt(tempMinute);
                }
                else{
                    nTM = tempMinute;
                }
                if(typeof(tempHour)=="string"){
                    nTH = parseInt(tempHour);
                }
                else{
                    nTH = tempHour;
                }
                var ss = nTH*60 + nTM +interval;
                var fHour = parseInt(ss/60);
                var fMinute = ss%60;
                var timeSpan = document.createElement('span');
                if(tempMinute<10 && typeof(tempMinute)=="number"){
                    tempMinute =0+ String(tempMinute);
                }
                if(fMinute<10 && typeof(fMinute)=="number"){
                    fMinute = 0+String(fMinute);
                }
                if(tempHour<10 && typeof(tempHour)=="number"){
                    tempHour =0+ String(tempHour);
                }
                if(fHour<10 && typeof(fHour)=="number"){
                    fHour = 0+String(fHour);
                }
                timeSpan.className='timespan';
                timeSpan.innerHTML=tempHour+':'+tempMinute+'-'+fHour+":"+fMinute;
                tempHour = fHour;
                tempMinute = fMinute;
                timeDiv.appendChild(timeSpan);
                orderDiv.appendChild(timeDiv);
            }
        }
    },

	load:function(data,start,curruser){
		start  = start || new Date();
		var fday = start.getDate();
		var fmonth = start.getMonth();
		var shour = this.start_time.getHours()+0;
        var sminute = this.start_time.getMinutes()+0;
        var ehour = this.end_time.getHours()+0;
        if(ehour==0){
        	ehour=24;
		}
        var eminute = this.end_time.getMinutes()+0;
		for(var i = 0; i<data.length;i++){
			var dateStr = data[i].date;
			var date = new Date(Date.parse(dateStr));
			var dday = date.getDate();
			var dmonth=date.getMonth();
			//var interval_i = dday - fday;
			var purpose = data[i].purpose;
			var start_time = data[i].startTime;
			var end_time = data[i].endTime;
			var user = data[i].user;
			var interval = this.interval;
			var divNum = parseInt((ehour*60+eminute-shour*60-sminute)/interval);
			if(end_time=="00:00:00"){
                end_time="24:00:00";
			}
			console.log(start_time)
			var divIndexArr = this.getDivIndex(divNum,start_time,end_time);
			//alert(user+curruser);
			if(dday == fday&&dmonth == fmonth){
				for(var j = 0;j<=divIndexArr[1]-divIndexArr[0];j++){
					var x=$("#orderDiv").children("div").eq(divIndexArr[0]+j);
					if(x.hasClass("bggrey")){
						if(user == curruser){
							x.addClass("bgblue").removeClass("bggrey");
						}
						else{
							x.addClass("bgpink").removeClass("bggrey");
						}
						var userSpan = $(document.createElement("span"));
						userSpan.addClass("userspan cw mt5");
                        userSpan.html(user+"<br/>"+('已预约'));
						var purposeSpan = $(document.createElement("span"));
						purposeSpan.addClass("userspan cg");
						purposeSpan.html(purpose);
						x.append(userSpan);
						x.append(purposeSpan);
					}
				}
			}
		}

	},
	getDivIndex:function(divNum,startTime,endTime){
		var divIndexArr = new Array();
		var tempHour = null;
		var tempMinute = null;
		var shour = this.start_time.getHours()+0;
		var sminute = this.start_time.getMinutes()+0;
		var interval = this.interval;
		console.log(startTime)
		var Start = startTime.split(":");
		var End = endTime.split(":");
        var endTime = parseInt(End[0])*60 + parseInt(End[1]);
		for(var i = 0;i<divNum;i++){
			if(i==0){
                tempHour = shour;
                tempMinute = sminute;

            }
            var ss = tempHour*60 + tempMinute +interval;
            var fHour = parseInt(ss/60);
            var fMinute = ss%60;

			if(tempHour == Start[0] && tempMinute <= Start[1]){
                divIndexArr[0] = i;
            }
/*			if(fHour == End[0] && fMinute == End[1]){
				divIndexArr[1] = i;
			}*/
            if(parseInt(ss)>=parseInt(endTime)){
                divIndexArr[1] = i;
                break;
            }
			tempHour = fHour;
            tempMinute = fMinute;
		}
		return divIndexArr;
	}
}