<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta http-equiv="X-UA-Compatible" content="IE=10">
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
		<script type="text/javascript">
			
			//取得附件名称
			function getFileName(){
			  var files=document.getElementById("FANKUI").value;
			  var arr =files.split('\\'); 
			      var fileName=arr[arr.length-1].substring(0,arr[arr.length-1].lastIndexOf('.'));
			      //将标题默认为附件名称
			  $("#WORKNAME").val(fileName);
			}
			
			//返回上一页
			function back(){
				//参数直接写在引号中即可
				window.location.href="http://127.0.0.1:8089/BJJD/bjjduserinfo/fileList.do?userName=${pd1.userName}&buMenId=${pd1.buMenId}&buMenName=${pd1.buMenName}";
			}
			
		</script>
	</head>
<body>
	<form action="bjjduserinfo/${msg}.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="WORKGUANLI_ID" id="WORKGUANLI_ID" value="${pd.WORKGUANLI_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">标题:</td>
				<td><input type="text" name="WORKNAME" id="WORKNAME" readonly="readonly" value="${pd.WORKNAME}" maxlength="32" placeholder="这里输入标题" title="标题"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">创建人:</td>
				<td><input type="text" readonly="readonly" name="ASSIGNER" id="ASSIGNER" value="${pd.ASSIGNER}" maxlength="32" placeholder="这里输入指派人" title="指派人"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">所属科室:</td>
				<td><input type="text" readonly="readonly" name="BUMENNAME" id="BUMENNAME" value="${pd.BUMENNAME}" maxlength="32" placeholder="这里输入所属科室" title="所属科室"/></td>
			</tr>
			<!-- <tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">附件:</td>
				<td><input type="file"  name="file" id="FANKUI" placeholder="这里输入附件" onmouseout="getFileName()" title="附件"/></td>
			</tr> -->
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">类型:</td>
				<td>
				  <select name="TYPE" id="TYPE" disabled="disabled">
			        <option value="免检" ${pd.TYPE == "免检"? 'selected="selected"':''}>免检</option>
					<option value="周审" ${pd.TYPE == "周审"? 'selected="selected"':''}>周审</option>
					<option value="月审" ${pd.TYPE == "月审"? 'selected="selected"':''}>月审</option>
					<option value="年审" ${pd.TYPE == "年审"? 'selected="selected"':''}>年审</option>
				  </select>
			   </td>
			</tr>
		
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">上传时间:</td>
				<td><input type="text" name="ASSIGNDATE" id="ASSIGNDATE" readonly="readonly" value="${pd.ASSIGNDATE}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
			</tr>
			
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">内容简介:</td>
				<td><input type="text" name="CONTENT" id="CONTENT" readonly="readonly" value="${pd.CONTENT}" maxlength="32" placeholder="这里输入工作备注" title="内容简介"/></td>
			</tr>

			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="back();">返回上一页</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>