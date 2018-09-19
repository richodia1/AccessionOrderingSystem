if (!IITA)
	var IITA = {};
IITA.FileUploader = Class.create();

IITA.FileUploader.prototype = {
	// number of pending files
	pending: 0,
	uploadedCount: 0,
	files: $A([]),
	postUrls: $A([]),
	
	initialize: function(statusElement, hostUrl) {
		if (statusElement.bar == null) {
			var bar = IITA.FileUploader.createBar(statusElement, this.cancelDownload.bind(this));
			statusElement.bar = $(bar);
		}
		this.progressBar=statusElement.bar.firstChild;
		this.statusBar=statusElement.bar.childNodes[2];

		this.log = function(logline) {
			var line=document.createElement('DIV');
			line.innerHTML=logline + "<br />";
			statusElement.bar.childNodes[1].appendChild(line);
		}

		var self=this;
		this.workerPool = google.gears.factory.create('beta.workerpool');
		this.workerPool.onmessage = function(a, b, message) {
			if (message.body.uploadComplete) {
				self.uploadComplete(message.body);
				if (!self.uploadNext())
					self.finishedTimeout=setTimeout(function() { window.location.href=window.location.href; }, 2000);
			} else if (message.body.errorMessage) {
				alert("Error in upload.js line " + message.body.errorMessage.lineNumber + ": " + message.body.errorMessage.message + "\nGears " + google.gears.factory.version);
			} else if (message.body.progress) {
				self.updateProgress(100 * message.body.progress.loaded / message.body.progress.total);
			} else if (message.body.startingUpload) {
				self.updateProgress(0, message.body.fileName);
			} else if (message.body.aborted) {
				self.log("Upload aborted.");
				self.uploadAborted();
			} else {
				self.log('Worker ' + message.sender + ': \n' + message.body);
			}
		};
		this.childWorkerId = this.workerPool.createWorkerFromUrl(hostUrl + '/script/gears/upload.js');
	},
	upload: function(files, postUrl) {
		if (self.finishedTimeout) clearTimeout(self.finishedTimeout);
		//this.log("Uploading " + files.length + " files");
		//this.log("Uploading to " + postUrl);
		if (!postUrl.startsWith('http://'))
			postUrl = window.location.protocol + "//" + window.location.host
					+ postUrl;
		for (var i=0; i<files.length; i++) {
			this.pending++;
			this.files[this.files.length]=files[i];
			this.postUrls[this.postUrls.length]=postUrl;
		}
		if (this.pending==files.length) this.uploadNext();
	},
	uploadNext: function() {
		if (this.files.length==0) {
			this.progressBar.innerHTML="Done. Will refresh in 2s.";
			return false;
		} else {
			var nextFile=this.files.splice(0, 1)[0];
			var nextFileUrl=this.postUrls.splice(0, 1)[0];
			this.workerPool.sendMessage( { file: nextFile, postUrl: nextFileUrl }, this.childWorkerId);
			this.log("Queue size: " + this.files.length);
			return true;
		}
	},
	uploadComplete: function() {
		this.pending--;
		this.uploadedCount++;
		this.progressBar.innerHTML="Done.";
		this.progressBar.addClassName("upload-complete");
		this.refreshStatus();
	},
	uploadAborted: function() {
		this.progressBar.removeClassName("upload-complete");
		this.progressBar.addClassName("upload-aborted");
		this.refreshStatus();
	},
	updateProgress: function(percentage, filename) {
		this.progressBar.removeClassName("upload-complete");
		this.progressBar.removeClassName("upload-aborted");
		var style = { width : "" + percentage + "%"	};
		this.progressBar.setStyle(style);
		if (filename) this.progressBar.innerHTML=filename;
		this.refreshStatus();
	},
	cancelDownload: function() {
		this.log("Aborting!");
		this.workerPool.sendMessage( { abort: true }, this.childWorkerId);
		this.pending = 0;
		this.uploadedCount = 0;
		this.files = $A([]);
		this.postUrls = $A([]);
	},
	refreshStatus: function() {
		if (this.files.length==0)
			this.statusBar.innerHTML=null;
		else {
			this.statusBar.innerHTML="";
//			this.statusBar.innerHTML="Queue: " + this.files.length + "<br />";
			for (var i=0; i<this.files.length; i++)
				this.statusBar.innerHTML+=(i>0 ? ', ' : '') + this.files[i].name;
		}
	}
};

