var common = {
	myconfirm : function() {
		return confirm("您确认要删除吗？");
	},
	initEvent : function() {
		$("a[del]").each(function(){
			$(this).unbind("click");
			$(this).bind("click", function(){
				return common.myconfirm();
			});
		});
	}
};

$().ready(function() {
	common.initEvent();
});
