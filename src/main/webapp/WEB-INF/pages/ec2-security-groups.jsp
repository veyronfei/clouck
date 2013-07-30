<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="securityGroups"/>
<title>Security Groups</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="/app/accounts/${currentAccount.id}/ec2/securityGroups/versions">Instances</a> <span class="divider">&gt;</span></li>
            <li class="active">
                <i class="icon-time"></i>
                <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="securityGroupsTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th width="5%">Region</th>
                    <th width="10%">Group ID</th>
                    <th width="10%">Name</th>
                    <th width="15%">VPC ID</th>
                    <th width="40%">Description</th>
                    <th width="20%">Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>
                        <a href="/app/accounts/${currentAccount.id}/ec2/securityGroups/${ec2Resource.id}?at=${at.time}">${ec2Resource.resource.groupId}</a>
                    </td>
                    <td>${ec2Resource.resource.groupName}</td>
                    <td>${ec2Resource.resource.vpcId}</td>
                    <td>${ec2Resource.resource.description}</td>
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
        loadSummaryDataTable('securityGroupsTable');
    });
</script>
</body>
</html>
