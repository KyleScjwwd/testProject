function CheckAdd() {
	var intensitygrade = $("input[name='intensityGrade']").val();
	var parameter = {
		/*bdCode : bdcode,
		 bhzCode: bhzcode,*/
		intensityGrade: intensitygrade
	};
	if (intensitygrade == "") {
		alert("强度等级 是必填项！");
	} else {
		$.post("intensityGradeAction!checkIntensityGrade_Add.action", parameter, function (data) {
			if (data.hint == "") {
				$("#AddIntensityGradeForm").submit();
			} else {
				alert(data.hint);
			}
		});
	}
}

function CheckUpdate() {
	var i = $("input[name='id']").val();

	var intensitygrade = $("input[name='intensityGrade']").val();

	var parameter = {
		id: i,
		intensityGrade: intensitygrade
	}
	if (intensitygrade == "") {
		alert("强度等级 是必填项！");
	} else {
		$.post("intensityGradeAction!checkIntensityGrade_Update.action", parameter, function (data) {
			if (data.hint == "") {
				$("#UpdateIntensityGradeForm").submit();
			} else {
				alert(data.hint);
			}
		});
	}
}
function CheckFind() {
	$("#FindIntensityGradeForm").submit();
}

/*var intensityGrade = {
	CheckAdd : function() {
		/!*var bdcode = $("select[name='bdCode']").val();
		 var bhzcode = $("select[name='bhzCode']").val();*!/
		var intensitygrade = $("input[name='intensityGrade']").val();

		var parameter = {
			/!*bdCode : bdcode,
			 bhzCode: bhzcode,*!/
			intensityGrade : intensitygrade
		};

		/!*if (bdcode == "null") {
		 alert("所属标段 是必填项！");
		 } else if (bhzcode == "null") {
		 alert("所属拌合站 是必填项！");
		 } else*!/ if(intensitygrade == "") {
			alert("强度等级 是必填项！");
		} else {
			$.post("intensityGradeAction!checkIntensityGrade_Add.action", parameter, function(data) {
				if (data.hint == "") {
					$("#AddIntensityGradeForm").submit();
				} else {
					alert(data.hint);
				}
			});
		}
	},
	CheckUpdate : function() {
		var i = $("input[name='id']").val();
		/!*var bdcode = $("select[name='bdCode']").val();
		 var bhzcode = $("select[name='bhzCode']").val();*!/
		var intensitygrade = $("input[name='intensityGrade']").val();

		var parameter = {
			id : i,
			/!*bdCode : bdcode,
			 bhzCode: bhzcode,*!/
			intensityGrade : intensitygrade
		};

		/!*if (bdcode == "null") {
		 alert("所属标段 是必填项！");
		 } else if (bhzcode == "null") {
		 alert("所属拌合站 是必填项！");
		 } else*!/ if(intensitygrade == "") {
			alert("强度等级 是必填项！");
		} else {
			$.post("intensityGradeAction!checkIntensityGrade_Update.action", parameter, function(data) {
				if (data.hint == "") {
					$("#UpdateIntensityGradeForm").submit();
				} else {
					alert(data.hint);
				}
			});
		}
	},
	CheckFind : function() {
		$("#FindIntensityGradeForm").submit();
	}

};*/

