package ch.heig.amt.project.one.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class User {
    private long id;
    private String username;
    private String password;
}
