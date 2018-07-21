ACC.questionary = {

	_autoload: [
		"checkCategoryAvailabe",
		"requestMethodChange"
	],
	checkCategoryAvailabe: function ()
	{
		

		$('#category_option_selection').on('change', function(e) {
		  e.preventDefault();
		  categorycode = $(this).val();
			searchCategoryIsLast(categorycode);
		});

		function searchCategoryIsLast(categorycode) {
			$("#questionary_action_button").attr('disabled','disabled');
			$.ajax({
				type : "POST",
				url : "/questionary/isSubCategoryAvailable",
		        data: "categoryCode=" + categorycode ,
				timeout : 100000,
				success : function(data) {
					console.log("SUCCESS: ", data);
					if(data == false){
						$("#questionary_action_button").attr('value', 'Submit');
						$('#questionaireForm').attr('action', '/en_GB/questionary/symptoms/'+categorycode+'/submit'); 
						$("#questionary_action_button").removeAttr('disabled');
						$("#recommendation-terms").show();

					}else{

						$("#questionary_action_button").attr('value', 'Next');
						$('#questionaireForm').attr('action', '/en_GB/questionary/symptoms/'+categorycode+'/next'); 
						$("#questionary_action_button").removeAttr('disabled');
						$("#recommendation-terms").hide();
					}
				},
				error : function(e) {
					console.log("ERROR: ", e);
				},
				done : function(e) {
					console.log("DONE");
				}
			});
		}
	},
requestMethodChange : function(){
	$(".store_data_validation").on('change', function() { 
	    if(this.checked) {
	    	
			$('#customerCommonQuestionaireForm').attr('method', 'get'); 
	    }else{
			$('#customerCommonQuestionaireForm').attr('method', 'post'); 

	    }
	});
}

}
