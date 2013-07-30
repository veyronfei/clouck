<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="menu" content="resources"/>
<meta name="serviceMenu" content="vpc"/>
<meta name="resourceMenu" content="routeTables"/>
<title>Route Table</title>
</head>
<body>
<table id="table_id" class="display">
    <thead>
        <tr>
            <th>Region</th>
            <th>Route Table ID</th>
            <th>VPC</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
$(document).ready(function() {
    loadPage('routeTables', '${currentAccount.id}', new Date().getTime());
});
</script>
</body>
</html>
