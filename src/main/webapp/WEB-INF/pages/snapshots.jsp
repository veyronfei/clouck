<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="menu" content="resources"/>
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="snapshots"/>
<title>Snapshot</title>
</head>
<body>
<table id="table_id" class="display">
    <thead>
        <tr>
            <th>Region</th>
            <th>Snapshot Id</th>
            <th>Description</th>
            <th>StartTime</th>
            <th>VolumeSize</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
$(document).ready(function() {
    loadPage('snapshots', '${currentAccount.id}', new Date().getTime());
});
</script>
</body>
</html>
