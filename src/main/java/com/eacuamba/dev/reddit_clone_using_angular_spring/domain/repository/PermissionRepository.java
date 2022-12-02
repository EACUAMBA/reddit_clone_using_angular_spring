package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.UserGroup;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(
            value = """
                        SELECT permission FROM Permission permission
                        JOIN FETCH permission.userGroupList userGroup
                        WHERE userGroup = :userGroup
                    """
    )
    List<Permission> findAllByUserGroup(@Param("userGroup") UserGroup userGroup);
}
