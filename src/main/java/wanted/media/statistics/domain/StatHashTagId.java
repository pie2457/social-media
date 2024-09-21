package wanted.media.statistics.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatHashTagId implements Serializable {

    private Integer statPostId;
    private String hashTag;
}

