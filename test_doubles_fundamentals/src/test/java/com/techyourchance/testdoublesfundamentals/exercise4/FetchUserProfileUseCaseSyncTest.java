package com.techyourchance.testdoublesfundamentals.exercise4;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Before;

public class FetchUserProfileUseCaseSyncTest {

    private FetchUserProfileUseCaseSync SUT;
    private UserProfileHttpSyncTD userProfileHttpSyncTD;
    private UsersCacheTD usersCacheTD;


    @Before
    public void setup(){

        userProfileHttpSyncTD = new UserProfileHttpSyncTD();
        usersCacheTD = new UsersCacheTD();
        SUT = new FetchUserProfileUseCaseSync(userProfileHttpSyncTD, usersCacheTD);

        //when I call getUserProfile method

    }




    //----------------------------------------------------------------------------------------------
    //Helper classes

    private static class UserProfileHttpSyncTD implements UserProfileHttpEndpointSync {
        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            return null;
        }
    }

    private static class UsersCacheTD implements UsersCache {
        @Override
        public void cacheUser(User user) {

        }

        @Nullable
        @Override
        public User getUser(String userId) {
            return null;
        }
    }

}