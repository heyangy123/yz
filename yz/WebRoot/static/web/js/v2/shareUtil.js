var shareFun = {};

//分享到腾讯微博  
shareFun.sharetoqqwebo=function(content,url,picurl)  
{  
 if(!picurl&&SHAREMSG.sharePicUrl){
	 picurl=SHAREMSG.sharePicUrl;
 }
 var shareqqstring='http://v.t.qq.com/share/share.php?title='+content+'&url='+url+'&pic='+picurl;  
 window.open(shareqqstring,'_blank');  
}  
//分享到新浪微博  
shareFun.sharetosina=function(title,url,picurl)  
{  
if(!picurl&&SHAREMSG.sharePicUrl){
	 picurl=SHAREMSG.sharePicUrl;
 }
 var sharesinastring='http://v.t.sina.com.cn/share/share.php?title='+title+'&url='+url+'&content=utf-8&sourceUrl='+url+'&pic='+picurl;  
 window.open(sharesinastring,'_blank');  
}  
//分享到QQ空间  
shareFun.sharetoqqzone=function(title,url,picurl)  
{
if(!picurl&&SHAREMSG.sharePicUrl){
	 picurl=SHAREMSG.sharePicUrl;
 }
 var shareqqzonestring='http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?summary='+title+'&url='+url+'&pics='+picurl;  
 window.open(shareqqzonestring,'_blank');  
}
//分享给qq好友
shareFun.sharetoqq=function(url,desc,title,summary,pics)  
{  
	if(!pics&&SHAREMSG.sharePicUrl){
		pics=SHAREMSG.sharePicUrl;
	 }
	 var p = {
    url:url, /*获取URL，可加上来自分享到QQ标识，方便统计*/
    desc:desc||'', 
    title:title, /*分享标题(可选)*/
    summary:summary||'', /*分享摘要(可选)*/
    pics:pics||SHAREMSG.sharePicUrl, /*分享图片(可选)*/
    site:SHAREMSG.site, /*分享来源(可选) 如：QQ分享*/
    style:'203',
    width:16,
    height:16
    };
    var s = [];
    for(var i in p){
        s.push(i + '=' + encodeURIComponent(p[i]||''));
    }
	var qhref = "http://connect.qq.com/widget/shareqq/index.html?"+s.join('&');
	window.open(qhref,'_blank');  
}
//朋友圈
shareFun.sharetopyq=function(url,desc,title,summary,pics)  
{  
	if(!pics&&SHAREMSG.sharePicUrl){
		pics=SHAREMSG.sharePicUrl;
	 }
	 var p = {
    url:url, /*获取URL，可加上来自分享到QQ标识，方便统计*/
    desc:desc||'', 
    title:title, /*分享标题(可选)*/
    summary:summary||'', /*分享摘要(可选)*/
    pics:pics||SHAREMSG.sharePicUrl, /*分享图片(可选)*/
    site:SHAREMSG.site, /*分享来源(可选) 如：QQ分享*/
    style:'203',
    width:16,
    height:16
    };
    var s = [];
    for(var i in p){
        s.push(i + '=' + encodeURIComponent(p[i]||''));
    }
	var qhref = "http://connect.qq.com/widget/shareqq/index.html?"+s.join('&');
	window.open(qhref,'_blank');  
}