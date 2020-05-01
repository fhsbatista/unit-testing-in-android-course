package com.techyourchance.mockitofundamentals.exercise5;

import com.techyourchance.mockitofundamentals.exercise5.eventbus.EventBusPoster;
import com.techyourchance.mockitofundamentals.exercise5.eventbus.UserDetailsChangedEvent;
import com.techyourchance.mockitofundamentals.exercise5.networking.NetworkErrorException;
import com.techyourchance.mockitofundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync;
import com.techyourchance.mockitofundamentals.exercise5.users.User;
import com.techyourchance.mockitofundamentals.exercise5.users.UsersCache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UpdateUsernameUseCaseSyncTest {

    public static final String USER_ID = "20934";
    public static final String USER_NAME = "Fernando Batista";

    UpdateUsernameUseCaseSync sut;
    UsersCache usersCache;
    EventBusPoster eventBusPoster;
    UpdateUsernameHttpEndpointSync updateUsernameHttpEndpointSync;

    @Before
    public void setUp() throws Exception {
        usersCache =  mock(UsersCache.class);
        eventBusPoster = mock(EventBusPoster.class);
        updateUsernameHttpEndpointSync = mock(UpdateUsernameHttpEndpointSync.class);
        sut = new UpdateUsernameUseCaseSync(updateUsernameHttpEndpointSync, usersCache, eventBusPoster);
        success();
    }

    @Test
    public void updateUsername_success_userIdAndUsernamePassedToEndpoint() throws NetworkErrorException {
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        sut.updateUsernameSync(USER_ID, USER_NAME);
        verify(updateUsernameHttpEndpointSync, times(1)).updateUsername(ac.capture(), ac.capture());
        List<String> captures = ac.getAllValues();
        assertThat(captures.get(0), is(USER_ID));
    }

    @Test
    public void updateUsername_success_userCached() throws NetworkErrorException {
        ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        sut.updateUsernameSync(USER_ID, USER_NAME);
        verify(usersCache, times(1)).cacheUser(ac.capture());
        User cachedUser = ac.getValue();
        assertThat(cachedUser.getUserId(), is(USER_ID));
    }

    @Test
    public void updateUserName_success_loggedInEventPosted() {
        ArgumentCaptor<Object> ac = ArgumentCaptor.forClass(Object.class);
        sut.updateUsernameSync(USER_ID, USER_NAME);
        verify(eventBusPoster, times(1)).postEvent(ac.capture());
        Object event = ac.getValue();
        assertThat(event, instanceOf(UserDetailsChangedEvent.class));
    }

    @Test
    public void updateUserName_authError_userNotCached() throws NetworkErrorException{
        authError();
        sut.updateUsernameSync(USER_ID, USER_NAME);
        verifyNoMoreInteractions(usersCache);
    }

    @Test
    public void updateUserName_authError_loggedInEventNotPosted() throws NetworkErrorException{
        authError();
        sut.updateUsernameSync(USER_ID, USER_NAME);
        verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUserName_authError_failureReturned() throws NetworkErrorException{
        authError();
        UpdateUsernameUseCaseSync.UseCaseResult result = sut.updateUsernameSync(USER_ID, USER_NAME);
        assertThat(result, is(UpdateUsernameUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void updateUserName_generalError_userNotCached() throws NetworkErrorException{
        generalError();
        sut.updateUsernameSync(USER_ID, USER_NAME);
        verifyNoMoreInteractions(usersCache);
    }

    @Test
    public void updateUserName_generalError_loggedInEventNotPosted() throws NetworkErrorException{
        generalError();
        sut.updateUsernameSync(USER_ID, USER_NAME);
        verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUserName_generalError_failureReturned() throws NetworkErrorException{
        generalError();
        UpdateUsernameUseCaseSync.UseCaseResult result = sut.updateUsernameSync(USER_ID, USER_NAME);
        assertThat(result, is(UpdateUsernameUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void updateUserName_serverError_userNotCached() throws NetworkErrorException{
        serverError();
        sut.updateUsernameSync(USER_ID, USER_NAME);
        verifyNoMoreInteractions(usersCache);
    }

    @Test
    public void updateUserName_serverError_loggedInEventNotPosted() throws NetworkErrorException{
        serverError();
        sut.updateUsernameSync(USER_ID, USER_NAME);
        verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUserName_serverError_failureReturned() throws NetworkErrorException{
        serverError();
        UpdateUsernameUseCaseSync.UseCaseResult result = sut.updateUsernameSync(USER_ID, USER_NAME);
        assertThat(result, is(UpdateUsernameUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void updateUserName_networkError_networkErrorReturned() throws NetworkErrorException {
        networkError();
        UpdateUsernameUseCaseSync.UseCaseResult result = sut.updateUsernameSync(USER_ID, USER_NAME);
        assertThat(result, is(UpdateUsernameUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    private void success() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSync.updateUsername(anyString(), anyString()))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.SUCCESS, USER_ID, USER_NAME));
    }

    private void authError() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSync.updateUsername(anyString(), anyString()))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.AUTH_ERROR, "",  ""));
    }

    private void generalError() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSync.updateUsername(anyString(), anyString()))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR, "", ""));
    }

    private void serverError() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSync.updateUsername(anyString(), anyString()))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.SERVER_ERROR, "", ""));
    }

    private void networkError() throws NetworkErrorException {
        doThrow(new NetworkErrorException())
                .when(updateUsernameHttpEndpointSync).updateUsername(anyString(), anyString());
    }

}