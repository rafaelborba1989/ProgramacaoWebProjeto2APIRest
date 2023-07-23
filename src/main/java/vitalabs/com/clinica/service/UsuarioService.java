package vitalabs.com.clinica.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vitalabs.com.clinica.model.Usuario;
import vitalabs.com.clinica.repository.UsuarioRepository;

@Service
public class UsuarioService extends GenericService<Usuario, UsuarioRepository> implements UserDetailsService {

    PasswordEncoder encoder;
    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder) {
        super(repository);
        this.encoder = encoder;
    }

    public void createUsuario(Usuario u){
        u.setPassword(encoder.encode(u.getPassword()));
        this.repository.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }
}
