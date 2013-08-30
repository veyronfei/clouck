<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="networkInterfaces"/>
<title>Network Interface History</title>
</head>
<body>
<c:choose>
    <c:when test="${currentRegion.regions.name == null}">
        <c:set var="regionUrl" scope="request"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="regionUrl" scope="request">&region=${currentRegion.regions.name}</c:set>
    </c:otherwise>
</c:choose>
<div class="row-fluid">
<h4>Total ${numOfEc2VersionMetas} Versions</h4>
</div>
<div class="row-fluid">
	<table class="table table-striped table-hover">
		<thead>
			<tr>
			    <c:if test="${currentRegion.regions.name == null}">
                    <th width="10%">Region</th>
                </c:if>
				<th width="10%">Time Detected</th>
				<th width="40%">Events</th>
				<th width="10%">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ec2VersionMetaReps}" var="ec2VersionMetaRep">
			<tr>
			    <c:if test="${currentRegion.regions.name == null}">
					<td>${ec2VersionMetaRep.region.shortDesc}</td>
                </c:if>
				<td>${ec2VersionMetaRep.timeDetected}</td>
				<td>
				<c:forEach items="${ec2VersionMetaRep.reps}" var="rep">
				${rep.message}<br>
				</c:forEach>
				</td>
				<td>
				    <a class="btn" href="/accounts/${currentAccount.id}/ec2/networkInterfaces?at=${ec2VersionMetaRep.millis}${regionUrl}">Browse</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        /* loadOverviewChart('instanceChart', 'instances', '${currentAccount.id}', '${millis}'); */
/*         $('#instancesHistoryTable').dataTable({
            "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sLengthMenu": "_MENU_ records per page"
            },
            "aaSorting": [[0,'desc']]
        }); */
    });
</script>
</body>
</html>
