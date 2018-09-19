/**
 * IITA Javascripts
 * Matija Obreza <m.obreza@iita.org>
 * September 2009
 */
if (!IITA) var IITA = {};

IITA.TableNav = Class.create();
IITA.TableNav.prototype = { 
	initialize: function(table) {
		Event.observe(table, "keypress", this.checkKey.bindAsEventListener(this));		
	},
	checkKey: function(ev) {
		if (ev.keyCode==Event.KEY_DOWN) {

		} else if (ev.keyCode==Event.KEY_UP) {

		} else if (ev.keyCode==Event.KEY_LEFT || ev.keyCode==Event.KEY_BACKSPACE) {
			var s=ev.element();
			var ss=$(s.form).getInputs();
			var i=ss.indexOf(s);
			if (s.selectionEnd==0 && i>0) ss[i-1].focus();
		} else if (ev.keyCode==Event.KEY_RIGHT || ev.keyCode==Event.KEY_RETURN) {
			var s=ev.element();
			var ss=$(s.form).getInputs();
			var i=ss.indexOf(s);
			if (ev.keyCode==Event.KEY_RETURN || (s.selectionEnd==s.value.length && i<ss.length-1)) ss[i+1].focus();
			if (ev.keyCode==Event.KEY_RETURN) ev.stop();
		}
	},
	doGetCaretPosition: function(ctrl) {
		var CaretPos = 0;
		// IE Support
		if (document.selection) {
			ctrl.focus ();
			var Sel = document.selection.createRange ();
			Sel.moveStart ('character', -ctrl.value.length);
			CaretPos = Sel.text.length;
		}
		// Firefox support
		else if (ctrl.selectionStart || ctrl.selectionStart == '0')
			CaretPos = ctrl.selectionStart;

		return (CaretPos);

	},
	setCaretPosition: function(ctrl, pos)
	{
		if(ctrl.setSelectionRange)
		{
			ctrl.focus();
			ctrl.setSelectionRange(pos,pos);
		}
		else if (ctrl.createTextRange) {
			var range = ctrl.createTextRange();
			range.collapse(true);
			range.moveEnd('character', pos);
			range.moveStart('character', pos);
			range.select();
		}
	}
}