IITA.FileUploader.upload = function(statusElement, files, postUrl, hostUrl) {
	if (files == null || files.length == 0)
		return true;
	if (!statusElement.gearsUploader) statusElement.gearsUploader = new IITA.FileUploader(statusElement, hostUrl);
	statusElement.gearsUploader.upload(files, postUrl);
};
IITA.FileUploader.createBar = function(statusElement, cancelDownloadFunction) {
	var div = $(document.createElement('DIV'));
	div.addClassName("upload-bar");
	var e = $(document.createElement('DIV'));
	e.addClassName("upload-progress");
	e.setStyle("width: 0px;");
	div.appendChild(e);
	e = $(document.createElement('DIV'));
	e.addClassName("upload-log");
	e.innerHTML = "x";
	div.appendChild(e);
	e = $(document.createElement('DIV'));
	e.addClassName("upload-queue-status");
	e.innerHTML = "";
	div.appendChild(e);
	e = $(document.createElement('DIV'));
	e.addClassName("upload-cancel");
	e.innerHTML="<input type='button' value='Cancel upload' />";
	div.appendChild(e);
	
	statusElement.appendChild(div);	
	// register event handlers	 
	Event.observe(e, 'click', cancelDownloadFunction);
	return div;
};
IITA.FileUploader.uploadFiles = function(statusElement, postUrl, rootUrl) {
	if (statusElement==null) throw new Error("Status element is null!");
	return function uploadFiles(event) {
		if (!IITA.hasGears()) { Event.stop(event); return false; }
		var desktop = google.gears.factory.create('beta.desktop');
		desktop.openFiles(function (files) {
			for (var i=0; i<files.length; i++)
				files[i].metaData=desktop.extractMetaData(files[i].blob);
	  		IITA.FileUploader.upload(statusElement, files, postUrl, rootUrl);
		}, { singleFile: false, filter: [] });
		return false;
	}
};
IITA.FileUploader.registerDocumentDrop = function(target, invitationElement, statusElement, postUrl, rootUrl) {
	if (invitationElement==null) throw new Error("Invitation element is null!");
	if (statusElement==null) throw new Error("Status element is null!");
	if (!target.iitaFileDrop) {
		target.iitaFileDrop=true;
		if (window.google && google.gears && !Prototype.Browser.IE) { 
			var desktop=google.gears.factory.create('beta.desktop');
			Event.observe(target, 'dragenter', function(event) { desktop.setDropEffect(event, 'copy'); })
			Event.observe(target, 'dragover', function(event) { desktop.setDropEffect(event, 'copy'); })
			Event.observe(target, 'dragdrop', function(event) { IITA.FileUploader.uploadFilesByDrag(event, statusElement, postUrl, rootUrl); });
		} else {
			// No drag and drop
			Event.observe(target, 'dragdrop', function(event) { window.alert("You need to have Gears http://gears.google.com installed for drag-and-drop to work!"); Event.stop(event); });
			// change invitation element!
			if (Prototype.Browser.IE) {
				invitationElement.innerHTML="You need to have <a target='_blank' href='http://gears.google.com'>Gears</a> installed for drag-and-drop file uploads to work! Does <b>NOT</b> seem to work with IE. Use <a href='http://getfirefox.com'>Firefox</a>.";
			} else {
				invitationElement.innerHTML="You need to have <a target='_blank' href='http://gears.google.com'>Gears</a> installed for drag-and-drop file uploads to work!";
			}
		}
		Element.setStyle(invitationElement, { display: 'block' });
	} else {
		Element.hide(invitationElement);
	}
};
IITA.FileUploader.uploadFilesByDrag = function(event, statusElement, postUrl, rootUrl) {
	if (!IITA.hasGears()) { alert('no gears'); Event.stop(event); return false; }
	var desktop = google.gears.factory.create('beta.desktop');
	var data = desktop.getDragData(event, 'application/x-gears-files');
	var files = data && data.files;
	if (files) {
		for (var i=0; i<files.length; i++)
			files[i].metaData=desktop.extractMetaData(files[i].blob);

		IITA.FileUploader.upload(statusElement, files, postUrl, rootUrl);
	} else {
		alert('No files available in drag information.'); 
	}
	Event.stop(event);
	return false;
};
IITA.hasGears = function() {
	if (!window.google || !google.gears) { 
		if (window.confirm("This function requires Gears plug-in.\nWould you like to install Gears?")) 
			window.location.href='http://gears.google.com'; 
		return false; 
	} else
		return true;
}

