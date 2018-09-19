/**
 * IITA Help Javascript library for inline help Matija Obreza, IITA
 * www.iita.org, 2010
 */

if (!IITA)
	var IITA = {};

IITA.Help = Class.create();

IITA.Help.prototype = {
	baseHref: null,
	
	initialize : function(baseHref) {
		this.baseHref=baseHref;
		this.closeListener = this.helpClick.bindAsEventListener(this);
		this.f1Listener=this.callHelp.bindAsEventListener(this);
		var self = this;

		// handle F1 and shift F1
		if (Prototype.Browser.IE)
			Event.observe(document, "help", this.f1Listener);
		if (Prototype.Browser.Gecko)
			Event.observe(document, "keypress",  function(ev) {
				if (ev.keyCode != 112) return false;
				self.callHelp(ev);
				return true;
			});
		if (Prototype.Browser.WebKit)
			Event.observe(document, "keydown",  function(ev) {
				if (ev.keyCode != 112) return false;
				self.callHelp(ev);
				return true;
			});
	},
	callHelp:function(ev) {
		Event.stop(ev);
		//alert("called help! " + ev);
		var helpFor = null;
		try {
			helpFor = this.findHelpSegment($(Event.element(ev)));
		} catch (e) {
		}
		if (ev.shiftKey || helpFor == null) {
			//alert("Showing help contents");
			this.helpFrame();
		} else {
			//alert("Inline help helptoc=" + helpFor);
			this.showInlineHelp($(Event.element(ev)), helpFor);
		}
		return true;
	},
	// Help requested on element, find iita:helptoc attribute if possible
	findHelpSegment : function(element) {
		if (!element.getAttribute)
			return null;
		// alert("Finding help segment for : " +element);
		var helpAttr = element.getAttribute("iita:helptoc");
		while (helpAttr == null && element != null) {
			element = element.parentNode;
			helpAttr = element.getAttribute("iita:helptoc");
		}
		return helpAttr;
	},
	// just show help window
	helpFrame : function() {
		if (this.baseHref!=null) {
			var helpAttr = document.body.getAttribute("iita:help");
			if (helpAttr!=null)
				window.location.href=this.baseHref + helpAttr + ".html";
			else
				window.location.href=this.baseHref + "index.html";
			return true;
		}
	},
	// show inline help
	showInlineHelp : function(element, helpFor) {
		this.hideInlineHelp();
		var content = this.fetchHelpFor(helpFor, element);
		if (content == null) {
			alert("No help found for topic: " + helpFor);
			return;
		}
		//alert("showing help at " + element + ":\n" + content);
		var helpDiv = $(document.createElement("DIV"));
		// Element.makePositioned(element);
		var offset = Element.cumulativeOffset(element);
		var originalLeft = parseFloat(element.getStyle('left') || '0')
				+ offset.left;
		var originalTop = parseFloat(element.getStyle('top') || '0')
				+ offset.top + element.getHeight() + 50;
		Insertion.After(element, helpDiv);
		this.registerInlineHelp(helpDiv);
		helpDiv.addClassName("inline-help");
		helpDiv.setStyle( {
			position : "absolute",
			width : "450px",
			height : "auto",
			display : "none"
		});
		helpDiv.innerHTML = "<div class='inline-help-contents help-contents'>" + content
				+ "</div>";
		// helpDiv.style.top=(originalTop + "px");
		helpDiv.style.left = (originalLeft + "px");
		helpDiv.show();
	},
	registerInlineHelp : function(helpDiv) {
		this.inlineHelp = helpDiv;
		Event.observe(document, "click", this.closeListener);
	},
	helpClick : function(ev) {
		var el = Event.element(ev);
		while (el != null) {
			if (el == this.inlineHelp)
				return false;
			el = el.parentNode;
		}
		this.hideInlineHelp();
	},
	hideInlineHelp : function() {
		if (this.inlineHelp != null) {
			Event.stopObserving(document, "click", this.closeListener);
			Element.remove(this.inlineHelp);
			this.inlineHelp = null;
		}
	},
	// fetch help from server
	fetchHelpFor : function(helpFor, element) {
		//alert("Fetching help for: " + helpFor);
		var results=/([^#]+)(#(.+))?$/.exec(helpFor);
		var url=results[1] + ".html";
		var section=results[3];
		var xx = document.createElement("DIV");
		var z = new Ajax.Request((this.baseHref ==null ? "" : this.baseHref) + url, {
			method : "GET",
			evalJSON : false,
			evalJS : false,
			asynchronous: false,
			onComplete : function(x) {
				xx.innerHTML = x.responseText;
			}
		});
		// pick the content's section
		var zz=Selector.findChildElements(xx, ['div#main'])[0];
		if (element && element.id!=null) section=element.id;
		if (section==null) section=results[3];
		var sectionData=$(zz).descendants().find(function(e) { if (e.id==section) return true; });
		if (sectionData!=null) return this.cleanHTML(sectionData.innerHTML, this.baseHref + results[1]);
		section=results[3];
		if (section!=null) sectionData=$(zz).descendants().find(function(e) { if (e.id==section) return true; })
		if (sectionData!=null) return this.cleanHTML(sectionData.innerHTML, this.baseHref + results[1]);
		return zz=null ? null : this.cleanHTML(zz.innerHTML, this.baseHref + results[1]);
	},
	cleanHTML: function(html, documentUrl) {
		if (html==null) return null;
		var baseHref=documentUrl.replace(/\/[^\/]+$/, "/");
		return html.replace(/(src|href)=("([^"]+)"|'([^']+)'|([^ ]+))/gi, "$1=\"" + baseHref + "$3\"  3=$3 4=$4 5=$5 6=$6");
	}
};
