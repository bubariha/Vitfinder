
$(document).ready(function() {
	
	if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
	    var msViewportStyle = document.createElement("style");
	    msViewportStyle.appendChild(
	        document.createTextNode(
	            "@-ms-viewport{width:auto!important}"
	        )
	    );
	    document.getElementsByTagName("head")[0].
	        appendChild(msViewportStyle);
	}
 
	// Questionary Page validation
	
	// gender
	$('#question\\.gender').on('change', function() {
		if($("#question\\.gender option:selected").index() != 0){
			$('#questn-select-vit-error').hide();
			if($('#progressGender'). prop("checked") == false){
				$("#progressGender").removeAttr("disabled");
				$('#progressGender').click();
				$("#progressGender").attr("disabled", true);
			}
		}else{
			progressVal -= 25;
			$('#questn-select-vit-error').show();
			if($('#progressGender'). prop("checked") == true){
				$("#progressGender").removeAttr("disabled");
				$('#progressGender').click();
				$("#progressGender").attr("disabled", true);
			}
		}
	});
	
	// age
	
	$('#question\\.age').keyup(function(event){
		var age = $("#question\\.age");
		if(this.value!='-')
	          while(isNaN(this.value))
	            this.value = this.value.split('').reverse().join('').replace(/[\D]/i,'')
	                                   .split('').reverse().join('');
		$("#question\\.age").prop('maxLength', 3);
		
		if(age.val() != ""){
			$('#age-vit-error').hide();
			if(age.val() >=0 && age.val() <= 130){
				$('#age-vit-error').hide();
				if($('#progressAge'). prop("checked") == false){
					$("#progressAge").removeAttr("disabled");
					$('#progressAge').click();
					$("#progressAge").attr("disabled", true);
				}
			}else{
				$('#age-vit-error').show();
				if($('#progressAge'). prop("checked") == true){
					$("#progressAge").removeAttr("disabled");
					$('#progressAge').click();
					$("#progressAge").attr("disabled", true);
				}	
			}
		}else{
			$('#age-vit-error').show();
			if($('#progressAge'). prop("checked") == true){
				$("#progressAge").removeAttr("disabled");
				$('#progressAge').click();
				$("#progressAge").attr("disabled", true);
			}
		}
	});
	
	// height
	$('#question\\.height').keyup(function(event){
		var height = $("#question\\.height");
		if(this.value!='-')
	          while(isNaN(this.value))
	            this.value = this.value.split('').reverse().join('').replace(/[\D]/i,'')
	                                   .split('').reverse().join('');
		$('#question\\.height').prop('maxLength', 3);
		if(height.val() != ""){
			$('#height-vit-error').hide();
			if(height.val() >=0 && height.val() <= 250){
				$('#height-vit-error').hide();
				if($('#progressHeight'). prop("checked") == false){
					$("#progressHeight").removeAttr("disabled");
					$('#progressHeight').click();
					$("#progressHeight").attr("disabled", true);
				}
			}else{
				$('#height-vit-error').show();
				if($('#progressHeight'). prop("checked") == true){
					$("#progressHeight").removeAttr("disabled");
					$('#progressHeight').click();
					$("#progressHeight").attr("disabled", true);
				}
			}
		}else{
			$('#height-vit-error').show();
			if($('#progressHeight'). prop("checked") == true){
				$("#progressHeight").removeAttr("disabled");
				$('#progressHeight').click();
				$("#progressHeight").attr("disabled", true);
			}
		}
	});
	
	// weight
	$('#question\\.weight').keyup(function(event){
		var weight = $("#question\\.weight");
		if(this.value!='-')
	          while(isNaN(this.value))
	            this.value = this.value.split('').reverse().join('').replace(/[\D]/i,'')
	                                   .split('').reverse().join('');
		$('#question\\.weight').prop('maxLength', 3);
		if(weight.val() != ""){
			$('#weight-vit-error').hide();
			if(weight.val() >=0 && weight.val() <= 300){
				$('#weight-vit-error').hide();
				if($('#progressWeight'). prop("checked") == false){
					$("#progressWeight").removeAttr("disabled");
					$('#progressWeight').click();
					$("#progressWeight").attr("disabled", true);
				}
			}else{
				$('#weight-vit-error').show();
				if($('#progressWeight'). prop("checked") == true){
					$("#progressWeight").removeAttr("disabled");
					$('#progressWeight').click();
					$("#progressWeight").attr("disabled", true);
				}
			}
		}else{
			$('#weight-vit-error').show();
			if($('#progressWeight'). prop("checked") == true){
				$("#progressWeight").removeAttr("disabled");
				$('#progressWeight').click();
				$("#progressWeight").attr("disabled", true);
			}
		}
	});
	

	
  $("#bannerCVtf").owlCarousel({
 	  autoPlay : true,
	  stopOnHover : true,
	  navigation : false, // Show next and prev buttons
      slideSpeed : 300,
      paginationSpeed : 400,
      singleItem:true
   });
  
  //
  $("#homeProductVtf").owlCarousel({
 	  autoPlay : 5000,
	  stopOnHover : true,
	  navigation : true, // Show next and prev buttons
      slideSpeed : 300,
      paginationSpeed : 400,
      singleItem:true,
      navigationText: [
                       "<i class='fa fa-angle-left'> </i>",
                       "<i class='fa fa-angle-right'> </i>"]
   });
  
  //
  enquire.register("screen and (max-width:1023px)", {
	  match : function() {
	   $('#headerNavVtf').removeClass('col-md-7');
	  },
	  unmatch : function() {
		  $('#headerNavVtf').addClass('col-md-7');
	  }
	});
  
  
  // Header Search 
  	$('.header-search-icon-vtf').click(function(){
  		$(this).hide();
  		$('.header-search-input-vtf').show().addClass("search-expand-vtf");
    });
  	
  	
 // Header Search form validation 
        $("#btn-search-vtf").click(function(){
        
        var hasError = false;
        
        var searchVal = $("#js-site-search-input").val();
        if(searchVal == '') {
            $(".header-search-input-vtf").addClass('search-error-vtf');
            hasError = true;
        } 
        if(hasError == true) {return false;}
    });

   
        
        var acc = document.getElementsByClassName("control-label");
        var i;

        for (i = 0; i < acc.length; i++) {
            acc[i].onclick = function(){
                this.classList.toggle("active");
                this.nextElementSibling.classList.toggle("show");
          }
        }
        
        $('.questn-select-vit').click(function(){
        	$('.controls').toggle();
        });
       	
});


