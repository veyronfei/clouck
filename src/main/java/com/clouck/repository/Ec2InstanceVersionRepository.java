package com.clouck.repository;

import org.springframework.data.repository.CrudRepository;

import com.clouck.model.aws.ec2.Ec2Instance;

public interface Ec2InstanceVersionRepository extends CrudRepository<Ec2Instance, String> {

}
