package com.clouck.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.BlockDeviceMapping;
import com.amazonaws.services.ec2.model.InstanceNetworkInterface;
import com.amazonaws.services.ec2.model.InstancePrivateIpAddress;
import com.amazonaws.services.ec2.model.Tag;
import com.clouck.comparator.CompareResult;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Instance;
import com.clouck.service.CompareResourceResult;
import com.clouck.validator.Ec2ResourceValidator;

@Component
public class ResourceUtil {
    private static final Logger log = LoggerFactory.getLogger(ResourceUtil.class);
    @Autowired
    private Ec2ResourceValidator validator;

    public <R extends AbstractResource<?>> Map<String, R> generateKeyMap(List<R> resources) {
        Map<String, R> result = new HashMap<>();
        for (R resource : resources) {
            result.put(resource.getUniqueId(), resource);
        }
        return result;
    }

    public <L> Map<Integer, L> generateHashCodeKeyMap(List<L> list) {
        Map<Integer, L> result = new HashMap<>();
        for (L object : list) {
            result.put(object.hashCode(), object);
        }
        return result;
    }

    public static Map<String, Tag> generateTagKeyMap(List<Tag> list) {
        Map<String, Tag> result = new HashMap<>();
        for (Tag tag : list) {
            result.put(tag.getKey(), tag);
        }
        return result;
    }

    public Map<String, InstanceNetworkInterface> generateNetworkInterfaceId(List<InstanceNetworkInterface> networkInterfaces) {
        Map<String, InstanceNetworkInterface> result = new HashMap<>();
        for (InstanceNetworkInterface networkInterface : networkInterfaces) {
            result.put(networkInterface.getNetworkInterfaceId(), networkInterface);
        }
        return result;
    }

    public Map<String, InstancePrivateIpAddress> generatePrivateIpAddress(List<InstancePrivateIpAddress> privateIpAddresses) {
        Map<String, InstancePrivateIpAddress> result = new HashMap<>();
        for (InstancePrivateIpAddress privateIpAddress : privateIpAddresses) {
            result.put(privateIpAddress.getPrivateIpAddress(), privateIpAddress);
        }
        return result;
    }
    
    public Map<String, BlockDeviceMapping> generateBlockDeviceMapping(List<BlockDeviceMapping> blockDeviceMappings) {
        Map<String, BlockDeviceMapping> result = new HashMap<>();
        for (BlockDeviceMapping blockDeviceMapping : blockDeviceMappings) {
            result.put(blockDeviceMapping.getDeviceName(), blockDeviceMapping);
        }
        return result;
    }

    public CompareResult<BlockDeviceMapping> compareBlockDeviceMappings(List<BlockDeviceMapping> oldBlockDeviceMappings, List<BlockDeviceMapping> newBlockDeviceMappings) {
        Validate.noNullElements(new Object[]{oldBlockDeviceMappings, newBlockDeviceMappings});
        CompareResult<BlockDeviceMapping> result = new CompareResult<>();

        Map<String, BlockDeviceMapping> oldBlockDeviceMappingMap = generateBlockDeviceMapping(oldBlockDeviceMappings);
        Map<String, BlockDeviceMapping> newBlockDeviceMappingMap = generateBlockDeviceMapping(newBlockDeviceMappings);

        for (String newKey : newBlockDeviceMappingMap.keySet()) {
            BlockDeviceMapping newBDM = newBlockDeviceMappingMap.get(newKey);
            BlockDeviceMapping oldBDM = oldBlockDeviceMappingMap.get(newKey);
            if (oldBDM != null) {
                if (!oldBDM.equals(newBDM)) {
                    result.getUpdate().add(Pair.of(oldBDM, newBDM));
                }
            } else {
                result.getAdd().add(newBDM);
            }
            oldBlockDeviceMappingMap.remove(newKey);
        }

        for (String oldKey : oldBlockDeviceMappingMap.keySet()) {
            BlockDeviceMapping oldBlockDeviceMapping = oldBlockDeviceMappingMap.get(oldKey);
            result.getDelete().add(oldBlockDeviceMapping);
        }
        return result;
    }

    public CompareResult<InstancePrivateIpAddress> comparePrivateIpAddresses(List<InstancePrivateIpAddress> oldPrivateIpAddresses, List<InstancePrivateIpAddress> newPrivateIpAddresses) {
        Validate.noNullElements(new Object[]{oldPrivateIpAddresses, newPrivateIpAddresses});
        CompareResult<InstancePrivateIpAddress> result = new CompareResult<>();

        Map<String, InstancePrivateIpAddress> oldPrivateIpAddressMap = generatePrivateIpAddress(oldPrivateIpAddresses);
        Map<String, InstancePrivateIpAddress> newPrivateIpAddressMap = generatePrivateIpAddress(newPrivateIpAddresses);

        for (String newKey : newPrivateIpAddressMap.keySet()) {
            InstancePrivateIpAddress newIPI = newPrivateIpAddressMap.get(newKey);
            InstancePrivateIpAddress oldIPI = oldPrivateIpAddressMap.get(newKey);
            if (oldIPI != null) {
                if (!oldIPI.equals(newIPI)) {
                    result.getUpdate().add(Pair.of(oldIPI, newIPI));
                }
            } else {
                result.getAdd().add(newIPI);
            }
            oldPrivateIpAddressMap.remove(newKey);
        }

        for (String oldKey : oldPrivateIpAddressMap.keySet()) {
            InstancePrivateIpAddress oldPrivateIpAddress = oldPrivateIpAddressMap.get(oldKey);
            result.getDelete().add(oldPrivateIpAddress);
        }
        return result;
    }

