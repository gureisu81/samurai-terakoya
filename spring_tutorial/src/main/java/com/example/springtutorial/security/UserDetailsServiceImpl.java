package com.example.springtutorial.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springtutorial.entity.User;
import com.example.springtutorial.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // 送信されたユーザー名と一致するユーザー情報をテーブルから取得
            User user = userRepository.findByUserName(username).get(0);

            // ロールIDに応じたロール名を取得(今回はテーブルを直接参照しない)
            String userRoleName = switch(user.getRoleId()) {
                case 1  -> "ROLE_GENERAL";
                case 2  -> "ROLE_ADMIN";
                default -> "ROLE_GENERAL";
            };

            // ロールのリスト用オブジェクトを生成
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            // ユーザーのロール名をリストに追加
            authorities.add(new SimpleGrantedAuthority(userRoleName));

            // ユーザー情報とロールリストをもとにUserDetailsImplを生成
            return new UserDetailsImpl(user, authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
        }
    }
}