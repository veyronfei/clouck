<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="keyPairs"/>
<title>Key Pair History</title>
</head>
<body>
<c:choose>
    <c:when test="${currentRegion.regions.name == null}">
        <c:set var="regionUrl" scope="request"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="regionUrl" scope="request">?region=${currentRegion.regions.name}</c:set>
    </c:otherwise>
</c:choose>
<div class="row-fluid">
<h4>Total ${numOfEc2VersionMetas} Version(s) of Ec2 Key Pair: ${uniqueId}</h4>
</div>
<div class="row-fluid">
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th width="20%">Time Detected</th>
				<th width="60%">Events</th>
				<th width="20%">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ec2ResourceReps}" var="ec2ResourceRep">
			<tr>
				<td>${ec2ResourceRep.timeDetected}</td>
				<td>
	                <c:forEach items="${ec2ResourceRep.reps}" var="rep">
	                ${rep.message}<br>
	                </c:forEach>
                </td>
				<td>
				    <c:if test="${ec2ResourceRep.resourceId != null}">
				    <a class="btn" href="/app/accounts/${currentAccount.id}/ec2/keyPairs/${ec2ResourceRep.resourceId}${regionUrl}">Browse</a>
				    </c:if>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

</body>
</html>
