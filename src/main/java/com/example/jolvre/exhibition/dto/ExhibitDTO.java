package com.example.jolvre.exhibition.dto;

import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponses;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class ExhibitDTO {
    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ExhibitUpdateRequest {
        private String title;
        private String authorWord;
        private String introduction;
        private String size;
        private String productionMethod;
        private int price;
        private boolean forSale;

        private MultipartFile thumbnail;
        private List<MultipartFile> images;

    }


    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitUploadRequest {
        private String title;
        private String authorWord;
        private String introduction;
        private String size;
        private String productionMethod;
        private int price;
        private boolean forSale;
        private MultipartFile thumbnail;
        private List<MultipartFile> images;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitResponse {
        private Long id;
        private String title;
        private String authorWord;
        private String introduction;
        private String size;
        private String productionMethod;
        private int price;
        private boolean forSale;
        private String thumbnail;
        private List<String> imagesUrl;
        private String image3d;
        private boolean distribute;
        private UserInfoResponse userInfoResponse;
        private DiaryInfoResponses diaryInfoResponses;

        public static ExhibitResponse toDTO(Exhibit exhibit) {
            return new ExhibitResponse(exhibit.getId(), exhibit.getTitle(),
                    exhibit.getAuthorWord(), exhibit.getIntroduction(),
                    exhibit.getSize(), exhibit.getProductionMethod(), exhibit.getPrice(),
                    exhibit.isForSale(), exhibit.getThumbnail(), exhibit.getImageUrls(), exhibit.getImage3d(),
                    exhibit.isDistribute(),
                    UserInfoResponse.toDTO(exhibit.getUser()), DiaryInfoResponses.toDTO(exhibit.getDiaries())
            );
        }

    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitInfoResponses {
        private List<ExhibitResponse> exhibitResponses;

        public static ExhibitInfoResponses toDTO(List<Exhibit> exhibits) {
            List<ExhibitResponse> responses = new ArrayList<>();

            exhibits.forEach(exhibit -> responses.add(ExhibitResponse.toDTO(exhibit)));

            return new ExhibitInfoResponses(responses);
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitUploadResponse {
        private Long exhibitId;
    }

}
