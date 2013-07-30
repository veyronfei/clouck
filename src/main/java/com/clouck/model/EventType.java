package com.clouck.model;

public enum EventType {
    Unknown,

    //Tag
    Tag_Add,
    Tag_Delete,
    Tag_Update,

    //AMI
    Ec2_AMI_First_Scan,
    Ec2_AMI_Found,
    Ec2_AMI_Registered,
    Ec2_AMI_Deregistered,
    Ec2_AMI_Pending,
    Ec2_AMI_Permission_Visibility_Public,
    Ec2_AMI_Permission_Visibility_Private,
    Ec2_AMI_Permission_Account_Added,
    Ec2_AMI_Permission_Account_Removed,
    Ec2_AMI_Description_Changed,
    Ec2_AMI_Snapshot_Completed,

    //Instance
    Ec2_Instance_Found,
    Ec2_Instance_First_Scan,
    Ec2_Instance_Launch,
    Ec2_Instance_Terminate,
    Ec2_Instance_Stop,
    Ec2_Instance_Pending,
    Ec2_Instance_Shutting_Down,
    Ec2_Instance_Stopping,
    Ec2_Instance_Security_Group_Added,
    Ec2_Instance_Security_Group_Deleted,
    Ec2_Instance_Source_Dest_Check_Enabled,
    Ec2_Instance_Source_Dest_Check_Disabled,
    Ec2_Instance_Termination_Protection_Enabled,
    Ec2_Instance_Termination_Protection_Disabled,
    Ec2_Instance_Shutdown_Behavior_Stop,
    Ec2_Instance_Shutdown_Behavior_Terminate,
    Ec2_Instance_Monitoring_Enabled,
    Ec2_Instance_Monitoring_Disabled,
    Ec2_Instance_Instance_Type,
    Ec2_Instance_EBS_Optimized_Enabled,
    Ec2_Instance_EBS_Optimized_Disabled,
    Ec2_Instance_Elastic_Ip_Associated,
    Ec2_Instance_Elastic_Ip_Disassociated,
    Ec2_Instance_User_Data_Changed,
    Ec2_Instance_Network_Interface_Attached,
    Ec2_Instance_Network_Interface_Attaching,
    Ec2_Instance_Network_Interface_Detached,
    Ec2_Instance_Network_Interface_Detaching,
    Ec2_Instance_Private_Ip_Unassigned,
    Ec2_Instance_Private_Ip_Assigned,

    //Snapshot
    Ec2_Snapshot_Found,
    Ec2_Snapshot_First_Scan,
    Ec2_Snapshot_Created,
    Ec2_Snapshot_Pending,
    Ec2_Snapshot_Deleted,
    Ec2_Snapshot_Permission_Visibility_Public,
    Ec2_Snapshot_Permission_Visibility_Private,
    Ec2_Snapshot_Permission_Account_Added,
    Ec2_Snapshot_Permission_Account_Removed,

    //Security group
    Ec2_Security_Group_Found,
    Ec2_Security_Group_First_Scan,
    Ec2_Security_Group_Create,
    Ec2_Security_Group_Delete,
    Ec2_Security_Group_Add_Rule,
    Ec2_Security_Group_Delete_Rule,

    //Elastic IP
    Ec2_Elastic_Ip_Found,
    Ec2_Elastic_Ip_First_Scan,
    Ec2_Elastic_Ip_Allocated,
    Ec2_Elastic_Ip_Released,
    Ec2_Elastic_Ip_Associated,
    Ec2_Elastic_Ip_Disassociated,

    //Placement group
    Ec2_PlacementGroup_Found,
    Ec2_PlacementGroup_First_Scan,
    Ec2_PlacementGroup_Create,
    Ec2_PlacementGroup_Delete,
    Ec2_PlacementGroup_Deleting,
    Ec2_PlacementGroup_Pending,

    //Key pair
    Ec2_Key_Pair_Found,
    Ec2_Key_Pair_First_Scan,
    Ec2_Key_Pair_Add,
    Ec2_Key_Pair_Update,
    Ec2_Key_Pair_Delete,
    
    //network interface
    Ec2_Network_Interface_Found,
    Ec2_Network_Interface_First_Scan,
    Ec2_Network_Interface_Created,
    Ec2_Network_Interface_Deleted,
    Ec2_Network_Interface_Detached,
    Ec2_Network_Interface_Attached,
    Ec2_Network_Interface_Security_Group_Added,
    Ec2_Network_Interface_Security_Group_Deleted,
    Ec2_Network_Interface_Source_Dest_Check_Enabled,
    Ec2_Network_Interface_Source_Dest_Check_Disabled,
    Ec2_Network_Interface_Description_Updated,

    //Volume
    Ec2_Volume_Found,
    Ec2_Volume_First_Scan,
    Ec2_Volume_Creating,
    Ec2_Volume_Deleting,
    Ec2_Volume_Create,
    Ec2_Volume_Delete,
    Ec2_Volume_Error,
    Ec2_Volume_Attaching,
    Ec2_Volume_Attached,
    Ec2_Volume_Detaching,
    Ec2_Volume_Detached,
    Ec2_Volume_Device,
    Ec2_Volume_Delete_On_Termination_Enabled,
    Ec2_Volume_Delete_On_Termination_Disabled,
    Ec2_Volume_Auto_Enable_IO_Enabled,
    Ec2_Volume_Auto_Enable_IO_Disabled,
//    creating, available, in-use, deleting, error

    //Spot Instance Request
    Ec2_Spot_Instance_Request_Found,
    Ec2_Spot_Instance_Request_First_Scan,
    Ec2_Spot_Instance_Request_Created,
    Ec2_Spot_Instance_Request_Active,
    Ec2_Spot_Instance_Request_Closed,
    Ec2_Spot_Instance_Request_Failed,
    Ec2_Spot_Instance_Request_Cancelled,
    Ec2_Spot_Instance_Request_Pending_Fulfillment,
    Ec2_Spot_Instance_Request_Price_Too_Low,
    Ec2_Spot_Instance_Request_Fulfilled,
    Ec2_Spot_Instance_Request_Instance_Terminated_By_Price,
    Ec2_Spot_Instance_Request_Instance_Terminated_No_Capacity,

    //Load Balancer
    Ec2_Load_Balancer_Found,
    Ec2_Load_Balancer_First_Scan,
    Ec2_Load_Balancer_Created,
    Ec2_Load_Balancer_Deleted,
    ;

    public String getName() {
        return name();
    }
}
