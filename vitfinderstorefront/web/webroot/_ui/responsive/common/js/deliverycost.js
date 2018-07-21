// vtf-40
$(document).ready(function() {
	$("#deliveryCostLightbox").click(function() {
		var retailername = $('#retailername').val();
		$.ajax({
			url : '/cart/deliveryCosts',
			type : "POST",
			data : {retailerName:retailername },
			title: 'hello',
			success : function(data) {
				var titleHeader = $('#showDeliveryCosts').html();
				ACC.colorbox.open(titleHeader,{
					html : data,
					width : 650,
					height : 700,
				});
							
			}
		});
	});
});
