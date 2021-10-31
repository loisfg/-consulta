package com.bandtec.mais.consulta.usecase.auth;

import com.bandtec.mais.consulta.domain.Usuario;
import com.bandtec.mais.consulta.models.dto.request.UsuarioSignInRequestDTO;
import com.bandtec.mais.consulta.models.dto.response.UsuarioSignInResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UsuarioSignIn {
    Optional<UsuarioSignInResponseDTO> execute(UsuarioSignInRequestDTO usuarioSignInRequestDTO, List<Usuario> usuariosLogados);
}
