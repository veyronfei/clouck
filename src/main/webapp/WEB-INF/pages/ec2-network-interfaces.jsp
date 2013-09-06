<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="networkInterfaces"/>
<title>Network Interfaces</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="${ctx}/accounts/${currentAccount.id}/ec2/networkInterfaces/versions">Network Interfaces</a> <span class="divider">&gt;</span></li>
            <li class="active">
                <i class="icon-time"></i>
                <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="networkInterfacesTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Name</th>
                    <th>Network Interface ID</th>
                    <th>Subnet ID</th>
                    <th>Zone</th>
                    <th>Security Groups</th>
                    <th>Description</th>
                    <th>Instance ID</th>
                    <th>Status</th>
                    <th>Public IP</th>
                    <th>Primary Private IP</th>
                    <th>Secondary Private IPs</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                    <c:set var="resource" value="${ec2Resource.resource}"/>
                    <tr>
	                    <td>${ec2Resource.region}</td>
	                    <td>${ec2Resource.tag}</td>
	                    <td>
	                        <a href="${ctx}/accounts/${currentAccount.id}/ec2/networkInterfaces/${ec2Resource.id}?at=${at.time}">${resource.networkInterfaceId}</a>
	                    </td>
	                    <td>${resource.subnetId}</td>
	                    <td>${resource.availabilityZone}</td>
	                    <td>
	                    <c:forEach items="${resource.groups}" var="securityGroup" varStatus="status">
	                        ${securityGroup.groupName}<c:if test="${status.count != fn:length(resource.groups)}">,&nbsp;</c:if>
	                    </c:forEach>
	                    </td>
	                    <td>${resource.description}</td>
	                    <td>${resource.attachment.instanceId}</td>
	                    <td>${resource.status}</td>
	                    <td>${resource.association.publicIp}</td>
	                    <td>${resource.privateIpAddress}</td>
	                    <td>
	                    <c:forEach items="${resource.privateIpAddresses}" var="privateIpAddress" varStatus="status">
	                        ${privateIpAddress.privateIpAddress}<c:if test="${status.count != fn:length(resource.privateIpAddresses)}">,&nbsp;</c:if>
	                    </c:forEach>
	                    </td>
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
        loadSummaryDataTable('networkInterfacesTable');
    });
</script>
</body>
</html>
