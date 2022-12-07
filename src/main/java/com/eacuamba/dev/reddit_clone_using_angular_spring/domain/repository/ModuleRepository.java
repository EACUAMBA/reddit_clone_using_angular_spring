package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.module.Module;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query(
            value = """
                        SELECT module FROM Module module
                        JOIN FETCH module.permissionList permission
                        WHERE permission = :permission
                    """
    )
    List<Module> findAllByPermission(@Param("permission") Permission permission);
}
