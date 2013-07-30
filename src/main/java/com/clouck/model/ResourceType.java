package com.clouck.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.clouck.exception.CloudVersionIllegalStateException;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Ami;
import com.clouck.model.aws.ec2.Ec2AutoScalingGroup;
import com.clouck.model.aws.ec2.Ec2ElasticIP;
import com.clouck.model.aws.ec2.Ec2Instance;
import com.clouck.model.aws.ec2.Ec2KeyPair;
import com.clouck.model.aws.ec2.Ec2LaunchConfiguration;
import com.clouck.model.aws.ec2.Ec2LoadBalancer;
import com.clouck.model.aws.ec2.Ec2NetworkInterface;
import com.clouck.model.aws.ec2.Ec2PlacementGroup;
import com.clouck.model.aws.ec2.Ec2Reservation;
import com.clouck.model.aws.ec2.Ec2SecurityGroup;
import com.clouck.model.aws.ec2.Ec2Snapshot;
import com.clouck.model.aws.ec2.Ec2SpotInstanceRequest;
import com.clouck.model.aws.ec2.Ec2Volume;
import com.clouck.model.aws.ec2.VpcDhcpOptions;
import com.clouck.model.aws.ec2.VpcInternetGateway;
import com.clouck.model.aws.ec2.VpcNetworkAcl;
import com.clouck.model.aws.ec2.VpcRouteTable;
import com.clouck.model.aws.ec2.VpcSubnet;
import com.clouck.model.aws.ec2.VpcVpc;
import com.clouck.model.aws.iam.IamGroup;
import com.clouck.model.aws.iam.IamRole;
import com.clouck.model.aws.iam.IamUser;

public enum ResourceType {
    Ec2_Ami(Ec2Ami.class, true, true, true, "imageId"),
    Ec2_Auto_Scaling_Group(Ec2AutoScalingGroup.class, true, true, true, "autoScalingGroupARN"),
    Ec2_Elastic_IP(Ec2ElasticIP.class, true, true, true, "publicIp"),
    Ec2_Instance(Ec2Instance.class, true, false, true, "instanceId"),
    Ec2_Key_Pair(Ec2KeyPair.class, true, true, true, "keyName"),
    Ec2_Launch_Configuration(Ec2LaunchConfiguration.class, true, true, true, "launchConfigurationARN"),
    Ec2_Load_Balancer(Ec2LoadBalancer.class, true, true, true, "loadBalancerName"),
    Ec2_Network_Interface(Ec2NetworkInterface.class, true, true, true, "networkInterfaceId"),
    Ec2_Placement_Group(Ec2PlacementGroup.class, true, true, true, "groupName"),
    Ec2_Reservation(Ec2Reservation.class, true, true, false, "reservationId"),
    Ec2_Security_Group(Ec2SecurityGroup.class, true, true, true, "groupId"),
    Ec2_Snapshot(Ec2Snapshot.class, true, true, true, "snapshotId"),
    Ec2_Spot_Instance_Request(Ec2SpotInstanceRequest.class, true, true, true, "spotInstanceRequestId"),
    Ec2_Volume(Ec2Volume.class, true, true, true, "volumeId"),
    Iam_Group(IamGroup.class, false, true, true, "groupId"),
    Iam_Role(IamRole.class, false, true, true, "roleId"),
    Iam_User(IamUser.class, false, true, true, "userId"),
    Vpc_DhcpOptions(VpcDhcpOptions.class, true, true, true, "dhcpOptionsId"),
    Vpc_Internet_Gateway(VpcInternetGateway.class, true, true, true, "internetGatewayId"),
    Vpc_NetworkAcl(VpcNetworkAcl.class, true, true, true, "networkAclId"),
    Vpc_Route_Table(VpcRouteTable.class, true, true, true, "routeTableId"),
    Vpc_Subnet(VpcSubnet.class, true, true, true, "subnetId"),
    Vpc_Vpc(VpcVpc.class, true, true, true, "vpcId");

    private Class<? extends AbstractResource<?>> resourceClass;
    private boolean isMultiRegion;
    private boolean isScaningResource;
    private boolean isViewResource;
    private Set<String> uniqueIdNames = new HashSet<>();

    <V extends AbstractResource<?>> ResourceType(Class<V> resourceClass, boolean isMultiRegion,
            boolean isScaningResource, boolean isViewResource, String... uniqueIdNames) {
        this.resourceClass = resourceClass;
        this.isMultiRegion = isMultiRegion;
        this.isScaningResource = isScaningResource;
        this.isViewResource = isViewResource;
        Collections.addAll(this.uniqueIdNames, uniqueIdNames);
    }

