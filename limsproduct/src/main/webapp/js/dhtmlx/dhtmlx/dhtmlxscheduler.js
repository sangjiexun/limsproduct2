﻿dhtmlx = function (a) {
    for (var b in a) dhtmlx[b] = a[b];
    return dhtmlx
};
dhtmlx.extend_api = function (a, b, c) {
    var d = window[a];
    if (d) window[a] = function (a) {
        if (a && typeof a == "object" && !a.tagName && !(a instanceof Array)) {
            var c = d.apply(this, b._init ? b._init(a) : arguments),
                g;
            for (g in dhtmlx) if (b[g]) this[b[g]](dhtmlx[g]);
            for (g in a) if (b[g]) this[b[g]](a[g]);
            else g.indexOf("on") == 0 && this.attachEvent(g, a[g])
        } else c = d.apply(this, arguments);
        b._patch && b._patch(this);
        return c || this
    }, window[a].prototype = d.prototype, c && dhtmlXHeir(window[a].prototype, c)
};
dhtmlxAjax = {
    get: function (a, b) {
        var c = new dtmlXMLLoaderObject(!0);
        c.async = arguments.length < 3;
        c.waitCall = b;
        c.loadXML(a);
        return c
    },
    post: function (a, b, c) {
        var d = new dtmlXMLLoaderObject(!0);
        d.async = arguments.length < 4;
        d.waitCall = c;
        d.loadXML(a, !0, b);
        return d
    },
    getSync: function (a) {
        return this.get(a, null, !0)
    },
    postSync: function (a, b) {
        return this.post(a, b, null, !0)
    }
};

function dtmlXMLLoaderObject(a, b, c, d) {
    this.xmlDoc = "";
    this.async = typeof c != "undefined" ? c : !0;
    this.onloadAction = a || null;
    this.mainObject = b || null;
    this.waitCall = null;
    this.rSeed = d || !1;
    return this
}
dtmlXMLLoaderObject.prototype.waitLoadFunction = function (a) {
    var b = !0;
    return this.check = function () {
        if (a && a.onloadAction != null && (!a.xmlDoc.readyState || a.xmlDoc.readyState == 4) && b) {
            b = !1;
            if (typeof a.onloadAction == "function") a.onloadAction(a.mainObject, null, null, null, a);
            if (a.waitCall) a.waitCall.call(this, a), a.waitCall = null
        }
    }
};
dtmlXMLLoaderObject.prototype.getXMLTopNode = function (a, b) {
    if (this.xmlDoc.responseXML) {
        var c = this.xmlDoc.responseXML.getElementsByTagName(a);
        c.length == 0 && a.indexOf(":") != -1 && (c = this.xmlDoc.responseXML.getElementsByTagName(a.split(":")[1]));
        var d = c[0]
    } else d = this.xmlDoc.documentElement;
    if (d) return this._retry = !1, d;
    if (_isIE && !this._retry) {
        var e = this.xmlDoc.responseText,
            b = this.xmlDoc;
        this._retry = !0;
        this.xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        this.xmlDoc.async = !1;
        this.xmlDoc.loadXML(e);
        return this.getXMLTopNode(a, b)
    }
    dhtmlxError.throwError("LoadXML", "Incorrect XML", [b || this.xmlDoc, this.mainObject]);
    return document.createElement("DIV")
};
dtmlXMLLoaderObject.prototype.loadXMLString = function (a) {
    try {
        var b = new DOMParser;
        this.xmlDoc = b.parseFromString(a, "text/xml")
    } catch (c) {
        this.xmlDoc = new ActiveXObject("Microsoft.XMLDOM"), this.xmlDoc.async = this.async, this.xmlDoc.loadXML(a)
    }
    this.onloadAction(this.mainObject, null, null, null, this);
    if (this.waitCall) this.waitCall(), this.waitCall = null
};
dtmlXMLLoaderObject.prototype.loadXML = function (a, b, c, d) {
    this.rSeed && (a += (a.indexOf("?") != -1 ? "&" : "?") + "a_dhx_rSeed=" + (new Date).valueOf());
    this.filePath = a;
    this.xmlDoc = !_isIE && window.XMLHttpRequest ? new XMLHttpRequest : new ActiveXObject("Microsoft.XMLHTTP");
    if (this.async) this.xmlDoc.onreadystatechange = new this.waitLoadFunction(this);
    this.xmlDoc.open(b ? "POST" : "GET", a, this.async);
    d ? (this.xmlDoc.setRequestHeader("User-Agent", "dhtmlxRPC v0.1 (" + navigator.userAgent + ")"), this.xmlDoc.setRequestHeader("Content-type", "text/xml")) : b && this.xmlDoc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    this.xmlDoc.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    this.xmlDoc.send(c);
    this.async || (new this.waitLoadFunction(this))()
};
dtmlXMLLoaderObject.prototype.destructor = function () {
    return this.xmlDoc = this.mainObject = this.onloadAction = null
};
dtmlXMLLoaderObject.prototype.xmlNodeToJSON = function (a) {
    for (var b = {}, c = 0; c < a.attributes.length; c++) b[a.attributes[c].name] = a.attributes[c].value;
    b._tagvalue = a.firstChild ? a.firstChild.nodeValue : "";
    for (c = 0; c < a.childNodes.length; c++) {
        var d = a.childNodes[c].tagName;
        d && (b[d] || (b[d] = []), b[d].push(this.xmlNodeToJSON(a.childNodes[c])))
    }
    return b
};

function callerFunction(a, b) {
    return this.handler = function (c) {
        if (!c) c = window.event;
        a(c, b);
        return !0
    }
}
function getAbsoluteLeft(a) {
    return getOffset(a).left
}

function getAbsoluteTop(a) {
    return getOffset(a).top
}
function getOffsetSum(a) {
    for (var b = 0, c = 0; a;) b += parseInt(a.offsetTop), c += parseInt(a.offsetLeft), a = a.offsetParent;
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
        i = d.clientLeft || c.clientLeft || 0,
        h = b.top + e - g,
        k = b.left + f - i;
    return {
        top: Math.round(h),
        left: Math.round(k)
    }
}
function getOffset(a) {
    return a.getBoundingClientRect && !_isChrome ? getOffsetRect(a) : getOffsetSum(a)
}

