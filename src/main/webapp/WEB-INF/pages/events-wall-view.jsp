<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
    <meta name="decorator" content="noty-theme"/>
    <title>Events Wall View</title>
	<script>
		$(document).ready(function() {
			sinceId = 0;
			setInterval(getEvents, 5000);
		});
	
		function getEvents() {
		    console.log('get events..');
	    	url = 'http://localhost:8080/rest/accounts/1/events' + '?since=' + sinceId;
			$.get(url, function(response) {
				$.each(response, function(index, event) {
					sinceId = event.version_id;
					console.log(sinceId);
					text = event.description + '(' + event.rep_name + ')' + '@' + event.time_detected;
					switch(event.action_type) {
					case 'Add':
					    generateNoty('success', text);
					    break;
					case 'Delete':
						generateNoty('error', text);
					    break;
                    case 'Update':
                    	generateNoty('warning', text);
                        break;
					default:
						console.log('something goes wrong.');
					}
				});
			});
		}
		function generateNoty(alertType, text) {
               var n = noty({
                   type: alertType,
                   text: text,
                   dismissQueue: true
               });
		}
	</script>
</head>
<body>
</body>
</html>