<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="networkInterfaces"/>
<title>Network Interfaces</title>
</head>
<body>
<c:choose>
    <c:when test="${currentRegion.regions.name == null}">
        <c:set var="regionUrl"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="regionUrl">?region=${currentRegion.regions.name}</c:set>
    </c:otherwise>
</c:choose>
<div class="row-fluid">
    <div class="span13">
        <div class="span6">
	        <h4>Network Interfaces @ ${at}</h4>
        </div>
        <div class="span1 offset5">
            <a class="btn" href="/accounts/${currentAccount.id}/ec2/versions/networkInterfaces${regionUrl}">${numOfEc2VersionMetas} versions</a>
        </div>
        <!-- <div id="instanceChart" style="width:100%; height:100px;"></div> -->
    </div>
</div>

<div class="row-fluid" style="padding-top: 20px;">
    <div class="span13">
        <table id="networkInterfacesTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <c:if test="${currentRegion.regions.name == null}">
	                    <th>Region</th>
                    </c:if>
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
                    <c:if test="${currentRegion.regions.name == null}">
	                    <td>${ec2Resource.region}</td>
                    </c:if>
                    <td>
                        <a href="/accounts/${currentAccount.id}/ec2/networkInterfaces/${ec2Resource.id}${regionUrl}">${resource.networkInterfaceId}</a>
                    </td>
                    <td>${resource.subnetId}</td>
                    <td>${resource.availabilityZone}</td>
                    <td>
                    <c:forEach items="${ec2Resource.resource.groups}" var="securityGroup">
                        ${securityGroup.groupName},&nbsp;
                    </c:forEach>
                    </td>
                    <td>${resource.description}</td>
                    <td>${resource.attachment.instanceId}</td>
                    <td>${resource.status}</td>
                    <td>${resource.association.publicIp}</td>
                    <td>${resource.privateIpAddress}</td>
                    <td>
                    <c:forEach items="${ec2Resource.resource.privateIpAddresses}" var="privateIpAddress">
                        ${privateIpAddress.privateIpAddress},&nbsp;
                    </c:forEach>
                    </td>
                    <td>
                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ec2Resource.timeDetected}" />
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
		/* loadOverviewChart('instanceChart', 'instances', '${currentAccount.id}', '${millis}'); */
        $('#networkInterfacesTable').dataTable({
            "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sLengthMenu": "_MENU_ records per page"
            }
        });
	});
</script>
</body>
</html>
