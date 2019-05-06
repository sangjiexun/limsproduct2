/*
This software is allowed to use under GPL or you need to obtain Commercial or Enterise License
to use it in non-GPL project. Please contact sales@dhtmlx.com for details
*/
window.dhtmlx || (dhtmlx = function(a) {
    for (var b in a) dhtmlx[b] = a[b];
    return dhtmlx
});
dhtmlx.extend_api = function(a, b, c) {
    var d = window[a];
    if (d) window[a] = function(a) {
        if (a && typeof a == "object" && !a.tagName) {
            var c = d.apply(this, b._init ? b._init(a) : arguments),
            g;
            for (g in dhtmlx) if (b[g]) this[b[g]](dhtmlx[g]);
            for (g in a) if (b[g]) this[b[g]](a[g]);
            else g.indexOf("on") == 0 && this.attachEvent(g, a[g])
        } else c = d.apply(this, arguments);
        b._patch && b._patch(this);
        return c || this
    },
    window[a].prototype = d.prototype,
    c && dhtmlXHeir(window[a].prototype, c)
};
dhtmlxAjax = {
    get: function(a, b) {
        var c = new dtmlXMLLoaderObject(!0);
        c.async = arguments.length < 3;
        c.waitCall = b;
        c.loadXML(a);
        return c
    },
    post: function(a, b, c) {
        var d = new dtmlXMLLoaderObject(!0);
        d.async = arguments.length < 4;
        d.waitCall = c;
        d.loadXML(a, !0, b);
        return d
    },
    getSync: function(a) {
        return this.get(a, null, !0)
    },
    postSync: function(a, b) {
        return this.post(a, b, null, !0)
    }
};
function dtmlXMLLoaderObject(a, b, c, d) {
    this.xmlDoc = "";
    this.async = typeof c != "undefined" ? c: !0;
    this.onloadAction = a || null;
    this.mainObject = b || null;
    this.waitCall = null;
    this.rSeed = d || !1;
    return this
}
dtmlXMLLoaderObject.count = 0;
dtmlXMLLoaderObject.prototype.waitLoadFunction = function(a) {
    var b = !0;
    return this.check = function() {
        if (a && a.onloadAction != null && (!a.xmlDoc.readyState || a.xmlDoc.readyState == 4) && b) {
            b = !1;
            dtmlXMLLoaderObject.count++;
            if (typeof a.onloadAction == "function") a.onloadAction(a.mainObject, null, null, null, a);
            if (a.waitCall) a.waitCall.call(this, a),
            a.waitCall = null
        }
    }
};
dtmlXMLLoaderObject.prototype.getXMLTopNode = function(a, b) {
    if (this.xmlDoc.responseXML) {
        var c = this.xmlDoc.responseXML.getElementsByTagName(a);
        c.length == 0 && a.indexOf(":") != -1 && (c = this.xmlDoc.responseXML.getElementsByTagName(a.split(":")[1]));
        var d = c[0]
    } else d = this.xmlDoc.documentElement;
    if (d) return this._retry = !1,
    d;
    if (!this._retry) return this._retry = !0,
    b = this.xmlDoc,
    this.loadXMLString(this.xmlDoc.responseText.replace(/^[\s]+/, ""), !0),
    this.getXMLTopNode(a, b);
    dhtmlxError.throwError("LoadXML", "Incorrect XML", 
    [b || this.xmlDoc, this.mainObject]);
    return document.createElement("DIV")
};
dtmlXMLLoaderObject.prototype.loadXMLString = function(a, b) {
    if (_isIE) this.xmlDoc = new ActiveXObject("Microsoft.XMLDOM"),
    this.xmlDoc.async = this.async,
    this.xmlDoc.onreadystatechange = function() {},
    this.xmlDoc.loadXML(a);
    else {
        var c = new DOMParser;
        this.xmlDoc = c.parseFromString(a, "text/xml")
    }
    if (!b) {
        if (this.onloadAction) this.onloadAction(this.mainObject, null, null, null, this);
        if (this.waitCall) this.waitCall(),
        this.waitCall = null
    }
};
dtmlXMLLoaderObject.prototype.loadXML = function(a, b, c, d) {
    this.rSeed && (a += (a.indexOf("?") != -1 ? "&": "?") + "a_dhx_rSeed=" + (new Date).valueOf());
    this.filePath = a;
    this.xmlDoc = !_isIE && window.XMLHttpRequest ? new XMLHttpRequest: new ActiveXObject("Microsoft.XMLHTTP");
    if (this.async) this.xmlDoc.onreadystatechange = new this.waitLoadFunction(this);
    this.xmlDoc.open(b ? "POST": "GET", a, this.async);
    d ? (this.xmlDoc.setRequestHeader("User-Agent", "dhtmlxRPC v0.1 (" + navigator.userAgent + ")"), this.xmlDoc.setRequestHeader("Content-type", 
    "text/xml")) : b && this.xmlDoc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    this.xmlDoc.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    this.xmlDoc.send(c);
    this.async || (new this.waitLoadFunction(this))()
};
dtmlXMLLoaderObject.prototype.destructor = function() {
    return this.setXSLParamValue = this.getXMLTopNode = this.xmlNodeToJSON = this.doSerialization = this.loadXMLString = this.loadXML = this.doXSLTransToString = this.doXSLTransToObject = this.doXPathOpera = this.doXPath = this.xmlDoc = this.mainObject = this.onloadAction = this.filePath = this.rSeed = this.async = this._retry = this._getAllNamedChilds = this._filterXPath = null
};
dtmlXMLLoaderObject.prototype.xmlNodeToJSON = function(a) {
    for (var b = {},
    c = 0; c < a.attributes.length; c++) b[a.attributes[c].name] = a.attributes[c].value;
    b._tagvalue = a.firstChild ? a.firstChild.nodeValue: "";
    for (c = 0; c < a.childNodes.length; c++) {
        var d = a.childNodes[c].tagName;
        d && (b[d] || (b[d] = []), b[d].push(this.xmlNodeToJSON(a.childNodes[c])))
    }
    return b
};
function callerFunction(a, b) {
    return this.handler = function(c) {
        if (!c) c = window.event;
        a(c, b);
        return ! 0
    }
}
function getAbsoluteLeft(a) {
    return getOffset(a).left
}
function getAbsoluteTop(a) {
    return getOffset(a).top
}
function getOffsetSum(a) {
    for (var b = 0, c = 0; a;) b += parseInt(a.offsetTop),
    c += parseInt(a.offsetLeft),
    a = a.offsetParent;
    return {
        top: b,
        left: c
    }
}
function getOffsetRect(a) {
    var b = a.getBoundingClientRect(),
    c = document.body,
    d = document.documentElement,
    e = window.pageYOffset || d.scrollTop || c.scrollTop,
    f = window.pageXOffset || d.scrollLeft || c.scrollLeft,
    g = d.clientTop || c.clientTop || 0,
    h = d.clientLeft || c.clientLeft || 0,
    k = b.top + e - g,
    i = b.left + f - h;
    return {
        top: Math.round(k),
        left: Math.round(i)
    }
}
function getOffset(a) {
    return a.getBoundingClientRect ? getOffsetRect(a) : getOffsetSum(a)
}
function convertStringToBoolean(a) {
    typeof a == "string" && (a = a.toLowerCase());
    switch (a) {
    case "1":
    case "true":
    case "yes":
    case "y":
    case 1:
    case ! 0: return ! 0;
    default:
        return ! 1
    }
}
function getUrlSymbol(a) {
    return a.indexOf("?") != -1 ? "&": "?"
}
function dhtmlDragAndDropObject() {
    if (window.dhtmlDragAndDrop) return window.dhtmlDragAndDrop;
    this.dragStartObject = this.dragStartNode = this.dragNode = this.lastLanding = 0;
    this.tempDOMM = this.tempDOMU = null;
    this.waitDrag = 0;
    window.dhtmlDragAndDrop = this;
    return this
}
dhtmlDragAndDropObject.prototype.removeDraggableItem = function(a) {
    a.onmousedown = null;
    a.dragStarter = null;
    a.dragLanding = null
};
dhtmlDragAndDropObject.prototype.addDraggableItem = function(a, b) {
    a.onmousedown = this.preCreateDragCopy;
    a.dragStarter = b;
    this.addDragLanding(a, b)
};
dhtmlDragAndDropObject.prototype.addDragLanding = function(a, b) {
    a.dragLanding = b
};
dhtmlDragAndDropObject.prototype.preCreateDragCopy = function(a) {
    if (! ((a || window.event) && (a || event).button == 2)) {
        if (window.dhtmlDragAndDrop.waitDrag) return window.dhtmlDragAndDrop.waitDrag = 0,
        document.body.onmouseup = window.dhtmlDragAndDrop.tempDOMU,
        document.body.onmousemove = window.dhtmlDragAndDrop.tempDOMM,
        !1;
        window.dhtmlDragAndDrop.dragNode && window.dhtmlDragAndDrop.stopDrag(a);
        window.dhtmlDragAndDrop.waitDrag = 1;
        window.dhtmlDragAndDrop.tempDOMU = document.body.onmouseup;
        window.dhtmlDragAndDrop.tempDOMM = 
        document.body.onmousemove;
        window.dhtmlDragAndDrop.dragStartNode = this;
        window.dhtmlDragAndDrop.dragStartObject = this.dragStarter;
        document.body.onmouseup = window.dhtmlDragAndDrop.preCreateDragCopy;
        document.body.onmousemove = window.dhtmlDragAndDrop.callDrag;
        window.dhtmlDragAndDrop.downtime = (new Date).valueOf();
        a && a.preventDefault && a.preventDefault();
        return ! 1
    }
};
dhtmlDragAndDropObject.prototype.callDrag = function(a) {
    if (!a) a = window.event;
    dragger = window.dhtmlDragAndDrop;
    if (! ((new Date).valueOf() - dragger.downtime < 100)) {
        if (!dragger.dragNode) if (dragger.waitDrag) {
            dragger.dragNode = dragger.dragStartObject._createDragNode(dragger.dragStartNode, a);
            if (!dragger.dragNode) return dragger.stopDrag();
            dragger.dragNode.onselectstart = function() {
                return ! 1
            };
            dragger.gldragNode = dragger.dragNode;
            document.body.appendChild(dragger.dragNode);
            document.body.onmouseup = dragger.stopDrag;
            dragger.waitDrag = 
            0;
            dragger.dragNode.pWindow = window;
            dragger.initFrameRoute()
        } else return dragger.stopDrag(a, !0);
        if (dragger.dragNode.parentNode != window.document.body && dragger.gldragNode) {
            var b = dragger.gldragNode;
            if (dragger.gldragNode.old) b = dragger.gldragNode.old;
            b.parentNode.removeChild(b);
            var c = dragger.dragNode.pWindow;
            b.pWindow && b.pWindow.dhtmlDragAndDrop.lastLanding && b.pWindow.dhtmlDragAndDrop.lastLanding.dragLanding._dragOut(b.pWindow.dhtmlDragAndDrop.lastLanding);
            if (_isIE) {
                var d = document.createElement("Div");
                d.innerHTML = 
                dragger.dragNode.outerHTML;
                dragger.dragNode = d.childNodes[0]
            } else dragger.dragNode = dragger.dragNode.cloneNode(!0);
            dragger.dragNode.pWindow = window;
            dragger.gldragNode.old = dragger.dragNode;
            document.body.appendChild(dragger.dragNode);
            c.dhtmlDragAndDrop.dragNode = dragger.dragNode
        }
        dragger.dragNode.style.left = a.clientX + 15 + (dragger.fx ? dragger.fx * -1: 0) + (document.body.scrollLeft || document.documentElement.scrollLeft) + "px";
        dragger.dragNode.style.top = a.clientY + 3 + (dragger.fy ? dragger.fy * -1: 0) + (document.body.scrollTop || 
        document.documentElement.scrollTop) + "px";
        var e = a.srcElement ? a.srcElement: a.target;
        dragger.checkLanding(e, a)
    }
};
dhtmlDragAndDropObject.prototype.calculateFramePosition = function(a) {
    if (window.name) {
        for (var b = parent.frames[window.name].frameElement.offsetParent, c = 0, d = 0; b;) c += b.offsetLeft,
        d += b.offsetTop,
        b = b.offsetParent;
        if (parent.dhtmlDragAndDrop) {
            var e = parent.dhtmlDragAndDrop.calculateFramePosition(1);
            c += e.split("_")[0] * 1;
            d += e.split("_")[1] * 1
        }
        if (a) return c + "_" + d;
        else this.fx = c;
        this.fy = d
    }
    return "0_0"
};
dhtmlDragAndDropObject.prototype.checkLanding = function(a, b) {
    a && a.dragLanding ? (this.lastLanding && this.lastLanding.dragLanding._dragOut(this.lastLanding), this.lastLanding = a, this.lastLanding = this.lastLanding.dragLanding._dragIn(this.lastLanding, this.dragStartNode, b.clientX, b.clientY, b), this.lastLanding_scr = _isIE ? b.srcElement: b.target) : a && a.tagName != "BODY" ? this.checkLanding(a.parentNode, b) : (this.lastLanding && this.lastLanding.dragLanding._dragOut(this.lastLanding, b.clientX, b.clientY, b), this.lastLanding = 
    0, this._onNotFound && this._onNotFound())
};
dhtmlDragAndDropObject.prototype.stopDrag = function(a, b) {
    dragger = window.dhtmlDragAndDrop;
    if (!b) {
        dragger.stopFrameRoute();
        var c = dragger.lastLanding;
        dragger.lastLanding = null;
        c && c.dragLanding._drag(dragger.dragStartNode, dragger.dragStartObject, c, _isIE ? event.srcElement: a.target)
    }
    dragger.lastLanding = null;
    dragger.dragNode && dragger.dragNode.parentNode == document.body && dragger.dragNode.parentNode.removeChild(dragger.dragNode);
    dragger.dragNode = 0;
    dragger.gldragNode = 0;
    dragger.fx = 0;
    dragger.fy = 0;
    dragger.dragStartNode = 
    0;
    dragger.dragStartObject = 0;
    document.body.onmouseup = dragger.tempDOMU;
    document.body.onmousemove = dragger.tempDOMM;
    dragger.tempDOMU = null;
    dragger.tempDOMM = null;
    dragger.waitDrag = 0
};
dhtmlDragAndDropObject.prototype.stopFrameRoute = function(a) {
    a && window.dhtmlDragAndDrop.stopDrag(1, 1);
    for (var b = 0; b < window.frames.length; b++) try {
        window.frames[b] != a && window.frames[b].dhtmlDragAndDrop && window.frames[b].dhtmlDragAndDrop.stopFrameRoute(window)
    } catch(c) {}
    try {
        parent.dhtmlDragAndDrop && parent != window && parent != a && parent.dhtmlDragAndDrop.stopFrameRoute(window)
    } catch(d) {}
};
dhtmlDragAndDropObject.prototype.initFrameRoute = function(a, b) {
    if (a) window.dhtmlDragAndDrop.preCreateDragCopy(),
    window.dhtmlDragAndDrop.dragStartNode = a.dhtmlDragAndDrop.dragStartNode,
    window.dhtmlDragAndDrop.dragStartObject = a.dhtmlDragAndDrop.dragStartObject,
    window.dhtmlDragAndDrop.dragNode = a.dhtmlDragAndDrop.dragNode,
    window.dhtmlDragAndDrop.gldragNode = a.dhtmlDragAndDrop.dragNode,
    window.document.body.onmouseup = window.dhtmlDragAndDrop.stopDrag,
    window.waitDrag = 0,
    !_isIE && b && (!_isFF || _FFrv < 1.8) && window.dhtmlDragAndDrop.calculateFramePosition();
    try {
        parent.dhtmlDragAndDrop && parent != window && parent != a && parent.dhtmlDragAndDrop.initFrameRoute(window)
    } catch(c) {}
    for (var d = 0; d < window.frames.length; d++) try {
        window.frames[d] != a && window.frames[d].dhtmlDragAndDrop && window.frames[d].dhtmlDragAndDrop.initFrameRoute(window, !a || b ? 1: 0)
    } catch(e) {}
};
_OperaRv = _KHTMLrv = _FFrv = _isChrome = _isMacOS = _isKHTML = _isOpera = _isIE = _isFF = !1;
navigator.userAgent.indexOf("Macintosh") != -1 && (_isMacOS = !0);
navigator.userAgent.toLowerCase().indexOf("chrome") > -1 && (_isChrome = !0);
if (navigator.userAgent.indexOf("Safari") != -1 || navigator.userAgent.indexOf("Konqueror") != -1) _KHTMLrv = parseFloat(navigator.userAgent.substr(navigator.userAgent.indexOf("Safari") + 7, 5)),
_KHTMLrv > 525 ? (_isFF = !0, _FFrv = 1.9) : _isKHTML = !0;
else if (navigator.userAgent.indexOf("Opera") != -1) _isOpera = !0,
_OperaRv = parseFloat(navigator.userAgent.substr(navigator.userAgent.indexOf("Opera") + 6, 3));
else if (navigator.appName.indexOf("Microsoft") != -1) {
    if (_isIE = !0, (navigator.appVersion.indexOf("MSIE 8.0") != -1 || navigator.appVersion.indexOf("MSIE 9.0") != 
    -1 || navigator.appVersion.indexOf("MSIE 10.0") != -1) && document.compatMode != "BackCompat") _isIE = 8
} else _isFF = !0,
_FFrv = parseFloat(navigator.userAgent.split("rv:")[1]);
dtmlXMLLoaderObject.prototype.doXPath = function(a, b, c, d) {
    if (_isKHTML || !_isIE && !window.XPathResult) return this.doXPathOpera(a, b);
    if (_isIE) return b || (b = this.xmlDoc.nodeName ? this.xmlDoc: this.xmlDoc.responseXML),
    b || dhtmlxError.throwError("LoadXML", "Incorrect XML", [b || this.xmlDoc, this.mainObject]),
    c != null && b.setProperty("SelectionNamespaces", "xmlns:xsl='" + c + "'"),
    d == "single" ? b.selectSingleNode(a) : b.selectNodes(a) || [];
    else {
        var e = b;
        b || (b = this.xmlDoc.nodeName ? this.xmlDoc: this.xmlDoc.responseXML);
        b || dhtmlxError.throwError("LoadXML", 
        "Incorrect XML", [b || this.xmlDoc, this.mainObject]);
        b.nodeName.indexOf("document") != -1 ? e = b: (e = b, b = b.ownerDocument);
        var f = XPathResult.ANY_TYPE;
        if (d == "single") f = XPathResult.FIRST_ORDERED_NODE_TYPE;
        var g = [],
        h = b.evaluate(a, e, 
        function() {
            return c
        },
        f, null);
        if (f == XPathResult.FIRST_ORDERED_NODE_TYPE) return h.singleNodeValue;
        for (var k = h.iterateNext(); k;) g[g.length] = k,
        k = h.iterateNext();
        return g
    }
};
function v() {
    if (!this.catches) this.catches = [];
    return this
}
v.prototype.catchError = function(a, b) {
    this.catches[a] = b
};
v.prototype.throwError = function(a, b, c) {
    if (this.catches[a]) return this.catches[a](a, b, c);
    if (this.catches.ALL) return this.catches.ALL(a, b, c);
    alert("Error type: " + a + "\nDescription: " + b);
    return null
};
window.dhtmlxError = new v;
dtmlXMLLoaderObject.prototype.doXPathOpera = function(a, b) {
    var c = a.replace(/[\/]+/gi, "/").split("/"),
    d = null,
    e = 1;
    if (!c.length) return [];
    if (c[0] == ".") d = [b];
    else if (c[0] == "") d = (this.xmlDoc.responseXML || this.xmlDoc).getElementsByTagName(c[e].replace(/\[[^\]]*\]/g, "")),
    e++;
    else return [];
    for (; e < c.length; e++) d = this._getAllNamedChilds(d, c[e]);
    c[e - 1].indexOf("[") != -1 && (d = this._filterXPath(d, c[e - 1]));
    return d
};
dtmlXMLLoaderObject.prototype._filterXPath = function(a, b) {
    for (var c = [], b = b.replace(/[^\[]*\[\@/g, "").replace(/[\[\]\@]*/g, ""), d = 0; d < a.length; d++) a[d].getAttribute(b) && (c[c.length] = a[d]);
    return c
};
dtmlXMLLoaderObject.prototype._getAllNamedChilds = function(a, b) {
    var c = [];
    _isKHTML && (b = b.toUpperCase());
    for (var d = 0; d < a.length; d++) for (var e = 0; e < a[d].childNodes.length; e++) _isKHTML ? a[d].childNodes[e].tagName && a[d].childNodes[e].tagName.toUpperCase() == b && (c[c.length] = a[d].childNodes[e]) : a[d].childNodes[e].tagName == b && (c[c.length] = a[d].childNodes[e]);
    return c
};
function dhtmlXHeir(a, b) {
    for (var c in b) typeof b[c] == "function" && (a[c] = b[c]);
    return a
}
function dhtmlxEvent(a, b, c) {
    a.addEventListener ? a.addEventListener(b, c, !1) : a.attachEvent && a.attachEvent("on" + b, c)
}
dtmlXMLLoaderObject.prototype.xslDoc = null;
dtmlXMLLoaderObject.prototype.setXSLParamValue = function(a, b, c) {
    if (!c) c = this.xslDoc;
    if (c.responseXML) c = c.responseXML;
    var d = this.doXPath("/xsl:stylesheet/xsl:variable[@name='" + a + "']", c, "http://www.w3.org/1999/XSL/Transform", "single");
    if (d != null) d.firstChild.nodeValue = b
};
dtmlXMLLoaderObject.prototype.doXSLTransToObject = function(a, b) {
    if (!a) a = this.xslDoc;
    if (a.responseXML) a = a.responseXML;
    if (!b) b = this.xmlDoc;
    if (b.responseXML) b = b.responseXML;
    if (_isIE) {
        d = new ActiveXObject("Msxml2.DOMDocument.3.0");
        try {
            b.transformNodeToObject(a, d)
        } catch(c) {
            d = b.transformNode(a)
        }
    } else {
        if (!this.XSLProcessor) this.XSLProcessor = new XSLTProcessor,
        this.XSLProcessor.importStylesheet(a);
        var d = this.XSLProcessor.transformToDocument(b)
    }
    return d
};
dtmlXMLLoaderObject.prototype.doXSLTransToString = function(a, b) {
    var c = this.doXSLTransToObject(a, b);
    return typeof c == "string" ? c: this.doSerialization(c)
};
dtmlXMLLoaderObject.prototype.doSerialization = function(a) {
    if (!a) a = this.xmlDoc;
    if (a.responseXML) a = a.responseXML;
    if (_isIE) return a.xml;
    else {
        var b = new XMLSerializer;
        return b.serializeToString(a)
    }
};
dhtmlxEventable = function(a) {
    a.attachEvent = function(a, c, d) {
        a = "ev_" + a.toLowerCase();
        this[a] || (this[a] = new this.eventCatcher(d || this));
        return a + ":" + this[a].addEvent(c)
    };
    a.callEvent = function(a, c) {
        a = "ev_" + a.toLowerCase();
        return this[a] ? this[a].apply(this, c) : !0
    };
    a.checkEvent = function(a) {
        return !! this["ev_" + a.toLowerCase()]
    };
    a.eventCatcher = function(a) {
        var c = [],
        d = function() {
            for (var d = !0, f = 0; f < c.length; f++) if (c[f] != null) var g = c[f].apply(a, arguments),
            d = d && g;
            return d
        };
        d.addEvent = function(a) {
            typeof a != "function" && 
            (a = eval(a));
            return a ? c.push(a) - 1: !1
        };
        d.removeEvent = function(a) {
            c[a] = null
        };
        return d
    };
    a.detachEvent = function(a) {
        if (a != !1) {
            var c = a.split(":");
            this[c[0]].removeEvent(c[1])
        }
    };
    a.detachAllEvents = function() {
        for (var a in this) a.indexOf("ev_") == 0 && delete this[a]
    };
    a = null
};
if (!window.dhtmlx) window.dhtmlx = {};
 (function() {
    function a(a, b) {
        var d = a.callback;
        c(!1);
        a.box.parentNode.removeChild(a.box);
        p = a.box = null;
        d && d(b)
    }
    function b(b) {
        if (p) {
            var b = b || event,
            c = b.which || event.keyCode;
            dhtmlx.message.keyboard && ((c == 13 || c == 32) && a(p, !0), c == 27 && a(p, !1));
            b.preventDefault && b.preventDefault();
            return ! (b.cancelBubble = !0)
        }
    }
    function c(a) {
        if (!c.cover) c.cover = document.createElement("DIV"),
        c.cover.onkeydown = b,
        c.cover.className = "dhx_modal_cover",
        document.body.appendChild(c.cover);
        var d = document.body.scrollHeight;
        c.cover.style.display = 
        a ? "inline-block": "none"
    }
    function d(a, b) {
        var c = "dhtmlx_" + a.toLowerCase().replace(/ /g, "_") + "_button";
        return "<div class='dhtmlx_popup_button " + c + "' result='" + b + "' ><div>" + a + "</div></div>"
    }
    function e(a) {
        if (!l.area) l.area = document.createElement("DIV"),
        l.area.className = "dhtmlx_message_area",
        l.area.style[l.position] = "5px",
        document.body.appendChild(l.area);
        l.hide(a.id);
        var b = document.createElement("DIV");
        b.innerHTML = "<div>" + a.text + "</div>";
        b.className = "dhtmlx-info dhtmlx-" + a.type;
        b.onclick = function() {
            l.hide(a.id);
            a = null
        };
        l.position == "bottom" && l.area.firstChild ? l.area.insertBefore(b, l.area.firstChild) : l.area.appendChild(b);
        a.expire > 0 && (l.timers[a.id] = window.setTimeout(function() {
            l.hide(a.id)
        },
        a.expire));
        l.pull[a.id] = b;
        b = null;
        return a.id
    }
    function f(b, c, e) {
        var f = document.createElement("DIV");
        f.className = " dhtmlx_modal_box dhtmlx-" + b.type;
        f.setAttribute("dhxbox", 1);
        var g = "";
        if (b.width) f.style.width = b.width;
        if (b.height) f.style.height = b.height;
        b.title && (g += '<div class="dhtmlx_popup_title">' + b.title + "</div>");
        g += 
        '<div class="dhtmlx_popup_text"><span>' + (b.content ? "": b.text) + '</span></div><div  class="dhtmlx_popup_controls">';
        c && (g += d(b.ok || "确认", !0));
        e && (g += d(b.cancel || "取消", !1));
        if (b.buttons) for (var h = 0; h < b.buttons.length; h++) g += d(b.buttons[h], h);
        g += "</div>";
        f.innerHTML = g;
        if (b.content) {
            var i = b.content;
            typeof i == "string" && (i = document.getElementById(i));
            if (i.style.display == "none") i.style.display = "";
            f.childNodes[b.title ? 1: 0].appendChild(i)
        }
        f.onclick = function(c) {
            var c = c || event,
            d = c.target || c.srcElement;
            if (!d.className) d = 
            d.parentNode;
            d.className.split(" ")[0] == "dhtmlx_popup_button" && (result = d.getAttribute("result"), result = result == "true" || (result == "false" ? !1: result), a(b, result))
        };
        b.box = f;
        if (c || e) p = b;
        return f
    }
    function g(a, d, e) {
        var g = a.tagName ? a: f(a, d, e);
        a.hidden || c(!0);
        document.body.appendChild(g);
        var h = Math.abs(Math.floor(((window.innerWidth || document.documentElement.offsetWidth) - g.offsetWidth) / 2)),
        i = Math.abs(Math.floor(((window.innerHeight || document.documentElement.offsetHeight) - g.offsetHeight) / 2));
        g.style.top = a.position == 
        "top" ? "-3px": i + "px";
        g.style.left = h + "px";
        g.onkeydown = b;
        g.focus();
        a.hidden && dhtmlx.modalbox.hide(g);
        return g
    }
    function h(a) {
        return g(a, !0, !1)
    }
    function k(a) {
        return g(a, !0, !0)
    }
    function i(a) {
        return g(a)
    }
    function j(a, b, c) {
        typeof a != "object" && (typeof b == "function" && (c = b, b = ""), a = {
            text: a,
            type: b,
            callback: c
        });
        return a
    }
    function m(a, b, c, d) {
        typeof a != "object" && (a = {
            text: a,
            type: b,
            expire: c,
            id: d
        });
        a.id = a.id || l.uid();
        a.expire = a.expire || l.expire;
        return a
    }
    var p = null;
    document.attachEvent ? document.attachEvent("onkeydown", 
    b) : document.addEventListener("keydown", b, !0);
    dhtmlx.alert = function() {
        text = j.apply(this, arguments);
        text.type = text.type || "confirm";
        return h(text)
    };
    dhtmlx.confirm = function() {
        text = j.apply(this, arguments);
        text.type = text.type || "alert";
        return k(text)
    };
    dhtmlx.modalbox = function() {
        text = j.apply(this, arguments);
        text.type = text.type || "alert";
        return i(text)
    };
    dhtmlx.modalbox.hide = function(a) {
        for (; a && a.getAttribute && !a.getAttribute("dhxbox");) a = a.parentNode;
        a && (a.parentNode.removeChild(a), c(!1))
    };
    var l = dhtmlx.message = 
    function(a, b, c, d) {
        a = m.apply(this, arguments);
        a.type = a.type || "info";
        var f = a.type.split("-")[0];
        switch (f) {
        case "alert":
            return h(a);
        case "confirm":
            return k(a);
        case "modalbox":
            return i(a);
        default:
            return e(a)
        }
    };
    l.seed = (new Date).valueOf();
    l.uid = function() {
        return l.seed++
    };
    l.expire = 4E3;
    l.keyboard = !0;
    l.position = "top";
    l.pull = {};
    l.timers = {};
    l.hideAll = function() {
        for (var a in l.pull) l.hide(a)
    };
    l.hide = function(a) {
        var b = l.pull[a];
        b && b.parentNode && (window.setTimeout(function() {
            b.parentNode.removeChild(b);
            b = null
        },
        2E3), b.className += " hidden", l.timers[a] && window.clearTimeout(l.timers[a]), delete l.pull[a])
    }
})();
function dataProcessor(a) {
    this.serverProcessor = a;
    this.action_param = "!nativeeditor_status";
    this.object = null;
    this.updatedRows = [];
    this.autoUpdate = !0;
    this.updateMode = "cell";
    this._tMode = "GET";
    this.post_delim = "_";
    this._waitMode = 0;
    this._in_progress = {};
    this._invalid = {};
    this.mandatoryFields = [];
    this.messages = [];
    this.styles = {
        updated: "font-weight:bold;",
        inserted: "font-weight:bold;",
        deleted: "text-decoration : line-through;",
        invalid: "background-color:FFE0E0;",
        invalid_cell: "border-bottom:2px solid red;",
        error: "color:red;",
        clear: "font-weight:normal;text-decoration:none;"
    };
    this.enableUTFencoding(!0);
    dhtmlxEventable(this);
    return this
}
dataProcessor.prototype = {
    setTransactionMode: function(a, b) {
        this._tMode = a;
        this._tSend = b
    },
    escape: function(a) {
        return this._utf ? encodeURIComponent(a) : escape(a)
    },
    enableUTFencoding: function(a) {
        this._utf = convertStringToBoolean(a)
    },
    setDataColumns: function(a) {
        this._columns = typeof a == "string" ? a.split(",") : a
    },
    getSyncState: function() {
        return ! this.updatedRows.length
    },
    enableDataNames: function(a) {
        this._endnm = convertStringToBoolean(a)
    },
    enablePartialDataSend: function(a) {
        this._changed = convertStringToBoolean(a)
    },
    setUpdateMode: function(a, 
    b) {
        this.autoUpdate = a == "cell";
        this.updateMode = a;
        this.dnd = b
    },
    ignore: function(a, b) {
        this._silent_mode = !0;
        a.call(b || window);
        this._silent_mode = !1
    },
    setUpdated: function(a, b, c) {
        if (!this._silent_mode) {
            var d = this.findRow(a),
            c = c || "updated",
            e = this.obj.getUserData(a, this.action_param);
            e && c == "updated" && (c = e);
            b ? (this.set_invalid(a, !1), this.updatedRows[d] = a, this.obj.setUserData(a, this.action_param, c), this._in_progress[a] && (this._in_progress[a] = "wait")) : this.is_invalid(a) || (this.updatedRows.splice(d, 1), this.obj.setUserData(a, 
            this.action_param, ""));
            b || this._clearUpdateFlag(a);
            this.markRow(a, b, c);
            b && this.autoUpdate && this.sendData(a)
        }
    },
    _clearUpdateFlag: function() {},
    markRow: function(a, b, c) {
        var d = "",
        e = this.is_invalid(a);
        e && (d = this.styles[e], b = !0);
        if (this.callEvent("onRowMark", [a, b, c, e]) && (d = this.styles[b ? c: "clear"] + d, this.obj[this._methods[0]](a, d), e && e.details)) {
            d += this.styles[e + "_cell"];
            for (var f = 0; f < e.details.length; f++) if (e.details[f]) this.obj[this._methods[1]](a, f, d)
        }
    },
    getState: function(a) {
        return this.obj.getUserData(a, 
        this.action_param)
    },
    is_invalid: function(a) {
        return this._invalid[a]
    },
    set_invalid: function(a, b, c) {
        c && (b = {
            value: b,
            details: c,
            toString: function() {
                return this.value.toString()
            }
        });
        this._invalid[a] = b
    },
    checkBeforeUpdate: function() {
        return ! 0
    },
    sendData: function(a) {
        if (!this._waitMode || !(this.obj.mytype == "tree" || this.obj._h2)) {
            this.obj.editStop && this.obj.editStop();
            if (typeof a == "undefined" || this._tSend) return this.sendAllData();
            if (this._in_progress[a]) return ! 1;
            this.messages = [];
            if (!this.checkBeforeUpdate(a) && this.callEvent("onValidatationError", 
            [a, this.messages])) return ! 1;
            this._beforeSendData(this._getRowData(a), a)
        }
    },
    _beforeSendData: function(a, b) {
        if (!this.callEvent("onBeforeUpdate", [b, this.getState(b), a])) return ! 1;
        this._sendData(a, b)
    },
    serialize: function(a, b) {
        if (typeof a == "string") return a;
        if (typeof b != "undefined") return this.serialize_one(a, "");
        else {
            var c = [],
            d = [],
            e;
            for (e in a) a.hasOwnProperty(e) && (c.push(this.serialize_one(a[e], e + this.post_delim)), d.push(e));
            c.push("ids=" + this.escape(d.join(",")));
            dhtmlx.security_key && c.push("dhx_security=" + 
            dhtmlx.security_key);
            return c.join("&")
        }
    },
    serialize_one: function(a, b) {
        if (typeof a == "string") return a;
        var c = [],
        d;
        for (d in a) a.hasOwnProperty(d) && c.push(this.escape((b || "") + d) + "=" + this.escape(a[d]));
        return c.join("&")
    },
    _sendData: function(a, b) {
        if (a) {
            if (!this.callEvent("onBeforeDataSending", b ? [b, this.getState(b), a] : [null, null, a])) return ! 1;
            b && (this._in_progress[b] = (new Date).valueOf());
            var c = new dtmlXMLLoaderObject(this.afterUpdate, this, !0),
            d = this.serverProcessor + (this._user ? getUrlSymbol(this.serverProcessor) + 
            ["dhx_user=" + this._user, "dhx_version=" + this.obj.getUserData(0, "version")].join("&") : "");
            this._tMode != "POST" ? c.loadXML(d + (d.indexOf("?") != -1 ? "&": "?") + this.serialize(a, b)) : c.loadXML(d, !0, this.serialize(a, b));
            this._waitMode++
        }
    },
    sendAllData: function() {
        if (this.updatedRows.length) {
            this.messages = [];
            for (var a = !0, b = 0; b < this.updatedRows.length; b++) a &= this.checkBeforeUpdate(this.updatedRows[b]);
            if (!a && !this.callEvent("onValidatationError", ["", this.messages])) return ! 1;
            if (this._tSend) this._sendData(this._getAllData());
            else for (b = 0; b < this.updatedRows.length; b++) if (!this._in_progress[this.updatedRows[b]] && !this.is_invalid(this.updatedRows[b]) && (this._beforeSendData(this._getRowData(this.updatedRows[b]), this.updatedRows[b]), this._waitMode && (this.obj.mytype == "tree" || this.obj._h2))) break
        }
    },
    _getAllData: function() {
        for (var a = {},
        b = !1, c = 0; c < this.updatedRows.length; c++) {
            var d = this.updatedRows[c]; ! this._in_progress[d] && !this.is_invalid(d) && this.callEvent("onBeforeUpdate", [d, this.getState(d)]) && (a[d] = this._getRowData(d, d + this.post_delim), 
            b = !0, this._in_progress[d] = (new Date).valueOf())
        }
        return b ? a: null
    },
    setVerificator: function(a, b) {
        this.mandatoryFields[a] = b || 
        function(a) {
            return a != ""
        }
    },
    clearVerificator: function(a) {
        this.mandatoryFields[a] = !1
    },
    findRow: function(a) {
        for (var b = 0, b = 0; b < this.updatedRows.length; b++) if (a == this.updatedRows[b]) break;
        return b
    },
    defineAction: function(a, b) {
        if (!this._uActions) this._uActions = [];
        this._uActions[a] = b
    },
    afterUpdateCallback: function(a, b, c, d) {
        var e = a,
        f = c != "error" && c != "invalid";
        f || this.set_invalid(a, c);
        if (this._uActions && 
        this._uActions[c] && !this._uActions[c](d)) return delete this._in_progress[e];
        this._in_progress[e] != "wait" && this.setUpdated(a, !1);
        var g = a;
        switch (c) {
        case "update":
        case "updated":
        case "inserted":
        case "insert":
            b != a && (this.obj[this._methods[2]](a, b), a = b);
            break;
        case "delete":
        case "deleted":
            return this.obj.setUserData(a, this.action_param, "true_deleted"),
            this.obj[this._methods[3]](a),
            delete this._in_progress[e],
            this.callEvent("onAfterUpdate", [a, c, b, d])
        }
        this._in_progress[e] != "wait" ? (f && this.obj.setUserData(a, 
        this.action_param, ""), delete this._in_progress[e]) : (delete this._in_progress[e], this.setUpdated(b, !0, this.obj.getUserData(a, this.action_param)));
        this.callEvent("onAfterUpdate", [a, c, b, d])
    },
    afterUpdate: function(a, b, c, d, e) {
        e.getXMLTopNode("data");
        if (e.xmlDoc.responseXML) {
            for (var f = e.doXPath("//data/action"), g = 0; g < f.length; g++) {
                var h = f[g],
                k = h.getAttribute("type"),
                i = h.getAttribute("sid"),
                j = h.getAttribute("tid");
                a.afterUpdateCallback(i, j, k, h)
            }
            a.finalizeUpdate()
        }
    },
    finalizeUpdate: function() {
        this._waitMode && 
        this._waitMode--; (this.obj.mytype == "tree" || this.obj._h2) && this.updatedRows.length && this.sendData();
        this.callEvent("onAfterUpdateFinish", []);
        this.updatedRows.length || this.callEvent("onFullSync", [])
    },
    init: function(a) {
        this.obj = a;
        this.obj._dp_init && this.obj._dp_init(this)
    },
    setOnAfterUpdate: function(a) {
        this.attachEvent("onAfterUpdate", a)
    },
    enableDebug: function() {},
    setOnBeforeUpdateHandler: function(a) {
        this.attachEvent("onBeforeDataSending", a)
    },
    setAutoUpdate: function(a, b) {
        a = a || 2E3;
        this._user = b || (new Date).valueOf();
        this._need_update = !1;
        this._loader = null;
        this._update_busy = !1;
        this.attachEvent("onAfterUpdate", 
        function(a, b, c, g) {
            this.afterAutoUpdate(a, b, c, g)
        });
        this.attachEvent("onFullSync", 
        function() {
            this.fullSync()
        });
        var c = this;
        window.setInterval(function() {
            c.loadUpdate()
        },
        a)
    },
    afterAutoUpdate: function(a, b) {
        return b == "collision" ? (this._need_update = !0, !1) : !0
    },
    fullSync: function() {
        if (this._need_update == !0) this._need_update = !1,
        this.loadUpdate();
        return ! 0
    },
    getUpdates: function(a, b) {
        if (this._update_busy) return ! 1;
        else this._update_busy = 
        !0;
        this._loader = this._loader || new dtmlXMLLoaderObject(!0);
        this._loader.async = !0;
        this._loader.waitCall = b;
        this._loader.loadXML(a)
    },
    _v: function(a) {
        return a.firstChild ? a.firstChild.nodeValue: ""
    },
    _a: function(a) {
        for (var b = [], c = 0; c < a.length; c++) b[c] = this._v(a[c]);
        return b
    },
    loadUpdate: function() {
        var a = this,
        b = this.obj.getUserData(0, "version"),
        c = this.serverProcessor + getUrlSymbol(this.serverProcessor) + ["dhx_user=" + this._user, "dhx_version=" + b].join("&"),
        c = c.replace("editing=true&", "");
        this.getUpdates(c, 
        function() {
            var b = 
            a._loader.doXPath("//userdata");
            a.obj.setUserData(0, "version", a._v(b[0]));
            var c = a._loader.doXPath("//update");
            if (c.length) {
                a._silent_mode = !0;
                for (var f = 0; f < c.length; f++) {
                    var g = c[f].getAttribute("status"),
                    h = c[f].getAttribute("id"),
                    k = c[f].getAttribute("parent");
                    switch (g) {
                    case "inserted":
                        a.callEvent("insertCallback", [c[f], h, k]);
                        break;
                    case "updated":
                        a.callEvent("updateCallback", [c[f], h, k]);
                        break;
                    case "deleted":
                        a.callEvent("deleteCallback", [c[f], h, k])
                    }
                }
                a._silent_mode = !1
            }
            a._update_busy = !1;
            a = null
        })
    }
};
if (window.dhtmlXGridObject) dhtmlXGridObject.prototype._init_point_connector = dhtmlXGridObject.prototype._init_point,
dhtmlXGridObject.prototype._init_point = function() {
    var a = function(a) {
        a = a.replace(/(\?|\&)connector[^\f]*/g, "");
        return a + (a.indexOf("?") != -1 ? "&": "?") + "connector=true" + (this.hdr.rows.length > 0 ? "&dhx_no_header=1": "")
    },
    b = function(b) {
        return a.call(this, b) + (this._connector_sorting || "") + (this._connector_filter || "")
    },
    c = function(a, c, d) {
        this._connector_sorting = "&dhx_sort[" + c + "]=" + d;
        return b.call(this, 
        a)
    },
    d = function(a, c, d) {
        for (var h = 0; h < c.length; h++) c[h] = "dhx_filter[" + c[h] + "]=" + encodeURIComponent(d[h]);
        this._connector_filter = "&" + c.join("&");
        return b.call(this, a)
    };
    this.attachEvent("onCollectValues", 
    function(a) {
        return this._con_f_used[a] ? typeof this._con_f_used[a] == "object" ? this._con_f_used[a] : !1: !0
    });
    this.attachEvent("onDynXLS", 
    function() {
        this.xmlFileUrl = b.call(this, this.xmlFileUrl);
        return ! 0
    });
    this.attachEvent("onBeforeSorting", 
    function(a, b, d) {
        if (b == "connector") {
            var h = this;
            this.clearAndLoad(c.call(this, 
            this.xmlFileUrl, a, d), 
            function() {
                h.setSortImgState(!0, a, d)
            });
            return ! 1
        }
        return ! 0
    });
    this.attachEvent("onFilterStart", 
    function(a, b) {
        return this._con_f_used.length ? (this.clearAndLoad(d.call(this, this.xmlFileUrl, a, b)), !1) : !0
    });
    this.attachEvent("onXLE", 
    function() {});
    this._init_point_connector && this._init_point_connector()
},
dhtmlXGridObject.prototype._con_f_used = [],
dhtmlXGridObject.prototype._in_header_connector_text_filter = function(a, b) {
    this._con_f_used[b] || (this._con_f_used[b] = 1);
    return this._in_header_text_filter(a, 
    b)
},
dhtmlXGridObject.prototype._in_header_connector_select_filter = function(a, b) {
    this._con_f_used[b] || (this._con_f_used[b] = 2);
    return this._in_header_select_filter(a, b)
},
dhtmlXGridObject.prototype.load_connector = dhtmlXGridObject.prototype.load,
dhtmlXGridObject.prototype.load = function(a, b, c) {
    if (!this._colls_loaded && this.cellType) {
        for (var d = [], e = 0; e < this.cellType.length; e++)(this.cellType[e].indexOf("co") == 0 || this._con_f_used[e] == 2) && d.push(e);
        d.length && (arguments[0] += (arguments[0].indexOf("?") != -1 ? "&": 
        "?") + "connector=true&dhx_colls=" + d.join(","))
    }
    return this.load_connector.apply(this, arguments)
},
dhtmlXGridObject.prototype._parseHead_connector = dhtmlXGridObject.prototype._parseHead,
dhtmlXGridObject.prototype._parseHead = function(a, b, c) {
    this._parseHead_connector.apply(this, arguments);
    if (!this._colls_loaded) {
        for (var d = this.xmlLoader.doXPath("./coll_options", arguments[0]), e = 0; e < d.length; e++) {
            var f = d[e].getAttribute("for"),
            g = [],
            h = null;
            this.cellType[f] == "combo" && (h = this.getColumnCombo(f));
            this.cellType[f].indexOf("co") == 
            0 && (h = this.getCombo(f));
            for (var k = this.xmlLoader.doXPath("./item", d[e]), i = 0; i < k.length; i++) {
                var j = k[i].getAttribute("value");
                if (h) {
                    var m = k[i].getAttribute("label") || j;
                    h.addOption ? h.addOption([[j, m]]) : h.put(j, m);
                    g[g.length] = m
                } else g[g.length] = j
            }
            this._con_f_used[f * 1] && (this._con_f_used[f * 1] = g)
        }
        this._colls_loaded = !0
    }
};
if (window.dataProcessor) dataProcessor.prototype.init_original = dataProcessor.prototype.init,
dataProcessor.prototype.init = function(a) {
    this.init_original(a);
    a._dataprocessor = this;
    this.setTransactionMode("POST", !0);
    this.serverProcessor += (this.serverProcessor.indexOf("?") != -1 ? "&": "?") + "editing=true"
};
dhtmlxError.catchError("LoadXML", 
function(a, b, c) {
    c[0].status != 0 && alert(c[0].responseText)
});
window.dhtmlXScheduler = window.scheduler = {
    version: "3.5.0"
};
dhtmlxEventable(scheduler);
scheduler.init = function(a, b, c) {
    b = b || new Date;
    c = c || "week";
    scheduler.date.init();
    this._obj = typeof a == "string" ? document.getElementById(a) : a;
    this._els = [];
    this._scroll = !0;
    this._quirks = _isIE && document.compatMode == "BackCompat";
    this._quirks7 = _isIE && navigator.appVersion.indexOf("MSIE 8") == -1;
    this.get_elements();
    this.init_templates();
    this.set_actions();
    dhtmlxEvent(window, "resize", 
    function() {
        window.clearTimeout(scheduler._resize_timer);
        scheduler._resize_timer = window.setTimeout(function() {
            scheduler.callEvent("onSchedulerResize", 
            []) && (scheduler.update_view(), scheduler.callEvent("onAfterSchedulerResize", []))
        },
        100)
    });
    this.set_sizes();
    scheduler.callEvent("onSchedulerReady", []);
    this.setCurrentView(b, c)
};
scheduler.xy = {
    nav_height: 22,
    min_event_height: 40,
    scale_width: 50,
    bar_height: 20,
    scroll_width: 18,
    scale_height: 20,
    month_scale_height: 20,
    menu_width: 25,
    margin_top: 0,
    margin_left: 0,
    editor_width: 140,
    lightbox_additional_height: 50
};
scheduler.keys = {
    edit_save: 13,
    edit_cancel: 27
};
scheduler.set_sizes = function() {
    var a = this._x = this._obj.clientWidth - this.xy.margin_left,
    b = this._y = this._obj.clientHeight - this.xy.margin_top,
    c = this._table_view ? 0: this.xy.scale_width + this.xy.scroll_width,
    d = this._table_view ? -1: this.xy.scale_width;
    this.set_xy(this._els.dhx_cal_navline[0], a, this.xy.nav_height, 0, 0);
    this.set_xy(this._els.dhx_cal_header[0], a - c, this.xy.scale_height, d, this.xy.nav_height + (this._quirks ? -1: 1));
    var e = this._els.dhx_cal_navline[0].offsetHeight;
    if (e > 0) this.xy.nav_height = e;
    var f = this.xy.scale_height + 
    this.xy.nav_height + (this._quirks ? -2: 0);
    this.set_xy(this._els.dhx_cal_data[0], a, b - (f + 2), 0, f + 2)
};
scheduler.set_xy = function(a, b, c, d, e) {
    a.style.width = Math.max(0, b) + "px";
    a.style.height = Math.max(0, c) + "px";
    if (arguments.length > 3) a.style.left = d + "px",
    a.style.top = e + "px"
};
scheduler.get_elements = function() {
    for (var a = this._obj.getElementsByTagName("DIV"), b = 0; b < a.length; b++) {
        var c = a[b].className;
        c && (c = c.split(" ")[0]);
        this._els[c] || (this._els[c] = []);
        this._els[c].push(a[b]);
        var d = scheduler.locale.labels[a[b].getAttribute("name") || c];
        if (d) a[b].innerHTML = d
    }
};
scheduler.set_actions = function() {
    for (var a in this._els) if (this._click[a]) for (var b = 0; b < this._els[a].length; b++) this._els[a][b].onclick = scheduler._click[a];
    this._obj.onselectstart = function() {
        return ! 1
    };
    this._obj.onmousemove = function(a) {
        scheduler._on_mouse_move(a || event)
    };
    this._obj.onmousedown = function(a) {
        scheduler._on_mouse_down(a || event)
    };
    this._obj.onmouseup = function(a) {
        scheduler._on_mouse_up(a || event)
    };
    this._obj.ondblclick = function(a) {
        scheduler._on_dbl_click(a || event)
    };
    this._obj.oncontextmenu = function(a) {
        var b = 
        a || event,
        e = b.target || b.srcElement,
        f = scheduler.callEvent("onContextMenu", [scheduler._locate_event(e), b]);
        return f
    }
};
scheduler.select = function(a) {
    if (this._select_id != a) this.editStop(!1),
    this.unselect(),
    this._select_id = a,
    this.updateEvent(a)
};
scheduler.unselect = function(a) {
    if (! (a && a != this._select_id)) {
        var b = this._select_id;
        this._select_id = null;
        b && this.updateEvent(b)
    }
};
scheduler.getState = function() {
    return {
        mode: this._mode,
        date: this._date,
        min_date: this._min_date,
        max_date: this._max_date,
        editor_id: this._edit_id,
        lightbox_id: this._lightbox_id,
        new_event: this._new_event,
        select_id: this._select_id,
        expanded: this.expanded,
        drag_id: this._drag_id,
        drag_mode: this._drag_mode
    }
};
scheduler._click = {
    dhx_cal_data: function(a) {
        var b = a ? a.target: event.srcElement,
        c = scheduler._locate_event(b),
        a = a || event;
        if (c) {
            if (!scheduler.callEvent("onClick", [c, a]) || scheduler.config.readonly) return
        } else scheduler.callEvent("onEmptyClick", [scheduler.getActionData(a).date, a]);
        if (c && scheduler.config.select) {
            scheduler.select(c);
            var d = b.className;
            if (d.indexOf("_icon") != -1) scheduler._click.buttons[d.split(" ")[1].replace("icon_", "")](c)
        } else scheduler._close_not_saved()
    },
    dhx_cal_prev_button: function() {
        scheduler._click.dhx_cal_next_button(0, 
        -1)
    },
    dhx_cal_next_button: function(a, b) {
        scheduler.setCurrentView(scheduler.date.add(scheduler.date[scheduler._mode + "_start"](scheduler._date), b || 1, scheduler._mode))
    },
    dhx_cal_today_button: function() {
        scheduler.setCurrentView(new Date)
    },
    dhx_cal_tab: function() {
        var a = this.getAttribute("name"),
        b = a.substring(0, a.search("_tab"));
        scheduler.setCurrentView(scheduler._date, b)
    },
    buttons: {
        "delete": function(a) {
            var b = scheduler.locale.labels.confirm_deleting;
            scheduler._dhtmlx_confirm(b, scheduler.locale.labels.title_confirm_deleting, 
            function() {
                scheduler.deleteEvent(a)
            })
        },
        edit: function(a) {
            scheduler.edit(a)
        },
        save: function() {
            scheduler.editStop(!0)
        },
        details: function(a) {
            scheduler.showLightbox(a)
        },
        cancel: function() {
            scheduler.editStop(!1)
        }
    }
};
scheduler._dhtmlx_confirm = function(a, b, c) {
    if (!a) return c();
    var d = {
        text: a
    };
    if (b) d.title = b;
    if (c) d.callback = function(a) {
        a && c()
    };
    dhtmlx.confirm(d)
};
scheduler.addEventNow = function(a, b, c) {
    var d = {};
    a && a.constructor.toString().match(/object/i) !== null && (d = a, a = null);
    var e = (this.config.event_duration || this.config.time_step) * 6E4;
    a || (a = Math.round((new Date).valueOf() / e) * e);
    var f = new Date(a);
    if (!b) {
        var g = this.config.first_hour;
        g > f.getHours() && (f.setHours(g), a = f.valueOf());
        b = a.valueOf() + e
    }
    var h = new Date(b);
    f.valueOf() == h.valueOf() && h.setTime(h.valueOf() + e);
    d.start_date = d.start_date || f;
    d.end_date = d.end_date || h;
    d.text = d.text || this.locale.labels.new_event;
    d.id = this._drag_id = this.uid();
    this._drag_mode = "new-size";
    this._loading = !0;
    this.addEvent(d);
    this.callEvent("onEventCreated", [this._drag_id, c]);
    this._loading = !1;
    this._drag_event = {};
    this._on_mouse_up(c)
};
scheduler._on_dbl_click = function(a, b) {
    b = b || a.target || a.srcElement;
    if (!this.config.readonly) {
        var c = b.className.split(" ")[0];
        switch (c) {
        case "dhx_scale_holder":
        case "dhx_scale_holder_now":
        case "dhx_month_body":
        case "dhx_wa_day_data":
        case "dhx_marked_timespan":
            if (!scheduler.config.dblclick_create) break;
            this.addEventNow(this.getActionData(a).date, null, a);
            break;
        case "dhx_cal_event":
        case "dhx_wa_ev_body":
        case "dhx_agenda_line":
        case "dhx_grid_event":
        case "dhx_cal_event_line":
        case "dhx_cal_event_clear":
            var d = 
            this._locate_event(b);
            if (!this.callEvent("onDblClick", [d, a])) break;
            this.config.details_on_dblclick || this._table_view || !this.getEvent(d)._timed || !this.config.select ? this.showLightbox(d) : this.edit(d);
            break;
        case "dhx_time_block":
        case "dhx_cal_container":
            break;
        default:
            var e = this["dblclick_" + c];
            if (e) e.call(this, a);
            else if (b.parentNode && b != this) return scheduler._on_dbl_click(a, b.parentNode)
        }
    }
};
scheduler._mouse_coords = function(a) {
    var b,
    c = document.body,
    d = document.documentElement;
    b = a.pageX || a.pageY ? {
        x: a.pageX,
        y: a.pageY
    }: {
        x: a.clientX + (c.scrollLeft || d.scrollLeft || 0) - c.clientLeft,
        y: a.clientY + (c.scrollTop || d.scrollTop || 0) - c.clientTop
    };
    b.x -= getAbsoluteLeft(this._obj) + (this._table_view ? 0: this.xy.scale_width);
    b.y -= getAbsoluteTop(this._obj) + this.xy.nav_height + (this._dy_shift || 0) + this.xy.scale_height - this._els.dhx_cal_data[0].scrollTop;
    b.ev = a;
    var e = this["mouse_" + this._mode];
    if (e) return e.call(this, 
    b);
    if (this._table_view) {
        if (!this._cols || !this._colsS) return b;
        for (var f = 0, f = 1; f < this._colsS.heights.length; f++) if (this._colsS.heights[f] > b.y) break;
        b.y = Math.ceil((Math.max(0, b.x / this._cols[0]) + Math.max(0, f - 1) * 7) * 1440 / this.config.time_step);
        if (scheduler._drag_mode || this._mode == "month") b.y = (Math.max(0, Math.ceil(b.x / this._cols[0]) - 1) + Math.max(0, f - 1) * 7) * 1440 / this.config.time_step;
        b.x = 0
    } else b.x = Math.min(this._cols.length - 1, Math.max(0, Math.ceil(b.x / this._cols[0]) - 1)),
    b.y = Math.max(0, Math.ceil(b.y * 60 / (this.config.time_step * 
    this.config.hour_size_px)) - 1) + this.config.first_hour * (60 / this.config.time_step);
    return b
};
scheduler._close_not_saved = function() {
    if ((new Date).valueOf() - (scheduler._new_event || 0) > 500 && scheduler._edit_id) {
        var a = scheduler.locale.labels.confirm_closing;
        scheduler._dhtmlx_confirm(a, scheduler.locale.labels.title_confirm_closing, 
        function() {
            scheduler.editStop(scheduler.config.positive_closing)
        })
    }
};
scheduler._correct_shift = function(a, b) {
    return a -= ((new Date(scheduler._min_date)).getTimezoneOffset() - (new Date(a)).getTimezoneOffset()) * 6E4 * (b ? -1: 1)
};
scheduler._on_mouse_move = function(a) {
    if (this._drag_mode) {
        var b = this._mouse_coords(a);
        if (!this._drag_pos || b.custom || this._drag_pos.x != b.x || this._drag_pos.y != b.y) {
            var c,
            d;
            this._edit_id != this._drag_id && this._close_not_saved();
            this._drag_pos = b;
            if (this._drag_mode == "create") {
                this._close_not_saved();
                this._loading = !0;
                c = this._get_date_from_pos(b).valueOf();
                var e = this.callEvent("onBeforeEventCreated", [a]);
                if (!e) return;
                if (!this._drag_start) {
                    this._drag_start = c;
                    return
                }
                d = c;
                if (d == this._drag_start) return;
                this._drag_id = 
                this.uid();
                this.addEvent(new Date(this._drag_start), new Date(d), this.locale.labels.new_event, this._drag_id, b.fields);
                this.callEvent("onEventCreated", [this._drag_id, a]);
                this._loading = !1;
                this._drag_mode = "new-size"
            }
            var f = this.getEvent(this._drag_id);
            if (this._drag_mode == "move") c = this._min_date.valueOf() + (b.y * this.config.time_step + b.x * 1440) * 6E4,
            !b.custom && this._table_view && (c += this.date.time_part(f.start_date) * 1E3),
            c = this._correct_shift(c),
            d = f.end_date.valueOf() - (f.start_date.valueOf() - c);
            else {
                c = f.start_date.valueOf();
                d = f.end_date.valueOf();
                if (this._table_view) {
                    var g = this._min_date.valueOf() + b.y * this.config.time_step * 6E4 + (b.custom ? 0: 864E5);
                    this._mode == "month" && (g = this._correct_shift(g, !1));
                    b.resize_from_start ? c = g: d = g
                } else if (d = this.date.date_part(new Date(f.end_date)).valueOf() + b.y * this.config.time_step * 6E4, this._els.dhx_cal_data[0].style.cursor = "s-resize", this._mode == "week" || this._mode == "day") d = this._correct_shift(d);
                if (this._drag_mode == "new-size") if (d <= this._drag_start) {
                    var h = b.shift || (this._table_view && !b.custom ? 
                    864E5: 0);
                    c = d - (b.shift ? 0: h);
                    d = this._drag_start + (h || this.config.time_step * 6E4)
                } else c = this._drag_start;
                else d <= c && (d = c + this.config.time_step * 6E4)
            }
            var k = new Date(d - 1),
            i = new Date(c);
            if (this._table_view || k.getDate() == i.getDate() && k.getHours() < this.config.last_hour || scheduler._allow_dnd) if (f.start_date = i, f.end_date = new Date(d), this.config.update_render) {
                var j = scheduler._els.dhx_cal_data[0].scrollTop;
                this.update_view();
                scheduler._els.dhx_cal_data[0].scrollTop = j
            } else this.updateEvent(this._drag_id);
            this._table_view && 
            this.for_rendered(this._drag_id, 
            function(a) {
                a.className += " dhx_in_move"
            })
        }
    } else if (scheduler.checkEvent("onMouseMove")) {
        var m = this._locate_event(a.target || a.srcElement);
        this.callEvent("onMouseMove", [m, a])
    }
};
scheduler._on_mouse_down = function(a, b) {
    if (!this.config.readonly && !this._drag_mode) {
        var b = b || a.target || a.srcElement,
        c = b.className && b.className.split(" ")[0];
        switch (c) {
        case "dhx_cal_event_line":
        case "dhx_cal_event_clear":
            if (this._table_view) this._drag_mode = "move";
            break;
        case "dhx_event_move":
        case "dhx_wa_ev_body":
            this._drag_mode = "move";
            break;
        case "dhx_event_resize":
            this._drag_mode = "resize";
            break;
        case "dhx_scale_holder":
        case "dhx_scale_holder_now":
        case "dhx_month_body":
        case "dhx_matrix_cell":
        case "dhx_marked_timespan":
            this._drag_mode = 
            "create";
            this.unselect(this._select_id);
            break;
        case "":
            if (b.parentNode) return scheduler._on_mouse_down(a, b.parentNode);
        default:
            if (scheduler.checkEvent("onMouseDown") && scheduler.callEvent("onMouseDown", [c]) && b.parentNode && b != this) return scheduler._on_mouse_down(a, b.parentNode);
            this._drag_id = this._drag_mode = null
        }
        if (this._drag_mode) {
            var d = this._locate_event(b); ! this.config["drag_" + this._drag_mode] || !this.callEvent("onBeforeDrag", [d, this._drag_mode, a]) ? this._drag_mode = this._drag_id = 0: (this._drag_id = d, 
            this._drag_event = scheduler._lame_clone(this.getEvent(this._drag_id) || {}))
        }
        this._drag_start = null
    }
};
scheduler._on_mouse_up = function(a) {
	//alert("鼠标动作");
    if (this._drag_mode && this._drag_id) {
        this._els.dhx_cal_data[0].style.cursor = "default";
        var b = this.getEvent(this._drag_id);
        if (this._drag_event._dhx_changed || !this._drag_event.start_date || b.start_date.valueOf() != this._drag_event.start_date.valueOf() || b.end_date.valueOf() != this._drag_event.end_date.valueOf()) {
            var c = this._drag_mode == "new-size";
            if (this.callEvent("onBeforeEventChanged", [b, a, c, this._drag_event])) {
                var d = this._drag_id;
                this._drag_id = this._drag_mode = null;
                if (c && 
                this.config.edit_on_create) {
                    this.unselect();
                    this._new_event = new Date;
                    if (this._table_view || this.config.details_on_create || !this.config.select) return this.showLightbox(d);
                    this._drag_pos = !0;
                    this._select_id = this._edit_id = d
                } else this._new_event || this.callEvent(c ? "onEventAdded": "onEventChanged", [d, this.getEvent(d)])
            } else c ? this.deleteEvent(b.id, !0) : (this._drag_event._dhx_changed = !1, scheduler._lame_copy(b, this._drag_event), this.updateEvent(b.id))
        }
        this._drag_pos && this.render_view_data()
    }
    this._drag_pos = this._drag_mode = 
    this._drag_id = null
};
scheduler.update_view = function() {
    this._reset_scale();
    if (this._load_mode && this._load()) return this._render_wait = !0;
    this.render_view_data()
};
scheduler.updateView = function(a, b) {
    var a = a || this._date,
    b = b || this._mode,
    c = "dhx_cal_data";
    this._mode ? this._obj.className = this._obj.className.replace("dhx_scheduler_" + this._mode, "dhx_scheduler_" + b) : this._obj.className += " dhx_scheduler_" + b;
    var d = this._mode == b && this.config.preserve_scroll ? this._els[c][0].scrollTop: !1;
    if (this[this._mode + "_view"] && b && this._mode != b) this[this._mode + "_view"](!1);
    this._close_not_saved();
    var e = "dhx_multi_day";
    this._els[e] && (this._els[e][0].parentNode.removeChild(this._els[e][0]), 
    this._els[e] = null);
    this._mode = b;
    this._date = a;
    this._table_view = this._mode == "month";
    for (var f = this._els.dhx_cal_tab, g = 0; g < f.length; g++) {
        var h = f[g].className,
        h = h.replace(/ active/g, "");
        f[g].getAttribute("name") == this._mode + "_tab" && (h += " active");
        f[g].className = h
    }
    var k = this[this._mode + "_view"];
    k ? k(!0) : this.update_view();
    if (typeof d == "number") this._els[c][0].scrollTop = d
};
scheduler.setCurrentView = function(a, b) {
    this.callEvent("onBeforeViewChange", [this._mode, this._date, b, a]) && (this.updateView(a, b), this.callEvent("onViewChange", [this._mode, this._date]))
};
scheduler._render_x_header = function(a, b, c, d) {
    var e = document.createElement("DIV");
    e.className = "dhx_scale_bar";
    this.set_xy(e, this._cols[a] - 1, this.xy.scale_height - 2, b, 0);
    e.innerHTML = this.templates[this._mode + "_scale_date"](c, this._mode);
    d.appendChild(e)
};
scheduler._reset_scale = function() {
    if (this.templates[this._mode + "_date"]) {
        var a = this._els.dhx_cal_header[0],
        b = this._els.dhx_cal_data[0],
        c = this.config;
        a.innerHTML = "";
        b.scrollTop = 0;
        b.innerHTML = "";
        var d = (c.readonly || !c.drag_resize ? " dhx_resize_denied": "") + (c.readonly || !c.drag_move ? " dhx_move_denied": "");
        if (d) b.className = "dhx_cal_data" + d;
        this._scales = {};
        this._cols = [];
        this._colsS = {
            height: 0
        };
        this._dy_shift = 0;
        this.set_sizes();
        var e = parseInt(a.style.width, 10),
        f = 0,
        g,
        h,
        k,
        i;
        h = this.date[this._mode + "_start"](new Date(this._date.valueOf()));
        g = k = this._table_view ? scheduler.date.week_start(h) : h;
        i = this.date.date_part(new Date);
        var j = scheduler.date.add(h, 1, this._mode),
        m = 7;
        if (!this._table_view) {
            var p = this.date["get_" + this._mode + "_end"];
            p && (j = p(h));
            m = Math.round((j.valueOf() - h.valueOf()) / 864E5)
        }
        this._min_date = g;
        this._els.dhx_cal_date[0].innerHTML = this.templates[this._mode + "_date"](h, j, this._mode);
        for (var l = 0; l < m; l++) {
            this._cols[l] = Math.floor(e / (m - l));
            this._render_x_header(l, f, g, a);
            if (!this._table_view) {
                var n = document.createElement("DIV"),
                r = 
                "dhx_scale_holder";
                g.valueOf() == i.valueOf() && (r = "dhx_scale_holder_now");
                n.className = r + " " + this.templates.week_date_class(g, i);
                this.set_xy(n, this._cols[l] - 1, c.hour_size_px * (c.last_hour - c.first_hour), f + this.xy.scale_width + 1, 0);
                b.appendChild(n);
                this.callEvent("onScaleAdd", [n, g])
            }
            g = this.date.add(g, 1, "day");
            e -= this._cols[l];
            f += this._cols[l];
            this._colsS[l] = (this._cols[l - 1] || 0) + (this._colsS[l - 1] || (this._table_view ? 0: this.xy.scale_width + 2));
            this._colsS.col_length = m + 1
        }
        this._max_date = g;
        this._colsS[m] = this._cols[m - 
        1] + this._colsS[m - 1];
        if (this._table_view) this._reset_month_scale(b, h, k);
        else if (this._reset_hours_scale(b, h, k), c.multi_day) {
            var o = "dhx_multi_day";
            this._els[o] && (this._els[o][0].parentNode.removeChild(this._els[o][0]), this._els[o] = null);
            var q = this._els.dhx_cal_navline[0],
            s = q.offsetHeight + this._els.dhx_cal_header[0].offsetHeight + 1,
            t = document.createElement("DIV");
            t.className = o;
            t.style.visibility = "hidden";
            this.set_xy(t, this._colsS[this._colsS.col_length - 1] + this.xy.scroll_width, 0, 0, s);
            b.parentNode.insertBefore(t, 
            b);
            var u = t.cloneNode(!0);
            u.className = o + "_icon";
            u.style.visibility = "hidden";
            this.set_xy(u, this.xy.scale_width, 0, 0, s);
            t.appendChild(u);
            this._els[o] = [t, u];
            this._els[o][0].onclick = this._click.dhx_cal_data
        }
    }
};
scheduler._reset_hours_scale = function(a) {
    var b = document.createElement("DIV");
    b.className = "dhx_scale_holder";
    for (var c = new Date(1980, 1, 1, this.config.first_hour, 0, 0), d = this.config.first_hour * 1; d < this.config.last_hour; d++) {
        var e = document.createElement("DIV");
        e.className = "dhx_scale_hour";
        e.style.height = this.config.hour_size_px - (this._quirks ? 0: 1) + "px";
        e.style.width = this.xy.scale_width + "px";
        e.innerHTML = scheduler.templates.hour_scale(c);
        b.appendChild(e);
        c = this.date.add(c, 1, "hour")
    }
    a.appendChild(b);
    if (this.config.scroll_hour) a.scrollTop = 
    this.config.hour_size_px * (this.config.scroll_hour - this.config.first_hour)
};
scheduler._reset_month_scale = function(a, b, c) {
    var d = scheduler.date.add(b, 1, "month"),
    e = new Date;
    this.date.date_part(e);
    this.date.date_part(c);
    var f = Math.ceil(Math.round((d.valueOf() - c.valueOf()) / 864E5) / 7),
    g = [],
    h = Math.floor(a.clientHeight / f) - 22;
    this._colsS.height = h + 22;
    for (var k = this._colsS.heights = [], i = 0; i <= 7; i++) g[i] = " style='height:" + h + "px; width:" + ((this._cols[i] || 0) - 1) + "px;' ";
    var j = 0;
    this._min_date = c;
    for (var m = "<table cellpadding='0' cellspacing='0'>", p = [], i = 0; i < f; i++) {
        m += "<tr>";
        for (var l = 0; l < 7; l++) {
            m += 
            "<td";
            var n = "";
            c < b ? n = "dhx_before": c >= d ? n = "dhx_after": c.valueOf() == e.valueOf() && (n = "dhx_now");
            m += " class='" + n + " " + this.templates.month_date_class(c, e) + "' ";
            m += "><div class='dhx_month_head'>" + this.templates.month_day(c) + "</div><div class='dhx_month_body' " + g[l] + "></div></td>";
            p.push(c);
            c = this.date.add(c, 1, "day")
        }
        m += "</tr>";
        k[i] = j;
        j += this._colsS.height
    }
    m += "</table>";
    this._max_date = c;
    a.innerHTML = m;
    this._scales = {};
    for (var r = a.getElementsByTagName("div"), i = 0; i < p.length; i++) {
        var o = r[i * 2 + 1],
        q = p[i];
        this._scales[ + q] = 
        o
    }
    for (i = 0; i < p.length; i++) q = p[i],
    this.callEvent("onScaleAdd", [this._scales[ + q], q]);
    return c
};
scheduler.getLabel = function(a, b) {
    for (var c = this.config.lightbox.sections, d = 0; d < c.length; d++) if (c[d].map_to == a) for (var e = c[d].options, f = 0; f < e.length; f++) if (e[f].key == b) return e[f].label;
    return ""
};
scheduler.updateCollection = function(a, b) {
    var c = scheduler.serverList(a);
    if (!c) return ! 1;
    c.splice(0, c.length);
    c.push.apply(c, b || []);
    scheduler.callEvent("onOptionsLoad", []);
    scheduler.resetLightbox();
    return ! 0
};
scheduler._lame_clone = function(a, b) {
    var c,
    d,
    e,
    b = b || [];
    for (c = 0; c < b.length; c += 2) if (a === b[c]) return b[c + 1];
    if (a && typeof a == "object") {
        e = {};
        d = [Array, Date, Number, String, Boolean];
        for (c = 0; c < d.length; c++) a instanceof d[c] && (e = c ? new d[c](a) : new d[c]);
        b.push(a, e);
        for (c in a) Object.prototype.hasOwnProperty.apply(a, [c]) && (e[c] = scheduler._lame_clone(a[c], b))
    }
    return e || a
};
scheduler._lame_copy = function(a, b) {
    for (var c in b) b.hasOwnProperty(c) && (a[c] = b[c]);
    return a
};
scheduler._get_date_from_pos = function(a) {
    var b = this._min_date.valueOf() + (a.y * this.config.time_step + (this._table_view ? 0: a.x) * 1440) * 6E4;
    return new Date(this._correct_shift(b))
};
scheduler.getActionData = function(a) {
    var b = this._mouse_coords(a);
    return {
        date: this._get_date_from_pos(b),
        section: b.section
    }
};
scheduler.date = {
    init: function() {
        for (var a = scheduler.locale.date.month_short, b = scheduler.locale.date.month_short_hash = {},
        c = 0; c < a.length; c++) b[a[c]] = c;
        a = scheduler.locale.date.month_full;
        b = scheduler.locale.date.month_full_hash = {};
        for (c = 0; c < a.length; c++) b[a[c]] = c
    },
    date_part: function(a) {
        a.setHours(0);
        a.setMinutes(0);
        a.setSeconds(0);
        a.setMilliseconds(0);
        a.getHours() != 0 && a.setTime(a.getTime() + 36E5 * (24 - a.getHours()));
        return a
    },
    time_part: function(a) {
        return (a.valueOf() / 1E3 - a.getTimezoneOffset() * 60) % 86400
    },
    week_start: function(a) {
        var b = a.getDay();
        scheduler.config.start_on_monday && (b === 0 ? b = 6: b--);
        return this.date_part(this.add(a, -1 * b, "day"))
    },
    month_start: function(a) {
        a.setDate(1);
        return this.date_part(a)
    },
    year_start: function(a) {
        a.setMonth(0);
        return this.month_start(a)
    },
    day_start: function(a) {
        return this.date_part(a)
    },
    add: function(a, b, c) {
        var d = new Date(a.valueOf());
        switch (c) {
        case "week":
            b *= 7;
        case "day":
            d.setDate(d.getDate() + b); ! a.getHours() && d.getHours() && d.setTime(d.getTime() + 36E5 * (24 - d.getHours()));
            break;
            case "month":
            var e = d.getMonth() + b,
            f = new Date(d);
            d.setMonth(e);
            d.getMonth() != e && d.getMonth() != e % 12 && (f.setDate(1), f.setMonth(e), f.setDate((new Date(f.getFullYear(), e + 1, 0)).getDate()), d = f);
            break;
        case "year":
            d.setYear(d.getFullYear() + b);
            break;
        case "hour":
            d.setHours(d.getHours() + b);
            break;
        case "minute":
            d.setMinutes(d.getMinutes() + b);
            break;
        default:
            return scheduler.date["add_" + c](a, b, c)
        }
        return d
    },
    to_fixed: function(a) {
        return a < 10 ? "0" + a: a
    },
    copy: function(a) {
        return new Date(a.valueOf())
    },
    date_to_str: function(a, 
    b) {
        a = a.replace(/%[a-zA-Z]/g, 
        function(a) {
            switch (a) {
            case "%d":
                return '"+scheduler.date.to_fixed(date.getDate())+"';
            case "%m":
                return '"+scheduler.date.to_fixed((date.getMonth()+1))+"';
            case "%j":
                return '"+date.getDate()+"';
            case "%n":
                return '"+(date.getMonth()+1)+"';
            case "%y":
                return '"+scheduler.date.to_fixed(date.getFullYear()%100)+"';
            case "%Y":
                return '"+date.getFullYear()+"';
            case "%D":
                return '"+scheduler.locale.date.day_short[date.getDay()]+"';
            case "%l":
                return '"+scheduler.locale.date.day_full[date.getDay()]+"';
                case "%M":
                return '"+scheduler.locale.date.month_short[date.getMonth()]+"';
            case "%F":
                return '"+scheduler.locale.date.month_full[date.getMonth()]+"';
            case "%h":
                return '"+scheduler.date.to_fixed((date.getHours()+11)%12+1)+"';
            case "%g":
                return '"+((date.getHours()+11)%12+1)+"';
            case "%G":
                return '"+date.getHours()+"';
            case "%H":
                return '"+scheduler.date.to_fixed(date.getHours())+"';
            case "%i":
                return '"+scheduler.date.to_fixed(date.getMinutes())+"';
            case "%a":
                return '"+(date.getHours()>11?"pm":"am")+"';
            case "%A":
                return '"+(date.getHours()>11?"PM":"AM")+"';
                case "%s":
                return '"+scheduler.date.to_fixed(date.getSeconds())+"';
            case "%W":
                return '"+scheduler.date.to_fixed(scheduler.date.getISOWeek(date))+"';
            default:
                return a
            }
        });
        b && (a = a.replace(/date\.get/g, "date.getUTC"));
        return new Function("date", 'return "' + a + '";')
    },
    str_to_date: function(a, b) {
        for (var c = "var temp=date.match(/[a-zA-Z]+|[0-9]+/g);", d = a.match(/%[a-zA-Z]/g), e = 0; e < d.length; e++) switch (d[e]) {
        case "%j":
        case "%d":
            c += "set[2]=temp[" + e + "]||1;";
            break;
        case "%n":
        case "%m":
            c += "set[1]=(temp[" + e + "]||1)-1;";
            break;
            case "%y":
            c += "set[0]=temp[" + e + "]*1+(temp[" + e + "]>50?1900:2000);";
            break;
        case "%g":
        case "%G":
        case "%h":
        case "%H":
            c += "set[3]=temp[" + e + "]||0;";
            break;
        case "%i":
            c += "set[4]=temp[" + e + "]||0;";
            break;
        case "%Y":
            c += "set[0]=temp[" + e + "]||0;";
            break;
        case "%a":
        case "%A":
            c += "set[3]=set[3]%12+((temp[" + e + "]||'').toLowerCase()=='am'?0:12);";
            break;
        case "%s":
            c += "set[5]=temp[" + e + "]||0;";
            break;
        case "%M":
            c += "set[1]=scheduler.locale.date.month_short_hash[temp[" + e + "]]||0;";
            break;
        case "%F":
            c += "set[1]=scheduler.locale.date.month_full_hash[temp[" + 
            e + "]]||0;"
        }
        var f = "set[0],set[1],set[2],set[3],set[4],set[5]";
        b && (f = " Date.UTC(" + f + ")");
        return new Function("date", "var set=[0,0,1,0,0,0]; " + c + " return new Date(" + f + ");")
    },
    getISOWeek: function(a) {
        if (!a) return ! 1;
        var b = a.getDay();
        b === 0 && (b = 7);
        var c = new Date(a.valueOf());
        c.setDate(a.getDate() + (4 - b));
        var d = c.getFullYear(),
        e = Math.round((c.getTime() - (new Date(d, 0, 1)).getTime()) / 864E5),
        f = 1 + Math.floor(e / 7);
        return f
    },
    getUTCISOWeek: function(a) {
        return this.getISOWeek(a)
    },
    convert_to_utc: function(a) {
        return new Date(a.getUTCFullYear(), 
        a.getUTCMonth(), a.getUTCDate(), a.getUTCHours(), a.getUTCMinutes(), a.getUTCSeconds())
    }
};
scheduler.locale = {
    date: {
        month_full: "一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月".split(","),
        month_short: "一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月".split(","),
        day_full: "星期日,星期一,星期二,星期三,星期四,星期五,星期六".split(","),
        day_short: "周日,周一,周二,周三,周四,周五,周六".split(",")
    },
    labels: {
        dhx_cal_today_button: "今天",
        day_tab: "天",
        week_tab: "周",
        month_tab: "月",
        new_event: "",
        icon_save: "保存",
        icon_cancel: "取消",
        icon_details: "详细",
        icon_edit: "修改",
        icon_delete: "删除",
        confirm_closing: "确认关闭?",
        confirm_deleting: "您确定要删除吗?",
        section_type: "项目",
        section_typef: "<input type='radio' name='d' checked='true' id='2s'>项目名称",
        section_typea: "<input type='radio' name='d' id='1s'>其他",
        section_description: "备注",
        section_phone: "联系电话",
        section_teacher: "指导老师",
        section_dean: "系主任",
        section_property: "申请性质",
        section_research:"课题组(*必填)",
        section_time: "申请时间",
        full_day: "全天",
        confirm_recurring: "你确定要重复?",
        section_recurring: "重复",
        button_recurring: "关闭",
        button_recurring_open: "开启",
        button_edit_series: "修改系列",
        button_edit_occurrence: "Edit occurrence",
        agenda_tab: "Agenda",
        date: "Date",
        description: "Description",
        year_tab: "年",
        week_agenda_tab: "周列表",
        grid_tab: "Grid"
    }
};
scheduler.config = {
    default_date: "%j %M %Y",
    month_date: "%F %Y",
    load_date: "%Y-%m-%d",
    week_date: "%l",
    day_date: "%D, %F %j",
    hour_date: "%H:%i",
    month_day: "%d",
    xml_date: "%Y-%m-%d %H:%i",
    api_date: "%Y-%m-%d %H:%i",
    hour_size_px: 42,
    time_step: 5,
    start_on_monday: 1,
    first_hour: 0,
    last_hour: 24,
    readonly: !1,
    drag_resize: 1,
    drag_move: 1,
    drag_create: 1,
    dblclick_create: 1,
    edit_on_create: 1,
    details_on_create: 0,
    click_form_details: 0,
    cascade_event_display: !1,
    cascade_event_count: 4,
    cascade_event_margin: 30,
    drag_lightbox: !0,
    preserve_scroll: !0,
    select: !0,
    server_utc: !1,
    positive_closing: !1,
    icons_edit: ["icon_save", "icon_cancel"],
    icons_select: ["icon_details", "icon_edit", "icon_delete"],
    buttons_left: ["dhx_save_btn", "dhx_cancel_btn"],
    buttons_right: ["dhx_delete_btn"],
    lightbox: {
        sections: [{
            name: "description",
            height: 150,
            map_to: "text",
            type: "textarea",
            focus: !0
        },
        {
            name: "time",
            height: 72,
            type: "calendar_time",
            map_to: "auto"
        }]
    },
    highlight_displayed_event: !0,
    displayed_event_color: "#ffc5ab",
    displayed_event_text_color: "#7e2727"
};
scheduler.templates = {};
scheduler.init_templates = function() {
    var a = scheduler.locale.labels;
    a.dhx_save_btn = a.icon_save;
    a.dhx_cancel_btn = a.icon_cancel;
    a.dhx_delete_btn = a.icon_delete;
    var b = scheduler.date.date_to_str,
    c = scheduler.config,
    d = function(a, b) {
        for (var c in b) a[c] || (a[c] = b[c])
    };
    d(scheduler.templates, {
        day_date: b(c.default_date),
        month_date: b(c.month_date),
        week_date: function(a, b) {
            return scheduler.templates.day_date(a) + " &ndash; " + scheduler.templates.day_date(scheduler.date.add(b, -1, "day"))
        },
        day_scale_date: b(c.default_date),
        month_scale_date: b(c.week_date),
        week_scale_date: b(c.day_date),
        hour_scale: b(c.hour_date),
        time_picker: b(c.hour_date),
        event_date: b(c.hour_date),
        month_day: b(c.month_day),
        xml_date: scheduler.date.str_to_date(c.xml_date, c.server_utc),
        load_format: b(c.load_date, c.server_utc),
        xml_format: b(c.xml_date, c.server_utc),
        api_date: scheduler.date.str_to_date(c.api_date),
        event_header: function(a, b) {
            return scheduler.templates.event_date(a) + " - " + scheduler.templates.event_date(b)
        },
        event_text: function(a, b, c) {
            return c.text
        },
        event_class: function() {
            return ""
        },
        month_date_class: function() {
            return ""
        },
        week_date_class: function() {
            return ""
        },
        event_bar_date: function(a) {
            return scheduler.templates.event_date(a) + " "
        },
        event_bar_text: function(a, b, c) {
            return c.text
        }
    });
    this.callEvent("onTemplatesReady", [])
};
scheduler.uid = function() {
    if (!this._seed) this._seed = (new Date).valueOf();
    return this._seed++
};
scheduler._events = {};
scheduler.clearAll = function() {
    this._events = {};
    this._loaded = {};
    this.clear_view()
};
scheduler.addEvent = function(a, b, c, d, e) {
    if (!arguments.length) return this.addEventNow();
    var f = a;
    if (arguments.length != 1) f = e || {},
    f.start_date = a,
    f.end_date = b,
    f.text = c,
    f.id = d;
    f.id = f.id || scheduler.uid();
    f.text = f.text || "";
    if (typeof f.start_date == "string") f.start_date = this.templates.api_date(f.start_date);
    if (typeof f.end_date == "string") f.end_date = this.templates.api_date(f.end_date);
    var g = (this.config.event_duration || this.config.time_step) * 6E4;
    f.start_date.valueOf() == f.end_date.valueOf() && f.end_date.setTime(f.end_date.valueOf() + 
    g);
    f._timed = this.is_one_day_event(f);
    var h = !this._events[f.id];
    this._events[f.id] = f;
    this.event_updated(f);
    this._loading || this.callEvent(h ? "onEventAdded": "onEventChanged", [f.id, f]);
    return f.id
};
scheduler.deleteEvent = function(a, b) {
    var c = this._events[a];
    if (b || this.callEvent("onBeforeEventDelete", [a, c]) && this.callEvent("onConfirmedBeforeEventDelete", [a, c])) c && (delete this._events[a], this.unselect(a), this.event_updated(c)),
    this.callEvent("onEventDeleted", [a, c])
};
scheduler.getEvent = function(a) {
    return this._events[a]
};
scheduler.setEvent = function(a, b) {
    this._events[a] = b
};
scheduler.for_rendered = function(a, b) {
    for (var c = this._rendered.length - 1; c >= 0; c--) this._rendered[c].getAttribute("event_id") == a && b(this._rendered[c], c)
};
scheduler.changeEventId = function(a, b) {
    if (a != b) {
        var c = this._events[a];
        if (c) c.id = b,
        this._events[b] = c,
        delete this._events[a];
        this.for_rendered(a, 
        function(a) {
            a.setAttribute("event_id", b)
        });
        if (this._select_id == a) this._select_id = b;
        if (this._edit_id == a) this._edit_id = b;
        this.callEvent("onEventIdChange", [a, b])
    }
};
 (function() {
    for (var a = "text,Text,start_date,StartDate,end_date,EndDate".split(","), b = function(a) {
        return function(b) {
            return scheduler.getEvent(b)[a]
        }
    },
    c = function(a) {
        return function(b, c) {
            var d = scheduler.getEvent(b);
            d[a] = c;
            d._changed = !0;
            d._timed = this.is_one_day_event(d);
            scheduler.event_updated(d, !0)
        }
    },
    d = 0; d < a.length; d += 2) scheduler["getEvent" + a[d + 1]] = b(a[d]),
    scheduler["setEvent" + a[d + 1]] = c(a[d])
})();
scheduler.event_updated = function(a) {
    this.is_visible_events(a) ? this.render_view_data() : this.clear_event(a.id)
};
scheduler.is_visible_events = function(a) {
    return a.start_date < this._max_date && this._min_date < a.end_date
};
scheduler.is_one_day_event = function(a) {
    var b = a.end_date.getDate() - a.start_date.getDate();
    return b ? (b < 0 && (b = Math.ceil((a.end_date.valueOf() - a.start_date.valueOf()) / 864E5)), b == 1 && !a.end_date.getHours() && !a.end_date.getMinutes() && (a.start_date.getHours() || a.start_date.getMinutes())) : a.start_date.getMonth() == a.end_date.getMonth() && a.start_date.getFullYear() == a.end_date.getFullYear()
};
scheduler.get_visible_events = function(a) {
    var b = [],
    c;
    for (c in this._events) this.is_visible_events(this._events[c]) && (!a || this._events[c]._timed) && this.filter_event(c, this._events[c]) && b.push(this._events[c]);
    return b
};
scheduler.filter_event = function(a, b) {
    var c = this["filter_" + this._mode];
    return c ? c(a, b) : !0
};
scheduler.render_view_data = function(a, b) {
    if (!a) {
        if (this._not_render) {
            this._render_wait = !0;
            return
        }
        this._render_wait = !1;
        this.clear_view();
        a = this.get_visible_events(!(this._table_view || this.config.multi_day))
    }
    if (this.config.multi_day && !this._table_view) {
        for (var c = [], d = [], e = 0; e < a.length; e++) a[e]._timed ? c.push(a[e]) : d.push(a[e]);
        this._rendered_location = this._els.dhx_multi_day[0];
        this._table_view = !0;
        this.render_data(d, b);
        this._table_view = !1;
        this._rendered_location = this._els.dhx_cal_data[0];
        this._table_view = 
        !1;
        this.render_data(c, b)
    } else this._rendered_location = this._els.dhx_cal_data[0],
    this.render_data(a, b)
};
scheduler.render_data = function(a, b) {
    for (var a = this._pre_render_events(a, b), c = 0; c < a.length; c++) this._table_view ? this.render_event_bar(a[c]) : this.render_event(a[c])
};
scheduler._pre_render_events = function(a, b) {
    var c = this.xy.bar_height,
    d = this._colsS.heights,
    e = this._colsS.heights = [0, 0, 0, 0, 0, 0, 0],
    f = this._els.dhx_cal_data[0],
    a = this._table_view ? this._pre_render_events_table(a, b) : this._pre_render_events_line(a, b);
    if (this._table_view) if (b) this._colsS.heights = d;
    else {
        var g = f.firstChild;
        if (g.rows) {
            for (var h = 0; h < g.rows.length; h++) {
                e[h]++;
                if (e[h] * c > this._colsS.height - 22) {
                    for (var k = g.rows[h].cells, i = 0; i < k.length; i++) k[i].childNodes[1].style.height = e[h] * c + "px";
                    e[h] = (e[h - 1] || 
                    0) + k[0].offsetHeight
                }
                e[h] = (e[h - 1] || 0) + g.rows[h].cells[0].offsetHeight
            }
            e.unshift(0);
            if (g.parentNode.offsetHeight < g.parentNode.scrollHeight && !g._h_fix) {
                for (h = 0; h < g.rows.length; h++) {
                    var j = g.rows[h].cells[6].childNodes[0],
                    m = j.offsetWidth - scheduler.xy.scroll_width + "px";
                    j.style.width = m;
                    j.nextSibling.style.width = m
                }
                g._h_fix = !0
            }
        } else if (!a.length && this._els.dhx_multi_day[0].style.visibility == "visible" && (e[0] = -1), a.length || e[0] == -1) {
            var p = g.parentNode.childNodes,
            l = (e[0] + 1) * c + 1 + "px";
            f.style.top = this._els.dhx_cal_navline[0].offsetHeight + 
            this._els.dhx_cal_header[0].offsetHeight + parseInt(l, 10) + "px";
            f.style.height = this._obj.offsetHeight - parseInt(f.style.top, 10) - (this.xy.margin_top || 0) + "px";
            var n = this._els.dhx_multi_day[0];
            n.style.height = l;
            n.style.visibility = e[0] == -1 ? "hidden": "visible";
            n = this._els.dhx_multi_day[1];
            n.style.height = l;
            n.style.visibility = e[0] == -1 ? "hidden": "visible";
            n.className = e[0] ? "dhx_multi_day_icon": "dhx_multi_day_icon_small";
            this._dy_shift = (e[0] + 1) * c;
            e[0] = 0
        }
    }
    return a
};
scheduler._get_event_sday = function(a) {
    return Math.floor((a.start_date.valueOf() - this._min_date.valueOf()) / 864E5)
};
scheduler._get_event_mapped_end_date = function(a) {
    var b = a.end_date;
    if (this.config.separate_short_events) {
        var c = (a.end_date - a.start_date) / 6E4;
        c < this._min_mapped_duration && (b = this.date.add(b, this._min_mapped_duration - c, "minute"))
    }
    return b
};
scheduler._pre_render_events_line = function(a, b) {
    a.sort(function(a, b) {
        return a.start_date.valueOf() == b.start_date.valueOf() ? a.id > b.id ? 1: -1: a.start_date > b.start_date ? 1: -1
    });
    var c = [],
    d = [];
    this._min_mapped_duration = Math.ceil(this.xy.min_event_height * 60 / this.config.hour_size_px);
    for (var e = 0; e < a.length; e++) {
        var f = a[e],
        g = f.start_date,
        h = f.end_date,
        k = g.getHours(),
        i = h.getHours();
        f._sday = this._get_event_sday(f);
        c[f._sday] || (c[f._sday] = []);
        if (!b) {
            f._inner = !1;
            for (var j = c[f._sday]; j.length;) {
                var m = j[j.length - 1],
                p = this._get_event_mapped_end_date(m);
                if (p.valueOf() <= f.start_date.valueOf()) j.splice(j.length - 1, 1);
                else break
            }
            for (var l = !1, n = 0; n < j.length; n++) if (m = j[n], p = this._get_event_mapped_end_date(m), p.valueOf() <= f.start_date.valueOf()) {
                l = !0;
                f._sorder = m._sorder;
                j.splice(n, 1);
                f._inner = !0;
                break
            }
            if (j.length) j[j.length - 1]._inner = !0;
            if (!l) if (j.length) if (j.length <= j[j.length - 1]._sorder) {
                if (j[j.length - 1]._sorder) for (n = 0; n < j.length; n++) {
                    for (var r = !1, o = 0; o < j.length; o++) if (j[o]._sorder == n) {
                        r = !0;
                        break
                    }
                    if (!r) {
                        f._sorder = 
                        n;
                        break
                    }
                } else f._sorder = 0;
                f._inner = !0
            } else {
                r = j[0]._sorder;
                for (n = 1; n < j.length; n++) if (j[n]._sorder > r) r = j[n]._sorder;
                f._sorder = r + 1;
                f._inner = !1
            } else f._sorder = 0;
            j.push(f);
            j.length > (j.max_count || 0) ? (j.max_count = j.length, f._count = j.length) : f._count = f._count ? f._count: 1
        }
        if (k < this.config.first_hour || i >= this.config.last_hour) if (d.push(f), a[e] = f = this._copy_event(f), k < this.config.first_hour && (f.start_date.setHours(this.config.first_hour), f.start_date.setMinutes(0)), i >= this.config.last_hour && (f.end_date.setMinutes(0), 
        f.end_date.setHours(this.config.last_hour)), f.start_date > f.end_date || k == this.config.last_hour) a.splice(e, 1),
        e--
    }
    if (!b) {
        for (e = 0; e < a.length; e++) a[e]._count = c[a[e]._sday].max_count;
        for (e = 0; e < d.length; e++) d[e]._count = c[d[e]._sday].max_count
    }
    return a
};
scheduler._time_order = function(a) {
    a.sort(function(a, c) {
        return a.start_date.valueOf() == c.start_date.valueOf() ? a._timed && !c._timed ? 1: !a._timed && c._timed ? -1: a.id > c.id ? 1: -1: a.start_date > c.start_date ? 1: -1
    })
};
scheduler._pre_render_events_table = function(a, b) {
    this._time_order(a);
    for (var c = [], d = [[], [], [], [], [], [], []], e = this._colsS.heights, f, g = this._cols.length, h = {},
    k = 0; k < a.length; k++) {
        var i = a[k],
        j = i.id;
        h[j] || (h[j] = {
            first_chunk: !0,
            last_chunk: !0
        });
        var m = h[j],
        p = f || i.start_date,
        l = i.end_date;
        if (p < this._min_date) m.first_chunk = !1,
        p = this._min_date;
        if (l > this._max_date) m.last_chunk = !1,
        l = this._max_date;
        var n = this.locate_holder_day(p, !1, i);
        i._sday = n % g;
        var r = this.locate_holder_day(l, !0, i) || g;
        i._eday = r % g || g;
        i._length = 
        r - n;
        i._sweek = Math.floor((this._correct_shift(p.valueOf(), 1) - this._min_date.valueOf()) / (864E5 * g));
        var o = d[i._sweek],
        q;
        for (q = 0; q < o.length; q++) if (o[q]._eday <= i._sday) break;
        if (!i._sorder || !b) i._sorder = q;
        if (i._sday + i._length <= g) f = null,
        c.push(i),
        o[q] = i,
        e[i._sweek] = o.length - 1,
        i._first_chunk = m.first_chunk,
        i._last_chunk = m.last_chunk;
        else {
            var s = this._copy_event(i);
            s.id = i.id;
            s._length = g - i._sday;
            s._eday = g;
            s._sday = i._sday;
            s._sweek = i._sweek;
            s._sorder = i._sorder;
            s.end_date = this.date.add(p, s._length, "day");
            if (s._first_chunk = 
            m.first_chunk) m.first_chunk = !1;
            c.push(s);
            o[q] = s;
            f = s.end_date;
            e[i._sweek] = o.length - 1;
            k--
        }
    }
    return c
};
scheduler._copy_dummy = function() {
    var a = new Date(this.start_date),
    b = new Date(this.end_date);
    this.start_date = a;
    this.end_date = b
};
scheduler._copy_event = function(a) {
    this._copy_dummy.prototype = a;
    return new this._copy_dummy
};
scheduler._rendered = [];
scheduler.clear_view = function() {
    for (var a = 0; a < this._rendered.length; a++) {
        var b = this._rendered[a];
        b.parentNode && b.parentNode.removeChild(b)
    }
    this._rendered = []
};
scheduler.updateEvent = function(a) {
    var b = this.getEvent(a);
    this.clear_event(a);
    if (b && this.is_visible_events(b) && this.filter_event(a, b) && (this._table_view || this.config.multi_day || b._timed)) this.config.update_render ? this.render_view_data() : this.render_view_data([b], !0)
};
scheduler.clear_event = function(a) {
    this.for_rendered(a, 
    function(a, c) {
        a.parentNode && a.parentNode.removeChild(a);
        scheduler._rendered.splice(c, 1)
    })
};
scheduler.render_event = function(a) {
    var b = scheduler.xy.menu_width;
    if (! (a._sday < 0)) {
        var c = scheduler.locate_holder(a._sday);
        if (c) {
            var d = a.start_date.getHours() * 60 + a.start_date.getMinutes(),
            e = a.end_date.getHours() * 60 + a.end_date.getMinutes() || scheduler.config.last_hour * 60,
            f = a._count || 1,
            g = a._sorder || 0,
            h = Math.round((d * 6E4 - this.config.first_hour * 36E5) * this.config.hour_size_px / 36E5) % (this.config.hour_size_px * 24),
            k = Math.max(scheduler.xy.min_event_height, (e - d) * this.config.hour_size_px / 60),
            i = Math.floor((c.clientWidth - 
            b) / f),
            j = g * i + 1;
            a._inner || (i *= f - g);
            if (this.config.cascade_event_display) var m = this.config.cascade_event_count,
            p = this.config.cascade_event_margin,
            j = g % m * p,
            l = a._inner ? (f - g - 1) % m * p / 2: 0,
            i = Math.floor(c.clientWidth - b - j - l);
            var n = this._render_v_bar(a.id, b + j, h, i, k, a._text_style, scheduler.templates.event_header(a.start_date, a.end_date, a), scheduler.templates.event_text(a.start_date, a.end_date, a));
            this._rendered.push(n);
            c.appendChild(n);
            j = j + parseInt(c.style.left, 10) + b;
            if (this._edit_id == a.id) {
                n.style.zIndex = 1;
                i = Math.max(i - 
                4, scheduler.xy.editor_width);
                n = document.createElement("DIV");
                n.setAttribute("event_id", a.id);
                this.set_xy(n, i, k - 20, j, h + 14);
                n.className = "dhx_cal_editor";
                var r = document.createElement("DIV");
                this.set_xy(r, i - 6, k - 26);
                r.style.cssText += ";margin:2px 2px 2px 2px;overflow:hidden;";
                n.appendChild(r);
                this._els.dhx_cal_data[0].appendChild(n);
                this._rendered.push(n);
                r.innerHTML = "<textarea class='dhx_cal_editor'>" + a.text + "</textarea>";
                if (this._quirks7) r.firstChild.style.height = k - 12 + "px";
                this._editor = r.firstChild;
                this._editor.onkeydown = 
                function(a) {
                    if ((a || event).shiftKey) return ! 0;
                    var b = (a || event).keyCode;
                    b == scheduler.keys.edit_save && scheduler.editStop(!0);
                    b == scheduler.keys.edit_cancel && scheduler.editStop(!1)
                };
                this._editor.onselectstart = function(a) {
                    return (a || event).cancelBubble = !0
                };
                r.firstChild.focus();
                this._els.dhx_cal_data[0].scrollLeft = 0;
                r.firstChild.select()
            }
            if (this.xy.menu_width !== 0 && this._select_id == a.id) {
                if (this.config.cascade_event_display && this._drag_mode) n.style.zIndex = 1;
                for (var o = this.config["icons_" + (this._edit_id == a.id ? 
                "edit": "select")], q = "", s = a.color ? "background-color: " + a.color + ";": "", t = a.textColor ? "color: " + a.textColor + ";": "", u = 0; u < o.length; u++) q += "<div class='dhx_menu_icon " + o[u] + "' style='" + s + "" + t + "' title='" + this.locale.labels[o[u]] + "'></div>";
                var w = this._render_v_bar(a.id, j - b + 1, h, b, o.length * 20 + 26 - 2, "", "<div style='" + s + "" + t + "' class='dhx_menu_head'></div>", q, !0);
                w.style.left = j - b + 1;
                this._els.dhx_cal_data[0].appendChild(w);
                this._rendered.push(w)
            }
        }
    }
};
scheduler._render_v_bar = function(a, b, c, d, e, f, g, h, k) {
    var i = document.createElement("DIV"),
    j = this.getEvent(a),
    m = k ? "dhx_cal_event dhx_cal_select_menu": "dhx_cal_event",
    p = scheduler.templates.event_class(j.start_date, j.end_date, j);
    p && (m = m + " " + p);
    var l = j.color ? "background:" + j.color + ";": "",
    n = j.textColor ? "color:" + j.textColor + ";": "",
    r = '<div event_id="' + a + '" class="' + m + '" style="position:absolute; top:' + c + "px; left:" + b + "px; width:" + (d - 4) + "px; height:" + e + "px;" + (f || "") + '"></div>';
    i.innerHTML = r;
    var o = i.cloneNode(!0).firstChild;
    if (!scheduler.renderEvent || k || !scheduler.renderEvent(o, j)) {
        var o = i.firstChild,
        q = '<div class="dhx_event_move dhx_header" style=" width:' + (d - 6) + "px;" + l + '" >&nbsp;</div>';
        q += '<div class="dhx_event_move dhx_title" style="' + l + "" + n + '">' + g + "</div>";
        q += '<div class="dhx_body" style=" width:' + (d - (this._quirks ? 4: 14)) + "px; height:" + (e - (this._quirks ? 20: 30) + 1) + "px;" + l + "" + n + '">' + h + "</div>";
        var s = "dhx_event_resize dhx_footer";
        k && (s = "dhx_resize_denied " + s);
        q += '<div class="' + s + '" style=" width:' + (d - 8) + "px;" + (k ? " margin-top:-1px;": 
        "") + "" + l + "" + n + '" ></div>';
        o.innerHTML = q
    }
    return o
};
scheduler.locate_holder = function(a) {
    return this._mode == "day" ? this._els.dhx_cal_data[0].firstChild: this._els.dhx_cal_data[0].childNodes[a]
};
scheduler.locate_holder_day = function(a, b) {
    var c = Math.floor((this._correct_shift(a, 1) - this._min_date) / 864E5);
    b && this.date.time_part(a) && c++;
    return c
};
scheduler.render_event_bar = function(a) {
    var b = this._rendered_location,
    c = this._colsS[a._sday],
    d = this._colsS[a._eday];
    d == c && (d = this._colsS[a._eday + 1]);
    var e = this.xy.bar_height,
    f = this._colsS.heights[a._sweek] + (this._colsS.height ? this.xy.month_scale_height + 2: 2) + a._sorder * e,
    g = document.createElement("DIV"),
    h = "dhx_cal_event_clear";
    a._timed || (h = "dhx_cal_event_line", a.hasOwnProperty("_first_chunk") && a._first_chunk && (h += " dhx_cal_event_line_start"), a.hasOwnProperty("_last_chunk") && a._last_chunk && (h += " dhx_cal_event_line_end"));
    var k = scheduler.templates.event_class(a.start_date, a.end_date, a);
    k && (h = h + " " + k);
    var i = a.color ? "background:" + a.color + ";": "",
    j = a.textColor ? "color:" + a.textColor + ";": "",
    m = '<div event_id="' + a.id + '" class="' + h + '" style="position:absolute; top:' + f + "px; left:" + c + "px; width:" + (d - c - 15) + "px;" + j + "" + i + "" + (a._text_style || "") + '">',
    a = scheduler.getEvent(a.id);
    a._timed && (m += scheduler.templates.event_bar_date(a.start_date, a.end_date, a));
    m += scheduler.templates.event_bar_text(a.start_date, a.end_date, a) + "</div>";
    m += "</div>";
    g.innerHTML = m;
    this._rendered.push(g.firstChild);
    b.appendChild(g.firstChild)
};
scheduler._locate_event = function(a) {
    for (var b = null; a && !b && a.getAttribute;) b = a.getAttribute("event_id"),
    a = a.parentNode;
    return b
};
scheduler.edit = function(a) {
    if (this._edit_id != a) this.editStop(!1, a),
    this._edit_id = a,
    this.updateEvent(a)
};
scheduler.editStop = function(a, b) {
    if (! (b && this._edit_id == b)) {
        var c = this.getEvent(this._edit_id);
        if (c) {
            if (a) c.text = this._editor.value;
            this._editor = this._edit_id = null;
            this.updateEvent(c.id);
            this._edit_stop_event(c, a)
        }
    }
};
scheduler._edit_stop_event = function(a, b) {
    this._new_event ? (b ? this.callEvent("onEventAdded", [a.id, a]) : a && this.deleteEvent(a.id, !0), this._new_event = null) : b && this.callEvent("onEventChanged", [a.id, a])
};
scheduler.getEvents = function(a, b) {
    var c = [],
    d;
    for (d in this._events) {
        var e = this._events[d];
        e && (!a && !b || e.start_date < b && e.end_date > a) && c.push(e)
    }
    return c
};
scheduler.getRenderedEvent = function(a) {
    if (a) {
        for (var b = scheduler._rendered, c = 0; c < b.length; c++) {
            var d = b[c];
            if (d.getAttribute("event_id") == a) return d
        }
        return null
    }
};
scheduler.showEvent = function(a, b) {
    var c = typeof a == "number" || typeof a == "string" ? scheduler.getEvent(a) : a,
    b = b || scheduler._mode;
    if (c && (!this.checkEvent("onBeforeEventDisplay") || this.callEvent("onBeforeEventDisplay", [c, b]))) {
        var d = scheduler.config.scroll_hour;
        scheduler.config.scroll_hour = c.start_date.getHours();
        var e = scheduler.config.preserve_scroll;
        scheduler.config.preserve_scroll = !1;
        var f = c.color,
        g = c.textColor;
        if (scheduler.config.highlight_displayed_event) c.color = scheduler.config.displayed_event_color,
        c.textColor = scheduler.config.displayed_event_text_color;
        scheduler.setCurrentView(new Date(c.start_date), b);
        c.color = f;
        c.textColor = g;
        scheduler.config.scroll_hour = d;
        scheduler.config.preserve_scroll = e;
        if (scheduler.matrix && scheduler.matrix[b]) scheduler._els.dhx_cal_data[0].scrollTop = getAbsoluteTop(scheduler.getRenderedEvent(c.id));
        scheduler.callEvent("onAfterEventDisplay", [c, b])
    }
};
scheduler._loaded = {};
scheduler._load = function(a, b) {
    a = a || this._load_url;
    a += (a.indexOf("?") == -1 ? "?": "&") + "timeshift=" + (new Date).getTimezoneOffset();
    this.config.prevent_cache && (a += "&uid=" + this.uid());
    var c,
    b = b || this._date;
    if (this._load_mode) {
        for (var d = this.templates.load_format, b = this.date[this._load_mode + "_start"](new Date(b.valueOf())); b > this._min_date;) b = this.date.add(b, -1, this._load_mode);
        c = b;
        for (var e = !0; c < this._max_date;) c = this.date.add(c, 1, this._load_mode),
        this._loaded[d(b)] && e ? b = this.date.add(b, 1, this._load_mode) : 
        e = !1;
        var f = c;
        do c = f,
        f = this.date.add(c, -1, this._load_mode);
        while (f > b && this._loaded[d(f)]);
        if (c <= b) return ! 1;
        for (dhtmlxAjax.get(a + "&from=" + d(b) + "&to=" + d(c), 
        function(a) {
            scheduler.on_load(a)
        }); b < c;) this._loaded[d(b)] = !0,
        b = this.date.add(b, 1, this._load_mode)
    } else dhtmlxAjax.get(a, 
    function(a) {
        scheduler.on_load(a)
    });
    this.callEvent("onXLS", []);
    return ! 0
};
scheduler.on_load = function(a) {
    var b;
    b = this._process ? this[this._process].parse(a.xmlDoc.responseText) : this._magic_parser(a);
    scheduler._process_loading(b);
    this.callEvent("onXLE", [])
};
scheduler._process_loading = function(a) {
    this._not_render = this._loading = !0;
    for (var b = 0; b < a.length; b++) this.callEvent("onEventLoading", [a[b]]) && this.addEvent(a[b]);
    this._not_render = !1;
    this._render_wait && this.render_view_data();
    this._loading = !1;
    this._after_call && this._after_call();
    this._after_call = null
};
scheduler.json = {};
scheduler.json.parse = function(a) {
    if (typeof a == "string") scheduler._temp = eval("(" + a + ")"),
    a = scheduler._temp ? scheduler._temp.data || scheduler._temp: [];
    if (a.dhx_security) dhtmlx.security_key = a.dhx_security;
    var b = scheduler._temp && scheduler._temp.collections ? scheduler._temp.collections: {},
    c = !1,
    d;
    for (d in b) if (b.hasOwnProperty(d)) {
        var c = !0,
        e = b[d],
        f = scheduler.serverList[d];
        if (f) {
            f.splice(0, f.length);
            for (var g = 0; g < e.length; g++) {
                var h = e[g],
                k = {
                    key: h.value,
                    label: h.label
                },
                i;
                for (i in h) if (h.hasOwnProperty(i) && !(i == 
                "value" || i == "label")) k[i] = h[i];
                f.push(k)
            }
        }
    }
    c && scheduler.callEvent("onOptionsLoad", []);
    for (var j = [], m = 0; m < a.length; m++) a[m].start_date = scheduler.templates.xml_date(a[m].start_date),
    a[m].end_date = scheduler.templates.xml_date(a[m].end_date),
    j.push(a[m]);
    return j
};
scheduler.parse = function(a, b) {
    this._process = b;
    this.on_load({
        xmlDoc: {
            responseText: a
        }
    })
};
scheduler.load = function(a, b, c) {
    if (typeof b == "string") this._process = b,
    b = c;
    this._load_url = a;
    this._after_call = b;
    this._load(a, this._date)
};
scheduler.setLoadMode = function(a) {
    a == "all" && (a = "");
    this._load_mode = a
};
scheduler.refresh = function() {
    alert("not implemented")
};
scheduler.serverList = function(a, b) {
    return b ? this.serverList[a] = b.slice(0) : this.serverList[a] = this.serverList[a] || []
};
scheduler._userdata = {};
scheduler._magic_parser = function(a) {
    var b;
    if (!a.getXMLTopNode) {
        var c = a.xmlDoc.responseText,
        a = new dtmlXMLLoaderObject(function() {});
        a.loadXMLString(c)
    }
    b = a.getXMLTopNode("data");
    if (b.tagName != "data") return [];
    var d = b.getAttribute("dhx_security");
    if (d) dhtmlx.security_key = d;
    for (var e = a.doXPath("//coll_options"), f = 0; f < e.length; f++) {
        var g = e[f].getAttribute("for"),
        h = this.serverList[g];
        if (h) {
            h.splice(0, h.length);
            for (var k = a.doXPath(".//item", e[f]), i = 0; i < k.length; i++) {
                for (var j = k[i], m = j.attributes, p = {
                    key: k[i].getAttribute("value"),
                    label: k[i].getAttribute("label")
                },
                l = 0; l < m.length; l++) {
                    var n = m[l];
                    if (! (n.nodeName == "value" || n.nodeName == "label")) p[n.nodeName] = n.nodeValue
                }
                h.push(p)
            }
        }
    }
    e.length && scheduler.callEvent("onOptionsLoad", []);
    for (var r = a.doXPath("//userdata"), f = 0; f < r.length; f++) {
        var o = this.xmlNodeToJSON(r[f]);
        this._userdata[o.name] = o.text
    }
    var q = [];
    b = a.doXPath("//event");
    for (f = 0; f < b.length; f++) q[f] = this.xmlNodeToJSON(b[f]),
    q[f].text = q[f].text || q[f]._tagvalue,
    q[f].start_date = this.templates.xml_date(q[f].start_date),
    q[f].end_date = 
    this.templates.xml_date(q[f].end_date);
    return q
};
scheduler.xmlNodeToJSON = function(a) {
    for (var b = {},
    c = 0; c < a.attributes.length; c++) b[a.attributes[c].name] = a.attributes[c].value;
    for (c = 0; c < a.childNodes.length; c++) {
        var d = a.childNodes[c];
        d.nodeType == 1 && (b[d.tagName] = d.firstChild ? d.firstChild.nodeValue: "")
    }
    if (!b.text) b.text = a.firstChild ? a.firstChild.nodeValue: "";
    return b
};
scheduler.attachEvent("onXLS", 
function() {
    if (this.config.show_loading === !0) {
        var a;
        a = this.config.show_loading = document.createElement("DIV");
        a.className = "dhx_loading";
        a.style.left = Math.round((this._x - 128) / 2) + "px";
        a.style.top = Math.round((this._y - 15) / 2) + "px";
        this._obj.appendChild(a)
    }
});
scheduler.attachEvent("onXLE", 
function() {
    var a;
    if ((a = this.config.show_loading) && typeof a == "object") this._obj.removeChild(a),
    this.config.show_loading = !0
});
scheduler.ical = {
    parse: function(a) {
        var b = a.match(RegExp(this.c_start + "[^\u000c]*" + this.c_end, ""));
        if (b.length) {
            b[0] = b[0].replace(/[\r\n]+(?=[a-z \t])/g, " ");
            b[0] = b[0].replace(/\;[^:\r\n]*/g, "");
            for (var c = [], d, e = RegExp("(?:" + this.e_start + ")([^\u000c]*?)(?:" + this.e_end + ")", "g"); d = e.exec(b);) {
                for (var f = {},
                g, h = /[^\r\n]+[\r\n]+/g; g = h.exec(d[1]);) this.parse_param(g.toString(), f);
                if (f.uid && !f.id) f.id = f.uid;
                c.push(f)
            }
            return c
        }
    },
    parse_param: function(a, b) {
        var c = a.indexOf(":");
        if (c != -1) {
            var d = a.substr(0, c).toLowerCase(),
            e = a.substr(c + 1).replace(/\\\,/g, ",").replace(/[\r\n]+$/, "");
            d == "summary" ? d = "text": d == "dtstart" ? (d = "start_date", e = this.parse_date(e, 0, 0)) : d == "dtend" && (d = "end_date", e = this.parse_date(e, 0, 0));
            b[d] = e
        }
    },
    parse_date: function(a, b, c) {
        var d = a.split("T");
        d[1] && (b = d[1].substr(0, 2), c = d[1].substr(2, 2));
        var e = d[0].substr(0, 4),
        f = parseInt(d[0].substr(4, 2), 10) - 1,
        g = d[0].substr(6, 2);
        return scheduler.config.server_utc && !d[1] ? new Date(Date.UTC(e, f, g, b, c)) : new Date(e, f, g, b, c)
    },
    c_start: "BEGIN:VCALENDAR",
    e_start: "BEGIN:VEVENT",
    e_end: "END:VEVENT",
    c_end: "END:VCALENDAR"
};
scheduler.lightbox = {};
scheduler.formSection = function(a) {
    for (var b = this.config.lightbox.sections, c = 0; c < b.length; c++) if (b[c].name == a) break;
    var d = b[c];
    scheduler._lightbox || scheduler.getLightbox();
    var e = document.getElementById(d.id),
    f = e.nextSibling,
    g = {
        section: d,
        header: e,
        node: f,
        getValue: function(a) {
            return scheduler.form_blocks[d.type].get_value(f, a || {},
            d)
        },
        setValue: function(a, b) {
            return scheduler.form_blocks[d.type].set_value(f, a, b || {},
            d)
        }
    },
    h = scheduler.lightbox["get_" + d.type + "_control"];
    return h ? h(g) : g
};
scheduler.lightbox.get_template_control = function(a) {
    a.control = a.node;
    return a
};
scheduler.lightbox.get_select_control = function(a) {
    a.control = a.node.getElementsByTagName("select")[0];
    return a
};
scheduler.lightbox.get_textarea_control = function(a) {
    a.control = a.node.getElementsByTagName("textarea")[0];
    return a
};
scheduler.lightbox.get_time_control = function(a) {
    a.control = a.node.getElementsByTagName("select");
    return a
};
scheduler.form_blocks = {
    template: {
        render: function(a) {
            var b = (a.height || "30") + "px";
            return "<div class='dhx_cal_ltext dhx_cal_template' style='height:" + b + ";'></div>"
        },
        set_value: function(a, b) {
            a.innerHTML = b || ""
        },
        get_value: function(a) {
            return a.innerHTML || ""
        },
        focus: function() {}
    },
    textarea: {
        render: function(a) {
            var b = (a.height || "130") + "px";
            return "<div class='dhx_cal_ltext' style='height:" + b + ";'>" + 
            "<textarea></textarea>" + 
            "</div>"
        },
        set_value: function(a, b) {
            a.firstChild.value = b || ""
        },
        get_value: function(a) {
            return a.firstChild.value
        },
        focus: function(a) {
            var b = a.firstChild;
            b.select();
            b.focus()
        }
    },
    select: {
        render: function(a) {
            for (var b = (a.height || "23") + "px", c = "<div class='dhx_cal_ltext' style='height:" + b + ";'><select class='chzn-select' style='width:100%;' required='true'>", d = 0; d < a.options.length; d++) 
            	{
            		if(d==0){
            			c += "<option value=''>" + "请选择" + "</option>";
            		}
            		c += "<option value='" + a.options[d].key + "'>" + a.options[d].label + "</option>";
            		
            	}
            	c += "</select></div>";
            	return c;
        },
        set_value: function(a, b, c, d) {
            var e = a.firstChild;
            if (!e._dhx_onchange && d.onchange) e.onchange = d.onchange,
            e._dhx_onchange = !0;
            if (typeof b == "undefined") b = (e.options[0] || {}).value;
            e.value = b || ""
        },
        get_value: function(a) {
            return a.firstChild.value
        },
        focus: function(a) {
            var b = a.firstChild;
            b.select && b.select();
            b.focus()
        }
    },
    time: {
        render: function(a) {
            var b = scheduler.config,
            c = this.date.date_part(new Date),
            d = 1440,
            e = 0;
            scheduler.config.limit_time_select && (d = 60 * b.last_hour + 1, e = 60 * b.first_hour, c.setHours(b.first_hour));
            var f = "<select class='chzn-select'>",
            g = e,
            h = c.getDate();
            for (a._time_values = []; g < d;) {
                var k = this.templates.time_picker(c);
                f += "<option value='" + g + "'>" + k + "</option>";
                a._time_values.push(g);
                c.setTime(c.valueOf() + 
                this.config.time_step * 6E4);
                var i = c.getDate() != h ? 1: 0,
                g = i * 1440 + c.getHours() * 60 + c.getMinutes()
            }
            f += "</select> <select>";
            for (g = 1; g < 32; g++) f += "<option value='" + g + "'>" + g + "</option>";
            f += "</select> <select>";
            for (g = 0; g < 12; g++) f += "<option value='" + g + "'>" + this.locale.date.month_full[g] + "</option>";
            f += "</select> <select>";
            c = c.getFullYear() - 5;
            for (g = 0; g < 10; g++) f += "<option value='" + (c + g) + "'>" + (c + g) + "</option>";
            f += "</select> ";
            return "<div style='height:30px;padding-top:0px;font-size:inherit;' class='dhx_section_time'>" + 
            f + "<span style='font-weight:normal; font-size:10pt;'> &nbsp;&ndash;&nbsp; </span>" + f + "</div>"
        },
        set_value: function(a, b, c, d) {
            function e(a, b, c) {
                for (var e = d._time_values, f = c.getHours() * 60 + c.getMinutes(), g = f, h = !1, i = 0; i < e.length; i++) {
                    var j = e[i];
                    if (j === f) {
                        h = !0;
                        break
                    }
                    j < f && (g = j)
                }
                a[b + 0].value = h ? f: g;
                a[b + 1].value = c.getDate();
                a[b + 2].value = c.getMonth();
                a[b + 3].value = c.getFullYear()
            }
            var f = scheduler.config,
            g = a.getElementsByTagName("select");
            if (f.full_day) {
                if (!a._full_day) {
                    var h = "<label class='dhx_fullday'><input type='checkbox' name='full_day' value='true'> " + 
                    scheduler.locale.labels.full_day + "&nbsp;</label></input>";
                    scheduler.config.wide_form || (h = a.previousSibling.innerHTML + h);
                    a.previousSibling.innerHTML = h;
                    a._full_day = !0
                }
                var k = a.previousSibling.getElementsByTagName("input")[0];
                k.checked = scheduler.date.time_part(c.start_date) === 0 && scheduler.date.time_part(c.end_date) === 0;
                g[0].disabled = k.checked;
                g[g.length / 2].disabled = k.checked;
                k.onclick = function() {
                    if (k.checked) {
                        var b = {};
                        scheduler.form_blocks.time.get_value(a, b);
                        var d = scheduler.date.date_part(b.start_date),
                        f = scheduler.date.date_part(b.end_date);
                        if ( + f == +d || +f >= +d && (c.end_date.getHours() != 0 || c.end_date.getMinutes() != 0)) f = scheduler.date.add(f, 1, "day")
                    }
                    g[0].disabled = k.checked;
                    g[g.length / 2].disabled = k.checked;
                    e(g, 0, d || c.start_date);
                    e(g, 4, f || c.end_date)
                }
            }
            if (f.auto_end_date && f.event_duration) for (var i = function() {
                var a = new Date(g[3].value, g[2].value, g[1].value, 0, g[0].value),
                b = new Date(a.getTime() + scheduler.config.event_duration * 6E4);
                e(g, 4, b)
            },
            j = 0; j < 4; j++) g[j].onchange = i;
            e(g, 0, c.start_date);
            e(g, 4, c.end_date)
        },
        get_value: function(a, b) {
            s = a.getElementsByTagName("select");
            b.start_date = new Date(s[3].value, s[2].value, s[1].value, 0, s[0].value);
            b.end_date = new Date(s[7].value, s[6].value, s[5].value, 0, s[4].value);
            if (b.end_date <= b.start_date) b.end_date = scheduler.date.add(b.start_date, scheduler.config.time_step, "minute");
            return {
                start_date: new Date(b.start_date),
                end_date: new Date(b.end_date)
            }
        },
        focus: function(a) {
            a.getElementsByTagName("select")[0].focus()
        }
    }
};
scheduler.showCover = function(a) {
    if (a) {
        a.style.display = "block";
        var b = window.pageYOffset || document.body.scrollTop || document.documentElement.scrollTop,
        c = window.pageXOffset || document.body.scrollLeft || document.documentElement.scrollLeft,
        d = window.innerHeight || document.documentElement.clientHeight;
        a.style.top = b ? Math.round(b + Math.max((d - a.offsetHeight) / 2, 0)) + "px": Math.round(Math.max((d - a.offsetHeight) / 2, 0) + 9) + "px";
        a.style.left = document.documentElement.scrollWidth > document.body.offsetWidth ? Math.round(c + (document.body.offsetWidth - 
        a.offsetWidth) / 2) + "px": Math.round((document.body.offsetWidth - a.offsetWidth) / 2) + "px"
    }
    this.show_cover()
};
scheduler.showLightbox = function(a) {
    if (a) if (this.callEvent("onBeforeLightbox", [a])) {
        var b = this.getLightbox();
        this.showCover(b);
        this._fill_lightbox(a, b);
        this.callEvent("onLightbox", [a])
    } else if (this._new_event) this._new_event = null
};
scheduler._fill_lightbox = function(a, b) {
    var c = this.getEvent(a),
    d = b.getElementsByTagName("span");
    scheduler.templates.lightbox_header ? (d[1].innerHTML = "", d[2].innerHTML = scheduler.templates.lightbox_header(c.start_date, c.end_date, c)) : (d[1].innerHTML = this.templates.event_header(c.start_date, c.end_date, c), d[2].innerHTML = (this.templates.event_bar_text(c.start_date, c.end_date, c) || "").substr(0, 70));
    for (var e = this.config.lightbox.sections, f = 0; f < e.length; f++) {
        var g = e[f],
        h = document.getElementById(g.id).nextSibling,
        k = this.form_blocks[g.type],
        i = c[g.map_to] !== void 0 ? c[g.map_to] : g.default_value;
        k.set_value.call(this, h, i, c, g);
        e[f].focus && k.focus.call(this, h)
    }
    scheduler._lightbox_id = a
};
scheduler._lightbox_out = function(a) {
    for (var b = this.config.lightbox.sections, c = 0; c < b.length; c++) {
        var d = document.getElementById(b[c].id),
        d = d ? d.nextSibling: d,
        e = this.form_blocks[b[c].type],
        f = e.get_value.call(this, d, a, b[c]);
        b[c].map_to != "auto" && (a[b[c].map_to] = f)
    }
    return a
};
scheduler._empty_lightbox = function(a) {
    var b = scheduler._lightbox_id,
    c = this.getEvent(b),
    d = this.getLightbox();
    this._lame_copy(c, a);
    c._timed = this.is_one_day_event(c);
    this.setEvent(c.id, c);
    this._edit_stop_event(c, !0);
    this.render_view_data()
};
scheduler.hide_lightbox = function() {
    this.hideCover(this.getLightbox());
    this._lightbox_id = null;
    this.callEvent("onAfterLightbox", [])
};
scheduler.hideCover = function(a) {
    if (a) a.style.display = "none";
    this.hide_cover()
};
scheduler.hide_cover = function() {
    this._cover && this._cover.parentNode.removeChild(this._cover);
    this._cover = null
};
scheduler.show_cover = function() {
    this._cover = document.createElement("DIV");
    this._cover.className = "dhx_cal_cover";
    var a = document.height !== void 0 ? document.height: document.body.offsetHeight,
    b = document.documentElement ? document.documentElement.scrollHeight: 0;
    this._cover.style.height = Math.max(a, b) + "px";
    document.body.appendChild(this._cover)
};
scheduler.save_lightbox = function() {
	//alert("请确认")
    var a = this._lightbox_out({},
    this._lame_copy(this.getEvent(this._lightbox_id)));
    if (!this.checkEvent("onEventSave") || this.callEvent("onEventSave", [this._lightbox_id, a, this._new_event])) this._empty_lightbox(a),
    this.hide_lightbox()
};
scheduler.startLightbox = function(a, b) {
    this._lightbox_id = a;
    this._lightbox = b;
    this.showCover(b)
};
scheduler.endLightbox = function(a, b) {
    this._edit_stop_event(scheduler.getEvent(this._lightbox_id), a);
    a && scheduler.render_view_data();
    this.hideCover(b);
    this._lightbox_id = null
};
scheduler.resetLightbox = function() {
    scheduler._lightbox && scheduler._lightbox.parentNode.removeChild(scheduler._lightbox);
    scheduler._lightbox = null
};
scheduler.cancel_lightbox = function() {
    this.callEvent("onEventCancel", [this._lightbox_id, this._new_event]);
    this.endLightbox(!1);
    this.hide_lightbox()
};
scheduler._init_lightbox_events = function() {
    this.getLightbox().onclick = function(a) {
        var b = a ? a.target: event.srcElement;
        if (!b.className) b = b.previousSibling;
        if (b && b.className) switch (b.className) {
        case "dhx_save_btn":
            scheduler.save_lightbox();
            break;
        case "dhx_delete_btn":
            var c = scheduler.locale.labels.confirm_deleting;
            scheduler._dhtmlx_confirm(c, scheduler.locale.labels.title_confirm_deleting, 
            function() {
                scheduler.deleteEvent(scheduler._lightbox_id);
                scheduler._new_event = null;
                scheduler.hide_lightbox()
            });
            break;
            case "dhx_cancel_btn":
            scheduler.cancel_lightbox();
            break;
        default:
            if (b.getAttribute("dhx_button")) scheduler.callEvent("onLightboxButton", [b.className, b, a]);
            else {
                var d,
                e,
                f;
                if (b.className.indexOf("dhx_custom_button") != -1) b.className.indexOf("dhx_custom_button_") != -1 ? (d = b.parentNode.getAttribute("index"), f = b.parentNode.parentNode) : (d = b.getAttribute("index"), f = b.parentNode, b = b.firstChild);
                d && (e = scheduler.form_blocks[scheduler.config.lightbox.sections[d].type], e.button_click(d, b, f, f.nextSibling))
            }
        }
    };
    this.getLightbox().onkeydown = 
    function(a) {
        switch ((a || event).keyCode) {
        case scheduler.keys.edit_save:
            if ((a || event).shiftKey) break;
            scheduler.save_lightbox();
            break;
        case scheduler.keys.edit_cancel:
            scheduler.cancel_lightbox()
        }
    }
};
scheduler.setLightboxSize = function() {
    var a = this._lightbox;
    if (a) {
        var b = a.childNodes[1];
        b.style.height = "0px";
        b.style.height = b.scrollHeight + "px";
        a.style.height = b.scrollHeight + scheduler.xy.lightbox_additional_height + "px";
        b.style.height = b.scrollHeight + "px"
    }
};
scheduler._init_dnd_events = function() {
    dhtmlxEvent(document.body, "mousemove", scheduler._move_while_dnd);
    dhtmlxEvent(document.body, "mouseup", scheduler._finish_dnd);
    scheduler._init_dnd_events = function() {}
};
scheduler._move_while_dnd = function(a) {
    if (scheduler._dnd_start_lb) {
        if (!document.dhx_unselectable) document.body.className += " dhx_unselectable",
        document.dhx_unselectable = !0;
        var b = scheduler.getLightbox(),
        c = a && a.target ? [a.pageX, a.pageY] : [event.clientX, event.clientY];
        b.style.top = scheduler._lb_start[1] + c[1] - scheduler._dnd_start_lb[1] + "px";
        b.style.left = scheduler._lb_start[0] + c[0] - scheduler._dnd_start_lb[0] + "px"
    }
};
scheduler._ready_to_dnd = function(a) {
    var b = scheduler.getLightbox();
    scheduler._lb_start = [parseInt(b.style.left, 10), parseInt(b.style.top, 10)];
    scheduler._dnd_start_lb = a && a.target ? [a.pageX, a.pageY] : [event.clientX, event.clientY]
};
scheduler._finish_dnd = function() {
    if (scheduler._lb_start) scheduler._lb_start = scheduler._dnd_start_lb = !1,
    document.body.className = document.body.className.replace(" dhx_unselectable", ""),
    document.dhx_unselectable = !1
};
scheduler.getLightbox = function() {
	//alert(11)
    if (!this._lightbox) {
        var a = document.createElement("DIV");
        a.className = "dhx_cal_light";
        scheduler.config.wide_form && (a.className += " dhx_cal_light_wide");
        scheduler.form_blocks.recurring && (a.className += " dhx_cal_light_rec");
        /msie|MSIE 6/.test(navigator.userAgent) && (a.className += " dhx_ie6");
        a.style.visibility = "hidden";
        for (var b = this._lightbox_template, c = this.config.buttons_left, d = 0; d < c.length; d++) b += "<div class='dhx_btn_set dhx_left_btn_set " + c[d] + "_set'><div dhx_button='1' class='" + 
        c[d] + "'></div><div>" + scheduler.locale.labels[c[d]] + "</div></div>";
        c = this.config.buttons_right;
        for (d = 0; d < c.length; d++) b += "<div class='dhx_btn_set dhx_right_btn_set " + c[d] + "_set' style='float:right;'><div dhx_button='1' class='" + c[d] + "'></div><div>" + scheduler.locale.labels[c[d]] + "</div></div>";
        b += "</div>";
        a.innerHTML = b;
        if (scheduler.config.drag_lightbox) a.firstChild.onmousedown = scheduler._ready_to_dnd,
        a.firstChild.onselectstart = function() {
            return ! 1
        },
        a.firstChild.style.cursor = "pointer",
        scheduler._init_dnd_events();
        document.body.insertBefore(a, document.body.firstChild);
        this._lightbox = a;
        for (var e = this.config.lightbox.sections, b = "", d = 0; d < e.length; d++) {
            var f = this.form_blocks[e[d].type];
            if (f) {
                e[d].id = "area_" + this.uid();
                var g = "";
                e[d].button && (g = "<div class='dhx_custom_button' index='" + d + "'><div class='dhx_custom_button_" + e[d].button + "'></div><div>" + this.locale.labels["button_" + e[d].button] + "</div></div>");
                this.config.wide_form && (b += "<div class='dhx_wrap_section'>");
                b += "<div id='" + e[d].id + "' class='dhx_cal_lsection'>" + 
                g + this.locale.labels["section_" + e[d].name] + "</div>" + f.render.call(this, e[d]);
                b += "</div>"
            }
        }
        for (var h = a.getElementsByTagName("div"), d = 0; d < h.length; d++) {
            var k = h[d];
            if (k.className == "dhx_cal_larea") {
                k.innerHTML = b;
                break
            }
        }
        this.setLightboxSize();
        this._init_lightbox_events(this);
        a.style.display = "none";
        a.style.visibility = "visible"
    }
    return this._lightbox
};
scheduler._lightbox_template = "<div class='dhx_cal_ltitle'><span class='dhx_mark'>&nbsp;</span><span class='dhx_time'></span><span class='dhx_title'></span></div><div class='dhx_cal_larea'><select class='chzn-select'><option value='10010162'>胡平华 10010162</option></select></div>";
scheduler._dp_init = function(a) {
    a._methods = ["setEventTextStyle", "", "changeEventId", "deleteEvent"];
    this.attachEvent("onEventAdded", 
    function(b) { ! this._loading && this.validId(b) && a.setUpdated(b, !0, "inserted")
    });
    this.attachEvent("onConfirmedBeforeEventDelete", 
    function(b) {
        if (this.validId(b)) {
            var c = a.getState(b);
            if (c == "inserted" || this._new_event) return a.setUpdated(b, !1),
            !0;
            if (c == "deleted") return ! 1;
            if (c == "true_deleted") return ! 0;
            a.setUpdated(b, !0, "deleted");
            return ! 1
        }
    });
    this.attachEvent("onEventChanged", 
    function(b) { ! this._loading && 
        this.validId(b) && a.setUpdated(b, !0, "updated")
    });
    a._getRowData = function(a) {
        var c = this.obj.getEvent(a),
        d = {},
        e;
        for (e in c) e.indexOf("_") != 0 && (d[e] = c[e] && c[e].getUTCFullYear ? this.obj.templates.xml_format(c[e]) : c[e]);
        return d
    };
    a._clearUpdateFlag = function() {};
    a.attachEvent("insertCallback", scheduler._update_callback);
    a.attachEvent("updateCallback", scheduler._update_callback);
    a.attachEvent("deleteCallback", 
    function(a, c) {
        this.obj.setUserData(c, this.action_param, "true_deleted");
        this.obj.deleteEvent(c)
    })
};
scheduler.setUserData = function(a, b, c) {
    a ? this.getEvent(a)[b] = c: this._userdata[b] = c
};
scheduler.getUserData = function(a, b) {
    return a ? this.getEvent(a)[b] : this._userdata[b]
};
scheduler.setEventTextStyle = function(a, b) {
    this.for_rendered(a, 
    function(a) {
        a.style.cssText += ";" + b
    });
    var c = this.getEvent(a);
    c._text_style = b;
    this.event_updated(c)
};
scheduler.validId = function() {
    return ! 0
};
scheduler._update_callback = function(a) {
    var b = scheduler.xmlNodeToJSON(a.firstChild);
    b.text = b.text || b._tagvalue;
    b.start_date = scheduler.templates.xml_date(b.start_date);
    b.end_date = scheduler.templates.xml_date(b.end_date);
    scheduler.addEvent(b)
};