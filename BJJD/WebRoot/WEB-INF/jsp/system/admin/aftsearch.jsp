<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附件搜索</title>
<!-- 调用js -->
<script type="text/javascript" src="../static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"> 

//根据附件扫描
function saomiao(){
	var keyword = $("#keyword").val();
	//清空div里的内容
	 $.ajax({
		type:'post',
		data:{"keyword":keyword},
		url:'/BJJD/bjjduserinfo/createIndex',
		dataType:'text',
// 		jsonp:'callback', 
		async:false,
		success:function(result){
			alert(result);													
		},
		    error:function(listInfo){
			console.log("错误信息："+JSON.stringify(listInfo));
		}
	}) 
}

//根据附件内容检索	
function submits(){
	var keyword = $("#keyword").val();
	if(keyword==""){
		alert("请输入关键字");
		return;
	}
	//清空div里的内容
	document.getElementById("fujian").innerHTML = "";
//	document.getElementById("fujian").style.display="none";
//	var keyword= document.getElementById("keyword").value;
	 $.ajax({
		type:'post',
		data:{"keyword":keyword},
		url:'/BJJD/bjjduserinfo/searchByKeyWord',
		dataType:'text',
// 		jsonp:'callback', 
		async:false,
		success:function(listInfo){
			var list = eval("("+listInfo+")");
		    if(list==""){
		    	alert("无此附件");
		    }else{
		    	for(var i=0;i<3;i++){//只下载那个内容最接近的3个附件 
				      //alert(list[i].MULU+" ===="+list[i].TITLE);//访问每一个的属性
				        //在控制器打开
				        console.log("附件路径:"+list[i].MULU+"附件名称:"+list[i].TITLE);
				        var fujianHtml='<sapn id="title">'+'<a title="'+list[i].TITLE+'" href=/BJJD/bjjduserinfo/download?path='+list[i].MULU+'>'+list[i].TITLE+'</span><br>';
				       /*  fujianHtml+='<sapn id="mulu">'+list[i].MULU+'</span>'; */
				        $("#fujian").append(fujianHtml);
				        document.getElementById("fujian").style.display="block";
		            }				
		    }
		 // for(var i=0;i<list.length;i++){
												
		},
		    error:function(listInfo){
			console.log("错误信息："+JSON.stringify(listInfo));
		}
	}) 
}
	 
//根据附件名称检索	 
function submitByFileName(){
	var keyword = $("#keyword").val();
	if(keyword==""){
		alert("请输入关键字");
		return;
	}
	//清空div里的内容
	document.getElementById("fujian").innerHTML = "";
//	document.getElementById("fujian").style.display="none";
//	var keyword= document.getElementById("keyword").value;
	 $.ajax({
		type:'post',
		data:{"fileName":keyword},
		url:'/BJJD/bjjduserinfo/searchByFileName',
		dataType:'text',
// 		jsonp:'callback', 
		async:false,
		success:function(listInfo){
			var list = eval("("+listInfo+")");
			 if(list==""){
			     alert("无此附件");
			 }else{
		 // for(var i=0;i<list.length;i++){
				for(var i=0;i<3;i++){//只下载那个内容最接近的3个附件 
			      //alert(list[i].MULU+" ===="+list[i].TITLE);//访问每一个的属性
			        //在控制器打开
			        console.log("附件路径:"+list[i].MULU+"附件名称:"+list[i].TITLE);
			        var fujianHtml='<sapn id="title">'+'<a title="'+list[i].TITLE+'" href=/BJJD/bjjduserinfo/download?path='+list[i].MULU+'>'+list[i].TITLE+'</span><br>';
			       /*  fujianHtml+='<sapn id="mulu">'+list[i].MULU+'</span>'; */
			        $("#fujian").append(fujianHtml);
			        document.getElementById("fujian").style.display="block";
	            }	
		    }
		},
		error:function(listInfo){
			console.log("错误信息："+JSON.stringify(listInfo));
		}
	}) 
}
	 
function subform(){
 if (document.zl_form.keyword.value==""){
  alert("请输入关键字！");
  document.zl_form.keyword.focus();
  return false;
 }     
 return true;       
}
//取得附件名称
function getFileName(){
  var files=document.getElementById("CONTENT").value;
  var arr =files.split('\\'); 
      var fileName=arr[arr.length-1].substring(0,arr[arr.length-1].lastIndexOf('.'));
      //将标题默认为附件名称
  $("#fileName").val(fileName);
}

</script>
<style type="text/css">
body { background:#fff; font:100% Helvetica, Arial, sans-serif; padding-top:50px; }

h1 { color:#fff; }

#container { margin:0 auto; width:590px; }
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
	width:650px;
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
.gray {
   color: #e9e9e9;
   border: solid 1px #555;
   background: #6e6e6e;
   background: -webkit-gradient(linear, left top, left bottom, from(#888), to(#575757));
   background: -moz-linear-gradient(top,  #888,  #575757);
   filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#888888', endColorstr='#575757');
}
.orange {
   color: #fef4e9;
   border: solid 1px #da7c0c;
   background: #f78d1d;
   background: -webkit-gradient(linear, left top, left bottom, from(#faa51a), to(#f47a20));
   background: -moz-linear-gradient(top,  #faa51a,  #f47a20);
   filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#faa51a', endColorstr='#f47a20');
}
</style>

</head>
<body>
   <div id="container">
		<p align="center"> 请您选择需要上传的文件</p>
		<form id="form1" name="form1" method="post" action="/BJJD/bjjduserinfo/uploadFile" enctype="multipart/form-data">
		 <table border="0" align="center">
		<tr>
		   <td>附件名称：</td>
		   <td>
		    <input id="fileName" name="name" style="border-radius:5px;" type="text" id="name" size="20" >
		   </td>
		  </tr>   
		  <tr>
		   <td>上传文件：</td>
		   <td><input id="CONTENT" name="file" style="border-radius: 5px;" onmouseout="getFileName()" type="file" size="20" ></td>
		  </tr> 
		     
		  <tr>   
		   <td></td>
		   
		   <td>
		    <input type="submit" name="submit" value="提交" >
		    <input type="reset" name="reset" value="重置" >
		   </td>
		  </tr>
		 </table>
		</form>
   </div>

   
   <div id="container">
		<h1>全文检索</h1>
		<form name="zl_form"  target="_new" method="post" action="/BJJD/bjjduserinfo/searchByKeyWord" id="search_box">
			<div class="wrapper">
			    <input size="50" type="text" id="keyword" placeholder="输入关键字" name="keyword"/>
			    <button type="button" class="gray" style="left:540px;" name="submit" onclick="submitByFileName()" value="标题搜索" >标题搜索</button>
			    <button type="button" class="orange" name="submit" onclick="submits()" value="全文搜索">全文搜索</button>
			</div>
		</form> 
	</div>
<div id="fujian" align="center" style="display: none"> 
</div>

</body>
</html>