Event.observe(window, 'load', function() {
	// gears-installed
	var matching = $$(".gears-only");
	if (window.google && google.gears) {
		if (matching!=null && matching.length>0) 
			for (var i=0; i<matching.length; i++) {
				Element.setStyle($(matching[i]), { display: 'block' });
			}
	} else {
		if (matching!=null && matching.length>0) 
			for (var i=0; i<matching.length; i++) {
				Element.hide($(matching[i]));
			}
	}
	// no gears
	var matching = $$(".gears-missing");
	if (window.google && google.gears) {
		if (matching!=null && matching.length>0) 
			for (var i=0; i<matching.length; i++) {
				Element.hide($(matching[i]));
			}
	} else {
		if (matching!=null && matching.length>0) 
			for (var i=0; i<matching.length; i++) {
				Element.setStyle($(matching[i]), { display: 'block' });
			}
	}

	// find drop zones
	var matching = $$(".file-drop-zone");
	if (window.google && google.gears && matching!=null && matching.length>0) 
		for (var i=0; i<matching.length; i++) {
			IITA.FileUploader.registerDocumentDrop($(matching[i]), $(matching[i]), $(matching[i].getAttribute("iita:upload-log")), matching[i].getAttribute("iita:destination"), matching[i].getAttribute("iita:root"));
		}
	// find gears browse buttons
	matching = $$(".file-gears-browse");
	if (matching!=null && matching.length>0) 
		for (var i=0; i<matching.length; i++) {
			if (!window.google || !google.gears || Prototype.Browser.IE) { 
				Element.hide($(matching[i]));
			} else {
				Event.observe($(matching[i]), "click", IITA.FileUploader.uploadFiles(
							$($(matching[i]).getAttribute("iita:upload-log")), 
							$(matching[i]).getAttribute("iita:destination"), 
							$(matching[i]).getAttribute("iita:root")));
				Element.setStyle($(matching[i]), { display: 'inline' });
			}
		}
});

IITA.Collapse = Class.create();

IITA.Collapse.collapse = function(element) {
	var a=$(element).ancestors();
	for (var i=0; i<a.length; i++) {
		if (Element.hasClassName(a[i], "collapse")) {
			Element.addClassName(a[i], "collapsed"); return true;
		}
	}
}

IITA.Collapse.autofocus = function(element) {
	var x=element.getElementsBySelector('.autofocus');
	if (x!=null && x.length>0) 
		x[x.length-1].focus();
	else
		try { Form.focusFirstElement(element.getElementsByTagName('form')[0]); } catch (e) { } 	
}

IITA.Collapse.show = function(element) {
	var a=$(element).ancestors();
	for (var i=0; i<a.length; i++) {
		if (Element.hasClassName(a[i], "collapse")) {
			Element.removeClassName(a[i], "collapsed"); 
			// focus first field
			IITA.Collapse.autofocus($(a[1]));
			return true;
		}
	}
};


IITA.InlineEditor = Class.create();


