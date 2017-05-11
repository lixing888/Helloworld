<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=10">
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
//	alert("全文搜索");
	var keyword1 = $("#keyword1").val();
	if(keyword1==""||keyword1==null){
		alert("请输入关键字");
		return;
	}
	//清空div里的内容
	document.getElementById("fujian").innerHTML = "";
//	document.getElementById("fujian").style.display="none";
//	var keyword= document.getElementById("keyword").value;
	 $.ajax({
		type:'post',
		data:{"keyword":keyword1},
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
				 //       console.log("附件路径:"+list[i].MULU+"附件名称:"+list[i].TITLE);
				        var fujianHtml='<sapn id="title">'+'<a title="'+list[i].TITLE+'" href=/BJJD/bjjduserinfo/download?path='+list[i].MULU+'>'+list[i].TITLE+'</a></span><br>';
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
	if(keyword==""||keyword==null){
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
			    //    console.log("附件路径:"+list[i].MULU+"附件名称:"+list[i].TITLE);
			        var fujianHtml='<sapn id="title">'+'<a title="'+list[i].TITLE+'" href=/BJJD/bjjduserinfo/download?path='+list[i].MULU+'>'+list[i].TITLE+'</a></span><br>';
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
	 


</script>
<style>
		*{margin: 0; padding: 0;}
		ul,li{list-style: none;}
		.content{width: 644px; height: 400px; margin: 100px auto;}
		.content h3{color: #666; font-size: 32px; text-align: center;}
		.content ul{width: 192px; height: 36px; overflow: hidden;}
		.content ul li{color: #666; font-size: 20px; text-align: center; width: 96px; height: 36px; line-height: 36px; float: left; background: #fff; cursor: pointer;}
		.content ul li.ac{background: #ffd336; color: #fff;}
		.content .sear{width: 644px; height: 54px; position: relative;}
		.content .sear input{width: 635px; height: 46px; border: 4px solid #ffd336; text-indent: 1em; outline: none;}
		.content .sear_but{width: 110px; height: 46px; position: absolute; right: 4px; top: 4px; background: url(../static/images/sear.png); cursor: pointer;}
	</style>

</head>
<body>

   				<%-- <div align="center" >
   					<img alt="center" src="<%=request.getContextPath()%>/static/img/qwjs.png">
   				</div> --%>
  <!--  <div id="container">
		<h1 style="text-align: center;">全文检索</h1>
		<form style="margin: 100px auto; border:none;" name="zl_form"  target="_new" method="post" action="/BJJD/bjjduserinfo/searchByKeyWord" id="search_box">
			<div class="wrapper" style="border:6px solid #ffd336;">
			   <input size="50" type="text" id="keyword" placeholder="输入关键字" name="keyword"/>
			   <button type="button" class="gray" style="left:540px; font-weight: bold;"  name="submit" onclick="submitByFileName()" value="标题搜索" >标题搜索</button>
			   <button type="button" class="orange" name="submit" onclick="submits()" value="全文搜索" style="font-weight: bold;">全文搜索</button>
			</div>
		</form> 
	</div> -->
<!-- <div id="fujian" align="center" style="display: none"> 
</div> -->
<form action="/BJJD/bjjduserinfo/searchByKeyWord" method="post" id="search_box">
<div class="content">
		<h3>全文检索</h3>
		<ul id="ul">
			<li class="ac" onclick="bt()"  id="bt">标题搜索</li>
			<li onclick="qw()"  id="qw">全文搜索</li>
		</ul>
		<div class="sear" id="title">
			<input type="text" name="keyword" id="keyword"  placeholder="请输入搜索内容">
			<div class="sear_but" onclick="subform()"></div>
		</div>
		
		<div class="sear" id="count" style="display: none">
			<input type="text" name="keyword1" id="keyword1"  placeholder="请输入搜索内容">
			<div class="sear_but" onclick="subform1()"></div>
		</div>
		<div id="fujian" align="center" style="display: none;margin-top: 50px"></div>
	</div>
	
</form>	
<script type="text/javascript">
$("#ul").on("click","li",function(){
	$("#ul li").removeClass("ac");
	$(this).addClass("ac");
});



	 function subform(){
	//	alert("标题");
		submitByFileName();
	}
		     
	 function qw() {
		 $("#title").hide();
	      $("#count").show();
	 }
	 
	 function subform1(){
	//	 alert("全文");
		 submits();
	}
	 
	 function bt() {
		 $("#title").show();
	      $("#count").hide();
	}
	
	
	
		
</script>
</body>
</html>