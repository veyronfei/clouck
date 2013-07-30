<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="menu" content="resources"/>
<meta name="serviceMenu" content="vpc"/>
<meta name="resourceMenu" content="internetGateways"/>
<title>Internet Gateway</title>
</head>
<body>
<table id="table_id" class="display">
    <thead>
        <tr>
            <th>Region</th>
            <th>ID</th>
            <th>State</th>
            <th>VPC</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
$(document).ready(function() {
    loadPage('internetGateways', '${currentAccount.id}', new Date().getTime());
});
</script>
</body>
</html>