IITA.InlineEditor.prototype = {
		boundbox: null,
		editor: null,
		target: null,
		
		initialize: function(editor) {
			this.editor=$(editor);
			this.target=this.configure();
		},
		configure: function() {
			var targetId=this.editor.getAttribute("iita:targetId");
			var target=$(targetId);
			if (target==null) {
				alert("Could not find target: " + targetId);
				return null;
			}
			// configure target
			var parent=target.parentNode;
			this.boundbox=$(document.createElement("DIV"));
			this.boundbox.addClassName("inlineeditor-boundingbox");
			parent.appendChild(this.boundbox);
			
//			var ch=$A(target.childNodes);
//			for (var i=0; i<ch.length; i++) {
//				var che=$(ch[i]);
//				if (che.nodeType == 1 && che.hasClassName("inlineeditor-content"))
//					break;
//				this.boundbox.appendChild(ch[i]);
//			}
			parent.replaceChild(this.boundbox, target);
			this.boundbox.appendChild(target);
			
			// add icons
			var icon=$(document.createElement("DIV"));
			icon.addClassName("icon");
			Insertion.Top(this.boundbox, icon);
			Event.observe(icon, "click", this.openEditor.bindAsEventListener(this));
			
			icon=$(document.createElement("DIV"));
			icon.addClassName("icon");
			Insertion.Top(this.editor, icon);
			Event.observe(icon, "click", this.closeEditor.bindAsEventListener(this));
			
			Event.observe(this.boundbox, "dblclick", this.openEditor.bindAsEventListener(this));
			
			return target;
		},
		// show editor
		openEditor: function(event) {
			Event.stop(event);
			this.boundbox.hide();
			this.editor.removeClassName("collapsed");
		},
		closeEditor: function(event) {
			Event.stop(event);
			this.editor.addClassName("collapsed");
			this.boundbox.show();
		}
};

Event.observe(window, 'load', function() {	
	var matching = $$(".inlineeditor");
	for (var i=0; i<matching.length; i++)
		new IITA.InlineEditor($(matching[i]));
});


IITA.AjaxRPC = Class.create();
IITA.AjaxRPC.prototype = {
	initialize: function(url) {
		var self=this;
		var ajaxRequest=new Ajax.Request(url, {
			method: 'get',
			//contentType: 'application/json',
			postBody: Object.toJSON({}),
			onSuccess: this._build.bind(this) 
		});
	},
	_build: function(response) {
//		alert("Build " + Object.toJSON(response.responseJSON));
		var result=response.responseJSON;
		this._serviceUrl=result.serviceUrl;
		this._serviceType=result.serviceType;
		for (var i=0; i<result.methods.length; i++) {
			this._addMethod(result.methods[i].name, result.methods[i].parameters);
		}
	},
	_addMethod: function(name, params) {
		this[name]=this._invoke(name, params).bind(this);
		this[name].comment="Call with " + Object.toJSON(params);
	},
	_invoke: function(name, params) {
		var paramCount=params.length;
		var self=this;
		var f=function() { 
			var args=[]; for (var i=0; i<arguments.length; i++) { 
				args[i]=arguments[i];
			}
//			alert(Object.toJSON(args));
			if (args.length!=paramCount+1) throw new Error("Wrong number of arguments, expecing " + (paramCount+1));
			var invocation=Object.toJSON({id: "IITA", method: name, params: args.splice(0, paramCount) });
//			alert(invocation);
			var ajaxRequest=new Ajax.Request(self._serviceUrl, {
				method: 'post',
				contentType: 'application/json-rpc',
				postBody: invocation,
				onSuccess: arguments[paramCount]
			});
		};
		return f;
	}
};

IITA.SizableTextarea = Class.create();

IITA.SizableTextarea.prototype = {
	initialize: function(textarea) {
		this.textarea=textarea;
		this.textarea.setStyle({ overflow: 'hidden', height: "60px" });
		Event.observe(textarea, 'keyup', this.fixHeight.bind(this));
		this.fixHeight();
	},
	fixHeight: function() {
		if ((this.textarea.scrollHeight + "px") == this.textarea.getStyle('height')) return;		
		var height=parseInt(this.textarea.getStyle('height'));
		if (this.textarea.scrollHeight > height) {
			if (this.textarea.scrollHeight>600)
				this.textarea.setStyle({ overflow: 'visible', height : "600px" });
			else
				this.textarea.setStyle({ overflow: 'hidden', height : "" + this.textarea.scrollHeight + "px" });			
		} else {
			this.textarea.setStyle({ overflow: 'hidden', height : "60px" });
			this.textarea.scrollTop=0;
			if (this.textarea.scrollHeight>600)
				this.textarea.setStyle({ overflow: 'visible', height : "600px" });
			else if (this.textarea.scrollHeight > 60)
				this.textarea.setStyle({ overflow: 'hidden', height : "" + this.textarea.scrollHeight + "px" });
		}
	}
};

