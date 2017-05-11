<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>时钟特效</title>
<style>
 * {
 margin: 0;
 padding: 0;
 }
 .main {
 position: relative;
 margin: 100px auto;
 width: 300px;
 height: 300px;
 border-radius: 300px;
 border: 1px solid #000;
 box-shadow: 2px 5px #808080;
 }
 #timeLabel {
 position: absolute;
 background-color: pink;
 width: 80px;
 height: 25px;
 left: 110px;
 top: 180px;
 color: #fff;
 line-height: 25px;
 text-align: center;
 }
 #hour {
 width: 100px;
 height: 10px;
 background-color: red;
 position: absolute;
 left: 150px;
 top: 145px;
 transform-origin: 0 50%;
 }
 #minute {
 width: 120px;
 height: 8px;
 background-color: blue;
 position: absolute;
 left: 150px;
 top: 146px;
 transform-origin: 0 50%;
 }
 #second {
 width: 140px;
 height: 4px;
 background-color: green;
 position: absolute;
 left: 150px;
 top: 148px;
 transform-origin: 0 50%;
 }
 .hourPointer, .minuterPointer, .secondPointer {
 position: absolute;
 transform-origin: 0 50%;
 }
 .hourPointer {
 height: 10px;
 width: 12px;
 left: 150px;
 top: 145px;
 background-color: #f00;
 z-index: 3;
 }
 .minuterPointer {
 height: 8px;
 width: 10px;
 left: 150px;
 top: 146px;
 background-color: #b6ff00;
 z-index: 2;
 }
 .secondPointer {
 height: 6px;
 width: 8px;
 left: 150px;
 top: 147px;
 background-color: #fa8;
 z-index: 1;
 }
 </style>
 <script>
 function Clock() {
 //定义属性
this.main = this.$("biaopan");
 this.timeLabel = this.$("timeLabel");
 this.hour = this.$("hour");
 this.minute = this.$("minute");
 this.second = this.$("second");
 this.nowHour = null;
 this.nowMinute = null;
 this.nowSecond = null;
 this.timer = null;
 var _this = this;
 //初始化函数
var init = function () {
 _this.getNowTime();
 _this.initClock();
 _this.InterVal();
 }
 init();
 }
 Clock.prototype.$ = function (id) {
 return document.getElementById(id)
 }
 Clock.prototype.CreateKeDu = function (className, deg, translateWidth) {
 var Pointer = document.createElement("div");
 Pointer.className = className
 Pointer.style.transform = "rotate(" + deg + "deg) translate(" + translateWidth + "px)";
 this.main.appendChild(Pointer);
 }
 Clock.prototype.getNowTime = function () {
 var now = new Date();
 this.nowHour = now.getHours();
 this.nowMinute = now.getMinutes();
 this.nowSecond = now.getSeconds();
 }
 Clock.prototype.setPosition = function () {
 this.second.style.transform = "rotate(" + (this.nowSecond * 6 - 90) + "deg)";
 this.minute.style.transform = "rotate(" + (this.nowMinute * 6 + 1 / 10 * this.nowSecond - 90) + "deg)";
 this.hour.style.transform = "rotate(" + (this.nowHour * 30 + 1 / 2 * this.nowMinute + 1 / 120 * this.nowSecond - 90) + "deg)";
 }
 Clock.prototype.initClock = function () {
 //初始化timeLabel
 this.timeLabel.innerHTML = this.nowHour + ":" + this.nowMinute + ":" + this.nowSecond;
 //初始化表盘
for (var index = 0; index < 4; index++) {
 this.CreateKeDu("hourPointer", index * 90, 138);
 }
 for (var index = 0; index < 12; index++) {
 this.CreateKeDu("minuterPointer", index * 30, 140);
 }
 for (var index = 0; index < 60; index++) {
 this.CreateKeDu("secondPointer", index * 6, 142);
 }
 this.setPosition();
 }
 Clock.prototype.InterVal = function () {
 clearInterval(this.timer);
 var _this = this;
 this.timer = setInterval(function () {
 _this.getNowTime();
 _this.second.style.transform = "rotate(" + (_this.nowSecond * 6 - 90) + "deg)";
 _this.minute.style.transform = "rotate(" + (_this.nowMinute * 6 + 1 / 10 * _this.nowSecond - 90) + "deg)";
 _this.hour.style.transform = "rotate(" + (_this.nowHour * 30 + 1 / 2 * _this.nowMinute + 1 / 120 * _this.nowSecond - 90) + "deg)";
 _this.timeLabel.innerHTML = _this.nowHour + ":" + _this.nowMinute + ":" + _this.nowSecond;
 }, 1000);
 }
 window.onload = function () {
 new Clock();
 }
 </script>

</head>
<body>

<div class="main" id="biaopan">
 <div id="timeLabel"></div>
 <div id="hour"></div>
 <div id="minute"></div>
 <div id="second"></div>
<!-- <canvas id="myCanvas" width="200" height="100" style="border:1px solid #c3c3c3;">
    Your browser does not support the canvas element.
</canvas> -->
</div>

</body>
</html>