package com.clouck.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.autoscaling.model.AutoScalingGroup;
import com.amazonaws.services.autoscaling.model.LaunchConfiguration;
import com.amazonaws.services.ec2.model.Address;
import com.amazonaws.services.ec2.model.CreateVolumePermission;
import com.amazonaws.services.ec2.model.DhcpOptions;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.amazonaws.services.ec2.model.LaunchPermission;
import com.amazonaws.services.ec2.model.NetworkAcl;
import com.amazonaws.services.ec2.model.NetworkInterface;
import com.amazonaws.services.ec2.model.PlacementGroup;
import com.amazonaws.services.ec2.model.ProductCode;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RouteTable;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.Snapshot;
import com.amazonaws.services.ec2.model.SpotInstanceRequest;
import com.amazonaws.services.ec2.model.Subnet;
import com.amazonaws.services.ec2.model.Volume;
import com.amazonaws.services.ec2.model.Vpc;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.clouck.model.Region;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Ami;
import com.clouck.model.aws.ec2.Ec2AutoScalingGroup;
import com.clouck.model.aws.ec2.Ec2ElasticIP;
import com.clouck.model.aws.ec2.Ec2Instance;
import com.clouck.model.aws.ec2.Ec2InstanceAttribute;
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

@Component
public class Ec2Converter {
    private static final Logger log = LoggerFactory.getLogger(Ec2Converter.class);

    private void conf(AbstractResource<?> resource, String accountId, Region region, DateTime dateTime) {
        resource.setAccountId(accountId);
        resource.setRegion(region);
        resource.setTimeDetected(dateTime.toDate());
    }

