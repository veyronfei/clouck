package com.clouck.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import com.amazonaws.services.autoscaling.model.AutoScalingGroup;
import com.amazonaws.services.autoscaling.model.LaunchConfiguration;
import com.amazonaws.services.ec2.model.Address;
import com.amazonaws.services.ec2.model.DhcpOptions;
import com.amazonaws.services.ec2.model.GroupIdentifier;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.InternetGatewayAttachment;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.amazonaws.services.ec2.model.NetworkAcl;
import com.amazonaws.services.ec2.model.NetworkAclAssociation;
import com.amazonaws.services.ec2.model.NetworkInterface;
import com.amazonaws.services.ec2.model.NetworkInterfacePrivateIpAddress;
import com.amazonaws.services.ec2.model.PlacementGroup;
import com.amazonaws.services.ec2.model.RouteTable;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.Snapshot;
import com.amazonaws.services.ec2.model.Subnet;
import com.amazonaws.services.ec2.model.Volume;
import com.amazonaws.services.ec2.model.Vpc;
import com.amazonaws.services.identitymanagement.model.Group;
import com.amazonaws.services.identitymanagement.model.Role;
import com.amazonaws.services.identitymanagement.model.User;
import com.clouck.application.Ec2Constants;
import com.clouck.exception.CloudVersionIllegalStateException;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Ami;
import com.clouck.model.aws.ec2.Ec2AutoScalingGroup;
import com.clouck.model.aws.ec2.Ec2ElasticIP;
import com.clouck.model.aws.ec2.Ec2Instance;
import com.clouck.model.aws.ec2.Ec2KeyPair;
import com.clouck.model.aws.ec2.Ec2LaunchConfiguration;
import com.clouck.model.aws.ec2.Ec2NetworkInterface;
import com.clouck.model.aws.ec2.Ec2PlacementGroup;
import com.clouck.model.aws.ec2.Ec2SecurityGroup;
import com.clouck.model.aws.ec2.Ec2Snapshot;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.model.aws.ec2.Ec2VersionMeta;
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
import com.clouck.rep.ChartDataRep;
import com.clouck.rep.ChartDataSeriesRep;
import com.clouck.rep.DataTableRep;
import com.clouck.rep.Ec2ResourceRep;
import com.clouck.rep.Ec2VersionMetaRep;
import com.clouck.rep.EventRep;
import com.clouck.rep.HighChartsDataRep;
import com.clouck.rep.HighChartsRep;
import com.clouck.rep.HighChartsSeriesRep;
import com.clouck.service.AwsService;
import com.clouck.wrapper.spring.MRWrapper;

@Component
public class RepConverter {
    private static final Logger log = LoggerFactory.getLogger(RepConverter.class);

    @Autowired
    private ConversionService conversionService;
    @Autowired
    private AwsService awsService;
    @Autowired
    private MRWrapper mrWrapper;


    public <T> T convert(Object source, Class<T> targetType) {
        return conversionService.convert(source, targetType);
    }

    public List<Ec2VersionMetaRep> toEc2VersionMetaReps(List<Ec2VersionMeta> ec2VersionMetas) {
        List<Ec2VersionMetaRep> result = new ArrayList<>();
        for (Ec2VersionMeta meta : ec2VersionMetas) {
            result.add(toEc2VersionMetaRep(meta));
        }
        return result;
    }

    public Ec2VersionMetaRep toEc2VersionMetaRep(Ec2VersionMeta ec2VersionMeta) {
        Ec2VersionMetaRep result = new Ec2VersionMetaRep();
        result.setTimeDetected(new DateTime(ec2VersionMeta.getTimeDetected()).toLocalDateTime().toString(Ec2Constants.Date_Time_Format));
        result.setMillis(ec2VersionMeta.getTimeDetected().getTime());
        result.setRegion(ec2VersionMeta.getRegion());
        List<EventRep> reps = new ArrayList<>();
        for (Event event : ec2VersionMeta.getEvents()) {
            EventRep rep = new EventRep();
            EventType et = event.getEventType();
            String message = mrWrapper.getEventMessage(et, event);
            rep.setMessage(message);
            reps.add(rep);
        }
        result.setReps(reps);
        return result;
    }


