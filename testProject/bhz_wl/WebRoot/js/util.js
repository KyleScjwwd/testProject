function openLoad() {
    var str = "";
    str += "<div id='seachdiv' style='display:none;width:200px;height:80px;background-color: gray;text-align:center;line-height:30px; '>";
    str += "<div style='margin-top:20px'><font size='2' color='#FFFFFF'>数据正在加载中...</font></div>";
    str += "</div>";
    $("body").after(str);
    var wnd = $(window), doc = $(document);
    var left = doc.scrollLeft();
    var top = doc.scrollTop();
    left += (wnd.width() - $("#seachdiv").width()) / 2;
    top += (wnd.height() - $("#seachdiv").height()) / 2;
    $("#seachdiv").css("position", "absolute");
    $("#seachdiv").css("top", top);
    $("#seachdiv").css("left", left);
    $("#seachdiv").show();
}
function closeLoad() {
    $("#seachdiv").hide();
}
function showPage(url, iWidth, iHeight, title) {
    dealWith(url, iWidth, iHeight, title);
}
function forwordURL() {
    openLoad();
    seleParam();
}
function seleParam() {
    $("#form1").submit();
}