    public List<AbstractResource<?>> toEc2SpotInstanceRequest(List<SpotInstanceRequest> sirs, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (SpotInstanceRequest sir : sirs) {
            Ec2SpotInstanceRequest ec2SpotInstanceRequest = new Ec2SpotInstanceRequest();
            conf(ec2SpotInstanceRequest, accountId, region, dt);
            ec2SpotInstanceRequest.setResource(sir);
            resources.add(ec2SpotInstanceRequest);
        }
        log.debug("{} spot instance requests found via api and converted to Ec2SpotInstanceRequest", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2SecurityGroups(List<SecurityGroup> sgs, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (SecurityGroup sg : sgs) {
            Ec2SecurityGroup ec2SecurityGroup = new Ec2SecurityGroup();
            conf(ec2SecurityGroup, accountId, region, dt);
            ec2SecurityGroup.setResource(sg);
            resources.add(ec2SecurityGroup);
        }
        log.debug("{} security groups found via api and converted to Ec2SecurityGroup", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2Reservations(List<Reservation> reservations, List<Map<String, Ec2InstanceAttribute>> attributes,
            String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (int i = 0; i < reservations.size(); i++) {
            Ec2Reservation ec2Reservation = new Ec2Reservation();
            conf(ec2Reservation, accountId, region, dt);
            ec2Reservation.setResource(reservations.get(i));
            ec2Reservation.setInstanceId2Attributes(attributes.get(i));
            resources.add(ec2Reservation);
        }
        log.debug("{} reservations found via api and converted to Ec2Reservation", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2AMIs(List<Image> images, List<List<LaunchPermission>> launchPermissions, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            Ec2Ami ec2Ami = new Ec2Ami();
            conf(ec2Ami, accountId, region, dt);
            ec2Ami.setResource(images.get(i));
            ec2Ami.setLaunchPermissions(launchPermissions.get(i));
            resources.add(ec2Ami);
        }
        log.debug("{} amis found via api and converted to Ec2Ami", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2Volumes(List<Volume> volumes, List<Boolean> autoEnableIOs, List<List<ProductCode>> productCodes, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (int i = 0; i < volumes.size(); i++) {
            Ec2Volume ec2Volume = new Ec2Volume();
            ec2Volume.setAutoEnableIO(autoEnableIOs.get(i));
            ec2Volume.setProductCodes(productCodes.get(i));
            conf(ec2Volume, accountId, region, dt);
            ec2Volume.setResource(volumes.get(i));
            resources.add(ec2Volume);
        }
        log.debug("{} volumes found via api and converted to Ec2Volume", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2InstancesFromEc2Reservations(List<AbstractResource<?>> ec2Rs, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (AbstractResource<?> abstractResource : ec2Rs) {
            Ec2Reservation ec2Reservation = (Ec2Reservation) abstractResource;
            Reservation reservation = ec2Reservation.getResource();
            List<Instance> instances = reservation.getInstances();
            resources.addAll(toEc2Instances(instances, accountId, region, dt, ec2Reservation.getInstanceId2Attributes(), reservation.getReservationId()));
        }
        log.debug("{} instances found in Ec2Reservations and converted to Ec2Instance", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2Instances(List<Instance> instances, String accountId, Region region,
            DateTime dt, Map<String, Ec2InstanceAttribute> instanceId2Attributes, String reservationId) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (Instance instance : instances) {
            Ec2Instance ec2Instance = new Ec2Instance();
            ec2Instance.setTerminationProtection(instanceId2Attributes.get(instance.getInstanceId()).getTerminationProtection());
            ec2Instance.setShutdownBehavior(instanceId2Attributes.get(instance.getInstanceId()).getShutdownBehavior());
            ec2Instance.setUserData(instanceId2Attributes.get(instance.getInstanceId()).getUserData());
            ec2Instance.setReservationId(reservationId);
            conf(ec2Instance, accountId, region, dt);
            ec2Instance.setResource(instance);
            resources.add(ec2Instance);
        }
        log.debug("{} instances converted to Ec2Instance", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2Snapshots(List<Snapshot> snapshots, List<List<CreateVolumePermission>> createVolumePermissions, List<List<ProductCode>> productCodes, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (int i = 0; i < snapshots.size(); i++) {
            Ec2Snapshot ec2Snapshot = new Ec2Snapshot();
            conf(ec2Snapshot, accountId, region, dt);
            ec2Snapshot.setResource(snapshots.get(i));
            ec2Snapshot.setCreateVolumePermissions(createVolumePermissions.get(i));
            ec2Snapshot.setProductCodes(productCodes.get(i));
            resources.add(ec2Snapshot);
        }
        log.debug("{} snapshots found via api and converted to Ec2Snapshot", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2KeyPairs(List<KeyPairInfo> keyPairs, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (KeyPairInfo keyPairInfo : keyPairs) {
            Ec2KeyPair ec2KeyPair = new Ec2KeyPair();
            conf(ec2KeyPair, accountId, region, dt);
            ec2KeyPair.setResource(keyPairInfo);
            resources.add(ec2KeyPair);
        }
        log.debug("{} key pairs found via api and converted to Ec2KeyPair", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2ElasticIPs(List<Address> addresses, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (Address address : addresses) {
            Ec2ElasticIP ec2ElasticIP = new Ec2ElasticIP();
            conf(ec2ElasticIP, accountId, region, dt);
            ec2ElasticIP.setResource(address);
            resources.add(ec2ElasticIP);
        }
        log.debug("{} elastic ips found via api and converted to Ec2ElasticIP", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2PlacementGroups(List<PlacementGroup> placementGroups, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (PlacementGroup placementGroup : placementGroups) {
            Ec2PlacementGroup ec2PlacementGroup = new Ec2PlacementGroup();
            conf(ec2PlacementGroup, accountId, region, dt);
            ec2PlacementGroup.setResource(placementGroup);
            resources.add(ec2PlacementGroup);
        }
        log.debug("{} placement groups found via api and converted to Ec2PlacementGroup", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toEc2NetworkInterfaces(List<NetworkInterface> networkInterfaces, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (NetworkInterface networkInterface : networkInterfaces) {
            Ec2NetworkInterface ec2NetworkInterface = new Ec2NetworkInterface();
            conf(ec2NetworkInterface, accountId, region, dt);
            ec2NetworkInterface.setResource(networkInterface);
            resources.add(ec2NetworkInterface);
        }
        log.debug("{} network interfaces found via api and converted to Ec2NetworkInterface", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toVpcVpcs(List<Vpc> vpcs, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (Vpc vpc : vpcs) {
            VpcVpc vpcVpc = new VpcVpc();
            conf(vpcVpc, accountId, region, dt);
            vpcVpc.setResource(vpc);
            resources.add(vpcVpc);
        }
        log.debug("{} vpcs found via api and converted to VpcVpc", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toVpcSubnets(List<Subnet> subnets, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (Subnet subnet : subnets) {
            VpcSubnet vpcSubnet = new VpcSubnet();
            conf(vpcSubnet, accountId, region, dt);
            vpcSubnet.setResource(subnet);
            resources.add(vpcSubnet);
        }
        log.debug("{} subnets found via api and converted to VpcSubnet", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toVpcRouteTables(List<RouteTable> routeTables, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (RouteTable routeTable : routeTables) {
            VpcRouteTable vpcRouteTable = new VpcRouteTable();
            conf(vpcRouteTable, accountId, region, dt);
            vpcRouteTable.setResource(routeTable);
            resources.add(vpcRouteTable);
        }
        log.debug("{} route tables found via api and converted to VpcRouteTable", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toVpcInternetGateways(List<InternetGateway> internetGateways, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (InternetGateway internetGateway : internetGateways) {
            VpcInternetGateway vpcInternetGateway = new VpcInternetGateway();
            conf(vpcInternetGateway, accountId, region, dt);
            vpcInternetGateway.setResource(internetGateway);
            resources.add(vpcInternetGateway);
        }
        log.debug("{} internet gateways found via api and converted to VpcInternetGateway", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toVpcDhcpOptions(List<DhcpOptions> dhcpOptionses, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (DhcpOptions dhcpOptions : dhcpOptionses) {
            VpcDhcpOptions vpcDpcDhcpOptions = new VpcDhcpOptions();
            conf(vpcDpcDhcpOptions, accountId, region, dt);
            vpcDpcDhcpOptions.setResource(dhcpOptions);
            resources.add(vpcDpcDhcpOptions);
        }
        log.debug("{} internet gateways found via api and converted to VpcInternetGateway", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toVpcNetworkAcls(List<NetworkAcl> networkAcls, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (NetworkAcl networkAcl : networkAcls) {
            VpcNetworkAcl vpcNetworkAcl = new VpcNetworkAcl();
            conf(vpcNetworkAcl, accountId, region, dt);
            vpcNetworkAcl.setResource(networkAcl);
            resources.add(vpcNetworkAcl);
        }
        log.debug("{} network acls found via api and converted to VpcNetworkAcl", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toAsLaunchConfigurations(List<LaunchConfiguration> launchConfigurations, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (LaunchConfiguration launchConfiguration : launchConfigurations) {
            Ec2LaunchConfiguration ec2LaunchConfiguration = new Ec2LaunchConfiguration();
            conf(ec2LaunchConfiguration, accountId, region, dt);
            ec2LaunchConfiguration.setResource(launchConfiguration);
            resources.add(ec2LaunchConfiguration);
        }
        log.debug("{} launch configurations found via api and converted to AsLaunchConfiguration", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toAsGroups(List<AutoScalingGroup> autoScalingGroups, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (AutoScalingGroup autoScalingGroup : autoScalingGroups) {
            Ec2AutoScalingGroup ec2AutoScalingGroup = new Ec2AutoScalingGroup();
            conf(ec2AutoScalingGroup, accountId, region, dt);
            ec2AutoScalingGroup.setResource(autoScalingGroup);
            resources.add(ec2AutoScalingGroup);
        }
        log.debug("{} auto scaling groups found via api and converted to Ec2AutoScalingGroup", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toLoadBalancers(List<LoadBalancerDescription> loadBalancerDescriptions, String accountId, Region region, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (LoadBalancerDescription loadBalancerDescription : loadBalancerDescriptions) {
            Ec2LoadBalancer ec2LoadBalancer = new Ec2LoadBalancer();
            conf(ec2LoadBalancer, accountId, region, dt);
            ec2LoadBalancer.setResource(loadBalancerDescription);
            resources.add(ec2LoadBalancer);
        }
        log.debug("{} load balancers found via api and converted to Ec2LoadBalancer", resources.size());
        return resources;
    }
}