    public List<Ec2ResourceRep> toEc2ResourceReps(List<Ec2VersionMeta> ec2VersionMetas, String uniqueId) {
        List<Ec2ResourceRep> result = new ArrayList<>();
        for (Ec2VersionMeta ec2VersionMeta : ec2VersionMetas) {
            result.add(toEc2ResourceRep(ec2VersionMeta, uniqueId));
        }
        return result;
    }

    public Ec2ResourceRep toEc2ResourceRep(Ec2VersionMeta ec2VersionMeta, String uniqueId) {
        Ec2ResourceRep result = new Ec2ResourceRep();
        result.setTimeDetected(new DateTime(ec2VersionMeta.getTimeDetected()).toLocalDateTime().toString(Ec2Constants.Date_Time_Format));
        result.setRegion(ec2VersionMeta.getRegion());
        String resourceId = null;
        for (Event event : ec2VersionMeta.getEvents()) {
            if (event.getUniqueId() != null && event.getUniqueId().equals(uniqueId)) {
                EventRep rep = new EventRep();
                resourceId = event.getNewResourceId();
                EventType et = event.getEventType();
                String message = mrWrapper.getEventMessage(et, event);
                rep.setMessage(message);
                result.getReps().add(rep);
            }
        }
        result.setResourceId(resourceId);
        return result;
    }
    
//    public List<EventRep> toEventReps(List<Event> events) {
//        List<EventRep> result = new ArrayList<>();
//        for (Event event : events) {
//            result.add(toEventRep(event));
//        }
//        return result;
//    }

//    public EventRep toEventRep(Event event) {
//        EventRep result = new EventRep();
//        result.setUniqueId(event.getUniqueId());
//        result.setResourceId(event.getResourceId());
//        return result;
//    }
//
//    public ResourceSizeRep toResourceSizeRep(int instanceSize, int securityGroupSize, int snapshotSize, int imageSize, int volumeSize) {
//        ResourceSizeRep res = new ResourceSizeRep();
//        res.setInstance_size(instanceSize);
//        res.setSecurity_group_size(securityGroupSize);
//        res.setSnapshot_size(snapshotSize);
//        res.setVolume_size(volumeSize);
//        return res;
//    }

