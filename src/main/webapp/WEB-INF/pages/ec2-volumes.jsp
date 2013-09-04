<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="volumes"/>
<title>Volumes</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="${ctx}/accounts/${currentAccount.id}/ec2/volumes/versions">Volumes</a> <span class="divider">&gt;</span></li>
            <li class="active">
                <i class="icon-time"></i>
                <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="volumesTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Name</th>
                    <th>Volume ID</th>
                    <th>Capacity</th>
                    <th>Volume Type</th>
                    <th>Snapshot</th>
                    <th>Created</th>
                    <th>Zone</th>
                    <th>State</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>${ec2Resource.tag}</td>
                    <td>
                        <a href="${ctx}/accounts/${currentAccount.id}/ec2/volumes/${ec2Resource.id}?at=${at.time}">${ec2Resource.resource.volumeId}</a>
                    </td>
                    <td>${ec2Resource.resource.size} GiB</td>
                    <td>${ec2Resource.resource.volumeType}</td>
                    <td>${ec2Resource.resource.snapshotId}</td>
                    <td>
                        <fmt:formatDate pattern="${datePattern}" value="${ec2Resource.resource.createTime}" />
                    </td>
                    <td>${ec2Resource.resource.availabilityZone}</td>
                    <td>${ec2Resource.resource.state}</td>
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
        loadSummaryDataTable('volumesTable');
    });
</script>
</body>
</html>