/**
 * 
 * @returns {Boolean}
 */
function validate(){
	var isError=false;
	var d = document.getElementById("question.gender");
	d.className += " show";
	if(d.selectedIndex==0){
		document.getElementById("questn-select-vit-error").style.display = "block";
	    isError=true;
	}else{
		document.getElementById("questn-select-vit-error").style.display = "none";	
	}
	var d = document.getElementById("question.age");
	d.className += " show";
	if(d.value==null ||d.value==""){
		document.getElementById("age-vit-error").style.display = "block";
		isError =true;
	}else{
		document.getElementById("age-vit-error").style.display = "none";	
	}
	var d = document.getElementById("question.height");
	d.className += " show";
	if(d.value==null ||d.value==""){
		document.getElementById("height-vit-error").style.display = "block";
		isError =true;
	}else{
		document.getElementById("height-vit-error").style.display = "none";	
	}
	var d = document.getElementById("question.weight");
	d.className += " show";
	if(d.value==null ||d.value==""){
		document.getElementById("weight-vit-error").style.display = "block";
		isError =true;
	}else{
		document.getElementById("weight-vit-error").style.display = "none";	
	}
	
	if(isError){
		return false;
	}else{
		return true;
	}
	
}
/**
 * 
 * @returns {Boolean}
 */
function validateQuestionaireForm(){
	var d = document.getElementById("category_option_selection");
	if(d.selectedIndex==0){
		document.getElementById("category_option_selection-error").style.display = "block";
	    return false;
	}else if($("#recommendation-terms").is(":visible")) {
		var f = document.getElementById("recommendation_terms_accept-check");
		if(!f.checked){
			document.getElementById("recommendation_terms_accept-error").style.display = "block";
			return false;
		}
	}
	else{
		document.getElementById("category_option_selection-error").style.display = "none";
		document.getElementById("recommendation_terms_accept-error").style.display = "none";
		return true;
	}
}


// Health Questionnaire Progress Bar
$('#progressInputVTF input').on('click', function() {
    var emptyValue = 0;
    $('input:checked').each(function() {
        emptyValue += parseInt($(this).val());
        
    });
    $('.progress-bar-vtf').css('width', emptyValue + '%').attr('aria-valuenow', emptyValue);
});




// Second Navigation responsive work
enquire.register("screen and (max-width:"+screenSmMax+")", {
	match : function() {
		$('#secondNavigationVtf').appendTo('#mainNavVtf .nav-pills');
		$('#secondNavigationVtf li').addClass('auto');
	},
	unmatch : function() {
		$('#secondNavigationVtf').appendTo('#secondaryNavVtf');
		$('#secondNavigationVtf li').removeClass('auto');
	}
});