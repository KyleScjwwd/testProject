var authoriz = {
	initEvent : function() {
		$("a[extend]").each(function(){
			$(this).unbind("click");
			$(this).bind("click", function(){
				authoriz.showOrHide($(this));
			});
		});
		
		$("input:checkbox").each(function(){
			$(this).unbind("click");
			$(this).bind("click", function(){
				authoriz.disableOrEnable($(this));
			});			
		});
		
		$("input[control]").each(function(){
			$(this).unbind("click");
			$(this).bind("click", function(){
				authoriz.disableOrEnable2($(this));
			});			
		});
	},
	showOrHide : function(btn) {
		var id = btn.attr("id");
		id = id.substr(2, id.length - 2);
		
		if ($("#tr_" + id).css("display") != "none") {
			$("#tr_" + id).hide();
			btn.html("+");
		} else {
			$("#tr_" + id).show();
			btn.html("-");
		}
	},
	disableOrEnable : function(btn) {
		var id = btn.attr("id");
		id = id.substr(3, id.length - 3);
		
		$("#tr_" + id + " input:radio").each(function(){
			if (btn.is(':checked')) {
				$(this).removeAttr("disabled");
			} else {
				$(this).removeAttr("checked");
				$(this).attr("disabled", "disabled");
			}
			
		});
	},
	disableOrEnable2 : function(btn) {
		var id = btn.attr("id");
		id = id.substr(6, id.length - 6);
		
		$("#tr_" + id + " input:radio").each(function() {
			$(this).removeAttr("disabled");
		});
		
		$("#tr_" + id + " input[value='" + btn.val() + "']").each(function(){
			$(this).attr("checked", "checked");
		});
	},
	checkSave : function() {
		$("#saveAuthorization").submit();
	}
};

$().ready(function() {
	authoriz.initEvent();
});
