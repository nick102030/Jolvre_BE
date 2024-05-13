package com.example.jolvre.group.dto;

import com.example.jolvre.group.entity.GroupInviteState;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GroupInviteDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class InviteRequest {
        private String inviteState;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class InviteResponse {
        private String exhibit;
        private String inviteState;

        public static InviteResponse toDTO(GroupInviteState inviteState) {
            return new InviteResponse(inviteState.getGroupExhibit().getName(),
                    inviteState.getInviteState().toString());
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class InviteResponses {
        private List<InviteResponse> inviteResponses;

        public static InviteResponses toDTO(List<GroupInviteState> inviteStates) {
            List<InviteResponse> invites = new ArrayList<>();

            inviteStates.forEach(invite -> invites.add(InviteResponse.toDTO(invite)));

            return new InviteResponses(invites);
        }
    }
}
