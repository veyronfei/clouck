package com.clouck.repository;

import org.springframework.data.repository.CrudRepository;

import com.clouck.model.aws.ec2.Ec2Version;

public interface Ec2VersionRepository extends CrudRepository<Ec2Version, String> {
}
