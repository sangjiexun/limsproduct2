
function dateLine(){
    this.prevbtn = $("#prev");
    this.nextbtn = $("#next");
    this.navlist = $("#flightlistnav");
	this.dateChose = $("#dateChose");
	this.dateInput = $("#dateInput");
    this.weekcn = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
    this.weeken = ["Sun.", "Mon.", "Tue.", "Wed.", "Thu.", "Fri.", "Sat."];
    this.monthen = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec");
    this.obj = this;        
    this.lan = ConditionController.Language == "CN" ? 0 : 1;
    this.init();  
	
}
dateLine.prototype = 
        {
            name : "OneWayConditon",
            init : function () {
                this.initializeEvent();
                this.prePorcess();
            },

            initializeEvent: function () {
                this.navlist.bind("click",  this.addEvent(this,this.navlistOnClick) );
				
            },
            prePorcess: function () {

                $("#DIVWaiting").hide();
                //如果不能向前翻页
                //var sdate = E.validate.getDateString(new Date(ConditionController.SearchRequest.DepartDate.replace(/-/g, "/")));
                //var sdate = $($("div[method=seachflightbyday] :visiable").eq(0)).attr("value");
                var sdate = $($("div[method=seachflightbyday]").children().eq(0)).attr("value");
				if(sdate==undefined){
					sdate = ConditionController.SearchRequest.searchDate;
				}
                var lastdate = this.addDays(new Date(), 3).replace(/\//g, "-");
                if (sdate <= lastdate) {
                    this.prevbtn.find("em").removeClass("enable").addClass("disable");
                }
                if (sdate >= this.addDays(new Date(), 362).replace(/\//g, "-")) {
                    this.nextbtn.find("em").removeClass("enable").addClass("disable");
                }

            },
			addEvent: function (obj, func) {
				return function (event) {
					return func.apply(obj, [event || window.event].concat(func.arguments));
				};
			},
			
            //获取和展示days天内的单程最低价
            appendNextOWLowestPirce: function (use, shows, date, days) {
                var begindate;
                var enddate;
                if (days > 0) {
                    begindate = this.addDays(date, 1);
                    enddate = this.addDays(date, days);
                } else {
                    enddate = this.addDays(date, -1);
                    begindate = this.addDays(date, days);
                }

                //比较时，将date格式统一为YYYY-MM-DD,直接比较字符串
                var showstart = this.prevbtn.next().find("em").attr("value");
                var showend = this.nextbtn.prev().find("em").attr("value");
                var searchdate = this.getDateString(new Date(ConditionController.SearchRequest.searchDate.replace(/-/g, "/")));
                var that = this;
				var beginDay = new Date(begindate);
				var endDay = new Date(enddate);
				endDay = endDay.valueOf();
				var dom = dateLine.stringbuilder;
                dom.array = [];
                while (beginDay.valueOf() < endDay) {
					
					/* html.push('<td class="' + classes.join(' ') + '">' + prevMonth.getUTCDate() + '</td>'); */
					/* if (prevMonth.getUTCDay() === this.weekEnd) {
					  html.push('</tr>');
					} */
					var week;
                    var date1;
					week = this.weekcn[beginDay.getDay()];
                    date1 = this.getDateString(beginDay).substring(5);
                    var tmpDate = this.getDateString(beginDay);
					var owli = $("#flightlistnav").find("li[class=navcols]:first").clone();
					//var $a_href = owli.find("a").attr("data-href");
                    //var len = $a_href.length;
                    //var $href = $a_href.substring(0,len-10)+tmpDate;
                    //owli.find("a").attr("data-href",$href)
                    var div = owli.find("div");
                    div.removeClass("f_l_nb").addClass("f_l_na");
                    div.html("<em value=" + tmpDate + ">" + date1 + "</em><em>&nbsp;(" + week + ")</em>");

                    if (tmpDate >= showstart && tmpDate <= showend) {
						if (tmpDate == searchdate) {
							div.attr("class", "f_l_nb");
							dom.append($("<div></div>").append(owli).html());
						}
						else{
							 dom.append($("<div></div>").append(owli).html());
						}
                    } else {
						if (tmpDate == searchdate) {
							div.attr("class", "f_l_nb");
							dom.append($("<div></div>").append(owli).html());
						}
						else{
							dom.append($("<div></div>").append(owli.hide()).html());
						}
                    }
					beginDay.setUTCDate(beginDay.getUTCDate() + 1);
				  }
				  var currDate = this.getDateString(new Date()).replace(/-/g, "/");
                if (days > 0) {
                                use.after(dom.toString());
                                shows.hide();
                                for (var i = 0; i < 7; i++) {
                                    use = use.next();
                                    use.show();
                                }
                            } else {
								
                                use.before(dom.toString());
                                shows.hide();
								var j = 0;
								var useb = use;
                                for (var i = 0; i < 7; i++) {
                                    use = use.prev();
                                    use.show();
									if(use.find("em").attr("value").replace(/-/g, "/")==currDate){
										j=i;
										break;
									}
                                }
								for(var k=1;k<7-j;k++){
									useb = useb.next();
                                    useb.show();
								}
                            }
               
            },

            //改变日期,返回date格式为YYYY/MM/DD,IE仅支持的date字符串,而C#和框架使用YYYY-MM-DD格式日期，比较大小时
            //注意转换符号
            addDays: function (date, days) {
                var time = new Date(date.getTime() + days * 24 * 60 * 60 * 1000);
                var month = time.getMonth() + 1;
                var day = time.getDate();
                return time.getFullYear() + "/" + (month < 10 ? "0" + month : month) + "/" + (day < 10 ? "0" + day : day);
            },

            //获取date和当前时间之间相差天数
            getOffset: function (date) {
                var search = new Date();
                var search2 = new Date(search.getFullYear() + "/" + (search.getMonth() + 1) + "/" + search.getDate());
                var iDays = parseInt((date - search2) / 1000 / 60 / 60 / 24);
                return iDays;
            },
			getDateString:function(date){
					var year = date.getFullYear();
					var month =date.getMonth()+1;
					var day = date.getDate();
					
					if(day<10){
						day = "0"+day;
					}
					if(month<10){
						month = "0"+month;
					}
					dateStr = year+"-"+month+"-"+day;
					return dateStr;
				
			},
            //--------------------低价导航日历开始--------------------------------
            navlistOnClick: function (evt) {
				var that = this;
				var ss=$(this)
                if (this.listWindow != null) { this.listWindow.close(); }
				if(evt.target.classList.contains('f_l_na_r') ||evt.target.classList.contains('f_l_na_l') )
				{
					var element =  $(evt.target).parent();
				}
				else{
					var element =  $(evt.target);
				}
                
                var method = element.attr("method");

                var IndexFind = 0;
                while (method == null && IndexFind < 5) {
                    IndexFind++;
                    element = element.parent();
                    method = element.attr("method");
                }
                switch (method) {
                    case "seachflightbyday":
                        //搜索单程，并跳转
                        var IndexFind = 0;



                        //window.location.href = url;
                        break;
                    case "prevweek":
                        //箭头变灰后，不翻页
                        if (element.find("em").attr("class") == "disable") return;

                        //向前翻页
                        var showlis = $("#lowlistnav").find("li[class=navcols]:visible");

                        var use = $(showlis[0]);
                        var prevs = use.prevAll("[class=navcols]:hidden");
                        this.nextbtn.find("em").removeClass("disable").addClass("enable");
                        //当到达当天时，箭头变灰
                        var lastdate = new Date();
                        if (ConditionController.SearchRequest.PageName == "backlist") {
                            lastdate = new Date(ConditionController.SearchRequest.searchDate.replace(/-/g, "/"));
                        }
                        if (use.find("em").attr("value").replace(/-/g, "/") <= this.addDays(lastdate, 7)) {
                            this.prevbtn.find("em").removeClass("enable").addClass("disable");
                        }

                        //翻页时不足一页时，预加载90天数据
                        if (prevs.length < 7) {
                            prevs.remove();
                            var begindate = new Date(use.find("em").attr("value").replace(/-/g, "/"));
                            this.appendNextOWLowestPirce(use, showlis, begindate, -90);
                            return;
                        }
                        showlis.hide();
                        for (var i = 0; i < 7; i++) {
                            use = use.prev();
                            //use.show("fast");
                            use.animate({
                                opacity: 'toggle'
                            }, "slow");

                        }
                        break;
                    case "nextweek":
                        //箭头变灰后，不翻页
                        if (element.find("em").attr("class") == "disable") return;
                        //向后翻页
                        var showlis = $("#lowlistnav").find("li[class=navcols]:visible");

                        var use = $(showlis[6]);
                        $("#prev").find("em").removeClass("disable").addClass("enable");

                        var nexts = use.nextAll("[class=navcols]:hidden");

                        //当到达一年后变灰
                        if (use.find("em").attr("value").replace(/-/g, "/") >= this.addDays(new Date(), 358)) {
                            this.nextbtn.find("em").removeClass("enable").addClass("disable");
                        }

                        if (nexts.length < 7) {
                            nexts.remove();
                            var begindate = new Date(use.find("em").attr("value").replace(/-/g, "/"));
                            this.appendNextOWLowestPirce(use, showlis, begindate, 90);
                            return;
                        }
                        if (nexts.length >= 7) {
                            showlis.hide();
                            for (var i = 0; i < 7; i++) {
                                use = use.next();
                                //use.show("fast");
                                use.animate({
                                    opacity: 'toggle'
                                }, "slow");
                            }
                        }

                        break;
                    default:
                        break;
                }
            }
        }
dateLine.stringbuilder = {
        array: [],
        append: function(a) {
            return this.array.push(a),
            this
        },
        toString: function() {
            return 0 === arguments.length ? this.array.join("") : this.array.join(arguments[0])
        },
        clear: function() {
            if (this.array.length > 0) for (var a = this.array.length,
            b = 0; a > b; b++) this.array.pop()
        },
        backspace: function() {
            this.array.pop()
        }
}
