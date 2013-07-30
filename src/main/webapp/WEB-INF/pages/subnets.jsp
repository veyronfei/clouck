<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="menu" content="resources"/>
<meta name="serviceMenu" content="vpc"/>
<meta name="resourceMenu" content="subnets"/>
<title>Subnet</title>
</head>
<body>
<table id="table_id" class="display">
    <thead>
        <tr>
            <th>Region</th>
            <th>Subnet ID</th>
            <th>State</th>
            <th>VPC ID</th>
            <th>CIDR</th>
            <th>Available IPs</th>
            <th>Availability Zone</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
$(document).ready(function() {
    loadPage('subnets', '${currentAccount.id}', new Date().getTime());
});
</script>
</body>
</html>