    public HighChartsRep toHigCharts(ResourceType resourceType, List<Ec2Version> ec2Versions) {
        HighChartsRep result = new HighChartsRep();
        
        if (resourceType.isMultiRegion()) {
            Map<Region, HighChartsSeriesRep> region2Series = new HashMap<>();
            for (Region region : Region.findAvailableRegions(resourceType)) {
                HighChartsSeriesRep rep = new HighChartsSeriesRep();
                rep.setName(region.toString());
                region2Series.put(region, rep);
            }
            
            for (int i = 0; i < ec2Versions.size(); i++) {
                Ec2Version ec2Version = ec2Versions.get(i);
                if (i != 0) {
                    HighChartsSeriesRep seriesRep = region2Series.get(ec2Version.getRegion());
                    if (ec2Version.getResourceIds().size() != 0) {
                        List<HighChartsDataRep> dataReps = seriesRep.getData();
                        dataReps.set(0, new HighChartsDataRep(ec2Version.getResourceIds().size(), ec2Version.getId(), ec2Version.getTimeDetected().getTime()));
                    }
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM kk:mm");
                    result.addCategory(sdf.format(ec2Version.getTimeDetected()));
                    HighChartsSeriesRep seriesRep = region2Series.get(ec2Version.getRegion());
                    if (ec2Version.getResourceIds().size() == 0) {
                        seriesRep.addData(new HighChartsDataRep(null, ec2Version.getId(), ec2Version.getTimeDetected().getTime()));
                    } else {
                        seriesRep.addData(new HighChartsDataRep(ec2Version.getResourceIds().size(), ec2Version.getId(), ec2Version.getTimeDetected().getTime()));
                    }
                    for (Region region : Region.findAvailableRegions(resourceType)) {
                        if (!region.equals(ec2Version.getRegion())) {
                            HighChartsSeriesRep otherSeriesRep = region2Series.get(region);
                            List<HighChartsDataRep> dataReps = otherSeriesRep.getData();
                            if (dataReps.size() == 0) {
                                otherSeriesRep.addData(new HighChartsDataRep(null, ec2Version.getId(), ec2Version.getTimeDetected().getTime()));
                            } else {
                                HighChartsDataRep dataRep = dataReps.get(dataReps.size() - 1);
                                otherSeriesRep.addData(dataRep.clone(ec2Version.getTimeDetected().getTime()));
                            }
                        }
                    }
                }
            }
            // remove items which are not in this page
            int size = result.getCategories().size();
            if (size > Ec2Constants.High_Charts_Page_Size) {
                List<String> categories = result.getCategories();
                result.setCategories(categories.subList(size - Ec2Constants.High_Charts_Page_Size, size));
                for (HighChartsSeriesRep rep : region2Series.values()) {
                    rep.setData(rep.getData().subList(size - Ec2Constants.High_Charts_Page_Size, size));
                    result.addSerie(rep);
                }
            } else {
                for (HighChartsSeriesRep rep : region2Series.values()) {
                    result.addSerie(rep);
                }
            }
        } else {
            HighChartsSeriesRep rep = new HighChartsSeriesRep();
            //TODO: XXX
            rep.setName("what is this?");
            for (int i = 0; i < ec2Versions.size(); i++) {
                Ec2Version ec2Version = ec2Versions.get(i);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM kk:mm");
                result.addCategory(sdf.format(ec2Version.getTimeDetected()));
                if (ec2Version.getResourceIds().size() == 0) {
                    rep.addData(new HighChartsDataRep(null, ec2Version.getId(), ec2Version.getTimeDetected().getTime()));
                } else {
                    rep.addData(new HighChartsDataRep(ec2Version.getResourceIds().size(), ec2Version.getId(), ec2Version.getTimeDetected().getTime()));
                }
            }
            // remove items which are not in this page
            int size = result.getCategories().size();
            if (size > Ec2Constants.High_Charts_Page_Size) {
                List<String> categories = result.getCategories();
                result.setCategories(categories.subList(size - Ec2Constants.High_Charts_Page_Size, size));
                rep.setData(rep.getData().subList(size - Ec2Constants.High_Charts_Page_Size, size));
                result.addSerie(rep);
            } else {
                result.addSerie(rep);
            }
        }
        return result;
    }

    public DataTableRep toDataTableData(Collection<AbstractResource<?>> resources) {
        DataTableRep result = new DataTableRep();
        for (AbstractResource<?> resource : resources) {
            result.addRow(createRow(resource));
        }
        return result;
    }

