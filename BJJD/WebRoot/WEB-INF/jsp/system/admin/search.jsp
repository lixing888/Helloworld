<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>全文搜索</title>
<style type="text/css">
body { background:#fff; font:100% Helvetica, Arial, sans-serif; padding-top:50px; }

h1 { color:#fff; }

#container { margin:0 auto; width:570px; }
#search_box { 
	background:-moz-linear-gradient(top, #ffd73a, #ffa500); 
	background:-webkit-gradient(linear, 0 0, 0 100%, from(#ffd73a), to(#ffa500)); 
	border:1px solid #d28703; 
	border-radius:5px; 
	-moz-border-radius:5px;
	-webkit-border-radius:5px; 
	-moz-box-shadow:inset 0 1px #ffff90, inset 0 -2px 5px #ffd05d, 0 0 0 4px rgba(255,255,255,0.65);
	-webkit-box-shadow:inset 0 1px #ffff90, inset 0 -2px 5px #ffd05d, 0 0 0 4px rgba(255,255,255,0.65);
	padding:9px;
	width:570px;
}

#search_box .wrapper { 
	background:#fff; 
	border:1px solid #d28703;
	-moz-border-radius:2px;
	-webkit-border-radius:2px;
	-moz-box-shadow:inset 0 1px 2px rgba(0,0,0,.3), 0 1px #ff0; 
	-webkit-box-shadow:inset 0 1px 2px rgba(0,0,0,.3), 0 1px #ff0; 
	height:50px;
	padding-left:10px; 
	position:relative;
}

#search_box input,
#search_box input:focus { border:none; color:#333; outline:none; font:bold 24px Helvetica, Arial, sans-serif; margin:12px 0; width:510px; }
#search_box button {
	background:-moz-linear-gradient(top, #453e26, #000);
	background:-webkit-gradient(linear, 0 0, 0 100%, from(#453e26), to(#000));
	border:1px solid #000;
	-moz-border-radius:2px;
	-webkit-border-radius:2px;
	-moz-box-shadow:inset 0 -2px 3px #193544, inset 0 1px #907817, 0 1px 1px rgba(0,0,0,4);
	-webkit-box-shadow:inset 0 -2px 3px #193544, inset 0 1px #907817, 0 1px 1px rgba(0,0,0,.4);
	cursor:pointer;
	height:45px; 
	position:absolute; 
	right:2px; 
	top:2px; 
	width:45px;
}

h1 { margin-bottom:0; }
.read_article { color:#fff; display:block; font-size:12px; margin-bottom:30px; }
</style>
</head>

<body>
	<div id="container">
		<h1>全文检索</h1>
		<form action="#" id="search_box">
			<div class="wrapper">
			    <input size="50" type="text" id="keyword" placeholder="输入关键字" name="keyword"/>
                <input type="button" name="submit" onclick="submitByFileName()" value="标题搜索" style="font-size: 20pt">
                <input type="button" name="submit" onclick="submits()" value="全文搜索" style="font-size: 20pt">
				<!-- <input type="text" id="search" name="search" placeholder="输入关键字" /> -->
				<button type="submit" class="search_btn"><img src="search_icon.png" title="Search" /></button>
			</div>
		</form>
	    
	</div>
    <script src="jquery-1.4.2.min.js"></script>
<script>
    $(function () {

        var $placeholder = $('input[placeholder]');

        if ($placeholder.length > 0) {

            var attrPh = $placeholder.attr('placeholder');

            $placeholder.attr('value', attrPh)
              .bind('focus', function () {

                  var $this = $(this);

                  if ($this.val() === attrPh)
                      $this.val('').css('color', '#171207');

              }).bind('blur', function () {

                  var $this = $(this);

                  if ($this.val() === '')
                      $this.val(attrPh).css('color', '#333');

              });

        }

    });

</script>
<!-- <div style="text-align:center;clear:both">
<p>适用浏览器：FireFox、Chrome、Safari、傲游、搜狗. 不支持IE8、360、Opera、世界之窗。</p>
<p><a href="http://www.quickui.net" target="_blank" class="white">点击下载更多组件</a></p>
</div> -->
</body>
</html>