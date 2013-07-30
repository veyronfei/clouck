package com.clouck.model.aws.ec2;

import static com.clouck.util.ResourceUtil.notEqual;
import static com.clouck.util.ResourceUtil.notEqualCollection;

import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.elasticloadbalancing.model.BackendServerDescription;
import com.amazonaws.services.elasticloadbalancing.model.ListenerDescription;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.amazonaws.services.elasticloadbalancing.model.Policies;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_load_balancer")
@TypeAlias(value = "ec2_load_balancer")
@SuppressWarnings("serial")
public class Ec2LoadBalancer extends AbstractResource<LoadBalancerDescription> {

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        LoadBalancerDescription oldLoadBalancer = this.getResource();
        Ec2LoadBalancer newEc2LoadBalancer = (Ec2LoadBalancer) newResource;
        LoadBalancerDescription newLoadBalancer = newEc2LoadBalancer.getResource();

        if (notEqual(oldLoadBalancer.getLoadBalancerName(), newLoadBalancer.getLoadBalancerName())) return false;
        if (notEqual(oldLoadBalancer.getDNSName(), newLoadBalancer.getDNSName())) return false;
        if (notEqual(oldLoadBalancer.getCanonicalHostedZoneName(), newLoadBalancer.getCanonicalHostedZoneName())) return false;
        if (notEqual(oldLoadBalancer.getCanonicalHostedZoneNameID(), newLoadBalancer.getCanonicalHostedZoneNameID())) return false;
        if (notEqualListenerDescriptions(oldLoadBalancer.getListenerDescriptions(), newLoadBalancer.getListenerDescriptions())) return false;
        Policies oldPolicies = oldLoadBalancer.getPolicies();
        Policies newPolicies = newLoadBalancer.getPolicies();
        if (notEqualCollection(oldPolicies.getAppCookieStickinessPolicies(), newPolicies.getAppCookieStickinessPolicies())) return false;
        if (notEqualCollection(oldPolicies.getLBCookieStickinessPolicies(), newPolicies.getLBCookieStickinessPolicies())) return false;
        if (notEqualCollection(oldPolicies.getOtherPolicies(), newPolicies.getOtherPolicies())) return false;
        if (notBackendServerDescriptions(oldLoadBalancer.getBackendServerDescriptions(), newLoadBalancer.getBackendServerDescriptions())) return false;
        if (notEqualCollection(oldLoadBalancer.getAvailabilityZones(), newLoadBalancer.getAvailabilityZones())) return false;
        if (notEqualCollection(oldLoadBalancer.getSubnets(), newLoadBalancer.getSubnets())) return false;
        if (notEqual(oldLoadBalancer.getVPCId(), newLoadBalancer.getVPCId())) return false;
        if (notEqualCollection(oldLoadBalancer.getInstances(), newLoadBalancer.getInstances())) return false;
        if (notEqual(oldLoadBalancer.getHealthCheck(), newLoadBalancer.getHealthCheck())) return false;
        if (notEqual(oldLoadBalancer.getSourceSecurityGroup(), newLoadBalancer.getSourceSecurityGroup())) return false;
        if (notEqualCollection(oldLoadBalancer.getSecurityGroups(), newLoadBalancer.getSecurityGroups())) return false;
        if (notEqual(oldLoadBalancer.getCreatedTime(), newLoadBalancer.getCreatedTime())) return false;
        if (notEqual(oldLoadBalancer.getScheme(), newLoadBalancer.getScheme())) return false;

        return true;
    }

    public boolean notEqualListenerDescriptions(List<ListenerDescription> l1, List<ListenerDescription> l2) {
        for (ListenerDescription ld1 : l1) {
            Collections.sort(ld1.getPolicyNames());
        }
        for (ListenerDescription ld2 : l2) {
            Collections.sort(ld2.getPolicyNames());
        }
        return notEqualCollection(l1, l2);
    }

    public boolean notBackendServerDescriptions(List<BackendServerDescription> l1, List<BackendServerDescription> l2) {
        for (BackendServerDescription bsd1 : l1) {
            Collections.sort(bsd1.getPolicyNames());
        }
        for (BackendServerDescription bsd2 : l2) {
            Collections.sort(bsd2.getPolicyNames());
        }
        return notEqualCollection(l1, l2);
    }
}
