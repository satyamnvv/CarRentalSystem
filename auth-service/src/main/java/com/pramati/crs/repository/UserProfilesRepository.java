package com.pramati.crs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pramati.crs.entity.UserProfile;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfile, String> {

}
