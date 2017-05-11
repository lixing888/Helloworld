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
	<title>附件列表</title>
	
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	<link rel="stylesheet" href="../../static/css/skin.css" type="text/css"> 
	</head>
<body>

<!-- 附件列表 -->		
<div class="container-fluid" id="fileLists"  style="display: block;">

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="bjjduserinfo/fileList.do?userName=${pd.ASSIGNER}&buMenId=${pd.BUMENID}&buMenName=${pd.BUMENNAME}" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="workName" value="${pd.workName}" placeholder="这里输入标题" />
							<input autocomplete="off" id="nav-search-input" type="text" name="creater" value="${pd.creater}" placeholder="这里输入创建人" />
							<td><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart" value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
							<td><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd" value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
							<td style="vertical-align:top;width: 20px"> 
							 	<select class="chzn-select" name="buMenName1" id="buMenName1" data-placeholder="请选择处室"  style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="办公室" ${pd.buMenName1 == "办公室"? 'selected="selected"':''}>办公室</option>
									<option value="研究室" ${pd.buMenName1 == "研究室"? 'selected="selected"':''}>研究室</option>
									<option value="综合处(政策法规处)" ${pd.buMenName1 == "综合室(政策法规处)"? 'selected="selected"':''}>综合室(政策法规处)</option>
									<option value="综合处(网络化工作协调办公区)" ${pd.buMenName1 == "综合室(网络化工作协调办公区)"? 'selected="selected"':''}>综合室(网络化工作协调办公区)</option>
									<option value="信息中心" ${pd.buMenName1 == "信息中心"? 'selected="selected"':''}>信息中心</option>
									<option value="社会建设处" ${pd.buMenName1 == "社会建设处"? 'selected="selected"':''}>社会建设处</option>
									<option value="党建工作处" ${pd.buMenName1 == "党建工作处"? 'selected="selected"':''}>党建工作处</option>
									<option value="社区组织工作处" ${pd.buMenName1 == "社区组织工作处"? 'selected="selected"':''}>社区组织工作处</option>
									<option value="机关党委(工会)" ${pd.buMenName1 == "机关党委(工会)"? 'selected="selected"':''}>机关党委(工会)</option>
									<option value="综合处(宣传处办公室)" ${pd.buMenName1 == "综合处(宣传处办公室)"? 'selected="selected"':''}>综合处(宣传处办公室)</option>
									<option value="社会心理研究所" ${pd.buMenName1 == "党建工作处"? 'selected="selected"':''}>党建工作处</option>
									<option value="社区组织工作处" ${pd.buMenName1 == "社会心理研究所"? 'selected="selected"':''}>社会心理研究所</option>
									<option value="社会动员工作处(志愿者工作处)" ${pd.buMenName1 == "社会动员工作处(志愿者工作处)"? 'selected="selected"':''}>社会动员工作处(志愿者工作处)</option>
							  	</select>
							</td>
							<td style="vertical-align:top;width: 20px"> 
							 	<select class="chzn-select" name="type1" id="type1" data-placeholder="请选择类别"  style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="免检" ${pd.type1 == "免检"? 'selected="selected"':''}>免检</option>
									<option value="周审" ${pd.type1 == "周审"? 'selected="selected"':''}>周审</option>
									<option value="月审" ${pd.type1 == "月审"? 'selected="selected"':''}>月审</option>
									<option value="年审" ${pd.type1 == "年审"? 'selected="selected"':''}>年审</option>
							  	</select>
							</td>
							
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
				    <tr bgcolor="#FF0000">
			        <td colspan="9" valign="top">
			             <ul>
			               <li onclick="add('${pd.ASSIGNER}','${pd.BUMENNAME}','${pd.BUMENID}');"style="float:left;cursor: pointer;height: 20px;line-height:25px;padding: 0px 0px 0 20px; margin-right:20px; margin-left: -32px; font-weight: bold;color:#0585c7; background-image: url(../../static/img/tx.png) no-repeat;">
			                 <img alt="" src="../static/img/tx.png"  style="margin-top: -10px;"  /> &nbsp;新增
			               </li>
			             </ul>
			        </td>
			      </tr>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">序号</th>
						<!-- <th class="center">ID</th> -->
						<th class="center">标题</th>
						<th class="center">创建人</th>
						<th class="center">所属处室</th>
						<th class="center">类别</th>
						<th class="center">上传时间</th>
						<th class="center">内容</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.WORKGUANLI_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
										<%-- <td>${var.ID}</td> --%>
										<td style="text-align: center;width: 250px;"><a href="bjjduserinfo/goXiangqing.do?WORKGUANLI_ID=${var.WORKGUANLI_ID}&userName=${pd.ASSIGNER}&buMenId=${pd.BUMENID}&buMenName=${pd.BUMENNAME}">${var.WORKNAME}</a></td>
										<td style="text-align: center;width: 40px;">${var.ASSIGNER}</td>
										<td style="text-align: center;width: 150px;">${var.BUMENNAME}</td>
										<td style="text-align: center;width: 30px;">${var.TYPE}</td>
										<td style="text-align: center;width: 150px;">${var.ASSIGNDATE}</td>
										<td style="text-align: center;">${var.CONTENT}</td>
										<%-- <td style="text-align: center;">${var.FANKUI}</td> --%>
										
								<td style="width: 30px;" class="center">
									<div class='hidden-phone visible-desktop btn-group'>
										<div class="inline position-relative">
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
											<li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.WORKGUANLI_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
											<li><a style="cursor:pointer;" title="删除" onclick="del('${var.WORKGUANLI_ID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>	
										</ul>
										</div>
									</div>
								</td>
							</tr>
						
						</c:forEach>
						
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<a class="btn btn-small btn-success" onclick="add('${pd.ASSIGNER}','${pd.BUMENNAME}','${pd.BUMENID}');">新增</a>
					<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>	
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>

	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
