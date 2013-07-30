<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="menu" content="resources"/>
<meta name="serviceMenu" content="vpc"/>
<meta name="resourceMenu" content="dhcpOptionss"/>
<title>DHCP Options Sets</title>
</head>
<body>
<table id="table_id" class="display">
    <thead>
        <tr>
            <th>Region</th>
            <th>DHCP Options Set ID</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
$(document).ready(function() {
    loadPage('dhcpOptionss', '${currentAccount.id}', new Date().getTime());
});
</script>
</body>
</html>
