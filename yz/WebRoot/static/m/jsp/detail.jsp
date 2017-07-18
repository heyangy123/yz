<style type="text/css">
.pin{ position: relative; box-shadow:none; -webkit-box-shadow:none; border:none;overflow:visible}
.article-detail{display: block; position: relative; margin: 0 auto;}
.article-detail .title,.article-detail h1 {font-size:20px; font-weight:700; text-align:left;}
.article-detail .subtitle{font-size: 12px;text-align:left;}
.article-detail .article-content img { max-width: 100%; _width:600px; display: block;clear: both; margin: 0 auto; opacity: 1; border: 1px solid gainsboro;}
.article-detail .article-content table{ margin:0 auto; }
.article-detail .article-content table tr td{border:1px solid #dadada; padding:5px}
.article-detail .article-content{color: #444; font-size: 16px; line-height: 28px;font-family:Tahoma, Helvetica, Arial, "微软雅黑","华文细黑", "宋体", sans-serif;word-break:break-all;word-wrap:break-word;}
.article-detail .article-content p { color: #444; font-size: 16px; line-height: 28px; font-family:Tahoma, Helvetica, Arial, "微软雅黑","华文细黑", "宋体", sans-serif;word-break:break-all;word-wrap:break-word;}
.article-content iframe { max-width: 100%;}
.article-detail .article-content h5{font-size:14px;	line-height:2em;}
.article-detail .article-content ul{ margin-left:40px; list-style-type:disc}
.article-detail .article-content ul li{ line-height:28px;}
.article-detail .article-content img{border-radius: 9px;-webkit-border-radius: 9px;-moz-border-radius: 9px;}
</style>
<div class="pin">
	<div class="article-detail">
		<div class="article-content" ng-bind-html="itemInfo.content"></div>
	</div>
</div>