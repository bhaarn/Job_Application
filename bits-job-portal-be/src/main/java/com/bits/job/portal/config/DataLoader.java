package com.bits.job.portal.config;
import com.bits.job.portal.enums.ROLE;
import com.bits.job.portal.model.Permission;
import com.bits.job.portal.model.Role;
import com.bits.job.portal.model.User;
import com.bits.job.portal.repository.PermissionRepository;
import com.bits.job.portal.repository.RoleRepository;
import com.bits.job.portal.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserService userService;

    public DataLoader(RoleRepository roleRepository, PermissionRepository permissionRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        permissionRepository.deleteAll();
        roleRepository.deleteAll();
        userService.deleteUserByUserName("admin");
        // Create Permissions
        Permission readPermission = new Permission();
        readPermission.setName("READ_PRIVILEGE");
        permissionRepository.save(readPermission);
        Permission writePermission = new Permission();
        writePermission.setName("WRITE_PRIVILEGE");
        permissionRepository.save(writePermission);
        Set<Permission> readWritePermissions = Set.of(readPermission, writePermission);
        Role adminRole = roleRepository.save(Role.builder().name(ROLE.ADMIN).permissions(readWritePermissions).build());
        roleRepository.save(Role.builder().name(ROLE.EMPLOYEE).permissions(readWritePermissions).build());
        roleRepository.save(Role.builder().name(ROLE.EMPLOYER).permissions(readWritePermissions).build());

        // Create Users
        User adminUser = User.builder()
                .username("admin").password("admin123")
                .build();
        adminUser.setUsername("admin");
        adminUser.setPassword("admin123");
        adminUser.setRoles(Set.of(adminRole));
        userService.saveUser(adminUser);

    }
}