function convertStringToBoolean(a) {
    typeof a == "string" && (a = a.toLowerCase());
    switch (a) {
    case "1":
    case "true":
    case "yes":
    case "y":
    case 1:
    case !0:
        return !0;
    default:
        return !1
    }
}
function getUrlSymbol(a) {
    return a.indexOf("?") != -1 ? "&" : "?"
}
function dhtmlDragAndDropObject() {
    if (window.dhtmlDragAndDrop) return window.dhtmlDragAndDrop;
    this.dragStartObject = this.dragStartNode = this.dragNode = this.lastLanding = 0;
    this.tempDOMM = this.tempDOMU = null;
    this.waitDrag = 0;
    window.dhtmlDragAndDrop = this;
    return this
}
dhtmlDragAndDropObject.prototype.removeDraggableItem = function (a) {
    a.onmousedown = null;
    a.dragStarter = null;
    a.dragLanding = null
};
dhtmlDragAndDropObject.prototype.addDraggableItem = function (a, b) {
    a.onmousedown = this.preCreateDragCopy;
    a.dragStarter = b;
    this.addDragLanding(a, b)
};
dhtmlDragAndDropObject.prototype.addDragLanding = function (a, b) {
    a.dragLanding = b
};
dhtmlDragAndDropObject.prototype.preCreateDragCopy = function (a) {
    if (!((a || event) && (a || event).button == 2)) {
        if (window.dhtmlDragAndDrop.waitDrag) return window.dhtmlDragAndDrop.waitDrag = 0, document.body.onmouseup = window.dhtmlDragAndDrop.tempDOMU, document.body.onmousemove = window.dhtmlDragAndDrop.tempDOMM, !1;
        window.dhtmlDragAndDrop.waitDrag = 1;
        window.dhtmlDragAndDrop.tempDOMU = document.body.onmouseup;
        window.dhtmlDragAndDrop.tempDOMM = document.body.onmousemove;
        window.dhtmlDragAndDrop.dragStartNode = this;
        window.dhtmlDragAndDrop.dragStartObject = this.dragStarter;
        document.body.onmouseup = window.dhtmlDragAndDrop.preCreateDragCopy;
        document.body.onmousemove = window.dhtmlDragAndDrop.callDrag;
        window.dhtmlDragAndDrop.downtime = (new Date).valueOf();
        a && a.preventDefault && a.preventDefault();
        return !1
    }
};
dhtmlDragAndDropObject.prototype.callDrag = function (a) {
    if (!a) a = window.event;
    dragger = window.dhtmlDragAndDrop;
    if (!((new Date).valueOf() - dragger.downtime < 100)) {
        if (a.button == 0 && _isIE) return dragger.stopDrag();
        if (!dragger.dragNode && dragger.waitDrag) {
            dragger.dragNode = dragger.dragStartObject._createDragNode(dragger.dragStartNode, a);
            if (!dragger.dragNode) return dragger.stopDrag();
            dragger.dragNode.onselectstart = function () {
                return !1
            };
            dragger.gldragNode = dragger.dragNode;
            document.body.appendChild(dragger.dragNode);
            document.body.onmouseup = dragger.stopDrag;
            dragger.waitDrag = 0;
            dragger.dragNode.pWindow = window;
            dragger.initFrameRoute()
        }
        if (dragger.dragNode.parentNode != window.document.body) {
            var b = dragger.gldragNode;
            if (dragger.gldragNode.old) b = dragger.gldragNode.old;
            b.parentNode.removeChild(b);
            var c = dragger.dragNode.pWindow;
            if (_isIE) {
                var d = document.createElement("Div");
                d.innerHTML = dragger.dragNode.outerHTML;
                dragger.dragNode = d.childNodes[0]
            } else dragger.dragNode = dragger.dragNode.cloneNode(!0);
            dragger.dragNode.pWindow = window;
            dragger.gldragNode.old = dragger.dragNode;
            document.body.appendChild(dragger.dragNode);
            c.dhtmlDragAndDrop.dragNode = dragger.dragNode
        }
        dragger.dragNode.style.left = a.clientX + 15 + (dragger.fx ? dragger.fx * -1 : 0) + (document.body.scrollLeft || document.documentElement.scrollLeft) + "px";
        dragger.dragNode.style.top = a.clientY + 3 + (dragger.fy ? dragger.fy * -1 : 0) + (document.body.scrollTop || document.documentElement.scrollTop) + "px";
        var e = a.srcElement ? a.srcElement : a.target;
        dragger.checkLanding(e, a)
    }
};
dhtmlDragAndDropObject.prototype.calculateFramePosition = function (a) {
    if (window.name) {
        for (var b = parent.frames[window.name].frameElement.offsetParent, c = 0, d = 0; b;) c += b.offsetLeft, d += b.offsetTop, b = b.offsetParent;
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
dhtmlDragAndDropObject.prototype.checkLanding = function (a, b) {
    a && a.dragLanding ? (this.lastLanding && this.lastLanding.dragLanding._dragOut(this.lastLanding), this.lastLanding = a, this.lastLanding = this.lastLanding.dragLanding._dragIn(this.lastLanding, this.dragStartNode, b.clientX, b.clientY, b), this.lastLanding_scr = _isIE ? b.srcElement : b.target) : a && a.tagName != "BODY" ? this.checkLanding(a.parentNode, b) : (this.lastLanding && this.lastLanding.dragLanding._dragOut(this.lastLanding, b.clientX, b.clientY, b), this.lastLanding = 0, this._onNotFound && this._onNotFound())
};
dhtmlDragAndDropObject.prototype.stopDrag = function (a, b) {
    dragger = window.dhtmlDragAndDrop;
    if (!b) {
        dragger.stopFrameRoute();
        var c = dragger.lastLanding;
        dragger.lastLanding = null;
        c && c.dragLanding._drag(dragger.dragStartNode, dragger.dragStartObject, c, _isIE ? event.srcElement : a.target)
    }
    dragger.lastLanding = null;
    dragger.dragNode && dragger.dragNode.parentNode == document.body && dragger.dragNode.parentNode.removeChild(dragger.dragNode);
    dragger.dragNode = 0;
    dragger.gldragNode = 0;
    dragger.fx = 0;
    dragger.fy = 0;
    dragger.dragStartNode = 0;
    dragger.dragStartObject = 0;
    document.body.onmouseup = dragger.tempDOMU;
    document.body.onmousemove = dragger.tempDOMM;
    dragger.tempDOMU = null;
    dragger.tempDOMM = null;
    dragger.waitDrag = 0
};
dhtmlDragAndDropObject.prototype.stopFrameRoute = function (a) {
    a && window.dhtmlDragAndDrop.stopDrag(1, 1);
    for (var b = 0; b < window.frames.length; b++) try {
        window.frames[b] != a && window.frames[b].dhtmlDragAndDrop && window.frames[b].dhtmlDragAndDrop.stopFrameRoute(window)
    } catch (c) {}
    try {
        parent.dhtmlDragAndDrop && parent != window && parent != a && parent.dhtmlDragAndDrop.stopFrameRoute(window)
    } catch (d) {}
};
dhtmlDragAndDropObject.prototype.initFrameRoute = function (a, b) {
    if (a) window.dhtmlDragAndDrop.preCreateDragCopy({}), window.dhtmlDragAndDrop.dragStartNode = a.dhtmlDragAndDrop.dragStartNode, window.dhtmlDragAndDrop.dragStartObject = a.dhtmlDragAndDrop.dragStartObject, window.dhtmlDragAndDrop.dragNode = a.dhtmlDragAndDrop.dragNode, window.dhtmlDragAndDrop.gldragNode = a.dhtmlDragAndDrop.dragNode, window.document.body.onmouseup = window.dhtmlDragAndDrop.stopDrag, window.waitDrag = 0, !_isIE && b && (!_isFF || w < 1.8) && window.dhtmlDragAndDrop.calculateFramePosition();
    try {
        parent.dhtmlDragAndDrop && parent != window && parent != a && parent.dhtmlDragAndDrop.initFrameRoute(window)
    } catch (c) {}
    for (var d = 0; d < window.frames.length; d++) try {
        window.frames[d] != a && window.frames[d].dhtmlDragAndDrop && window.frames[d].dhtmlDragAndDrop.initFrameRoute(window, !a || b ? 1 : 0)
    } catch (e) {}
};
w = _OperaRv = x = _isChrome = _isMacOS = _isKHTML = _isOpera = _isIE = _isFF = !1;
navigator.userAgent.indexOf("Macintosh") != -1 && (_isMacOS = !0);
navigator.userAgent.toLowerCase().indexOf("chrome") > -1 && (_isChrome = !0);
if (navigator.userAgent.indexOf("Safari") != -1 || navigator.userAgent.indexOf("Konqueror") != -1) {
    var x = parseFloat(navigator.userAgent.substr(navigator.userAgent.indexOf("Safari") + 7, 5));
    if (x > 525) {
        _isFF = !0;
        var w = 1.9
    } else _isKHTML = !0
} else navigator.userAgent.indexOf("Opera") != -1 ? (_isOpera = !0, _OperaRv = parseFloat(navigator.userAgent.substr(navigator.userAgent.indexOf("Opera") + 6, 3))) : navigator.appName.indexOf("Microsoft") != -1 ? (_isIE = !0, navigator.appVersion.indexOf("MSIE 8.0") != -1 && document.compatMode != "BackCompat" && (_isIE = 8)) : (_isFF = !0, w = parseFloat(navigator.userAgent.split("rv:")[1]));
dtmlXMLLoaderObject.prototype.doXPath = function (a, b, c, d) {
    if (_isKHTML || !_isIE && !window.XPathResult) return this.doXPathOpera(a, b);
    if (_isIE) return b || (b = this.xmlDoc.nodeName ? this.xmlDoc : this.xmlDoc.responseXML), b || dhtmlxError.throwError("LoadXML", "Incorrect XML", [b || this.xmlDoc, this.mainObject]), c != null && b.setProperty("SelectionNamespaces", "xmlns:xsl='" + c + "'"), d == "single" ? b.selectSingleNode(a) : b.selectNodes(a) || [];
    else {
        var e = b;
        b || (b = this.xmlDoc.nodeName ? this.xmlDoc : this.xmlDoc.responseXML);
        b || dhtmlxError.throwError("LoadXML", "Incorrect XML", [b || this.xmlDoc, this.mainObject]);
        b.nodeName.indexOf("document") != -1 ? e = b : (e = b, b = b.ownerDocument);
        var f = XPathResult.ANY_TYPE;
        if (d == "single") f = XPathResult.FIRST_ORDERED_NODE_TYPE;
        var g = [],
            i = b.evaluate(a, e, function () {
                return c
            }, f, null);
        if (f == XPathResult.FIRST_ORDERED_NODE_TYPE) return i.singleNodeValue;
        for (var h = i.iterateNext(); h;) g[g.length] = h, h = i.iterateNext();
        return g
    }
};

function z() {
    if (!this.catches) this.catches = [];
    return this
}
z.prototype.catchError = function (a, b) {
    this.catches[a] = b
};
z.prototype.throwError = function (a, b, c) {
    if (this.catches[a]) return this.catches[a](a, b, c);
    if (this.catches.ALL) return this.catches.ALL(a, b, c);
    alert("Error type: " + a + "\nDescription: " + b);
    return null
};
window.dhtmlxError = new z;
dtmlXMLLoaderObject.prototype.doXPathOpera = function (a, b) {
    var c = a.replace(/[\/]+/gi, "/").split("/"),
        d = null,
        e = 1;
    if (!c.length) return [];
    if (c[0] == ".") d = [b];
    else if (c[0] == "") d = (this.xmlDoc.responseXML || this.xmlDoc).getElementsByTagName(c[e].replace(/\[[^\]]*\]/g, "")), e++;
    else return [];
    for (; e < c.length; e++) d = this._getAllNamedChilds(d, c[e]);
    c[e - 1].indexOf("[") != -1 && (d = this._filterXPath(d, c[e - 1]));
    return d
};
dtmlXMLLoaderObject.prototype._filterXPath = function (a, b) {
    for (var c = [], b = b.replace(/[^\[]*\[\@/g, "").replace(/[\[\]\@]*/g, ""), d = 0; d < a.length; d++) a[d].getAttribute(b) && (c[c.length] = a[d]);
    return c
};
dtmlXMLLoaderObject.prototype._getAllNamedChilds = function (a, b) {
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
dtmlXMLLoaderObject.prototype.setXSLParamValue = function (a, b, c) {
    if (!c) c = this.xslDoc;
    if (c.responseXML) c = c.responseXML;
    var d = this.doXPath("/xsl:stylesheet/xsl:variable[@name='" + a + "']", c, "http://www.w3.org/1999/XSL/Transform", "single");
    if (d != null) d.firstChild.nodeValue = b
};
dtmlXMLLoaderObject.prototype.doXSLTransToObject = function (a, b) {
    if (!a) a = this.xslDoc;
    if (a.responseXML) a = a.responseXML;
    if (!b) b = this.xmlDoc;
    if (b.responseXML) b = b.responseXML;
    if (_isIE) {
        d = new ActiveXObject("Msxml2.DOMDocument.3.0");
        try {
            b.transformNodeToObject(a, d)
        } catch (c) {
            d = b.transformNode(a)
        }
    } else {
        if (!this.XSLProcessor) this.XSLProcessor = new XSLTProcessor, this.XSLProcessor.importStylesheet(a);
        var d = this.XSLProcessor.transformToDocument(b)
    }
    return d
};
dtmlXMLLoaderObject.prototype.doXSLTransToString = function (a, b) {
    var c = this.doXSLTransToObject(a, b);
    return typeof c == "string" ? c : this.doSerialization(c)
};
dtmlXMLLoaderObject.prototype.doSerialization = function (a) {
    if (!a) a = this.xmlDoc;
    if (a.responseXML) a = a.responseXML;
    if (_isIE) return a.xml;
    else {
        var b = new XMLSerializer;
        return b.serializeToString(a)
    }
};
dhtmlxEventable = function (a) {
    a.dhx_SeverCatcherPath = "";
    a.attachEvent = function (a, c, d) {
        a = "ev_" + a.toLowerCase();
        this[a] || (this[a] = new this.eventCatcher(d || this));
        return a + ":" + this[a].addEvent(c)
    };
    a.callEvent = function (a, c) {
        a = "ev_" + a.toLowerCase();
        return this[a] ? this[a].apply(this, c) : !0
    };
    a.checkEvent = function (a) {
        return !!this["ev_" + a.toLowerCase()]
    };
    a.eventCatcher = function (a) {
        var c = [],
            d = function () {
                for (var d = !0, f = 0; f < c.length; f++) if (c[f] != null) var g = c[f].apply(a, arguments),
                    d = d && g;
                return d
            };
        d.addEvent = function (a) {
            typeof a != "function" && (a = eval(a));
            return a ? c.push(a) - 1 : !1
        };
        d.removeEvent = function (a) {
            c[a] = null
        };
        return d
    };
    a.detachEvent = function (a) {
        if (a != !1) {
            var c = a.split(":");
            this[c[0]].removeEvent(c[1])
        }
    };
    a.detachAllEvents = function () {
        for (var a in this) a.indexOf("ev_") == 0 && delete this[a]
    }
};

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
        clear: "font-weight:normal;text-decoration:none;"
    };
    this.enableUTFencoding(!0);
    dhtmlxEventable(this);
    return this
}
dataProcessor.prototype = {
    setTransactionMode: function (a, b) {
        this._tMode = a;
        this._tSend = b
    },
    escape: function (a) {
        return this._utf ? encodeURIComponent(a) : escape(a)
    },
    enableUTFencoding: function (a) {
        this._utf = convertStringToBoolean(a)
    },
    setDataColumns: function (a) {
        this._columns = typeof a == "string" ? a.split(",") : a
    },
    getSyncState: function () {
        return !this.updatedRows.length
    },
    enableDataNames: function (a) {
        this._endnm = convertStringToBoolean(a)
    },
    enablePartialDataSend: function (a) {
        this._changed = convertStringToBoolean(a)
    },
    setUpdateMode: function (a, b) {
        this.autoUpdate = a == "cell";
        this.updateMode = a;
        this.dnd = b
    },
    ignore: function (a, b) {
        this._silent_mode = !0;
        a.call(b || window);
        this._silent_mode = !1
    },
    setUpdated: function (a, b, c) {
        if (!this._silent_mode) {
            var d = this.findRow(a),
                c = c || "updated",
                e = this.obj.getUserData(a, this.action_param);
            e && c == "updated" && (c = e);
            b ? (this.set_invalid(a, !1), this.updatedRows[d] = a, this.obj.setUserData(a, this.action_param, c), this._in_progress[a] && (this._in_progress[a] = "wait")) : this.is_invalid(a) || (this.updatedRows.splice(d, 1), this.obj.setUserData(a, this.action_param, ""));
            b || this._clearUpdateFlag(a);
            this.markRow(a, b, c);
            b && this.autoUpdate && this.sendData(a)
        }
    },
    _clearUpdateFlag: function () {},
    markRow: function (a, b, c) {
        var d = "",
            e = this.is_invalid(a);
        e && (d = this.styles[e], b = !0);
        if (this.callEvent("onRowMark", [a, b, c, e]) && (d = this.styles[b ? c : "clear"] + d, this.obj[this._methods[0]](a, d), e && e.details)) {
            d += this.styles[e + "_cell"];
            for (var f = 0; f < e.details.length; f++) if (e.details[f]) this.obj[this._methods[1]](a, f, d)
        }
    },
    getState: function (a) {
        return this.obj.getUserData(a, this.action_param)
    },
    is_invalid: function (a) {
        return this._invalid[a]
    },
    set_invalid: function (a, b, c) {
        c && (b = {
            value: b,
            details: c,
            toString: function () {
                return this.value.toString()
            }
        });
        this._invalid[a] = b
    },
    checkBeforeUpdate: function () {
        return !0
    },
    sendData: function (a) {
        if (!this._waitMode || !(this.obj.mytype == "tree" || this.obj._h2)) {
            this.obj.editStop && this.obj.editStop();
            if (typeof a == "undefined" || this._tSend) return this.sendAllData();
            if (this._in_progress[a]) return !1;
            this.messages = [];
            if (!this.checkBeforeUpdate(a) && this.callEvent("onValidatationError", [a, this.messages])) return !1;
            this._beforeSendData(this._getRowData(a), a)
        }
    },
    _beforeSendData: function (a, b) {
        if (!this.callEvent("onBeforeUpdate", [b, this.getState(b), a])) return !1;
        this._sendData(a, b)
    },
    serialize: function (a, b) {
        if (typeof a == "string") return a;
        if (typeof b != "undefined") return this.serialize_one(a, "");
        else {
            var c = [],
                d = [],
                e;
            for (e in a) a.hasOwnProperty(e) && (c.push(this.serialize_one(a[e], e + this.post_delim)), d.push(e));
            c.push("ids=" + this.escape(d.join(",")));
            return c.join("&")
        }
    },
    serialize_one: function (a, b) {
        if (typeof a == "string") return a;
        var c = [],
            d;
        for (d in a) a.hasOwnProperty(d) && c.push(this.escape((b || "") + d) + "=" + this.escape(a[d]));
        return c.join("&")
    },
    _sendData: function (a, b) {
        if (a) {
            if (!this.callEvent("onBeforeDataSending", b ? [b, this.getState(b), a] : [null, null, a])) return !1;
            b && (this._in_progress[b] = (new Date).valueOf());
            var c = new dtmlXMLLoaderObject(this.afterUpdate, this, !0),
                d = this.serverProcessor + (this._user ? getUrlSymbol(this.serverProcessor) + ["dhx_user=" + this._user, "dhx_version=" + this.obj.getUserData(0, "version")].join("&") : "");
            this._tMode != "POST" ? c.loadXML(d + (d.indexOf("?") != -1 ? "&" : "?") + this.serialize(a, b)) : c.loadXML(d, !0, this.serialize(a, b));
            this._waitMode++
        }
    },
    sendAllData: function () {
        if (this.updatedRows.length) {
            this.messages = [];
            for (var a = !0, b = 0; b < this.updatedRows.length; b++) a &= this.checkBeforeUpdate(this.updatedRows[b]);
            if (!a && !this.callEvent("onValidatationError", ["", this.messages])) return !1;
            if (this._tSend) this._sendData(this._getAllData());
            else for (b = 0; b < this.updatedRows.length; b++) if (!this._in_progress[this.updatedRows[b]] && !this.is_invalid(this.updatedRows[b]) && (this._beforeSendData(this._getRowData(this.updatedRows[b]), this.updatedRows[b]), this._waitMode && (this.obj.mytype == "tree" || this.obj._h2))) break
        }
    },
    _getAllData: function () {
        for (var a = {}, b = !1, c = 0; c < this.updatedRows.length; c++) {
            var d = this.updatedRows[c];
            !this._in_progress[d] && !this.is_invalid(d) && this.callEvent("onBeforeUpdate", [d, this.getState(d)]) && (a[d] = this._getRowData(d, d + this.post_delim), b = !0, this._in_progress[d] = (new Date).valueOf())
        }
        return b ? a : null
    },
    setVerificator: function (a, b) {
        this.mandatoryFields[a] = b ||
        function (a) {
            return a != ""
        }
    },
    clearVerificator: function (a) {
        this.mandatoryFields[a] = !1
    },
    findRow: function (a) {
        for (var b = 0, b = 0; b < this.updatedRows.length; b++) if (a == this.updatedRows[b]) break;
        return b
    },
    defineAction: function (a, b) {
        if (!this._uActions) this._uActions = [];
        this._uActions[a] = b
    },
    afterUpdateCallback: function (a, b, c, d) {
        var e = a,
            f = c != "error" && c != "invalid";
        f || this.set_invalid(a, c);
        if (this._uActions && this._uActions[c] && !this._uActions[c](d)) return delete this._in_progress[e];
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
            return this.obj.setUserData(a, this.action_param, "true_deleted"), this.obj[this._methods[3]](a), delete this._in_progress[e], this.callEvent("onAfterUpdate", [a, c, b, d])
        }
        this._in_progress[e] != "wait" ? (f && this.obj.setUserData(a, this.action_param, ""), delete this._in_progress[e]) : (delete this._in_progress[e], this.setUpdated(b, !0, this.obj.getUserData(a, this.action_param)));
        this.callEvent("onAfterUpdate", [a, c, b, d])
    },
    afterUpdate: function (a, b, c, d, e) {
        e.getXMLTopNode("data");
        if (e.xmlDoc.responseXML) {
            for (var f = e.doXPath("//data/action"), g = 0; g < f.length; g++) {
                var i = f[g],
                    h = i.getAttribute("type"),
                    k = i.getAttribute("sid"),
                    j = i.getAttribute("tid");
                a.afterUpdateCallback(k, j, h, i)
            }
            a.finalizeUpdate()
        }
    },
    finalizeUpdate: function () {
        this._waitMode && this._waitMode--;
        (this.obj.mytype == "tree" || this.obj._h2) && this.updatedRows.length && this.sendData();
        this.callEvent("onAfterUpdateFinish", []);
        this.updatedRows.length || this.callEvent("onFullSync", [])
    },
    init: function (a) {
        this.obj = a;
        this.obj._dp_init && this.obj._dp_init(this)
    },
    setOnAfterUpdate: function (a) {
        this.attachEvent("onAfterUpdate", a)
    },
    enableDebug: function () {},
    setOnBeforeUpdateHandler: function (a) {
        this.attachEvent("onBeforeDataSending", a)
    },
    setAutoUpdate: function (a, b) {
        a = a || 2E3;
        this._user = b || (new Date).valueOf();
        this._need_update = !1;
        this._loader = null;
        this._update_busy = !1;
        this.attachEvent("onAfterUpdate", function (a, b, c, g) {
            this.afterAutoUpdate(a, b, c, g)
        });
        this.attachEvent("onFullSync", function () {
            this.fullSync()
        });
        var c = this;
        window.setInterval(function () {
            c.loadUpdate()
        }, a)
    },
    afterAutoUpdate: function (a, b) {
        return b == "collision" ? (this._need_update = !0, !1) : !0
    },
    fullSync: function () {
        if (this._need_update == !0) this._need_update = !1, this.loadUpdate();
        return !0
    },
    getUpdates: function (a, b) {
        if (this._update_busy) return !1;
        else this._update_busy = !0;
        this._loader = this._loader || new dtmlXMLLoaderObject(!0);
        this._loader.async = !0;
        this._loader.waitCall = b;
        this._loader.loadXML(a)
    },
    _v: function (a) {
        return a.firstChild ? a.firstChild.nodeValue : ""
    },
    _a: function (a) {
        for (var b = [], c = 0; c < a.length; c++) b[c] = this._v(a[c]);
        return b
    },
    loadUpdate: function () {
        var a = this,
            b = this.obj.getUserData(0, "version"),
            c = this.serverProcessor + getUrlSymbol(this.serverProcessor) + ["dhx_user=" + this._user, "dhx_version=" + b].join("&"),
            c = c.replace("editing=true&", "");
        this.getUpdates(c, function () {
            var b = a._loader.doXPath("//userdata");
            a.obj.setUserData(0, "version", a._v(b[0]));
            var c = a._loader.doXPath("//update");
            if (c.length) {
                a._silent_mode = !0;
                for (var f = 0; f < c.length; f++) {
                    var g = c[f].getAttribute("status"),
                        i = c[f].getAttribute("id"),
                        h = c[f].getAttribute("parent");
                    switch (g) {
                    case "inserted":
                        a.callEvent("insertCallback", [c[f], i, h]);
                        break;
                    case "updated":
                        a.callEvent("updateCallback", [c[f], i, h]);
                        break;
                    case "deleted":
                        a.callEvent("deleteCallback", [c[f], i, h])
                    }
                }
                a._silent_mode = !1
            }
            a._update_busy = !1;
            a = null
        })
    }
};
if (window.dhtmlXGridObject) dhtmlXGridObject.prototype._init_point_connector = dhtmlXGridObject.prototype._init_point, dhtmlXGridObject.prototype._init_point = function () {
    var a = function (a) {
            a = a.replace(/(\?|\&)connector[^\f]*/g, "");
            return a + (a.indexOf("?") != -1 ? "&" : "?") + "connector=true" + (this.hdr.rows.length > 0 ? "&dhx_no_header=1" : "")
        },
        b = function (b) {
            return a.call(this, b) + (this._connector_sorting || "") + (this._connector_filter || "")
        },
        c = function (a, c, d) {
            this._connector_sorting = "&dhx_sort[" + c + "]=" + d;
            return b.call(this, a)
        },
        d = function (a, c, d) {
            for (var i = 0; i < c.length; i++) c[i] = "dhx_filter[" + c[i] + "]=" + encodeURIComponent(d[i]);
            this._connector_filter = "&" + c.join("&");
            return b.call(this, a)
        };
    this.attachEvent("onCollectValues", function (a) {
        return this._con_f_used[a] ? typeof this._con_f_used[a] == "object" ? this._con_f_used[a] : !1 : !0
    });
    this.attachEvent("onDynXLS", function () {
        this.xmlFileUrl = b.call(this, this.xmlFileUrl);
        return !0
    });
    this.attachEvent("onBeforeSorting", function (a, b, d) {
        if (b == "connector") {
            var i = this;
            this.clearAndLoad(c.call(this, this.xmlFileUrl, a, d), function () {
                i.setSortImgState(!0, a, d)
            });
            return !1
        }
        return !0
    });
    this.attachEvent("onFilterStart", function (a, b) {
        return this._con_f_used.length ? (this.clearAndLoad(d.call(this, this.xmlFileUrl, a, b)), !1) : !0
    });
    this.attachEvent("onXLE", function () {});
    this._init_point_connector && this._init_point_connector()
}, dhtmlXGridObject.prototype._con_f_used = [], dhtmlXGridObject.prototype._in_header_connector_text_filter = function (a, b) {
    this._con_f_used[b] || (this._con_f_used[b] = 1);
    return this._in_header_text_filter(a, b)
}, dhtmlXGridObject.prototype._in_header_connector_select_filter = function (a, b) {
    this._con_f_used[b] || (this._con_f_used[b] = 2);
    return this._in_header_select_filter(a, b)
}, dhtmlXGridObject.prototype.load_connector = dhtmlXGridObject.prototype.load, dhtmlXGridObject.prototype.load = function (a, b, c) {
    if (!this._colls_loaded && this.cellType) {
        for (var d = [], e = 0; e < this.cellType.length; e++)(this.cellType[e].indexOf("co") == 0 || this._con_f_used[e] == 2) && d.push(e);
        d.length && (arguments[0] += (arguments[0].indexOf("?") != -1 ? "&" : "?") + "connector=true&dhx_colls=" + d.join(","))
    }
    return this.load_connector.apply(this, arguments)
}, dhtmlXGridObject.prototype._parseHead_connector = dhtmlXGridObject.prototype._parseHead, dhtmlXGridObject.prototype._parseHead = function (a, b, c) {
    this._parseHead_connector.apply(this, arguments);
    if (!this._colls_loaded) {
        for (var d = this.xmlLoader.doXPath("./coll_options", arguments[0]), e = 0; e < d.length; e++) {
            var f = d[e].getAttribute("for"),
                g = [],
                i = null;
            this.cellType[f] == "combo" && (i = this.getColumnCombo(f));
            this.cellType[f].indexOf("co") == 0 && (i = this.getCombo(f));
            for (var h = this.xmlLoader.doXPath("./item", d[e]), k = 0; k < h.length; k++) {
                var j = h[k].getAttribute("value");
                if (i) {
                    var l = h[k].getAttribute("label") || j;
                    i.addOption ? i.addOption([
                        [j, l]
                    ]) : i.put(j, l);
                    g[g.length] = l
                } else g[g.length] = j
            }
            this._con_f_used[f * 1] && (this._con_f_used[f * 1] = g)
        }
        this._colls_loaded = !0
    }
};
if (window.dataProcessor) dataProcessor.prototype.init_original = dataProcessor.prototype.init, dataProcessor.prototype.init = function (a) {
    this.init_original(a);
    a._dataprocessor = this;
    this.setTransactionMode("POST", !0);
    this.serverProcessor += (this.serverProcessor.indexOf("?") != -1 ? "&" : "?") + "editing=true"
};
dhtmlxError.catchError("LoadXML", function (a, b, c) {
    alert(c[0].responseText)
});
window.dhtmlXScheduler = window.scheduler = {
    version: 3
};
dhtmlxEventable(scheduler);
scheduler.init = function (a, b, c) {
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
    dhtmlxEvent(window, "resize", function () {
        window.clearTimeout(scheduler._resize_timer);
        scheduler._resize_timer = window.setTimeout(function () {
            scheduler.callEvent("onSchedulerResize", []) && scheduler.update_view()
        }, 100)
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
    editor_width: 140
};
scheduler.keys = {
    edit_save: 13,
    edit_cancel: 27
};
scheduler.set_sizes = function () {
    var a = this._x = this._obj.clientWidth - this.xy.margin_left,
        b = this._y = this._obj.clientHeight - this.xy.margin_top,
        c = this._table_view ? 0 : this.xy.scale_width + this.xy.scroll_width,
        d = this._table_view ? -1 : this.xy.scale_width;
    this.set_xy(this._els.dhx_cal_navline[0], a, this.xy.nav_height, 0, 0);
    this.set_xy(this._els.dhx_cal_header[0], a - c, this.xy.scale_height, d, this.xy.nav_height + (this._quirks ? -1 : 1));
    var e = this._els.dhx_cal_navline[0].offsetHeight;
    if (e > 0) this.xy.nav_height = e;
    var f = this.xy.scale_height + this.xy.nav_height + (this._quirks ? -2 : 0);
    this.set_xy(this._els.dhx_cal_data[0], a, b - (f + 2), 0, f + 2)
};
scheduler.set_xy = function (a, b, c, d, e) {
    a.style.width = Math.max(0, b) + "px";
    a.style.height = Math.max(0, c) + "px";
    if (arguments.length > 3) a.style.left = d + "px", a.style.top = e + "px"
};
scheduler.get_elements = function () {
    for (var a = this._obj.getElementsByTagName("DIV"), b = 0; b < a.length; b++) {
        var c = a[b].className;
        this._els[c] || (this._els[c] = []);
        this._els[c].push(a[b]);
        var d = scheduler.locale.labels[a[b].getAttribute("name") || c];
        if (d) a[b].innerHTML = d
    }
};
scheduler.set_actions = function () {
    for (var a in this._els) if (this._click[a]) for (var b = 0; b < this._els[a].length; b++) this._els[a][b].onclick = scheduler._click[a];
    this._obj.onselectstart = function () {
        return !1
    };
    this._obj.onmousemove = function (a) {
        scheduler._on_mouse_move(a || event)
    };
    this._obj.onmousedown = function (a) {
        scheduler._on_mouse_down(a || event)
    };
    this._obj.onmouseup = function (a) {
        scheduler._on_mouse_up(a || event)
    };
    this._obj.ondblclick = function (a) {
        scheduler._on_dbl_click(a || event)
    }
};
scheduler.select = function (a) {
    if (!this._table_view && this.getEvent(a)._timed && this._select_id != a) this.editStop(!1), this.unselect(), this._select_id = a, this.updateEvent(a)
};
scheduler.unselect = function (a) {
    if (!(a && a != this._select_id)) {
        var b = this._select_id;
        this._select_id = null;
        b && this.updateEvent(b)
    }
};
scheduler.getState = function () {
    return {
        mode: this._mode,
        date: this._date,
        min_date: this._min_date,
        max_date: this._max_date,
        editor_id: this._edit_id,
        lightbox_id: this._lightbox_id,
        new_event: this._new_event
    }
};
scheduler._click = {
    dhx_cal_data: function (a) {
        var b = a ? a.target : event.srcElement,
            c = scheduler._locate_event(b),
            a = a || event;
        if (!(c && !scheduler.callEvent("onClick", [c, a]) || scheduler.config.readonly)) if (c) {
            scheduler.select(c);
            var d = b.className;
            if (d.indexOf("_icon") != -1) scheduler._click.buttons[d.split(" ")[1].replace("icon_", "")](c)
        } else scheduler._close_not_saved()
    },
    dhx_cal_prev_button: function () {
        scheduler._click.dhx_cal_next_button(0, -1)
    },
    dhx_cal_next_button: function (a, b) {
        scheduler.setCurrentView(scheduler.date.add(scheduler.date[scheduler._mode + "_start"](scheduler._date), b || 1, scheduler._mode))
    },
    dhx_cal_today_button: function () {
        scheduler.setCurrentView(new Date)
    },
    dhx_cal_tab: function () {
        var a = this.getAttribute("name"),
            b = a.substring(0, a.search("_tab"));
        scheduler.setCurrentView(scheduler._date, b)
    },
    buttons: {
        "delete": function (a) {
            var b = scheduler.locale.labels.confirm_deleting;
            (!b || confirm(b)) && scheduler.deleteEvent(a)
        },
        edit: function (a) {
            scheduler.edit(a)
        },
        save: function () {
            scheduler.editStop(!0)
        },
        details: function (a) {
            scheduler.showLightbox(a)
        },
        cancel: function () {
            scheduler.editStop(!1)
        }
    }
};
scheduler.addEventNow = function (a, b, c) {
    var d = {};
    a && a.constructor.toString().match(/object/i) !== null && (d = a, a = null);
    var e = (this.config.event_duration || this.config.time_step) * 6E4;
    a || (a = Math.round((new Date).valueOf() / e) * e);
    var f = new Date(a);
    if (!b) {
        var g = this.config.first_hour;
        g > f.getHours() && (f.setHours(g), a = f.valueOf());
        b = a + e
    }
    var i = new Date(b);
    f.valueOf() == i.valueOf() && i.setTime(i.valueOf() + e);
    d.start_date = d.start_date || f;
    d.end_date = d.end_date || i;
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
scheduler._on_dbl_click = function (a, b) {
    b = b || a.target || a.srcElement;
    if (!this.config.readonly) {
        var c = b.className.split(" ")[0];
        switch (c) {
        case "dhx_scale_holder":
        case "dhx_scale_holder_now":
        case "dhx_month_body":
        case "dhx_wa_day_data":
            if (!scheduler.config.dblclick_create) break;
            var d = this._mouse_coords(a),
                e = this._min_date.valueOf() + (d.y * this.config.time_step + (this._table_view ? 0 : d.x) * 1440) * 6E4,
                e = this._correct_shift(e);
            this.addEventNow(e, null, a);
            break;
        case "dhx_body":
        case "dhx_wa_ev_body":
        case "dhx_cal_event_line":
        case "dhx_cal_event_clear":
            var f = this._locate_event(b);
            if (!this.callEvent("onDblClick", [f, a])) break;
            this.config.details_on_dblclick || this._table_view || !this.getEvent(f)._timed ? this.showLightbox(f) : this.edit(f);
            break;
        case "":
            if (b.parentNode) return scheduler._on_dbl_click(a, b.parentNode);
        default:
            var g = this["dblclick_" + c];
            g && g.call(this, a)
        }
    }
};
scheduler._mouse_coords = function (a) {
    var b, c = document.body,
        d = document.documentElement;
    b = a.pageX || a.pageY ? {
        x: a.pageX,
        y: a.pageY
    } : {
        x: a.clientX + (c.scrollLeft || d.scrollLeft || 0) - c.clientLeft,
        y: a.clientY + (c.scrollTop || d.scrollTop || 0) - c.clientTop
    };
    b.x -= getAbsoluteLeft(this._obj) + (this._table_view ? 0 : this.xy.scale_width);
    b.y -= getAbsoluteTop(this._obj) + this.xy.nav_height + (this._dy_shift || 0) + this.xy.scale_height - this._els.dhx_cal_data[0].scrollTop;
    b.ev = a;
    var e = this["mouse_" + this._mode];
    if (e) return e.call(this, b);
    if (this._table_view) {
        for (var f = 0, f = 1; f < this._colsS.heights.length; f++) if (this._colsS.heights[f] > b.y) break;
        b.y = (Math.max(0, Math.ceil(b.x / this._cols[0]) - 1) + Math.max(0, f - 1) * 7) * 1440 / this.config.time_step;
        b.x = 0
    } else b.x = Math.max(0, Math.ceil(b.x / this._cols[0]) - 1), b.y = Math.max(0, Math.ceil(b.y * 60 / (this.config.time_step * this.config.hour_size_px)) - 1) + this.config.first_hour * (60 / this.config.time_step);
    return b
};
scheduler._close_not_saved = function () {
    if ((new Date).valueOf() - (scheduler._new_event || 0) > 500 && scheduler._edit_id) {
        var a = scheduler.locale.labels.confirm_closing;
        (!a || confirm(a)) && scheduler.editStop(scheduler.config.positive_closing)
    }
};
scheduler._correct_shift = function (a, b) {
    return a -= ((new Date(scheduler._min_date)).getTimezoneOffset() - (new Date(a)).getTimezoneOffset()) * 6E4 * (b ? -1 : 1)
};
scheduler._on_mouse_move = function (a) {
    if (this._drag_mode) {
        var b = this._mouse_coords(a);
        if (!this._drag_pos || b.custom || this._drag_pos.x != b.x || this._drag_pos.y != b.y) {
            this._edit_id != this._drag_id && this._close_not_saved();
            this._drag_pos = b;
            if (this._drag_mode == "create") {
                this._close_not_saved();
                this._loading = !0;
                var c = this._min_date.valueOf() + (b.y * this.config.time_step + (this._table_view ? 0 : b.x) * 1440) * 6E4,
                    c = this._correct_shift(c);
                if (!this._drag_start) {
                    this._drag_start = c;
                    return
                }
                var d = c;
                if (d == this._drag_start) return;
                this._drag_id = this.uid();
                this.addEvent(new Date(this._drag_start), new Date(d), this.locale.labels.new_event, this._drag_id, b.fields);
                this.callEvent("onEventCreated", [this._drag_id, a]);
                this._loading = !1;
                this._drag_mode = "new-size"
            }
            var e = this.getEvent(this._drag_id);
            if (this._drag_mode == "move") c = this._min_date.valueOf() + (b.y * this.config.time_step + b.x * 1440) * 6E4, !b.custom && this._table_view && (c += this.date.time_part(e.start_date) * 1E3), c = this._correct_shift(c), d = e.end_date.valueOf() - (e.start_date.valueOf() - c);
            else {
                c = e.start_date.valueOf();
                if (this._table_view) d = this._min_date.valueOf() + b.y * this.config.time_step * 6E4 + (b.custom ? 0 : 864E5), this._mode == "month" && (d = this._correct_shift(d, !1));
                else if (d = this.date.date_part(new Date(e.end_date)).valueOf() + b.y * this.config.time_step * 6E4, this._els.dhx_cal_data[0].style.cursor = "s-resize", this._mode == "week" || this._mode == "day") d = this._correct_shift(d);
                if (this._drag_mode == "new-size") if (d <= this._drag_start) var f = b.shift || (this._table_view && !b.custom ? 864E5 : 0),
                    c = d - (b.shift ? 0 : f),
                    d = this._drag_start + (f || this.config.time_step * 6E4);
                else c = this._drag_start;
                else d <= c && (d = c + this.config.time_step * 6E4)
            }
            var g = new Date(d - 1),
                i = new Date(c);
            if (this._table_view || g.getDate() == i.getDate() && g.getHours() < this.config.last_hour || scheduler._wa && scheduler._wa._dnd) e.start_date = i, e.end_date = new Date(d), this.config.update_render ? this.update_view() : this.updateEvent(this._drag_id);
            this._table_view && this.for_rendered(this._drag_id, function (a) {
                a.className += " dhx_in_move"
            })
        }
    } else if (scheduler.checkEvent("onMouseMove")) {
        var h = this._locate_event(a.target || a.srcElement);
        this.callEvent("onMouseMove", [h, a])
    }
};
scheduler._on_mouse_context = function (a, b) {
    return this.callEvent("onContextMenu", [this._locate_event(b), a])
};
scheduler._on_mouse_down = function (a, b) {
    if (!this.config.readonly && !this._drag_mode) {
        b = b || a.target || a.srcElement;
        if (a.button == 2 || a.ctrlKey) return this._on_mouse_context(a, b);
        switch (b.className.split(" ")[0]) {
        case "dhx_cal_event_line":
        case "dhx_cal_event_clear":
            if (this._table_view) this._drag_mode = "move";
            break;
        case "dhx_header":
        case "dhx_title":
        case "dhx_wa_ev_body":
            this._drag_mode = "move";
            break;
        case "dhx_footer":
            this._drag_mode = "resize";
            break;
        case "dhx_scale_holder":
        case "dhx_scale_holder_now":
        case "dhx_month_body":
        case "dhx_matrix_cell":
            this._drag_mode = "create";
            break;
        case "":
            if (b.parentNode) return scheduler._on_mouse_down(a, b.parentNode);
        default:
            this._drag_id = this._drag_mode = null
        }
        if (this._drag_mode) {
            var c = this._locate_event(b);
            !this.config["drag_" + this._drag_mode] || !this.callEvent("onBeforeDrag", [c, this._drag_mode, a]) ? this._drag_mode = this._drag_id = 0 : (this._drag_id = c, this._drag_event = scheduler._lame_copy({}, this._copy_event(this.getEvent(this._drag_id) || {})))
        }
        this._drag_start = null
    }
};
scheduler._on_mouse_up = function (a) {
    if (this._drag_mode && this._drag_id) {
        this._els.dhx_cal_data[0].style.cursor = "default";
        var b = this.getEvent(this._drag_id);
        if (this._drag_event._dhx_changed || !this._drag_event.start_date || b.start_date.valueOf() != this._drag_event.start_date.valueOf() || b.end_date.valueOf() != this._drag_event.end_date.valueOf()) {
            var c = this._drag_mode == "new-size";
            if (this.callEvent("onBeforeEventChanged", [b, a, c])) if (c && this.config.edit_on_create) {
                this.unselect();
                this._new_event = new Date;
                if (this._table_view || this.config.details_on_create) return this._drag_mode = null, this.showLightbox(this._drag_id);
                this._drag_pos = !0;
                this._select_id = this._edit_id = this._drag_id
            } else this._new_event || this.callEvent(c ? "onEventAdded" : "onEventChanged", [this._drag_id, this.getEvent(this._drag_id)]);
            else c ? this.deleteEvent(b.id, !0) : (this._drag_event._dhx_changed = !1, scheduler._lame_copy(b, this._drag_event), this.updateEvent(b.id))
        }
        this._drag_pos && this.render_view_data()
    }
    this._drag_pos = this._drag_mode = null
};
scheduler.update_view = function () {
    this._reset_scale();
    if (this._load_mode && this._load()) return this._render_wait = !0;
    this.render_view_data()
};
scheduler.setCurrentView = function (a, b) {
    b = b || this._mode;
    if (this.callEvent("onBeforeViewChange", [this._mode, this._date, b, a])) {
        if (this[this._mode + "_view"] && b && this._mode != b) this[this._mode + "_view"](!1);
        this._close_not_saved();
        var c = "dhx_multi_day";
        this._els[c] && (this._els[c][0].parentNode.removeChild(this._els[c][0]), this._els[c] = null);
        this._mode = b;
        this._date = a;
        this._table_view = this._mode == "month";
        for (var d = this._els.dhx_cal_tab, e = 0; e < d.length; e++) d[e].className = "dhx_cal_tab" + (d[e].getAttribute("name") == this._mode + "_tab" ? " active" : "");
        var f = this[this._mode + "_view"];
        f ? f(!0) : this.update_view();
        this.callEvent("onViewChange", [this._mode, this._date])
    }
};
scheduler._render_x_header = function (a, b, c, d) {
    var e = document.createElement("DIV");
    e.className = "dhx_scale_bar";
    this.set_xy(e, this._cols[a] - 1, this.xy.scale_height - 2, b, 0);
    e.innerHTML = this.templates[this._mode + "_scale_date"](c, this._mode);
    d.appendChild(e)
};
scheduler._reset_scale = function () {
    if (this.templates[this._mode + "_date"]) {
        var a = this._els.dhx_cal_header[0],
            b = this._els.dhx_cal_data[0],
            c = this.config;
        a.innerHTML = "";
        b.scrollTop = 0;
        b.innerHTML = "";
        var d = (c.readonly || !c.drag_resize ? " dhx_resize_denied" : "") + (c.readonly || !c.drag_move ? " dhx_move_denied" : "");
        if (d) b.className = "dhx_cal_data" + d;
        this._cols = [];
        this._colsS = {
            height: 0
        };
        this._dy_shift = 0;
        this.set_sizes();
        var e = parseInt(a.style.width),
            f = 0,
            g, i, h, k;
        i = this.date[this._mode + "_start"](new Date(this._date.valueOf()));
        g = h = this._table_view ? scheduler.date.week_start(i) : i;
        k = this.date.date_part(new Date);
        var j = scheduler.date.add(i, 1, this._mode),
            l = 7;
        if (!this._table_view) {
            var o = this.date["get_" + this._mode + "_end"];
            o && (j = o(i));
            l = Math.round((j.valueOf() - i.valueOf()) / 864E5)
        }
        this._min_date = g;
        this._els.dhx_cal_date[0].innerHTML = this.templates[this._mode + "_date"](i, j, this._mode);
        for (var m = 0; m < l; m++) {
            this._cols[m] = Math.floor(e / (l - m));
            this._render_x_header(m, f, g, a);
            if (!this._table_view) {
                var n = document.createElement("DIV"),
                    p = "dhx_scale_holder";
                g.valueOf() == k.valueOf() && (p = "dhx_scale_holder_now");
                n.className = p + " " + this.templates.week_date_class(g, k);
                this.set_xy(n, this._cols[m] - 1, c.hour_size_px * (c.last_hour - c.first_hour), f + this.xy.scale_width + 1, 0);
                b.appendChild(n);
                this.callEvent("onScaleAdd", [n, g])
            }
            g = this.date.add(g, 1, "day");
            e -= this._cols[m];
            f += this._cols[m];
            this._colsS[m] = (this._cols[m - 1] || 0) + (this._colsS[m - 1] || (this._table_view ? 0 : this.xy.scale_width + 2));
            this._colsS.col_length = l + 1
        }
        this._max_date = g;
        this._colsS[l] = this._cols[l - 1] + this._colsS[l - 1];
        if (this._table_view) this._reset_month_scale(b, i, h);
        else {
            this._reset_hours_scale(b, i, h);
            if (c.multi_day) {
                var q = "dhx_multi_day";
                this._els[q] && (this._els[q][0].parentNode.removeChild(this._els[q][0]), this._els[q] = null);
                var v = this._els.dhx_cal_navline[0],
                    s = v.offsetHeight + this._els.dhx_cal_header[0].offsetHeight + 1,
                    r = document.createElement("DIV");
                r.className = q;
                r.style.visibility = "hidden";
                this.set_xy(r, this._colsS[this._colsS.col_length - 1] + this.xy.scroll_width, 0, 0, s);
                b.parentNode.insertBefore(r, b);
                var u = r.cloneNode(!0);
                u.className = q + "_icon";
                u.style.visibility = "hidden";
                this.set_xy(u, this.xy.scale_width, 0, 0, s);
                r.appendChild(u);
                this._els[q] = [r, u];
                this._els[q][0].onclick = this._click.dhx_cal_data
            }
            if (this.config.mark_now) {
                var t = new Date;
                if (t < this._max_date && t > this._min_date && t.getHours() >= this.config.first_hour && t.getHours() < this.config.last_hour) {
                    var A = this.locate_holder_day(t),
                        B = t.getHours() * 60 + t.getMinutes(),
                        y = document.createElement("DIV");
                    y.className = "dhx_now_time";
                    y.style.top = Math.round((B * 6E4 - this.config.first_hour * 36E5) * this.config.hour_size_px / 36E5) % (this.config.hour_size_px * 24) + 1 + "px";
                    b.childNodes[A].appendChild(y)
                }
            }
        }
    }
};
scheduler._reset_hours_scale = function (a) {
    var b = document.createElement("DIV");
    b.className = "dhx_scale_holder";
    for (var c = new Date(1980, 1, 1, this.config.first_hour, 0, 0), d = this.config.first_hour * 1; d < this.config.last_hour; d++) {
        var e = document.createElement("DIV");
        e.className = "dhx_scale_hour";
        e.style.height = this.config.hour_size_px - (this._quirks ? 0 : 1) + "px";
        e.style.width = this.xy.scale_width + "px";
        e.innerHTML = scheduler.templates.hour_scale(c);
        b.appendChild(e);
        c = this.date.add(c, 1, "hour")
    }
    a.appendChild(b);
    if (this.config.scroll_hour) a.scrollTop = this.config.hour_size_px * (this.config.scroll_hour - this.config.first_hour)
};
scheduler._reset_month_scale = function (a, b, c) {
    var d = scheduler.date.add(b, 1, "month"),
        e = new Date;
    this.date.date_part(e);
    this.date.date_part(c);
    var f = Math.ceil(Math.round((d.valueOf() - c.valueOf()) / 864E5) / 7),
        g = [],
        i = Math.floor(a.clientHeight / f) - 22;
    this._colsS.height = i + 22;
    for (var h = this._colsS.heights = [], k = 0; k <= 7; k++) g[k] = " style='height:" + i + "px; width:" + ((this._cols[k] || 0) - 1) + "px;' ";
    var j = 0;
    this._min_date = c;
    for (var l = "<table cellpadding='0' cellspacing='0'>", k = 0; k < f; k++) {
        l += "<tr>";
        for (var o = 0; o < 7; o++) {
            l += "<td";
            var m = "";
            c < b ? m = "dhx_before" : c >= d ? m = "dhx_after" : c.valueOf() == e.valueOf() && (m = "dhx_now");
            l += " class='" + m + " " + this.templates.month_date_class(c, e) + "' ";
            l += "><div class='dhx_month_head'>" + this.templates.month_day(c) + "</div><div class='dhx_month_body' " + g[o] + "></div></td>";
            c = this.date.add(c, 1, "day")
        }
        l += "</tr>";
        h[k] = j;
        j += this._colsS.height
    }
    l += "</table>";
    this._max_date = c;
    a.innerHTML = l;
    return c
};
scheduler.getLabel = function (a, b) {
    for (var c = this.config.lightbox.sections, d = 0; d < c.length; d++) if (c[d].map_to == a) for (var e = c[d].options, f = 0; f < e.length; f++) if (e[f].key == b) return e[f].label;
    return ""
};
scheduler.updateCollection = function (a, b) {
    var c = scheduler.serverList(a);
    if (!c) return !1;
    c.splice(0, c.length);
    c.push.apply(c, b || []);
    scheduler.callEvent("onOptionsLoad", []);
    scheduler.resetLightbox();
    return !0
};
scheduler._lame_copy = function (a, b) {
    for (var c in b) a[c] = b[c];
    return a
};
scheduler.date = {
    init: function () {
        for (var a = scheduler.locale.date.month_short, b = scheduler.locale.date.month_short_hash = {}, c = 0; c < a.length; c++) b[a[c]] = c;
        a = scheduler.locale.date.month_full;
        b = scheduler.locale.date.month_full_hash = {};
        for (c = 0; c < a.length; c++) b[a[c]] = c
    },
    date_part: function (a) {
        a.setHours(0);
        a.setMinutes(0);
        a.setSeconds(0);
        a.setMilliseconds(0);
        return a
    },
    time_part: function (a) {
        return (a.valueOf() / 1E3 - a.getTimezoneOffset() * 60) % 86400
    },
    week_start: function (a) {
        var b = a.getDay();
        scheduler.config.start_on_monday && (b === 0 ? b = 6 : b--);
        return this.date_part(this.add(a, -1 * b, "day"))
    },
    month_start: function (a) {
        a.setDate(1);
        return this.date_part(a)
    },
    year_start: function (a) {
        a.setMonth(0);
        return this.month_start(a)
    },
    day_start: function (a) {
        return this.date_part(a)
    },
    add: function (a, b, c) {
        var d = new Date(a.valueOf());
        switch (c) {
        case "day":
            d.setDate(d.getDate() + b);
            if (a.getDate() == d.getDate() && b) {
                do d.setTime(d.getTime() + 36E5);
                while (a.getDate() == d.getDate())
            }
            break;
        case "week":
            d.setDate(d.getDate() + 7 * b);
            break;
        case "month":
            d.setMonth(d.getMonth() + b);
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
    to_fixed: function (a) {
        return a < 10 ? "0" + a : a
    },
    copy: function (a) {
        return new Date(a.valueOf())
    },
    date_to_str: function (a, b) {
        a = a.replace(/%[a-zA-Z]/g, function (a) {
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
    str_to_date: function (a, b) {
        for (var c = "var temp=date.split(/[^0-9a-zA-Z]+/g);", d = a.match(/%[a-zA-Z]/g), e = 0; e < d.length; e++) switch (d[e]) {
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
            c += "set[1]=scheduler.locale.date.month_full_hash[temp[" + e + "]]||0;"
        }
        var f = "set[0],set[1],set[2],set[3],set[4],set[5]";
        b && (f = " Date.UTC(" + f + ")");
        return new Function("date", "var set=[0,0,1,0,0,0]; " + c + " return new Date(" + f + ");")
    },
    getISOWeek: function (a) {
        if (!a) return !1;
        var b = a.getDay();
        b === 0 && (b = 7);
        var c = new Date(a.valueOf());
        c.setDate(a.getDate() + (4 - b));
        var d = c.getFullYear(),
            e = Math.round((c.getTime() - (new Date(d, 0, 1)).getTime()) / 864E5),
            f = 1 + Math.floor(e / 7);
        return f
    },
    getUTCISOWeek: function (a) {
        return this.getISOWeek(a)
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
        new_event: "新预约",
        icon_save: "保存",
        icon_cancel: "取消",
        icon_details: "详细",
        icon_edit: "编辑",
        icon_delete: "删除",
        confirm_closing: "",
        confirm_deleting: "你确定要删除?",
        section_description: "申请内容",
        section_time: "时间段",
        full_day: "全天",
        confirm_recurring: "你确定要重复?",
        section_recurring: "重复时间",
        button_recurring: "关闭",
        button_recurring_open: "开启",
        agenda_tab: "Agenda",
        date: "时间",
        description: "描述",
        year_tab: "年"
    }
};
scheduler.config = {
    default_date: "%Y %M  %j",
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
    first_hour: 7,
    last_hour: 24,
    readonly: !1,
    drag_resize: !1,
    drag_move: !1,
    drag_create: 1,
    dblclick_create: !1,
    edit_on_create: 1,
    details_on_create: 1,
    click_form_details: 1,
    cascade_event_display: 0,
    cascade_event_count: 4,
    cascade_event_margin: 30,
    drag_lightbox: !0,
    server_utc: !1,
    positive_closing: !1,
    icons_edit: ["icon_save", "icon_cancel"],
    icons_select: ["icon_details", "icon_edit", "icon_delete"],
    buttons_left: ["dhx_save_btn", "dhx_cancel_btn"],
    buttons_right: ["dhx_delete_btn"],
    lightbox: {
        sections: [{
            name: "description",
            height: 200,
            map_to: "text",
            type: "textarea",
            focus: !0
        }, {
            name: "time",
            height: 72,
            type: "time",
            map_to: "auto"
        }]
    },
    repeat_date_of_end: "01.01.2010"
};
scheduler.templates = {};
scheduler.init_templates = function () {
    var a = scheduler.date.date_to_str,
        b = scheduler.config,
        c = function (a, b) {
            for (var c in b) a[c] || (a[c] = b[c])
        };
    c(scheduler.templates, {
        day_date: a(b.default_date),
        month_date: a(b.month_date),
        week_date: function (a, b) {
            return scheduler.templates.day_date(a) + " &ndash; " + scheduler.templates.day_date(scheduler.date.add(b, -1, "day"))
        },
        day_scale_date: a(b.default_date),
        month_scale_date: a(b.week_date),
        week_scale_date: a(b.day_date),
        hour_scale: a(b.hour_date),
        time_picker: a(b.hour_date),
        event_date: a(b.hour_date),
        month_day: a(b.month_day),
        xml_date: scheduler.date.str_to_date(b.xml_date, b.server_utc),
        load_format: a(b.load_date, b.server_utc),
        xml_format: a(b.xml_date, b.server_utc),
        api_date: scheduler.date.str_to_date(b.api_date),
        event_header: function (a, b) {
            return scheduler.templates.event_date(a) + " - " + scheduler.templates.event_date(b)
        },
        event_text: function (a, b, c) {
            return c.text
        },
        event_class: function () {
            return ""
        },
        month_date_class: function () {
            return ""
        },
        week_date_class: function () {
            return ""
        },
        event_bar_date: function (a) {
            return scheduler.templates.event_date(a) + " "
        },
        event_bar_text: function (a, b, c) {
            return c.text
        }
    });
    this.callEvent("onTemplatesReady", [])
};
scheduler.uid = function () {
    if (!this._seed) this._seed = (new Date).valueOf();
    return this._seed++
};
scheduler._events = {};
scheduler.clearAll = function () {
    this._events = {};
    this._loaded = {};
    this.clear_view()
};
scheduler.addEvent = function (a, b, c, d, e) {
    if (!arguments.length) return this.addEventNow();
    var f = a;
    if (arguments.length != 1) f = e || {}, f.start_date = a, f.end_date = b, f.text = c, f.id = d;
    f.id = f.id || scheduler.uid();
    f.text = f.text || "";
    if (typeof f.start_date == "string") f.start_date = this.templates.api_date(f.start_date);
    if (typeof f.end_date == "string") f.end_date = this.templates.api_date(f.end_date);
    var g = (this.config.event_duration || this.config.time_step) * 6E4;
    f.start_date.valueOf() == f.end_date.valueOf() && f.end_date.setTime(f.end_date.valueOf() + g);
    f._timed = this.is_one_day_event(f);
    var i = !this._events[f.id];
    this._events[f.id] = f;
    this.event_updated(f);
    this._loading || this.callEvent(i ? "onEventAdded" : "onEventChanged", [f.id, f])
};
scheduler.deleteEvent = function (a, b) {
    var c = this._events[a];
    if (b || this.callEvent("onBeforeEventDelete", [a, c])) this.callEvent("onConfirmedBeforeEventDelete", [a, c]), c && (delete this._events[a], this.unselect(a), this.event_updated(c)), this.callEvent("onEventDeleted", [a])
};
scheduler.getEvent = function (a) {
    return this._events[a]
};
scheduler.setEvent = function (a, b) {
    this._events[a] = b
};
scheduler.for_rendered = function (a, b) {
    for (var c = this._rendered.length - 1; c >= 0; c--) this._rendered[c].getAttribute("event_id") == a && b(this._rendered[c], c)
};
scheduler.changeEventId = function (a, b) {
    if (a != b) {
        var c = this._events[a];
        if (c) c.id = b, this._events[b] = c, delete this._events[a];
        this.for_rendered(a, function (a) {
            a.setAttribute("event_id", b)
        });
        if (this._select_id == a) this._select_id = b;
        if (this._edit_id == a) this._edit_id = b;
        this.callEvent("onEventIdChange", [a, b])
    }
};
(function () {
    for (var a = "text,Text,start_date,StartDate,end_date,EndDate".split(","), b = function (a) {
            return function (b) {
                return scheduler.getEvent(b)[a]
            }
        }, c = function (a) {
            return function (b, c) {
                var d = scheduler.getEvent(b);
                d[a] = c;
                d._changed = !0;
                d._timed = this.is_one_day_event(d);
                scheduler.event_updated(d, !0)
            }
        }, d = 0; d < a.length; d += 2) scheduler["getEvent" + a[d + 1]] = b(a[d]), scheduler["setEvent" + a[d + 1]] = c(a[d])
})();
scheduler.event_updated = function (a) {
    this.is_visible_events(a) ? this.render_view_data() : this.clear_event(a.id)
};
scheduler.is_visible_events = function (a) {
    return a.start_date < this._max_date && this._min_date < a.end_date
};
scheduler.is_one_day_event = function (a) {
    var b = a.end_date.getDate() - a.start_date.getDate();
    return b ? (b < 0 && (b = Math.ceil((a.end_date.valueOf() - a.start_date.valueOf()) / 864E5)), b == 1 && !a.end_date.getHours() && !a.end_date.getMinutes() && (a.start_date.getHours() || a.start_date.getMinutes())) : a.start_date.getMonth() == a.end_date.getMonth() && a.start_date.getFullYear() == a.end_date.getFullYear()
};
scheduler.get_visible_events = function () {
    var a = [],
        b = this["filter_" + this._mode],
        c;
    for (c in this._events) if (this.is_visible_events(this._events[c]) && (this._table_view || this.config.multi_day || this._events[c]._timed))(!b || b(c, this._events[c])) && a.push(this._events[c]);
    return a
};
scheduler.render_view_data = function (a, b) {
    if (!a) {
        if (this._not_render) {
            this._render_wait = !0;
            return
        }
        this._render_wait = !1;
        this.clear_view();
        a = this.get_visible_events()
    }
    if (this.config.multi_day && !this._table_view) {
        for (var c = [], d = [], e = 0; e < a.length; e++) a[e]._timed ? c.push(a[e]) : d.push(a[e]);
        this._rendered_location = this._els.dhx_multi_day[0];
        this._table_view = !0;
        this.render_data(d, b);
        this._table_view = !1;
        this._rendered_location = this._els.dhx_cal_data[0];
        this._table_view = !1;
        this.render_data(c, b)
    } else this._rendered_location = this._els.dhx_cal_data[0], this.render_data(a, b)
};
scheduler.render_data = function (a, b) {
    for (var a = this._pre_render_events(a, b), c = 0; c < a.length; c++) this._table_view ? this.render_event_bar(a[c]) : this.render_event(a[c])
};
scheduler._pre_render_events = function (a, b) {
    var c = this.xy.bar_height,
        d = this._colsS.heights,
        e = this._colsS.heights = [0, 0, 0, 0, 0, 0, 0],
        f = this._els.dhx_cal_data[0],
        a = this._table_view ? this._pre_render_events_table(a, b) : this._pre_render_events_line(a, b);
    if (this._table_view) if (b) this._colsS.heights = d;
    else {
        var g = f.firstChild;
        if (g.rows) {
            for (var i = 0; i < g.rows.length; i++) {
                e[i]++;
                if (e[i] * c > this._colsS.height - 22) {
                    for (var h = g.rows[i].cells, k = 0; k < h.length; k++) h[k].childNodes[1].style.height = e[i] * c + "px";
                    e[i] = (e[i - 1] || 0) + h[0].offsetHeight
                }
                e[i] = (e[i - 1] || 0) + g.rows[i].cells[0].offsetHeight
            }
            e.unshift(0);
            if (g.parentNode.offsetHeight < g.parentNode.scrollHeight && !g._h_fix) {
                for (i = 0; i < g.rows.length; i++) {
                    var j = g.rows[i].cells[6].childNodes[0],
                        l = j.offsetWidth - scheduler.xy.scroll_width + "px";
                    j.style.width = l;
                    j.nextSibling.style.width = l
                }
                g._h_fix = !0
            }
        } else if (!a.length && this._els.dhx_multi_day[0].style.visibility == "visible" && (e[0] = -1), a.length || e[0] == -1) {
            var o = g.parentNode.childNodes,
                m = (e[0] + 1) * c + 1 + "px";
            f.style.top = this._els.dhx_cal_navline[0].offsetHeight + this._els.dhx_cal_header[0].offsetHeight + parseInt(m) + "px";
            f.style.height = this._obj.offsetHeight - parseInt(f.style.top) - (this.xy.margin_top || 0) + "px";
            var n = this._els.dhx_multi_day[0];
            n.style.height = m;
            n.style.visibility = e[0] == -1 ? "hidden" : "visible";
            n = this._els.dhx_multi_day[1];
            n.style.height = m;
            n.style.visibility = e[0] == -1 ? "hidden" : "visible";
            n.className = e[0] ? "dhx_multi_day_icon" : "dhx_multi_day_icon_small";
            this._dy_shift = (e[0] + 1) * c;
            e[0] = 0
        }
    }
    return a
};
scheduler._get_event_sday = function (a) {
    return Math.floor((a.start_date.valueOf() - this._min_date.valueOf()) / 864E5)
};
scheduler._pre_render_events_line = function (a, b) {
    a.sort(function (a, b) {
        return a.start_date.valueOf() == b.start_date.valueOf() ? a.id > b.id ? 1 : -1 : a.start_date > b.start_date ? 1 : -1
    });
    for (var c = [], d = [], e = 0; e < a.length; e++) {
        var f = a[e],
            g = f.start_date.getHours(),
            i = f.end_date.getHours();
        f._sday = this._get_event_sday(f);
        c[f._sday] || (c[f._sday] = []);
        if (!b) {
            f._inner = !1;
            for (var h = c[f._sday]; h.length && h[h.length - 1].end_date <= f.start_date;) h.splice(h.length - 1, 1);
            for (var k = !1, j = 0; j < h.length; j++) if (h[j].end_date.valueOf() < f.start_date.valueOf()) {
                k = !0;
                f._sorder = h[j]._sorder;
                h.splice(j, 1);
                f._inner = !0;
                break
            }
            if (h.length) h[h.length - 1]._inner = !0;
            if (!k) if (h.length) if (h.length <= h[h.length - 1]._sorder) {
                if (h[h.length - 1]._sorder) for (j = 0; j < h.length; j++) {
                    var l = !1;
                    for (k = 0; k < h.length; k++) if (h[k]._sorder == j) {
                        l = !0;
                        break
                    }
                    if (!l) {
                        f._sorder = j;
                        break
                    }
                } else f._sorder = 0;
                f._inner = !0
            } else {
                l = h[0]._sorder;
                for (j = 1; j < h.length; j++) if (h[j]._sorder > l) l = h[j]._sorder;
                f._sorder = l + 1;
                f._inner = !1
            } else f._sorder = 0;
            h.push(f);
            h.length > (h.max_count || 0) ? (h.max_count = h.length, f._count = h.length) : f._count = f._count ? f._count : 1
        }
        if (g < this.config.first_hour || i >= this.config.last_hour) if (d.push(f), a[e] = f = this._copy_event(f), g < this.config.first_hour && (f.start_date.setHours(this.config.first_hour), f.start_date.setMinutes(0)), i >= this.config.last_hour && (f.end_date.setMinutes(0), f.end_date.setHours(this.config.last_hour)), f.start_date > f.end_date || g == this.config.last_hour) a.splice(e, 1), e--
    }
    if (!b) {
        for (e = 0; e < a.length; e++) a[e]._count = c[a[e]._sday].max_count;
        for (e = 0; e < d.length; e++) d[e]._count = c[d[e]._sday].max_count
    }
    return a
};
scheduler._time_order = function (a) {
    a.sort(function (a, c) {
        return a.start_date.valueOf() == c.start_date.valueOf() ? a._timed && !c._timed ? 1 : !a._timed && c._timed ? -1 : a.id > c.id ? 1 : -1 : a.start_date > c.start_date ? 1 : -1
    })
};
scheduler._pre_render_events_table = function (a, b) {
    this._time_order(a);
    for (var c = [], d = [
        [],
        [],
        [],
        [],
        [],
        [],
        []
    ], e = this._colsS.heights, f, g = this._cols.length, i = 0; i < a.length; i++) {
        var h = a[i],
            k = f || h.start_date,
            j = h.end_date;
        if (k < this._min_date) k = this._min_date;
        if (j > this._max_date) j = this._max_date;
        var l = this.locate_holder_day(k, !1, h);
        h._sday = l % g;
        var o = this.locate_holder_day(j, !0, h) || g;
        h._eday = o % g || g;
        h._length = o - l;
        h._sweek = Math.floor((this._correct_shift(k.valueOf(), 1) - this._min_date.valueOf()) / (864E5 * g));
        var m = d[h._sweek],
            n;
        for (n = 0; n < m.length; n++) if (m[n]._eday <= h._sday) break;
        if (!h._sorder || !b) h._sorder = n;
        if (h._sday + h._length <= g) f = null, c.push(h), m[n] = h, e[h._sweek] = m.length - 1;
        else {
            var p = this._copy_event(h);
            p._length = g - h._sday;
            p._eday = g;
            p._sday = h._sday;
            p._sweek = h._sweek;
            p._sorder = h._sorder;
            p.end_date = this.date.add(k, p._length, "day");
            c.push(p);
            m[n] = p;
            f = p.end_date;
            e[h._sweek] = m.length - 1;
            i--
        }
    }
    return c
};
scheduler._copy_dummy = function () {
    this.start_date = new Date(this.start_date);
    this.end_date = new Date(this.end_date)
};
scheduler._copy_event = function (a) {
    this._copy_dummy.prototype = a;
    return new this._copy_dummy
};
scheduler._rendered = [];
scheduler.clear_view = function () {
    for (var a = 0; a < this._rendered.length; a++) {
        var b = this._rendered[a];
        b.parentNode && b.parentNode.removeChild(b)
    }
    this._rendered = []
};
scheduler.updateEvent = function (a) {
    var b = this.getEvent(a);
    this.clear_event(a);
    b && this.is_visible_events(b) && this.render_view_data([b], !0)
};
scheduler.clear_event = function (a) {
    this.for_rendered(a, function (a, c) {
        a.parentNode && a.parentNode.removeChild(a);
        scheduler._rendered.splice(c, 1)
    })
};
scheduler.render_event = function (a) {
    var b = scheduler.xy.menu_width;
    if (!(a._sday < 0)) {
        var c = scheduler.locate_holder(a._sday);
        if (c) {
            var d = a.start_date.getHours() * 60 + a.start_date.getMinutes(),
                e = a.end_date.getHours() * 60 + a.end_date.getMinutes() || scheduler.config.last_hour * 60,
                f = Math.round((d * 6E4 - this.config.first_hour * 36E5) * this.config.hour_size_px / 36E5) % (this.config.hour_size_px * 24) + 1,
                g = Math.max(scheduler.xy.min_event_height, (e - d) * this.config.hour_size_px / 60) + 1,
                i = Math.floor((c.clientWidth - b) / a._count),
                h = a._sorder * i + 1;
            a._inner || (i *= a._count - a._sorder);
            if (this.config.cascade_event_display) var k = this.config.cascade_event_count,
                j = this.config.cascade_event_margin,
                h = a._sorder % k * j,
                l = a._inner ? (a._count - a._sorder - 1) % k * j / 2 : 0,
                i = Math.floor(c.clientWidth - b - h - l);
            var o = this._render_v_bar(a.id, b + h, f, i, g, a._text_style, scheduler.templates.event_header(a.start_date, a.end_date, a), scheduler.templates.event_text(a.start_date, a.end_date, a));
            this._rendered.push(o);
            c.appendChild(o);
            h = h + parseInt(c.style.left, 10) + b;
            if (this._edit_id == a.id) {
                o.style.zIndex = 1;
                i = Math.max(i - 4, scheduler.xy.editor_width);
                o = document.createElement("DIV");
                o.setAttribute("event_id", a.id);
                this.set_xy(o, i, g - 20, h, f + 14);
                o.className = "dhx_cal_editor";
                var m = document.createElement("DIV");
                this.set_xy(m, i - 6, g - 26);
                m.style.cssText += ";margin:2px 2px 2px 2px;overflow:hidden;";
                o.appendChild(m);
                this._els.dhx_cal_data[0].appendChild(o);
                this._rendered.push(o);
                m.innerHTML = "<textarea class='dhx_cal_editor'>" + a.text + "</textarea>";
                if (this._quirks7) m.firstChild.style.height = g - 12 + "px";
                this._editor = m.firstChild;
                this._editor.onkeypress = function (a) {
                    if ((a || event).shiftKey) return !0;
                    var b = (a || event).keyCode;
                    b == scheduler.keys.edit_save && scheduler.editStop(!0);
                    b == scheduler.keys.edit_cancel && scheduler.editStop(!1)
                };
                this._editor.onselectstart = function (a) {
                    return (a || event).cancelBubble = !0
                };
                m.firstChild.focus();
                this._els.dhx_cal_data[0].scrollLeft = 0;
                m.firstChild.select()
            }
            if (this._select_id == a.id) {
                if (this.config.cascade_event_display && this._drag_mode) o.style.zIndex = 1;
                for (var n = this.config["icons_" + (this._edit_id == a.id ? "edit" : "select")], p = "", q = a.color ? "background-color:" + a.color + ";" : "", v = a.textColor ? "color:" + a.textColor + ";" : "", s = 0; s < n.length; s++) p += "<div class='dhx_menu_icon " + n[s] + "' style='" + q + "" + v + "' title='" + this.locale.labels[n[s]] + "'></div>";
                var r = this._render_v_bar(a.id, h - b + 1, f, b, n.length * 20 + 26, "", "<div style='" + q + "" + v + "' class='dhx_menu_head'></div>", p, !0);
                r.style.left = h - b + 1;
                this._els.dhx_cal_data[0].appendChild(r);
                this._rendered.push(r)
            }
        }
    }
};
scheduler._render_v_bar = function (a, b, c, d, e, f, g, i, h) {
    var k = document.createElement("DIV"),
        j = this.getEvent(a),
        l = "dhx_cal_event",
        o = scheduler.templates.event_class(j.start_date, j.end_date, j);
    o && (l = l + " " + o);
    var m = j.color ? "background-color:" + j.color + ";" : "",
        n = j.textColor ? "color:" + j.textColor + ";" : "",
        p = '<div event_id="' + a + '" class="' + l + '" style="position:absolute; top:' + c + "px; left:" + b + "px; width:" + (d - 4) + "px; height:" + e + "px;" + (f || "") + '">';
    p += '<div class="dhx_header" style=" width:' + (d - 6) + "px;" + m + '" >&nbsp;</div>';
    p += '<div class="dhx_title" style="' + m + "" + n + '">' + g + "</div>";
    p += '<div class="dhx_body" style=" width:' + (d - (this._quirks ? 4 : 14)) + "px; height:" + (e - (this._quirks ? 20 : 30)) + "px;" + m + "" + n + '">' + i + "</div>";
    p += '<div class="dhx_footer" style=" width:' + (d - 8) + "px;" + (h ? " margin-top:-1px;" : "") + "" + m + "" + n + '" ></div></div>';
    k.innerHTML = p;
    return k.firstChild
};
scheduler.locate_holder = function (a) {
    return this._mode == "day" ? this._els.dhx_cal_data[0].firstChild : this._els.dhx_cal_data[0].childNodes[a]
};
scheduler.locate_holder_day = function (a, b) {
    var c = Math.floor((this._correct_shift(a, 1) - this._min_date) / 864E5);
    b && this.date.time_part(a) && c++;
    return c
};
scheduler.render_event_bar = function (a) {
    var b = this._rendered_location,
        c = this._colsS[a._sday],
        d = this._colsS[a._eday];
    d == c && (d = this._colsS[a._eday + 1]);
    var e = this.xy.bar_height,
        f = this._colsS.heights[a._sweek] + (this._colsS.height ? this.xy.month_scale_height + 2 : 2) + a._sorder * e,
        g = document.createElement("DIV"),
        i = a._timed ? "dhx_cal_event_clear" : "dhx_cal_event_line",
        h = scheduler.templates.event_class(a.start_date, a.end_date, a);
    h && (i = i + " " + h);
    var k = a.color ? "background-color:" + a.color + ";" : "",
        j = a.textColor ? "color:" + a.textColor + ";" : "",
        l = '<div event_id="' + a.id + '" class="' + i + '" style="position:absolute; top:' + f + "px; left:" + c + "px; width:" + (d - c - 15) + "px;" + j + "" + k + "" + (a._text_style || "") + '">';
    a._timed && (l += scheduler.templates.event_bar_date(a.start_date, a.end_date, a));
    l += scheduler.templates.event_bar_text(a.start_date, a.end_date, a) + "</div>";
    l += "</div>";
    g.innerHTML = l;
    this._rendered.push(g.firstChild);
    b.appendChild(g.firstChild)
};
scheduler._locate_event = function (a) {
    for (var b = null; a && !b && a.getAttribute;) b = a.getAttribute("event_id"), a = a.parentNode;
    return b
};
scheduler.edit = function (a) {
    if (this._edit_id != a) this.editStop(!1, a), this._edit_id = a, this.updateEvent(a)
};
scheduler.editStop = function (a, b) {
    if (!(b && this._edit_id == b)) {
        var c = this.getEvent(this._edit_id);
        if (c) {
            if (a) c.text = this._editor.value;
            this._editor = this._edit_id = null;
            this.updateEvent(c.id);
            this._edit_stop_event(c, a)
        }
    }
};
scheduler._edit_stop_event = function (a, b) {
    this._new_event ? (b ? this.callEvent("onEventAdded", [a.id, a]) : this.deleteEvent(a.id, !0), this._new_event = null) : b && this.callEvent("onEventChanged", [a.id, a])
};
scheduler.getEvents = function (a, b) {
    var c = [],
        d;
    for (d in this._events) {
        var e = this._events[d];
        e && e.start_date < b && e.end_date > a && c.push(e)
    }
    return c
};
scheduler._loaded = {};
scheduler._load = function (a, b) {
    a = a || this._load_url;
    a += (a.indexOf("?") == -1 ? "?" : "&") + "timeshift=" + (new Date).getTimezoneOffset();
    this.config.prevent_cache && (a += "&uid=" + this.uid());
    var c, b = b || this._date;
    if (this._load_mode) {
        for (var d = this.templates.load_format, b = this.date[this._load_mode + "_start"](new Date(b.valueOf())); b > this._min_date;) b = this.date.add(b, -1, this._load_mode);
        c = b;
        for (var e = !0; c < this._max_date;) c = this.date.add(c, 1, this._load_mode), this._loaded[d(b)] && e ? b = this.date.add(b, 1, this._load_mode) : e = !1;
        var f = c;
        do c = f, f = this.date.add(c, -1, this._load_mode);
        while (f > b && this._loaded[d(f)]);
        if (c <= b) return !1;
        for (dhtmlxAjax.get(a + "&from=" + d(b) + "&to=" + d(c), function (a) {
            scheduler.on_load(a)
        }); b < c;) this._loaded[d(b)] = !0, b = this.date.add(b, 1, this._load_mode)
    } else dhtmlxAjax.get(a, function (a) {
        scheduler.on_load(a)
    });
    this.callEvent("onXLS", []);
    return !0
};
scheduler.on_load = function (a) {
    this._loading = !0;
    var b = this._process ? this[this._process].parse(a.xmlDoc.responseText) : this._magic_parser(a);
    this._not_render = !0;
    for (var c = 0; c < b.length; c++) this.callEvent("onEventLoading", [b[c]]) && this.addEvent(b[c]);
    this._not_render = !1;
    this._render_wait && this.render_view_data();
    this._after_call && this._after_call();
    this._after_call = null;
    this._loading = !1;
    this.callEvent("onXLE", [])
};
scheduler.json = {};
scheduler.json.parse = function (a) {
    if (typeof a == "string") eval("scheduler._temp = " + a + ";"), a = scheduler._temp;
    for (var b = [], c = 0; c < a.length; c++) a[c].start_date = scheduler.templates.xml_date(a[c].start_date), a[c].end_date = scheduler.templates.xml_date(a[c].end_date), b.push(a[c]);
    return b
};
scheduler.parse = function (a, b) {
    this._process = b;
    this.on_load({
        xmlDoc: {
            responseText: a
        }
    })
};
scheduler.load = function (a, b, c) {
    if (typeof b == "string") this._process = b, b = c;
    this._load_url = a;
    this._after_call = b;
    this._load(a, this._date)
};
scheduler.setLoadMode = function (a) {
    a == "all" && (a = "");
    this._load_mode = a
};
scheduler.refresh = function () {
    alert("not implemented")
};
scheduler.serverList = function (a, b) {
    return b ? this.serverList[a] = b.slice(0) : this.serverList[a] = this.serverList[a] || []
};
scheduler._userdata = {};
scheduler._magic_parser = function (a) {
    if (!a.getXMLTopNode) {
        var b = a.xmlDoc.responseText,
            a = new dtmlXMLLoaderObject(function () {});
        a.loadXMLString(b)
    }
    var c = a.getXMLTopNode("data");
    if (c.tagName != "data") return [];
    for (var d = a.doXPath("//coll_options"), e = 0; e < d.length; e++) {
        var f = d[e].getAttribute("for"),
            g = this.serverList[f];
        if (g) {
            g.splice(0, g.length);
            for (var i = a.doXPath(".//item", d[e]), h = 0; h < i.length; h++) {
                for (var k = i[h], j = k.attributes, l = {
                    key: i[h].getAttribute("value"),
                    label: i[h].getAttribute("label")
                }, o = 0; o < j.length; o++) {
                    var m = j[o];
                    if (!(m.nodeName == "value" || m.nodeName == "label")) l[m.nodeName] = m.nodeValue
                }
                g.push(l)
            }
        }
    }
    d.length && scheduler.callEvent("onOptionsLoad", []);
    for (var n = a.doXPath("//userdata"), e = 0; e < n.length; e++) {
        var p = this.xmlNodeToJSON(n[e]);
        this._userdata[p.name] = p.text
    }
    for (var q = [], c = a.doXPath("//event"), e = 0; e < c.length; e++) q[e] = this.xmlNodeToJSON(c[e]), q[e].text = q[e].text || q[e]._tagvalue, q[e].start_date = this.templates.xml_date(q[e].start_date), q[e].end_date = this.templates.xml_date(q[e].end_date);
    return q
};
scheduler.xmlNodeToJSON = function (a) {
    for (var b = {}, c = 0; c < a.attributes.length; c++) b[a.attributes[c].name] = a.attributes[c].value;
    for (c = 0; c < a.childNodes.length; c++) {
        var d = a.childNodes[c];
        d.nodeType == 1 && (b[d.tagName] = d.firstChild ? d.firstChild.nodeValue : "")
    }
    if (!b.text) b.text = a.firstChild ? a.firstChild.nodeValue : "";
    return b
};
scheduler.attachEvent("onXLS", function () {
    if (this.config.show_loading === !0) {
        var a;
        a = this.config.show_loading = document.createElement("DIV");
        a.className = "dhx_loading";
        a.style.left = Math.round((this._x - 128) / 2) + "px";
        a.style.top = Math.round((this._y - 15) / 2) + "px";
        this._obj.appendChild(a)
    }
});
scheduler.attachEvent("onXLE", function () {
    var a;
    if ((a = this.config.show_loading) && typeof a == "object") this._obj.removeChild(a), this.config.show_loading = !0
});
scheduler.ical = {
    parse: function (a) {
        var b = a.match(RegExp(this.c_start + "[^\u000c]*" + this.c_end, ""));
        if (b.length) {
            b[0] = b[0].replace(/[\r\n]+(?=[a-z \t])/g, " ");
            b[0] = b[0].replace(/\;[^:\r\n]*/g, "");
            for (var c = [], d, e = RegExp("(?:" + this.e_start + ")([^\u000c]*?)(?:" + this.e_end + ")", "g"); d = e.exec(b);) {
                for (var f = {}, g, i = /[^\r\n]+[\r\n]+/g; g = i.exec(d[1]);) this.parse_param(g.toString(), f);
                if (f.uid && !f.id) f.id = f.uid;
                c.push(f)
            }
            return c
        }
    },
    parse_param: function (a, b) {
        var c = a.indexOf(":");
        if (c != -1) {
            var d = a.substr(0, c).toLowerCase(),
                e = a.substr(c + 1).replace(/\\\,/g, ",").replace(/[\r\n]+$/, "");
            d == "summary" ? d = "text" : d == "dtstart" ? (d = "start_date", e = this.parse_date(e, 0, 0)) : d == "dtend" && (d = "end_date", e = b.start_date && b.start_date.getHours() == 0 ? this.parse_date(e, 24, 0) : this.parse_date(e, 23, 59));
            b[d] = e
        }
    },
    parse_date: function (a, b, c) {
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
scheduler.formSection = function (a) {
    for (var b = this.config.lightbox.sections, c = 0; c < b.length; c++) if (b[c].name == a) break;
    var d = b[c],
        e = document.getElementById(d.id).nextSibling;
    return {
        getValue: function (a) {
            return scheduler.form_blocks[d.type].get_value(e, a || {}, d)
        },
        setValue: function (a, b) {
            return scheduler.form_blocks[d.type].set_value(e, a, b || {}, d)
        }
    }
};
scheduler.form_blocks = {
    template: {
        render: function (a) {
            var b = (a.height || "30") + "px";
            return "<div class='dhx_cal_ltext dhx_cal_template' style='height:" + b + ";'></div>"
        },
        set_value: function (a, b) {
            a.innerHTML = b || ""
        },
        get_value: function (a) {
            return a.innerHTML || ""
        },
        focus: function () {}
    },
    textarea: {
        render: function (a) {
            var b = (a.height || "130") + "px";
            return "<div class='dhx_cal_ltext' style='height:" + b + ";'><textarea></textarea></div>"
        },
        set_value: function (a, b) {
            a.firstChild.value = b || ""
        },
        get_value: function (a) {
            return a.firstChild.value
        },
        focus: function (a) {
            var b = a.firstChild;
            b.select();
            b.focus()
        }
    },
    select: {
        render: function (a) {
            for (var b = (a.height || "23") + "px", c = "<div class='dhx_cal_ltext' style='height:" + b + ";'><select style='width:100%;'>", d = 0; d < a.options.length; d++) c += "<option value='" + a.options[d].key + "'>" + a.options[d].label + "</option>";
            c += "</select></div>";
            return c
        },
        set_value: function (a, b) {
            if (typeof b == "undefined") b = (a.firstChild.options[0] || {}).value;
            a.firstChild.value = b || ""
        },
        get_value: function (a) {
            return a.firstChild.value
        },
        focus: function (a) {
            var b = a.firstChild;
            b.select && b.select();
            b.focus()
        }
    },
    time: {
        render: function () {
            var a = scheduler.config,
                b = this.date.date_part(new Date),
                c = 1440,
                d = 0;
            scheduler.config.limit_time_select && (c = 60 * a.last_hour + 1, d = 60 * a.first_hour, b.setHours(a.first_hour));
            for (var e = "<select>", f = d, g = b.getDate(); f < c;) {
                var i = this.templates.time_picker(b);
                e += "<option value='" + f + "'>" + i + "</option>";
                b.setTime(b.valueOf() + this.config.time_step * 6E4);
                var h = b.getDate() != g ? 1 : 0,
                    f = h * 1440 + b.getHours() * 60 + b.getMinutes()
            }
            e += "</select> <select>";
            for (f = 1; f < 32; f++) e += "<option value='" + f + "'>" + f + "</option>";
            e += "</select> <select>";
            for (f = 0; f < 12; f++) e += "<option value='" + f + "'>" + this.locale.date.month_full[f] + "</option>";
            e += "</select> <select>";
            b = b.getFullYear() - 5;
            for (f = 0; f < 10; f++) e += "<option value='" + (b + f) + "'>" + (b + f) + "</option>";
            e += "</select> ";
            return "<div style='height:30px;padding-top:0px;font-size:inherit;' class='dhx_section_time'>" + e + "<span style='font-weight:normal; font-size:10pt;'> &nbsp;&ndash;&nbsp; </span>" + e + "</div>"
        },
        set_value: function (a, b, c) {
            function d(a, b, c) {
                a[b + 0].value = Math.round((c.getHours() * 60 + c.getMinutes()) / scheduler.config.time_step) * scheduler.config.time_step;
                a[b + 1].value = c.getDate();
                a[b + 2].value = c.getMonth();
                a[b + 3].value = c.getFullYear()
            }
            var e = a.getElementsByTagName("select");
            if (scheduler.config.full_day) {
                if (!a._full_day) {
                    var f = "<label class='dhx_fullday'><input type='checkbox' name='full_day' value='true'> " + scheduler.locale.labels.full_day + "&nbsp;</label></input>";
                    scheduler.config.wide_form || (f = a.previousSibling.innerHTML + f);
                    a.previousSibling.innerHTML = f;
                    a._full_day = !0
                }
                var g = a.previousSibling.getElementsByTagName("input")[0],
                    i = scheduler.date.time_part(c.start_date) === 0 && scheduler.date.time_part(c.end_date) === 0 && c.end_date.valueOf() - c.start_date.valueOf() < 1728E5;
                g.checked = i;
                for (var h in e) e[h].disabled = g.checked;
                g.onclick = function () {
                    if (g.checked) {
                        var a = new Date(c.start_date),
                            b = new Date(c.end_date);
                        scheduler.date.date_part(a);
                        b = scheduler.date.add(a, 1, "day")
                    }
                    for (var f in e) e[f].disabled = g.checked;
                    d(e, 0, a || c.start_date);
                    d(e, 4, b || c.end_date)
                }
            }
            if (scheduler.config.auto_end_date && scheduler.config.event_duration) for (var k = function () {
                    c.start_date = new Date(e[3].value, e[2].value, e[1].value, 0, e[0].value);
                    c.end_date.setTime(c.start_date.getTime() + scheduler.config.event_duration * 6E4);
                    d(e, 4, c.end_date)
                }, j = 0; j < 4; j++) e[j].onchange = k;
            d(e, 0, c.start_date);
            d(e, 4, c.end_date)
        },
        get_value: function (a, b) {
            s = a.getElementsByTagName("select");
            b.start_date = new Date(s[3].value, s[2].value, s[1].value, 0, s[0].value);
            b.end_date = new Date(s[7].value, s[6].value, s[5].value, 0, s[4].value);
            if (b.end_date <= b.start_date) b.end_date = scheduler.date.add(b.start_date, scheduler.config.time_step, "minute")
        },
        focus: function (a) {
            a.getElementsByTagName("select")[0].focus()
        }
    }
};
scheduler.showCover = function (a) {
    if (a) {
        a.style.display = "block";
        var b = window.pageYOffset || document.body.scrollTop || document.documentElement.scrollTop,
            c = window.pageXOffset || document.body.scrollLeft || document.documentElement.scrollLeft,
            d = window.innerHeight || document.documentElement.clientHeight;
        a.style.top = b ? Math.round(b + Math.max((d - a.offsetHeight) / 2, 0)) + "px" : Math.round(Math.max((d - a.offsetHeight) / 2, 0) + 9) + "px";
        a.style.left = document.documentElement.scrollWidth > document.body.offsetWidth ? Math.round(c + (document.body.offsetWidth - a.offsetWidth) / 2) + "px" : Math.round((document.body.offsetWidth - a.offsetWidth) / 2) + "px"
    }
    this.show_cover()
};
scheduler.showLightbox = function (a) {
    if (a && this.callEvent("onBeforeLightbox", [a])) {
        var b = this._get_lightbox();
        this.showCover(b);
        this._fill_lightbox(a, b);
        this.callEvent("onLightbox", [a])
    }
};
scheduler._fill_lightbox = function (a, b) {
    var c = this.getEvent(a),
        d = b.getElementsByTagName("span");
    scheduler.templates.lightbox_header ? (d[1].innerHTML = "", d[2].innerHTML = scheduler.templates.lightbox_header(c.start_date, c.end_date, c)) : (d[1].innerHTML = this.templates.event_header(c.start_date, c.end_date, c), d[2].innerHTML = (this.templates.event_bar_text(c.start_date, c.end_date, c) || "").substr(0, 70));
    for (var e = this.config.lightbox.sections, f = 0; f < e.length; f++) {
        var g = document.getElementById(e[f].id).nextSibling,
            i = this.form_blocks[e[f].type];
        i.set_value.call(this, g, c[e[f].map_to], c, e[f]);
        e[f].focus && i.focus.call(this, g)
    }
    scheduler._lightbox_id = a
};
scheduler._lightbox_out = function (a) {
    for (var b = this.config.lightbox.sections, c = 0; c < b.length; c++) {
        var d = document.getElementById(b[c].id),
            d = d ? d.nextSibling : d,
            e = this.form_blocks[b[c].type],
            f = e.get_value.call(this, d, a, b[c]);
        b[c].map_to != "auto" && (a[b[c].map_to] = f)
    }
    return a
};
scheduler._empty_lightbox = function () {
    var a = scheduler._lightbox_id,
        b = this.getEvent(a),
        c = this._get_lightbox();
    this._lightbox_out(b);
    b._timed = this.is_one_day_event(b);
    this.setEvent(b.id, b);
    this._edit_stop_event(b, !0);
    this.render_view_data()
};
scheduler.hide_lightbox = function () {
    this.hideCover(this._get_lightbox());
    this._lightbox_id = null;
    this.callEvent("onAfterLightbox", [])
};
scheduler.hideCover = function (a) {
    if (a) a.style.display = "none";
    this.hide_cover()
};
scheduler.hide_cover = function () {
    this._cover && this._cover.parentNode.removeChild(this._cover);
    this._cover = null
};
scheduler.show_cover = function () {
    this._cover = document.createElement("DIV");
    this._cover.className = "dhx_cal_cover";
    var a = document.height !== void 0 ? document.height : document.body.offsetHeight,
        b = document.documentElement ? document.documentElement.scrollHeight : 0;
    this._cover.style.height = Math.max(a, b) + "px";
    document.body.appendChild(this._cover)
};
scheduler.save_lightbox = function () {
    if (!this.checkEvent("onEventSave") || this.callEvent("onEventSave", [this._lightbox_id, this._lightbox_out({
        id: this._lightbox_id
    }), this._new_event])) this._empty_lightbox(), this.hide_lightbox()
};
scheduler.startLightbox = function (a, b) {
    this._lightbox_id = a;
    this.showCover(b)
};
scheduler.endLightbox = function (a, b) {
    this._edit_stop_event(scheduler.getEvent(this._lightbox_id), a);
    a && scheduler.render_view_data();
    this.hideCover(b)
};
scheduler.resetLightbox = function () {
    scheduler._lightbox && scheduler._lightbox.parentNode.removeChild(scheduler._lightbox);
    scheduler._lightbox = null
};
scheduler.cancel_lightbox = function () {
    this.callEvent("onEventCancel", [this._lightbox_id, this._new_event]);
    this.endLightbox(!1);
    this.hide_lightbox()
};
scheduler._init_lightbox_events = function () {
    this._get_lightbox().onclick = function (a) {
        var b = a ? a.target : event.srcElement;
        if (!b.className) b = b.previousSibling;
        if (b && b.className) switch (b.className) {
        case "dhx_save_btn":
            scheduler.save_lightbox();
            break;
        case "dhx_delete_btn":
            var c = scheduler.locale.labels.confirm_deleting;
            if (!c || confirm(c)) scheduler.deleteEvent(scheduler._lightbox_id), scheduler._new_event = null, scheduler.hide_lightbox();
            break;
        case "dhx_cancel_btn":
            scheduler.cancel_lightbox();
            break;
        default:
            if (b.getAttribute("dhx_button")) scheduler.callEvent("onLightboxButton", [b.className, b, a]);
            else if (b.className.indexOf("dhx_custom_button_") != -1) {
                var d = b.parentNode.getAttribute("index"),
                    e = scheduler.form_blocks[scheduler.config.lightbox.sections[d].type],
                    f = b.parentNode.parentNode;
                e.button_click(d, b, f, f.nextSibling)
            }
        }
    };
    this._get_lightbox().onkeydown = function (a) {
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
scheduler.setLightboxSize = function () {
    var a = this._lightbox;
    if (a) {
        var b = a.childNodes[1];
        b.style.height = "0px";
        b.style.height = b.scrollHeight + "px";
        a.style.height = b.scrollHeight + 50 + "px";
        b.style.height = b.scrollHeight + "px"
    }
};
scheduler._init_dnd_events = function () {
    dhtmlxEvent(document.body, "mousemove", scheduler._move_while_dnd);
    dhtmlxEvent(document.body, "mouseup", scheduler._finish_dnd);
    scheduler._init_dnd_events = function () {}
};
scheduler._move_while_dnd = function (a) {
    if (scheduler._dnd_start_lb) {
        if (!document.dhx_unselectable) document.body.className += " dhx_unselectable", document.dhx_unselectable = !0;
        var b = scheduler._get_lightbox(),
            c = a && a.target ? [a.pageX, a.pageY] : [event.clientX, event.clientY];
        b.style.top = scheduler._lb_start[1] + c[1] - scheduler._dnd_start_lb[1] + "px";
        b.style.left = scheduler._lb_start[0] + c[0] - scheduler._dnd_start_lb[0] + "px"
    }
};
scheduler._ready_to_dnd = function (a) {
    var b = scheduler._get_lightbox();
    scheduler._lb_start = [parseInt(b.style.left, 10), parseInt(b.style.top, 10)];
    scheduler._dnd_start_lb = a && a.target ? [a.pageX, a.pageY] : [event.clientX, event.clientY]
};
scheduler._finish_dnd = function () {
    if (scheduler._lb_start) scheduler._lb_start = scheduler._dnd_start_lb = !1, document.body.className = document.body.className.replace(" dhx_unselectable", ""), document.dhx_unselectable = !1
};
scheduler._get_lightbox = function () {
    if (!this._lightbox) {
        var a = document.createElement("DIV");
        a.className = "dhx_cal_light";
        scheduler.config.wide_form && (a.className += " dhx_cal_light_wide");
        scheduler.form_blocks.recurring && (a.className += " dhx_cal_light_rec");
        /msie|MSIE 6/.test(navigator.userAgent) && (a.className += " dhx_ie6");
        a.style.visibility = "hidden";
        var b = this._lightbox_template,
            c = this.config.buttons_left;
        scheduler.locale.labels.dhx_save_btn = scheduler.locale.labels.icon_save;
        scheduler.locale.labels.dhx_cancel_btn = scheduler.locale.labels.icon_cancel;
        scheduler.locale.labels.dhx_delete_btn = scheduler.locale.labels.icon_delete;
        for (var d = 0; d < c.length; d++) b += "<div class='dhx_btn_set'><div dhx_button='1' class='" + c[d] + "'></div><div>" + scheduler.locale.labels[c[d]] + "</div></div>";
        c = this.config.buttons_right;
        for (d = 0; d < c.length; d++) b += "<div class='dhx_btn_set' style='float:right;'><div dhx_button='1' class='" + c[d] + "'></div><div>" + scheduler.locale.labels[c[d]] + "</div></div>";
        b += "</div>";
        a.innerHTML = b;
        if (scheduler.config.drag_lightbox) a.firstChild.onmousedown = scheduler._ready_to_dnd, a.firstChild.onselectstart = function () {
            return !1
        }, a.firstChild.style.cursor = "pointer", scheduler._init_dnd_events();
        document.body.insertBefore(a, document.body.firstChild);
        this._lightbox = a;
        for (var e = this.config.lightbox.sections, b = "", d = 0; d < e.length; d++) {
            var f = this.form_blocks[e[d].type];
            if (f) {
                e[d].id = "area_" + this.uid();
                var g = "";
                if (e[d].button) var i = scheduler.config.wide_form ? "position:absolute; top:0px; right:0px; height:25px; margin-top:2px;" : "float:right",
                    g = "<div style='" + i + "' class='dhx_custom_button' index='" + d + "'><div class='dhx_custom_button_" + e[d].button + "'></div><div>" + this.locale.labels["button_" + e[d].button] + "</div></div>";
                this.config.wide_form && (b += "<div class='dhx_wrap_section'>");
                b += "<div id='" + e[d].id + "' class='dhx_cal_lsection'>" + g + this.locale.labels["section_" + e[d].name] + "</div>" + f.render.call(this, e[d]);
                b += "</div>"
            }
        }
        var h = a.getElementsByTagName("div");
        h[1].innerHTML = b;
        this.setLightboxSize();
        this._init_lightbox_events(this);
        a.style.display = "none";
        a.style.visibility = "visible"
    }
    return this._lightbox
};
scheduler._lightbox_template = "<div class='dhx_cal_ltitle'><span class='dhx_mark'>&nbsp;</span><span class='dhx_time'></span><span class='dhx_title'></span></div><div class='dhx_cal_larea'></div>";
scheduler._dp_init = function (a) {
    a._methods = ["setEventTextStyle", "", "changeEventId", "deleteEvent"];
    this.attachEvent("onEventAdded", function (b) {
        !this._loading && this.validId(b) && a.setUpdated(b, !0, "inserted")
    });
    this.attachEvent("onConfirmedBeforeEventDelete", function (b) {
        if (this.validId(b)) {
            var c = a.getState(b);
            if (c == "inserted" || this._new_event) return a.setUpdated(b, !1), !0;
            if (c == "deleted") return !1;
            if (c == "true_deleted") return !0;
            a.setUpdated(b, !0, "deleted");
            return !1
        }
    });
    this.attachEvent("onEventChanged", function (b) {
        !this._loading && this.validId(b) && a.setUpdated(b, !0, "updated")
    });
    a._getRowData = function (a) {
        var c = this.obj.getEvent(a),
            d = {},
            e;
        for (e in c) e.indexOf("_") != 0 && (d[e] = c[e] && c[e].getUTCFullYear ? this.obj.templates.xml_format(c[e]) : c[e]);
        return d
    };
    a._clearUpdateFlag = function () {};
    a.attachEvent("insertCallback", scheduler._update_callback);
    a.attachEvent("updateCallback", scheduler._update_callback);
    a.attachEvent("deleteCallback", function (a, c) {
        this.obj.setUserData(c, this.action_param, "true_deleted");
        this.obj.deleteEvent(c)
    })
};
scheduler.setUserData = function (a, b, c) {
    a ? this.getEvent(a)[b] = c : this._userdata[b] = c
};
scheduler.getUserData = function (a, b) {
    return a ? this.getEvent(a)[b] : this._userdata[b]
};
scheduler.setEventTextStyle = function (a, b) {
    this.for_rendered(a, function (a) {
        a.style.cssText += ";" + b
    });
    var c = this.getEvent(a);
    c._text_style = b;
    this.event_updated(c)
};
scheduler.validId = function () {
    return !0
};
scheduler._update_callback = function (a) {
    var b = scheduler.xmlNodeToJSON(a.firstChild);
    b.text = b.text || b._tagvalue;
    b.start_date = scheduler.templates.xml_date(b.start_date);
    b.end_date = scheduler.templates.xml_date(b.end_date);
    scheduler.addEvent(b)
};