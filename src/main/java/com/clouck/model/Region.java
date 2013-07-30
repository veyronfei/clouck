package com.clouck.model;

import static com.clouck.model.ResourceType.Ec2_Placement_Group;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.amazonaws.regions.Regions;
import com.google.common.base.Optional;

public enum Region {
    //never save Region All into db, this exists as presentation layer and use this to query all resources.
    All(null, "All Regions", "All Regions", false),
    Virginia(Regions.US_EAST_1, "US East (N. Virginia)", "N. Virginia", true),
    Oregon(Regions.US_WEST_2, "US West (Oregon)", "Oregon", true),
    NCalifornia(Regions.US_WEST_1, "US West (N. California)", "N. California", true, Ec2_Placement_Group),
    Ireland(Regions.EU_WEST_1, "EU (Ireland)", "Ireland", true),
    Singapore(Regions.AP_SOUTHEAST_1, "Asia Pacific (Singapore)", "Singapore", true, Ec2_Placement_Group),
    Tokyo(Regions.AP_NORTHEAST_1, "Asia Pacific (Tokyo)", "Tokyo", true, Ec2_Placement_Group),
    Sydney(Regions.AP_SOUTHEAST_2, "Asia Pacific (Sydney)", "Sydney", true, Ec2_Placement_Group),
    SaoPaulo(Regions.SA_EAST_1, "South America (São Paulo)", "São Paulo", true, Ec2_Placement_Group);

    private Regions regions;
    private String desc;
    private String shortDesc;
    private boolean isScan;
    private Set<ResourceType> excludedResourceTypes = new HashSet<>();

    Region(Regions regions, String desc, String shortDesc, boolean isScan, ResourceType... resourceTypes) {
        this.regions = regions;
        this.desc = desc;
        this.shortDesc = shortDesc;
        this.isScan = isScan;
        Collections.addAll(this.excludedResourceTypes, resourceTypes);
    }

    //TODO: at some point, it may be possible to replace this code 
    //with Region.getRegion(Regions.US_WEST_2).isServiceSupported(ServiceAbbreviations.Dynamodb
    // and cache the result.
    // there is no need to caculate this every time as well
    public static Set<Region> findAvailableRegions(ResourceType resourceType) {
        Set<Region> result = new HashSet<>();
        for (Region region : values()) {
            if (region.isScan && !region.getExcludedResourceTypes().contains(resourceType)) {
                result.add(region);
            }
        }
        return result;
    }

    public static Set<Region> findAvailableRegions() {
        Set<Region> result = new HashSet<>();
        for (Region region : values()) {
            if (region.isScan) {
                result.add(region);
            }
        }
        return result;
    }

    public static Optional<Region> toRegion(String regionEndpoint) {
        if (regionEndpoint == null) {
            return Optional.of(Region.All);
        } else {
            for (Region region : Region.findAvailableRegions()) {
                if (region.getRegions().getName().equals(regionEndpoint)) {
                    return Optional.of(region);
                }
            }
        }
        return Optional.absent();
    }

    public Regions getRegions() {
        return regions;
    }

    public Set<ResourceType> getExcludedResourceTypes() {
        return excludedResourceTypes;
    }

    public String getDesc() {
        return desc;
    }

    public String getShortDesc() {
        return shortDesc;
    }
}