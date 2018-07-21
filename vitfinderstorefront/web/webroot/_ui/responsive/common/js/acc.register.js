ACC.register = {

		validateRegistration: function(){
			 if($("body").hasClass("page-login")){
			$("#registerForm").validate({
				rules: {
				titleCode:"required",
				firstName: "required",
				lastName:  "required",
				line1:"required",
				townCity:"required",
				countryIso:"required",
				postcode:{
					required:true,
					custompostcode: true
				},
				phone:{
					required:true,
					customphone: true
				},
				email: {
					required: true,
					email:true
				},
				pwd:{
					minlength:7
				},
				checkPwd:{
					minlength : 7,
					equalTo: "#password"
				}
				},
			messages: {
				titleCode:{
					required:registerErrorMessage[0]
				},
				firstName:{
					required:registerErrorMessage[1]
				},
				lastName:{
					required:registerErrorMessage[2]
				},
				line1: {
					required: registerErrorMessage[3]
				},
				townCity:{
					required: registerErrorMessage[4]
				},
				countryIso:{
					required: registerErrorMessage[5]
				},
				postcode:{
					required: registerErrorMessage[6],
					custompostcode: registerErrorMessage[6]
				},
				phone:{
					required: registerErrorMessage[7],
					customphone: registerErrorMessage[7]
				},
				email: {
					required: registerErrorMessage[8],
					email: registerErrorMessage[8]
				},
				/*pwd:{
					minlength: registerErrorMessage[9]
				},
				checkPwd:{
					equalTo:registerErrorMessage[10]
				}*/
			}
				
			
		});
	}
}
		
};

$(document).ready(function ()
		{
	ACC.register.validateRegistration();
	$.validator.addMethod('customphone', function (value, element) {
		if ( value.length != 11 ){
			return false;
		}
        return this.optional(element) || /^\+?[0-9]+$/.test(value);
    }, "Please enter a valid phone number");
	
	$.validator.addMethod('custompostcode', function (test, element) {
		
		 size = test.length
		 test = test.toUpperCase(); // Change to uppercase
		 while (test.slice(0,1) == " ") // Strip leading spaces
		  {test = test.substr(1,size-1);size = test.length
		  }
		 while(test.slice(size-1,size)== " ") // Strip trailing spaces
		  {test = test.substr(0,size-1);size = test.length
		  }
		 if (size < 6 || size > 8){ // Code length rule
		  return false;
		  }
		 if (!(isNaN(test.charAt(0)))){ // leftmost character must be alpha
										// character rule
		   return false;
		  }
		 if (isNaN(test.charAt(size-3))){ // first character of inward code
											// must be numeric rule
		   return false;
		  }
		 if (!(isNaN(test.charAt(size-2)))){ // second character of inward
												// code must be alpha rule
		   return false;
		  }
		 if (!(isNaN(test.charAt(size-1)))){ // third character of inward
												// code must be alpha rule
		   return false;
		  }
		 if (!(test.charAt(size-4) == " ")){// space in position length-3 rule
		   return false;
		   }
		 count1 = test.indexOf(" ");count2 = test.lastIndexOf(" ");
		 if (count1 != count2){// only one space rule
		   return false;
		  }
		return true;
		
    }, "Please enter postcode");
});