    public CompareResult<InstanceNetworkInterface> compareNetworkInterfaces(List<InstanceNetworkInterface> oldNetworkInterfaces, List<InstanceNetworkInterface> newNetworkInterfaces) {
        Validate.noNullElements(new Object[]{oldNetworkInterfaces, newNetworkInterfaces});
        CompareResult<InstanceNetworkInterface> result = new CompareResult<>();

        Map<String, InstanceNetworkInterface> oldNetworkInterfaceIdMap = generateNetworkInterfaceId(oldNetworkInterfaces);
        Map<String, InstanceNetworkInterface> newNetworkInterfaceIdMap = generateNetworkInterfaceId(newNetworkInterfaces);

        for (String newKey : newNetworkInterfaceIdMap.keySet()) {
            InstanceNetworkInterface newINI = newNetworkInterfaceIdMap.get(newKey);
            InstanceNetworkInterface oldINI = oldNetworkInterfaceIdMap.get(newKey);
            if (oldINI != null) {
                Ec2Instance.sortInstanceNetworkInterface(oldINI);
                Ec2Instance.sortInstanceNetworkInterface(newINI);
                if (!oldINI.equals(newINI)) {
                    result.getUpdate().add(Pair.of(oldINI, newINI));
                }
            } else {
                result.getAdd().add(newINI);
            }
            oldNetworkInterfaceIdMap.remove(newKey);
        }

        for (String oldKey : oldNetworkInterfaceIdMap.keySet()) {
            InstanceNetworkInterface oldInstanceNetworkInterface = oldNetworkInterfaceIdMap.get(oldKey);
            result.getDelete().add(oldInstanceNetworkInterface);
        }
        return result;
    }

    public <R> CompareResult<R> compare(List<R> oldList, List<R> newList) {
        Validate.noNullElements(new Object[]{oldList, newList});
        CompareResult<R> result = new CompareResult<>();

        Map<Integer, R> oldListKeyMap = generateHashCodeKeyMap(oldList);
        Map<Integer, R> newListKeyMap = generateHashCodeKeyMap(newList);

        for (Integer newKey : newListKeyMap.keySet()) {
            R newObject = newListKeyMap.get(newKey);
            R oldObject = oldListKeyMap.get(newKey);
            if (oldObject == null) {
                result.getAdd().add(newObject);
            }
            oldListKeyMap.remove(newKey);
        }

        for (Integer oldKey : oldListKeyMap.keySet()) {
            R oldObject = oldListKeyMap.get(oldKey);
            result.getDelete().add(oldObject);
        }
        return result;
    }

    public CompareResult<Tag> compareTags(List<Tag> oldTags, List<Tag> newTags) {
        Validate.noNullElements(new Object[]{oldTags, newTags});
        CompareResult<Tag> result = new CompareResult<>();

        Map<String, Tag> oldTagKeyMap = ResourceUtil.generateTagKeyMap(oldTags);
        Map<String, Tag> newTagKeyMap = ResourceUtil.generateTagKeyMap(newTags);

        for (String newKey : newTagKeyMap.keySet()) {
            Tag newTag = newTagKeyMap.get(newKey);
            Tag oldTag = oldTagKeyMap.get(newKey);
            if (oldTag != null) {
                if (!oldTag.equals(newTag)) {
                    result.getUpdate().add(Pair.of(oldTag, newTag));
                }
            } else {
                result.getAdd().add(newTag);
            }
            oldTagKeyMap.remove(newKey);
        }

        for (String oldKey : oldTagKeyMap.keySet()) {
            Tag oldTag = oldTagKeyMap.get(oldKey);
            result.getDelete().add(oldTag);
        }
        return result;
    }

    public CompareResourceResult<AbstractResource<?>> compareResources(List<AbstractResource<?>> oldResources,
            List<AbstractResource<?>> newResources) {
        validator.validate(oldResources, newResources);

        CompareResourceResult<AbstractResource<?>> result = new CompareResourceResult<>();

        Map<String, AbstractResource<?>> oldKeyMap = generateKeyMap(oldResources);
        Map<String, AbstractResource<?>> newKeyMap = generateKeyMap(newResources);

        for (String newUniqueId : newKeyMap.keySet()) {
            AbstractResource<?> newResource = newKeyMap.get(newUniqueId);
            AbstractResource<?> oldResource = oldKeyMap.get(newUniqueId);

            if (oldResource != null) {
                log.debug("unique id {} found, check changes", newUniqueId);
                if (!oldResource.equals(newResource)) {
                    log.debug("unique id {} changed, add it", newUniqueId);
                    result.getAddedResources().add(newResource);
                } else {
                    log.debug("unique id {} unchanged, ignore it", newUniqueId);
                    result.getUnchangedResourceIds().add(oldResource.getId());
                }
            } else {
                log.debug("unique id {} not found, add it", newUniqueId);
                result.getAddedResources().add(newResource);
            }
            oldKeyMap.remove(newUniqueId);
        }

        for (AbstractResource<?> deletedResource : oldKeyMap.values()) {
            log.debug("unique id {} not exist, delete it", deletedResource.getUniqueId());
            result.getDeletedResourceIds().add(deletedResource.getId());
        }

        return result;
    }

    public static boolean notEqual(Object o1, Object o2) {
        return !Objects.equals(o1, o2);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean notEqualCollection(Collection c1, Collection c2) {
        return !c1.containsAll(c2) || !c2.containsAll(c1);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static boolean equalCollection(Collection c1, Collection c2) {
        return c1.containsAll(c2) && c2.containsAll(c1);
    }
    //TODO: in eventserviceimple, generate events using compare as well.
}