<!-- 新增附件DIV -->	
<div class="container-fluid" id="addFile" style="display: none;">
<form action="<%=basePath%>bjjduserinfo/uploadFile.do" name="Form1" id="Form1" method="post" enctype="multipart/form-data">
		<input type="hidden" name="WORKGUANLI_ID" id="WORKGUANLI_ID" value="${pd.WORKGUANLI_ID}"/>
		<input type="hidden" name="BUMENID" id="BUMENID" value="${pd.BUMENID}"/>
		<input type="hidden" name="BUMENNAME" id="BUMENNAME" value="${pd.BUMENNAME}"/>
		<input type="hidden" name="userName" id="userName" value="${pd.ASSIGNER}"/>
        <input type="hidden" name="buMenId" id="buMenId" value="${pd.BUMENID}"/>
          <input type="hidden" name="buMenName" id="buMenName" value="${pd.BUMENNAME}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">标题:</td>
				<td><input type="text" name="WORKNAME" id="WORKNAME" value="${pd.WORKNAME}" maxlength="32" placeholder="这里输入标题" title="标题"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">创建人:</td>
				<td><input type="text" readonly="readonly" name="ASSIGNER" id="ASSIGNER" value="${pd.ASSIGNER}" maxlength="32" placeholder="这里输入创建人" title="创建人"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">所属科室:</td>
				<td><input type="text" readonly="readonly" name="BUMENNAME" id="BUMENNAME" value="${pd.BUMENNAME}" maxlength="32" placeholder="这里输入所属科室" title="所属科室"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">附件:</td>
				<td><input type="file"  name="file" id="FANKUI" placeholder="这里输入附件" onmouseout="getFileName()" title="附件"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">类型:</td>
				<td>
				  <select name="TYPE" id="TYPE">
			        <option value="免检" ${pd.TYPE == "免检"? 'selected="selected"':''}>免检</option>
					<option value="周审" ${pd.TYPE == "周审"? 'selected="selected"':''}>周审</option>
					<option value="月审" ${pd.TYPE == "月审"? 'selected="selected"':''}>月审</option>
					<option value="年审" ${pd.TYPE == "年审"? 'selected="selected"':''}>年审</option>
				  </select>
			   </td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">上传时间:</td>
				<td><input type="text" name="ASSIGNDATE" id="ASSIGNDATE" value="${pd.ASSIGNDATE}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
			</tr>
			
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">内容:</td>
				<td>
				   <textarea name="CONTENT" id="CONTENT" value="${pd.CONTENT}" style=" height:100%;" ></textarea>
				</td>
<%-- 				<input type="textarea" name="CONTENT" id="CONTENT" value="${pd.CONTENT}" maxlength="32" placeholder="这里输入内容" title="内容"/></td>--%>			</tr>

			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="goback();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
</div>

