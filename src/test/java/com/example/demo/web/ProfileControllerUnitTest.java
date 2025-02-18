package com.example.demo.web;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.security.core.parameters.P;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileControllerUnitTest {

    @Test
    public void real_profile(){
        String expected = "real";

        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expected);
        env.addActiveProfile("oauth");

        ProfileController controller = new ProfileController(env);

        String profile = controller.profile();

        assertThat(profile).isEqualTo(expected);
    }



    @Test
    public void real_profile이_없으면_첫번째가_조회된다() {
        //given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void active_profile이_없으면_default가_조회된다() {
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}