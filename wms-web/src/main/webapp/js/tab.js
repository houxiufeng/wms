var addTabs = function (options) {
  var url = window.location.protocol + '//' + window.location.host;
  options.url = url + options.url;
  id = "tab_" + options.id;
  jQuery(".active").removeClass("active");
  //如果TAB不存在，创建一个新的TAB
  if (!jQuery("#" + id)[0]) {
    //固定TAB中IFRAME高度
    mainHeight = document.documentElement.clientHeight;
    //创建新TAB的title
    title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + options.title;
    //是否允许关闭
    if (options.close) {
      title += ' <i class="icon-remove" tabclose="' + id + '"></i>';
    }
    title += '</a></li>';
    //是否指定TAB内容
    if (options.content) {
      content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
    } else {//没有内容，使用IFRAME打开链接
      content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="' + options.url + '" width="100%" height="' + mainHeight +
      '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="yes"></iframe></div>';
    }
    //加入TABS
    jQuery(".nav-tabs").append(title);
    jQuery(".tab-content").append(content);
  }
  //激活TAB
  jQuery("#tab_" + id).addClass('active');
  jQuery("#" + id).addClass("active");
};
var closeTab = function (id) {
  //如果关闭的是当前激活的TAB，激活他的前一个TAB
  if (jQuery("li.active").attr('id') == "tab_" + id) {
    jQuery("#tab_" + id).prev().addClass('active');
    jQuery("#" + id).prev().addClass('active');
  }
  //关闭TAB
  jQuery("#tab_" + id).remove();
  jQuery("#" + id).remove();
};


jQuery(function () {
 
  jQuery(".nav-tabs").on("click", "[tabclose]", function (e) {
    id = jQuery(this).attr("tabclose");
    closeTab(id);
  });
});