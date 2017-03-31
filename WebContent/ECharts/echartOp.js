document.write("<script src='../js/dist/echarts-all.js'></script>");
function getChart(comId,op){
	var myChart = echarts.init(document.getElementById(comId));
    var option = op;
myChart.setOption(option);
}     