Event.observe(window, 'load', function() {	
	var matching = $$(".sizable-textarea");
	for (var i=0; i<matching.length; i++)
		new IITA.SizableTextarea($(matching[i]));
});

Event.observe(window, 'load', function() {	
	var matching = $$("#notificationMessages");
	if (matching && matching.size()>0) {
		setTimeout( function() { new Effect.Fade(matching[0], {duration: 1}); }, 3000);
	}
});



//Ajax.Responders.register({
//	onCreate:   function() { $('ajax-indicator').show(); },
//	onComplete: function() { $('ajax-indicator').hide(); }
//});

Event.observe(window, 'load', function() {
	var docOff=document.viewport.getScrollOffsets();
	if (docOff.top!=0) return;
	var x=$$('.autofocus');
	if (x!=null && x.length>0) 
		x[x.length-1].focus();
	else
		try { 
			var firstForm=$('main').getElementsByTagName('form')[0];
			if (firstForm==null) return;
			var offset=Element.cumulativeOffset(Form.findFirstElement(firstForm));
			if (offset.top<docOff.top+document.viewport.getHeight())
				Form.focusFirstElement(firstForm); 
		} catch (e) { } 
}); 

/**
 * From Epoch
 */
Date.prototype.dateFormat = function(format) {
	if (!format) { // the default date format to use - can be customized to the current locale
		format = 'm/d/Y';
	}
	LZ = function(x) {
		return (x < 0 || x > 9 ? '' : '0') + x
	};
	var MONTH_NAMES = new Array('January', 'February', 'March', 'April', 'May',
			'June', 'July', 'August', 'September', 'October', 'November',
			'December', 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
			'Sep', 'Oct', 'Nov', 'Dec');
	var DAY_NAMES = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday',
			'Thursday', 'Friday', 'Saturday', 'Sun', 'Mon', 'Tue', 'Wed',
			'Thu', 'Fri', 'Sat');
	var result = "";
	var i_format = 0;
	var c = "";
	var token = "";
	var y = this.getFullYear().toString();
	var M = this.getMonth() + 1;
	var d = this.getDate();
	var E = this.getDay();
	var H = this.getHours();
	var m = this.getMinutes();
	var s = this.getSeconds();
	value = {
		Y : y.toString(),
		yyyy : y.toString(),
		y : y.substring(2),
		yy : y.substring(2),
		yyy : y.substring(1, 3),
		M : M,
		MM : LZ(M),
		F : MONTH_NAMES[M - 1],
		MMM : MONTH_NAMES[M + 11],
		d : d,
		dd : LZ(d),
		DDD : DAY_NAMES[E + 7],
		l : DAY_NAMES[E],
		H : H,
		HH : LZ(H),
		m : m,
		mm : LZ(m)
	};
	if (H == 0) {
		value['g'] = 12;
	} else if (H > 12) {
		value['g'] = H - 12;
	} else {
		value['g'] = H;
	}
	value['h'] = LZ(value['g']);
	if (H > 11) {
		value['a'] = 'pm';
		value['A'] = 'PM';
	} else {
		value['a'] = 'am';
		value['A'] = 'AM';
	}
	value['i'] = LZ(m);
	value['s'] = LZ(s);
	// construct the result string
	while (i_format < format.length) {
		c = format.charAt(i_format++);
		var token = "" + c, token2 = null;
		while (value[token] != null && (i_format < format.length)) {
			token2 = token;
			token += format.charAt(i_format++);
		}
		if (value[token] == null && token2 != null) {
			token = token2;
			i_format--;
		}
		if (value[token] != null) {
			result = result + value[token];
		} else {
			result = result + token;
		}
	}
	return result;
};

