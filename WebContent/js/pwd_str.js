//Meter update function 
function meterUpdate(e) {
	var score = strengthMeasure(e.value), 
		desc = [ "", "非常弱", "弱", "中", "中", "强", "非常强" ],
		meter = $("$meter"), 
		meterWidget = zk.Widget.$(meter);

	switch (score) {
		case 1:
		case 2:
			meterWidget.setSclass("meter meter-red");
			break;
		case 3:
		case 4:
			meterWidget.setSclass("meter meter-orange");
			break;
		case 5:
		case 6:
			meterWidget.setSclass("meter meter-green");
			break;
		default:
			meterWidget.setSclass("meter");
	}
	
	//Get ZK Widget through jQuery selector
	zk.Widget.$($(".meter-inner")).setWidth(score * meter.width() / desc.length + "px");
	
	//Get ZK Widget through ID
	zk.Widget.$("$msg").setValue(desc[score]);
}

/* Simple Rule */
function strengthMeasure(text) {
	var score = 0;
	if (text.length > 0)
		score++;

	if (text.length > 6)
		score++;

	if ((text.match(/[a-z]/)) && (text.match(/[A-Z]/)))
		score++;

	if (text.match(/\d+/))
		score++;

	if (text.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/))
		score++;

	if (text.length > 12)
		score++;

	if (text.length == 0)
		score = 0;

	return score;
}