<!-- 修改附件DIV 
<div class="container-fluid" id="editFileDiv" style="display: none;">
<form action="bjjduserinfo/fileEdit.do" name="Form2" id="Form2" method="post" enctype="multipart/form-data">
		<input type="hidden" name="WORKGUANLI_ID" id="WORKGUANLI_ID" value="${pd.WORKGUANLI_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">标题:</td>
				<td><input type="text" name="WORKNAME" id="WORKNAME" value="${pd.WORKNAME}" maxlength="32" placeholder="这里输入标题" title="标题"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">创建人:</td>
				<td><input type="text" readonly="readonly" name="ASSIGNER" id="ASSIGNER" value="${pd.ASSIGNER}" maxlength="32" placeholder="这里输入指派人" title="指派人"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">附件:</td>
				<td><input type="file"  name="file" id="FANKUI" placeholder="这里输入附件" onmouseout="getFileName()" title="附件"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">上传时间:</td>
				<td><input type="text" name="ASSIGNDATE" id="ASSIGNDATE" value="${pd.ASSIGNDATE}" maxlength="32" placeholder="这里输入指派时间" title="指派时间"/></td>
			</tr>
			
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">工作内容:</td>
				<td><input type="text" name="CONTENT" id="CONTENT" value="${pd.CONTENT}" maxlength="32" placeholder="这里输入工作备注" title="工作备注"/></td>
			</tr>

			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="editFile();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
</div>
-->
	
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		
		//新增
		function add(creater,bumenname,bumenId){
			$("#fileLists").hide();
			$("#addFile").show();
<%-- 		window.location.href='<%=basePath%>bjjduserinfo/goFileAdd.do'; --%>	
        	 <%-- var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>bjjduserinfo/goFileAdd.do';
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show(); --%>
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
				//	top.jzts();
					var url = "<%=basePath%>bjjduserinfo/deleteFile.do?WORKGUANLI_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
					    //nextPage(${page.currentPage});
						window.location.reload();
						
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			//跳转到编辑页面
			window.location.href='<%=basePath%>bjjduserinfo/gofileEdit.do?WORKGUANLI_ID='+Id;
		    
			// top.jzts();
			 <%-- var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>bjjduserinfo/gofileEdit.do?WORKGUANLI_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show(); --%>
		}
		</script>
		
		<script type="text/javascript">

		//取得附件名称
		function getFileName(){
		  var files=document.getElementById("FANKUI").value;
		  var arr =files.split('\\'); 
		      var fileName=arr[arr.length-1].substring(0,arr[arr.length-1].lastIndexOf('.'));
		      //将标题默认为附件名称
		  $("#WORKNAME").val(fileName);
		}
		
		//保存
		function save(){

		  if($("#WORKNAME").val()==""){
				$("#WORKNAME").tips({
					side:3,
		            msg:'请输入工作名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#WORKNAME").focus();
				return false;
			}
			if($("#ASSIGNER").val()==""){
				$("#ASSIGNER").tips({
					side:3,
		            msg:'请输入指派人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ASSIGNER").focus();
				return false;
			}
			
			if($("#ASSIGNDATE").val()==""){
				$("#ASSIGNDATE").tips({
					side:3,
		            msg:'请输入指派时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ASSIGNDATE").focus();
				return false;
			}
			var userName=$("#ASSIGNER").val();
			var buMenId=$("#BUMENID").val();
			var buMenName=$("#BUMENNAME").val();
			$("#Form1").submit();
			alert("保存成功");
		//  console.log(userName+buMenName);
			Form1.action ='<%=basePath%>bjjduserinfo/fileList.do';
			Form1.submit();
			//location.href="http://127.0.0.1:8088/BJJD/bjjduserinfo/fileList.do?userName="+userName+"&buMenId="+buMenId+"&buMenName="+buMenName
			/* $("#fileLists").show();
			$("#addFile").hide(); */
		}
		
		//保存编辑
		function editFile(){
			$("#Form2").submit();
			$("#fileLists").show();
			$("#addFile").hide();
			$("#editFileDiv").hide();
		}
		
		//取消按钮
		function goback(){
			window.location.reload();
		}

		$(function() {
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
						//	top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>bjjduserinfo/deleteFileAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											/* nextPage(${page.currentPage}); */
											window.location.reload();
									 });
								}
							});
						}
					}
				}
			});
		}
		
		</script>
		
	</body>
</html>

