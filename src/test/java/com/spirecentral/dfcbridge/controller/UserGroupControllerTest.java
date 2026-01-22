package com.spirecentral.dfcbridge.controller;

import com.spirecentral.dfcbridge.exception.GroupNotFoundException;
import com.spirecentral.dfcbridge.exception.UserNotFoundException;
import com.spirecentral.dfcbridge.model.GroupInfo;
import com.spirecentral.dfcbridge.model.UserInfo;
import com.spirecentral.dfcbridge.service.UserGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserGroupController.
 */
class UserGroupControllerTest {

    private UserGroupService userGroupService;
    private UserGroupController controller;

    private static final String SESSION_ID = "test-session-123";

    @BeforeEach
    void setUp() {
        userGroupService = mock(UserGroupService.class);
        controller = new UserGroupController(userGroupService);
    }

    // ============== User Tests ==============

    @Test
    void listUsers_returnsUserList() {
        List<UserInfo> users = Arrays.asList(
                UserInfo.builder().objectId("0b123").userName("user1").build(),
                UserInfo.builder().objectId("0b456").userName("user2").build()
        );
        when(userGroupService.listUsers(SESSION_ID, null)).thenReturn(users);

        ResponseEntity<List<UserInfo>> response = controller.listUsers(SESSION_ID, null);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("user1", response.getBody().get(0).getUserName());
    }

    @Test
    void listUsers_withPattern_passesPatternToService() {
        when(userGroupService.listUsers(SESSION_ID, "admin*")).thenReturn(Collections.emptyList());

        controller.listUsers(SESSION_ID, "admin*");

        verify(userGroupService).listUsers(SESSION_ID, "admin*");
    }

    @Test
    void getUser_returnsUserInfo() {
        UserInfo user = UserInfo.builder()
                .objectId("0b123")
                .userName("dmadmin")
                .userOsName("dmadmin")
                .userState("0")
                .superUser(true)
                .build();
        when(userGroupService.getUser(SESSION_ID, "dmadmin")).thenReturn(user);

        ResponseEntity<UserInfo> response = controller.getUser("dmadmin", SESSION_ID);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("dmadmin", response.getBody().getUserName());
        assertTrue(response.getBody().isSuperUser());
    }

    @Test
    void getUser_notFound_throwsUserNotFoundException() {
        when(userGroupService.getUser(SESSION_ID, "nonexistent"))
                .thenThrow(new UserNotFoundException("nonexistent"));

        assertThrows(UserNotFoundException.class,
                () -> controller.getUser("nonexistent", SESSION_ID));
    }

    // ============== Group Tests ==============

    @Test
    void listGroups_returnsGroupList() {
        List<GroupInfo> groups = Arrays.asList(
                GroupInfo.builder().objectId("0c123").groupName("group1").build(),
                GroupInfo.builder().objectId("0c456").groupName("group2").build()
        );
        when(userGroupService.listGroups(SESSION_ID, null)).thenReturn(groups);

        ResponseEntity<List<GroupInfo>> response = controller.listGroups(SESSION_ID, null);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("group1", response.getBody().get(0).getGroupName());
    }

    @Test
    void listGroups_withPattern_passesPatternToService() {
        when(userGroupService.listGroups(SESSION_ID, "dm_*")).thenReturn(Collections.emptyList());

        controller.listGroups(SESSION_ID, "dm_*");

        verify(userGroupService).listGroups(SESSION_ID, "dm_*");
    }

    @Test
    void getGroup_returnsGroupInfoWithMembers() {
        GroupInfo group = GroupInfo.builder()
                .objectId("0c123")
                .groupName("docu")
                .description("Documentation group")
                .usersNames(Arrays.asList("user1", "user2", "user3"))
                .groupsNames(Collections.emptyList())
                .build();
        when(userGroupService.getGroup(SESSION_ID, "docu")).thenReturn(group);

        ResponseEntity<GroupInfo> response = controller.getGroup("docu", SESSION_ID);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("docu", response.getBody().getGroupName());
        assertEquals(3, response.getBody().getUsersNames().size());
    }

    @Test
    void getGroup_notFound_throwsGroupNotFoundException() {
        when(userGroupService.getGroup(SESSION_ID, "nonexistent"))
                .thenThrow(new GroupNotFoundException("nonexistent"));

        assertThrows(GroupNotFoundException.class,
                () -> controller.getGroup("nonexistent", SESSION_ID));
    }

    // ============== User Groups Tests ==============

    @Test
    void getGroupsForUser_returnsGroupList() {
        List<GroupInfo> groups = Arrays.asList(
                GroupInfo.builder().objectId("0c123").groupName("group1").build(),
                GroupInfo.builder().objectId("0c456").groupName("group2").build()
        );
        when(userGroupService.getGroupsForUser(SESSION_ID, "user1")).thenReturn(groups);

        ResponseEntity<List<GroupInfo>> response = controller.getGroupsForUser("user1", SESSION_ID);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getGroupsForUser_noGroups_returnsEmptyList() {
        when(userGroupService.getGroupsForUser(SESSION_ID, "lonely")).thenReturn(Collections.emptyList());

        ResponseEntity<List<GroupInfo>> response = controller.getGroupsForUser("lonely", SESSION_ID);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isEmpty());
    }
}
