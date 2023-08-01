package com.zerobase.dividends.config;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public Trie<String, String> trie() {
        return new PatriciaTrie<>();
    }

    @Bean // 페스워드를 그냥 저장하지 않기 위해서 passwordEncoder를 사용하기 위한 코드
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
