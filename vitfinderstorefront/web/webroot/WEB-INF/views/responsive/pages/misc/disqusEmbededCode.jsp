<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<body>

	<div id="disqus_thread"></div>
	<script>
			/**
			 *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
			 *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables
			 */

			var disqus_config = function() {

				var base64EncodedStr = "${base64EncodedStr}";
				var signature = "${signature}";
				var timestamp = "${timestamp}";

				this.page.remote_auth_s3 = base64EncodedStr + ' ' + signature
						+ ' ' + timestamp;

				this.page.api_key = "${publicApiKey}";
				this.page.url = "https://vitfinder.com/articles/"+"${pageCode}"; // Replace PAGE_URL with your page's canonical URL variable
				this.page.identifier = "${pageID}"; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
				
				//this.sso = {
				  //        logout:  "https://vitfinder.local:9002/en_GB/logout",
				    //      width:   "800",
				      //    height:  "400"
				   // };
				
			};

			(function() { // REQUIRED CONFIGURATION VARIABLE: EDIT THE SHORTNAME BELOW
				var d = document, s = d.createElement('script');

				s.src = '//'+"${forumName}"+'.disqus.com/embed.js'; // IMPORTANT: Replace EXAMPLE with your forum shortname!
				//s.src = '//devvit.disqus.com/embed.js';
				s.setAttribute('data-timestamp', +new Date());
				(d.head || d.body).appendChild(s);
			})();
		</script>
	<noscript>
		Please enable JavaScript to view the <a
			href="https://disqus.com/?ref_noscript" rel="nofollow">comments
			powered by Disqus.</a>
	</noscript>
	
</body>