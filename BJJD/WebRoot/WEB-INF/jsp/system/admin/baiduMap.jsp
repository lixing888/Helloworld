<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>  
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
    <style type="text/css">  
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}  
    </style>  
  <!--引入地图包，地图包网址的ak属性是你在百度地图开放平台上申请的秘钥-->  
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=pP3qX41UimuuNTGodOHcL15NMYQm76nb"></script>  
  <!--引入jquery-->  
  <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>  
    <title>地址解析</title>  
</head>  
<body>  
  <!--我们要在这里创建地图实例，这个div容器的id属性会在后面用到-->  
    <div id="allmap"></div>  
</body>  
</html>  
<script type="text/javascript">  
    // 百度地图API功能  
    var map = new BMap.Map("allmap");//创建百度地图实例，这里的allmap是地图容器的id  
    var point = new BMap.Point(116.270888,40.131966);//创建一个点对象(北京)，这里的参数是地图上的经纬度  
    map.centerAndZoom(point, 16);//这里是将地图的中心移动到我们刚才创建的点；这里的12是地图的缩放界别；数值越大，地图看的越细   20
    var navigationControl = new BMap.NavigationControl();//创建平移缩放控件  
    map.addControl(navigationControl);//添加到地图  
    var scaleControl = new BMap.ScaleControl();//这里创建比例尺控件  
    map.addControl(scaleControl);//添加到地图  
    var overviewMapControl = new BMap.OverviewMapControl();//创建缩略图控件，注意这个控件默认是在地图右下角，并且是缩着的  
    map.addControl(overviewMapControl);//添加到地图  
    var mapTypeControl = new BMap.MapTypeControl();//创建地图类型控件  
    map.addControl(mapTypeControl);  
    var x = 100;  
    var y = 80;  
    var opts = {anchor: BMAP_ANCHOR_BOTTOM_RIGHT, offset: new BMap.Size(x, y)};  
    var navigationControl = new BMap.NavigationControl(opts);//创建平移缩放控件  
    map.addControl(navigationControl);//添加到地图  
    
  //定位  
    var geolocation = new BMap.Geolocation();  
       geolocation.getCurrentPosition(function(result){  
        if(this.getStatus() == BMAP_STATUS_SUCCESS){  
               var mk = new BMap.Marker(result.point);//创建一个覆盖物  
               map.addOverlay(mk);//增加一个标示到地图上  
               map.panTo(result.point);  
               /* latitude  = result.point.lat;//获取到的纬度  
               longitude = result.point.lng;//获取到的经度   */
               latitude  = 40.131966;//获取到的纬度  ,
               longitude = 116.270888;//获取到的经度  
            // alert("获取到的经度  :"+longitude+"获取到的纬度 :"+latitude);
           }  
       });  
       
       function addMarker(point){  // 创建图标对象     
           var myIcon = new BMap.Icon("position.png", new BMap.Size(23, 50), {offset: new BMap.Size(10, 25)});  
           // 创建标注对象并添加到地图     
           var marker = new BMap.Marker(point, {icon: myIcon});      
           map.addOverlay(marker);  
       }  
</script>  
