package com.techyourchance.testdoublesfundamentals.exercise4;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FetchUserProfileUseCaseSyncTest {

    public static final String USER_ID = "92384";
    public static final String USER_NAME = "Fernando Batista";
    public static final String USER_IMAGE = "data:image/jpeg;base64,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";

    FetchUserProfileUseCaseSync sut;
    UserProfileHttpEndpointSyncTd userProfileHttpEndpointSync;
    UsersCacheTd usersCache;

    @Before
    public void setup() {
        userProfileHttpEndpointSync = new UserProfileHttpEndpointSyncTd();
        usersCache = new UsersCacheTd();
        sut = new FetchUserProfileUseCaseSync(userProfileHttpEndpointSync, usersCache);
    }

    @Test
    public void fetchProfile_success_userIdPassedToEndPoint() {
        sut.fetchUserProfileSync(USER_ID);
        assertThat(userProfileHttpEndpointSync.mUserId, is(USER_ID));
    }

    @Test
    public void fetchProfile_success_userCached() {
        sut.fetchUserProfileSync(USER_ID);
        assertThat(usersCache.getUser(USER_ID).getUserId(), is(USER_ID));
    }

    //    Helper classes
    private static class UserProfileHttpEndpointSyncTd implements UserProfileHttpEndpointSync {

        public EndpointResultStatus mCurrentEndpointStatus = EndpointResultStatus.SUCCESS;

        public String mUserId = "";

        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            mUserId = userId;
            switch (mCurrentEndpointStatus) {
                case SUCCESS:
                    return new EndpointResult(EndpointResultStatus.SUCCESS, userId, USER_NAME, USER_IMAGE);
                default:
                    return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, userId, "", "");
            }
        }
    }

    private static class UsersCacheTd implements UsersCache {

        public HashMap<String, User> cachedUsers = new HashMap<>();

        @Override
        public void cacheUser(User user) {
            cachedUsers.put(user.getUserId(), user);
        }

        @Nullable
        @Override
        public User getUser(String userId) {
            return cachedUsers.get(userId);
        }
    }

}