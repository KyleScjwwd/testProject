var messageSendType = {
	initEvent : function() {
		$("input[type='radio']").each(function(){
			$(this).unbind("click");
			$(this).bind("click", function(){
				messageSendType.controlTextarea($(this));
			});
		});
	},
	controlTextarea : function(radio) {
		var textArea = $("textarea[name='msgTemplate']");
		if (radio.val() == "1") {
			textArea.removeAttr("disabled");
		} else if (radio.val() == "0") {
			textArea.attr("disabled", "disabled");
		}
	},
	checkSave : function() {
		var radio = $("input[checked='checked']");
		if (radio.val() == "1") {
			//
		}
		$("#SaveMessageSendTypeForm").submit();
	}
};

$().ready(function() {
	messageSendType.initEvent();
	messageSendType.controlTextarea($("input[checked='checked']"));
	var hint = $("#hint").val();
	if (hint != "") {
		alert(hint);
	}
});