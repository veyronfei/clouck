<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="loadBalancers"/>
<title>Load Balancer</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="/accounts/${currentAccount.id}/ec2/loadBalancers/versions">Load Balancers</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="/accounts/${currentAccount.id}/ec2/loadBalancers?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.loadBalancerName}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="30%">Load Balancer Name:</th>
                <th width="70%">${ec2Resource.resource.loadBalancerName}</th>
            </tr>
            <tr>
                <th width="30%">Time Detected:</th>
                <th width="70%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
		<tbody>
            <tr>
                <td><strong>DNS Name:</strong></td>
                <td>
                ${ec2Resource.resource.DNSName} (A Record)<br>
                ipv6.${ec2Resource.resource.DNSName} (AAAA Record)<br>
                dualstack.${ec2Resource.resource.DNSName} (A or AAAA Record)
                </td>
            </tr>
			<tr>
				<td><strong>Scheme:</strong></td>
				<td>${ec2Resource.resource.scheme}</td>
			</tr>
            <tr>
                <td><strong>Status:</strong></td>
                <td>XXXXX</td>
            </tr>
            <tr>
                <td><strong>Port Configuration:</strong></td>
                <td>
                <c:forEach items="${ec2Resource.resource.listenerDescriptions}" var="listenerDescription">
                ${listenerDescription.listener.loadBalancerPort} (${listenerDescription.listener.protocol}) forwarding to ${listenerDescription.listener.instancePort} (${listenerDescription.listener.instanceProtocol})<br>
                XXX
                </c:forEach>
                </td>
            </tr>
            <tr>
                <td><strong>Availability Zones:</strong></td>
                <td>
                <c:forEach items="${ec2Resource.resource.availabilityZones}" var="availabilityZone" varStatus="status">
                    ${availabilityZone}<br>
                </c:forEach>
                </td>
            </tr>
            <tr>
                <td><strong>Source Security Group:</strong></td>
                <td>
                ${ec2Resource.resource.sourceSecurityGroup.ownerAlias}/${ec2Resource.resource.sourceSecurityGroup.groupName}<br>
                Owner Alias: ${ec2Resource.resource.sourceSecurityGroup.ownerAlias}<br>
                Group Name: ${ec2Resource.resource.sourceSecurityGroup.groupName}
                </td>
            </tr>
            <tr>
                <td><strong>Hosted Zone ID:</strong></td>
                <td>
                ${ec2Resource.resource.canonicalHostedZoneNameID}
                </td>
            </tr>
            <tr>
                <td><strong>VPC ID:</strong></td>
                <td>
                ${ec2Resource.resource.VPCId}
                </td>
            </tr>
		</tbody>
	</table>
    <br>
    <table class="table table-striped table-bordered table-hover">
        <thead>
            <caption>
                <h4>Health Check</h4>
            </caption>
        </thead>
        <tbody>
            <tr>
                <td width="30%"><strong>Ping Target:</strong></td>
                <td width="70%">${ec2Resource.resource.healthCheck.target}</td>
            </tr>
            <tr>
                <td width="30%"><strong>Timeout:</strong></td>
                <td width="70%">${ec2Resource.resource.healthCheck.timeout} seconds</td>
            </tr>
            <tr>
                <td width="30%"><strong>Interval:</strong></td>
                <td width="70%">${ec2Resource.resource.healthCheck.interval} seconds</td>
            </tr>
            <tr>
                <td width="30%"><strong>Unhealthy Threshold:</strong></td>
                <td width="70%">${ec2Resource.resource.healthCheck.unhealthyThreshold}</td>
            </tr>
            <tr>
                <td width="30%"><strong>Healthy Threshold:</strong></td>
                <td width="70%">${ec2Resource.resource.healthCheck.healthyThreshold}</td>
            </tr>
            
        </tbody>
    </table>
    <br>
    <table class="table table-striped table-bordered table-hover">
        <thead>
            <caption>
                <h4>Listeners</h4>
            </caption>
            <tr>
                <th>Load Balancer Protocol</th>
                <th>Load Balancer Port</th>
                <th>Instance Protocol</th>
                <th>Instance Port</th>
                <th>Cipher</th>
                <th>SSL Certificate</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${ec2Resource.resource.listenerDescriptions}" var="listenerDescription">
            <tr>
                <td>${listenerDescription.listener.protocol}</td>
                <td>${listenerDescription.listener.loadBalancerPort}</td>
                <td>${listenerDescription.listener.instanceProtocol}</td>
                <td>${listenerDescription.listener.instancePort}</td>
                <td></td>
                <td>
                <c:choose>
                    <c:when test="${listenerDescription.listener.SSLCertificateId != null}">
		                ${fn:split(listenerDescription.listener.SSLCertificateId, '/')[1]}
                    </c:when>
                    <c:otherwise>
                        N/A
                    </c:otherwise>
                </c:choose>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>