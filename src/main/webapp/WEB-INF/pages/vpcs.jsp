<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="menu" content="resources"/>
<meta name="serviceMenu" content="vpc"/>
<meta name="resourceMenu" content="vpcs"/>
<title>VPC</title>
</head>
<body>
<table id="table_id" class="display">
    <thead>
        <tr>
            <th>Region</th>
            <th>VPC ID</th>
            <th>State</th>
            <th>CIDR</th>
            <th>DHCP Options Set</th>
            <th>Tenancy</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
$(document).ready(function() {
    loadPage('vpcs', '${currentAccount.id}', new Date().getTime());
});
</script>
</body>
</html>
