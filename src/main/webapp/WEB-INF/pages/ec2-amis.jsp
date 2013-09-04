<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="amis"/>
<title>AMIs</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="${ctx}/accounts/${currentAccount.id}/ec2/amis/versions">AMIs</a> <span class="divider">&gt;</span></li>
            <li class="active">
                <i class="icon-time"></i>
                <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="amisTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Name</th>
                    <th>AMI Name</th>
                    <th>AMI ID</th>
                    <th>Source</th>
                    <th>Owner</th>
                    <th>Visibility</th>
                    <th>Status</th>
                    <th>Platform</th>
                    <th>Root Device</th>
                    <th>Virtualization</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                <c:set var="resource" value="${ec2Resource.resource}"/>
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>${ec2Resource.tag}</td>
                    <td>${resource.name}</td>
                    <td>
                        <a href="${ctx}/accounts/${currentAccount.id}/ec2/amis/${ec2Resource.id}?at=${at.time}">${resource.imageId}</a>
                    </td>
                    <td>${resource.ownerId}/${resource.name}</td>
                    <td>${resource.ownerId}</td>
                    <td>
                    <c:choose>
                        <c:when test="${resource.public}">
                        Public
                        </c:when>
                        <c:otherwise>
                        Private
                        </c:otherwise>
                    </c:choose>
                    </td>
                    <td>${resource.state}</td>
                    <td>
                    <c:choose>
                        <c:when test="${empty resource.platform}">
                        Other Linux
                        </c:when>
                        <c:otherwise>
                        ${resource.platform}
                        </c:otherwise>
                    </c:choose>
                    </td>
                    <td>${resource.rootDeviceType}</td>
                    <td>${resource.virtualizationType}</td>
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
    	loadSummaryDataTable('amisTable');
	});
</script>
</body>
</html>