    //TODO: I need to move this to a more proper json format intead of table format.
    private List<String> createRow(AbstractResource<?> resource) {
        List<String> result = new ArrayList<>();
        ResourceType rt = ResourceType.find(resource);
        switch (rt) {
        case Ec2_Reservation:
            Ec2Instance ec2Instance = (Ec2Instance) resource;
            Instance instance = ec2Instance.getResource();
            result.add(resource.getRegion().toString());
            result.add(instance.getInstanceId());
            result.add(instance.getState().getName());
            result.add(instance.getPublicDnsName());
            result.add(instance.getPrivateIpAddress());
            break;
        case Ec2_Security_Group:
            Ec2SecurityGroup ec2SecurityGroup = (Ec2SecurityGroup) resource;
            SecurityGroup securityGroup = ec2SecurityGroup.getResource();
            result.add(resource.getRegion().toString());
            result.add(securityGroup.getGroupId());
            result.add(securityGroup.getGroupName());
            result.add(securityGroup.getDescription());
            result.add(securityGroup.getVpcId());
            break;
        case Ec2_Ami:
            Ec2Ami ec2Ami = (Ec2Ami) resource;
            Image image = ec2Ami.getResource();
            result.add(resource.getRegion().toString());
            result.add(image.getImageId());
            result.add(image.getName());
            result.add(image.getState().toString());
            result.add(image.getArchitecture());
            result.add(image.getPlatform());
            result.add(image.getDescription());
            result.add(image.getRootDeviceType());
            result.add(image.getHypervisor());
            break;
        case Ec2_Volume:
            Ec2Volume ec2Volume = (Ec2Volume) resource;
            Volume volume = ec2Volume.getResource();
            result.add(resource.getRegion().toString());
            result.add(volume.getVolumeId());
            result.add(volume.getSize().toString() + " GiB");
            result.add(volume.getAvailabilityZone());
            result.add(volume.getState().toString());
            result.add(volume.getCreateTime().toString());
            result.add(volume.getVolumeType());
            break;
        case Ec2_Snapshot:
            Ec2Snapshot ec2Snapshot = (Ec2Snapshot) resource;
            Snapshot snapshot = ec2Snapshot.getResource();
            result.add(resource.getRegion().toString());
            result.add(snapshot.getSnapshotId());
            result.add(snapshot.getDescription());
            result.add(snapshot.getStartTime().toString());
            result.add(snapshot.getVolumeSize().toString() + " GiB");
            break;
        case Ec2_Key_Pair:
            Ec2KeyPair ec2KeyPair = (Ec2KeyPair) resource;
            KeyPairInfo keyPair = ec2KeyPair.getResource();
            result.add(resource.getRegion().toString());
            result.add(keyPair.getKeyName());
            result.add(keyPair.getKeyFingerprint());
            break;
        case Ec2_Elastic_IP:
            Ec2ElasticIP ec2ElasticIP = (Ec2ElasticIP) resource;
            Address address = ec2ElasticIP.getResource();
            result.add(resource.getRegion().toString());
            result.add(address.getPublicIp());
            result.add(address.getInstanceId());
            result.add(address.getNetworkInterfaceId());
            break;
        case Ec2_Placement_Group:
            Ec2PlacementGroup ec2PlacementGroup = (Ec2PlacementGroup) resource;
            PlacementGroup placementGroup = ec2PlacementGroup.getResource();
            result.add(resource.getRegion().toString());
            result.add(placementGroup.getGroupName());
            result.add(placementGroup.getStrategy());
            result.add(placementGroup.getState());
            break;
        case Ec2_Network_Interface:
            Ec2NetworkInterface ec2NetworkInterface = (Ec2NetworkInterface) resource;
            NetworkInterface networkInterface = ec2NetworkInterface.getResource();
            result.add(resource.getRegion().toString());
            result.add(networkInterface.getNetworkInterfaceId());
            result.add(networkInterface.getSubnetId());
            result.add(networkInterface.getAvailabilityZone());
            StringBuilder sb = new StringBuilder();
            List<GroupIdentifier> groups = networkInterface.getGroups();
            for (int i = 0; i < groups.size(); i++) {
                if (i != groups.size() - 1) {
                    sb.append(groups.get(i).getGroupName() + ", ");
                } else {
                    sb.append(groups.get(i).getGroupName());
                }
            }
            result.add(sb.toString());
            result.add(networkInterface.getDescription());
            if (networkInterface.getAttachment() != null) {
                result.add(networkInterface.getAttachment().getInstanceId());
            } else {
                result.add("");
            }
            result.add(networkInterface.getStatus());
            if (networkInterface.getAssociation() != null) {
                result.add(networkInterface.getAssociation().getPublicIp());
            } else {
                result.add("");
            }
            result.add(networkInterface.getPrivateIpAddress());
            List<NetworkInterfacePrivateIpAddress> privateIpAddresses = networkInterface.getPrivateIpAddresses();
            List<NetworkInterfacePrivateIpAddress> secondaryAddresses = new ArrayList<>();
            for (NetworkInterfacePrivateIpAddress privateIpAddress : privateIpAddresses) {
                if (!privateIpAddress.getPrimary()) {
                    secondaryAddresses.add(privateIpAddress);
                }
            }
            StringBuilder sb1 = new StringBuilder();
            for (int i = 0; i < secondaryAddresses.size(); i++) {
                if (i != secondaryAddresses.size() - 1) {
                    sb1.append(secondaryAddresses.get(i).getPrivateIpAddress() + ", ");
                } else {
                    sb1.append(secondaryAddresses.get(i).getPrivateIpAddress());
                }
            }
            result.add(sb1.toString());
            break;
        case Ec2_Launch_Configuration:
            Ec2LaunchConfiguration ec2LaunchConf = (Ec2LaunchConfiguration) resource;
            LaunchConfiguration launchConf = ec2LaunchConf.getResource();
            result.add(resource.getRegion().toString());
            result.add(launchConf.getImageId());
            break;
        case Ec2_Auto_Scaling_Group:
            Ec2AutoScalingGroup ec2AutoScalingGroup = (Ec2AutoScalingGroup) resource;
            AutoScalingGroup autoScalingGroup = ec2AutoScalingGroup.getResource();
            result.add(resource.getRegion().toString());
            result.add(autoScalingGroup.getAutoScalingGroupName());
            break;
        case Vpc_Vpc:
            VpcVpc vpcVpc = (VpcVpc) resource;
            Vpc vpc = vpcVpc.getResource();
            result.add(resource.getRegion().toString());
            result.add(vpc.getVpcId());
            result.add(vpc.getState());
            result.add(vpc.getCidrBlock());
            result.add(vpc.getDhcpOptionsId());
            result.add(vpc.getInstanceTenancy());
            //TODO: need to add main route table, default network acl.
            break;
        case Vpc_Subnet:
            VpcSubnet vpcSubnet = (VpcSubnet) resource;
            Subnet subnet = vpcSubnet.getResource();
            result.add(resource.getRegion().toString());
            result.add(subnet.getSubnetId());
            result.add(subnet.getState());
            result.add(subnet.getVpcId());
            result.add(subnet.getCidrBlock());
            result.add(subnet.getAvailableIpAddressCount().toString());
            result.add(subnet.getAvailabilityZone());
            //TODO: Add route table, network acl
            break;
        case Vpc_Route_Table:
            VpcRouteTable vpcRouteTable = (VpcRouteTable) resource;
            RouteTable routeTable = vpcRouteTable.getResource();
            result.add(resource.getRegion().toString());
            result.add(routeTable.getRouteTableId());
            result.add(routeTable.getVpcId());
          //TODO: need to add others
            break;
        case Vpc_Internet_Gateway:
            VpcInternetGateway vpcInternetGateway = (VpcInternetGateway) resource;
            InternetGateway internetGateway = vpcInternetGateway.getResource();
            result.add(resource.getRegion().toString());
            result.add(internetGateway.getInternetGatewayId());
            List<InternetGatewayAttachment> attachments = internetGateway.getAttachments();
            if (attachments.size() == 0) {
                result.add("");
                result.add("");
            } else if (attachments.size() == 1) {
                result.add(attachments.get(0).getState());
                result.add(attachments.get(0).getVpcId());
            } else {
                throw new CloudVersionIllegalStateException("internet gateway should at most have 1 attachment, but we've got " + attachments.size());
            }
            break;
        case Vpc_DhcpOptions:
            VpcDhcpOptions vpcDhcpOptions = (VpcDhcpOptions) resource;
            DhcpOptions dhcpOptions = vpcDhcpOptions.getResource();
            result.add(resource.getRegion().toString());
            result.add(dhcpOptions.getDhcpOptionsId());
            //TODO: ADD Options
            break;
        case Vpc_NetworkAcl:
            VpcNetworkAcl vpcNetworkAcl = (VpcNetworkAcl) resource;
            NetworkAcl networkAcl = vpcNetworkAcl.getResource();
            result.add(resource.getRegion().toString());
            result.add(networkAcl.getNetworkAclId());
            List<NetworkAclAssociation> associations = networkAcl.getAssociations();
            result.add(associations.size() + " Subnets");
            result.add(networkAcl.getIsDefault() ? "Yes" : "No");
            result.add(networkAcl.getVpcId());
            break;
        case Iam_Group:
            IamGroup iamGroup = (IamGroup) resource;
            Group group = iamGroup.getResource();
            result.add(group.getGroupName());
            result.add(group.getCreateDate().toString());
            break;
        case Iam_User:
            IamUser iamUser = (IamUser) resource;
            User user = iamUser.getResource();
            result.add(user.getUserName());
            result.add(user.getCreateDate().toString());
            break;
        case Iam_Role:
            IamRole iamRole = (IamRole) resource;
            Role role = iamRole.getResource();
            result.add(role.getRoleName());
            result.add(role.getCreateDate().toString());
            break;
        default:
            throw new CloudVersionIllegalStateException("unhandled resource type:" + rt);
        }
        result.add(resource.getTimeDetected().toString());
        return result;
    }

//    private Long calculateTotal(Map<Region, Long> region2Num) {
//        Long total = 0L;
//        for (Region key : region2Num.keySet()) {
//            Long num = region2Num.get(key);
//            if (num != null) {
//                total += num;
//            }
//        }
//        return total;
//    }

