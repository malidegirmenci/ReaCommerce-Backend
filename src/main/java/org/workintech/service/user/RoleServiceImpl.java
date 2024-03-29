package org.workintech.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.workintech.converter.DtoConverter;
import org.workintech.dto.user.RoleResponse;
import org.workintech.entity.user.Role;
import org.workintech.repository.user.RoleRepository;
import org.workintech.service.user.RoleService;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> saveAll(List<Role> roles) {
        return roleRepository.saveAll(roles);
    }

    @Override
    public List<RoleResponse> getAll() {
        return DtoConverter.convertToRoleResponseList(roleRepository.findAll());
    }
}
