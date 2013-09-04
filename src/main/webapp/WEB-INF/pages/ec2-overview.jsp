<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="overview"/>
<title>Ec2 Overview</title>
</head>
<body>
<h4>Recent 10 events</h4>
<div class="row-fluid">
    <div class="span9">
	    <table class="table">
	        <thead>
	            <tr>
                    <th width="10%">Region</th>
	                <th width="20%">Time Detected</th>
	                <th width="60%">Operations</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:forEach items="${ec2VersionMetaReps}" var="ec2VersionMetaRep">
	            <tr>
                    <td>${ec2VersionMetaRep.region.shortDesc}</td>
	                <td>${ec2VersionMetaRep.timeDetected}</td>
	                <td>
	                <c:forEach items="${ec2VersionMetaRep.reps}" var="rep">
	                ${rep.message}<br>
	                </c:forEach>
	                </td>
	            </tr>
	            </c:forEach>
	        </tbody>
	    </table>
    </div>
    <div class="span3">
        <div style="border-bottom: solid 1px #ccc;">
            <h4>Resources</h4>
        </div>
		<ul id="resources" class="unstyled" style="font-size: 18px;">
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/instances?at=${millis}${regionUrl}">${instances}&nbsp;&nbsp;Instances</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/spotInstanceRequests?at=${millis}${regionUrl}">${spotInstanceRequests}&nbsp;&nbsp;Spot Requests</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/amis?at=${millis}${regionUrl}">${amis}&nbsp;&nbsp;AMIs</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/volumes?at=${millis}${regionUrl}">${volumes}&nbsp;&nbsp;Volumes</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/snapshots?at=${millis}${regionUrl}">${snapshots}&nbsp;&nbsp;Snapshots</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/securityGroups?at=${millis}${regionUrl}">${securityGroups}&nbsp;&nbsp;Security Groups</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/elasticIPs?at=${millis}${regionUrl}">${elasticIPs}&nbsp;&nbsp;Elastic IPs</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/placementGroups?at=${millis}${regionUrl}">${placementGroups}&nbsp;&nbsp;Placement Groups</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/loadBalancers?at=${millis}${regionUrl}">${loadBalancers}&nbsp;&nbsp;Load Balancers</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/keyPairs?at=${millis}${regionUrl}">${keyPairs}&nbsp;&nbsp;Key Pairs</a></li>
			<li><a href="${ctx}/accounts/${currentAccount.id}/ec2/networkInterfaces?at=${millis}${regionUrl}">${networkInterfaces}&nbsp;&nbsp;Network Interfaces</a></li>
		</ul>
    </div>
</div>
<!-- <div class="row-fluid">
    <div class="span6">
        <h5>Instances</h5>
        <div id="instanceChart" style="width:100%; height:150px;"></div>
    </div>
    /span
    <div class="span6">
        <h5>Volumes</h5>
        <div id="volumeChart" style="width:100%; height:150px;"></div>
    </div>
</div>
<div class="row-fluid">
    <div class="span6">
        <h5>Snapshots</h5>
        <div id="snapshotChart" style="width:100%; height:150px;"></div>
    </div>
    /span
    <div class="span6">
        <h5>Elastic IPs</h5>
        <div id="elasticIPChart" style="width:100%; height:150px;"></div>
    </div>
</div> -->

<script type="text/javascript">
/* $(document).ready(function() {
	loadOverviewChart('instanceChart', 'instances', '${currentAccount.id}', '${millis}', '${currentRegion.regions.name}');
	loadOverviewChart('volumeChart', 'volumes', '${currentAccount.id}', '${millis}', '${currentRegion.regions.name}');
	loadOverviewChart('snapshotChart', 'snapshots', '${currentAccount.id}', '${millis}', '${currentRegion.regions.name}');
	loadOverviewChart('elasticIPChart', 'elasticIPs', '${currentAccount.id}', '${millis}', '${currentRegion.regions.name}');
}); */
</script>


</body>
</html>