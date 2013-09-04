<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="instances"/>
<title>Instances</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="${ctx}/accounts/${currentAccount.id}/ec2/instances/versions">Instances</a> <span class="divider">&gt;</span></li>
            <li class="active">
	            <i class="icon-time"></i>
	            <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="instancesTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Name</th>
                    <th>Instance</th>
                    <th>AMI ID</th>
                    <th>Root Device</th>
                    <th>Type</th>
                    <th>State</th>
                    <th>Monitoring</th>
                    <th>Security Groups</th>
                    <th>Key Pair Name</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>${ec2Resource.tag}</td>
                    <td>
                        <a href="${ctx}/accounts/${currentAccount.id}/ec2/instances/${ec2Resource.id}?at=${at.time}">${ec2Resource.resource.instanceId}</a>
                    </td>
                    <td>${ec2Resource.resource.imageId}</td>
                    <td>${ec2Resource.resource.rootDeviceType}</td>
                    <td>${ec2Resource.resource.instanceType}</td>
                    <td>${ec2Resource.resource.state.name}</td>
                    <td>
	                <c:choose>
	                    <c:when test="${ec2Resource.resource.monitoring.state == 'enabled'}">
	                        detailed
	                    </c:when>
	                    <c:otherwise>
	                        basic
	                    </c:otherwise>
	                </c:choose>
                    </td>
                    <td>
                    <c:forEach items="${ec2Resource.resource.securityGroups}" var="securityGroup" varStatus="status">
                        ${securityGroup.groupName}<c:if test="${status.count != fn:length(ec2Resource.resource.securityGroups)}">,&nbsp;</c:if>
                    </c:forEach>
                    </td>
                    <td>${ec2Resource.resource.keyName}</td>
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
    	loadSummaryDataTable('instancesTable');
	});
</script>
</body>
</html>