Date.parseDate = function(text, format) {
	if (!format || format.length==0)
		format="d/M/yyyy";
	
	if (text==null || text.length==0)
		return null;

	var array = new Array( "ddd", "dd", "d", "MMMM", "MM", "M", "yyyy", "yy", "HH", "H", "hh", "h", "mm", "m", "ss", "s", "tt" );

	var posDate = 0;
	var posPatt = 0;

	var currentDate=new Date();

	var day=currentDate.getDate();
	var month=currentDate.getMonth()+1;
	var year=currentDate.getFullYear();
	var hours=0;
	var minutes=0;
	var seconds=0;

	while(posPatt<format.length)
	{
		var found=false;
		for(var i=0; i<array.length; i++)
		{
			var type = array[i];

			if(format.substr(posPatt,type.length) == type)
			{
				//month: full string
				if(type=="ddd")
				{
					//alert("ddd");
					var mfound=false;
					for(var j=0; j<Date.dayNames.length; j++)
					{
						var dayName=Date.dayNames[j].toLowerCase();
						if(dayName==text.substr(posDate, dayName.length))
						{
							//day=j;
							posDate+=dayName.length;
							mfound=true;
							found=true;
							break;
						}
					}
					if(!mfound)
						throw ("Month is invalid!");
				}

				//day : double digit
				else if(type=="dd")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						day=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else
						throw ("Date is invalid!");
				}
				//day: single/double digit
				else if(type=="d")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						day=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else if(/^[\d]$/.test(text.substr(posDate,1)))
					{
						day=parseInt(text.substr(posDate,1), 10);
						posDate++;
					}
					else
						throw ("Date is invalid!");
				}


				//month: full string
				else if(type=="MMMM")
				{

					var mfound=false;
					for(var j=0; j<text.monthNames.length; j++)
					{
						var monthName=text.monthNames[j].toLowerCase();
						if(monthName==text.substr(posDate, monthName.length).toLowerCase())
						{
							month=j+1;
							posDate+=monthName.length;
							mfound=true;
							found=true;
							break;
						}
					}
					if(!mfound)
						throw ("Month is invalid!");
				}
				//month: double digit
				else if(type=="MM")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						month=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else
						throw ("Month is invalid!");
				}
				//month: single/double digit
				else if(type=="M")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						month=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else if(/^[\d]$/.test(text.substr(posDate,1)))
					{
						month=parseInt(text.substr(posDate,1), 10);
						posDate++;
					}
					else
						throw ("Month is invalid!");
				}


				//year four digit
				else if(type=="yyyy")
				{
					if(/^[\d]{4,4}$/.test(text.substr(posDate,4)))
					{
						year=parseInt(text.substr(posDate,4), 10);
						posDate+=4;
					}
					else
						throw ("Year is invalid!");
				}
				//year two digit
				else if(type=="yy")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						year=parseInt(year.toString().substr(0,2) + text.substr(posDate,2), 10);
						posDate+=2;
					}
					else
						throw ("Year is invalid!");
				}


				//hours two digit
				else if(type=="HH")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						hours=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else
						throw ("Hour is invalid!");
				}
				//hours one/two digit
				else if(type=="H")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						hours=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else if(/^[\d]$/.test(text.substr(posDate,1)))
					{
						hours=parseInt(text.substr(posDate,1), 10);
						posDate++;
					}
					else
						throw ("Hour is invalid!");
				}

				//hours two digit
				else if(type=="hh")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						hours=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else
						throw ("Hour is invalid!");
				}
				//hours one/two digit
				else if(type=="h")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						hours=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else if(/^[\d]$/.test(text.substr(posDate,1)))
					{
						hours=parseInt(text.substr(posDate,1), 10);
						posDate++;
					}
					else
						throw ("Hour is invalid!");
				}


				//minutes two digit
				else if(type=="mm")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						minutes=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else
						throw ("Minutes is invalid!");
				}
				//minutes one/two digit
				else if(type=="m")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						minutes=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else if(/^[\d]$/.test(text.substr(posDate,1)))
					{
						minutes=parseInt(text.substr(posDate,1), 10);
						posDate++;
					}
					else
						throw ("Minutes is invalid!");
				}



				//seconds one/two digit
				else if(type=="ss")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						seconds=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else
						throw ("Seconds is invalid!");
				}
				//seconds one digit
				else if(type=="s")
				{
					if(/^[\d]{2,2}$/.test(text.substr(posDate,2)))
					{
						seconds=parseInt(text.substr(posDate,2), 10);
						posDate+=2;
					}
					else if(/^[\d]$/.test(text.substr(posDate,1)))
					{
						seconds=parseInt(text.substr(posDate,1), 10);
						posDate++;
					}
					else
						throw ("Seconds is invalid!");
				}
				else if(type=="tt")
				{
					if(/^am$/i.test(text.substr(posDate,2)))
					{
					}
					else if(/^pm$/i.test(text.substr(posDate,2)))
					{
						hours+=12;
					}
					else
						throw ("Hour is invalid!");

					posDate+=2;
				}


				posPatt += type.length;
				found=true;
				break;
			}
		}
		if(!found)
		{
			if(format.substr(posPatt,1)!=text.substr(posDate,1))
				throw ("Wrong chars: " + (text.substr(posDate,1)) + " at position " + posDate + " in string: '" + (date) + "'");

			posPatt++;
			posDate++;
		}
	}

	if(year<1970 || year>9999)
		throw ("Year is invalid!");
	if(month<1 || month>12)
		throw ("Month is invalid!");

	var maxday=31;
	if(month==4 || month==6 || month==9 || month==11)
		maxday=30;
	else if(month==2 && year%4==0)
		maxday=29;
	else if(month==2)
		maxday=28;

	if(day<1 || day>maxday)
		throw ("Day is invalid!");

	if(hours<0 || hours>23)
		throw ("Hours is invalid!");

	if(minutes<0 || minutes>59)
		throw ("Minutes is invalid!");

	if(seconds<0 || seconds>59)
		throw ("Seconds is invalid!");

	return new Date(year, month-1, day, hours, minutes, seconds);
};

