<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="menu" content="resources"/>
<meta name="serviceMenu" content="iam"/>
<meta name="resourceMenu" content="roles"/>
<title>Role</title>
</head>
<body>
<table id="table_id" class="display">
    <thead>
        <tr>
            <th>Role Name</th>
            <th>Creation Time</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
$(document).ready(function() {
    loadPage('roles', '${currentAccount.id}', new Date().getTime());
});
</script>
</body>
</html>