    public static List<ResourceType> findScaningResourceTypes() {
        List<ResourceType> rts = new ArrayList<>();
        rts.add(ResourceType.Ec2_Reservation);
        rts.add(ResourceType.Ec2_Ami);
        rts.add(ResourceType.Ec2_Volume);
        rts.add(ResourceType.Ec2_Snapshot);
        rts.add(ResourceType.Ec2_Security_Group);
        rts.add(ResourceType.Ec2_Elastic_IP);
        rts.add(ResourceType.Ec2_Placement_Group);
        rts.add(ResourceType.Ec2_Key_Pair);
        rts.add(ResourceType.Ec2_Spot_Instance_Request);
        rts.add(ResourceType.Ec2_Load_Balancer);
        rts.add(ResourceType.Ec2_Network_Interface);
        return rts;
    }

    public static List<ResourceType> findViewResourceTypes() {
        List<ResourceType> rts = new ArrayList<>();
        rts.add(ResourceType.Ec2_Instance);
        rts.add(ResourceType.Ec2_Ami);
        rts.add(ResourceType.Ec2_Volume);
        rts.add(ResourceType.Ec2_Snapshot);
        rts.add(ResourceType.Ec2_Security_Group);
        rts.add(ResourceType.Ec2_Elastic_IP);
        rts.add(ResourceType.Ec2_Placement_Group);
        rts.add(ResourceType.Ec2_Key_Pair);
        rts.add(ResourceType.Ec2_Spot_Instance_Request);
        rts.add(ResourceType.Ec2_Load_Balancer);
        rts.add(ResourceType.Ec2_Network_Interface);
        return rts;
    }

    public static ResourceType find(AbstractResource<?> resource) {
        for (ResourceType resourceType : values()) {
            if (resourceType.getResourceClass().isInstance(resource)) {
                return resourceType;
            }
        }
        throw new CloudVersionIllegalStateException(resource.toString());
    }

    /**
     * @param variableName e.g. instances
     * @return
     */
    public static ResourceType find(String variableName) {
        for (ResourceType resourceType : values()) {
            if (resourceType.findVariableName().equals(variableName)) {
                return resourceType;
            }
        }
        throw new CloudVersionIllegalStateException("invalid variableName:" + variableName);
    }

    /**
     * change Ec2_Security_Group to securityGroups
     * @return
     */
    public String findVariableName() {
        String whole;
        if (name().startsWith("Ec2")) {
            whole = name().replaceAll("_", "").replaceFirst("Ec2", "");
        } else if (name().startsWith("Vpc")) {
            whole = name().replaceAll("_", "").replaceFirst("Vpc", "");
        } else if (name().startsWith("Iam")) {
            whole = name().replaceAll("_", "").replaceFirst("Iam", "");
        } else {
            throw new CloudVersionIllegalStateException("uncompleted code.");
        }
        return Character.toLowerCase(whole.charAt(0)) + (whole.length() > 1 ? whole.substring(1) : "") + "s";
    }
    
    /**
     * change Ec2_Security_Group to ec2-security-groups
     * @return
     */
    public String findResourcesPage() {
        return findResourcePage() + "s";
    }
    
    /**
     * change Ec2_Security_Group to ec2-security-groups-raw
     * @return
     */
    public String findResourcesRawPage() {
        return findResourcesPage() + "-raw";
    }

    /**
     * change Ec2_Security_Group to ec2-security-group-versions
     * @return
     */
    public String findResourceHistoryPage() {
        return findResourcePage() + "-versions";
    }

    public String findSummaryVariableName() {
        return findVariableName() + "-summary";
    }

    /**
     * change Ec2_Security_Group to ec2-security-group
     * @return
     */
    public String findResourcePage() {
        return name().toLowerCase().replaceAll("_", "-");
    }
    
    /**
     * change Ec2_Security_Group to ec2-security-group-raw
     * @return
     */
    public String findResourceRawPage() {
        return findResourcePage() + "-raw";
    }

    /**
     * change Ec2_Security_Group to ec2-security-groups-versions
     * @return
     */
    public String findResourcesHistoryPage() {
        return findResourcesPage() + "-versions";
    }

    public Class<? extends AbstractResource<?>> getResourceClass() {
        return resourceClass;
    }

    public boolean isMultiRegion() {
        return isMultiRegion;
    }

    public boolean isScaningResource() {
        return isScaningResource;
    }

    public Set<String> getUniqueIdNames() {
        return uniqueIdNames;
    }

    public boolean isViewResource() {
        return isViewResource;
    }
}
