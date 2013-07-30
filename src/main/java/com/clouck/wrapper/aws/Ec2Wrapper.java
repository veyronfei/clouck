package com.clouck.wrapper.aws;

import java.util.List;

import org.joda.time.DateTime;

import com.amazonaws.services.ec2.model.CreateVolumePermission;
import com.amazonaws.services.ec2.model.LaunchPermission;
import com.amazonaws.services.ec2.model.ProductCode;
import com.clouck.application.Ec2Filter;
import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.aws.AbstractResource;

public interface Ec2Wrapper {

    List<AbstractResource<?>> describeReservations(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeSecurityGroups(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeAMIs(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeVolumes(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeSnapshots(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeKeyPairs(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeElasticIPs(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describePlacementGroups(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeNetworkInterfaces(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeVpcs(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeSubnets(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeRouteTables(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeInternetGateways(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeDhcpOptions(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeNetworkAcls(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<AbstractResource<?>> describeSpotInstanceRequests(Account account, Region region, DateTime dt, Ec2Filter... filters);

    List<LaunchPermission> findImageLaunchPermissions(Account account, Region region, String imageId);

    Boolean findTerminationProtection(Account account, Region region, String instanceId);

    String findShutdownBehavior(Account account, Region region, String instanceId);

    String findUserData(Account account, Region region, String instanceId);

    List<CreateVolumePermission> findCreateVolumePermissions(Account account, Region region, String snapshotId);

    Boolean findAutoEnableIO(Account account, Region region, String volumeId);

    List<ProductCode> findProductCodes(Account account, Region region, String volumeId);

    List<ProductCode> findSnapshotProductCodes(Account account, Region region, String snapshotId);
}
