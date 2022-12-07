package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
@Configuration()
public class AuthenticationManagerImplementation implements AuthenticationManager {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = this.userService.loadUserByUsername(username);

        if(user == null) {
            String usernameNotFoundMessage = String.format("Utilizador '%s' não foi encontrado!<br>Verifique se digitou correctamente o email ou nome de user", username);
            log.debug(usernameNotFoundMessage);
            throw new UsernameNotFoundException(usernameNotFoundMessage);
        }

        if(!user.isAccountNonExpired()){
            String accountExpiredException = String.format("Utilizador '%s' a sua conta está expirada!<br>Entre em contacto com o administrador do sistema.", username);
            log.debug(accountExpiredException);
            throw new AccountExpiredException(accountExpiredException);
        }

        if(!user.isAccountNonLocked()){
            //this.tokenService.sendTokenMail(user, TipoToken.REDEFINIR_SENHA);
            String lockedException = String.format("Utilizador '%s' a sua conta está bloqueada!<br>Enviamos um email na sua conta para restaurar a sua conta.", username);
            log.debug(lockedException);
            throw new LockedException(lockedException);
        }

        if(!user.isEnabled()){
            String disabledException = String.format("Utilizador '%s' a sua conta está desactivada!<br>Entre em contacto com o administrador do sistema.", username);
            log.debug(disabledException);
            throw new DisabledException(disabledException);
        }

//        if(!user.isCredentialsNonExpired()){
//            this.tokenService.sendTokenMail(user, TipoToken.REDEFINIR_SENHA);
//            String userDetailsEmail = Configurations.getValue("mail.from");
//            PessoaColectiva pessoaColectiva = user.getPessoaColectiva();
//            PessoaSingular pessoaSingular = user.getPessoaSingular();
//            if(pessoaColectiva != null)
//                userEmail = pessoaColectiva.getEmailStringList().stream().reduce((newEmail, emails)-> emails += ", " + newEmail).orElse(null);
//            else if(pessoaSingular != null)
//                userEmail = pessoaSingular.getContacto().getEmail().concat(", ").concat(pessoaSingular.getContacto().getEmailAlternativo());
//            String credentialsExpiredException = String.format("Utilizador '%s' as suas credenciais expiraram!<br>Acede ao seu email (%s) para proceder a redefinição da sua conta.", username, userEmail);
//            log.debug(credentialsExpiredException);
//            throw new CredentialsExpiredException(credentialsExpiredException);
//        }

        if(!this.passwordEncoder.matches(password, user.getPassword())){
            user.setTries(user.getTries() - 1);
            Integer leftTries = user.getTries();
            String credentialsExpiredException;
            if(leftTries <= 0){
                user.setAccountNonLocked(false);
                credentialsExpiredException = String.format("Utilizador '%s' excedeu o numero de tentativas restantes!<br>A sua conta foi bloqueada, aceda ao seu email para redefinir a sua senha.", username);
                //this.tokenService.sendTokenMail(user, TipoToken.REDEFINIR_SENHA);
            }else {
                credentialsExpiredException = String.format("Utilizador '%s' a sua senha esta incorrecta!<br>Dispões de mais %d tentativas, apos isso será obrigado a redefinir a sua senha.", username, leftTries);
            }
            this.userService.update(user);
            log.debug(credentialsExpiredException);
            throw new BadCredentialsException(credentialsExpiredException);
        }

        //Set<Token> tokenSet = this.tokenRepository.findAllByUtilizadorId(user.getId());
        //Optional<Token> tokenOptional = tokenSet.stream().filter((token) -> !token.getConfirmado()).findFirst();
//        if(tokenOptional.isPresent()){
//            Token token = tokenOptional.get();
//            this.tokenService.resendTokenMail(token);
//            String nomeUtilizador = token.getUtilizador().getNome();
//            String accountNonVerifiedException = String.format("%s a sua conta ainda não foi verificada!<br>Aceda o seu email e confirme a sua conta.", nomeUtilizador);
//            throw new AccountNonVerifiedException(accountNonVerifiedException);
//        }

        user.setTries(5);
        user = this.userService.save(user);
        log.debug(String.format("Autenticação bem sucedida para utilizador '%s'.", user.getUsername()));
        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }
}
