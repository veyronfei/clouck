<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="placementGroups"/>
<title>Placement Groups</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="${ctx}/accounts/${currentAccount.id}/ec2/placementGroups/versions">Placement Groups</a> <span class="divider">&gt;</span></li>
            <li class="active">
                <i class="icon-time"></i>
                <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="placementGroupsTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Group Name</th>
                    <th>Strategy</th>
                    <th>State</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                <c:set var="resource" value="${ec2Resource.resource}"/>
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>
                        <a href="${ctx}/accounts/${currentAccount.id}/ec2/placementGroups/${ec2Resource.id}?at=${at.time}">${resource.groupName}</a>
                    </td>
                    <td>${resource.strategy}</td>
                    <td>${resource.state}</td>
                    <td>
                        <fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}" />
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        loadSummaryDataTable('placementGroupsTable');
    });
</script>
</body>
</html>
