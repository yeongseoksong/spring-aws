package com.example.demo.config.auth;

import com.example.demo.config.auth.dto.OAuthAttributes;
import com.example.demo.config.auth.dto.SessionUser;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;


@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private final HttpSession httpSession;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

          OAuthAttributes attrs=OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

          User user = saveOrUpdate(attrs);

          httpSession.setAttribute("user",new SessionUser(user));


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attrs.getAttributes(),
                attrs.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attrs){
        User user = userRepository.findByEmail(attrs.getEmail())
                .map(entity->entity.update(attrs.getName(),attrs.getPicture()))
                .orElse(attrs.toEntity());

        return userRepository.save(user);

    }
}
