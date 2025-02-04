package ch.heig.amt.project.one.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class Entity {
    private long id;
    private long owner;
}
