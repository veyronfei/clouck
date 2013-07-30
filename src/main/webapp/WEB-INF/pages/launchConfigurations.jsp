<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="menu" content="resources"/>
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="launchConfigurations"/>
<title>Launch Configuration</title>
</head>
<body>
<table id="table_id" class="display">
	<thead>
		<tr>
			<th>Region</th>
			<th>Image ID</th>
			<th>Time Detected</th>
		</tr>
	</thead>
</table>
<script type="text/javascript">
	$(document).ready(
			function() {
				loadPage('launchConfigurations', '${currentAccount.id}',
						new Date().getTime());
			});
</script>
</body>
</html>