    private List<Long> createPoint(Long millis, Long size) {
        List<Long> point = new ArrayList<>();
        point.add(millis);
        point.add(size);
        return point;
    }

    public ChartDataRep toChartData(ResourceType resourceType, List<Ec2Version> ec2Versions) {
        ChartDataRep result = new ChartDataRep();
        ChartDataSeriesRep srep = new ChartDataSeriesRep();
        result.addSerie(srep);

        int size = 0;
        for (Ec2Version ec2Version : ec2Versions) {
            size = ec2Version.getResourceIds().size();
            srep.addData(createPoint(ec2Version.getTimeDetected().getTime(), Long.valueOf(size)));
        }
//        for (int i = 0; i < ec2Versions.size(); i++) {
//            region2Num.put(ec2Versions.get(i).getRegion(), Long.valueOf(ec2Versions.get(i).getResourceIds().size()));
//            if (!ec2Versions.get(i).isFirstTimeScan()
//                    || (ec2Versions.get(i).isFirstTimeScan()
//                            && region2Num.keySet().size() == Region.findAvailableRegions(resourceType).size())) {
//                srep.addData(createPoint(ec2Versions.get(i), region2Num));
//            }
//        }
        return result;
    }

    public DataTableRep toDataTableData(String accountId, String resourceType, Region region,
            List<Ec2VersionMeta> ec2VersionMetas, Integer sEcho,
            long totalNumEc2VersionMetas, long numOfFilteredEc2VersionMetas,
            String ctx) {
        DataTableRep result = new DataTableRep();
        result.setiTotalRecords((int)totalNumEc2VersionMetas);
        result.setiTotalDisplayRecords((int)numOfFilteredEc2VersionMetas);
        result.setsEcho(sEcho);

        List<List<String>> aaData = new ArrayList<>();
        for (Ec2VersionMeta meta : ec2VersionMetas) {
            List<String> row = new ArrayList<>();
            row.add(meta.getRegion().name());
            row.add(new DateTime(meta.getTimeDetected()).toString(Ec2Constants.Date_Time_Format));
            StringBuilder sb = new StringBuilder();
            List<Event> events = meta.getEvents();
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                EventType et = event.getEventType();
                String message = mrWrapper.getEventMessage(et, event);
                if (i != events.size() - 1) {
                    sb.append(message + "<br>");
                } else {
                    sb.append(message);
                }
            }
            row.add(sb.toString());
            sb = new StringBuilder();
            sb.append("<a class=\"btn\" href=\""+ ctx + "/accounts/");
            sb.append(accountId);
            sb.append("/ec2/");
            sb.append(resourceType);
            sb.append("?at=");
            sb.append(meta.getTimeDetected().getTime());
            if (!region.equals(Region.All)) {
                sb.append("&region=");
                sb.append(region.getRegions().getName());
            }
            sb.append("\">Browse</a>");
            row.add(sb.toString());
            aaData.add(row);
        }
        result.setAaData(aaData);
        return result;
    }

    public List<Object> toResources(List<AbstractResource<?>> ec2Resources) {
        List<Object> result = new ArrayList<>();
        for (AbstractResource<?> resource : ec2Resources) {
            result.add(resource.getResource());
        }
        return result;
    }
}
