package com.brittany.sprinboot_manager_task.services;

import com.brittany.sprinboot_manager_task.models.UsuarioModel;

public interface AuthenticatedUserServiceI {
    UsuarioModel getCurrentUser();

    String getCurrentUsername();

    boolean isCurrentUser(Long userId);

}