IITA.SessionTimeout = Class.create();
IITA.SessionTimeout.prototype = {
	initialize: function(div, timeout, alertTime, url) {
		this.timeout=timeout;
		this.alertTime=alertTime;
		this.url=window.location.protocol + "//" + window.location.host + url;
		this.div=div;
		this.clock=this.div.getElementsByTagName("span")[0];		
		this.resetup();
	},
	resetup: function() {
		Element.removeClassName(this.div, "sessionAlertWarning");
		Element.removeClassName(this.div, "sessionAlertExpired");
		if (this.expiredTO!=null) clearTimeout(this.expiredTO);
		if (this.secondTO!=null) clearInterval(this.secondTO);
		setTimeout( this.trigger.bind(this), (this.timeout-this.alertTime-5) * 1000);
		this.expiredTO=setTimeout(this.expired.bind(this), (this.timeout-5) * 1000);
	},
	trigger: function(div) {
		this.remaining=this.alertTime;
		this.second();
		Element.removeClassName(this.div, "sessionAlertExpired");
		Element.addClassName(this.div, "sessionAlertWarning");
		this.secondTO=setInterval(this.second.bind(this), 1000);
		setTimeout(this.refresh.bind(this), 1000);
	},
	expired: function() {
		Element.addClassName(this.div, "sessionAlertExpired");
		Element.removeClassName(this.div, "sessionAlertWarning");
		if (this.expiredTO) clearTimeout(this.expiredTO);
		if (this.secondTO) clearInterval(this.secondTO);
		this.expiredTO=this.secondTO=null;
	},
	refresh: function() {
		// get with timeout of 5 seconds... need to trigger this at least 5 seconds before to be sure it worked!
		var options = Object.extend({ method: 'get' }, []);
		Object.extend(options, {
			evalJS: false,
			method: 'get',
			onComplete: Prototype.emptyFunction,
			onSuccess: this.resetup.bind(this),
		  	onFailure: this.expired.bind(this)
		});
		new Ajax.Request(this.url, options);
	},
	second: function() {
		var mins=Math.floor(this.remaining/60), secs=this.remaining%60;
		this.clock.innerHTML="" + (mins<10 ? "0" + mins : mins) + ":" + (secs<10 ? "0" + secs : secs);
		this.remaining--;
	}
};

//register event listeners on the Ajax requests to show/hide the processing indicator
Ajax.Responders.register({
   onCreate : function() {
		if (Ajax.activeRequestCount === 1) {
			$('ajax-indicator').show();
		}
	},
	onComplete : function() {
		if (Ajax.activeRequestCount === 0) {
			$('ajax-indicator').hide();
		}
	}
});